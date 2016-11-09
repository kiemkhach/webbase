function Lab011Range(id, fromNumber, toNumber, orderBy) {
    this.id = id;
    this.fromNumber = fromNumber;
    this.toNumber = toNumber;
    this.orderBy = orderBy;
}
function clearDetailForm() {
    $('#txtRangeId').val('');
    $('#txtFromNumber').val('');
    $('#txtToNumber').val('');
    $('#txtOrderBy').val('');
}
$(document).ready(function () {
	$("#creatRange").dialog({
		autoOpen : false,
		minWidth : 400,
		minHeight : 500
	}).css("font-family" , "Exo");
	$("#btncreateLab011Range").click(function(e) {
		$("#creatRange").dialog('open');
	});
	 var listRangeDialog = $('#detailRangeDialog').data('dialog');
	 $('#detailRangeDialog .dialog-close-button').click(function () {
	        clearDetailForm();
	    });
	 $('.btnEditRange').click(function (e) {
	        $.ajax({
	            method: "POST",
	            url: "getLab011RangeById.action",
	            data: {
	            	rangeId: e.currentTarget.value
	            },
	            traditional: true,
	            success: function (data) {
	                var jsonData = JSON.parse(data);
	                if (jsonData != null) {
	                    $('#txtRangeId').val(jsonData.rangeId);
	                    if (jsonData.fromNumber != null) {
	                        $('#txtFromNumber').val(jsonData.fromNumber);
	                    }
	                    if (jsonData.toNumber != null) {
	                        $('#txtToNumber').val(jsonData.toNumber);
	                    }
	                    if (jsonData.orderBy != null) {
	                        $('#txtOrderBy').val(jsonData.orderBy);
	                    }
	                    listRangeDialog.open();
	                }
	            },
	            error: function (XMLHttpRequest, textStatus, errorThrown) {
	                console.log(XMLHttpRequest.responseText);
	            }
	        });
	    });
	 
	 $('#btnSaveRange').click(
				function() {
					var id = null;
					if ($('#txtRangeId').val() != '') {
						id = parseInt($('#txtRangeId').val());
					}
					var fromNumber = ($('#txtFromNumber').val() != '') ? $('#txtFromNumber').val() : null;
					var toNumber = ($('#txtToNumber').val() != '') ? $('#txtToNumber').val() : null;
					var orderBy = ($('#txtOrderBy').val()!= '') ? $('#txtOrderBy').val(): null;
					if(!Number.isInteger(Number(fromNumber))){
						alert("From Number must be integer");
						$("#txtFromNumber").focus();
						return false;
					}
					if(!Number.isInteger(Number(toNumber))){
						alert("To Number must be integer");
						$("#txtToNumber").focus();
						return false;
					}
					if(!Number.isInteger(Number(orderBy))){
						alert("Order By must be integer");
						$("#txtOrderBy").focus();
						return false;
					}
					var lab011Range = new Lab011Range(id, fromNumber, toNumber, orderBy);
					console.log("range = "+ JSON.stringify(lab011Range));
//					if(energyEmissions==null || isNaN(energyEmissions) == true){
//						alert("Energy Emissions must be a number");
//						$('#txtEnergyEmissions').focus();
//						return false;
//					}
					var data = {
							caloonConsommationRange : lab011Range
					};
					$.ajax({
						url : 'saveLab011Range.action',
						type : 'POST',
						data : JSON.stringify(data),
						dataType : 'json',
						contentType : 'application/json;charset=utf-8',
						async : false,
						beforeSend : function() {
							$('#btnSaveRange').text('Saving...');
						},
						success : function(data) {
							console.log(data);
							var rs = JSON.parse(data);
							if (rs.result == 'success') {
								$('#btnSaveRange').text('Success');
								location.reload(true);
							} else {
								$('#btnSaveRange').text('Save');
								alert(rs.result);
							}
						},
						error : function(XMLHttpRequest,textStatus,errorThrown) {
							console.log(XMLHttpRequest.responseText);
							$('#btnSaveRange').text('Save');
						}
					});

				});
	 $('.btnRemoveRange').click(
				function(e) {
					var result = confirm('Are you sure you want to delete this item?');
					if (result) {
						$.ajax({
							url : 'removeLab011Range.action',
							data : {
								rangeId: e.currentTarget.value
							},
							traditional : true,
							success : function(data) {
								var rs = JSON.parse(data);
								if (rs.result == 'success') {
									$.Notify({
												caption : 'Success',
												content : 'Remove Consommation Range success',
												type : 'success'
											});
									window.location.href = 'rangeConfig.action';
								} else {
									$.Notify({
												caption : 'Failed',
												content : 'Remove Consommation Range failed',
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
