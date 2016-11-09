<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title><s:property value='eLeclercBean.siteName' /></title>
<link href="../css/bootstrap.min.css" rel="stylesheet" />
<link href="../css/labstyle.css" rel="stylesheet" />
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
<!-- 	<div onclick="javascript:location.href='logOut.action';" id="logout"> -->
<%-- 		<img src="../img/icon-deconnexion.png"> <s:text name="label.logout" /> --%>
<!-- 	</div> -->
	<div id="wrapper" class="container">
		<div id="header-logo" class="page-header">
			<div id="logo-img"
				style="background-image: url('<s:url action='loadLogo' />')"></div>
		</div>
		<div id="content" class="row">
			<div id="content-body">
				<div class="body-block">
					<div id="address-bar">
					<span>
						<img src="../img/icon-location.png" />
						<s:property value='eLeclercBean.SiteStr' /></span>
					</div>

					<div id="magasin">
						<div class="magasin-content">
							<div style="cursor: pointer" class="red" id="froid">
								<div>
									<span> <s:if test="%{eLeclercBean.froid == null }">
										---
									</s:if> <s:else>
											<s:property
												value="%{getText('format.number',{eLeclercBean.froid})}" />
										</s:else> <s:text name="unit.kWh" /></span>
								</div>
								<div>
									<span><s:text name="label.uppercase.froid"></s:text></span>
								</div>
							</div>
							<div
								<s:if test='%{eLeclercBean.mapEclairageCap.size > 1}'>style="cursor: pointer"</s:if>
								class="green" id="eclairage">
								<div>
									<span> <s:if test="%{eLeclercBean.eclairage == null }">
										---
									</s:if> <s:else>
											<s:property
												value="%{getText('format.number',{eLeclercBean.eclairage})}" />
										</s:else> <s:text name="unit.kWh" />
									</span>
								</div>
								<div>
									<span><s:text name="label.uppercase.eclairage"></s:text></span>
								</div>
							</div>
							<div
								<s:if test='%{eLeclercBean.mapBoulangerieCap.size > 1}'>style="cursor: pointer"</s:if>
								class="yellow" id="boulangerie">
								<div>
									<span> <s:if test="%{eLeclercBean.boulangerie == null }">
										---
									</s:if> <s:else>
											<s:property
												value="%{getText('format.number',{eLeclercBean.boulangerie})}" />
										</s:else> <s:text name="unit.kWh" />
									</span>
								</div>
								<div>
									<span><s:text name="label.uppercase.boulangerie"></s:text></span>
								</div>
							</div>
							<div
								<s:if test='%{eLeclercBean.mapBureauCap.size > 1}'>style="cursor: pointer"</s:if>
								class="cadetblue" id="bureu">
								<div>
									<span> <s:if test="%{eLeclercBean.bureau == null }">
										---
									</s:if> <s:else>
											<s:property
												value="%{getText('format.number',{eLeclercBean.bureau})}" />
										</s:else> <s:text name="unit.kWh" />
									</span>
								</div>
								<div>
									<span><s:text name="label.uppercase.bureu"></s:text></span>
								</div>
							</div>
							<div
								<s:if test='%{eLeclercBean.mapCvcCap.size > 1}'>style="cursor: pointer"</s:if>
								class="purple" id="cvc">
								<div>
									<span><s:if test="%{eLeclercBean.cvc == null }">
										---
									</s:if> <s:else>
											<s:property
												value="%{getText('format.number',{eLeclercBean.cvc})}" />
										</s:else> <s:text name="unit.kWh" /></span>
								</div>
								<div>
									<span><s:text name="label.uppercase.cvc"></s:text></span>
								</div>
							</div>
							<div class="temperature-ext">
								<div class="temperature-ext-value">
									<s:if test="%{eLeclercBean.temperateIn == null }">
										---
									</s:if>
									<s:else>
										<s:property value='eLeclercBean.temperateIn' />
									</s:else>
									<s:text name="degree.celsius" />
								</div>
							</div>
						</div>
						<div class="parking-content">
							<div class="temperature-parking">
								<div class="temperature-parking-value">
									<s:if test="%{eLeclercBean.temperateOut == null }">
										---
									</s:if>
									<s:else>
										<s:property value='eLeclercBean.temperateOut' />
									</s:else>
									<s:text name="degree.celsius" />
								</div>
							</div>
<!-- 							<div class="parking-driver"> -->
<%-- 								<s:if test="%{eLeclercBean.drive == null }"> --%>
<!-- 										--- -->
<%-- 									</s:if> --%>
<%-- 								<s:else> --%>
<%-- 									<s:property --%>
<%-- 										value="%{getText('format.number',{eLeclercBean.drive})}" /> --%>
<%-- 								</s:else> --%>
<%-- 								<s:text name="unit.kWh" /> --%>
<%-- 								<br><s:text name="label.uppercase.drive"/> --%>
<!-- 							</div> -->
							<div class="parking-top-logo"></div>
						</div>
						<div style="clear: both;"></div>
					</div>

					<div id="energetic-report">
						<div class="block-inside" id="energetic">
							<div id="electrique" class="computeur-block">
								<div class="energetic-value">
									<s:if test="%{eLeclercBean.compteurElec == null }">
										---
									</s:if>
									<s:else>
										<s:property
											value="%{getText('format.number',{eLeclercBean.compteurElec})}" />
									</s:else>
									<span><s:text name="unit.kWh" /></span>
								</div>
							</div>
							<div id="gaz" class="computeur-block">
								<div class="energetic-value">
									<s:if test="%{eLeclercBean.compteurGaz == null }">
										---
									</s:if>
									<s:else>
										<s:property
											value="%{getText('format.number',{eLeclercBean.compteurGaz})}" />
									</s:else>
									<span><s:text name="unit.kWh" /></span>
								</div>
							</div>
							<div style="clear: both;"></div>
							<div class="computeur-block-title">
								<s:text name="number.electric" />
							</div>
							<div class="computeur-block-title">
								<s:text name="number.gaz" />
							</div>
							<div style="clear: both;"></div>
						</div>
						<div class="block-inside">
							<div id="energetique">
								<div class="energetique-title"><s:text name="label.uppercase.energy"/></div>
								<div class="energetique-years">
									<div class="enertique-year-block" id="preYear">
										<s:if test="%{eLeclercBean.pastYear == null }">
										---
									</s:if>
										<s:else>
											<s:property value='eLeclercBean.pastYear' />
										</s:else>
									</div>
									<div class="enertique-year-block" id="curYear">
										<s:if test="%{eLeclercBean.currentYear == null }">
										---
									</s:if>
										<s:else>
											<s:property value='eLeclercBean.currentYear' />
										</s:else>
									</div>
									<div id="changePer1">
										<s:if test="%{eLeclercBean.discount == null }">
										---
										</s:if>
										<s:else>
											<s:property value='eLeclercBean.discount' />%
										</s:else>
									</div>
								</div>
								<div class="enertique-clockwises">
									<div id="rangeAplus" class="range-value"><
										${eLeclercBean.range_APlus_Max}</div>
									<div id="rangeA" class="range-value">${eLeclercBean.range_A_Min}-${eLeclercBean.range_A_Max}</div>
									<div id="rangeB" class="range-value">${eLeclercBean.range_B_Min}-${eLeclercBean.range_B_Max}</div>
									<div id="rangeC" class="range-value">${eLeclercBean.range_C_Min}-${eLeclercBean.range_C_Max}</div>
									<div id="rangeD" class="range-value">${eLeclercBean.range_D_Min}-${eLeclercBean.range_D_Max}</div>
									<div id="rangeE" class="range-value">${eLeclercBean.range_E_Min}-${eLeclercBean.range_E_Max}</div>
									<div id="rangeF" class="range-value">${eLeclercBean.range_F_Min}-${eLeclercBean.range_F_Max}</div>
									<div id="rangeG" class="range-value">${eLeclercBean.range_G_Min}-${eLeclercBean.range_G_Max}</div>
									<div id="rangeH" class="range-value">${eLeclercBean.range_H_Min}-${eLeclercBean.range_H_Max}</div>
									<div id="rangeI" class="range-value">>${eLeclercBean.range_H_Max}</div>
									<div id="grade" class="clockwise">
										<div class="lineDiv"></div>
										<div class="clockwise-value"></div>
										<div class="unit">
											<s:text name="unit.kWhm2" />
										</div>
									</div>
								</div>
							</div>

							<div id="export">
								<s:url action="eleclercDrive.action" var="urlDrive" >
   									<s:param name="siteId"><s:property value='eLeclercBean.siteId' /></s:param>
								</s:url>
								<s:a href="%{urlDrive}">
									<div class="export-button" id="reportImage"></div>
								</s:a>
								<s:if test="%{isReport}">
									<s:a action="downloadBackLog">
										<div id="reportAnalyse" class="export-button"></div>
										<s:param name="linkReport" value="%{linkReport}" />
									</s:a>
								</s:if>
								<s:else>
									<div id="reportAnalyse" class="export-button" style="cursor: pointer;"></div>
								</s:else>	
							</div>
							<div style="clear: both;"></div>
						</div>
						<div style="clear: both;"></div>
					</div>
				</div>
			</div>
			<div id="annotation">*<s:text name="label.annotation"/></div>
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
		value="<s:property value='eLeclercBean.currentYearEnergy' />" />
	<input type="hidden" id="pastYearEnergy"
		value="<s:property value='eLeclercBean.pastYearEnergy' />" />
	<input type="hidden" id="discountEnergy"
		value="<s:property value='eLeclercBean.discountEnergy' />" />
	<input type="hidden" id="indexPastYear"
		value="<s:property value='eLeclercBean.indexPastYear' />" />
	<input type="hidden" id="indexCurrentYear"
		value="<s:property value='eLeclercBean.indexCurrentYear' />" />
	<!-- Init lab js-->
	<script src="../js/initlab.js"></script>
	<!-- end init lab -->
	<%-- 	<s:if test="%{eLeclercBean.mapFroidCap.size > 1}"> --%>
	<div data-remodal-id="froidDetail" class="remodal" align="center">
		<button data-remodal-action="close" class="remodal-close"></button>
		<table>
			<tr>
				<td><s:text name="label.uppercase.froid.positive" /></td>
				<td>:</td>
				<td><s:if test="%{eLeclercBean.froidPos == null }">
										---
									</s:if> <s:else>
						<s:property
							value="%{getText('format.number',{eLeclercBean.froidPos})}" />
					</s:else> <s:text name="unit.kWh" /></td>
			</tr>
			<tr>
				<td><s:text name="label.uppercase.froid.reserve" /></td>
				<td>:</td>
				<td><s:if test="%{eLeclercBean.froidRes == null }">
										---
									</s:if> <s:else>
						<s:property
							value="%{getText('format.number',{eLeclercBean.froidRes})}" />
					</s:else> <s:text name="unit.kWh" /></td>
			</tr>
			<tr>
				<td><s:text name="label.uppercase.froid.negative" /></td>
				<td>:</td>
				<td><s:if test="%{eLeclercBean.froidNeg == null }">
										---
									</s:if> <s:else>
						<s:property
							value="%{getText('format.number',{eLeclercBean.froidNeg})}" />
					</s:else> <s:text name="unit.kWh" /></td>
			</tr>
		</table>
	</div>
	<%-- 	</s:if> --%>
	<%-- 	<s:if test="%{eLeclercBean.mapEclairageCap.size > 1}"> --%>
	<!-- 		<div data-remodal-id="eclairageDetail" class="remodal" align="center"> -->
	<!-- 			<button data-remodal-action="closInteger.parseInt(Integer.parseInt(e" class="remodal-close"></button> -->
	<!-- 			<table> -->
	<%-- 				<s:iterator value="eLeclercBean.mapEclairageCap" --%>
	<%-- 					var="giftMapElement"> --%>
	<%-- 					<s:set var="giftKey" value="#giftMapElement.key" /> --%>
	<%-- 					<s:set var="giftValue" value="#giftMapElement.value" /> --%>
	<!-- 					<tr> -->
	<%-- 						<td><s:property value="#giftKey" /></td> --%>
	<!-- 						<td>:</td> -->
	<%-- 						<td><s:property value="%{getText('format.number',{#giftValue})}" /> <s:text name="unit.kWh"/></td> --%>
	<!-- 					</tr> -->
	<%-- 				</s:iterator> --%>
	<!-- 			</table> -->
	<!-- 		</div> -->
	<%-- 	</s:if> --%>
	<%-- 	<s:if test="%{eLeclercBean.mapBoulangerieCap.size > 1}"> --%>
	<!-- 		<div data-remodal-id="boulangerieDetail" class="remodal" -->
	<!-- 			align="center"> -->
	<!-- 			<button data-remodal-action="close" class="remodal-close"></button> -->
	<!-- 			<table> -->
	<%-- 				<s:iterator value="eLeclercBean.mapBoulangerieCap" --%>
	<%-- 					var="giftMapElement"> --%>
	<%-- 					<s:set var="giftKey" value="#giftMapElement.key" /> --%>
	<%-- 					<s:set var="giftValue" value="#giftMapElement.value" /> --%>
	<!-- 					<tr> -->
	<%-- 						<td><s:property value="#giftKey" /></td> --%>
	<!-- 						<td>:</td> -->
	<%-- 						<td><s:property value="%{getText('format.number',{#giftValue})}" /> <s:text name="unit.kWh"/></td> --%>
	<!-- 					</tr> -->
	<%-- 				</s:iterator> --%>
	<!-- 			</table> -->
	<!-- 		</div> -->
	<%-- 	</s:if> --%>
	<%-- 	<s:if test="%{eLeclercBean.mapBureauCap.size > 1}"> --%>
	<!-- 		<div data-remodal-id="bureuDetail" class="remodal" align="center"> -->
	<!-- 			<button data-remodal-action="close" class="remodal-close"></button> -->
	<!-- 			<table> -->
	<%-- 				<s:iterator value="eLeclercBean.mapBureauCap" var="giftMapElement"> --%>
	<%-- 					<s:set var="giftKey" value="#giftMapElement.key" /> --%>
	<%-- 					<s:set var="giftValue" value="#giftMapElement.value" /> --%>
	<!-- 					<tr> -->
	<%-- 						<td><s:property value="#giftKey" /></td> --%>
	<!-- 						<td>:</td> -->
	<%-- 						<td><s:property value="%{getText('format.number',{#giftValue})}" /> <s:text name="unit.kWh"/></td> --%>
	<!-- 					</tr> -->
	<%-- 				</s:iterator> --%>
	<!-- 			</table> -->
	<!-- 		</div> -->
	<%-- 	</s:if> --%>
	<%-- 	<s:if test="%{eLeclercBean.mapCvcCap.size > 1}"> --%>
	<!-- 		<div data-remodal-id="cvcDetail" class="remodal" align="center"> -->
	<!-- 			<button data-remodal-action="close" class="remodal-close"></button> -->
	<!-- 			<table> -->
	<%-- 				<s:iterator value="eLeclercBean.mapCvcCap" var="giftMapElement"> --%>
	<%-- 					<s:set var="giftKey" value="#giftMapElement.key" /> --%>
	<%-- 					<s:set var="giftValue" value="#giftMapElement.value" /> --%>
	<!-- 					<tr> -->
	<%-- 						<td><s:property value="#giftKey" /></td> --%>
	<!-- 						<td>:</td> -->
	<%-- 						<td><s:property value="%{getText('format.number',{#giftValue})}" /> <s:text name="unit.kWh"/></td> --%>
	<!-- 					</tr> -->
	<%-- 				</s:iterator> --%>
	<!-- 			</table> -->
	<!-- 		</div> -->
	<%-- 	</s:if> --%>

	<script src="../js/remodal.min.js"></script>
	<script src="../js/jquery.textfill.min.js"></script>
</body>
</html>