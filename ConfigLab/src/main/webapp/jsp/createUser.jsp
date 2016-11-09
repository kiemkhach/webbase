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
				href="<s:url action='redirectCaloon' />"><s:text name="label.userCaloon" /></a>
		</div>
		<div class = "createUser">
			<form action="saveConfigLab011" method="post">
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
						<td><s:radio 
								id= "" list="#{'0':'Syndic','1':'Résident'}"
								value="%{numconfigLab011Caloon.status}"	
								name="numconfigLab011Caloon.status" 
								label="%{getText('label.status')}"
								onchange="changeRadio(this.value)"></s:radio></td>
					</tr>
					<tr>	
						<td>
							<s:if test="%{(listCaloonResident != null && listCaloonResident.size > 0)}">
								<s:checkboxlist list="listCaloonResident"
									listKey="id" listValue="appartementNumber" name="residents"/>
							</s:if> 
						</td>
					</tr>
					<tr>
						<td>
							<s:textfield
 								id = "txtselectResident" 
 								value="%{numconfigLab011Caloon.appartementNumber}" 
 								name="numconfigLab011Caloon.appartementNumber"  
 								label="%{getText('label.logement')}"></s:textfield> 
						</td>
					</tr>
					<tr><td><s:textfield 
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
					<tr>
						<td><a class="tbl-button"> <input type="submit"
								id="btnSaveConfigLab011" value="<s:text name="label.save"/>"
								class="button primary">
						</a></td>		
					</tr>
				</table>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	$('#txtselectResident').parent().parent().css('display','none');
	$('#__multiselect_residents').parent().parent().css('display','none');
		function changeRadio(value){
			if(value == 0){
				$('#__multiselect_residents').parent().parent().css('display','table-row');
				$('#txtselectResident').parent().parent().css('display','none');
			}
			if(value == 1){
				$('#txtselectResident').parent().parent().css('display','table-row');
				$('#__multiselect_residents').parent().parent().css('display','none');
			}
		}
	</script>
	<script src="../js/jquery-1.11.3.min.js"></script>
</body>
</html>