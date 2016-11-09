$(document).ready(
function() {
	var mess = $('#message').val();
	if (mess == 'success') {
		$.Notify({
			caption : 'Success',
			content : 'Action success',
			type : 'success'
		});
	} else {
		$.Notify({
			caption : 'Error',
			content : mess,
			type : 'alert'
		});
	}
	
	$('#btnSaveConfigLab009').click(function(){
		if($('#txtClientName').val() == null || $('#txtClientName').val() =='') {
			alert("Client Name must be not null");
			$("#txtClientName").focus();
			return false;
		}
		

	});
	// ......................Lab009
	// EnergyTypes....................
	var detailEnergyTypesDialog = $('#detailEnergyTypesDialog').data('dialog');
	$('.btnRemoveLab009EnergyTypes').click(
		function(e) {
			var result = confirm('Are you sure you want to delete this item?');
			if (result) {
				$.ajax({
					url : 'removeLab009EnergyType.action',
					data : {
						id : $(this).val()
					},
					traditional : true,
					success : function(data) {
						var rs = JSON.parse(data);
						if (rs.result == 'success') {
							$.Notify({
										caption : 'Success',
										content : 'Remove Lab009EnergyType success',
										type : 'success'
									});
							window.location.href = 'redirectLab009.action';
						} else {
							$.Notify({
										caption : 'Failed',
										content : 'Remove Lab009EnergyType failed',
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
	$('.btnEditLab009EnergyTypes').click(
		function(e) {
			$.ajax({
				method : "POST",
				url : "getEnergyTypesById.action",
				data : {
					id : $(this).val()
				},
				traditional : true,
				success : function(data) {
					var jsonData = JSON.parse(data);
					if (jsonData != null) {
						$('#txtEnergyId').val(jsonData.energyId);
						// alert($('#txtEnergyId').val(jsonData.energyId));
						if (jsonData.energyName != null && jsonData.energyName != '') {
							$('#txtEnergyName').val(jsonData.energyName);
						}
						if (jsonData.energyCode != null&& jsonData.energyCode != '') {
							$('#txtEnergyCode').val(jsonData.energyCode);
						}
						if (jsonData.energyEmissions != null&& jsonData.energyEmissions != '') {
							$('#txtEnergyEmissions').val(jsonData.energyEmissions);
						}
						if (jsonData.colorCode != null&& jsonData.colorCode != '') {
							$('#txtColorCodeEnergy').val(jsonData.colorCode);
						}
						detailEnergyTypesDialog.open();
					}
				},
				error : function(XMLHttpRequest,textStatus,errorThrown) {
					console.log(XMLHttpRequest.responseText);
				}
			});
		});
	$("#creatEnergyTypes").dialog({
		autoOpen : false,
		minWidth : 400,
		minHeight : 500
	}).css("font-family" , "Exo");

	// $("#creatEnergyTypes").dialog('opent');
	$("#btncreateLab009EnergyTypes").click(function(e) {
		$("#creatEnergyTypes").dialog('open');
	});
	$('#btnSaveEnergy').click(
		function() {
			var energyId = null;
			if ($('#txtEnergyId').val() != '') {
				energyId = parseInt($('#txtEnergyId').val());
			}
			var energyName = ($('#txtEnergyName').val() != '') ? $('#txtEnergyName').val() : null;
			var energyCode = ($('#txtEnergyCode').val() != '') ? $('#txtEnergyCode').val() : null;
			var energyEmissions = ($('#txtEnergyEmissions').val()!= '') ? $('#txtEnergyEmissions').val(): null;
			var colorCode = ($('#txtColorCodeEnergy').val()!= '') ? $('#txtColorCodeEnergy').val(): null;
			var lab009Energy = new Lab009EnergyType(energyId, energyName,energyCode, energyEmissions,colorCode);
			console.log("energy = "+ JSON.stringify(lab009Energy));
//			if ($('#txtEnergyEmissions').val() == null || !$('#txtEnergyEmissions').val().isNaN) {
//				alert($('#txtEnergyEmissions').val());
//				alert("Energy Emissions must be a number");
//				$('#txtEnergyEmissions').focus();
//				return false;
//			}
			if(energyEmissions==null || isNaN(energyEmissions) == true){
				alert("Energy Emissions must be a number");
				$('#txtEnergyEmissions').focus();
				return false;
			}
			var data = {
				numConfigLab009Energy : lab009Energy
			};
			$.ajax({
				url : 'saveLab009Energy.action',
				type : 'POST',
				data : JSON.stringify(data),
				dataType : 'json',
				contentType : 'application/json;charset=utf-8',
				async : false,
				beforeSend : function() {
					$('#btnSaveEnergy').text('Saving...');
				},
				success : function(data) {
					console.log(data);
					var rs = JSON.parse(data);
					if (rs.result == 'success') {
						$('#btnSaveEnergy').text('Success');
						location.reload(true);
					} else {
						$('#btnSaveEnergy').text('Save');
						alert(rs.result);
					}
				},
				error : function(XMLHttpRequest,textStatus,errorThrown) {
					console.log(XMLHttpRequest.responseText);
					$('#btnSaveEnergy').text('Save');
				}
			});

		});

	// ....................................Lot
	// Consommation...............................
	var detailLotConsommationDialog = $('#detailLotConsommationDialog').data('dialog');
	$('.btnRemoveLab009LotConsommation').click(
		function(e) {
			var result = confirm('Are you sure you want to delete this item?');
			if (result) {
				$.ajax({
					url : 'removeLab009LotConsommation.action',
					data : {
						id : $(this).val()
					},
					traditional : true,
					success : function(data) {
						var rs = JSON.parse(data);
						if (rs.result == 'success') {
							$.Notify({
										caption : 'Success',
										content : 'Remove Lab009LotConsomation success',
										type : 'success'
									});
							window.location.href = 'redirectLab009.action';
						} else {
							$.Notify({
										caption : 'Failed',
										content : 'Remove Lab009LotConsommation failed',
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
	$('.btnEditLab009LotConsommation').click(
		function(e) {
			// listTemperatureProductionChauffageDialog.close();
			$.ajax({
				method : "POST",
				url : "getLotById.action",
				data : {
					id : $(this).val()
				},
				traditional : true,
				success : function(data) {
					var jsonData = JSON.parse(data);
					if (jsonData != null) {
						$('#txtLotConsommationId').val(jsonData.lotId);
						if (jsonData.lotName != null && jsonData.lotName != '') {
							$('#txtLotName').val(jsonData.lotName);
						}
						if (jsonData.lotCode != null && jsonData.lotCode != '') {
							$('#txtLotCode').val(jsonData.lotCode);
						}
						if (jsonData.colorCode != null && jsonData.colorCode != '') {
							$('#txtColorCodeLot').val(jsonData.colorCode);
						}
						detailLotConsommationDialog.open();
					}
				},
				error : function(XMLHttpRequest,textStatus,errorThrown) {
					console.log(XMLHttpRequest.responseText);
				}
			});
		});

	$("#creatLotConsommation").dialog({
		autoOpen : false,
		minWidth : 400,
		minHeight : 500
	});

	// $("#creatEnergyTypes").dialog('opent');
	$("#btncreateLab009LotConsommation").click(function(e) {
		$("#creatLotConsommation").dialog('open');
	});

	$('#btnSaveLotConsommation').click(
		function() {
			var lotId = null;
			if ($('#txtLotConsommationId').val() != '') {
				lotId = parseInt($('#txtLotConsommationId').val());
			}
			var lotName = ($('#txtLotName').val() != '') ? $('#txtLotName').val(): null;
			var lotCode = ($('#txtLotCode').val() != '') ? $('#txtLotCode').val(): null;
			var colorCode = ($('#txtColorCodeLot').val() != '') ? $('#txtColorCodeLot').val(): null;
			var lab009LotConsommation = new Lab009LotConsommation(lotId, lotName, lotCode,colorCode);
			console.log("lotconsommation = "+ JSON.stringify(lab009LotConsommation));
			var data = {
				numConfigLab009LotConsommation : lab009LotConsommation
			};
			$.ajax({
				url : 'saveLab009LotConsommation.action',
				type : 'POST',
				data : JSON.stringify(data),
				dataType : 'json',
				contentType : 'application/json;charset=utf-8',
				async : false,
				beforeSend : function() {
					$('#btnSaveLotConsommation').text('Saving...');
				},
				success : function(data) {
					console.log(data);
					var rs = JSON.parse(data);
					if (rs.result == 'success') {
						$('#btnSaveLotConsommation').text('Success');
						location.reload(true);
					} else {
						$('#btnSaveLotConsommation').text('Save');
						alert(rs.result);
					}
				},
				error : function(XMLHttpRequest,textStatus,errorThrown) {
					console.log(XMLHttpRequest.responseText);
					$('#btnSaveLotConsommation').text('Save');
				}
			});

		});

	// .................................Provider...........................................
	$('.btnRemoveLab009Provider').click(
		function(e) {
			var result = confirm('Are you sure you want to delete this item?');
			if (result) {
				$.ajax({
					url : 'removeLab009Provider.action',
					data : {
						id : $(this).val()
					},
					traditional : true,
					success : function(data) {
						var rs = JSON.parse(data);
						if (rs.result == 'success') {
							$.Notify({
								caption : 'Success',
								content : 'Remove Lab009Provider success',
								type : 'success'
							});
							window.location.href = 'redirectLab009.action';
						} else {
							$.Notify({
								caption : 'Failed',
								content : 'Remove Lab009Provider failed',
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
	$('.btnEditLab009Provider').click(
		function(e) {
			$.ajax({
				method : "POST",
				url : "getProviderById.action",
				data : {
					id : $(this).val()
				},
				traditional : true,
				success : function(data) {
					var jsonData = JSON.parse(data);
					if (jsonData != null) {
						$('#txtProviderId').val(jsonData.providerId);
						if (jsonData.providerName != null && jsonData.providerName != '') {
							$('#txtProName').val(jsonData.providerName);
						}
						if (jsonData.providerCode != null && jsonData.providerCode != '') {
							$('#txtProCode').val(jsonData.providerCode);
						}
						if (jsonData.colorCode != null && jsonData.colorCode != '') {
							$('#txtColorCodePro').val(jsonData.colorCode);
						}
						$('#detailProviderDialog').data('dialog').open();
					}
				},
				error : function(XMLHttpRequest,textStatus,errorThrown) {
					console.log(XMLHttpRequest.responseText);
				}
			});
		});

	$("#creatProvider").dialog({
		autoOpen : false,
		minWidth : 400,
		minHeight : 500
	});

	// $("#creatEnergyTypes").dialog('opent');
	$("#btncreateLab009Provider").click(function(e) {
		$("#creatProvider").dialog('open');
	});
	$('#btnSaveProvider').click(
		function() {
			var providerId = null;
			if ($('#txtProviderId').val() != '') {
				providerId = parseInt($('#txtProviderId').val());
			}
			var providerName = ($('#txtProName').val() != '') ? $('#txtProName').val(): null;
			var providerCode = ($('#txtProCode').val() != '') ? $('#txtProCode').val(): null;
			var colorCode = ($('#txtColorCodePro').val() != '') ? $('#txtColorCodePro').val(): null;
			var lab009Provider = new Lab009Provider(providerId, providerName, providerCode, colorCode);
			console.log("lotconsommation = "+ JSON.stringify(lab009Provider));
			var data = {
					numConfigLab009Provider : lab009Provider
			};
			$.ajax({
				url : 'saveLab009Provider.action',
				type : 'POST',
				data : JSON.stringify(data),
				dataType : 'json',
				contentType : 'application/json;charset=utf-8',
				async : false,
				beforeSend : function() {
					$('#btnSaveProvider').text('Saving...');
				},
				success : function(data) {
					console.log(data);
					var rs = JSON.parse(data);
					if (rs.result == 'success') {
						$('#btnSaveProvider').text('Success');
						location.reload(true);
					} else {
						$('#btnSaveProvider').text('Save');
						alert(rs.result);
					}
				},
				error : function(XMLHttpRequest,textStatus,errorThrown) {
					console.log(XMLHttpRequest.responseText);
					$('#btnSaveProvider').text('Save');
				}
			});
	
		});

});
function Lab009EnergyType(energyId, energyName, energyCode, energyEmissions,colorCode) {
	this.energyId = energyId;
	this.energyName = energyName;
	this.energyCode = energyCode;
	this.energyEmissions = energyEmissions;
	this.colorCode = colorCode;
}
function Lab009LotConsommation(lotId, lotName, lotCode, colorCode) {
	this.lotId = lotId;
	this.lotName = lotName;
	this.lotCode = lotCode;
	this.colorCode = colorCode;
}
function Lab009Provider(providerId, providerName, providerCode, colorCode) {
	this.providerId = providerId;
	this.providerName = providerName;
	this.providerCode = providerCode;
	this.colorCode = colorCode;
}