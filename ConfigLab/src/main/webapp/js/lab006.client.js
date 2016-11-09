function Lab006Client(id, subscriptionId, clientName, listModuleHCE, listModuleHCH, listModuleHPE, listModuleHPH,reportClientName,imgClientName) {
    this.id = id;
    this.subscriptionId = subscriptionId;
    this.clientName = clientName;
    this.listModuleHCE = listModuleHCE;
    this.listModuleHCH = listModuleHCH;
    this.listModuleHPE = listModuleHPE;
    this.listModuleHPH = listModuleHPH;
    this.reportClientName = reportClientName;
    this.imgClientName = imgClientName;
}
function clearDetailForm() {
    $('#txtClientId').val('');
    $('#txtClientName').val('');
    $('#txtListModuleHCE').val('');
    $('#txtListModuleHCH').val('');
    $('#txtListModuleHPE').val('');
    $('#txtListModuleHPH').val('');
    $('#txtReportClientName').val('');
    $('#txtImageClient').val('');
}
$(document).ready(function () {
    var listClientDialog = $('#listClientDialog').data('dialog');
    var detailClientDialog = $('#detailClientDialog').data('dialog');
    //$('#dpkSummerStartDate').datepicker();
    $('#btnCreateClient').click(function () {
        listClientDialog.close();
        clearDetailForm();
        detailClientDialog.open();
    });
    $('#btnShowAllClients').click(function () {
        listClientDialog.open();
    });
    $('#detailClientDialog .dialog-close-button').click(function () {
        clearDetailForm();
        listClientDialog.open();
    });
    $('.btnRemoveClient').click(function (e) {
        var result = confirm('Are you sure you want to delete this item?');
        if (result) {
            $.ajax({
                url: 'removeLab006Client.action',
                data: {
                    clientId: e.currentTarget.value
                },
                traditional: true,
                success: function (data) {
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $.Notify({
                            caption: 'Success',
                            content: 'Remove client success',
                            type: 'success'
                        });
                        location.reload();
                    } else {
                        $.Notify({
                            caption: 'Failed',
                            content: 'Remove client failed',
                            type: 'alert'
                        });
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(XMLHttpRequest.responseText);
                }
            });
        }
    });
    $('.btnEditClient').click(function (e) {
        listClientDialog.close();
        $.ajax({
            method: "POST",
            url: "getLab006ClientById.action",
            data: {
                clientId: e.currentTarget.value
            },
            traditional: true,
            success: function (data) {
                var jsonData = JSON.parse(data);
                if (jsonData != null) {
                    $('#txtClientId').val(jsonData.id);
                    if (jsonData.clientName != null && jsonData.clientName != '') {
                        $('#txtClientName').val(jsonData.clientName);
                    }
                    if (jsonData.listModuleHCE != null && jsonData.listModuleHCE != '') {
                        $('#txtListModuleHCE').val(jsonData.listModuleHCE);
                    }
                    if (jsonData.listModuleHCH != null && jsonData.listModuleHCH != '') {
                        $('#txtListModuleHCH').val(jsonData.listModuleHCH);
                    }
                    if (jsonData.listModuleHPE != null && jsonData.listModuleHPE != '') {
                        $('#txtListModuleHPE').val(jsonData.listModuleHPE);
                    }
                    if (jsonData.listModuleHPH != null && jsonData.listModuleHPH != '') {
                        $('#txtListModuleHPH').val(jsonData.listModuleHPH);
                    }
                    if (jsonData.reportClientName != null && jsonData.reportClientName != '') {
                        $('#txtReportClientName').val(jsonData.reportClientName);
                    }
                    if (jsonData.imgClientName != null && jsonData.imgClientName != '') {
                        $('#txtImageClient').val(jsonData.imgClientName);
                    }
                    detailClientDialog.open();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    });
    $('#btnSave').click(function () {
        var isOk = true;
        var fileReportName = null;
        var fileImgName = null;
        if ($('#uploadClientImage').get(0).files.length > 0) {
            fileImgName = $('#uploadClientImage').get(0).files[0].name;
            var formData = new FormData();
            formData.append('fileImage',$('#uploadClientImage').get(0).files[0]);
            var clientId = ($('#txtClientId').val() != '') ? $('#txtClientId').val() : 0;
            formData.append('clientId',clientId);
            formData.append('fileImgName',fileImgName);
            $.ajax({
                url: 'uploadClientFiles.action',
                data: formData,
                processData: false,
                contentType: false,
                async: false,
                type: 'POST',
                success: function(result){
                    console.log('a'+result.result);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    isOk = false;
                    console.log(XMLHttpRequest.responseText);
                    return;
                }
            });
        }

        
        //save client object
        if (isOk) {
            var id = null;
            if ($('#txtClientId').val() != '') {
                id = parseInt($('#txtClientId').val());
            }
            var subscriptionId = ($('#subscriptionId').val() != '') ? $('#subscriptionId').val() : null;
            var clientName = ($('#txtClientName').val() != '') ? $('#txtClientName').val() : null;
            var listModuleHCE = ($('#txtListModuleHCE').val() != '') ? $('#txtListModuleHCE').val() : null;
            var listModuleHPE = ($('#txtListModuleHPE').val() != '') ? $('#txtListModuleHPE').val() : null;
            var listModuleHCH = ($('#txtListModuleHCH').val() != '') ? $('#txtListModuleHCH').val() : null;
            var listModuleHPH = ($('#txtListModuleHPH').val() != '') ? $('#txtListModuleHPH').val() : null;
            var reportClientName = fileReportName;
            var imgClientName = fileImgName;
            var client = new Lab006Client(id, subscriptionId, clientName, listModuleHCE, listModuleHCH, listModuleHPE, listModuleHPH, fileReportName, fileImgName);
        		var fileImgNameDefault = null;
                if ($('#useDefaultClientImage').is(':checked')) {
                	client = new Lab006Client(id, subscriptionId, clientName, listModuleHCE, listModuleHCH, listModuleHPE, listModuleHPH, fileReportName, fileImgNameDefault);
                	console.log("radio checked");
                	console.log("client checked radio = " + JSON.stringify(client));
                }
                else {
                	console.log("not check");
                }
            	
            console.log("client = " + JSON.stringify(client));
            var data = {
                client: client
            };
            $.ajax({
                url: 'saveLab006Client.action',
                type: 'POST',
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                beforeSend: function () {
                    $('#btnSave').addClass('loading-cube');
                    $('#btnSave').addClass('lighten');
                    $('#btnSave').text('Saving...');
                },
                success: function (data) {
                    console.log(data);
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $('#btnSave').removeClass('primary loading-cube ');
                        $('#btnSave').addClass('success');
                        $('#btnSave').attr('disabled', 'disabled');
                        $('#btnSave').text('Success');
                        location.reload(true);
                    } else {
                        console.log(rs);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(XMLHttpRequest.responseText);
                }
            });
        };
        
        $('#useDefaultClientImage').click(function () {
        	var id = null;
            if ($('#txtClientId').val() != '') {
                id = parseInt($('#txtClientId').val());
            }
            var subscriptionId = ($('#subscriptionId').val() != '') ? $('#subscriptionId').val() : null;
            var clientName = ($('#txtClientName').val() != '') ? $('#txtClientName').val() : null;
            var listModuleHCE = ($('#txtListModuleHCE').val() != '') ? $('#txtListModuleHCE').val() : null;
            var listModuleHPE = ($('#txtListModuleHPE').val() != '') ? $('#txtListModuleHPE').val() : null;
            var listModuleHCH = ($('#txtListModuleHCH').val() != '') ? $('#txtListModuleHCH').val() : null;
            var listModuleHPH = ($('#txtListModuleHPH').val() != '') ? $('#txtListModuleHPH').val() : null;
            var reportClientName = fileReportName;
            var imgClientName = fileImgName;
            var client = new Lab006Client(id, subscriptionId, clientName, listModuleHCE, listModuleHCH, listModuleHPE, listModuleHPH, fileReportName, fileImgName);
        	var fileImgNameDefault = null;
            if ($('#useDefaultClientImage').is(':checked')) {
            	client = new Lab006Client(id, subscriptionId, clientName, listModuleHCE, listModuleHCH, listModuleHPE, listModuleHPH, fileReportName, fileImgNameDefault);
            	console.log("radio checked");
            	console.log("client checked radio = " + JSON.stringify(client));
            }
            console.log("client = " + JSON.stringify(client));
            var data = {
                client: client
            };
            $.ajax({
                url: 'saveLab006Client.action',
                type: 'POST',
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                beforeSend: function () {
                    $('#btnSave').addClass('loading-cube');
                    $('#btnSave').addClass('lighten');
                    $('#btnSave').text('Saving...');
                },
                success: function (data) {
                    console.log(data);
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $('#btnSave').removeClass('primary loading-cube ');
                        $('#btnSave').addClass('success');
                        $('#btnSave').attr('disabled', 'disabled');
                        $('#btnSave').text('Success');
                        location.reload(true);
                    } else {
                        console.log(rs);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(XMLHttpRequest.responseText);
                }
            });
        	
        });
    });
});
