<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<s:if test="%{!isCreate}">
	<title><s:property value="%{numConfigLab008.siteName}" /></title>
</s:if>
<s:else>
	<title><s:property value="lab.name" /> - <s:text
			name="label.create.siteId" /></title>
</s:else>
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<script src="../js/jquery-1.11.3.min.js"></script>
<script src="../metro/js/metro.min.js"></script>
</head>
<body>
	<div class="main">
		<div class="menu">
		<input type="hidden" id="configSiteId" 
			value="${numConfigLab008.siteId}">
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='home' />"><s:text name="label.home" /></a> <a
				class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white" id = "btnConfigHome"
				><s:text
					name="label.configLab008HomePage" /></a>
		</div>
		<s:if test="%{!isCreate}">
			<%-- 			<a class="" href="<s:url action='createConfigLab008' />" --%>
			<%-- 				style="margin-left: 15px;"><s:text name="label.create.siteId" /></a> --%>
			<s:if test="%{listAllConfig != null && listAllConfig.size > 0}">
				<form action="selectSiteLab008">
					<s:select class="input-control select" list="listAllConfig"
						listKey="siteId" listValue="siteName" name="siteId"
						value="%{numConfigLab008.siteId}" onchange="this.form.submit()"
						cssClass="select-tag" />
				</form>
			</s:if>
		</s:if>
		<input type="hidden" id="message" value="<s:property value='mes'/>" />
		<div class="lab-name">
			<img src="../img/logos-boilers-monitor.png" height="200" width="200">
			<div class="text-lab-name">
				<s:property value="%{lab.name}" />
			</div>
		</div>
		<div class="config-site">
			<s:if test="%{isCreate}">
				<div class="sub-leader">
					<s:text name="label.create.siteId" />
				</div>
			</s:if>
			<s:else>
				<s:if test="%{listAllConfig != null && listAllConfig.size > 0}">
					<div class="sub-leader">
						<s:text name="label.siteId" />
						:
						<s:property value="%{numConfigLab008.siteId}" />
					</div>
					<div class="unregister-user">
						<form action="unregisterUserForLab008">
							<s:if test="%{listUsers != null && listUsers.size > 0}">
								<s:iterator value="listUsers" status="rows">
									<label class="input-control checkbox small-check"> <s:if
											test="%{listUsers[#rows.index] == username_lab_service}">
											<input type="checkbox" value="<s:property/>"
												disabled="disabled" />
										</s:if> <s:else>
											<input type="checkbox" value="<s:property/>" name="listUser" />
										</s:else> <span class="check"></span> <span class="caption"><s:property /></span>
									</label>
								</s:iterator>
								<input type="submit" class="button primary"
									value="<s:text name="label.unregister"/>" />
							</s:if>
						</form>
					</div>
					<div class="register-user">
						<form action="registerUserForLab008">
							<input type="text" name="listUserRegist" size="50"
								placeholder='<s:text name="placeholder.user.list"/>' /> <input
								type="submit" class="button primary"
								value="<s:text name="label.register"/>" />
						</form>
					</div>
				</s:if>
			</s:else>
			<s:if
				test="%{(!isCreate && listAllConfig != null && listAllConfig.size > 0) || isCreate}">
				<form action="configLab008" method="post"
					enctype="multipart/form-data">
					<table>
						<s:hidden name="isCreate" value="%{isCreate}" />
						<s:hidden name="reportName" value="%{numConfigLab008.reportName}" />
						<s:if test="%{!isCreate}">
							<s:hidden name="id" value="%{numConfigLab008.id}" />
						</s:if>
						<tr>
							<s:textfield value="%{numConfigLab008.siteId}" name="siteId"
								key="label.siteId" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.siteName}" name="siteName"
								key="label.siteName" size="50" />
						</tr>
						<tr>
							<s:if
								test="%{numConfigLab008.gasNaturalModuleId == null || numConfigLab008.gasNaturalModuleId == ''}">
								<td><s:text name="label.gasNaturalModuleId" /></td>
								<td><input type="text" name="gasNaturalModuleId"
									placeholder='<s:text name="placeholder.module.list" />'
									size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield value="%{numConfigLab008.gasNaturalModuleId}"
									name="gasNaturalModuleId" key="label.gasNaturalModuleId"
									size="50" />
							</s:else>
						</tr>
						<tr>
							<s:if
								test="%{numConfigLab008.productionModuleId == null || numConfigLab008.productionModuleId == ''}">
								<td><s:text name="label.productionModuleId" /></td>
								<td><input type="text" name="productionModuleId"
									placeholder='<s:text name="placeholder.module.list" />'
									size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield value="%{numConfigLab008.productionModuleId}"
									name="productionModuleId" key="label.productionModuleId"
									size="50" />
							</s:else>
						</tr>
						<tr>
							<s:if
								test="%{numConfigLab008.chauffageModuleId == null || numConfigLab008.chauffageModuleId == ''}">
								<td><s:text name="label.chauffageModuleId" /></td>
								<td><input type="text" name="chauffageModuleId"
									placeholder='<s:text name="placeholder.module.list" />'
									size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield value="%{numConfigLab008.chauffageModuleId}"
									name="chauffageModuleId" key="label.chauffageModuleId"
									size="50" />
							</s:else>
						</tr>
						<tr>
							<s:if
								test="%{numConfigLab008.ecsZoneBasse == null || numConfigLab008.ecsZoneBasse == ''}">
								<td><s:text name="label.ecsZoneBasse" /></td>
								<td><input type="text" name="ecsZoneBasse"
									placeholder='<s:text name="placeholder.module.list" />'
									size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield value="%{numConfigLab008.ecsZoneBasse}"
									name="ecsZoneBasse" key="label.ecsZoneBasse" size="50" />
							</s:else>
						</tr>
						<tr>
							<s:if
								test="%{numConfigLab008.ecsZoneHaute == null || numConfigLab008.ecsZoneHaute == ''}">
								<td><s:text name="label.ecsZoneHaute" /></td>
								<td><input type="text" name="ecsZoneHaute"
									placeholder='<s:text name="placeholder.module.list" />'
									size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield value="%{numConfigLab008.ecsZoneHaute}"
									name="ecsZoneHaute" key="label.ecsZoneHaute" size="50" />
							</s:else>
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.boilerModel}"
								name="boilerModel" key="label.boilerModel" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.boilerYear}"
								name="boilerYear" key="label.boilerYear" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.boilerTheoryPower}"
								name="boilerTheoryPower" key="label.boilerTheoryPower" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.coeff1}" name="coeff1"
								key="label.coeff1" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.coeff2}" name="coeff2"
								key="label.coeff2" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.coeff3}" name="coeff3"
								key="label.coeff3" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.coeff4}" name="coeff4"
								key="label.coeff4" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.coeff5}" name="coeff5"
								key="label.coeff5" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.coeff6}" name="coeff6"
								key="label.coeff6" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.coeffRadnConvection}"
								name="coeffRadnConvection" key="label.coeffRadnConvection"
								size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.modelPrecision}"
								name="modelPrecision" key="label.precision" size="50" />
						</tr>
						<tr >
							<td><s:text name="label.fromDate" /></td>
							<td>
								<div class="data-datepicker" id="datepicker">
									<input type="text" value="<s:property value="%{numConfigLab008.fromDate}"/>" name="fromDate">
									<button class="button">
										<span class="mif-calendar"></span>
									</button>
								</div>
							</td>
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.uriIcon}" name="uriIcon"
								key="label.uriIcon" size="80" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab008.logo}" name="logo"
								key="label.logo" size="80" />
						</tr>
						<!-- 						<tr> -->
						<%-- 							<td class="upload-file-label"><s:text name="label.reportName" /></td> --%>
						<!-- 							<td class="upload-file">			 -->
						<%-- 								<input type="text" disabled="true" size="20" value='<s:property value='%{numConfigLab008.reportName}'/>'/>						 --%>
						<!-- 								<div class="input-control file" data-role="input" style="width: 235px;">									 -->
						<%-- 									<input type="file" name="file" accept=".pdf" placeholder="<s:text name="label.upload"/>" > --%>
						<!-- 									<button class="button"> -->
						<!-- 										<img src="../img/Blank_Folder.png" /> -->
						<!-- 									</button> -->
						<!-- 								</div> -->
						<!-- 							</td> -->
						<!-- 						</tr> -->
						<tr>
							<td></td>
							<td>
								<div class="tbl-button">
									<input type="submit" value="<s:text name="label.save"/>"
										class="button primary" />
									<s:if test="%{isCreate}">
										<a class="button bg-red bg-active-darkRed fg-white"
											href="<s:url action='cancelConfigLab008' />"><s:text
												name="label.cancel" /></a>
									</s:if>
									<s:else>
										<a class="button bg-red bg-active-darkRed fg-white"
											id="delete-subscription"
											href="<s:url action='deleteConfigLab008'>
										<s:param name="id" value="%{numConfigLab008.id}"></s:param>
										<s:param name="siteId" value="%{numConfigLab008.siteId}"></s:param>
										</s:url>">
											<s:text name="label.delete" />
										</a>
									</s:else>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</s:if>
			<s:else>
				<s:text name="text.nodata" />
			</s:else>
			<br /> <br />
			<table style="width: 80%;" border="1">
				<tr>
					<td style="width: 50%"><s:text
							name="label.exportBoilerFilePath" /></td>
					<td>
						<form action="importlab008BoilerInfoFrCsv" method="post"
							enctype="multipart/form-data">
							<s:hidden id="siteImport" name="siteImport"
								value="%{numConfigLab008.siteId}" />
							<table style="width: 70%">
								<tr>
									<td><input type="file" name="importCsvFile" value=""
										accept=".csv" id="importCsvFile"></td>
								</tr>
								<tr>
									<td><input type="submit"
										value="<s:text name="label.import"/>" class="button primary" />
									</td>
								</tr>
							</table>
						</form>
						<form action="exportlab008BoilerInfoFrCsv" method="post"
							enctype="multipart/form-data">
							<s:hidden id="siteImport" name="siteImport"
								value="%{numConfigLab008.siteId}" />
							<table style="width: 70%">
								<tr>
									<td><input type="submit"
										value="<s:text name="label.export"/>" class="button primary" />
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
<!-- 				<tr> -->
<%-- 					<td><s:text name="label.importlab008TrefFrCsv" /></td> --%>
<!-- 					<td> -->
<!-- 						<form action="importlab008TrefFrCsv" method="post" -->
<!-- 							enctype="multipart/form-data"> -->
<%-- 							<s:hidden id="siteImport" name="siteImport" --%>
<%-- 								value="%{numConfigLab008.siteId}" /> --%>
<!-- 							<table style="width: 70%"> -->
<!-- 								<tr> -->
<!-- 									<td><input type="file" name="importTrefCsvFile" value="" -->
<!-- 										accept=".csv" id="importTrefCsvFile"></td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td><input type="submit" -->
<%-- 										value="<s:text name="label.import"/>" class="button primary" /> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 							</table> -->
<!-- 						</form> -->
<!-- 						<form action="exportlab008TrefFrCsv" method="post" -->
<!-- 							enctype="multipart/form-data"> -->
<%-- 							<s:hidden id="siteImport" name="siteImport" --%>
<%-- 								value="%{numConfigLab008.siteId}" /> --%>
<!-- 							<table style="width: 70%"> -->
<!-- 								<tr> -->
<!-- 									<td><input type="submit" -->
<%-- 										value="<s:text name="label.export"/>" class="button primary" /> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 							</table> -->
<!-- 						</form> -->
<!-- 					</td> -->
<!-- 				</tr> -->
				<tr>
					<td><s:text name="label.importLab008Performance" /></td>
					<td>
						<form action="importlab008PerformanceCsv" method="post"
							enctype="multipart/form-data">
							<s:hidden id="siteImport" name="siteImport"
								value="%{numConfigLab008.siteId}" />
							<table style="width: 70%">
								<tr>
									<td><input type="file" name="importPerformaceCsvFile"
										value="" accept=".csv" id="importPerformaceCsvFile"></td>
								</tr>
								<tr>
									<td><input type="submit"
										value="<s:text name="label.import"/>" class="button primary" />
									</td>
								</tr>
							</table>
						</form>
						<form action="exportlab008PerformanceCsv" method="post"
							enctype="multipart/form-data">
							<s:hidden id="siteImport" name="siteImport"
								value="%{numConfigLab008.siteId}" />
							<table style="width: 70%">
								<tr>
									<td><input type="submit"
										value="<s:text name="label.export"/>" class="button primary" />
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script src="../js/initlab.js"></script>
</body>
</html>