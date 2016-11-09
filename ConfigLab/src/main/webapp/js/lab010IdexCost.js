var idexInstallationId = $('#idexInstallationId').val();
$("#selectIdexInstallation").val(idexInstallationId);
var idexCostId = $('#selectIdexCost').val();
$(".selectIdexCostValue").val(idexCostId);
function showDialog(id){
    var dialog = $(id).data('dialog');
    dialog.open();
}
selectCategoryChange();
$( "input[type=radio]" ).on( "click", function() {
	  var check = $("input:checked").val();
	  if(check=="typeSite"){
		  $('#formByEnergySupplier').css("display","none");
		  $('#formBySite').css("display","block");
	  }else if(check=="typeEnergy"){
		  $('#formBySite').css("display","none");
		  $('#formByEnergySupplier').css("display","block");
	  }else if(check=="typeEnergyImport"){
		  $('#selectSiteArea').css("display","none");
		  $('#selectEnergyArea').css("display","block");
		  selectCategoryChange();
	  }else if(check=="typeSiteImport"){
		  $('#selectEnergyArea').css("display","none");
		  $('#selectSiteArea').css("display","block");
		  selectCategoryChange();
	  }
});
$("#selectIdexInstallation").change(function(){
	var selectValue = $("#selectIdexInstallation").val();
	window.location.href = 'redirectLab010SubCategory.action?idexInstallationId='+selectValue;
});
$('#selectIdexCost').change(function(){
	idexCostId = $('#selectIdexCost').val();
	$(".selectIdexCostValue").val(idexCostId);
});
showMessage();
function showMessage(){
	var message = $('#message').val();
	if(message != null && message != ""){
		alert(message);
	}
}
function filterSelectOption(typeCheck, valueCheck){
	if(typeCheck == "typeEnergyImport"){
		$( "#selectIdexCost option" ).each(function(){
			var value = $(this ).attr("data-energy");
			if(value == valueCheck){
				$(this).css("display","block");
			}else{
				$(this).css("display","none");
			}
		});
	}else if(typeCheck == "typeSiteImport"){
		$( "#selectIdexCost option" ).each(function(){
			var value = $(this ).attr("data-site");
			if(value == valueCheck){
				$(this).css("display","block");
			}else{
				$(this).css("display","none");
			}
		});
		
	}
	$('#selectIdexCost').val(0);

}
/*$('select #filterSelectValue').on('change', 'select', function(){
	var typeCheck = $("input:checked").val();
	var valueCheck;
	if(typeCheck=="typeEnergyImport"){
		valueCheck = $("#selectEnergyImport").val();
		filterSelectOption(typeCheck, valueCheck);
	  }else if(typeCheck=="typeSiteImport"){
		valueCheck = $("#selectSiteImport").val();
		filterSelectOption(typeCheck, valueCheck);
	}
})*/
function selectCategoryChange() {
	var typeCheck = $("input:checked").val();
	var valueCheck;
	if(typeCheck=="typeEnergyImport"){
		valueCheck = $("#selectEnergyImport").val();
		filterSelectOption(typeCheck, valueCheck);
	  }else if(typeCheck=="typeSiteImport"){
		valueCheck = $("#selectSiteImport").val();
		filterSelectOption(typeCheck, valueCheck);
	}
}


