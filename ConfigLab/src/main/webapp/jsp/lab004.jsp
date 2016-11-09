<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<s:if test="%{!isCreate}">
	<title><s:property value="%{numConfigLab004.siteName}" /></title>
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
			<a class="" href="<s:url action='createConfigLab004' />" style="margin-left: 15px;"><s:text name="label.create.siteId" /></a>	
			<s:if test="%{listAllConfig != null && listAllConfig.size > 0}">
				<form action="selectSiteLab004">
					<s:select class="input-control select" list="listAllConfig"
						listKey="siteId" listValue="siteName" name="siteId"
						value="%{numConfigLab004.siteId}" onchange="this.form.submit()" cssClass="select-tag"/>
				</form>
			</s:if>
		</s:if>
		<input type="hidden" id="message" value="<s:property value='mes'/>" />
		<div class="lab-name">
			<img src="../img/logos-yoplait.png">
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
						<s:property value="%{numConfigLab004.siteId}" />
					</div>
					<div class="unregister-user">
						<form action="unregisterUserForLab004">
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
						<form action="registerUserForLab004">
							<input type="text" name="listUserRegist" size="50" placeholder="<s:text name="placeholder.user.list"/>"/> 
							<input type="submit" class="button primary" value="<s:text name="label.register"/>" />
						</form>
					</div>
				</s:if>
			</s:else>
			<s:if test="%{(!isCreate && listAllConfig != null && listAllConfig.size > 0) || isCreate}">
				<form action="configLab004">
					<table>
						<s:hidden name="isCreate" value="%{isCreate}" />						
						<s:if test="%{!isCreate}">
							<s:hidden name="id" value="%{numConfigLab004.id}" />
							<tr>
								<s:textfield value="%{numConfigLab004.siteId}" name="siteId" key="label.siteId" />
							</tr>
						</s:if>					
						<tr>
							<s:textfield value="%{numConfigLab004.siteName}" name="siteName"
								key="label.siteName" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab004.siteInfo}" name="siteInfo"
								key="label.siteInfo" size="80" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab004.uriIcon}" name="uriIcon"
								key="label.uriIcon" size="50" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab004.cible}" name="cible"
								key="label.cible" />
						</tr>
						<tr>
							<s:textfield value="%{numConfigLab004.unitPrice}"
								name="unitPrice" key="label.unitPrice" />
						</tr>						
						<tr>
							<td></td>
							<td>
								<div class="tbl-button" >					
									<input type="submit" value="<s:text name="label.save"/>" class="button primary"/>
									<s:if test="%{isCreate}">
										<a class="button bg-red bg-active-darkRed fg-white" 
										href="<s:url action='cancelConfigLab004' />"><s:text name="label.cancel"/></a>
									</s:if>	
									<s:else>
										<a class="button bg-red bg-active-darkRed fg-white" id="delete-subscription"
										href="<s:url action='deleteConfigLab004'>
										<s:param name="id" value="%{numConfigLab004.id}"></s:param>
										<s:param name="siteId" value="%{numConfigLab004.siteId}"></s:param>
										 </s:url>">
										<s:text name="label.delete"/></a>
									</s:else>				
								</div>
							</td>	
						</tr>
					</table>					
				</form>			
				<s:if test="%{!isCreate}">
					<s:if
						test="%{listAllConfigLine != null && listAllConfigLine.size > 0}">						
						<form action="configLab004Line"  method="post" enctype="multipart/form-data">		
							<table>
								<s:iterator value="listAllConfigLine" status="rows" var="items">
									<s:if test="%{#rows.index == 0}">
										<s:label key="label.type1" value="" id="labelType" />
									</s:if>
									<s:if test="%{#rows.index == 1}">
										<s:label key="label.type2" value="" id="labelType" />
									</s:if>
									<s:if test="%{#rows.index == 2}">
										<s:label key="label.type3" value="" id="labelType" />
									</s:if>
									<tr>
										<s:hidden name="listAllConfigLine[%{#rows.index}].id" />
										<s:hidden
											name="listAllConfigLine[%{#rows.index}].configLab004Id" />
										<s:hidden name="listAllConfigLine[%{#rows.index}].type" />
									</tr>
									<tr>
										<s:textfield name="listAllConfigLine[%{#rows.index}].logoType"
											key="label.logoType" size="50" />
									</tr>
									<tr>
										<s:textfield name="listAllConfigLine[%{#rows.index}].address"
											key="label.address" size="50" />
									</tr>									
									<tr>										
										<td class="upload-file-label"><s:text name="label.reportName" /></td>										
										<td class="upload-file">
											<s:hidden name="listAllConfigLine[%{#rows.index}].reportName" />
											<input type="text" disabled="true" size="20" value='<s:property value='reportName'/>'/>						
											<div class="input-control file" data-role="input" style="width: 235px;">
												<s:if test="%{#rows.index == 0}">
													<input type="file" name="file1" accept=".pdf" placeholder="<s:text name="label.upload"/>" >
												</s:if>
												<s:if test="%{#rows.index == 1}">
													<input type="file" name="file2" accept=".pdf" placeholder="<s:text name="label.upload"/>" >
												</s:if>
												<s:if test="%{#rows.index == 2}">
													<input type="file" name="file3" accept=".pdf" placeholder="<s:text name="label.upload"/>" >
												</s:if>								
												<button class="button">
													<img src="../img/Blank_Folder.png" />
												</button>
											</div>
										</td>
									</tr>
								</s:iterator>		
									<tr>
										<td></td>
										<td>
											<div class="tbl-button" >	
												<input type="submit" value="<s:text name="label.save"/>" class="button primary"/>
											</div>
										</td>
									</tr>					
							</table>							
						</form>
					</s:if>
					<s:else>
						<div><s:text name="text.nodata"/></div>
					</s:else>
				</s:if>	
			</s:if>	
			<s:else>
				<s:text name="text.nodata"/>
			</s:else>		
		</div>		
	</div>
	<script src="../js/initlab.js"></script>
</body>
</html>