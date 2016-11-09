<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Config Lab</title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<style type="text/css">
#table-container {
	display: -webkit-box;
	display: -moz-box;
	display: -ms-flexbox;
	display: -webkit-flex;
	display: flex;
	-webkit-justify-content: center;
	justify-content: center;
	-webkit-align-items: center;
	align-items: center;
}

#detailLabDialog {
	display: -webkit-flex; /* Safari */
	display: flex;
	display: -ms-flexbox; /* TWEENER - IE 10 */
	flex-direction: column;
	-webkit-flex-direction: column;
	justify-content: space-around;
	-webkit-justify-content: space-around;
	align-items: center;
	-webkit-align-items: center;
}

.row-input {
	width: 90%;
	margin-bottom: 10px;
}

.row-input .input-control {
	width: 100%;
}
.row-input .input-control input{
	height: 2rem;
}
</style>
</head>
<body>
	<div id="wrapper" class="grid">
		<div id="table-container" class="row cells12">
			<div class="cell colspan10">
			<h2>Lab Management</h2>
			<button id="btnCreate" class="button primary"><span class="mif-plus"></span> Create</button>
				<table id="tblLab" class="table striped hovered border">
					<thead>
						<tr>
							<th>Id</th>
							<th>Name</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="listLabs">
							<tr>
								<td><s:property value="id" /></td>
								<td><s:property value="name" /></td>
								<td class="toolbar">
									<div class="toolbar-section">
										<button id='<s:property value="id"/>' onclick="editLab(this)"
											class="toolbar-button">
											<span class="mif-pencil mif-ani-hover-shake"></span>
										</button>
										<button id='<s:property value="id" />'
											onclick="deleteLab(this)" class="toolbar-button">
											<span class="mif-bin mif-ani-hover-shake"></span>
										</button>
									</div>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div data-role="dialog" data-overlay="true"
		data-overlay-color="op-dark" data-width="65%" data-height="60%"
		data-close-button="true" id="detailLabDialog">
		<div class="flex-grid">
			<div class="row flex-just-sa">
				<div class="cell size5">
					<div class="row-input">
						<div class="input-control text">
							<input type="text" id="txtClientId" disabled>
						</div>
					</div>
					<div class="row-input">
						<label for="txtLabName">Name</label>
						<div class="input-control modern text">
							<input type="text" id="txtLabName"
								placeholder="Enter lab name here" />
							<span class="informer">Please enter lab name here</span>
						</div>
					</div>
					<div class="row-input">
						<label for="txtUrl">URL</label>
						<div class="input-control modern text">
							<input type="text" id="txtUrl" placeholder="Enter URL here" /> <span
								class="informer">Please enter URL here</span>
						</div>
					</div>
					<div class="row-input">
						<label for="txtUri">Module URI</label>
						<div class="input-control modern text">
							<input type="text" id="txtUri" placeholder="Enter uri here" /> <span
								class="informer">Please enter URI here</span>
						</div>
					</div>
				</div>
				<div class="cell size6">
					<div class="row-input">
						<label for="txtLogoPath">Logo path</label>
						<div class="input-control modern text">
							<input type="text" id="txtLogoPath" placeholder="Enter logo path" />
							<span class="informer">Please enter logo path here</span>
						</div>
					</div>
					<div class="row-input">
						<label for="txtColumnKey">Column key</label>
						<div class="input-control modern text">
							<input type="text" id="txtColumnKey"
								placeholder="Enter column key here" /> <span class="informer">Please
								enter column key here</span>
						</div>
					</div>
					<div class="row-input">
						<label for="txtColumnSite">Column site</label>
						<div class="input-control modern text">
							<input type="text" id="txtColumnSite"
								placeholder="Enter column site here" /> <span class="informer">Please
								enter column site here</span>
						</div>
					</div>
					<div class="row-input">
						<label for="txtTableSite">Table site</label>
						<div class="input-control modern text">
							<input type="text" id="txtTableSite"
								placeholder="Enter table site here" /> <span class="informer">Please
								enter table site here</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row-input">
			<button mode="" class="button primary" id="btnSave">Save</button>
		</div>
	</div>
	<script src="../metro/js/metro.min.js"></script>
	<script src="../js/jquery.dataTables.min.js"></script>
	<script src="../js/configlab.js"></script>
</body>
</html>