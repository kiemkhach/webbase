	var idexInstallationId = $('#idexInstallationId').val();
	$("#selectIdexInstallation").val(idexInstallationId);
	var idexCounterId = $('#selectIdexCounter').val();
	$(".selectCounterValue").val(idexCounterId);
	$("#selectIdexInstallation").change(function(){
		var selectValue = $("#selectIdexInstallation").val();
		window.location.href = 'redirectLab010Releves.action?idexInstallationId='+selectValue;
	});
	$('#selectIdexCounter').change(function(){
		idexCounterId = $('#selectIdexCounter').val();
		$(".selectCounterValue").val(idexCounterId);
	});
	showMessage();
	function showMessage(){
		var message = $('#messageReleves').val();
		if(message != null && message != ""){
			alert(message);
		}
	}