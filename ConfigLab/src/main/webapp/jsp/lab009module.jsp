<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><s:text name="label.configModule"></s:text></title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<link href="../jquery-ui/jquery-ui.min.css" rel="stylesheet">
<style type="text/css">

.table thead th{
	text-align: center;
}
.row-input {
	padding: 10px;
}
.row-input .input-control {
	width: 100%;
}
</style>
</head>
<body>
	<div class="main padding20" style="padding-bottom: 50px">
			<div class="menu">
			<s:url action="selectSiteLab009" var="selectSiteUrl">
					<s:param name="clientId" value="clientId"></s:param>
				</s:url>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:property value="#selectSiteUrl" />"><s:text name="label.back" /></a> 
		</div>
			<h3 class="header">
				<s:text name="label.clientId"></s:text>: <s:text name="clientId"></s:text>
				<input type="hidden" id="tempClientId"
					value="<s:property value="clientId" />"/>
			</h3>
			<h4 class="sub-header">Filter</h4>
				<s:text name="label.siteName"></s:text><div class="input-control select" id="filterSiteName"></div>
				<s:text name="label.lotName"></s:text><div class="input-control select" id="filterLotName"></div>
				<s:text name="label.energyName"></s:text><div class="input-control select" id="filterEnergyName"></div>

		<table class="table border bordered dataTable"
			id="moduleTable">
			<thead>
				<tr>
					<th style="display:none">Lab009 ModuleId</th>
					<th><s:text name="label.siteId"></s:text></th>
					<th><s:text name="label.siteName"></s:text></th>
					<th><s:text name="label.moduleNumber"></s:text></th>
					<th><s:text name="label.lotName"></s:text></th>
					<th><s:text name="label.energyName"></s:text></th>
					<th style="width: 10%">
						<button id="btnCreateLab009Module" onclick="showDialog('#dialogCreateLab009Module')"
							class="cycle-button default-button">
							<span class="mif-plus"></span>
						</button></th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="numConfigLab009ModuleLst">
					<tr>
						<td style="display:none"><s:property value="lab009ModuleId" /></td>
						<td><s:property value="siteId" /></td>
						<td><s:property value="siteName" /></td>
						<td><s:property value="moduleNumber" /></td>						
						<td><s:property value="lotName" /></td>
						<td><s:property value="energyName" /></td>						
						<td><input type="hidden" id="editModuleId<s:property value='lab009ModuleId' />"
							value="<s:property value="moduleId" />"/>
						<input type="hidden" id="editLotId<s:property value='lab009ModuleId' />"
							value="<s:property value="lotId" />"/>
						<input type="hidden" id="editEnergyId<s:property value='lab009ModuleId' />"
							value="<s:property value="energyId" />"/>
							<button id="btnEditLab009Module<s:property value='lab009ModuleId' />"
							onclick="showDialog('#dialogEditLab009Module')"
								value='<s:property value="lab009ModuleId" />'
								class="btnEditLab009Module cycle-button default-button">
								<span class="mif-pencil"></span>
							</button>
							<button id="btnRemoveLab009Module<s:property value='lab009ModuleId' />"
								value='<s:property value="lab009ModuleId" />'
								class="btnRemoveLab009Module cycle-button default-button">
								<span class="mif-bin"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
<!-- Create Lab009Module Dialog -->
	<div class="padding20"
		 data-role="dialog" data-overlay="true" data-width="500"
			data-overlay-color="op-dark" data-close-button="true" id="dialogCreateLab009Module">
			<form action="createLab009Module.action" method="post">
				<input type="hidden" id="clientId"
					value="<s:property value="clientId" />" name="clientId"/>
			<div class="row-input">
				<label for="txtSite" class="sub-header"><s:text name="label.siteName" /></label>
				<div class="input-control" data-role="select" data-placeholder="Select a Module Number" >
				    <select name="numConfigLab009Module.moduleId" style="display:none">
				    <option value="" disabled selected></option>
				    <s:iterator value="moduleCSMLst">
				        <option value="<s:property value="id" />"><s:property value="numberModule" /></option>
				        </s:iterator>
				    </select>
				</div>
			</div>
			<div class="row-input">
				<label for="txtLotName" class="sub-header"><s:text name="label.lotName" /></label>
				<div class="input-control" data-role="select" data-placeholder="Select a Lot Consommation Name" >
				<select name="numConfigLab009Module.lotId" style="display:none">
					<option value="" disabled selected></option>
				    <s:iterator value="numConfigLab009LotConsommation.lab009LotConsommationsLst">
				        <option value="<s:property value="id" />"><s:property value="lotName" /></option>
				        </s:iterator> 
				        </select>
				</div>
			</div>
			<div class="row-input" >
				<label for="txtEnergyName" class="sub-header"><s:text name="label.energyName" /></label>
				<div class="input-control" data-role="select" data-placeholder="Select a Energy Type Name" >
				    <select name="numConfigLab009Module.energyId" style="display:none">
				    <option value="" disabled selected></option>
				    <s:iterator value="numConfigLab009Energy.lab009EnergyTypesLst">
				        <option value="<s:property value="id" />"><s:property value="energyName" /></option>
				        </s:iterator>
				    </select>
				</div>
			</div>
			<div class="row-input">
				<button type="submit" class="button primary">Save</button>
			</div>
			</form>
		</div>
		<!-- Edit Lab009Module Dialog -->
			<div class="padding20"
		 data-role="dialog" data-overlay="true" data-width="500"
			data-overlay-color="op-dark" data-close-button="true" id="dialogEditLab009Module">
			<form action="editLab009Module.action" method="post">
				<input type="hidden" value="<s:property value="clientId" />" name="clientId"/>
				<input type="hidden" id="selectLab009ModuleId" name="numConfigLab009Module.lab009ModuleId"/>
				<div class="row-input">
				<label for="txtSite" class="sub-header"><s:text name="label.siteName" /></label>
				<div class="input-control">
				    <select name="numConfigLab009Module.moduleId" id="selectModuleNumber" style="display:none">
				    <s:iterator value="moduleCSMLst">
				        <option value="<s:property value="id" />"><s:property value="numberModule" /></option>
				        </s:iterator>
				    </select>
				</div>
			</div>
			<div class="row-input">
				<label for="txtLotName" class="sub-header"><s:text name="label.lotName" /></label>
				<div class="input-control">
				<select name="numConfigLab009Module.lotId" id="selectLotName" style="display:none">
				    <s:iterator value="numConfigLab009LotConsommation.lab009LotConsommationsLst">
				        <option value="<s:property value="id" />"><s:property value="lotName" /></option>
				        </s:iterator> 
				        </select>
				</div>
			</div>
			<div class="row-input" >
				<label for="txtEnergyName" class="sub-header"><s:text name="label.energyName" /></label>
				<div class="input-control">
				    <select name="numConfigLab009Module.energyId" id="selectEnergyName" style="display:none">
				    <s:iterator value="numConfigLab009Energy.lab009EnergyTypesLst">
				        <option value="<s:property value="id" />"><s:property value="energyName" /></option>
				        </s:iterator>
				    </select>
				</div>
			</div>
			<div class="row-input">
				<button type="submit" class="button primary">Save</button>
			</div>
			</form>
		</div>
	</div>
	<script src="../js/jquery-1.11.3.min.js"></script>
	<script src="../js/select2.full.min.js"></script>
	<script src="../js/jquery.dataTables.min.js"></script>	
		<script src="../js/lab009Module.js"></script>	
	
	<script src="../metro/js/metro.min.js"></script>
	
</body>
</html>