var appartementNumber;
var residentName;
var chauffage;
var eauChaude;
var eauFroide;
var appartementTemp;
var meteoTemp;
var endDate;

function callbackUpperData(residentId) {
	$('.preloading-top').fadeIn("fast", function() {
		var fromDate = $('#fromDateText').data('daterangepicker').startDate.format('YYYYMMDD');
		getUpperData(residentId, fromDate);
		$('.preloading-top').fadeOut("slow");
	});
	
}
function callbackChartData(residentId) {
	$('.preloading-bottom').fadeIn("fast", function() {
		var startDateBlue = $('#interessePeriode').data('daterangepicker').startDate.format('YYYYMMDD');
		var endDateBlue = $('#interessePeriode').data('daterangepicker').endDate.format('YYYYMMDD');
		var startDateGreen = $('#comparativePeriode').data('daterangepicker').startDate.format('YYYYMMDD');
		var endDateGreen = $('#comparativePeriode').data('daterangepicker').endDate.format('YYYYMMDD');
		getChartData(residentId,startDateBlue,endDateBlue,startDateGreen,endDateGreen);
	});
}
function getUpperData(residentId, fromDate) {
	var url = '/WebServiceLab/lab011/getLab011Resident?fromDate='+ fromDate +'&residentId='+residentId;
	$('#chauffageText').text('--');
	$('#eauChaudeText').text('--');
	$('#eauFroideText').text('--');
	$('#appartementTempText').text('--');
	$('#meteoTempText').text('--');
	$.ajax({
	    url: url,
	    type: 'GET',
	    async: false,
	    success: function(json) {
	    	var upperData = json;
	    	
	    	if(upperData != null){
				if(upperData.chauffage != null){
					chauffage = upperData.chauffage;
					$('#chauffageText').text(chauffage);

				}
				if(upperData.eauChaude != null){
					eauChaude = upperData.eauChaude;
					$('#eauChaudeText').text(eauChaude);

				}
				if(upperData.eauFroide != null){
					eauFroide = upperData.eauFroide;
					$('#eauFroideText').text(eauFroide);
					
				}
				if(upperData.tempAppartement != null){
					appartementTemp = upperData.tempAppartement;
					$('#appartementTempText').text(appartementTemp);

				}
				if(upperData.tempMeteo != null){
					meteoTemp = upperData.tempMeteo;
					$('#meteoTempText').text(meteoTemp);
				}
			}
	    },
	  });
}

function getChartData(residentId, startDateBlue, endDateBlue, startDateGreen, endDateGreen) {
	var url = '/WebServiceLab/lab011/getLab011ResidentBottom?fromDateBlue='+startDateBlue+'&toDateBlue='+endDateBlue+
		'&fromDateGreen='+startDateGreen+'&toDateGreen='+endDateGreen+'&residentId='+residentId;
	console.log(url);
	var chartData;
	$.ajax({
	    url: url,
	    type: 'GET',
	    async: true,
	    success: function(json) {
	    	chartData = json;
	    	
			var typeTime;
			var chauffageBlueList;
			var chauffageGreenList;
			var eauChaudeBlueList;
			var eauChaudeGreenList;
			var eauFroideBlueList;
			var eauFroideGreenList;
			var chauffageBlueData = [];
			var chauffageGreenData = [];
			var eauChaudeBlueData = [];
			var eauChaudeGreenData = [];
			var eauFroideBlueData = [];
			var eauFroideGreenData = [];
			var chauffageBlueTotal = 0;
			var chauffageGreenTotal = 0;
			var eauChaudeBlueTotal = 0;
			var eauChaudeGreenTotal = 0;
			var eauFroideBlueTotal = 0;
			var eauFroideGreenTotal = 0;
			
			var startDateBlueDate = $('#interessePeriode').data('daterangepicker').startDate;
			var endDateBlueDate = $('#interessePeriode').data('daterangepicker').endDate;
			var startDateGreenDate = $('#comparativePeriode').data('daterangepicker').startDate;
			var endDateGreenDate = $('#comparativePeriode').data('daterangepicker').endDate;
			var dateBlueDurationDay = Math.round(moment.duration(endDateBlueDate.diff(startDateBlueDate)).asDays());
			var dateGreenDurationDay = Math.round(moment.duration(endDateGreenDate.diff(startDateGreenDate)).asDays());
			
	    	if(chartData != null){
	    		if(chartData.typeTime != null){
	    			typeTime = chartData.typeTime;
	    		}
	    		if(chartData.chauffageBlueLst != null){
	    			chauffageBlueList = chartData.chauffageBlueLst;
	    			for (var i = 0; i < chauffageBlueList.length; i++) {
	    				var point = [chauffageBlueList[i][0],chauffageBlueList[i][1]];
	    				chauffageBlueData.push(point);
	    				chauffageBlueTotal += chauffageBlueList[i][1];
	    			  }
	    		}
	    		if(chartData.chauffageGreenLst != null){
	    			chauffageGreenList = chartData.chauffageGreenLst;
	    			for (var i = 0; i < chauffageGreenList.length; i++) {
	    				var point = [chauffageGreenList[i][0],chauffageGreenList[i][1]];
	    				chauffageGreenData.push(point);
	    				chauffageGreenTotal += chauffageGreenList[i][1];
	    			  }			
	    		}
	    		if(chartData.eauChaudeBlueLst != null){
	    			eauChaudeBlueList = chartData.eauChaudeBlueLst;
	    			for (var i = 0; i < eauChaudeBlueList.length; i++) {
	    				var point = [eauChaudeBlueList[i][0],eauChaudeBlueList[i][1]];
	    				eauChaudeBlueData.push(point);
	    				eauChaudeBlueTotal += eauChaudeBlueList[i][1];
	    			  }			
	    		}
	    		if(chartData.eauChaudeGreenLst != null){
	    			eauChaudeGreenList = chartData.eauChaudeGreenLst;
	    			for (var i = 0; i < eauChaudeGreenList.length; i++) {
	    				var point = [eauChaudeGreenList[i][0],eauChaudeGreenList[i][1]];
	    				eauChaudeGreenData.push(point);
	    				eauChaudeGreenTotal += eauChaudeGreenList[i][1];
	    			  }			
	    		}
	    		if(chartData.eauFroideBlueLst != null){
	    			eauFroideBlueList = chartData.eauFroideBlueLst;
	    			for (var i = 0; i < eauFroideBlueList.length; i++) {
	    				var point = [eauFroideBlueList[i][0],eauFroideBlueList[i][1]];
	    				eauFroideBlueData.push(point);
	    				eauFroideBlueTotal += eauFroideBlueList[i][1];
	    			  }			
	    		}
	    		if(chartData.eauFroideGreenLst != null){
	    			eauFroideGreenList = chartData.eauFroideGreenLst;
	    			for (var i = 0; i < eauFroideGreenList.length; i++) {
	    				var point = [eauFroideGreenList[i][0],eauFroideGreenList[i][1]];
	    				eauFroideGreenData.push(point);
	    				eauFroideGreenTotal += eauFroideGreenList[i][1];
	    			  }			
	    		}
	    		drawChart(1, typeTime, chauffageBlueData, chauffageGreenData);
	    		drawChart(2, typeTime, eauChaudeBlueData, eauChaudeGreenData);
	    		drawChart(3, typeTime, eauFroideBlueData, eauFroideGreenData);
	    		var chauffageBlueRate = chauffageBlueTotal / dateBlueDurationDay;
	    		var chauffageGreenRate = chauffageGreenTotal / dateGreenDurationDay;
	    		var eauChaudeBlueRate = eauChaudeBlueTotal / dateBlueDurationDay;
	    		var eauChaudeGreenRate = eauChaudeGreenTotal / dateGreenDurationDay;
	    		var eauFroideBlueRate = eauFroideBlueTotal / dateBlueDurationDay;
	    		var eauFroideGreenRate = eauFroideGreenTotal / dateGreenDurationDay;
	    		if(chauffageBlueTotal < 0.9 * chauffageGreenTotal){
	    			$('#chauffageFinger').attr("src","../img/ico-hand-1.png");
	    		}else if(chauffageBlueTotal > 1.1 * chauffageGreenTotal){
	    			$('#chauffageFinger').attr("src","../img/ico-hand-3.png");
	    		}else{
	    			$('#chauffageFinger').attr("src","../img/ico-hand-2.png");
	    		}
	    		if(eauChaudeBlueTotal < 0.9 * eauChaudeGreenTotal){
	    			$('#eauChaudeFinger').attr("src","../img/ico-hand-1.png");
	    		}else if(eauChaudeBlueTotal > 1.1 * eauChaudeGreenTotal){
	    			$('#eauChaudeFinger').attr("src","../img/ico-hand-3.png");
	    		}else{
	    			$('#eauChaudeFinger').attr("src","../img/ico-hand-2.png");
	    		}
	    		if(eauFroideBlueTotal < 0.9 * eauFroideGreenTotal){
	    			$('#eauFroideFinger').attr("src","../img/ico-hand-1.png");
	    		}else if(eauFroideBlueTotal > 1.1 * eauFroideGreenTotal){
	    			$('#eauFroideFinger').attr("src","../img/ico-hand-3.png");
	    		}else{
	    			$('#eauFroideFinger').attr("src","../img/ico-hand-2.png");
	    		}
	    		console.log("ChaffageBlueRate:" +chauffageBlueRate+"\n"
	    				+"ChaffageGreenRate:" +chauffageGreenRate+"\n"
	    				+"EauChaudeBlueRate:" +eauChaudeBlueRate+"\n"
	    				+"EauChaudeGreenRate:" +eauChaudeGreenRate+"\n"
	    				+"EauFroideBlueRate:" +eauFroideBlueRate+"\n"
	    				+"EauFroideGreenRate:" +eauFroideGreenRate+"\n");
	    		$('.preloading-bottom').fadeOut("slow");
	    	}
	    	
	    },
	  });
}
moment.locale('fr');
var defaultFromDate = moment().subtract(1, 'month').format('DD/MM/YYYY');
$('#fromDateText').text(defaultFromDate);
var defaultStartDateBlue = moment().startOf('month').subtract(1, 'year').format('DD/MM/YYYY');
var defaultEndDateBlue = moment().endOf('month').subtract(1, 'month').format('DD/MM/YYYY');
var defaultStartDateGreen = moment().startOf('month').subtract(2, 'year').format('DD/MM/YYYY');;
var defaultEndDateGreen = moment().endOf('month').subtract(1, 'year').subtract(1, 'month').format('DD/MM/YYYY');;

$('#interessePeriode').daterangepicker({
    "opens": "left",
    locale:{
        format: "DD/MM/YYYY",
		applyLabel: "Appliquer",
        cancelLabel: "Annuler",
    },
	startDate: defaultStartDateBlue,
	endDate: defaultEndDateBlue
}, function(start, end) {
  console.log('New Interesse Periode selected: ' + start.format('YYYYMMDD') + ' to ' + end.format('YYYYMMDD'));
  var caloonResidentId = $("#caloonResidentId").val();
  callbackChartData(caloonResidentId);
});

$('#comparativePeriode').daterangepicker({
	"opens": "left",
	locale:{
		format: "DD/MM/YYYY",
		applyLabel: "Appliquer",
        cancelLabel: "Annuler",
	},
	startDate: defaultStartDateGreen,
	endDate: defaultEndDateGreen
}, function(start, end) {
  console.log('New Comparative Periode selected: ' + start.format('YYYYMMDD') + ' to ' + end.format('YYYYMMDD'));
  var caloonResidentId = $("#caloonResidentId").val();
  callbackChartData(caloonResidentId);
});

$('#fromDateText').daterangepicker({
    singleDatePicker: true,
    showDropdowns: true,
	locale:{
		format: "DD/MM/YYYY",
	},
	startDate: defaultFromDate,
}, function(start, end) {
	console.log('New From Date selected: ' + start.format('YYYYMMDD') + ' to ' + end.format('YYYYMMDD'));
	$('#fromDateText').text(start.format('DD/MM/YYYY'));
	var caloonResidentId = $("#caloonResidentId").val();
	callbackUpperData(caloonResidentId);
});

var caloonResidentId = $("#caloonResidentId").val();

callbackUpperData(caloonResidentId);
callbackChartData(caloonResidentId);
Highcharts.setOptions({
	lang: {
		months: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin',  'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
		weekdays: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi']
	}
});

function drawChart(chartType, timeType, blueData, greenData) {
	var timeLabelBlue = {format:'{value:%d/%m/%Y}', style: {color: "#223564",},};
	var unit;
	var yLabel;
	var tooltipDateFormat = '%d/%m/%Y';
	if(timeType == 1){
		timeLabelBlue = {            	  
				formatter: function() {
					var hourStr = Highcharts.dateFormat('%H', this.value);
					if(hourStr == "00"){
						hourStr = Highcharts.dateFormat('%d/%m/%Y', this.value);
					}
					return hourStr;
			}, style: {color: "#223564",},};
		tooltipDateFormat = '%H';
	}else if(timeType == 3){
		timeLabelBlue = {format:'{value:%m/%Y}',style: {color: "#223564",},};
		tooltipDateFormat = '%m/%Y';
	}
	var timeLabelGreen = {format:'{value:%d/%m/%Y}', style: {color: "#89bf5d",},};
	if(timeType == 1){
		timeLabelGreen = {            	  
				formatter: function() {
					var hourStr = Highcharts.dateFormat('%H', this.value);
					if(hourStr == "00"){
						hourStr = Highcharts.dateFormat('%d/%m/%Y', this.value);
					}
					return hourStr;
				}, style: {color: "#89bf5d",},};
	}else if(timeType == 3){
		timeLabelGreen = {format:'{value:%m/%Y}', style: {color: "#89bf5d",},};
	}
	var chartContainer = '';
	if(chartType == 1){
		chartContainer = 'chauffageChartContainer';
		unit = 'kwh';
		yLabel = 'Chauffage (kwh)';
	}else if(chartType == 2){
		chartContainer = 'eauChaudeChartContainer';
		unit = 'm3';
		yLabel = 'Eau Chaude / Sanitaire(m3)';
	}else if(chartType == 3){
		chartContainer = 'eauFroideChartContainer';
		unit = 'm3';
		yLabel = 'Eau Froide (m3)';
	}
	$('#'+chartContainer).highcharts({
		title: {
            text: '',
        },
        subtitle: {
            text: '',
        },
        xAxis: [{
    		title: {
                text: ''
            },
        	type: 'datetime',
        	labels: timeLabelGreen,
        },{
        	title: {
                text: ''
            },
        	type: 'datetime',
    		labels: timeLabelBlue,
    		opposite: true,
        }],
        yAxis: {
            title: {
                text: '',
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }],
            minorGridLineWidth: 0,
            gridLineWidth: 0,
            alternateGridColor: null,
        },
        tooltip: {
            useHTML: true,
            formatter: function () {
                var tooltip = '';
                var i, len;
                tooltip += '<table>';
                for (i = 0, len = 2; i < len; i++) {
						if(this.points[i] != undefined){
                if(this.points[i].series.name.indexOf('M’INTERESSE') >= 0){
                    tooltip += '<tr>';
                    tooltip += '<td style="color:' + this.points[i].series.color + '">\u25CF</td>';
                    tooltip += '<td style="font-weight: normal">' + Highcharts.dateFormat(tooltipDateFormat, this.points[i].x) + ': </td>';
                    tooltip += '<td style="text-align: right"> <b>' + this.points[i].y.toFixed(2) + '</b><span style="font-weight: normal"> '+unit+'</span></td>';
                    tooltip += '</tr>';
                    }
                    }
                }
                tooltip += '</table>';
                tooltip += '<table>';
                for (i = 0, len = 2; i < len; i++) {
						if(this.points[i] != undefined){
                if(this.points[i].series.name.indexOf('COMPARATIVE') >= 0){
                    tooltip += '<tr>';
                    tooltip += '<td style="color: ' + this.points[i].series.color + '">\u25CF</td>';
                    tooltip += '<td style="font-weight: normal">' + Highcharts.dateFormat(tooltipDateFormat, this.points[i].x) + ': </td>';
                    tooltip += '<td style="text-align: right"> <b>' + this.points[i].y.toFixed(2) + '</b><span style="font-weight: normal"> '+unit+'</span></td>';
                    tooltip += '</tr>';
                    }
                    }
                }
                tooltip += '</table>';
                return tooltip;
            },
            shared: true,

        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
	    plotOptions: {
            spline: {
                lineWidth: 2,
                states: {
                    hover: {
                        lineWidth: 2,
                    }
                },
                marker: {
                    enabled: false
                },
            }
        },
        series: [{
            name: 'COMPARATIVE',
            color: '#89bf5d',
            data: greenData,
            showInLegend: false,
        },{
            name: 'M’INTERESSE',
            data: blueData,
            xAxis: 1,
            color: '#223564',
            showInLegend: false,
        }],
        credits : {
			enabled : false
				},
		exporting : {
			enabled : false
		},		
		chart: {
			type: 'spline',
			backgroundColor: 'none',
			},			
    });
};