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
#detailResidentDialog{
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
    width: 90%;
    margin-left: 10%;
}
.row-input .input-control{
    width: 99%;
}
.wrapper-resident-list {
width: 80%;
margin: 0 auto;
}
#moduleTable th, #moduleTable td {
    text-align: center;
}
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
	</div>
	<div class="wrapper-resident-list">
		<table class="table border bordered dataTable"	id="moduleTable">
			<thead>
				<tr>
					<th style="display:none">Lab011Resident</th>
					<th><s:text name="Id"></s:text></th>
					<th><s:text name="Appartement Number"></s:text></th>
					<th><s:text name="Client Name"></s:text></th>
					<th style="display:none"><s:text name="Caloon Syndic Id"></s:text></th>
					<th><s:text name="Chauffage"></s:text></th>
					<th><s:text name="Eau Chaude"></s:text></th>
					<th><s:text name="Eau Froide"></s:text></th>
					<th><s:text name="Superficie"></s:text></th>
					<th><s:text name="Logements"></s:text></th>
					<th><s:text name="label.username"/></th>
					<th><button type="button" id="btncreateLab011Resident"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button></th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="listAllResidents">
					<tr>
						<td style="display:none"><s:property value="numResident" /></td>
						<td><s:property value="residentId" /></td>
						<td><s:property value="appartementNumber" /></td>
						<td><s:property value="clientName" /></td>
						<td style="display:none"><s:property value="caloonSyndicId" /></td>
						<td><s:property value="chauffage" /></td>
						<td><s:property value="eauChaude" /></td>
						<td><s:property value="eauFroide" /></td>
						<td><s:property value="superficie"/></td>
						<td><s:property value="txtLogements"/></td>
						<td><s:property value="userName"/></td>
						<td>
							<button type="button"
								id="btnEditResident<s:property value='residentId' />"
								value='<s:property value="residentId" />'
								class="btnEditResident cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
							id="btnRemoveResident<s:property value='residentId' />"
							value='<s:property value="residentId" />'
							class="btnRemoveResident cycle-button large-button">
							<span class="mif-bin mif-ani-hover-pass"></span>
						</button>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<div data-role="dialog" data-overlay="true" data-width="500"
		data-overlay-color="op-dark" data-close-button="true" id="createLab011ResidentDialog">
		<form action="createLab011Resident.action" method="post">
			<div class="row-input">
		        <div class="input-control text">
		            <input type="text" id="" disabled>
		        </div>
		    </div>
			<div class="row-input">
				<label for="txtAppartementNumbers">Appartement Number</label>
				<div class="input-control modern text">
<!-- 		            <input type="text" id="txtAppartementNumbers" placeholder="Appartement Number"/> -->
					<s:textfield id = "txtAppartementNumbers" placeholder="Appartement Number"
								value="%{numconfigLab011Resident.appartementNumber}"
								name="numconfigLab011Resident.appartementNumber" ></s:textfield>
		            <span class="informer">Please enter Appartement Number here</span>
	        	</div>
			</div>
			<div class="row-input">
		        <label for="txtClientNames">Client Name</label>
		        <div class="input-control modern text">
<!-- 		            <input type="text" id="txtClientNames" placeholder="Client Name"/> -->
					<s:textfield id = "txtClientNames" placeholder="Client Name"
								value="%{numconfigLab011Resident.clientName}"
								name="numconfigLab011Resident.clientName" ></s:textfield>
		            <span class="informer">Please enter Client Name here</span>
		        </div>
		    </div>
		    <div class="row-input">
		        <label for="txtChauffages">Chauffage</label>
		        <div class="input-control modern text">
<!-- 		            <input type="text" id="txtChauffages" placeholder="Enter Chauffage here"/> -->
					<s:textfield id = "txtChauffages" placeholder="Enter Chauffage here"
								value="%{numconfigLab011Resident.chauffage}"
								name="numconfigLab011Resident.chauffage" ></s:textfield>
		            <span class="informer">Please enter Chauffage here</span>
		        </div>
		    </div>
		    <div class="row-input">
		        <label for="txtEauChaudes">Eau Chaude</label>
		        <div class="input-control modern text">
<!-- 		            <input type="text" id="txtEauChaudes" placeholder="EnterEau Chaude here"/> -->
					<s:textfield id = "txtEauChaudes" placeholder="EnterEau Chaude here"
								value="%{numconfigLab011Resident.eauChaude}"
								name="numconfigLab011Resident.eauChaude" ></s:textfield>
		            <span class="informer">Please enter Eau Chaude here</span>
		        </div>
		    </div>
		    <div class="row-input">
		        <label for="txtEauFroides">Eau Froide</label>
		        <div class="input-control modern text">
<!-- 		            <input type="text" id="txtEauFroides" placeholder="Enter Eau Froide here"/> -->
					<s:textfield id = "txtEauFroides" placeholder="Enter Eau Froide here"
								value="%{numconfigLab011Resident.eauFroide}"
								name="numconfigLab011Resident.eauFroide" ></s:textfield>
		            <span class="informer">Please enter Eau Froide here</span>
		        </div>
		    </div>
		    <div class="row-input">
		        <label for="txtSuperficies">Superficie</label>
		        <div class="input-control modern text">
<!-- 		            <input type="text" id="txtSuperficies" placeholder="Enter Superficie here"/> -->
					<s:textfield id = "txtSuperficies" placeholder="Enter Superficie here"
								value="%{numconfigLab011Resident.superficie}"
								name="numconfigLab011Resident.superficie" ></s:textfield>
		            <span class="informer">Please enter Superficie here</span>
		        </div>
		    </div>
		    <div class="row-input">
	     	<label for="txtSuperficie">Logements</label>
		        <s:radio name="numconfigLab011Resident.logements" list="#{'true':'Occupied','false':'Empty'}"
		         value="%{numconfigLab011Resident.logements}"></s:radio>
		    </div>
		    <div class="row-input">
				<label>List User</label>
				<s:select class="input-control select" list="userResidentLst"
				listKey="id" listValue="userName"
				name="numconfigLab011Resident.userId"
				value="%{numconfigLab011Resident.userId}"
				cssClass="select-tag"/>
			</div>
		    <div class="row-input">
				<button type="submit" class="button primary" id="btnSaveNewResident">Save</button>
			</div>
		</form>
	</div>
	
<!-- 	................................... -->
	<div data-role="dialog" data-overlay="true" data-overlay-color="op-dark"
	     data-width="500"
	     data-height="665"
	     data-close-button="true"
	     id="detailResidentDialog">
	    <div class="row-input">
	        <div class="input-control text">
	            <input type="text" id="txtResidentId" disabled>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtAppartementNumber">Appartement Number</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtAppartementNumber" placeholder="Appartement Number"/>
	            <span class="informer">Please enter Appartement Number here</span>
	        </div>
	    </div>
	    <div class="row-input">
	        <label for="txtClientName">Client Name</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtClientName" placeholder="Client Name"/>
	            <span class="informer">Please enter Client Name here</span>
	        </div>
	    </div>
	    <div class="row-input" style="display: none;">
	        <label for="txtcaloonUserId">Caloon Syndic Id</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtcaloonUserId" placeholder="Enter Caloon Syndic Id here"/>
	            <span class="informer">Please enter Caloon Syndic Id here</span>
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
	        <label for="txtSuperficie">Superficie</label>
	        <div class="input-control modern text">
	            <input type="text" id="txtSuperficie" placeholder="Enter Superficie here"/>
	            <span class="informer">Please enter Superficie here</span>
	        </div>
	    </div>
	     <div class="row-input">
	     	<label>Logements</label>
	        <input type="checkbox" id="occupied" name="occupied" value="true" onchange="changeCheckBoxTrue()"> Occupied
	        <input type="checkbox" id="empty" name="empty" value="false" onchange="changeCheckBoxFalse()"> Empty
	    </div>
	    <div class="row-input">
	        <button class="button primary" id="btnSaveResident">Save</button>
	    </div>
	</div>
	<script src="../js/jquery-1.11.3.min.js"></script>
	<script src="../metro/js/metro.min.js"></script>
	<script src="../js/jquery.nicescroll.min.js"></script>
	<script src="../js/lab011Resident.js"></script>
	<script>
		$(function(){
			$('html').niceScroll();;
		});
	</script>
</body>
</html>