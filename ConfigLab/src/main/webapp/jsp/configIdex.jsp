<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CofigIdex</title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<link href="../jquery-ui/jquery-ui.min.css" rel="stylesheet">
</head>
<body>
<div class="main">
	<div class="menu">
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='home' />"><s:text name="label.home" /></a> <a
				class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='configLabIdexEnergySupplier' />"><s:text
					name="label.configLabIdexEnergySupplier" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='redirectLab010IdexSite' />"><s:text
					name="label.configLabIdexSite" /></a>
	</div>
	<div class="config-site">
		<form action="saveIdexConfig" method="post">
			<s:select class="input-control select" list="listAllIdexInstallation"
				listKey="idexInstallationId" listValue="name" name="numConfigLab010Idex.idexInstallationId"
				value="%{numConfigLab010Idex.idexInstallationId}"
				cssClass="select-tag" cssStyle="margin-left: 0px"/>
			<table>
				<tr>
					<td><s:select id="target"
							label="%{getText('lable.unitIdexConsomation')}"
							list="#{'1':'KWH', '2':'MWH','3':'GWH'}"
							name="numConfigLab010Idex.unitConsommation"
							value="%{numConfigLab010Idex.unitConsommation}" /></td>
				</tr>
				<tr>
					<td><s:select id="target"
							label="%{getText('label.unitIdexMontal')}"
							list="#{'1':'E', '2':'KE', '3':'ME'}"
							name="numConfigLab010Idex.unitMontal"
							value="%{numConfigLab010Idex.unitMontal}" /></td>
				</tr>
				<tr>
					<td><s:checkbox 
							id = "checkConsommation" 
							value="%{numConfigLab010Idex.isConsommationFirst}"
							name="numConfigLab010Idex.isConsommationFirst" 
							label="%{getText('label.checkConsommation')}"></s:checkbox>
				</tr>
				<td><a class="tbl-button"> <input type="submit"
						id="btnSaveIdexConfig" value="<s:text name="label.save"/>"
						class="button primary">
				</a></td>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript">
$('#numConfigLab010Idex_idexInstallationId').change(function () {
	window.location.href = 'configLabIdex.action?installationId='+$(this).val();
});
</script>
</body>
</html>