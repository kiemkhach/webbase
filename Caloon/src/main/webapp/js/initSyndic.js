// load a language
numeral.language('fr', {
    delimiters: {
        thousands: ' ',
        decimal: ','
    },
    abbreviations: {
        thousand: 'k',
        million: 'm',
        billion: 'b',
        trillion: 't'
    },
    ordinal : function (number) {
        return number === 1 ? 'er' : 'ème';
    },
    currency: {
        symbol: '€'
    }
});
moment.locale('fr');
numeral.language('fr');
$(document).ready(function(){
//	$('#preloadAll').fadeIn("fast", function(){
	var defaultFromDate = moment().subtract(1, 'month').format('DD/MM/YYYY');
	$('#fromDateTop').text(defaultFromDate);
	$('#fromDateTopInput').val(moment().subtract(1, 'month').format('YYYYMMDD'));
	$('#fromDateTop').daterangepicker({
		"opens": "right",
		singleDatePicker: true,
	    showDropdowns: true,
		startDate: defaultFromDate,
		locale:{
			format: "DD/MM/YYYY",
		}
	}, function(start, end) {
		$('#fromDateTop').text(start.format('DD/MM/YYYY'));
		$('#fromDateTopInput').val(start.format('YYYYMMDD'));
		fillDataTop();
	});
	$('.consommation-number').textfill({
		maxFontPixels: 30,
	});
	
	$('.left-rs-number').textfill({
		maxFontPixels: 30,
	});
	$('html').niceScroll();
	$('.right-rs-clients').niceScroll();
	fillDataTop();
	$('#fromDateBottom').val(moment().subtract(3, 'month').format('YYYYMMDD'));
	$('#toDateBottom').val(moment().format('YYYYMMDD'));
	
	filterAnalysis();
	
	var startDate = moment().subtract(3, 'month').format('DD/MM/YYYY');
	var endDate = moment().format('DD/MM/YYYY');
	$('#period-analysis-fromDate').text(moment().subtract(3, 'month').format('D MMMM'));
	$('#period-analysis-toDate').text(moment().format('D MMMM'));
	$('#periodAnalysis').daterangepicker({
		"opens": "left",
		startDate: startDate,
		endDate: endDate,
		locale:{
			format: "DD/MM/YYYY",
			applyLabel: "Appliquer",
	        cancelLabel: "Annuler",
		}
	});
	$('#periodAnalysis').click(function(){
		$('.wrapper-bottom-loading').css('display', 'none');
		$(".right-rs-clients").getNiceScroll().hide();
	});
	$('#logement-analysis').change(function(){
		$('.wrapper-bottom-loading').css('display', 'none');
		$(".right-rs-clients").getNiceScroll().hide();
	});
	$('#input-consommation').click(function(){
		$('.wrapper-bottom-loading').css('display', 'none');
		$(".right-rs-clients").getNiceScroll().hide();
	});
	$('#input-superficie').click(function(){
		$('.wrapper-bottom-loading').css('display', 'none');
		$(".right-rs-clients").getNiceScroll().hide();
	});
	var consommationRange;
	$.ajax({
		 url : '/WebServiceLab/lab011/getAllConsommationRange',
		type : 'GET',
		async : false,
		success : function(json) {
			consommationRange = json;
		},
	});
	$('#consommationAnalysis').append($('<option>', { 
        value: 0,
        text : 'CONSOMMATION' 
    }));
	$.each(consommationRange, function (i, item) {
	    $('#consommationAnalysis').append($('<option>', { 
	        value: item.id,
	        text : item.rangeSearch 
	    }));
	});
	var consommationSelected = $( "#consommationAnalysis option:selected" ).text();
//	$('.consommation-analysis').html(consommationSelected);
	downloadReport($('.img-download'));
	downloadReport($('.download-lbl'));
	downloadReport($('.download-arrow'));
	
	// ON change
	$('#sort-bottom').change(function(){
		fillDataClients();
	});
	
	$('.sort-icon').click(function(){
		if($(this).hasClass('sort-asc')){
			$(this).removeClass('sort-asc');
			if(!$(this).hasClass('sort-desc')){
				$(this).addClass('sort-desc');	
			}
		}else{
			if($(this).hasClass('sort-desc')){
				$(this).removeClass('sort-desc');
			}
			
			$(this).addClass('sort-asc');
		}
		fillDataClients();
	});
	
	changeResident();
	$('#preloadAll').fadeOut("slow");
//	});
});

function fillDataClients(){
	var clientsSize = lab011ClientsLst != null ? lab011ClientsLst.length : 0;
	orderClient(lab011ClientsLst);
	var unit = $('#modeActive').val() == 1 ? 'kwh' : 'm3';
	var html = '';
	for(var i = 0; i < clientsSize; ++i){
		var tmp = lab011ClientsLst[i];
		var residentId = tmp.residentId;
		var clientName = (tmp.clientName != null && tmp.clientName != '') ? tmp.clientName : '--';
		html += '<div class="result-item client-result"><div id="'+residentId+'" class="rs-name"><a href="residenceSite.action?residentId='+residentId+'" target="_blank">'+clientName
				+'</a></div><div class="rs-consommation"><div class="rs-consommation-flex"><span class="rs-consommation-number">'
				+ numeral(tmp.energy).format('0,0').replace(/,/g,' ') +'</span><span class="rs-consommation-unit">'+unit
				+'</span></div></div><div class="rs-percent"><span class="rs-percent-number">'
				+ numeral(tmp.rate).format('0.00') +'</span><span class="rs-percent-unit">%</span></div></div>';
	}
	$('.right-rs-clients').html(html);
}

function downloadReport(element){
	element.click(function(){
		window.location.href = 'exportReport.action?syndicId=' + $('#caloonSyndicId').val();
	});
}
function setHeightClients(){	
	var itemResultOfHeight = 68 + 24 + $('.left-result').height() + 45;
	$('.right-result').css('height', itemResultOfHeight);
	var heightItem = (itemResultOfHeight - 40  - 6) / 5 ;
	$('.rs-name').css('height', heightItem);
	$('.rs-consommation').css('height', heightItem);
	$('.rs-percent').css('height', heightItem);
	$('.right-rs-clients').css('height', heightItem*4+40);
}
function setPaddingItemResult(){
	var itemResultOfHeight = 68 + 24 + $('.left-result').height() + 45;
	$('.item-result-of').css("padding-top", 572 - itemResultOfHeight + 5);
}
$(window).resize(function(){
	setPaddingItemResult();
});
function fillDataTop(){
	$('#preloadTop').fadeIn("fast", function(){
		var dataTop;
		$.ajax({
			 url : '/WebServiceLab/lab011/getLab011SyndicUpper?fromDate='+$('#fromDateTopInput').val()+'&syndicId=' + $('#caloonSyndicId').val(),
			type : 'GET',
			async : false,
			success : function(json) {
				dataTop = json;
			},
		});
		if(dataTop == null){
			return;
		}
		var globaleChauffage = dataTop.globaleChauffage != null ? numeral(dataTop.globaleChauffage).format('0,0').replace(/,/g,' ') : '--';
		var globaleEauChaude = dataTop.globaleEauChaude != null ? numeral(dataTop.globaleEauChaude).format('0,0').replace(/,/g,' ') : '--';
		var globaleEauFroide = dataTop.globaleEauFroide != null ? numeral(dataTop.globaleEauFroide).format('0,0').replace(/,/g,' ') : '--';
		var logementEtMoisChauffage = dataTop.logementEtMoisChauffage != null ? numeral(dataTop.logementEtMoisChauffage).format('0,0').replace(/,/g,' ') : '--';
		var logementEtMoisEauChaude = dataTop.logementEtMoisEauChaude != null ? numeral(dataTop.logementEtMoisEauChaude).format('0,0').replace(/,/g,' ') : '--';
		var logementEtMoisEauFroide = dataTop.logementEtMoisEauFroide != null ? numeral(dataTop.logementEtMoisEauFroide).format('0,0').replace(/,/g,' ') : '--';
		var wastageEnergy = dataTop.wastageEnergy != null ? numeral(dataTop.wastageEnergy).format('0,0').replace(/,/g,' ') : '--';
		$('#global-chauffage').html(globaleChauffage);
		$('#global-sanitaire').html(globaleEauChaude);
		$('#global-cold-water').html(globaleEauFroide);
		$('#per-chauffage').html(logementEtMoisChauffage);
		$('#per-sanitaire').html(logementEtMoisEauChaude);
		$('#per-cold-water').html(logementEtMoisEauFroide);
		$('#wastage-chauffage').html(wastageEnergy);
		$('#preloadTop').fadeOut("slow");
	});
}

var lab011ClientsLst;

function fillDataBottom(){
	$('.wrapper-bottom-loading').css('display', 'block');
	$('#preloadBottom').fadeIn("fast", function(){
		clearBottom();
		
		var dataBottom;
		var fromDateBottom = $('#fromDateBottom').val();
		var toDateBottom = $('#toDateBottom').val();
		var isConsommationGreater = false;
		var isSuperficieGreater = false;
		if(!$('#consommation-gs').hasClass('smaller')){
			isConsommationGreater = true;
		}
		if(!$('#superficie-gs').hasClass('smaller')){
			isSuperficieGreater = true;
		}
		var consommationRange=$('#input-consommation').val();
		var superficie=$('#input-superficie').val();
		var logement = $( "#logement-analysis option:selected" ).val();
		$('#logement-analysis').change(function(){
			logement = $( "#logement-analysis option:selected" ).val();
		});
		$('#input-consommation').change(function(){
			consommationRange = $('#input-consommation').val();
		});
		$('#input-superficie').change(function(){
			superficie = $('#input-superficie').val();
		});
		$.ajax({
			 url : '/WebServiceLab/lab011/getLab011SyndicBottom?syndicId='+ $('#caloonSyndicId').val()+'&type='+$('#modeActive').val()
			 		+'&fromDate='+fromDateBottom+'&toDate=' + toDateBottom+'&isConsommationGreater='+isConsommationGreater+ '&consommationRange='
			 		+consommationRange+ '&isSuperficieGreater=' + isSuperficieGreater + '&superficie=' +superficie+ '&logements=' +logement,
			type : 'GET',
			async : false,
			success : function(json) {
				dataBottom = json;
			},
		});
		if(dataBottom == null){
			return;
		}
		var globaleConsommation = dataBottom.globaleConsommation != null ? numeral(dataBottom.globaleConsommation).format('0,0').replace(/,/g,' ') : '--';
		var globaleMoyenneConsommation = dataBottom.globaleMoyenneConsommation != null ? numeral(dataBottom.globaleMoyenneConsommation).format('0,0').replace(/,/g,' ') : '--';
		var wastageEnergy = dataBottom.wastageEnergy != null ? numeral(dataBottom.wastageEnergy).format('0,0').replace(/,/g,' ') : '--';
		lab011ClientsLst = dataBottom.lab011ClientsLst;
		var partiesCommunes = dataBottom.partiesCommunes;
		var totalEnergy;
		var totalRate;
		if(partiesCommunes != null){
			totalEnergy = partiesCommunes.energy != null ? numeral(partiesCommunes.energy).format('0,0').replace(/,/g,' ') : '--';
			totalRate = partiesCommunes.rate != null ? numeral(partiesCommunes.rate).format('0.00') : '--';
		}
//		var unit = $('#modeActive').val() == 1? 'kwh' : 'unit';
		$('#parties-number').html(totalEnergy);
		$('#parties-percent').html(totalRate);

		fillDataClients();
		$('#global-rs-number').html(globaleConsommation);
		$('#consommation-rs-number').html(globaleMoyenneConsommation);
		$('#wastage-rs-number').html(wastageEnergy);
//		setHeightClients();
		$('.right-rs-clients').css("height", "510px");
		setPaddingItemResult();
		$('#preloadBottom').fadeOut("slow");
	});
}

function orderClient(lab011ClientsLst){
//	var lab011ClientsLst = [];
	if(lab011ClientsLst != null){
		if($('#sort-bottom').val() == 0){
			// Consommation
			if($('.sort-icon').hasClass('sort-desc')){
				lab011ClientsLst.sort(sortByConsommationDESC);
			}else{
				lab011ClientsLst.sort(sortByConsommationASC);
			}
			
		}else{
			if($('.sort-icon').hasClass('sort-desc')){
				lab011ClientsLst.sort(sortByNameDESC);
			}else{
				lab011ClientsLst.sort(sortByNameASC);
			}
		}
	}
//	return lab011ClientsLst;
}

function sortByNameASC(a,b){
	var aName = a.clientName.toLowerCase();
	var bName = b.clientName.toLowerCase(); 
	return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
}

function sortByNameDESC(a,b){
	var aName = a.clientName.toLowerCase();
	var bName = b.clientName.toLowerCase(); 
	return ((aName > bName) ? -1 : ((aName < bName) ? 1 : 0));
}

function sortByConsommationASC(a,b){
	var aEnergy = a.energy;
	var bEnergy = b.energy; 
	return ((aEnergy < bEnergy) ? -1 : ((aEnergy > bEnergy) ? 1 : 0));
}

function sortByConsommationDESC(a,b){
	var aEnergy = a.energy;
	var bEnergy = b.energy; 
	return ((aEnergy > bEnergy) ? -1 : ((aEnergy < bEnergy) ? 1 : 0));
}

function clearBottom(){
	$('#global-rs-number').html('--');
	$('#consommation-rs-number').html('--');
	$('#wastage-rs-number').html('--');
	$('#parties-number').html('--');
	$('#parties-percent').html('--');
	
}
function filterAnalysis(){
	$('.wrapper-bottom-loading').css('display', 'none');
	$('.wrapper-loading-all-real').fadeOut('fast');
	$('#preloadBottom').fadeOut('fast');
	$(".right-rs-clients").getNiceScroll().hide();
	var fromDateAnalysisLbl;
	var toDateAnalysisLbl;
	$('#periodAnalysis').on('apply.daterangepicker', function(ev, picker) {
		fromDate: picker.startDate.format('YYYYMMDD');
		toDate: picker.endDate.format('YYYYMMDD');
		fromDateAnalysisLbl = picker.startDate.format('D MMMM');
		toDateAnalysisLbl = picker.endDate.format('D MMMM');
		$('#fromDateBottom').val(picker.startDate.format('YYYYMMDD'));
		$('#toDateBottom').val(picker.endDate.format('YYYYMMDD'));
	});
//	var consommationSelected = $( "#consommationAnalysis option:selected" ).text();
//	$('.consommation-analysis').html(consommationSelected);
//	$( "#consommationAnalysis" ).change(function() {
//		$('#consommationSelected').val($( "#consommationAnalysis option:selected" ).val());
//	});
	
	smallOrGreater();
	activeMode();
	
	$('.okBtn').click(function(){
		$('#period-analysis-fromDate').text(fromDateAnalysisLbl);
		$('#period-analysis-toDate').text(toDateAnalysisLbl);
//		$('.consommation-analysis').html($( "#consommationAnalysis option:selected" ).text());
		var unit = $('#modeActive').val() == '1' ? 'kwh' : 'm3';
		var html= "CONSOMMATION ";
		if(!$('#consommation-gs').hasClass('smaller')){
			html += '> ';
		}else {
			html += '< ';
		}
		if($( "#input-consommation").val() == '') {
			html += '--';
		}else {
			html += $( "#input-consommation").val();
		}
		
		
		html += ' ' + unit;
		$('.rs-consommation-unit').html(unit);
		$('.consommation-analysis').html(html);
		fillDataBottom();
	});
	
	
	
}
function activeMode(){
	$('.analysis-chosen-item').click(function(){
		$('.wrapper-bottom-loading').css('display', 'none');
		var thisId = $(this).attr('id');
		$('.analysis-chosen-item').addClass('disabled');
		$('#' + thisId).removeClass('disabled');
		if(thisId == 'chauffage-analysis-mode'){
			$('#result-img-mode').attr('src', '../img/chauffage.png');
			$('#result-img-mode').css('width', 28);
			$('#type-result-mode').html('CHAUFFAGE');
			$('#modeActive').val('1');
			$('#consommation-after').addClass('chauffage');
			$('#consommation-after').removeClass('sanitaire');
			$('#global-rs-unit').html('kwh');
			$('#consommation-rs-unit').html('kwh');
			$('#wastage-rs-unit').html('kwh');
		}else if(thisId == 'sanitaire-analysis-mode'){
			$('#result-img-mode').attr('src', '../img/red-water.png');
			$('#result-img-mode').css('width', 20);
			$('#type-result-mode').html('EAU CHAUDE / SANITAIRE');
			$('#modeActive').val('2');
			$('#consommation-after').addClass('sanitaire');
			$('#consommation-after').removeClass('chauffage');
			$('#global-rs-unit').html('m3');
			$('#consommation-rs-unit').html('m3');
			$('#wastage-rs-unit').html('m3');
		}else if(thisId == 'coldWater-analysis-mode'){
			$('#result-img-mode').attr('src', '../img/blue-water.png');
			$('#result-img-mode').css('width', 20);
			$('#type-result-mode').html('EAU FROIDE');
			$('#modeActive').val('3');
			$('#consommation-after').addClass('sanitaire');
			$('#consommation-after').removeClass('chauffage');
			$('#global-rs-unit').html('m3');
			$('#consommation-rs-unit').html('m3');
			$('#wastage-rs-unit').html('m3');
		}
//		fillDataBottom();
	});
}
function smallOrGreater(){
	$('.btn-greater-smaller').click(function(){
		$('.wrapper-bottom-loading').css('display', 'none');
		var thisId = $(this).attr('id');
		var $this = $(this);
		if(!$this.hasClass('smaller')){
			$this.addClass('smaller');
		}else {
			$this.removeClass('smaller');
		}
	});
}
var dataPopup;
function searchHome(){
	$('#preloadPopup').fadeIn("fast", function(){
		var keySearch = $('#p-search-value').val();
		
		$.ajax({
			 url : '/WebServiceLab/lab011/searchCaloonSyndic?key=' + keySearch,
			type : 'GET',
			async : false,
			success : function(json) {
				dataPopup = json;
			},
		});
		var html='';
		for(var i = 0; i < dataPopup.length; ++i){
			var item = dataPopup[i];
			var caloonSyndicId = item.caloonSyndicId;
			var name = item.name;
			var ville = item.ville;
			var address = item.address;
			var codePostal = item.codePostal;
			var reportPath = item.reportPath;
			html += '<div class="p-result-item"><div class="p-item-name"><a href="syndicSite.action?syndicId='+caloonSyndicId+'">'+name+'</a></div><div class="p-item-address"><span id="pitem-address">'
					+ address +',</span><span id="pitem-postal-code">'+codePostal+' </span><span id="pitem-city">'+ville+'</span></div></div>';
		}
		$('.p-result').html(html);
		$('.p-result').niceScroll();
		$('#preloadPopup').fadeOut("slow");
	});
}
function changeResident(){
	$('.address-wrapper').click(function(){
		$('#preloadPopup').fadeIn("fast", function(){
			$('.mask-popup-header').css('display', 'block');
			$('.popup-container-header').css('display', 'block');
			searchHome();
			$('#p-search-value').keypress(function (e) {
				if (e.which == 13) {
					searchHome();
					return false;   
				}
			});
			$('.p-search-btn').click(function(){
				searchHome();
			});
			closePopupHeader($('.mask-popup-header'));
			closePopupHeader($('.p-close'));
			$('#preloadPopup').fadeOut("slow");
		});
	});
}
function closePopupHeader(element){
	element.click(function(){
		$('.mask-popup-header').css('display', 'none');
		$('.popup-container-header').css('display', 'none');
	});
}

function gotoResident(){
	$('.rs-name').click(function(){
		var thisId = $(this).attr('id');
		
	});
}