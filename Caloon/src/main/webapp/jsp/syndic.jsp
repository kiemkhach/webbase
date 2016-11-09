<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome Syndic</title>
    <link href="../css/jquery-ui.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="../css/fontUtil.css" />
	<link rel="stylesheet" type="text/css" href="../css/ka-loading.css" /> 
	<link rel="stylesheet" type="text/css" href="../css/daterangepicker.css" />
	<link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="../css/syndic.css" />        
</head>
<body>
	<input type="hidden" id="fromDateTopInput" value="">   
	<input type="hidden" id="fromDateBottom" value="">
	<input type="hidden" id="toDateBottom" value="">
	<input type="hidden" id="caloonSyndicId" value='<s:property value="#session.caloon_syndic_id" />' />
	<input type="hidden" id="modeActive" value="1">
	<input type="hidden" id="consommationSelected" value="0">
	
	<div class="container-fluid">
		<div class="wrapper-loading-all">
			<div class="wrapper-loading-all-real">
				<div class="ka-loader-container">
					<div id="preloadAll" class="preloadPage">
						<div class="ka-loader">
							<i class="loader__tile loader__tile__1"></i><i
								class="loader__tile loader__tile__2"></i><i
								class="loader__tile loader__tile__3"></i><i
								class="loader__tile loader__tile__4"></i><i
								class="loader__tile loader__tile__5"></i><i
								class="loader__tile loader__tile__6"></i><i
								class="loader__tile loader__tile__7"></i><i
								class="loader__tile loader__tile__8"></i><i
								class="loader__tile loader__tile__9"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="mask-popup-header"></div>
		<div class="popup-container-header">
			<div class="popup-header">
				<div class="ka-loader-container">
						<div id="preloadPopup" class="preloadPage">
							<div class="ka-loader">
								<i class="loader__tile loader__tile__1"></i><i
									class="loader__tile loader__tile__2"></i><i
									class="loader__tile loader__tile__3"></i><i
									class="loader__tile loader__tile__4"></i><i
									class="loader__tile loader__tile__5"></i><i
									class="loader__tile loader__tile__6"></i><i
									class="loader__tile loader__tile__7"></i><i
									class="loader__tile loader__tile__8"></i><i
									class="loader__tile loader__tile__9"></i>
							</div>
						</div>
					</div>
				<div class="p-header">
					<div class="p-header-name">Sélectionnez une résidence</div>
					<div class="p-close"><img class="p-close-btn" src="../img/p-close.png" /></div>
				</div>
				<div class="p-container">
					<div class="p-search">
						<input type="text" id="p-search-value" />
						<img class="p-search-btn" src="../img/search.png" />
					</div>
					<div class="p-result">
<!-- 						<div class="p-result-item"> -->
<!-- 							<div class="p-item-name">Résidence Bellevue</div> -->
<%-- 							<div class="p-item-address"><span id="pitem-address">74 avenue du Général Leclerc,</span><span id="pitem-postal-code">92100</span><span id="pitem-city">Boulogne-Billancourt</span></div> --%>
<!-- 						</div> -->
<!-- 						<div class="p-result-item"> -->
<!-- 							<div class="p-item-name">Résidence Bellevue</div> -->
<%-- 							<div class="p-item-address"><span id="pitem-address">74 avenue du Général Leclerc,</span><span id="pitem-postal-code">92100</span><span id="pitem-city">Boulogne-Billancourt</span></div> --%>
<!-- 						</div> -->
					</div>
				</div>
			</div>
		</div>
		<div class="wrapper-header">
			<div class="header-content">
				<div class="caloon-icon">
					<img class="caloon-img" src="../img/caloon-icon.png" />
				</div>
			</div>
			<div class="header-bottom">
				<div class="address-wrapper">
					<div class="address"><s:property value="#session.address_name"/></div>
					<div class="address-value"><s:property value="#session.address_value"/></div>
					<div class="postal-code-value"><s:property value="#session.postal_code"/></div>
					<div class="city-value"><s:property value="#session.city"/></div>
				</div>
				<div class="welcom-client">
					<div class="welcome">BIENVENUE</div>
					<div class="welcome-client-name"><span style="text-transform: capitalize;"> <s:property value="#session.first_name_key"/></span> <s:property value="#session.last_name_key"/></div>
				</div>
				<div class="logout-btn">
					<span class="logout"> <a href="logout.action">DÉCONNEXION</a>
					</span>
				</div>
			</div>
		</div>
		<div class="wrapper-top-content">
			<div class="top-main-left">
				<div class="situation-current">
					<div class="situation">LA SITUATION</div>
					<div class="current">ACTUELLE</div>
				</div>
				<div class="consumption-since">
					<div class="consumption-lbl">La consommation</div>
					<div class="consumption-since-lbl">depuis le <span id="consumption-from"><input type="text" id="fromDateTop"></span></div>
				</div>
				<div class="last-statement">
					<div class="last-statement-lbl">Date de dernier relevé</div>
					<div class="last-statement-date"><s:property value="#session.last_day_data"/></div>
				</div>
			</div>
			<div class="top-main-right">
				<div class="ka-loader-container">
					<div id="preloadTop" class="preloadPage">
						<div class="ka-loader">
							<i class="loader__tile loader__tile__1"></i><i
								class="loader__tile loader__tile__2"></i><i
								class="loader__tile loader__tile__3"></i><i
								class="loader__tile loader__tile__4"></i><i
								class="loader__tile loader__tile__5"></i><i
								class="loader__tile loader__tile__6"></i><i
								class="loader__tile loader__tile__7"></i><i
								class="loader__tile loader__tile__8"></i><i
								class="loader__tile loader__tile__9"></i>
						</div>
					</div>
				</div>
			
				<div class="top-row top-icon">
					<div class="top1"></div>
					<div class="top2 chauffage">
						<div class="img-top"><img class="chauffage-img" src="../img/chauffage.png"/></div>
						
					</div>
					<div class="top3 sanitaire">
						<div class="img-top"><img class="sanitaire-img" src="../img/red-water.png"/></div>
						
					</div>
					<div class="top4 cold-water">
						<div class="img-top"><img class="cold-water-img" src="../img/blue-water.png"/></div>
						
					</div>
				</div>
				<div class="top-row top-lbl">
					<div class="top1"></div>
					<div class="top2 chauffage">					
						<div class="top-lbl-header">CHAUFFAGE</div>
					</div>
					<div class="top3 sanitaire">					
						<div class="top-lbl-header">EAU CHAUDE <br />/ SANITAIRE</div>
					</div>
					<div class="top4 cold-water">						
						<div class="top-lbl-header">EAU FROIDE</div>
					</div>
				</div>
				<div class="top-row row-item">
					<div class="top1 top-lbl-left">CONSOMMATION GLOBALE</div>
					<div class="top2 consommation">
						<div class="consommation-number" ><span id="global-chauffage">--</span></div>
						<div class="consommation-unit" ><span id="global-chauffage-unit">kwh</span></div>
					</div>
					<div class="top3 consommation">
						<div class="consommation-number" ><span id="global-sanitaire">--</span></div>
						<div class="consommation-unit" ><span id="global-sanitaire-unit">m3</span></div>
					</div>
					<div class="top4 consommation">
						<div class="consommation-number"><span id="global-cold-water">--</span></div>
						<div class="consommation-unit" ><span id="global-cold-water-unit">m3</span></div>
					</div>
				</div>
				<div class="top-row row-item">
					<div class="top1 top-lbl-left">CONSOMMATION MOYENNE<br />PAR LOGEMENT ET PAR MOIS</div>
					<div class="top2 consommation">
						<div class="consommation-number"><span id="per-chauffage">--</span></div>
						<div class="consommation-unit"><span id="per-chauffage-unit">kwh</span></div>
					</div>
					<div class="top3 consommation">
						<div class="consommation-number"><span id="per-sanitaire">--</span></div>
						<div class="consommation-unit"><span id="per-sanitaire-unit">m3</span></div>
					</div>
					<div class="top4 consommation">
						<div class="consommation-number"><span id="per-cold-water">--</span></div>
						<div class="consommation-unit"><span  id="per-cold-water-unit">m3</span></div>
					</div>
				</div>
				<div class="top-row row-item">
					<div class="top1 top-lbl-left">DÉPERDITION D’ÉNERGIE</div>
					<div class="top2 consommation">
						<div class="consommation-number" ><span id="wastage-chauffage">--</span></div>
						<div class="consommation-unit" ><span id="wastage-chauffage-unit">kwh</span></div>
					</div>	
					<div class="top3 consommation">
						
					</div>
					<div class="top4 consommation">
						
					</div>					
				</div>
			</div>
		</div><!--end wrapper-top-content-->
		<div class="wrapper-bottom-content">
			<div class="wrapper-top-analysis">
				<div class="analysis-header">J’ANALYSE LA CONSOMMATION AVEC CALOON</div>
				<div class="analysis-description">Grâce aux outils Caloon, je peux suivre mais aussi analyser les consommations globales de chauffage, d’eau chaude/sanitaire et d’eau froide de l’immeuble,
 mais aussi les consommations individuelles des résidents.</div>
				<div class="analysis-chosen">
					<div class="analysis-chosen-lbl">JE CHOISIS MON TYPE D’ENERGIE</div>
					<div class="wrapper-chosen-filter-icon">
						<div class="analysis-chosen-item" id="chauffage-analysis-mode"><img class="chosen1-img" src="../img/chauffage.png" /></div>
						<div class="analysis-chosen-item disabled" id="sanitaire-analysis-mode"><img class="chosen2-img" src="../img/red-water.png" /></div>
						<div class="analysis-chosen-item disabled" id="coldWater-analysis-mode"><img class="chosen3-img" src="../img/blue-water.png" /></div>
					</div>
				</div>
				<div class="filter-chosen">
					<div class="calendar-lbl"><img class="calendar-img" src="../img/datepicker_new.png" /></div>
					<div class="chosen-wrapper chosen-wrapper1">
						<div class="filter">
							<div class="filter-lbl">PÉRIODE ANALYSÉE</div>
							<div class="select-analysis" id="chosen-analysis1">
								<label class="label-filter">
									<input type="text" id="periodAnalysis" />
								</label>
							</div>
						</div>
						<div class="filter">
							<div class="filter-lbl">CONSOMMATION</div>
							<div class="consommation-filter-wrapper">
								<div class="btn-greater-smaller smaller" id="consommation-gs"></div>
								<div class="input-filter consommation-input chauffage" id="consommation-after"><input type="number" id="input-consommation"></div>
							</div>
						</div>
						<div class="filter">
							<div class="filter-lbl">SUPERFICIE</div>
							<div class="consommation-filter-wrapper">
								<div class="btn-greater-smaller smaller" id="superficie-gs"></div>
								<div class="input-filter consommation-input" id="superficie-after">
									<input type="number" id="input-superficie">
									<div class="superficie-unit">m²</div>
								</div>
							</div>
						</div>
						<div class="filter">
							<div class="filter-lbl">LOGEMENTS</div>
							<div class="select-analysis" id="chosen-logement">
								<label class="label-filter">
									<select id="logement-analysis">
										<option value="">Tous</option>
										<option value="true">Occupés</option>
										<option value="false">Vacants</option>
									</select>
								</label>
							</div>
						</div>
					</div>
				</div>
				<div class="ok-btn"><span class="okBtn">OK</span></div>
			</div>
			<div class="wrapper-bottom-analysis">
				<div class="filter-detail">
					<div class="type-analysis">PÉRIODE ANALYSÉE</div>
					<div class="period-analysis"><span id="period-analysis-fromDate">--</span> AU <span id="period-analysis-toDate">--</span></div>
					<div class="consommation-analysis">CONSOMMATION <span id="consommation-value-analysis"> -- </span></div>
				</div>
				<div class="wrapper-bottom-loading">
					<div class="ka-loader-container">
						<div id="preloadBottom" class="preloadPage">
							<div class="ka-loader">
								<i class="loader__tile loader__tile__1"></i><i
									class="loader__tile loader__tile__2"></i><i
									class="loader__tile loader__tile__3"></i><i
									class="loader__tile loader__tile__4"></i><i
									class="loader__tile loader__tile__5"></i><i
									class="loader__tile loader__tile__6"></i><i
									class="loader__tile loader__tile__7"></i><i
									class="loader__tile loader__tile__8"></i><i
									class="loader__tile loader__tile__9"></i>
							</div>
						</div>
					</div>
					<div class="wrapper-rs-detail">
						<div class="filter-result">
							<div class="sort-result-bottom">
								<div class="wrapper-sort-bottom">
									<div class="lbl-sort-bottom">TRIER PAR</div>
									<div class="select-analysis" id="chosen-sort-bottom">
										<label class="label-filter">
											<select id="sort-bottom">
												<option value="0">CONSOMMATION</option>
												<option value="1">NOM</option>
											</select>
										</label>
									</div>
									<div class="sort-icon sort-desc"></div>
								</div>
							</div>
							<div class="building"><img class="building-img" src="../img/building.png" /></div>
							<div class="item-result-of">
								<div class="icon-result"><img id="result-img-mode" class="result-img" src="../img/chauffage.png" /></div>
								<div class="type-result" id="type-result-mode">CHAUFFAGE</div>
								<div class="left-result">
									<div class="left-rs-item">
										<div class="left-rs-lbl consommation-global">CONSOMMATION GLOBALE</div>
										<div class="left-rs-value">
											<div  class="left-rs-number"><span id="global-rs-number">--</span></div>
											<div  class="left-rs-unit"><span id="global-rs-unit">kwh</span></div>
										</div>
									</div>
									<div class="left-rs-item">
										<div class="left-rs-lbl">CONSOMMATION MOYENNE PAR LOGEMENT</div>
										<div class="left-rs-value">
											<div  class="left-rs-number"><span id="consommation-rs-number">--</span></div>
											<div class="left-rs-unit"><span id="consommation-rs-unit">kwh</span></div>
										</div>
									</div>
									<div class="left-rs-item">
										<div class="left-rs-lbl">DÉPERDITION D’ÉNERGIE </div>
										<div class="left-rs-value">
											<div class="left-rs-number"><span id="wastage-rs-number">--</span></div>
											<div class="left-rs-unit"><span id="wastage-rs-unit">kwh</span></div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="right-result">
							<div class="right-rs-clients">
	<!-- 							<div class="result-item client-result"> -->
	<!-- 								<div class="rs-name">Client N ̊</div> -->
	<%-- 								<div class="rs-consommation"><div class="rs-consommation-flex"><span class="rs-consommation-number">9 016</span><span class="rs-consommation-unit">kwh</span></div></div> --%>
	<%-- 								<div class="rs-percent"><span class="rs-percent-number">0,26</span><span class="rs-percent-unit">%</span></div> --%>
	<!-- 							</div> -->
	<!-- 							<div class="result-item client-result"> -->
	<!-- 								<div class="rs-name">Client N ̊</div> -->
	<%-- 								<div class="rs-consommation"><div class="rs-consommation-flex"><span class="rs-consommation-number">9 016</span><span class="rs-consommation-unit">kwh</span></div></div> --%>
	<%-- 								<div class="rs-percent"><span class="rs-percent-number">0,26</span><span class="rs-percent-unit">%</span></div> --%>
	<!-- 							</div> -->
	<!-- 							<div class="result-item client-result"> -->
	<!-- 								<div class="rs-name">Client N ̊</div> -->
	<%-- 								<div class="rs-consommation"><div class="rs-consommation-flex"><span class="rs-consommation-number">9 016</span><span class="rs-consommation-unit">kwh</span></div></div> --%>
	<%-- 								<div class="rs-percent"><span class="rs-percent-number">6865</span><span class="rs-percent-unit">%</span></div> --%>
	<!-- 							</div> -->
	<!-- 							<div class="result-item client-result"> -->
	<!-- 								<div class="rs-name">Client N ̊</div> -->
	<%-- 								<div class="rs-consommation"><div class="rs-consommation-flex"><span class="rs-consommation-number">9 016</span><span class="rs-consommation-unit">kwh</span></div></div> --%>
	<%-- 								<div class="rs-percent"><span class="rs-percent-number">26,36</span><span class="rs-percent-unit">%</span></div> --%>
	<!-- 							</div> -->
							</div>
							<div class="right-rs-common">
								<div class="result-item common-result">
									<div class="rs-name common-name">Parties <br />communes</div>
									<div class="rs-consommation"><div class="rs-consommation-flex"><span class="rs-consommation-number"  id="parties-number">--</span><span class="rs-consommation-unit">--</span></div></div>
									<div class="rs-percent common-percent"><span class="rs-percent-number" id="parties-percent">--</span><span class="rs-percent-unit">%</span></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="download-file">
					<div class="img-download"><img class="download-img" src="../img/pdf-download.png" /></div>
					<div class="download-lbl">RAPPORT GLOBAL</div>
					<div class="download-arrow"><img class="download-img-arrow" src="../img/arraw-download.png" /></div>
				</div>
			</div>
		</div><!--end wrapper-bottom-content-->
		<div class="wrapper-footer"></div><!--end wrapper-footer-->
	</div><!-- end container-fluid-->
   	
	<script src="../js/jquery-1.11.3.min.js"></script>
	<script src="../js/jquery-ui.min.js"></script>
	<script src="../js/datepicker-fr.js"></script>
	<script src="../js/jquery.textfill.min.js"></script>
	<script src="../js/jquery.nicescroll.min.js"></script>
	<script src="../js/numeral.min.js"></script>
	<script src="../js/moment.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/daterangepicker.js"></script>
	<script src="../js/initSyndic.js"></script>
	<script>
		$(function(){
			
			
		});
	</script>
  </body>
</html>

