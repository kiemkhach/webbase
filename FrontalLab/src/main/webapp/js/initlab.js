var widthLineAplus = 68;
var tickAmount = 10;
var jsonCssClockwise = {
	"gradeAplus" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "-2px",
			"right" : "0px"
		},
		"lineDivCss" : {
			"width" : widthLineAplus + "px"
		}
	},
	"gradeA" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "13px",
			"right" : "0px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount) + "px"
		}
	},
	"gradeB" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "27px",
			"right" : "0px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 2) + "px"
		}
	},
	"gradeC" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "42px",
			"right" : "0px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 3) + "px"
		}
	},
	"gradeD" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "57px",
			"right" : "0px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 4) + "px"
		}
	},
	"gradeE" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "72px",
			"right" : "0px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 5) + "px"
		}
	},
	"gradeF" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "87px",
			"right" : "0px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 6) + "px"
		}
	},
	"gradeG" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "101px",
			"right" : "0px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 7) + "px"
		}
	},
	"gradeH" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "116px",
			"right" : "0px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 8) + "px"
		}
	},
	"gradeI" : {
		"clockwiseCss" : {
			"position" : "absolute",
			"top" : "131px",
			"right" : "0px"
		},
		"lineDivCss" : {
			"width" : (widthLineAplus - tickAmount * 9) + "px"
		}
	}
};
var pastYearEnergy = $('#pastYearEnergy').val();
var curYearEnergy = $('#currentYearEnergy').val();
$(document).ready(function() {
	$('.magasin-content div').textfill({
		maxFontPixels : 20
	});
	$('#address-bar').textfill({
		maxFontPixels : 20,
		minFontPixels : 15
	});
	$('.magasin-content div').click(function() {
		var id = this.id + 'Detail';
		switch (this.id) {
		case 'froid':
			$('.remodal').css("background-color", "#B52426");
			break;
		case 'eclairage':
			$('.remodal').css("background-color", "#4BB056");
			break;
		case 'boulangerie':
			$('.remodal').css("background-color", "#D9A628");
			break;
		case 'bureu':
			$('.remodal').css("background-color", "#46A6AB");
			break;
		case 'cvc':
			$('.remodal').css("background-color", "#7851A1");
			break;
		default:
			break;
		}
		window.location = '#' + id;
	});

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
	case '9':
		$('#grade').children('.clockwise-value').text(energy);
		$('#grade').css('display', 'block');
		$('#grade').css(jsonCssClockwise.gradeH.clockwiseCss);
		$('#grade .lineDiv').css(jsonCssClockwise.gradeH.lineDivCss);
		break;
	case '10':
		$('#grade').children('.clockwise-value').text(energy);
		$('#grade').css('display', 'block');
		$('#grade').css(jsonCssClockwise.gradeI.clockwiseCss);
		$('#grade .lineDiv').css(jsonCssClockwise.gradeI.lineDivCss);
		break;
	default:
		break;
	}
}
// function hideOtherClockWise(element){
// $('.clockwise').not(element).css('display','none');
// }
