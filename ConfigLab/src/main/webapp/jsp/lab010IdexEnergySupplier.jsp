<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Config Idex Energy Supplier</title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<script src="../js/jquery-1.11.3.min.js"></script>
<script src="../metro/js/metro.min.js"></script>
<style type="text/css">
.table {
	text-align: center;
	width: 60%;
}
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
	<div class="main">
		<div class="menu">
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='home' />"><s:text name="label.home" /></a> <a
				class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='redirectLab010IdexSite' />"><s:text
					name="label.configLabIdexSite" /></a>
		</div>
		<input type="hidden" id="message" value="<s:property value='mes'/>" />
		<div class="lab-name">
			<img src="../img/logos-IDEX.jpg" style="max-width: 50%">
		</div>
		<div class="config-site">
	
		<form action="uploadImgLab010IndexEnergySupplier" method="post" enctype="multipart/form-data">
			<select name="selectEnnergySupplier" id="selectIdexSupplier" onchange="getIdexSupplier(this);" style="display: block">
				<s:iterator value="listAllConfig">
					<option value="<s:property value="idexEnergySupplierId"/>">
						<s:property	value="name" />
					</option>
				</s:iterator>
			</select>
			<input type="hidden" id="idexEnergySupplierId" name="idexSiteSupplier"/>
			<input type="file" id="idexLogo" name="myFile" placeholder=""/>
			<input type="submit" value="Upload" />
		</form>
			
		<table class="table border bordered dataTable"	id="moduleTable">
				<thead>
					<tr>
						<th style="display:none">Lab010IdexEnergySupplier</th>
						<th><s:text name="ID"></s:text></th>
						<th><s:text name="Name"></s:text></th>
						<th><s:text name="Logo"></s:text></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="numConfigLab010IdexSupplierLst">
						<tr>
							<td style="display:none"><s:property value="numIdexSupplier" /></td>
							<td><s:property value="idexEnergySupplierId" /></td>
							<td><s:property value="name" /></td>
							<td><s:property value="logo" /></td>
							<td>
								<button type="button"
									id="btnRemoveLogo<s:property value='idexEnergySupplierId' />"
									value='<s:property value="idexEnergySupplierId" />'
									class="btnRemoveLogo cycle-button large-button"
									onclick="window.location.href='deleteImgLab010IndexEnergySupplier.action?energySupplierId=<s:property value='idexEnergySupplierId'/>'">
									<span class="mif-bin mif-ani-hover-pass"></span>
								</button>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		
		</div>
		<script>
			var idexEnergySupplierId = document.getElementById("selectIdexSupplier").value;
			$('#idexEnergySupplierId').val(idexEnergySupplierId);
			function getIdexSupplier(sel) {
				$('#idexEnergySupplierId').val(sel.value);
			}
		</script>
		<script src="../js/jquery-1.11.3.min.js"></script>
		<script src="../js/select2.full.min.js"></script>
		<script src="../js/jquery.dataTables.min.js"></script>
		<script src="../js/lab010Idex.js"></script>

		<script src="../metro/js/metro.min.js"></script>
</body>
</html>