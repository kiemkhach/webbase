<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<s:if test="%{!isCreate}">
	<title><s:property value="%{numConfigLab007.siteName}" /></title>
</s:if>
<s:else>
	<title><s:property value="lab.name"/> - <s:text name="label.create.siteId" /></title>
</s:else>
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<script src="../js/jquery-1.11.3.min.js"></script>
<script src="../metro/js/metro.min.js"></script>
<style>
</style>
</head>
<body>
	<div class="menu">
		<a class="button bg-teal bg-active-darkTeal fg-white" href="redirectLab010IdexSite.action">Back</a> 
	</div>
	<form action="uploadReport.action" method="post" enctype="multipart/form-data">
		<table>
		<tr>
			<td>
				<s:select list="idexInstallationLst" label="Installation" listKey="idexInstallationId" listValue="name" name="installationId"></s:select>
			</td>
		</tr>
		<tr>
			<td>Report type</td>
			<td>
				<select name="fileType">
					<option value="1">Report for Cost Analyst</option>
					<option value="2">Report for Energy Analyst</option>
				</select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>	
				<input type="file" name="fileUpload" accept=".pdf" placeholder=""/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Upload" /></td>
		</tr>
		</table>
	</form>
			
	<table class="table border bordered dataTable"	id="moduleTable">
			<thead>
				<tr>
					<th>Name</th>
					<th>Report for Cost Analyst</th>
					<th>Report for Energy Analyst</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="idexInstallationLst">
					<tr>
						<td><s:property value="name" /></td>
						<td><s:property value="costReportFile" /></td>
						<td><s:property value="energyReportFile" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
			
	<script>
		
	</script>
</body>
</html>