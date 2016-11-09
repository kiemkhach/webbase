<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Config Caloon</title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<style type="text/css">

</style>
</head>

<body>
	<div class="main" style="margin-left: 15px">
		<div class="menu">
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='home' />"><s:text name="label.home" /></a> 
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='syndicConfig' />"><s:text name="label.syndic" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='residentConfig' />"><s:text name="label.resident" /></a>
<!-- 			<a class="button bg-teal bg-active-darkTeal fg-white" -->
<%-- 				href="<s:url action='rangeConfig' />"><s:text name="label.range" /></a> --%>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='temperatureConfig' />"><s:text name="label.configTemperature" /></a>
		</div>
		<div class = "loadUer">
			<div class="createUser">
				<a class="" href="<s:url action='createUserCaloon' />"
					style="margin-left: 15px;"><s:text name="label.createUser" /></a>
			</div>
			<form action="saveUserCaloon" method="post">
				<input type="hidden" id="userCaloonId"
				value="${numconfigLab011Caloon.id}" />
			<s:if test="%{listUserCaloon != null && listUserCaloon.size > 0}">
			<s:select class="input-control select" list="listUserCaloon"
				listKey="id" listValue="userName" name="numconfigLab011Caloon.id"
				value="%{numconfigLab011Caloon.id}"
				cssClass="select-tag"/>
				<table>
					<tr>
						<td><s:textfield 
								id = "txtFirstName" 
								value="%{numconfigLab011Caloon.firstName}"
								name="numconfigLab011Caloon.firstName" 
								label="%{getText('label.firstName')}"></s:textfield></td>
					</tr>
					<tr>
						<td><s:textfield 
								id = "txtLastName" 
								value="%{numconfigLab011Caloon.lastName}"
								name="numconfigLab011Caloon.lastName" 
								label="%{getText('label.lastName')}"></s:textfield></td>
					</tr>
					<tr>
						<td><s:textfield 
								id = "txtUserName" 
								value="%{numconfigLab011Caloon.userName}"
								name="numconfigLab011Caloon.userName" 
								label="%{getText('label.userName')}"></s:textfield></td>
					</tr>
					<tr>
						<td><s:textfield 
								id = "txtPassWord" 
								value="%{numconfigLab011Caloon.password}"
								name="numconfigLab011Caloon.password" 
								label="%{getText('label.pass')}"></s:textfield></td>	
					</tr>
					<s:if test="%{numconfigLab011Caloon.status == 0}">
						<tr>
							<td><s:label value="Syndic"
								label="%{getText('label.status')}"></s:label> </td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<td><s:label value="Résident"
								label="%{getText('label.status')}"></s:label> </td>
						</tr>
					</s:else>
					<s:if test="%{numconfigLab011Caloon.status == 0}">
						<tr>
							<td>
							<s:if test="%{(listResidentBySyndicId != null && listResidentBySyndicId.size > 0)}">
								<s:checkboxlist list="listResidentBySyndicId"
								value="listResidentChecked" label="Appartement Number"
									listKey="id" listValue="appartementNumber" name="ischeck"
									></s:checkboxlist>
							</s:if>
							<s:if test="%{(listCaloonResident != null && listCaloonResident.size > 0)}">
								<s:checkboxlist list="listCaloonResident"
									listKey="id" listValue="appartementNumber" name="residents"
									label="Appartement Number Available"
									></s:checkboxlist>
							</s:if>
							</td>
						</tr>
					</s:if>
					<tr>
							<td></td>
							<td>
								<div class="tbl-button">
									<input type="submit" id="btnSaveBean"
										value="<s:text name="label.save"/>" class="button primary" />
										<a class="button bg-red bg-active-darkRed fg-white"
											id="delete-subscription"
											href="
										<s:url action='deleteUserCaloon'>
											<s:param name="userId" value="%{numconfigLab011Caloon.id}"></s:param>
									 	</s:url>">
											<s:text name="label.delete" />
										</a>
								</div>
							</td>
						</tr>
				</table>
				</s:if>
			</form>
		</div>
	</div>
<script type="text/javascript">
$('#numconfigLab011Caloon_id').change(function () {
	window.location.href = 'redirectCaloon.action?userId='+$(this).val();
});
</script>
</body>
</html>