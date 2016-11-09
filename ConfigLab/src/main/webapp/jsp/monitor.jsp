<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><s:text name="label.title.monitor" /></title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<script src="../js/jquery-1.11.3.min.js"></script>
<script src="../metro/js/metro.min.js"></script>
</head>
<body>
	<div class="main">
		<div class="menu">
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='home' />"><s:text name="label.home" /></a> <a
				class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
		</div>
		<input type="hidden" id="message" value="<s:property value='mes'/>" />
		<br>
		<div class="sub-leader">
			<s:text name="label.config.monitor" />
		</div>
		<form action="saveMonitor">
			<table>
				<tr>
					<s:textfield name="userName" value="%{monitorBean.userName}"
						key="label.username" />
				</tr>
				<tr>
					<s:password name="passWord" value="%{monitorBean.passWord}"
						key="label.password" showPassword="true" />
				</tr>
				<tr>
					<s:textfield name="timeoutGatewayLcpc"
						value="%{monitorBean.timeoutGatewayLcpc}"
						key="label.timeoutGatewayLcpc" />
				</tr>
				<tr>
					<s:textfield name="timeoutGatewayDevice"
						value="%{monitorBean.timeoutGatewayDevice}"
						key="label.timeoutGatewayDevice" />
				</tr>
				<tr>
					<s:textfield name="timeoutModuleDevice"
						value="%{monitorBean.timeoutModuleDevice}"
						key="label.timeoutModuleDevice" />
				</tr>
				<tr>
					<td></td>
					<td>
						<div class="tbl-button" >
							<input type="submit" value="<s:text name="label.save"/>" class="button primary" />
						</div>
					</td>
				</tr>
			</table>			
		</form>
	</div>
	<script src="../js/initlab.js"></script>
</body>
</html>