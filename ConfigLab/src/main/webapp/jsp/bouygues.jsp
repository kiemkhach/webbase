<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<s:if test="%{!isCreate}">
	<title><s:property value="%{numConfigLab002.siteName}" /></title>
</s:if>
<s:else>
	<title><s:property value="lab.name"/> - <s:text name="label.create.siteId" /></title>
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
			<a class="button bg-teal bg-active-darkTeal fg-white" href="<s:url action='home' />"><s:text name="label.home" /></a> 
			<a class="button bg-teal bg-active-darkTeal fg-white" href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
		</div>
		<s:if test="%{!isCreate}"> 
			<a class="" href="<s:url action='createConfigLab002' />" style="margin-left: 15px;"><s:text name="label.create.siteId" /></a>		
			<s:if test="%{listAllConfig != null && listAllConfig.size > 0}">
				<form action="selectSiteLab002">
					<s:select class="input-control select" list="listAllConfig"
						listKey="siteId" listValue="siteName" name="siteId"
						value="%{numConfigLab002.siteId}" onchange="this.form.submit()" cssClass="select-tag"/>
				</form>
			</s:if>
		</s:if>
		<input type="hidden" id="message" value="<s:property value='mes'/>" />
		<div class="lab-name">			
			<img src="../img/logos-contruction.png">
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
						<s:property value="%{numConfigLab002.siteId}" />
					</div>
					<div class="unregister-user">
						<form action="unregisterUserForLab002">
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
						<form action="registerUserForLab002">
							<input type="text" name="listUserRegist" size="50" placeholder='<s:text name="placeholder.user.list"/>'/> 
							<input type="submit" class="button primary" value="<s:text name="label.register"/>" />
						</form>
					</div>
				</s:if>
			</s:else>
			<s:if test="%{(!isCreate && listAllConfig != null && listAllConfig.size > 0) || isCreate}">
				<form action="configLab002" method="post" enctype="multipart/form-data">	
					<table>
						<s:hidden name="isCreate" value="%{isCreate}" />
						<s:hidden name="reportName" value="%{numConfigLab002.reportName}" />
						<s:if test="%{!isCreate}">
							<s:hidden name="id" value="%{numConfigLab002.id}" />
							<tr>
								<s:textfield value="%{numConfigLab002.siteId}" name="siteId" key="label.siteId" />
							</tr>
						</s:if>
						<tr>
							<s:textfield value="%{numConfigLab002.siteName}" name="siteName"
								key="label.siteName" size="50" />
						</tr>							
						<tr>
							<s:if
								test="%{numConfigLab002.gruesModuleId == null || numConfigLab002.gruesModuleId == ''}">
								<td><s:text name="label.gruesModuleId" /></td>
								<td><input type="text" name="gruesModuleId" placeholder='<s:text name="placeholder.module.list"/>' size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield value="%{numConfigLab002.gruesModuleId}" name="gruesModuleId" key="label.gruesModuleId" size="50" />
							</s:else>
						</tr>									
						<tr>
							<s:if
								test="%{numConfigLab002.piedModuleId == null || numConfigLab002.piedModuleId == ''}">
								<td><s:text name="label.piedModuleId" /></td>
								<td><input type="text" name="piedModuleId" placeholder='<s:text name="placeholder.module.list"/>' size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield value="%{numConfigLab002.piedModuleId}" name="piedModuleId" key="label.piedModuleId"  size="50" />
							</s:else>
						</tr>						
						<tr>
							<s:if
								test="%{numConfigLab002.cantonModuleId == null || numConfigLab002.cantonModuleId == ''}">
								<td><s:text name="label.cantonModuleId" /></td>
								<td><input type="text" name="cantonModuleId" placeholder='<s:text name="placeholder.module.list" />' size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield value="%{numConfigLab002.cantonModuleId}" name="cantonModuleId" key="label.cantonModuleId" size="50" />
							</s:else>
						</tr>	
						<tr>
							<s:textfield value="%{numConfigLab002.numberGrues}"
								name="numberGrues" key="label.numberGrues" />
						</tr>	
						<tr>
							<s:textfield value="%{numConfigLab002.numberPied}"
								name="numberPied" key="label.numberPied" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab002.numberCanton}"
								name="numberCanton" key="label.numberCanton" />
						</tr>					
						<tr>
							<s:textfield value="%{numConfigLab002.startDate}"
								name="startDate" key="label.startDate" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab002.numberPermit}"
								name="numberPermit" key="label.numberPermit"  />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab002.uriIcon}" name="uriIcon"
								key="label.uriIcon" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab002.logo}" name="logo"
								key="label.logo" size="50" />
						</tr>
						<tr>
							<td class="upload-file-label"><s:text name="label.reportName" /></td>
							<td class="upload-file">			
								<input type="text" disabled="true" size="20" value='<s:property value='%{numConfigLab002.reportName}'/>'/>						
								<div class="input-control file" data-role="input" style="width: 235px;">									
									<input type="file" name="file" accept=".pdf" placeholder="<s:text name="label.upload"/>" >
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
										href="<s:url action='cancelConfigLab002' />"><s:text name="label.cancel"/></a>
									</s:if>
									<s:else>
										<a class="button bg-red bg-active-darkRed fg-white" id="delete-subscription"
										href="<s:url action='deleteConfigLab002'>
										<s:param name="id" value="%{numConfigLab002.id}"></s:param>
										<s:param name="siteId" value="%{numConfigLab002.siteId}"></s:param>
										</s:url>">
										<s:text name="label.delete"/>
										</a>
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