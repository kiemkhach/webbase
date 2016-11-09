<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><s:text name = "label.title.perial"></s:text></title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<link href="../jquery-ui/jquery-ui.min.css" rel="stylesheet">
</head>
<body>
	<div class="main">
		<input type="hidden" id="message" value="<s:property value='mes'/>" />
		<div class="menu">
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='home' />"><s:text name="label.home" /></a> <a
				class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
		</div>
		<%-- <div class="lab-name">
			<img src="../img/logos-boilers-monitor.png" height="200" width="200">
			<div class="text-lab-name">
				<s:property value="%{lab.name}" />
			</div>
		</div> --%>

		<div class="config-site">
			<form data-role="validator" action="registerPerial" method="post">
			
					<table>
						<div class="sub-leader">
							<s:text name="label.create.siteId" />
						</div>
						<tr>
							<s:textfield data-validate-func="required"
								data-validate-hint="You must insert Client Id" name="clientId"
								key="label.clientId" size="50" />
						</tr>
						<tr>
							<s:textfield data-validate-func="required"
								data-validate-hint="You must insert Client Name"
								name="clientName" key="label.clientName" size="50" />
						</tr>
						<tr>
							<td><s:select id="target"
								label="%{getText('lable.unitEmissions')}"
								list="#{'t':'t', 'kt':'kt'}"
								name="unitEmissions"
								value="%{numConfigLab009Perial.unitEmissions}" /></td>
						</tr>
						<tr>
							<td><s:select id="target"
									label="%{getText('label.unitConsommation')}"
									list="#{'1':'kWh', '2':'MWh', '3':'GWh'}"
									name="unitConsommation"
									value="%{numConfigLab009Perial.unitConsommation}"
									/></td>
						</tr>
						<tr>
							<td><s:select id="target"
									label="%{getText('label.unitMontal')}"
									list="#{'1':'€', '2':'k€', '3':'M€'}"
									name="unitmontal"
									value="%{numConfigLab009Perial.unitMontal}"
									 /></td>
						</tr>
						<tr>
							<td>
								<a class="tbl-button">
									<input type="submit" id="btnSaveLab009EnergyTypes"
										value="<s:text name="label.save"/>" class="button primary"
										href="<s:url action='saveUnitCO2'/>">
								</a>
								<a class="button bg-red bg-active-darkRed fg-white"
											href="<s:url action='redirectLab009' />"><s:text
												name="label.cancel" /></a>
							</td>
						</tr>
					</table>
			
			</form>
		</div>
		
		<script src="../js/jquery-1.11.3.min.js"></script>
		<script src="../metro/js/metro.min.js"></script>
		<script src="../jquery-ui/jquery-ui.min.js"></script>
		<script src="../js/lab009EnergyTypes.js"></script>
</body>
</html>