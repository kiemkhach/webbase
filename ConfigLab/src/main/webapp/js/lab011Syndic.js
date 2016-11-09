function Lab011Syndic(id, address, chauffage, eauChaude, eauFroide, chauffageCommunes,eauChaudeCommunes,
		eauFroideCommunes,codePostal,ville,name,caloonUserId,reportPath,isDefaultSyndic,coeffUnit,coeffTotal,coeffEcs) {
    this.id = id;
    this.address = address;
    this.chauffage = chauffage;
    this.eauChaude = eauChaude;
    this.eauFroide = eauFroide;
    this.chauffageCommunes = chauffageCommunes;
    this.eauChaudeCommunes = eauChaudeCommunes;
    this.eauFroideCommunes = eauFroideCommunes;
    this.codePostal = codePostal;
    this.ville = ville;
    this.name = name;
    this.caloonUserId = caloonUserId;
    this.reportPath = reportPath;
    this.isDefaultSyndic = isDefaultSyndic;
    this.coeffUnit = coeffUnit;
    this.coeffTotal = coeffTotal;
    this.coeffEcs = coeffEcs;
}

function clearDetailForm() {
    $('#txtSyndicId').val('');
    $('#txtAddress').val('');
    $('#txtChauffage').val('');
    $('#txtEauChaude').val('');
    $('#txtEauFroide').val('');
    $('#txtChauffageCommunes').val('');
    $('#txtEauChaudeCommunes').val('');
    $('#txtEauFroideCommunes').val('');
    $('#txtCodePostal').val('');
    $('#txtVille').val('');
    $('#txtName').val('');
    $('#txtCoeffUnit').val('');
    $('#txtCoeffTotal').val('');
    $('#txtCoeffEcs').val('');
}
function clearCreateForm() {
    $('#txtAddresss').val('');
    $('#txtChauffages').val('');
    $('#txtEauChaudes').val('');
    $('#txtEauFroides').val('');
    $('#txtChauffageCommuness').val('');
    $('#txtEauChaudeCommuness').val('');
    $('#txtEauFroideCommuness').val('');
    $('#txtCodePostals').val('');
    $('#txtVilles').val('');
    $('#txtNames').val('');
    $('#txtCoeffUnits').val('');
    $('#txtCoeffTotals').val('');
    $('#txtCoeffEcss').val('');
}
$(document).ready(function () {
    var listSyndicDialog = $('#detailSyndicDialog').data('dialog');
    var createSyndicDialog = $('#createLab011SyndicDialog').data('dialog');
    $('#detailSyndicDialog .dialog-close-button').click(function () {
        clearDetailForm();
    });
    $('#createLab011SyndicDialog .dialog-close-button').click(function () {
    	clearCreateForm();
    });
    $('#btncreateLab011Syndic').click(function(){
    	$('#txtCoeffUnits').val(1);
        $('#txtCoeffTotals').val(1);
        $('#txtCoeffEcss').val(1);
    	createSyndicDialog.open();
    });
    $('.btnEditSyndic').click(function (e) {
        $.ajax({
            method: "POST",
            url: "getLab011SyndicById.action",
            data: {
            	caloonSyndicId: e.currentTarget.value
            },
            traditional: true,
            success: function (data) {
                var jsonData = JSON.parse(data);
                if (jsonData != null) {
                    $('#txtSyndicId').val(jsonData.id);
                    if (jsonData.address != null && jsonData.address != '') {
                        $('#txtAddress').val(jsonData.address);
                    }
                    if (jsonData.chauffage != null && jsonData.chauffage != '') {
                        $('#txtChauffage').val(jsonData.chauffage);
                    }
                    if (jsonData.eauChaude != null && jsonData.eauChaude != '') {
                        $('#txtEauChaude').val(jsonData.eauChaude);
                    }
                    if (jsonData.eauFroide != null && jsonData.eauFroide != '') {
                        $('#txtEauFroide').val(jsonData.eauFroide);
                    }
                    if (jsonData.chauffageCommunes != null && jsonData.chauffageCommunes != '') {
                        $('#txtChauffageCommunes').val(jsonData.chauffageCommunes);
                    }
                    if (jsonData.eauChaudeCommunes != null && jsonData.eauChaudeCommunes != '') {
                        $('#txtEauChaudeCommunes').val(jsonData.eauChaudeCommunes);
                    }
                    if (jsonData.eauFroideCommunes != null && jsonData.eauFroideCommunes != '') {
                        $('#txtEauFroideCommunes').val(jsonData.eauFroideCommunes);
                    }
                    if (jsonData.caloonUserId != null && jsonData.caloonUserId != '') {
                        $('#txtcaloonUserId').val(jsonData.caloonUserId);
                    }
                    if (jsonData.codePostal != null && jsonData.codePostal != '') {
                        $('#txtCodePostal').val(jsonData.codePostal);
                    }
                    if (jsonData.ville != null && jsonData.ville != '') {
                        $('#txtVille').val(jsonData.ville);
                    }
                    if (jsonData.name != null && jsonData.name != '') {
                        $('#txtName').val(jsonData.name);
                    }
                    if (jsonData.isDefaultSyndic != null) {
                        $('#txtIsDefault').val(jsonData.isDefaultSyndic);
                    }
                    if (jsonData.coeffUnit != null && jsonData.coeffUnit != '') {
                        $('#txtCoeffUnit').val(jsonData.coeffUnit);
                    }
                    if (jsonData.coeffTotal != null && jsonData.coeffTotal != '') {
                        $('#txtCoeffTotal').val(jsonData.coeffTotal);
                    }
                    if (jsonData.coeffEcs != null && jsonData.coeffEcs != '') {
                        $('#txtCoeffEcs').val(jsonData.coeffEcs);
                    }
                    
                    listSyndicDialog.open();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    });
    $('#btnSaveSyndic').click(function () {
    	 var isOk = true;
         var fileReportName = null;
         if ($('#uploadFileReport').get(0).files.length > 0) {
        	 fileReportName = $('#uploadFileReport').get(0).files[0].name;
             var formData = new FormData();
             formData.append('fileReport',$('#uploadFileReport').get(0).files[0]);
             var syndicId = ($('#txtSyndicId').val() != '') ? $('#txtSyndicId').val() : 0;
             formData.append('caloonSyndicId',syndicId);
             formData.append('fileReportName',fileReportName);
             $.ajax({
                 url: 'uploadFileReport.action',
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
         if (isOk) {
             var id = null;
             if ($('#txtSyndicId').val() != '') {
                 id = parseInt($('#txtSyndicId').val());
             }
             var address = ($('#txtAddress').val() != '') ? $('#txtAddress').val() : null;
             var chauffage = ($('#txtChauffage').val() != '') ? $('#txtChauffage').val() : null;
             var eauChaude = ($('#txtEauChaude').val() != '') ? $('#txtEauChaude').val() : null;
             var eauFroide = ($('#txtEauFroide').val() != '') ? $('#txtEauFroide').val() : null;
             var chauffageCommunes = ($('#txtChauffageCommunes').val() != '') ? $('#txtChauffageCommunes').val() : null;
             var eauChaudeCommunes = ($('#txtEauChaudeCommunes').val() != '') ? $('#txtEauChaudeCommunes').val() : null;
             var eauFroideCommunes = ($('#txtEauFroideCommunes').val() != '') ? $('#txtEauFroideCommunes').val() : null;
             var codePostal = ($('#txtCodePostal').val() != '') ? $('#txtCodePostal').val() : null;
             var ville = ($('#txtVille').val() != '') ? $('#txtVille').val() : null;
             var name = ($('#txtName').val() != '') ? $('#txtName').val() : null;
             var coeffUnit = ($('#txtCoeffUnit').val() != '') ? $('#txtCoeffUnit').val() : null;
             var coeffTotal = ($('#txtCoeffTotal').val() != '') ? $('#txtCoeffTotal').val() : null;
             var coeffEcs = ($('#txtCoeffEcs').val() != '') ? $('#txtCoeffEcs').val() : null;
             var caloonUserId = null;
             if ($('#txtcaloonUserId').val() != '') {
            	 caloonUserId = parseInt($('#txtcaloonUserId').val());
             }
             var isDefaultSyndic = $('#txtIsDefault').val();
             var reportPath = fileReportName;
             
             if($('#txtCoeffUnit').val() == null || $('#txtCoeffUnit').val() =='' || isNaN($('#txtCoeffUnit').val())) {
     			alert("Coefficients for convert kwh to m3 not a number");
     			$("#txtCoeffUnit").focus();
     			return false;
     		}
             if($('#txtCoeffTotal').val() == null || $('#txtCoeffTotal').val() =='' || isNaN($('#txtCoeffTotal').val())) {
      			alert("Coefficients for total not a number");
      			$("#txtCoeffTotal").focus();
      			return false;
      		}
             if($('#txtCoeffEcs').val() == null || $('#txtCoeffEcs').val() == '' || isNaN($('#txtCoeffEcs').val())) {
      			alert("Coefficients for ECS not a number");
      			$("#txtCoeffEcs").focus();
      			return false;
      		}
             
             var caloonSyndic = new Lab011Syndic(id, address, chauffage, eauChaude, eauFroide, chauffageCommunes, 
            			eauChaudeCommunes,eauFroideCommunes,codePostal,ville,name,caloonUserId,reportPath,isDefaultSyndic,coeffUnit,coeffTotal,coeffEcs);
             console.log("client = " + JSON.stringify(caloonSyndic));
             var data = {
            		 caloonSyndic: caloonSyndic
             };
             $.ajax({
                 url: 'saveLab011Syndic.action',
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

    });
    $('#btnSaveNewSyndic').click(function () {
    	var isOk = true;
        var fileReportName = null;
        if ($('#uploadFileReports').get(0).files.length > 0) {
       	 fileReportName = $('#uploadFileReports').get(0).files[0].name;
            var formData = new FormData();
            formData.append('fileReport',$('#uploadFileReports').get(0).files[0]);
            var syndicId = ($('#txtSyndicId').val() != '') ? $('#txtSyndicId').val() : 0;
            formData.append('caloonSyndicId',syndicId);
            formData.append('fileReportName',fileReportName);
            $.ajax({
                url: 'uploadFileReport.action',
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
        if (isOk) {
        	var id = null;
            var address = ($('#txtAddresss').val() != '') ? $('#txtAddresss').val() : null;
            var chauffage = ($('#txtChauffages').val() != '') ? $('#txtChauffages').val() : null;
            var eauChaude = ($('#txtEauChaudes').val() != '') ? $('#txtEauChaudes').val() : null;
            var eauFroide = ($('#txtEauFroides').val() != '') ? $('#txtEauFroides').val() : null;
            var chauffageCommunes = ($('#txtChauffageCommuness').val() != '') ? $('#txtChauffageCommuness').val() : null;
            var eauChaudeCommunes = ($('#txtEauChaudeCommuness').val() != '') ? $('#txtEauChaudeCommuness').val() : null;
            var eauFroideCommunes = ($('#txtEauFroideCommuness').val() != '') ? $('#txtEauFroideCommuness').val() : null;
            var codePostal = ($('#txtCodePostals').val() != '') ? $('#txtCodePostals').val() : null;
            var ville = ($('#txtVilles').val() != '') ? $('#txtVilles').val() : null;
            var name = ($('#txtNames').val() != '') ? $('#txtNames').val() : null;
            var coeffUnit = ($('#txtCoeffUnits').val() != '') ? $('#txtCoeffUnits').val() : null;
            var coeffTotal = ($('#txtCoeffTotals').val() != '') ? $('#txtCoeffTotals').val() : null;
            var coeffEcs = ($('#txtCoeffEcss').val() != '') ? $('#txtCoeffEcss').val() : null;
            var caloonUserId = $('#userId').val();
            var reportPath = fileReportName;
            var isDefaultSyndic = false;
            
            if($('#txtCoeffUnits').val() == null || $('#txtCoeffUnits').val() =='' || isNaN($('#txtCoeffUnits').val())) {
     			alert("Coefficients for convert kwh to m3 not a number");
     			$("#txtCoeffUnits").focus();
     			return false;
     		}
             if($('#txtCoeffTotals').val() == null || $('#txtCoeffTotals').val() =='' || isNaN($('#txtCoeffTotals').val())) {
      			alert("Coefficients for total not a number");
      			$("#txtCoeffTotals").focus();
      			return false;
      		}
             if($('#txtCoeffEcss').val() == null || $('#txtCoeffEcss').val() =='' || isNaN($('#txtCoeffEcss').val())) {
      			alert("Coefficients for ECS not a number");
      			$("#txtCoeffEcss").focus();
      			return false;
      		}
            
            var caloonSyndic = new Lab011Syndic(id, address, chauffage, eauChaude, eauFroide, chauffageCommunes,
            		eauChaudeCommunes,eauFroideCommunes,codePostal,ville,name,caloonUserId,reportPath,isDefaultSyndic,coeffUnit,coeffTotal,coeffEcs);
            console.log("client = " + JSON.stringify(caloonSyndic));
            var data = {
           		 caloonSyndic: caloonSyndic
            };
            $.ajax({
                url: 'saveLab011Syndic.action',
                type: 'POST',
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                beforeSend: function () {
                    $('#btnSaveNewSyndic').addClass('loading-cube');
                    $('#btnSaveNewSyndic').addClass('lighten');
                    $('#btnSaveNewSyndic').text('Saving...');
                },
                success: function (data) {
                    console.log(data);
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $('#btnSaveNewSyndic').removeClass('primary loading-cube ');
                        $('#btnSaveNewSyndic').addClass('success');
                        $('#btnSaveNewSyndic').attr('disabled', 'disabled');
                        $('#btnSaveNewSyndic').text('Success');
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
    });
    $('.btnRemoveSyndic').click(
			function(e) {
				var result = confirm('Are you sure you want to delete this item?');
				if (result) {
					$.ajax({
						url : 'removeLab011Syndic.action',
						data : {
							syndicId: e.currentTarget.value
						},
						traditional : true,
						success : function(data) {
							var rs = JSON.parse(data);
							if (rs.result == 'success') {
								$.Notify({
											caption : 'Success',
											content : 'Remove Syndic success',
											type : 'success'
										});
								window.location.href = 'syndicConfig.action';
							} else {
								$.Notify({
											caption : 'Failed',
											content : 'Remove Syndic failed',
											type : 'alert'
										});
							}
						},
						error : function(XMLHttpRequest,textStatus,errorThrown) {
							console.log(XMLHttpRequest.responseText);
						}
					});
				}
			});
});
