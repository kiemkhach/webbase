<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title><s:property value='carreFourBean.siteName' /></title>
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
			<div id="logo-img" style="background-image: url('<s:url action='loadLogo' />')"
				></div>
		</div>
		<div id="content" class="row">
			<div id="content-body">
				<div class="body-block">
					<div id="address-bar">
						<span><img src="../img/icon-location.png" />
						<s:property value='carreFourBean.SiteStr' /></span>
					</div>

					<div id="magasin">
						<div class="magasin-content">
							<div style="cursor: pointer" class="red" id="froid">
								<div>
									<span> <s:if test="%{carreFourBean.froid == null }">
										---
									</s:if> <s:else>
											<s:property
												value="%{getText('format.number',{carreFourBean.froid})}" />
										</s:else> <s:text name="unit.kWh" /></span>
								</div>
								<div>
									<span><s:text name="label.uppercase.froid"></s:text></span>
								</div>
							</div>
							<div
								<s:if test='%{carreFourBean.mapEclairageCap.size > 1}'>style="cursor: pointer"</s:if>
								class="green" id="eclairage">
								<div>
									<span> <s:if test="%{carreFourBean.eclairage == null }">
										---
									</s:if> <s:else>
											<s:property
												value="%{getText('format.number',{carreFourBean.eclairage})}" />
										</s:else> <s:text name="unit.kWh" />
									</span>
								</div>
								<div>
									<span><s:text name="label.uppercase.eclairage"></s:text></span>
								</div>
							</div>
							<div
								<s:if test='%{carreFourBean.mapBoulangerieCap.size > 1}'>style="cursor: pointer"</s:if>
								class="yellow" id="boulangerie">
								<div>
									<span> <s:if
											test="%{carreFourBean.boulangerie == null }">
										---
									</s:if> <s:else>
											<s:property
												value="%{getText('format.number',{carreFourBean.boulangerie})}" />
										</s:else> <s:text name="unit.kWh" />
									</span>
								</div>
								<div>
									<span><s:text name="label.uppercase.boulangerie"></s:text></span>
								</div>
							</div>
							<div
								<s:if test='%{carreFourBean.mapBureauCap.size > 1}'>style="cursor: pointer"</s:if>
								class="cadetblue" id="bureu">
								<div>
									<span> <s:if test="%{carreFourBean.bureau == null }">
										---
									</s:if> <s:else>
											<s:property
												value="%{getText('format.number',{carreFourBean.bureau})}" />
										</s:else> <s:text name="unit.kWh" />
									</span>
								</div>
								<div>
									<span><s:text name="label.uppercase.bureu"></s:text></span>
								</div>
							</div>
							<div
								<s:if test='%{carreFourBean.mapCvcCap.size > 1}'>style="cursor: pointer"</s:if>
								class="purple" id="cvc">
								<div>
									<span><s:if test="%{carreFourBean.cvc == null }">
										---
									</s:if> <s:else>
											<s:property
												value="%{getText('format.number',{carreFourBean.cvc})}" />
										</s:else> <s:text name="unit.kWh" /></span>
								</div>
								<div>
									<span><s:text name="label.uppercase.cvc"></s:text></span>
								</div>
							</div>
							<div class="temperature-ext">
								<div class="temperature-ext-value">
									<s:if test="%{carreFourBean.temperateIn == null }">
										---
									</s:if>
									<s:else>
										<s:property value='carreFourBean.temperateIn' />
									</s:else>
									<s:text name="degree.celsius" />
								</div>
							</div>
						</div>
						<div class="parking-content">
							<div class="temperature-parking">
								<div class="temperature-parking-value">
									<s:if test="%{carreFourBean.temperateOut == null }">
										---
									</s:if>
									<s:else>
										<s:property value='carreFourBean.temperateOut' />
									</s:else>
									<s:text name="degree.celsius" />
								</div>
							</div>
							<div class="parking-driver">
								<s:if test="%{carreFourBean.drive == null }">
										---
									</s:if>
								<s:else>
									<s:property
										value="%{getText('format.number',{carreFourBean.drive})}" />
								</s:else>
								<s:text name="unit.kWh" />
								<br><s:text name="label.uppercase.drive" />
							</div>
							<div class="parking-top-logo"></div>
							<div class="parking-bot-logo"></div>
						</div>
						<div style="clear: both;"></div>
					</div>

					<div id="energetic-report">
						<div class="block-inside" id="energetic">
							<div id="electrique" class="computeur-block">
								<div class="energetic-value">
									<s:if test="%{carreFourBean.compteurElec == null }">
										---
									</s:if>
									<s:else>
										<s:property
											value="%{getText('format.number',{carreFourBean.compteurElec})}" />
									</s:else>
									<span><s:text name="unit.kWh" /></span>
								</div>
							</div>
							<div id="gaz" class="computeur-block">
								<div class="energetic-value">
									<s:if test="%{carreFourBean.compteurGaz == null }">
										---
									</s:if>
									<s:else>
										<s:property
											value="%{getText('format.number',{carreFourBean.compteurGaz})}" />
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
								<div class="energetique-title"><s:text name="label.uppercase.energy" /></div>
								<div class="energetique-years">
									<div class="enertique-year-block" id="preYear">
										<s:if test="%{carreFourBean.pastYear == null }">
										---
									</s:if>
										<s:else>
											<s:property value='carreFourBean.pastYear' />
										</s:else>
									</div>
									<div class="enertique-year-block" id="curYear">
										<s:if test="%{carreFourBean.currentYear == null }">
										---
									</s:if>
										<s:else>
											<s:property value='carreFourBean.currentYear' />
										</s:else>
									</div>
									<div class="enertique-year-block" id="changePer">
										<s:if test="%{carreFourBean.discount == null }">
										---
									</s:if>
										<s:else>
											<s:property value='carreFourBean.discount' />%
										</s:else>
									</div>
								</div>
								<div class="enertique-clockwises">
									<div id="rangeAplus" class="range-value"><
										${carreFourBean.range_APlus_Max}</div>
									<div id="rangeA" class="range-value">${carreFourBean.range_A_Min}-${carreFourBean.range_A_Max}</div>
									<div id="rangeB" class="range-value">${carreFourBean.range_B_Min}-${carreFourBean.range_B_Max}</div>
									<div id="rangeC" class="range-value">${carreFourBean.range_C_Min}-${carreFourBean.range_C_Max}</div>
									<div id="rangeD" class="range-value">${carreFourBean.range_D_Min}-${carreFourBean.range_D_Max}</div>
									<div id="rangeE" class="range-value">${carreFourBean.range_E_Min}-${carreFourBean.range_E_Max}</div>
									<div id="rangeF" class="range-value">${carreFourBean.range_F_Min}-${carreFourBean.range_F_Max}</div>
									<div id="rangeG" class="range-value">${carreFourBean.range_G_Min}-${carreFourBean.range_G_Max}</div>
									<div id="rangeH" class="range-value">${carreFourBean.range_H_Min}-${carreFourBean.range_H_Max}</div>
									<div id="rangeI" class="range-value">>${carreFourBean.range_H_Max}</div>
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
								<div class="export-button"></div>
								<div class="export-button"></div>
								<s:if test="%{isReport}">
									<s:a action="downloadBackLog">	
										<div id="reportAnalyse" class="export-button"></div>									
										<s:param name="linkReport" value="%{linkReport}" />
									</s:a>
								</s:if>
								<s:else>
									<div id="reportAnalyse" style="cursor: pointer;" class="export-button">							
									</div>
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
				<div id="logo1" class="logo-company">
					<img width="150px" height="62px" src="../img/logo-1.png" />
				</div>
				<div id="logo2" class="logo-company">
					<img width="150px" height="62px" src="../img/logo-2.png" />
				</div>
				<div id="logo3" class="logo-company">
					<img width="150px" height="62px" src="../img/logo-3.png" />
				</div>
			</div>
		</div>
	</div>
	<!-- input hidden -->
	<input type="hidden" id="currentYearEnergy"
		value="<s:property value='carreFourBean.currentYearEnergy' />" />
	<input type="hidden" id="pastYearEnergy"
		value="<s:property value='carreFourBean.pastYearEnergy' />" />
	<input type="hidden" id="discountEnergy"
		value="<s:property value='carreFourBean.discountEnergy' />" />
	<input type="hidden" id="indexPastYear"
		value="<s:property value='carreFourBean.indexPastYear' />" />
	<input type="hidden" id="indexCurrentYear"
		value="<s:property value='carreFourBean.indexCurrentYear' />" />
	<!-- Init lab js-->
	<script src="../js/initlab.js"></script>
	<!-- end init lab -->
	<%-- 	<s:if test="%{carreFourBean.mapFroidCap.size > 1}"> --%>
	<div data-remodal-id="froidDetail" class="remodal" align="center">
		<button data-remodal-action="close" class="remodal-close"></button>
		<table>
			<tr>
				<td><s:text name="label.uppercase.froid.positive" /></td>
				<td>:</td>
				<td><s:if test="%{carreFourBean.froidPos == null }">
										---
									</s:if> <s:else>
						<s:property
							value="%{getText('format.number',{carreFourBean.froidPos})}" />
					</s:else> <s:text name="unit.kWh" /></td>
			</tr>
			<tr>
				<td><s:text name="label.uppercase.froid.reserve" /></td>
				<td>:</td>
				<td><s:if test="%{carreFourBean.froidRes == null }">
										---
									</s:if> <s:else>
						<s:property
							value="%{getText('format.number',{carreFourBean.froidRes})}" />
					</s:else> <s:text name="unit.kWh" /></td>
			</tr>
			<tr>
				<td><s:text name="label.uppercase.froid.negative" /></td>
				<td>:</td>
				<td><s:if test="%{carreFourBean.froidNeg == null }">
										---
									</s:if> <s:else>
						<s:property
							value="%{getText('format.number',{carreFourBean.froidNeg})}" />
					</s:else>
					<s:text name="unit.kWh" /></td>
			</tr>
		</table>
	</div>
	<%-- 	</s:if> --%>
	<%-- 	<s:if test="%{carreFourBean.mapEclairageCap.size > 1}"> --%>
	<!-- 		<div data-remodal-id="eclairageDetail" class="remodal" align="center"> -->
	<!-- 			<button data-remodal-action="closInteger.parseInt(Integer.parseInt(e" class="remodal-close"></button> -->
	<!-- 			<table> -->
	<%-- 				<s:iterator value="carreFourBean.mapEclairageCap" --%>
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
	<%-- 	<s:if test="%{carreFourBean.mapBoulangerieCap.size > 1}"> --%>
	<!-- 		<div data-remodal-id="boulangerieDetail" class="remodal" -->
	<!-- 			align="center"> -->
	<!-- 			<button data-remodal-action="close" class="remodal-close"></button> -->
	<!-- 			<table> -->
	<%-- 				<s:iterator value="carreFourBean.mapBoulangerieCap" --%>
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
	<%-- 	<s:if test="%{carreFourBean.mapBureauCap.size > 1}"> --%>
	<!-- 		<div data-remodal-id="bureuDetail" class="remodal" align="center"> -->
	<!-- 			<button data-remodal-action="close" class="remodal-close"></button> -->
	<!-- 			<table> -->
	<%-- 				<s:iterator value="carreFourBean.mapBureauCap" var="giftMapElement"> --%>
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
	<%-- 	<s:if test="%{carreFourBean.mapCvcCap.size > 1}"> --%>
	<!-- 		<div data-remodal-id="cvcDetail" class="remodal" align="center"> -->
	<!-- 			<button data-remodal-action="close" class="remodal-close"></button> -->
	<!-- 			<table> -->
	<%-- 				<s:iterator value="carreFourBean.mapCvcCap" var="giftMapElement"> --%>
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