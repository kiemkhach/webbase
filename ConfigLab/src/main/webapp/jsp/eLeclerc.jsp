<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<s:if test="%{!isCreate}">
	<title><s:property value="%{numConfigLab003.siteName}" /></title>
</s:if>
<s:else>
	<title><s:property value="lab.name"/> - <s:text name="label.create.siteId" /></title>
</s:else>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<script src="../js/jquery-1.11.3.min.js"></script>
<script src="../metro/js/metro.min.js"></script>
</head>
<body>
	<div class="main">
		<div class="menu">
			<a class="button bg-teal bg-active-darkTeal fg-white" href="<s:url action='home' />"><s:text name="label.home" /></a> 
			<a class="button bg-teal bg-active-darkTeal fg-white" href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
		</div>
		<s:if test="%{!isCreate}"> 
			<a class="" href="<s:url action='createConfigLab003' />" style="margin-left: 15px;"><s:text name="label.create.siteId" /></a>
			<s:if test="%{listAllConfig != null && listAllConfig.size > 0}">
				<form action="selectSiteLab003">
					<s:select class="input-control select" list="listAllConfig"
						listKey="siteId" listValue="siteName" name="siteId"
						value="%{numConfigLab003.siteId}" onchange="this.form.submit()" cssClass="select-tag"/>
				</form>
			</s:if>
		</s:if>
		<input type="hidden" id="message" value="<s:property value='mes'/>" />
		<div class="lab-name">
			<img src="../img/logos-leclerc.png">
			<div class="text-lab-name">
				<s:property value="%{lab.name}" />
			</div>
		</div>
		<div class="config-site">
			<s:if test="%{isCreate}">
				<div class="sub-leader" style="margin-bottom: 15px;">
					<s:text name="label.create.siteId" />
				</div>
			</s:if>
			<s:else>
				<s:if test="%{listAllConfig != null && listAllConfig.size > 0}">
					<div class="sub-leader">
						<s:text name="label.siteId" />
						:
						<s:property value="%{numConfigLab003.siteId}" />
					</div>
					<div class="unregister-user">
						<form action="unregisterUserForLab003">
							<s:if test="%{listUsers != null && listUsers.size > 0}">
								<s:iterator value="listUsers" status="rows">
									<label class="input-control checkbox small-check"> 
										<s:if test="%{listUsers[#rows.index] == username_lab_service}" >																															
											<input type="checkbox" value="<s:property/>" disabled="disabled"/>
										</s:if>
										<s:else>
											<input type="checkbox" value="<s:property/>" name="listUser" />
										</s:else> 
										<span class="check"></span> 
										<span class="caption"><s:property /></span>
									</label>
								</s:iterator>
								<input type="submit" class="button primary" value="<s:text name="label.unregister"/>" />
							</s:if>
						</form>
					</div>
					<div class="register-user">
						<form action="registerUserForLab003">
							<input type="text" name="listUserRegist" size="50" placeholder="<s:text name="placeholder.user.list"/>"/> 
							<input type="submit" class="button primary" value="<s:text name="label.register"/>" />
						</form>
					</div>
				</s:if>
			</s:else>
			<s:if test="%{(!isCreate && listAllConfig != null && listAllConfig.size > 0) || isCreate}">
				<form action="configLab003" method="post" enctype="multipart/form-data">	
					<table>
						<s:hidden name="isCreate" value="%{isCreate}" />			
						<s:hidden name="reportName" value="%{numConfigLab003.reportName}" />
						<s:hidden name="driveReportName" value="%{numConfigLab003.driveReportName}" />
						<s:if test="%{!isCreate}">
							<s:hidden name="id" value="%{numConfigLab003.id}" />
							<tr>
								<s:textfield value="%{numConfigLab003.siteId}" name="siteId" key="label.siteId" />
							</tr>
						</s:if>
						<tr>
							<s:textfield value="%{numConfigLab003.siteName}" name="siteName"
								key="label.siteName" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.siteSuperficies}"
								name="siteSuperficies" key="label.siteSuperficies" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.siteInfo}" name="siteInfo"
								key="label.siteInfo" size="80" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.modulePos}"
								name="modulePos" key="label.modulePos" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleNeg}"
								name="moduleNeg" key="label.moduleNeg" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleReg}"
								name="moduleReg" key="label.moduleReg" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleEclairage}"
								name="moduleEclairage" key="label.moduleEclairage" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleBureau}"
								name="moduleBureau" key="label.moduleBureau" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleBoulangerie}"
								name="moduleBoulangerie" key="label.moduleBoulangerie" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleCvc}"
								name="moduleCvc" key="label.moduleCvc" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleTemperatureIn}"
								name="moduleTemperatureIn" key="label.moduleTemperatureIn" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleTemperatureOut}"
								name="moduleTemperatureOut" key="label.moduleTemperatureOut" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleDrive}"
								name="moduleDrive" key="label.moduleDrive" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleElectric}"
								name="moduleElectric" key="label.moduleElectric" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleGaz}"
								name="moduleGaz" key="label.moduleGaz" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleTemperatureFranis}"
								name="moduleTemperatureFranis"
								key="label.moduleTemperatureFranis" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleEnergyPreviousYear}"
								name="moduleEnergyPreviousYear"
								key="label.moduleEnergyPreviousYear" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.moduleDrivePreviousYear}"
								name="moduleDrivePreviousYear"
								key="label.moduleDrivePreviousYear" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.uriIcon}" name="uriIcon"
								key="label.uriIcon" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab003.logo}" name="logo"
								key="label.logo" size="50" />
						</tr>
						<tr>
							<td class="upload-file-label"><s:text name="label.reportName" /></td>
							<td class="upload-file">			
								<input type="text" disabled="true" size="20" value='<s:property value='%{numConfigLab003.reportName}'/>'/>						
								<div class="input-control file" data-role="input" style="width: 235px;">									
									<input type="file" name="file" accept=".pdf" placeholder="<s:text name="label.upload"/>" >
									<button class="button">
										<img src="../img/Blank_Folder.png" />
									</button>
								</div>
							</td>
						</tr>
						<tr>
							<td class="upload-file-label"><s:text name="label.driveReportName" /></td>
							<td class="upload-file">			
								<input type="text" disabled="true" size="20" value='<s:property value='%{numConfigLab003.driveReportName}'/>'/>						
								<div class="input-control file" data-role="input" style="width: 235px;">									
									<input type="file" name="driveFile" accept=".pdf" placeholder="<s:text name="label.upload"/>" >
									<button class="button">
										<img src="../img/Blank_Folder.png" />
									</button>
								</div>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<div class="tbl-button" >					
									<input type="submit" value="<s:text name="label.save"/>" class="button primary"/>
									<s:if test="%{isCreate}">
										<a class="button bg-red bg-active-darkRed fg-white" 
										href="<s:url action='cancelConfigLab003' />"><s:text name="label.cancel"/></a>
									</s:if>
									<s:else>
										<a class="button bg-red bg-active-darkRed fg-white" id="delete-subscription"
										href="<s:url action='deleteConfigLab003'>
										<s:param name="id" value="%{numConfigLab003.id}"></s:param>
										<s:param name="siteId" value="%{numConfigLab003.siteId}"></s:param>
										 </s:url>">
										<s:text name="label.delete"/></a>
									</s:else>					
								</div>		
							</td>
						</tr>
					</table>		
				</form>
			</s:if>	
			<s:else>
				<s:text name="text.nodata"/>
			</s:else>
		</div>		
	</div>
	<script src="../js/initlab.js"></script>
</body>
</html>