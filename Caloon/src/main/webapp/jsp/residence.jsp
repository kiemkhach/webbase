<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Residence</title>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/daterangepicker.css" rel="stylesheet">
    <link href="../css/ka-loading.css" rel="stylesheet">
    <link href="../css/caloon-resident.css" rel="stylesheet">
    
</head>
<body>
<!--     <div class="col-md-12 header"> -->
<!--         <div class="logo-area"> -->
<!--             <div class="col-md-3"> -->
<!--                 <img src="../img/ico-caloon.png" alt="Caloon Logo Image" /> -->
<!--             </div> -->
<!--         </div> -->
<!--         <div class="header-bottom"> -->
<!--             <div class="col-md-3"> -->
<!--                 <div class="detail-area-left"> -->
<!--                     <p>Votre numéro de logement</p> -->
<!--                     <p class="text-detail-green" id="appartementNumberText">--</p> -->
<!--                 </div> -->
<!--             </div> -->
<!--             <div class="col-md-6 detail-area-right"> -->
<!--                 <p class="header-name">BIENVENUE</p> -->
<%--                 <p class="header-name" id="residentNameText"><s:property value="caloonUserBean.firstName"/> <s:property value="caloonUserBean.lastName"/></p> --%>
<!--             </div> -->
<!--             <div class="col-md-3"> -->
<!--                 <div class="disconnect-area"> -->
<!--                     <input type="button" value="DÉCONNEXION"> -->
<!--                 </div> -->
<!--             </div> -->
<!--         </div> -->
<!--     </div> -->
	<input type="hidden" id="caloonResidentId" value='<s:property value="#session.caloon_resident_id" />'/>
    <div class="body-content">
        <div class="col-md-12 body-top">
            <div class="ka-loader-container">
					<div class="preloadPage preloading-top">
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
            <div class="col-md-3 left-side">
                <div class="text-area">
                    <p class="text-name">VOTRE SITUATION ACTUELLE</p>
                </div>
                <div class="text-area">
                    <p class="text-detail">Votre consommation depuis le <span class="text-date" id="fromDateText">--</span></p>
                </div>
                <div class="text-area">
                    <p class="text-detail">Date de dernier relevé <span class="text-date" id="endDateText"><s:property value="#session.last_day_data"/></span></p>
                </div>
            </div>
            <div class="col-md-6 middle-side">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-sm-2 row-logo"> <img src="../img/ico-chauffage.png">
                        </div>
                        <div class="col-sm-4 row-name">
                            <p>CHAUFFAGE</p>
                        </div>
                        <div class="col-sm-6 row-value">
                            <div class="text-value">
                                <p id="chauffageText">--</p>
                            </div>
                            <div class="text-unit">
                                <p>kwh</p>
                            </div>
                        </div>
                        <div class="end-line"></div>
                    </div>
                </div>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-sm-2 row-logo"> <img src="../img/ico-eau-chaude.png">
                        </div>
                        <div class="col-sm-4 row-name">
                            <p>EAU CHAUDE / SANITAIRE</p>
                        </div>
                        <div class="col-sm-6 row-value">
                            <div class="text-value">
                                <p id="eauChaudeText">--</p>
                            </div>
                            <div class="text-unit">
                                <p>m3</p>
                            </div>
                        </div>
                        <div class="end-line"></div>
                    </div>
                </div>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-sm-2 row-logo"> <img src="../img/ico-eau-froide.png">
                        </div>
                        <div class="col-sm-4 row-name">
                            <p>EAU FROIDE</p>
                        </div>
                        <div class="col-sm-6 row-value">
                            <div class="text-value">
                                <p id="eauFroideText">--</p>
                            </div>
                            <div class="text-unit">
                                <p>m3</p>
                            </div>
                        </div>
                        <div class="end-line"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-3 right-side">
                <p class="text-name">TEMPÉRATURE</p>
                <p class="text-detail">APPARTEMENT</p>
                <p class="text-temperature" id="appartementTempText">--</p><p class="text-temperature" style="font-weight: normal">˚C</p>
                <p class="text-detail">MÉTEO</p>
                <p class="text-temperature" id="meteoTempText">--</p><p class="text-temperature" style="font-weight: normal">˚C</p>
            </div>
        </div>
        <div class="col-md-12 body-bottom">
            <h3>J’ANALYSE MA CONSOMMATION AVEC CALOON</h3>
            <div class="body-bottom-detail">
                <p>Grâce aux outils Caloon, je peux suivre mais aussi analyser mes consommations de chauffage, d’eau chaude/sanitaire et d’eau froide.</p>
                <p>J’obtiens ainsi l’évolution de ma consomation par rapport à l’année précédente et je peux voir si je n’ai pas fait d’écart !</p>
            </div>
            <div class="ka-loader-container">
					<div class="preloadPage preloading-bottom">
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
            <div class="select-area">
                <div class="time-select">
                    <div class="time-from">
                        <img src="../img/ico-calendar.png">
                        <p>PERIODE QUI M’INTERESSE</p>
                        <input type="text" id="interessePeriode" name="daterange" class="date-range-picker">
                        <!--<select></select>-->
                    </div>
                    <div class="time-to">
                        <img src="../img/ico-calendar-green.png">
                        <p>PERIODE COMPARATIVE</p>
                        <input type="text" id="comparativePeriode" class="date-range-picker">
                        <!--<select></select>-->
                    </div>
                </div>
                <!--            <button>OK</button>-->
            </div>
            <div class="row-content">
                <div class="col-md-3 left-area">
                    <div class="left-center">
                        <img src="../img/ico-chauffage.png">
                        <h4>CHAUFFAGE</h4>
                    </div>
                </div>
                <div class="col-md-7 middle-area" id="chauffageChartContainer">
                </div>
                <div class="col-md-2 right-area">
                    <img src="" id="chauffageFinger">
                </div>
            </div>
            <div class="row-content">
                <div class="col-md-3 left-area">
                    <div class="left-center">
                        <img src="../img/ico-eau-chaude.png">
                        <h4>EAU CHAUDE / SANITAIRE</h4>
                    </div>
                </div>
                <div class="col-md-7 middle-area" id="eauChaudeChartContainer">
                </div>
                <div class="col-md-2 right-area">
                    <img src="" id="eauChaudeFinger">
                </div>
            </div>
            <div class="row-content">
                <div class="col-md-3 left-area">
                    <div class="left-center">
                        <img src="../img/ico-eau-froide.png">
                        <h4>EAU FROIDE</h4>
                    </div>
                </div>
                <div class="col-md-7 middle-area" id="eauFroideChartContainer">
                </div>
                <div class="col-md-2 right-area">
                    <img src="" id="eauFroideFinger">
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-12 footer">

    </div>
    <script src="../js/jquery-1.12.4.min.js"></script>
    <script src="../js/highcharts.js"></script>
    <script src="../js/exporting.js"></script>
    <script src="../js/moment-with-locales.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/daterangepicker.js"></script>
    <script src="../js/caloon-resident.js"></script>
</body>
</html>