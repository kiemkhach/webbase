<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Config Idex Site</title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<style type="text/css">
.table{
	text-align: center;
}
.table thead th{
	text-align: center;
}

</style>
</head>
<body>
	<div class="main padding20">
		<div class="menu">
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='home' />"><s:text name="label.home" /></a> <a
				class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='configLabIdexEnergySupplier' />"><s:text
					name="label.configLabIdexEnergySupplier" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='configLabIdex' />"><s:text
					name="label.configIdex" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='redirectLab010SubCategory.action' />"><s:text
					name="label.subCategory" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='redirectLab010Releves' />">Idex Releves</a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='getConfigConfident.action' />">Coefficient énergétique</a>
				<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='getUploadPage.action' />">Upload Report</a>
		</div>
		<h3 class="header">Select Installation</h3>
		<div class="grid">
                                <div class="row cells3">
                    			<div class="cell">
                        		<div class="input-control full-size">
 									<select id="selectIdexInstallation">
										<s:iterator value="idexInstallationList">
											<option value="<s:property value="idexInstallationId" />"><s:property
													value="name" /></option>
										</s:iterator>
									</select>
								</div>
                    </div>
                 </div>
                    				
		</div>
		<input type="hidden" id="message" value="<s:property value='mes'/>" />
		<div class="lab-name">
			<img src="../img/logos-IDEX.jpg" style="max-width: 50%">
		</div>
		<div class="config-site">
			<form action="uploadImgLab010IndexSite" method="post" enctype="multipart/form-data">
				<select name="selectSite" id="selectIdexSite" onchange="getIdexSiteId(this);" style="display: block">
					<s:iterator value="listAllConfig">
						<option value="<s:property value="idexSiteId"/>">
							<s:property	value="name" />
						</option>
					</s:iterator>
				</select>
				<input type="hidden" id="idexSiteId" name="siteIdIdex" value=/>
				<input type="file" id="idexLogo" name="myFile" placeholder=""/>
				<input type="submit" id="submit-idex-site" onclick="return validateFile(this)" value="Upload" />
			</form>
		<table class="table border bordered dataTable"	id="moduleTable">
				<thead>
					<tr>
						<th><s:text name="ID"></s:text></th>
						<th><s:text name="Name"></s:text></th>
						<th><s:text name="Logo"></s:text></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="numConfigLab010IdexSiteLst" >
						<tr>
							<td><s:property value="idexSiteId"/></td>
							<td><s:property value="name" /></td>
							<td><s:property value="logo" /></td>
							<td>
								<button type="button"
									id="btnRemoveLogo<s:property value='idexSiteId' />"
									value='<s:property value="idexSiteId" />'
									class="btnRemoveLogo cycle-button large-button"
									onclick="window.location.href='deleteImgLab010IndexSite.action?siteIdIdex=<s:property value='idexSiteId' />'">
									<span class="mif-bin mif-ani-hover-pass"></span>
								</button>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<script>
			var idexSiteId = document.getElementById("selectIdexSite").value;
			$('#idexSiteId').val(idexSiteId);
			function getIdexSiteId(sel) {
				$('#idexSiteId').val(sel.value);
			}
			
		</script>
		<script src="../js/jquery-1.11.3.min.js"></script>
		<script src="../js/select2.full.min.js"></script>
		<script src="../js/jquery.dataTables.min.js"></script>
		<script src="../js/lab010Idex.js"></script>
		<script src="../metro/js/metro.min.js"></script>
</body>
</html>