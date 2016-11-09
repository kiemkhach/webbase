$(document).ready(function() {

	var mTable = $('#moduleTable').DataTable({
		aoColumnDefs: [{
		            	   bSortable: false,
		            	   aTargets: [ -1 ]}
		               ],
		initComplete : function() {
			/* Select box for Site Name filter */
			var siteNameColumn = this.api().column(1);
			var siteNameDropdown = $('<select><option value="">All</option></select>')
			.appendTo( $('#filterSiteName').empty() )
			.on( 'change', function () {
				var val = $.fn.dataTable.util.escapeRegex(
						$(this).val()
                    	);
				
				siteNameColumn.search( val ? '^'+val+'$' : '', true, false ).draw();
			});
			siteNameColumn.data().unique().sort().each( function ( d, j ) {
			siteNameDropdown.append( '<option value="'+d+'">'+d+'</option>' )
			} );

				/* Select box for Lot Name filter */
				var lotNameColumn = this.api().column(3);
				var lotNameDropdown = $('<select><option value="">All</option></select>')
                .appendTo( $('#filterLotName').empty() )
                .on( 'change', function () {
                    var val = $.fn.dataTable.util.escapeRegex(
                        $(this).val()
                    );

                    lotNameColumn.search( val ? '^'+val+'$' : '', true, false ).draw();
                });
				lotNameColumn.data().unique().sort().each( function ( d, j ) {
					lotNameDropdown.append( '<option value="'+d+'">'+d+'</option>' )
                } );
				/* Select box for Energy Name filter */
				var energyNameColumn = this.api().column(4);
				var energyNameDropdown = $('<select><option value="">All</option></select>')
                .appendTo( $('#filterEnergyName').empty() )
                .on( 'change', function () {
                    var val = $.fn.dataTable.util.escapeRegex(
                        $(this).val()
                    );

                    energyNameColumn.search( val ? '^'+val+'$' : '', true, false ).draw();
                });
				energyNameColumn.data().unique().sort().each( function ( d, j ) {
					energyNameDropdown.append( '<option value="'+d+'">'+d+'</option>' )
                } );

			}

		});

});
function showDialog(id){
    var dialog = $(id).data('dialog');
    dialog.open();
}
$(".btnEditLab009Module").click(function() {
	var selectLab009ModuleId = $(this).val();
	var selectModuleId = $("#editModuleId"+selectLab009ModuleId).val();
	var selectLotId = $("#editLotId"+selectLab009ModuleId).val();
	var selectEnergyId = $("#editEnergyId"+selectLab009ModuleId).val();
	$('#selectLab009ModuleId').val(selectLab009ModuleId);
	$('#selectModuleNumber').val(selectModuleId);
	$("#selectModuleNumber").select2({val:selectModuleId});
	$('#selectLotName').val(selectLotId);
	$('#selectLotName').select2({val:selectLotId});
	$('#selectEnergyName').val(selectEnergyId);
	$('#selectEnergyName').select2({val:selectEnergyId});

});


$('.btnRemoveLab009Module').click(
		function(e) {
			var result = confirm('Are you sure you want to delete this item?');
			if (result) {
				var tempClientId = $("#tempClientId").val();
				$.ajax({
					url : 'removeLab009Module.action',
					data : {
						id : $(this).val()
					},
					traditional : true,
					success : function(data) {
						var rs = JSON.parse(data);
						if (rs.result == 'success') {
							$.Notify({
										caption : 'Success',
										content : 'Remove Lab009Module success',
										type : 'success'
									});
							window.location.href = 'redirectLab009Module.action?clientId='+tempClientId;
						} else {
							$.Notify({
										caption : 'Failed',
										content : 'Remove Lab009Module failed',
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