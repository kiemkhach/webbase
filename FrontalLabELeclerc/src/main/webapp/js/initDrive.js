var widthLineAplus = 55;
var tickAmount = 10;
var jsonCssClockwise = {
	"gradeAplus" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "-6px",
			"right" : "20px"
		},
		"lineDivCss" : {
			"width" : widthLineAplus + "px"
		}
	},
	"gradeA" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "13px",
			"right" : "20px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount) + "px"
		}
	},
	"gradeB" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "32px",
			"right" : "20px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 2) + "px"
		}
	},
	"gradeC" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "51px",
			"right" : "20px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 3) + "px"
		}
	},
	"gradeD" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "70px",
			"right" : "20px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 4) + "px"
		}
	},
	"gradeE" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "89px",
			"right" : "20px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 5) + "px"
		}
	},
	"gradeF" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "108px",
			"right" : "20px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 6) + "px"
		}
	},
	"gradeG" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "127px",
			"right" : "20px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 7) + "px"
		}
	}
};
var pastYearEnergy = $('#pastYearEnergy').val();
var curYearEnergy = $('#currentYearEnergy').val();
var driveLevel = $('#driveLevel').val();
var percent = $('#percent').val();
$(document).ready(function (){
	$('#address-bar').textfill({
		maxFontPixels : 20,
		minFontPixels : 15
	});
    initProgress();
	var indexPastYear = $('#indexPastYear').val();
	var indexCurYear = $('#indexCurrentYear').val();
	if (pastYearEnergy > 0) {
		defaultInit('preYear', pastYearEnergy, indexPastYear);
	}
	$('#preYear').addClass('enertique-year-block-active');
	$('#curYear').click(function() {
		if (curYearEnergy > 0) {
			defaultInit(this.id, curYearEnergy, indexCurYear);
		}else{
			$(this).addClass('enertique-year-block-active');
			$('#grade').css('display', 'none');
		}
		$('#preYear').removeClass('enertique-year-block-active');
		$('#preYear').addClass('enertique-year-block-inactive');
	});
	$('#preYear').click(function() {
		if (pastYearEnergy > 0) {
			defaultInit(this.id, pastYearEnergy, indexPastYear);
		}else{
			$(this).addClass('enertique-year-block-active');
			$('#grade').css('display', 'none');
		}
		$('#curYear').removeClass('enertique-year-block-active');
		$('#curYear').addClass('enertique-year-block-inactive');
	});
	$('.meter > span').each(function() {
		$(this)
			.data("origWidth", $(this).width())
			.width(0)
			.animate({
				width: $(this).data("origWidth")
			}, 1200);
	});
	//export report
//	$('#btnReport').click(function(){
//		window.location.href= 'downloadReport.action';
//	});
});
function defaultInit(id, energy, index) {
	$('#' + id).addClass('enertique-year-block-active');
	switch (index) {
	case '1':
		$('#grade').children('.clockwise-value').text(energy);
		$('#grade').css('display', 'block');
		$('#grade').css(jsonCssClockwise.gradeAplus.clockwiseCss);
		$('#grade .lineDiv').css(jsonCssClockwise.gradeAplus.lineDivCss);
		// hideOtherClockWise(document.getElementById('gradeAplus'));
		break;
	case '2':
		$('#grade').children('.clockwise-value').text(energy);
		$('#grade').css('display', 'block');
		$('#grade').css(jsonCssClockwise.gradeA.clockwiseCss);
		$('#grade .lineDiv').css(jsonCssClockwise.gradeA.lineDivCss);
		// hideOtherClockWise(document.getElementById('gradeA'));
		break;
	case '3':
		$('#grade').children('.clockwise-value').text(energy);
		$('#grade').css('display', 'block');
		$('#grade').css(jsonCssClockwise.gradeB.clockwiseCss);
		$('#grade .lineDiv').css(jsonCssClockwise.gradeB.lineDivCss);
		// hideOtherClockWise(document.getElementById('gradeB'));
		break;
	case '4':
		$('#grade').children('.clockwise-value').text(energy);
		$('#grade').css('display', 'block');
		$('#grade').css(jsonCssClockwise.gradeC.clockwiseCss);
		$('#grade .lineDiv').css(jsonCssClockwise.gradeC.lineDivCss);
		// hideOtherClockWise(document.getElementById('gradeC'));
		break;
	case '5':
		$('#grade').children('.clockwise-value').text(energy);
		$('#grade').css('display', 'block');
		$('#grade').css(jsonCssClockwise.gradeD.clockwiseCss);
		$('#grade .lineDiv').css(jsonCssClockwise.gradeD.lineDivCss);
		// hideOtherClockWise(document.getElementById('gradeD'));
		break;
	case '6':
		$('#grade').children('.clockwise-value').text(energy);
		$('#grade').css('display', 'block');
		$('#grade').css(jsonCssClockwise.gradeE.clockwiseCss);
		$('#grade .lineDiv').css(jsonCssClockwise.gradeE.lineDivCss);
		// hideOtherClockWise(document.getElementById('gradeE'));
		break;
	case '7':
		$('#grade').children('.clockwise-value').text(energy);
		$('#grade').css('display', 'block');
		$('#grade').css(jsonCssClockwise.gradeF.clockwiseCss);
		$('#grade .lineDiv').css(jsonCssClockwise.gradeF.lineDivCss);
		// hideOtherClockWise(document.getElementById('gradeF'));
		break;
	case '8':
		$('#grade').children('.clockwise-value').text(energy);
		$('#grade').css('display', 'block');
		$('#grade').css(jsonCssClockwise.gradeG.clockwiseCss);
		$('#grade .lineDiv').css(jsonCssClockwise.gradeG.lineDivCss);
		// hideOtherClockWise(document.getElementById('gradeG'));
		break;
	default:
		break;
	}
}
//set level color progress bar
function initProgress(){
	switch (driveLevel) {
	case '1':
        $('#progress div:first-child span').css('width',percent+'%');
		break;
    case '2':
        $('#progress div:first-child').removeClass('green');    
        $('#progress div:first-child').addClass('yellow');
        $('#progress div:first-child span').css('width',percent+'%');
        break;
    case '3':
        $('#progress div:first-child').removeClass('green');    
        $('#progress div:first-child').addClass('red');
        $('#progress div:first-child span').css('width',percent+'%');
        break;
	default:
		$('#progress div:first-child span').css('width',0+'%');
		break;
	}
}