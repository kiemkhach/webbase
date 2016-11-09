<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Config Caloon</title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<link href="../jquery-ui/jquery-ui.min.css" rel="stylesheet">
<body>
	<div class="main" style="margin-left: 15px">
		<div class="menu">
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='home' />"><s:text name="label.home" /></a> 
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='redirectCaloon' />"><s:text name="label.userCaloon" /></a>
		</div>
		<div class = "configTemp">
		
			<form action="saveConfigTmp" method="post">
				<table>
					<tr>
						<td><s:textfield 
								id = "txtTempAppartement" 
								value="%{numConfigLab011Temperature.tempAppartement}"
								name="numConfigLab011Temperature.tempAppartement" 
								label="%{getText('label.tempAppartement')}"></s:textfield></td>
					</tr>
					<tr>
						<td><s:textfield 
								id = "txtTempMeteo" 
								value="%{numConfigLab011Temperature.tempMeteo}"
								name="numConfigLab011Temperature.tempMeteo" 
								label="%{getText('label.tempMeteo')}"></s:textfield></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" class="button primary"
							id="btnSaveTemp" value="Save"> </buton></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>