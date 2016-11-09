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
<style type="text/css">
	#detailRangeDialog{
	    display: -webkit-flex; /* Safari */
	    display: flex;
	    display: -ms-flexbox; /* TWEENER - IE 10 */
	    flex-direction: column;
	    -webkit-flex-direction: column;
	    justify-content: space-between;
	    -webkit-justify-content: space-between;
	    align-items: center;
	    -webkit-align-items: center;
	}
    .body{
		font-family: Exo;
	}

	.row-input {
		width: 90%;
	}

	.row-input .input-control {
		width: 100%;
	}
	.dialog{
		z-index:999;
	}
	.dialog-overlay{
		z-index:998
	}
	.main{
		margin-left: 15px;
		font-family: Exo;
	}
	.table{
		width: 60%;
		text-align: center;
	}
</style>
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
	</div>
	<table class="table border bordered dataTable"	id="moduleTable">
		<thead>
			<tr>
				<th style="display:none">Lab011Range</th>
				<th style="width: 20%;text-align: center;"><s:text name="Consommation Range Id"></s:text></th>
				<th style="width: 20%;text-align: center;"><s:text name="From Number"></s:text></th>
				<th style="width: 20%;text-align: center;"><s:text name="To Number"></s:text></th>
				<th style="width: 20%;text-align: center;"><s:text name="Order By"></s:text></th>
				<th style="width: 25%;text-align: center;"><button type="button" id="btncreateLab011Range"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="listAllRange">
				<tr>
					<td style="display:none"><s:property value="numRange" /></td>
					<td><s:property value="rangeId" /></td>
					<td><s:property value="fromNumber" /></td>
					<td><s:property value="toNumber" /></td>
					<td><s:property value="orderBy" /></td>
					<td>
						<button type="button"
							id="btnEditRange<s:property value='rangeId' />"
							value='<s:property value="rangeId" />'
							class="btnEditRange cycle-button large-button">
							<span class="mif-pencil mif-ani-hover-spanner"></span>
						</button>
						<button type="button"
							id="btnRemoveRange<s:property value='rangeId' />"
							value='<s:property value="rangeId" />'
							class="btnRemoveRange cycle-button large-button">
							<span class="mif-bin mif-ani-hover-pass"></span>
						</button>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<div class="creatRange" id="creatRange">
			<form data-role="validator" action="createLab011Range.action" method="post">
				<table>
					<tr>
						<td><s:textfield data-validate-func="number"
								data-validate-hint="You must insert Number"
								value="%{numConfigLab011Range.fromNumber}" name="numConfigLab011Range.fromNumber"
								label="From Number" /></td>
					</tr>
					<tr>
						<td><s:textfield data-validate-func="number"
								data-validate-hint="You must insert Number"
								value="%{numConfigLab011Range.toNumber}" name="numConfigLab011Range.toNumber"
								label="To Number" /></td>
					</tr>
					<tr>
						<td><s:textfield data-validate-func="number"
								data-validate-hint="You must insert Number"
								value="%{numConfigLab011Range.orderBy}"
								name="numConfigLab011Range.orderBy" label="Order By" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" class="button primary"
							id="btnSaveNewRange" value="Save"> </buton></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-role="dialog" data-overlay="true" data-overlay-color="op-dark"
	     data-width="500"
	     data-height="600"
	     data-close-button="true"
	     id="detailRangeDialog">
	    <div class="row-input">
	        <div class="input-control text">
	            <input type="text" id="txtRangeId" disabled>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtFromNumber">From Number</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtFromNumber" placeholder="From Number"/>
	            <span class="informer">Please enter From Number here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtToNumber">To Number</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtToNumber" placeholder="Enter To Number here"/>
	            <span class="informer">Please enter To Number here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtOrderBy">Order By</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtOrderBy" placeholder="Enter Order By here"/>
	            <span class="informer">Please enter Order By here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <button class="button primary" id="btnSaveRange">Save</button>
	    </div>
	</div>
	<script src="../js/jquery-1.11.3.min.js"></script>
	<script src="../metro/js/metro.min.js"></script>
	<script src="../jquery-ui/jquery-ui.min.js"></script>
	<script src="../js/lab011Range.js"></script>
</body>
</html>
