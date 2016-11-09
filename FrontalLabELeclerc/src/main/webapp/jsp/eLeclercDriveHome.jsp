<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title><s:property value='driveBean.siteName' /></title>
<link href="../css/bootstrap.min.css" rel="stylesheet" />
<link href="../css/eleclercdrive.css" rel="stylesheet" />
<link href="../css/progressbar.css" rel="stylesheet" />
<link href="../css/remodal.css" rel="stylesheet" />
<link href="../css/remodal-default-theme.css" rel="stylesheet" />
<script src="../js/jquery-1.11.3.min.js"></script>

<script type="text/javascript">
	/*reload if caching*/
	$(window).bind("pageshow", function(event) {
		var cacheTest = $('#cacheTest').val();
		if (cacheTest == '') {
			$('#cacheTest').val('cached');
		} else {
			window.location.href = window.location.href;
		}
	});

	
</script>
</head>
<body>
	<input type="hidden" id="cacheTest" value="" />
	<a id="home" href="eLeclerc.action?siteId=<s:property value='driveBean.siteId' />"></a>
<!-- 	<div onclick="javascript:location.href='logOut.action';" id="logout"> -->
<!-- 		<img src="../img/icon-deconnexion.png"> -->
<%-- 		<s:text name="label.logout" /> --%>
<!-- 	</div> -->
	<div id="wrapper" class="container">
		<div id="header-logo" class="page-header">
			<div id="logo-img"></div>
		</div>
		<div id="content" class="row">
			<div id="content-body">
				<div class="body-block">
					<div id="address-bar">
						<span> <img src="../img/icon-location.png" /> <s:property
								value='driveBean.siteStr' /></span>
					</div>
					<div id="consomation">
						<div id="consomation-progress">
							<div id="consomation-progress-title"><s:text name="label.uppercase.consomation" /></div>
							<div id="progress">
								<div class="meter green nostripes">
									<span style="width: 33.3%"></span>
									<div id="drive-value">
										<s:if test="%{driveBean.drive == null }">
										---
									</s:if>
									<s:else>
										<s:property value="%{getText('format.number',{driveBean.drive})}" />
									</s:else>
									<s:text name="unit.kWh" />
									</div>
								</div>
							</div>
						</div>
						<div id="temperature">
							<div id="temperature-title"><s:text name="label.uppercase.temperature" /></div>
							<div class="temperature-parking">
								<div class="temperature-parking-value">
									<s:if test="%{driveBean.temperateOut == null }">
										---
									</s:if>
									<s:else>
										<s:property value='driveBean.temperateOut' />
									</s:else>
									<s:text name="degree.celsius" />
								</div>
							</div>
						</div>
					</div>
					<div id="energetic-report">
						<div class="block-inside">
							<div id="energetique">
								<div class="energetique-title">
									<s:text name="label.uppercase.energy" />
								</div>
								<div class="energetique-years">
									<div class="enertique-year-block" id="preYear">
										<s:if test="%{driveBean.pastYear == null }">
										---
									</s:if>
										<s:else>
											<s:property value='driveBean.pastYear' />
										</s:else>
									</div>
									<div class="enertique-year-block" id="curYear">
										<s:if test="%{driveBean.currentYear == null }">
										---
									</s:if>
										<s:else>
											<s:property value='driveBean.currentYear' />
										</s:else>
									</div>
									<div id="changePer1">
										<s:if test="%{driveBean.discount == null }">
										---
										</s:if>
										<s:else>
											<s:property value='driveBean.discount' />%
										</s:else>
									</div>
								</div>
								<div class="enertique-clockwises">
									<div id="rangeAplus" class="range-value"><
										${driveBean.range_APlus_Max}</div>
									<div id="rangeA" class="range-value">${driveBean.range_A_Min}-${driveBean.range_A_Max}</div>
									<div id="rangeB" class="range-value">${driveBean.range_B_Min}-${driveBean.range_B_Max}</div>
									<div id="rangeC" class="range-value">${driveBean.range_C_Min}-${driveBean.range_C_Max}</div>
									<div id="rangeD" class="range-value">${driveBean.range_D_Min}-${driveBean.range_D_Max}</div>
									<div id="rangeE" class="range-value">${driveBean.range_E_Min}-${driveBean.range_E_Max}</div>
									<div id="rangeF" class="range-value">${driveBean.range_F_Min}-${driveBean.range_F_Max}</div>
									<div id="rangeG" class="range-value">>${driveBean.range_G_Min}</div>
									<div id="grade" class="clockwise">
										<div class="lineDiv"></div>
										<div class="clockwise-value"></div>
										<div class="unit">
											<s:text name="unit.kWhm2" />
										</div>
									</div>
								</div>
							</div>

							<div style="clear: both;"></div>	
							<s:if test="%{isReport}">
								<s:a action="downloadReport">
									<div id="btnReport"></div>
									<s:param name="linkReport" value="%{linkReport}" />
								</s:a>
							</s:if>
							<s:else>
								<div id="btnReport" style="cursor: pointer;"></div>
							</s:else>				
						</div>
						<div style="clear: both;"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<div class="logo-footer">
				<div id="logo3" class="logo-company">
					<img width="150px" height="62px" src="../img/logo-3.png" />
				</div>
			</div>
		</div>
	</div>
	<!-- input hidden -->
	<input type="hidden" id="currentYearEnergy"
		value="<s:property value='driveBean.currentYearEnergy' />" />
	<input type="hidden" id="pastYearEnergy"
		value="<s:property value='driveBean.pastYearEnergy' />" />
	<input type="hidden" id="discountEnergy"
		value="<s:property value='driveBean.discountEnergy' />" />
	<input type="hidden" id="indexPastYear"
		value="<s:property value='driveBean.indexPastYear' />" />
	<input type="hidden" id="indexCurrentYear"
		value="<s:property value='driveBean.indexCurrentYear' />" />
	<input type="hidden" id="driveLevel" value="<s:property value='driveBean.level_Drive' />" />
	<input type="hidden" id="percent" value="<s:property value='percent' />"/>
	<!-- Init lab js-->
	<script src="../js/initDrive.js"></script>
	<!-- end init lab -->

	<script src="../js/remodal.min.js"></script>
	<script src="../js/jquery.textfill.min.js"></script>
</body>
</html>