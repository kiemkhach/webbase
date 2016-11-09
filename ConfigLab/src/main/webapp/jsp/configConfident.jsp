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
/* 	select,input{ */
/* 		width: 80%; */
/* 	} */
/* 	#btnSaveIdexConfig{ */
/* 		width: 100px !important; */
/* 	} */
</style>
</head>
<body>
	<div class="menu">
		<a class="button bg-teal bg-active-darkTeal fg-white" href="redirectLab010IdexSite.action">Back</a> 
	</div>

	<s:select class="input-control select" list="installationLst"
				listKey="idexInstallationId" listValue="name" name="installationId"
				value="installationId" label="Installation"
				cssClass="select-tag" cssStyle="margin-left: 0px"/>
					
	<table class="table border bordered dataTable"	id="moduleTable">
		<thead>
			<tr>
				<th>Counter Name</th>
				<th>Coefficient énergétique</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="couteurLst">
				<tr>
					<td><div><s:property value="name" /></div></td>
					<td><input class="input-confident" couterId="<s:property value="idexCounterId" />" value="<s:property value="coefficientEnergetique" />" /></td>
				</tr>
			</s:iterator>
			<tr>
				<td></td>
				<td><input type="submit"
						id="btnSaveConfig" value="<s:text name="label.save"/>"
						class="button primary"></td>
			</tr>
		</tbody>
	</table>
			
	<script>
		$('#installationId').change(function(){
			var install = '';
			if($(this).val() != null){
				install =$(this).val(); 
			}
			var cp = '';
			if($("#counterId").val() != null){
				cp = $("#counterId").val();
				
			}
			window.location.href="getConfigConfident.action?installationId="+install+"&counterId="+cp;
		});
		
		$('#btnSaveConfig').click(function(){
			var isValidate = true;
			$('.input-confident').each(function(){
				if(isNaN($(this).val())){
					alert($(this).val() + ' Not A Number');
					isValidate = false;
					return;
				}
			});
			if(isValidate){
				$('.input-confident').each(function(){
					var counterID = $(this).attr('couterId');
					var coefficient = $(this).val();
					$.ajax({
						 url : 'saveConfigConfident.action?counterId=' +counterID+ '&coefficient='+coefficient,
						type : 'GET',
						async : false,
						success : function(json) {
						},
					});				
				});
				alert('success!');
			}
			
		});
		
		
		
	</script>
</body>
</html>