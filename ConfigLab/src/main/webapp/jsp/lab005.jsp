<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<s:if test="%{!isCreate}">
	<title><s:property value="%{numConfigLab005.siteName}" /></title>
</s:if>
<s:else>
	<title><s:property value="lab.name" /> - <s:text name="label.create.siteId" /></title>
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
			<a class="" href="<s:url action='createConfigLab005' />" style="margin-left: 15px;"><s:text name="label.create.siteId" /></a>	
			<s:if test="%{listAllConfig != null && listAllConfig.size > 0}">
				<form action="selectSiteLab005">
					<s:select class="input-control select" list="listAllConfig"
						listKey="siteId" listValue="siteName" name="siteId"
						value="%{numConfigLab005.siteId}" onchange="this.form.submit()" cssClass="select-tag"/>
				</form>
			</s:if>
		</s:if>		
		<input type="hidden" id="message" value="<s:property value='mes'/>" />
		<div class="lab-name" >
			<img src="../img/logos-Grand-oulin.png">
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
						<s:property value="%{numConfigLab005.siteId}" />
					</div>
					<div class="unregister-user">
						<form action="unregisterUserForLab005">
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
						<form action="registerUserForLab005">
							<input type="text" name="listUserRegist" size="50" placeholder="<s:text name="placeholder.user.list"/>"/> 
							<input type="submit" class="button primary" value="<s:text name="label.register"/>" />
						</form>
					</div>
				</s:if>
			</s:else>
			<s:if test="%{(!isCreate && listAllConfig != null && listAllConfig.size > 0) || isCreate}">
				<form action="configLab005" method="post" enctype="multipart/form-data">
					<table>
						<s:hidden name="isCreate" value="%{isCreate}" />									
						<s:hidden name="reportName" value="%{numConfigLab005.reportName}" />
						<s:if test="%{!isCreate}">
							<s:hidden name="id" value="%{numConfigLab005.id}" />
							<tr>
								<s:textfield value="%{numConfigLab005.siteId}" name="siteId" key="label.siteId" />
							</tr>
						</s:if>	
						<tr>
						<s:textfield value="%{numConfigLab005.siteName}" name="siteName"
							key="label.siteName" size="50" />
						</tr>
						<tr>
						<s:textfield value="%{numConfigLab005.uriIcon}" name="uriIcon"
							key="label.uriIcon" size="50" />
						</tr>
						<tr>
							<td class="upload-file-label"><s:text name="label.reportName" /></td>
							<td class="upload-file">			
								<input type="text" disabled="true" size="20" value='<s:property value='%{numConfigLab005.reportName}'/>'/>						
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
									<input type="submit" value="<s:text name="label.save"/>" class="button primary" />
									<s:if test="%{isCreate}">
										<a class="button bg-red bg-active-darkRed fg-white" href="<s:url action='cancelConfigLab005' />"><s:text name="label.cancel" /></a>
									</s:if>
									<s:else>
										<a class="button bg-red bg-active-darkRed fg-white"	id="delete-subscription" href="
										<s:url action='deleteConfigLab005'>
										<s:param name="id" value="%{numConfigLab005.id}"></s:param>
										<s:param name="siteId" value="%{numConfigLab005.siteId}"></s:param>
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
				<s:text name="text.nodata"/>
			</s:else>
		</div>
	</div>
	<script src="../js/initlab.js"></script>
</body>