$(document).ready(function() {
	var mess = $('#message').val();
	if (mess == 'success') {
		$.Notify({
			caption : 'Success',
			content : 'Action success',
			type : 'success'
		});
	} else{
	if(mess.substring(0, 3) == 'Sum'){
		$.Notify({
			caption : 'Success',
			content : mess,
			type : 'success'
		});
	}
	else {
		$.Notify({
			caption : 'Error',
			content : mess,
			type : 'alert'
		});
	}
	}
	$('#delete-subscription').click(function() {
		return confirm('Are you sure you want to delete ?');
	});	
	$("input:file").not('#uploadClientImage').not('#importTrefCsvFile').not('#importCsvFile').not('#importPerformaceCsvFile').each(function(){
		$(this).on('change',function(){	
			var button = $(this).parent().siblings('button');
			var fileExtension = ['pdf'];
			if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
				alert("Only formats are allowed : "+ fileExtension);				
				button.prop('disabled', true);
				button.removeClass('info');	
			} else {
				button.prop('disabled', false);
				button.addClass('info');				
			}
		});
	});
//	$("#fromdate").mask("99/99/9999",{ placeholder: "dd/MM/yyyy" });
	$("#datepicker").datepicker({
	        format: "dd/mm/yyyy", // set output format
	        effect: "none", // none, slide, fade
	        position: "bottom", // top or bottom,
	        locale: 'en', // 'ru' or 'en', default is $.Metro.currentLocale
	});
	$("#btnConfigChart").click(function(){
		var site = $("#homeConfigSiteId").val();
		if($("#homeConfigSiteId").val() != null && $("#homeConfigSiteId").val() != ""){
			window.location.href = 'redirectLab008.action?siteId='+$("#homeConfigSiteId").val();
		}else{
			window.location.href = 'redirectLab008.action';
		}
	});
	$("#btnConfigHome").click(function(){
		window.location.href = 'redirectLab008V2.action?siteId='+$("#configSiteId").val();
	});
	var siteImport = $("#siteImport").val();
});