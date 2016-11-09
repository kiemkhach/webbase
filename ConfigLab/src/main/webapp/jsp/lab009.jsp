<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><s:text name="label.title.perial"></s:text></title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<link href="../jquery-ui/jquery-ui.min.css" rel="stylesheet">
<style type="text/css">
#detailEnergyTypesDialog, #detailLotConsommationDialog, #detailProviderDialog {
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
</head>
<body>
	<div class="main" >
		<div class="menu">
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='home' />"><s:text name="label.home" /></a> <a
				class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
				<s:if test="%{(numConfigLab009Perial != null)}">
				<s:url action="redirectLab009Module" var="lab009ModuleUrl">
					<s:param name="clientId" value="%{numConfigLab009Perial.clientId}"></s:param>
				</s:url>
				<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:property value="#lab009ModuleUrl" />"><s:text name="label.configModule" /></a> 
				</s:if>
		</div>
		<div>
			<a class="" href="<s:url action='createConfigLab009' />">
			<s:text name="label.create.siteId" /></a>
		</div>
		<s:if
			test="%{(listAllConfigLab009 != null && listAllConfigLab009.size > 0)}">
			<form action="selectSiteLab009">
				<s:select class="input-control select" list="listAllConfigLab009"
					listKey="clientId" listValue="clientName" name="clientId"
					value="%{numConfigLab009Perial.clientId}"
					onchange="this.form.submit()" cssClass="select-tag" cssStyle="margin-left: 0px"/>
			</form>
			
			<!-- saveUnitCO2.action -->
			<form action="saveConfigLab009" method="post">
				<input type="hidden" id="clientId"
					value="${numConfigLab009Perial.clientId}"
					name="numConfigLab009Perial.clientId" />
				<table>
					<tr>
						<td><s:textfield 
								id = "txtClientName" 
								value="%{numConfigLab009Perial.clientName}"
								name="numConfigLab009Perial.clientName" 
								label="Client Name"></s:textfield>
					</tr>
					<tr>
						<td><s:select id="target"
								label="%{getText('lable.unitEmissions')}"
								list="#{'t':'t', 'kt':'kt'}"
								name="numConfigLab009Perial.unitEmissions"
								value="%{numConfigLab009Perial.unitEmissions}" /></td>
					</tr>
					<tr>
						<td><s:select id="target"
								label="%{getText('label.unitConsommation')}"
								list="#{'1':'kWh', '2':'MWh', '3':'GWh'}"
								name="numConfigLab009Perial.unitConsommation"
								value="%{numConfigLab009Perial.unitConsommation}" /></td>
					</tr>
					<tr>
						<td><s:select id="target"
								label="%{getText('label.unitMontal')}"
								list="#{'1':'€', '2':'k€', '3':'M€'}"
								name="numConfigLab009Perial.unitMontal"
								value="%{numConfigLab009Perial.unitMontal}" /></td>
					</tr>
					<td><a class="tbl-button"> <input type="submit"
							id="btnSaveConfigLab009" value="<s:text name="label.save"/>"
							class="button primary">
					</a></td>
				</table>
			</form>
		</s:if>
		<input type="hidden" id="message" value="<s:property value='mes'/>" />
		<div style="font-weight: bold;">
			<s:text name="label.energyTypes"></s:text>
		</div>
		<table class="table border bordered">
			<thead>
				<tr>
					<th style="width: 20%;text-align: center;"><s:text name="label.energyName"></s:text></th>
					<th style="width: 20%;text-align: center;"><s:text name="label.energyCode"></s:text></th>
					<th style="width: 20%;text-align: center;"><s:text name="label.energyEmissions"></s:text></th>
					<th style="width: 20%;text-align: center;"><s:text name="label.colorCode"></s:text></th>
					<th style="width: 20%;text-align: center;"><button type="button" id="btncreateLab009EnergyTypes"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button></th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="numConfigLab009Energy.lab009EnergyTypesLst">
					<tr>
						<td><s:property value="energyName" /></td>
						<td><s:property value="energyCode" /></td>
						<td><s:property value="energyEmissions" /></td>
						<td><s:property value="colorCode" /> <span
							style="border: 8px solid rgba(0,0,0,0.15);border-radius: 3px;;background-color:<s:property 
							value='colorCode' />"></span>
						<td>
							<button type="button"
								id="btnEditLab009EnergyTypes<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnEditLab009EnergyTypes cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
								id="btnRemoveLab009EnergyTypes<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnRemoveLab009EnergyTypes cycle-button large-button">
								<span class="mif-bin mif-ani-hover-pass"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<div style="font-weight: bold;">
			<s:text name="label.lotConsommation"></s:text>
		</div>
		<table class="table border bordered">
			<thead>
				<tr>
					<th style="width: 25%;text-align: center;"><s:text name="label.lotName"></s:text></th>
					<th style="width: 25%;text-align: center;"><s:text name="label.lotCode"></s:text></th>
					<th style="width: 25%;text-align: center;"><s:text name="label.colorCode"></s:text></th>
					<th style="width: 25%;text-align: center;"><button type="button" id="btncreateLab009LotConsommation"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button></th>
				</tr>
			</thead>
			<tbody>
				<s:iterator
					value="numConfigLab009LotConsommation.lab009LotConsommationsLst">
					<tr>
						<td><s:property value="lotName" /></td>
						<td><s:property value="lotCode" /></td>
						<td><s:property value="colorCode" /> <span
							style="border: 8px solid rgba(0,0,0,0.15);border-radius: 3px;;background-color:<s:property 
							value='colorCode' />"></span>
						</td>
						<td>
							<button type="button"
								id="btnEditLab009LotConsommation<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnEditLab009LotConsommation cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
								id="btnRemoveLab009LotConsommation<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnRemoveLab009LotConsommation cycle-button large-button">
								<span class="mif-bin mif-ani-hover-pass"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<div style="font-weight: bold;">
			<s:text name="label.provider"></s:text>
		</div>
		<table class="table border bordered">
			<thead>
				<tr>
					<th style="width: 25%;text-align: center;"><s:text name="label.providerName"></s:text></th>
					<th style="width: 25%;text-align: center;"><s:text name="label.providerCode"></s:text></th>
					<th style="width: 25%;text-align: center;"><s:text name="label.colorCode"></s:text></th>
					<th style="width: 25%;text-align: center;"><button type="button" id="btncreateLab009Provider"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button></th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="numConfigLab009Provider.lab009ProviderLst">
					<tr>
						<td><s:property value="providerName" /></td>
						<td><s:property value="providerCode" /></td>
						<td><s:property value="colorCode" /> <span
							style="border: 8px solid rgba(0,0,0,0.15);border-radius: 3px;;background-color:<s:property 
							value='colorCode' />"></span>
						</td>
						<td>
							<button type="button"
								id="btnEditLab009Provider<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnEditLab009Provider cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
								id="btnRemoveLab009Provider<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnRemoveLab009Provider cycle-button large-button">
								<span class="mif-bin mif-ani-hover-pass"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<!-- ......................Create Energy Types........................... -->
		<div class="creatEnergyTypes" id="creatEnergyTypes">
			<form data-role="validator" action="createLab009.action" method="post">
				<table>
					<tr>
						<td><s:textfield data-validate-func="required"
								data-validate-hint="You must insert Energy Name"
								value="%{numConfigLab009Energy.energyName}" name="energyName"
								label="Energy Name" /></td>
					</tr>
					<tr>
						<td><s:textfield data-validate-func="required"
								data-validate-hint="You must insert Energy Code"
								value="%{numConfigLab009Energy.energyCode}" name="energyCode"
								label="Energy Code" /></td>
					</tr>
					<tr>
						<td><s:textfield data-validate-func="number"
								data-validate-hint="You must insert Number Energy Emissions"
								value="%{numConfigLab009Energy.energyEmissions}"
								name="energyEmissions" label="Energy Emissions" /></td>
					</tr>
					<tr>
						<td><s:textfield data-validate-func="required"
								data-validate-hint="You must insert Color Code"
								value="%{numConfigLab009Energy.colorCode}" name="colorCode"
								label="Color Code" cssClass="jscolor" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" class="button primary"
							id="btnSaveNewEnergyTypes" value="Save"> </buton></td>
					</tr>
				</table>
			</form>
		</div>

		<!-- ..................................Create Lot Consommation..................... -->
		<div class="creatLotConsommation" id="creatLotConsommation">
			<form data-role="validator"
				action="createLab009LotConsommation.action"
				method="post">
				<table>
					<tr>
						<td><s:textfield data-validate-func="required"
								data-validate-hint="You must insert Lot Name"
								value="%{numConfigLab009LotConsommation.lotName}"
								name="numConfigLab009LotConsommation.lotName" label="Lot Name" /></td>
					</tr>
					<tr>
						<td><s:textfield data-validate-func="required"
								data-validate-hint="You must insert Number Lot Code"
								value="%{numConfigLab009LotConsommation.lotCode}"
								name="numConfigLab009LotConsommation.lotCode" label="Lot Code" /></td>
					</tr>
					<tr>
						<td><s:textfield data-validate-func="required"
								data-validate-hint="You must insert Color Code"
								value="%{numConfigLab009LotConsommation.colorCode}"
								name="numConfigLab009LotConsommation.colorCode"
								label="Color Code" cssClass="jscolor" /></td>

					</tr>
					<tr>
						<td></td>
						<td><input type="submit" class="button primary"
							id="btnSaveLotConsommation" value="Save"> </buton></td>
					</tr>
				</table>
			</form>
		</div>

		<!-- .....................................Create Provider......................... -->
		<div class="creatProvider" id="creatProvider">
			<form data-role="validator" action="createLab009Provider.action"
			method="post">
				<table>
					<tr>
						<td><s:textfield data-validate-func="required"
								data-validate-hint="You must insert Provider Name"
								value="%{numConfigLab009Provider.providerName}"
								name="numConfigLab009Provider.providerName"
								label="Provider Name" /></td>
					</tr>
					<tr>
						<td><s:textfield data-validate-func="required"
								data-validate-hint="You must insert Number Lot Code"
								value="%{numConfigLab009Provider.providerCode}"
								name="numConfigLab009Provider.providerCode"
								label="Provider Code" /></td>
					</tr>
					<tr>
						<td><s:textfield data-validate-func="required"
								data-validate-hint="You must insert Color Code"
								value="%{numConfigLab009Provider.colorCode}"
								name="numConfigLab009Provider.colorCode" label="Color Code"
								cssClass="jscolor" /></td>

					</tr>
					<tr>
						<td></td>
						<td><input type="submit" class="button primary"
							id="btnSaveProvider" value="Save"> </buton></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-role="dialog" data-overlay="true"
			data-overlay-color="op-dark" data-width="400" data-height="450"
			data-close-button="true" id="detailEnergyTypesDialog">
			<input type="hidden" id="txtEnergyId" />
			<div class="row-input">
				<label for="txtEnergyName">Energy Name</label>
				<div class="input-control modern text">
					<input type="text" id="txtEnergyName"
						data-validate-func="required"
						data-validate-hint="You must insert Provider Name" 
						placeholder="Enter energy name here" /> <span class="informer">Please
						enter energy name here</span>
				</div>
			</div>
			<div class="row-input">
				<label for="txtEnergyCode">Energy Code</label>
				<div class="input-control modern text">
					<input type="text" id="txtEnergyCode"
						placeholder="Enter Energy Code here" /> <span class="informer">Please
						enter Energy Code here</span>
				</div>
			</div>
			<div class="row-input">
				<label for="txtEnergyEmissions">Energy Emissions</label>
				<div class="input-control modern text">
					<input type="text" id="txtEnergyEmissions"
						placeholder="Enter Energy Emissions here" /> <span
						class="informer">Please enter number Energy Emissions here</span>
				</div>
			</div>
			<div class="row-input">
				<label for="txtColorCode">Color Code</label>
				<div class="input-control modern text">
					<input class="jscolor" type="text" id="txtColorCodeEnergy"
						placeholder="Enter Color Code here"
						data-validate-func="required"
						data-validate-hint="You must insert Provider Name" /> <span class="informer">Please
						enter Color Code here</span>
			</div>
			<div class="row-input">
				<button class="button primary" id="btnSaveEnergy">Save</button>
			</div>
		</div>
		<div data-role="dialog" data-overlay="true"
			data-overlay-color="op-dark" data-width="400" data-height="450"
			data-close-button="true" id="detailLotConsommationDialog">
			<input type="hidden" id="txtLotConsommationId" />
			<div class="row-input">
				<label for="txtLotName">Lot Name</label>
				<div class="input-control modern text">
					<input type="text" id="txtLotName"
						placeholder="Enter lot name here" /> <span class="informer">Please
						enter lot name here</span>
				</div>
			</div>
			<div class="row-input">
				<label for="txtLotCode">Lot Code</label>
				<div class="input-control modern text">
					<input type="text" id="txtLotCode"
						placeholder="Enter lot code here" /> <span class="informer">Please
						enter lot code here</span>
				</div>
			</div>
			<div class="row-input">
				<label for="txtColorCode">Color Code</label>
				<div class="input-control modern text">
					<input class="jscolor" type="text" id="txtColorCodeLot"
						placeholder="Enter Color Code here" /> <span class="informer">Please
						enter Color Code here</span>
				</div>
			</div>
			<div class="row-input">
				<button class="button primary" id="btnSaveLotConsommation">Save</button>
			</div>
		</div>
		<div data-role="dialog" data-overlay="true"
			data-overlay-color="op-dark" data-width="400" data-height="450"
			data-close-button="true" id="detailProviderDialog">
			<input type="hidden" id="txtProviderId" />
			<div class="row-input">
				<label for="txtProName">Provider Name</label>
				<div class="input-control modern text">
					<input type="text" id="txtProName"
						placeholder="Enter provider name here" /> <span class="informer">Please
						enter provider name here</span>
				</div>
			</div>
			<div class="row-input">
				<label for="txtProCode">Provider Code</label>
				<div class="input-control modern text">
					<input type="text" id="txtProCode"
						placeholder="Enter provider code here" /> <span class="informer">Please
						enter provider code here</span>
				</div>
			</div>
			<div class="row-input">
				<label for="txtColorCode">Color Code</label>
				<div class="input-control modern text">
					<input class="jscolor" type="text" id="txtColorCodePro"
						placeholder="Enter Color Code here" /> <span class="informer">Please
						enter Color Code here</span>
				</div>
			</div>
			<div class="row-input">
				<button class="button primary" id="btnSaveProvider">Save</button>
			</div>
		</div>
	</div>
	<script src="../js/jquery-1.11.3.min.js"></script>
	<script src="../metro/js/metro.min.js"></script>
	<script src="../jquery-ui/jquery-ui.min.js"></script>
	<script src="../js/lab009EnergyTypes.js"></script>
	<script src="../js/jscolor.js"></script>
</body>
</html>