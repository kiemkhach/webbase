function Lab008ECS(id, moduleNumberCSM, configlab008v2id, name, type) {
    this.id = id;
    this.moduleNumberCSM = moduleNumberCSM;
    this.configlab008v2id = configlab008v2id;
    this.name = name;
    this.type = type;
}
function clearDetailForm() {
    $('#txtECSId').val('');
    $('#txtECSName').val('');
    $('#txtListModuleNoCSM').val('');
   // $('#selectType').val(1);
}
$(document).ready(function () {
	var listLab008ConsommationChauffageDialog = $('#listLab008ConsommationChauffageDialog').data('dialog');
	var listConsommationECSDialog = $('#listConsommationECSDialog').data('dialog');
	var listTemperatureAmbianteLogementDialog = $('#listTemperatureAmbianteLogementDialog').data('dialog');
	var listTemperatureProductionChauffageDialog = $('#listTemperatureProductionChauffageDialog').data('dialog');
	var listTemperatureProductionECSDialog = $('#listTemperatureProductionECSDialog').data('dialog');
	var listTemperatureRecyclageECSDialog = $('#listTemperatureRecyclageECSDialog').data('dialog');
	var listModuleWaterECSDialog = $('#listModuleWaterECSDialog').data('dialog');
	var listModuleVentilationECSDialog = $('#listModuleVentilationECSDialog').data('dialog');
    var detailECSDialog = $('#detailECSDialog').data('dialog');
    
    
    //8
    $('#btncreateModuleVentilationECS').click(function () {
    	listModuleVentilationECSDialog.close();
        clearDetailForm();
        $('#selectType').val(8);
        detailECSDialog.open();
    });
    $('#btnShowAlllab008ModuleVentilationLst').click(function () {
    	listModuleVentilationECSDialog.open();
    });
    $('#detailECSDialog .dialog-close-button').click(function () {
        clearDetailForm();
       // listECSDialog.open();//here
    });
    $('.btnRemoveLab008ModuleVentilationECSLst').click(function (e) {
        var result = confirm('Are you sure you want to delete this item?');
        if (result) {
            $.ajax({
                url: 'removeLab008ECS.action',
                data: {
                    ecsId: e.currentTarget.value
                },
                traditional: true,
                success: function (data) {
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $.Notify({
                            caption: 'Success',
                            content: 'Remove ecs success',
                            type: 'success'
                        });
                        location.reload();
                    } else {
                        $.Notify({
                            caption: 'Failed',
                            content: 'Remove ecs failed',
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
    $('.btnEditLab008ModuleVentilationECSLst').click(function (e) {
    	listTemperatureRecyclageECSDialog.close();
        $.ajax({
            method: "POST",
            url: "getLab008ECSById.action",
            data: {
            	ecsId: e.currentTarget.value
            },
            traditional: true,
            success: function (data) {
                var jsonData = JSON.parse(data);
                if (jsonData != null) {
                    $('#txtECSId').val(jsonData.id);
                    if (jsonData.name != null && jsonData.name != '') {
                        $('#txtECSName').val(jsonData.name);
                    }
                    if (jsonData.moduleNumberCSM != null && jsonData.moduleNumberCSM != '') {
                        $('#txtListModuleNoCSM').val(jsonData.moduleNumberCSM);
                    }
                    $('#selectType').val(jsonData.type);
                    detailECSDialog.open();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    });
    
    //1
    $('#btncreateLab008ConsommationChauffageLst').click(function () {
    	listLab008ConsommationChauffageDialog.close();
        clearDetailForm();
        $('#selectType').val(1);
        detailECSDialog.open();
    });
    $('#btnShowAlllab008ConsommationChauffageLst').click(function () {
    	listLab008ConsommationChauffageDialog.open();
    });
    $('#detailECSDialog .dialog-close-button').click(function () {
        clearDetailForm();
       // listECSDialog.open();//here
    });
    $('.btnRemoveLab008ConsommationChauffage').click(function (e) {
        var result = confirm('Are you sure you want to delete this item?');
        if (result) {
            $.ajax({
                url: 'removeLab008ECS.action',
                data: {
                    ecsId: e.currentTarget.value
                },
                traditional: true,
                success: function (data) {
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $.Notify({
                            caption: 'Success',
                            content: 'Remove ecs success',
                            type: 'success'
                        });
                        location.reload();
                    } else {
                        $.Notify({
                            caption: 'Failed',
                            content: 'Remove ecs failed',
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
    $('.btnEditLab008ConsommationChauffage').click(function (e) {
    	listLab008ConsommationChauffageDialog.close();
        $.ajax({
            method: "POST",
            url: "getLab008ECSById.action",
            data: {
            	ecsId: e.currentTarget.value
            },
            traditional: true,
            success: function (data) {
                var jsonData = JSON.parse(data);
                if (jsonData != null) {
                    $('#txtECSId').val(jsonData.id);
                    if (jsonData.name != null && jsonData.name != '') {
                        $('#txtECSName').val(jsonData.name);
                    }
                    if (jsonData.moduleNumberCSM != null && jsonData.moduleNumberCSM != '') {
                        $('#txtListModuleNoCSM').val(jsonData.moduleNumberCSM);
                    }
                    $('#selectType').val(jsonData.type);
                    detailECSDialog.open();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    });
    
    //2
    $('#btncreateConsommationECS').click(function () {
    	listConsommationECSDialog.close();
        clearDetailForm();
        $('#selectType').val(2);
        detailECSDialog.open();
    });
    
    
    $('#btnShowAllConsommationECSLst').click(function () {
    	listConsommationECSDialog.open();
    });
    $('#detailECSDialog .dialog-close-button').click(function () {
        clearDetailForm();
       // listECSDialog.open();//here
    });
    $('.btnRemoveLab008ConsommationECSLst').click(function (e) {
        var result = confirm('Are you sure you want to delete this item?');
        if (result) {
            $.ajax({
                url: 'removeLab008ECS.action',
                data: {
                    ecsId: e.currentTarget.value
                },
                traditional: true,
                success: function (data) {
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $.Notify({
                            caption: 'Success',
                            content: 'Remove ecs success',
                            type: 'success'
                        });
                        location.reload();
                    } else {
                        $.Notify({
                            caption: 'Failed',
                            content: 'Remove ecs failed',
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
    $('.btnEditLab008ConsommationECSLst').click(function (e) {
    	listConsommationECSDialog.close();
        $.ajax({
            method: "POST",
            url: "getLab008ECSById.action",
            data: {
            	ecsId: e.currentTarget.value
            },
            traditional: true,
            success: function (data) {
                var jsonData = JSON.parse(data);
                if (jsonData != null) {
                    $('#txtECSId').val(jsonData.id);
                    if (jsonData.name != null && jsonData.name != '') {
                        $('#txtECSName').val(jsonData.name);
                    }
                    if (jsonData.moduleNumberCSM != null && jsonData.moduleNumberCSM != '') {
                        $('#txtListModuleNoCSM').val(jsonData.moduleNumberCSM);
                    }
                    $('#selectType').val(jsonData.type);
                    detailECSDialog.open();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    });
    
    //3
    $('#btncreateTemperatureAmbianteLogement').click(function () {
    	listTemperatureAmbianteLogementDialog.close();
        clearDetailForm();
        $('#selectType').val(3);
        detailECSDialog.open();
    });
    $('#btnShowAllTemperatureAmbianteLogementLst').click(function () {
    	listTemperatureAmbianteLogementDialog.open();
    });
    $('#detailECSDialog .dialog-close-button').click(function () {
        clearDetailForm();
       // listECSDialog.open();//here
    });
    $('.btnRemoveLab008TemperatureAmbianteLogementLst').click(function (e) {
        var result = confirm('Are you sure you want to delete this item?');
        if (result) {
            $.ajax({
                url: 'removeLab008ECS.action',
                data: {
                    ecsId: e.currentTarget.value
                },
                traditional: true,
                success: function (data) {
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $.Notify({
                            caption: 'Success',
                            content: 'Remove ecs success',
                            type: 'success'
                        });
                        location.reload();
                    } else {
                        $.Notify({
                            caption: 'Failed',
                            content: 'Remove ecs failed',
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
    $('.btnEditLab008TemperatureAmbianteLogementLst').click(function (e) {
    	listTemperatureAmbianteLogementDialog.close();
        $.ajax({
            method: "POST",
            url: "getLab008ECSById.action",
            data: {
            	ecsId: e.currentTarget.value
            },
            traditional: true,
            success: function (data) {
                var jsonData = JSON.parse(data);
                if (jsonData != null) {
                    $('#txtECSId').val(jsonData.id);
                    if (jsonData.name != null && jsonData.name != '') {
                        $('#txtECSName').val(jsonData.name);
                    }
                    if (jsonData.moduleNumberCSM != null && jsonData.moduleNumberCSM != '') {
                        $('#txtListModuleNoCSM').val(jsonData.moduleNumberCSM);
                    }
                    $('#selectType').val(jsonData.type);
                    detailECSDialog.open();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    });
    
    //4
    $('#btncreateTemperatureProductionChauffage').click(function () {
    	listTemperatureProductionChauffageDialog.close();
        clearDetailForm();
        $('#selectType').val(4);
        detailECSDialog.open();
    });
    $('#btnShowAllTemperatureProductionChauffageLst').click(function () {
    	listTemperatureProductionChauffageDialog.open();
    });
    $('#detailECSDialog .dialog-close-button').click(function () {
        clearDetailForm();
       // listECSDialog.open();//here
    });
    $('.btnRemoveLab008TemperatureProductionChauffageLst').click(function (e) {
        var result = confirm('Are you sure you want to delete this item?');
        if (result) {
            $.ajax({
                url: 'removeLab008ECS.action',
                data: {
                    ecsId: e.currentTarget.value
                },
                traditional: true,
                success: function (data) {
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $.Notify({
                            caption: 'Success',
                            content: 'Remove ecs success',
                            type: 'success'
                        });
                        location.reload();
                    } else {
                        $.Notify({
                            caption: 'Failed',
                            content: 'Remove ecs failed',
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
    $('.btnEditLab008TemperatureProductionChauffageLst').click(function (e) {
    	listTemperatureProductionChauffageDialog.close();
        $.ajax({
            method: "POST",
            url: "getLab008ECSById.action",
            data: {
            	ecsId: e.currentTarget.value
            },
            traditional: true,
            success: function (data) {
                var jsonData = JSON.parse(data);
                if (jsonData != null) {
                    $('#txtECSId').val(jsonData.id);
                    if (jsonData.name != null && jsonData.name != '') {
                        $('#txtECSName').val(jsonData.name);
                    }
                    if (jsonData.moduleNumberCSM != null && jsonData.moduleNumberCSM != '') {
                        $('#txtListModuleNoCSM').val(jsonData.moduleNumberCSM);
                    }
                    $('#selectType').val(jsonData.type);
                    detailECSDialog.open();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    });
    
    //5
    $('#btncreateTemperatureProductionECS').click(function () {
    	listTemperatureProductionECSDialog.close();
        clearDetailForm();
        $('#selectType').val(5);
        detailECSDialog.open();
    });
    $('#btnShowAllTemperatureProductionECSLst').click(function () {
    	listTemperatureProductionECSDialog.open();
    });
    $('#detailECSDialog .dialog-close-button').click(function () {
        clearDetailForm();
       // listECSDialog.open();//here
    });
    $('.btnRemoveLab008TemperatureProductionECSLst').click(function (e) {
        var result = confirm('Are you sure you want to delete this item?');
        if (result) {
            $.ajax({
                url: 'removeLab008ECS.action',
                data: {
                    ecsId: e.currentTarget.value
                },
                traditional: true,
                success: function (data) {
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $.Notify({
                            caption: 'Success',
                            content: 'Remove ecs success',
                            type: 'success'
                        });
                        location.reload();
                    } else {
                        $.Notify({
                            caption: 'Failed',
                            content: 'Remove ecs failed',
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
    $('.btnEditLab008TemperatureProductionECSLst').click(function (e) {
    	listTemperatureProductionECSDialog.close();
        $.ajax({
            method: "POST",
            url: "getLab008ECSById.action",
            data: {
            	ecsId: e.currentTarget.value
            },
            traditional: true,
            success: function (data) {
                var jsonData = JSON.parse(data);
                if (jsonData != null) {
                    $('#txtECSId').val(jsonData.id);
                    if (jsonData.name != null && jsonData.name != '') {
                        $('#txtECSName').val(jsonData.name);
                    }
                    if (jsonData.moduleNumberCSM != null && jsonData.moduleNumberCSM != '') {
                        $('#txtListModuleNoCSM').val(jsonData.moduleNumberCSM);
                    }
                    $('#selectType').val(jsonData.type);
                    detailECSDialog.open();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    });
    
    //6
    $('#btncreateTemperatureRecyclageECS').click(function () {
    	listTemperatureRecyclageECSDialog.close();
        clearDetailForm();
        $('#selectType').val(6);
        detailECSDialog.open();
    });
    $('#btnShowAllTemperatureRecyclageECSLst').click(function () {
    	listTemperatureRecyclageECSDialog.open();
    });
    $('#detailECSDialog .dialog-close-button').click(function () {
        clearDetailForm();
       // listECSDialog.open();//here
    });
    $('.btnRemoveLab008TemperatureRecyclageECSLst').click(function (e) {
        var result = confirm('Are you sure you want to delete this item?');
        if (result) {
            $.ajax({
                url: 'removeLab008ECS.action',
                data: {
                    ecsId: e.currentTarget.value
                },
                traditional: true,
                success: function (data) {
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $.Notify({
                            caption: 'Success',
                            content: 'Remove ecs success',
                            type: 'success'
                        });
                        location.reload();
                    } else {
                        $.Notify({
                            caption: 'Failed',
                            content: 'Remove ecs failed',
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
    $('.btnEditLab008TemperatureRecyclageECSLst').click(function (e) {
    	listTemperatureRecyclageECSDialog.close();
        $.ajax({
            method: "POST",
            url: "getLab008ECSById.action",
            data: {
            	ecsId: e.currentTarget.value
            },
            traditional: true,
            success: function (data) {
                var jsonData = JSON.parse(data);
                if (jsonData != null) {
                    $('#txtECSId').val(jsonData.id);
                    if (jsonData.name != null && jsonData.name != '') {
                        $('#txtECSName').val(jsonData.name);
                    }
                    if (jsonData.moduleNumberCSM != null && jsonData.moduleNumberCSM != '') {
                        $('#txtListModuleNoCSM').val(jsonData.moduleNumberCSM);
                    }
                    $('#selectType').val(jsonData.type);
                    detailECSDialog.open();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    });
    
    //7
    $('#btncreateModuleWaterECS').click(function () {
    	listModuleWaterECSDialog.close();
        clearDetailForm();
        $('#selectType').val(7);
        detailECSDialog.open();
    });
    $('#btnShowAlllab008ModuleWateLst').click(function () {
    	listModuleWaterECSDialog.open();
    });
    $('#detailECSDialog .dialog-close-button').click(function () {
        clearDetailForm();
       // listECSDialog.open();//here
    });
    $('.btnRemoveLab008ModuleWaterECSLst').click(function (e) {
        var result = confirm('Are you sure you want to delete this item?');
        if (result) {
            $.ajax({
                url: 'removeLab008ECS.action',
                data: {
                    ecsId: e.currentTarget.value
                },
                traditional: true,
                success: function (data) {
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $.Notify({
                            caption: 'Success',
                            content: 'Remove ecs success',
                            type: 'success'
                        });
                        location.reload();
                    } else {
                        $.Notify({
                            caption: 'Failed',
                            content: 'Remove ecs failed',
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
    $('.btnEditLab008ModuleWaterECSLst').click(function (e) {
    	listTemperatureRecyclageECSDialog.close();
        $.ajax({
            method: "POST",
            url: "getLab008ECSById.action",
            data: {
            	ecsId: e.currentTarget.value
            },
            traditional: true,
            success: function (data) {
                var jsonData = JSON.parse(data);
                if (jsonData != null) {
                    $('#txtECSId').val(jsonData.id);
                    if (jsonData.name != null && jsonData.name != '') {
                        $('#txtECSName').val(jsonData.name);
                    }
                    if (jsonData.moduleNumberCSM != null && jsonData.moduleNumberCSM != '') {
                        $('#txtListModuleNoCSM').val(jsonData.moduleNumberCSM);
                    }
                    $('#selectType').val(jsonData.type);
                    detailECSDialog.open();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    });
    
    $('#btnSaveBean').click(function () {
    	 var selectedOption = $("#target option:selected");
    	if (selectedOption.val() != 1) {
			var inputUpdate = $("input#subscribed").val();
			var inputCreate = $("#subInput").val();
			$("input#subscribed").val("1");
			$("input#energyProvider").val("");
			$("#subInput").val("1");
			$("#energy").val("");
		}else{
			var inputUpdate = $("input#subscribed").val();
			var inputCreate = $("#subInput").val();
			if ((inputUpdate == 0 ||inputUpdate == undefined )&& ( inputCreate == 0 ||inputCreate == undefined) ) {
				isOk = false;
				alert("value Subcribed Power must be greater than 0");
				return false;
			}
		} 
    });
    
    $('#btnSave').click(function () {
//        var isOk = true;
//        //save client object
//        
//        var selectedOption = $("#target option:selected");
//		if (selectedOption.val() != 1) {
//			var inputUpdate = $("input#subscribed").val();
//			var inputCreate = $("#subInput").val();
//			$("input#subscribed").val("1");
//			$("#subInput").val("1");
//		}else{
//			var inputUpdate = $("input#subscribed").val();
//			var inputCreate = $("#subInput").val();
//			if ((inputUpdate == 0 ||inputUpdate == undefined )&& ( inputCreate == 0 ||inputCreate == undefined) ) {
//				isOk = false;
//				alert("value Subcribed Power must be greater than 0");
//				  
//			}
//		} 
//        if (isOk) {
            var id = null;
            if ($('#txtECSId').val() != '') {
                id = parseInt($('#txtECSId').val());
            }
            var configlab008v2id = ($('#subscriptionId').val() != '') ? $('#subscriptionId').val() : null;
            var name = ($('#txtECSName').val() != '') ? $('#txtECSName').val() : null;
            var moduleNumberCSM = ($('#txtListModuleNoCSM').val() != '') ? $('#txtListModuleNoCSM').val() : null;
            var type = $('#selectType').val();
            var ecs = new Lab008ECS(id, moduleNumberCSM, configlab008v2id, name, type);
            	
            console.log("ecs = " + JSON.stringify(ecs));
            var data = {
                ecs: ecs
            };
            $.ajax({
                url: 'saveLab008ECS.action',
                type: 'POST',
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                async: false,
                beforeSend: function () {
                    $('#btnSave').text('Saving...');
                },
                success: function (data) {
                    console.log(data);
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $('#btnSave').text('Success');
                        location.reload(true);
                    } else {
                    	$('#btnSave').text('Save');
                        alert(rs.result);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(XMLHttpRequest.responseText);
//                    $('#btnSave').removeClass('primary loading-cube ');
                    $('#btnSave').text('Save');
                }
            });
//        };
	
        
    });
});
