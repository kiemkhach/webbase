function Lab011Resident(id, appartementNumber, chauffage, eauChaude, eauFroide, clientName,superficie,logements,caloonUserId) {
    this.id = id;
    this.appartementNumber = appartementNumber;
    this.chauffage = chauffage;
    this.eauChaude = eauChaude;
    this.eauFroide = eauFroide;
    this.clientName= clientName;
    this.superficie = superficie;
    this.logements = logements;
    this.caloonUserId = caloonUserId;
}

function clearDetailForm() {
    $('#txtResidentId').val('');
    $('#txtAppartementNumber').val('');
//    $('#txtcaloonSyndicId').val('');
    $('#txtChauffage').val('');
    $('#txtEauChaude').val('');
    $('#txtEauFroide').val('');
    $('#txtClientName').val('');
    $('#txtSuperficies').val('');
    $('#empty').prop('checked', false);
    $('#occupied').prop('checked', false);
}
function changeCheckBoxTrue(){
		$('#empty').prop('checked', false);
	    $('#occupied').prop('checked', true);
}
function changeCheckBoxFalse(){
		$('#empty').prop('checked', true);
	    $('#occupied').prop('checked', false);
}
$(document).ready(function () {
    var listResidentDialog = $('#detailResidentDialog').data('dialog');
    var createDialog = $('#createLab011ResidentDialog').data('dialog');
    $('#detailResidentDialog .dialog-close-button').click(function () {
        clearDetailForm();
    });
    
    $('#btncreateLab011Resident').click(function(){
    	createDialog.open();
    });
    
    $('.btnEditResident').click(function (e) {
        $.ajax({
            method: "POST",
            url: "getLab011ResidentById.action",
            data: {
            	residentId: e.currentTarget.value
            },
            traditional: true,
            success: function (data) {
                var jsonData = JSON.parse(data);
                if (jsonData != null) {
                    $('#txtResidentId').val(jsonData.residentId);
                    if (jsonData.appartementNumber != null && jsonData.appartementNumber != '') {
                        $('#txtAppartementNumber').val(jsonData.appartementNumber);
                    }
                    if (jsonData.clientName != null && jsonData.clientName != '') {
                        $('#txtClientName').val(jsonData.clientName);
                    }
                    if (jsonData.caloonSyndicId != null && jsonData.caloonSyndicId != '') {
                        $('#txtcaloonSyndicId').val(jsonData.caloonSyndicId);
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
                    if (jsonData.superficie != null && jsonData.superficie != '') {
                        $('#txtSuperficie').val(jsonData.superficie);
                    }
                    if (jsonData.userId != null && jsonData.userId != '') {
                        $('#txtcaloonUserId').val(jsonData.userId);
                    }
                	if(!jsonData.logements){
                		$('#empty').prop('checked', true);
                	}else{
                		$('#occupied').prop('checked', true);
                	}
                
                    listResidentDialog.open();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(XMLHttpRequest.responseText);
            }
        });
    });
    $('#btnSaveResident').click(function () {
        var isOk = true;
        if (isOk) {
            var id = null;
            if ($('#txtResidentId').val() != '') {
                id = parseInt($('#txtResidentId').val());
            }
            var appartementNumber = ($('#txtAppartementNumber').val() != '') ? $('#txtAppartementNumber').val() : null;
            var clientName = ($('#txtClientName').val() != '') ? $('#txtClientName').val() : null;
            var chauffage = ($('#txtChauffage').val() != '') ? $('#txtChauffage').val() : null;
            var eauChaude = ($('#txtEauChaude').val() != '') ? $('#txtEauChaude').val() : null;
            var eauFroide = ($('#txtEauFroide').val() != '') ? $('#txtEauFroide').val() : null;
            var superficie = ($('#txtSuperficie').val() != '') ? $('#txtSuperficie').val() : null;
            var logements = true;
            if($("#empty").is(":checked")){
            	logements = false;
            }
            if ($('#txtcaloonUserId').val() != '') {
               var caloonUserId = parseInt($('#txtcaloonUserId').val());
            }
            var resident = new Lab011Resident(id, appartementNumber, chauffage, eauChaude, eauFroide, clientName,superficie,logements,caloonUserId);
            console.log("resident = " + JSON.stringify(resident));
            var data = {
            		resident: resident
            };
            $.ajax({
                url: 'saveLab011Resident.action',
                type: 'POST',
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                beforeSend: function () {
                    $('#btnSaveResident').addClass('loading-cube');
                    $('#btnSaveResident').addClass('lighten');
                    $('#btnSaveResident').text('Saving...');
                },
                success: function (data) {
                    console.log(data);
                    var rs = JSON.parse(data);
                    if (rs.result == 'success') {
                        $('#btnSaveResident').removeClass('primary loading-cube ');
                        $('#btnSaveResident').addClass('success');
                        $('#btnSaveResident').attr('disabled', 'disabled');
                        $('#btnSaveResident').text('Success');
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
    $('.btnRemoveResident').click(
			function(e) {
				var result = confirm('Are you sure you want to delete this item?');
				if (result) {
					$.ajax({
						url : 'removeLab011Resident.action',
						data : {
							residentId: e.currentTarget.value
						},
						traditional : true,
						success : function(data) {
							var rs = JSON.parse(data);
							if (rs.result == 'success') {
								$.Notify({
											caption : 'Success',
											content : 'Remove Resident success',
											type : 'success'
										});
								window.location.href = 'residentConfig.action';
							} else {
								$.Notify({
											caption : 'Failed',
											content : 'Remove Resident failed',
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
