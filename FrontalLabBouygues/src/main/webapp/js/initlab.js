$(document).ready(function(){
	$('#logout').click(function(){
		window.location.href='logOut.action';
	});
//	$('#export').click(function(){
//		window.location.href='exportReport.action';
//	});
	$('.value').textfill({
		maxFontPixels : 17
	});
});