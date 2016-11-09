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
        #detailSyndicDialog, #createLab011SyndicDialog{
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
        .row-input{
            width: 80%;
        }
        .row-input .input-control{
            width: 99%;
        }
        .wrapper-resident-list {
		    width: 90%;
		    margin: 0 auto;
		}
		#moduleTable th, #moduleTable td {
		    text-align: center;
		}
		.add-new-syndic-main, .detail-syndic-main {
		    width: 90%;
	     	display: -webkit-box;
		    display: -moz-box;
		    display: -ms-flexbox;
		    display: -webkit-flex;
		    display: flex;
		    justify-content: space-between;
		}
		.add-new-syndic-left, .add-new-syndic-right {
		    width: 48%;
		}
		.detail-syndic-left, .detail-syndic-right {
		    width: 48%;
		}
		#btnSaveNewSyndic,#btnSaveSyndic {
		    float: right;
		}
		#moduleTable th {
    		font-size: 12px;
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
				<th style="display:none">Lab011Syndic</th>
				<th style="display:none"><s:text name="Id"></s:text></th>
				<th><s:text name="Adress"></s:text></th>
				<th><s:text name="Chauffage"></s:text></th>
				<th><s:text name="Eau Chaude"></s:text></th>
				<th><s:text name="Eau Froide"></s:text></th>
				<th><s:text name="Chauffage Communes"></s:text></th> 
				<th><s:text name="Eau Chaude Communes"></s:text></th>
				<th><s:text name="Eau Froide Communes"></s:text></th>
				<th><s:text name="Code Postal"></s:text></th>
				<th><s:text name="Name"></s:text></th>
				<th><s:text name="Ville"></s:text></th>
				<th><s:text name="Report path"></s:text></th>
				<th><s:text name="label.username"></s:text></th>
				<th><s:text name="Coefficients for convert kwh to m3"></s:text></th>
				<th><s:text name="Coefficients for total"></s:text></th>
				<th><s:text name="Coefficients for ECS"></s:text></th>
				<th><button type="button" id="btncreateLab011Syndic"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="listAllSyndic">
				<tr>
					<td style="display:none"><s:property value="numConfigLab011Syndic" /></td>
					<td style="display:none"><s:property value="id" /></td>
					<td><s:property value="address" /></td>
					<td><s:property value="chauffage" /></td>
					<td><s:property value="eauChaude" /></td>
					<td><s:property value="eauFroide" /></td>
					<td><s:property value="chauffageCommunes" /></td>
					<td><s:property value="eauChaudeCommunes" /></td>
					<td><s:property value="eauFroideCommunes" /></td>
					<td><s:property value="codePostal" /></td>
					<td><s:property value="name" /></td>
					<td><s:property value="ville" /></td>
					<td><s:property value="reportPath" /></td>
					<td><s:property value="userName" /></td>
					<td><s:property value="coeffUnit" /></td>
					<td><s:property value="coeffTotal" /></td>
					<td><s:property value="coeffEcs" /></td>
					<td>
						<button type="button"
							id="btnEditSyndic<s:property value='id' />"
							value='<s:property value="id" />'
							class="btnEditSyndic cycle-button large-button">
							<span class="mif-pencil mif-ani-hover-spanner"></span>
						</button>
						<button type="button"
							id="btnRemoveSyndic<s:property value='id' />"
							value='<s:property value="id" />'
							class="btnRemoveSyndic cycle-button large-button">
							<span class="mif-bin mif-ani-hover-pass"></span>
						</button>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
		<div data-role="dialog" data-overlay="true" data-width="800"
		data-overlay-color="op-dark" data-close-button="true" id="createLab011SyndicDialog">
		 <div class="row-input">
	        <div class="input-control text">
	            <input type="text" id="" disabled>
	        </div>
	    </div>
	    <div class="add-new-syndic-main">
		    <div class="add-new-syndic-left">
	    <div class="row-input">
	        <label for="txtAddresss">Address</label>
	        <div class="input-control modern text">
<!-- 	        	<label for="txtAddresss">Address</label> -->
	            <input type="text" id="txtAddresss" placeholder="Address" />
	            <span class="informer">Please enter Address here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtChauffages">Chauffage</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtChauffages" placeholder="Enter Chauffage here"/>
	            <span class="informer">Please enter Chauffage here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtEauChaudes">Eau Chaude</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtEauChaudes" placeholder="EnterEau Chaude here"/>
	            <span class="informer">Please enter Eau Chaude here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtEauFroides">Eau Froide</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtEauFroides" placeholder="Enter Eau Froide here"/>
	            <span class="informer">Please enter Eau Froide here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtChauffageCommuness">Chauffage Communes</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtChauffageCommuness" placeholder="Enter Chauffage Communes here"/>
	            <span class="informer">Please enter Chauffage Communes here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtEauChaudeCommuness">Eau Chaude Communes</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtEauChaudeCommuness" placeholder="Enter Eau Chaude Communes here"/>
	            <span class="informer">Please enter Eau Chaude Communes here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtEauFroideCommuness">Eau Froide Communes</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtEauFroideCommuness" placeholder="Enter Eau Froide Communes here"/>
	            <span class="informer">Please enter Eau Froide Communes here</span>
	        </div>
	    </div>
	    </div>
	    <div class="add-new-syndic-right">
	    <div class="row-input">
	        <label for="txtCodePostals">Code Postal</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtCodePostals" placeholder="Enter Code Postal here"/>
	            <span class="informer">Please enter Code Postal here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtNames">Name</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtNames" placeholder="Enter Name here"/>
	            <span class="informer">Please enter Name here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtVilles">Ville</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtVilles" placeholder="Enter Ville here"/>
	            <span class="informer">Please enter Ville here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtCoeffUnits">Coefficients for convert kwh to m3</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtCoeffUnits" placeholder="Enter Coefficients here"/>
	            <span class="informer">Please enter Coefficients here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtCoeffTotals">Coefficients for total</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtCoeffTotals" placeholder="Enter Coefficients here"/>
	            <span class="informer">Please enter Coefficients here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtCoeffEcss">Coefficients for ECS</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtCoeffEcss" placeholder="Enter Coefficients here"/>
	            <span class="informer">Please enter Coefficients here</span>
	        </div>
	    </div>	
	    <div class="row-input">
			<label>List User</label>
			<s:select class="input-control select" list="userSyndicLst"
			listKey="id" listValue="userName"
			id = "userId"
			cssClass="select-tag"/>
		</div>
	   <div class="row-input">
	        <label for="txtFileReports">Report path</label>
	<!--         <input type="text" id="txtImageClient" disabled="true" size="20"/> -->
	        <div class="input-control file" data-role="input" style="width: 235px;">
	            <input id="uploadFileReports" type="file" name="filePdf" accept="application/pdf"
	                   placeholder="<s:text name="label.upload"/>">
	            <button class="button">
	                <img src="../img/Blank_Folder.png"/>
	            </button>
	        </div>
    	</div>
    	</div>
    	</div>
    	 <div class="row-input">
	        <button class="button primary" id="btnSaveNewSyndic">Save</button>
	    </div>
	    </div>
	
	
	<div data-role="dialog" data-overlay="true" data-overlay-color="op-dark"
	     data-width="800"
	     data-close-button="true"
	     id="detailSyndicDialog">
	     <div class="row-input" style="display: none;">
	        <label for="txtcaloonUserId">Caloon Syndic Id</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtcaloonUserId" placeholder="Enter Caloon Syndic Id here"/>
	            <span class="informer">Please enter Caloon Syndic Id here</span>
	        </div>
	    </div>
	    <div class="row-input" style="display: none;">
	        <label for="txtIsDefault">Default</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtIsDefault" placeholder="Enter Caloon Syndic Id here"/>
	        </div>
	    </div>
	    <div class="row-input">
	        <div class="input-control text">
	            <input type="text" id="txtSyndicId" disabled>
	        </div>
	    </div>
	    <div class="detail-syndic-main">
		    <div class="detail-syndic-left">
	    <div class="row-input">
	        <label for="txtAddress">Address</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtAddress" placeholder="Address"/>
	            <span class="informer">Please enter Address here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtChauffage">Chauffage</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtChauffage" placeholder="Enter Chauffage here"/>
	            <span class="informer">Please enter Chauffage here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtEauChaude">Eau Chaude</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtEauChaude" placeholder="EnterEau Chaude here"/>
	            <span class="informer">Please enter Eau Chaude here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtEauFroide">Eau Froide</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtEauFroide" placeholder="Enter Eau Froide here"/>
	            <span class="informer">Please enter Eau Froide here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtChauffageCommunes">Chauffage Communes</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtChauffageCommunes" placeholder="Enter Chauffage Communes here"/>
	            <span class="informer">Please enter Chauffage Communes here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtEauChaudeCommunes">Eau Chaude Communes</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtEauChaudeCommunes" placeholder="Enter Eau Chaude Communes here"/>
	            <span class="informer">Please enter Eau Chaude Communes here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtEauFroideCommunes">Eau Froide Communes</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtEauFroideCommunes" placeholder="Enter Eau Froide Communes here"/>
	            <span class="informer">Please enter Eau Froide Communes here</span>
	        </div>
	    </div>
	    </div>
	    <div class="detail-syndic-right">
	    <div class="row-input">
	        <label for="txtCodePostal">Code Postal</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtCodePostal" placeholder="Enter Code Postal here"/>
	            <span class="informer">Please enter Code Postal here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtName">Name</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtName" placeholder="Enter Name here"/>
	            <span class="informer">Please enter Name here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtVille">Ville</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtVille" placeholder="Enter Ville here"/>
	            <span class="informer">Please enter Ville here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtCoeffUnit">Coefficients for convert kwh to m3</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtCoeffUnit" placeholder="Enter Coefficients here"/>
	            <span class="informer">Please enter Coefficients here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtCoeffTotal">Coefficients for total</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtCoeffTotal" placeholder="Enter Coefficients here"/>
	            <span class="informer">Please enter Coefficients here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtCoeffEcs">Coefficients for ECS</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtCoeffEcs" placeholder="Enter Coefficients here"/>
	            <span class="informer">Please enter Coefficients here</span>
	        </div>
	    </div>	
	   <div class="row-input">
        <label for="txtFileReport">Report path</label>
<!--         <input type="text" id="txtImageClient" disabled="true" size="20"/> -->
        <div class="input-control file" data-role="input" style="width: 235px;">
            <input id="uploadFileReport" type="file" name="filePdf" accept="application/pdf"
                   placeholder="<s:text name="label.upload"/>">
            <button class="button">
                <img src="../img/Blank_Folder.png"/>
            </button>
        </div>
    </div>
    </div>
    </div>
	    <div class="row-input">
	        <button class="button primary" id="btnSaveSyndic">Save</button>
	    </div>
	</div>
	<script src="../js/jquery-1.11.3.min.js"></script>
	<script src="../metro/js/metro.min.js"></script>
	<script src="../jquery-ui/jquery-ui.min.js"></script>
	<script src="../js/jquery.nicescroll.min.js"></script>
	<script src="../js/lab011Syndic.js"></script>
	<script>
		$(function(){
			$('html').niceScroll();;
		});
	</script>
</body>
</html>