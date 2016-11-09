<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<s:if test="%{!isCreate}">
	<title><s:property value="%{numConfigLab008V2.siteName}" /></title>
</s:if>
<s:else>
	<title><s:property value="lab.name" /> - <s:text
			name="label.create.siteId" /></title>
</s:else>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<script src="../js/jquery-1.11.3.min.js"></script>
<script src="../metro/js/metro.min.js"></script>
<style type="text/css">
#detailECSDialog {
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
}

.row-input .input-control {
	width: 100%;
}

#daterangepicker {
	display: none;
}
</style>
</head>
<body>
	<div class="main">
		<input type="hidden" id="subscriptionId"
			value="${numConfigLab008V2.id}" /> <input type="hidden"
			id="homeConfigSiteId" value="${numConfigLab008V2.siteId}">
		<div class="menu">
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='home' />"><s:text name="label.home" /></a> <a
				class="button bg-teal bg-active-darkTeal fg-white"
				href="<s:url action='logOut' />"><s:text name="label.logout" /></a>
			<a class="button bg-teal bg-active-darkTeal fg-white"
				id="btnConfigChart"><s:text name="label.configChart" /></a>
		</div>
		<s:if test="%{!isCreate}">
			<a class="" href="<s:url action='createConfigLab008V2' />"
				style="margin-left: 15px;"><s:text name="label.create.siteId" /></a>
			<s:if test="%{listAllConfig != null && listAllConfig.size > 0}">
				<form action="selectSiteLab008V2">
					<s:select class="input-control select" list="listAllConfig"
						listKey="siteId" listValue="siteName" name="siteId"
						value="%{numConfigLab008V2.siteId}" onchange="this.form.submit()"
						cssClass="select-tag" />
				</form>
			</s:if>
		</s:if>
		<input type="hidden" id="message" value="<s:property value='mes'/>" />
		<div class="lab-name">
			<img src="../img/logos-boilers-monitor.png" height="200" width="200">
			<div class="text-lab-name">
				<s:property value="%{lab.name}" />
			</div>
		</div>
		<div class="config-site">
			<s:if test="%{isCreate}">
				<div class="sub-leader">
					<s:text name="label.create.siteId" />
				</div>
			</s:if>
			<s:else>
				<s:if test="%{listAllConfig != null && listAllConfig.size > 0}">
					<div class="sub-leader">
						<s:text name="label.siteId" />
						:
						<s:property value="%{numConfigLab008V2.siteId}" />
					</div>
					<div class="unregister-user">
						<form action="unregisterUserForLab008V2">
							<s:if test="%{listUsers != null && listUsers.size > 0}">
								<s:iterator value="listUsers" status="rows">
									<label class="input-control checkbox small-check"> <s:if
											test="%{listUsers[#rows.index] == username_lab_service}">
											<input type="checkbox" value="<s:property/>"
												disabled="disabled" />
										</s:if> <s:else>
											<input type="checkbox" value="<s:property/>" name="listUser" />
										</s:else> <span class="check"></span> <span class="caption"><s:property /></span>
									</label>
								</s:iterator>
								<input type="submit" class="button primary"
									value="<s:text name="label.unregister"/>" />
							</s:if>
						</form>
					</div>
					<div class="register-user">
						<form action="registerUserForLab008V2">
							<input type="text" name="listUserRegist" size="50"
								placeholder="<s:text name="placeholder.user.list"/>" /> <input
								type="submit" class="button primary"
								value="<s:text name="label.register"/>" />
						</form>
					</div>
				</s:if>
			</s:else>
			<s:if
				test="%{(!isCreate && listAllConfig != null && listAllConfig.size > 0) || isCreate}">
				<form data-role="validator" action="configLab008V2" method="post"
					enctype="multipart/form-data">
					<s:hidden name="isCreate" value="%{isCreate}" />
					<table>
						<s:if test="%{!isCreate}">
							<s:hidden name="id" value="%{numConfigLab008V2.id}" />
							<tr>
								<s:textfield value="%{numConfigLab008V2.siteId}" name="siteId"
									key="label.siteId" />
							</tr>
						</s:if>
						<tr>
							<s:textfield value="%{numConfigLab008V2.siteName}"
								data-validate-func="required"
								data-validate-hint="You must insert site name" name="siteName"
								key="label.siteName" size="50" />
						</tr>
						<!--                     <tr> -->
						<%--                         <s:if --%>
						<%--                                 test="%{numConfigLab008V2.moduleHeating == null || numConfigLab008V2.moduleHeating == ''}"> --%>
						<%--                             <td><s:text name="label.moduleHeating"/></td> --%>
						<!--                             <td><input type="text" name="moduleHeating" -->
						<%--                                        placeholder='<s:text name="placeholder.module.list" />' size="50"/></td> --%>
						<%--                         </s:if> --%>
						<%--                         <s:else> --%>
						<%--                             <s:textfield value="%{numConfigLab008V2.moduleHeating}" name="moduleHeating" --%>
						<%--                                          key="label.moduleHeating" size="50"/> --%>
						<%--                         </s:else> --%>
						<!--                     </tr> -->
						<tr>
							<s:if
								test="%{numConfigLab008V2.moduleOutsite == null || numConfigLab008V2.moduleOutsite == ''}">
								<td><s:text name="label.moduleOutsite" /></td>
								<td><input type="text" name="moduleOutsite"
									data-validate-func="required"
									data-validate-hint="You must insert module outsite"
									placeholder='<s:text name="placeholder.module.list" />'
									size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield value="%{numConfigLab008V2.moduleOutsite}"
									data-validate-func="required"
									data-validate-hint="You must insert module outsite"
									name="moduleOutsite" key="label.moduleOutsite" size="50" />
							</s:else>
						</tr>
						<tr>
							<s:if
								test="%{numConfigLab008V2.temperatureAmbianteLogementLimit == null}">
								<td><s:text name="label.temperatureAmbianteLogementLimit" /></td>
								<td><input data-validate-func="number"
									data-validate-hint="Only number is accepted" type="text"
									name="temperatureAmbianteLogementLimit"
									placeholder='Enter Temperature Ambiante Logenment Limit'
									size="50" autocomplete="off" /></td>
							</s:if>
							<s:else>
								<s:textfield data-validate-func="number"
									data-validate-hint="Only number is accepted"
									value="%{numConfigLab008V2.temperatureAmbianteLogementLimit}"
									name="temperatureAmbianteLogementLimit"
									key="label.temperatureAmbianteLogementLimit" size="50" />
							</s:else>
						</tr>
						<tr>
							<s:if
								test="%{numConfigLab008V2.temperatureProductionChauffageLimit == null}">
								<td><s:text
										name="label.temperatureProductionChauffageLimit" /></td>
								<td><input data-validate-func="number"
									data-validate-hint="Only number is accepted" type="text"
									name="temperatureProductionChauffageLimit"
									placeholder='Enter Temperature Production Chauffage Limit'
									size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield data-validate-func="number"
									data-validate-hint="Only number is accepted"
									value="%{numConfigLab008V2.temperatureProductionChauffageLimit}"
									name="temperatureProductionChauffageLimit"
									key="label.temperatureProductionChauffageLimit" size="50" />
							</s:else>
						</tr>
						<tr>
							<s:if
								test="%{numConfigLab008V2.temperatureProductionECSLimit == null}">
								<td><s:text name="label.temperatureProductionECSLimit" /></td>
								<td><input data-validate-func="number"
									data-validate-hint="Only number is accepted" type="text"
									name="temperatureProductionECSLimit"
									placeholder='Enter Temperature Production ECS Limit' size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield data-validate-func="number"
									data-validate-hint="Only number is accepted"
									value="%{numConfigLab008V2.temperatureProductionECSLimit}"
									name="temperatureProductionECSLimit"
									key="label.temperatureProductionECSLimit" size="50" />
							</s:else>
						</tr>
						<tr>
							<s:if
								test="%{numConfigLab008V2.temperatureRecyclageECSLimit == null}">
								<td><s:text name="label.temperatureRecyclageECSLimit" /></td>
								<td><input data-validate-func="number"
									data-validate-hint="Only number is accepted" type="text"
									name="temperatureRecyclageECSLimit"
									placeholder='Enter Temperature Recyclage ECS Limit' size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield data-validate-func="number"
									data-validate-hint="Only number is accepted"
									value="%{numConfigLab008V2.temperatureRecyclageECSLimit}"
									name="temperatureRecyclageECSLimit"
									key="label.temperatureRecyclageECSLimit" size="50" />
							</s:else>
						</tr>
						<tr>
							<s:if test="%{numConfigLab008V2.unitWater == null}">
								<td><s:text name="label.unitWater" /></td>
								<td><input type="text" name="unitWater"
									placeholder='Enter Unit of Water module' size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield value="%{numConfigLab008V2.unitWater}"
									name="unitWater" key="label.unitWater" size="50" />
							</s:else>
						</tr>

						<tr>
							<td><s:select id="target"
									label="%{getText('label.boilerType')}"
									list="#{'1':'Urban heat', '2':'Gas boiler', '3':'Fuel boiler', '4':'Wood boiler'}"
									name="numConfigLab008V2.boilerType"
									value="%{numConfigLab008V2.boilerType}" /></td>
						</tr>

						<tr id="energyProvider">
							<s:if test="%{isCreate}">
								<td><s:text name="label.energyProvider" /></td>
								<td><input type="text" name="energyProvider" id="energy"
									placeholder='Enter Energy Provider' size="50" /></td>
							</s:if>
							<s:else>
								<s:textfield value="%{numConfigLab008V2.energyProvider}"
									id="energyProvider" placeholder='Enter Energy Provider'
									name="energyProvider" key="label.energyProvider" size="50" />
							</s:else>
						</tr>

						<tr id="subscribed">
							<s:if test="%{isCreate}">
								<td><s:text name="label.subscribedPower" /></td>
								<td><input data-validate-func="number" id="subInput"
									data-validate-hint="Only number is accepted" type="text"
									name="subscribedPower" placeholder='Enter Subcribed Power'
									size="50" /></td>
							</s:if>
							<s:else>
								<td><s:textfield data-validate-func="number"
										id="subscribed" data-validate-hint="Only number is accepted"
										value="%{numConfigLab008V2.subscribedPower}"
										placeholder='Enter Subcribed Power' name="subscribedPower"
										key="label.subscribedPower" size="50" /></td>
							</s:else>
						</tr>
						<tr>
							<td><s:textfield id="productionENR"
									value="%{numConfigLab008V2.productionENR}"
									placeholder='Enter productionENR' name="productionENR"
									key="label.productionENR" size="50" /></td>
						</tr>
						<tr>
							<td><s:text name="label.usefor" /></td>
							<td>
								<div>
									<s:checkbox theme="simple" name="usedECSproduction"
										id="usedECSproduction"
										value="%{numConfigLab008V2.usedECSproduction}" />
									ECS Production &nbsp;&nbsp;&nbsp;&nbsp;
									<s:checkbox theme="simple" name="usedHeatproduction"
										id="usedHeatproduction"
										value="%{numConfigLab008V2.usedHeatproduction}" />
									Heat Production
								</div>
							</td>
						</tr>

						<s:if test="%{!isCreate}">
							<tr>
								<s:if
									test="%{numConfigLab008V2.moduleVentilationLst == null || numConfigLab008V2.moduleVentilationLst.size() == 0}">
									<td><s:text name="label.modulVentilation" /></td>
									<td><s:if
											test="%{numConfigLab008V2.moduleVentilationLst == null || numConfigLab008V2.moduleVentilationLst.size() == 0}">
											<s:text name="label.empty" />
										</s:if>
										<button type="button" id="btncreateModuleVentilationECS"
											class="button info block-shadow-info text-shadow">
											<s:text name="label.button.createModuleVentilationLst" />
										</button></td>
								</s:if>
								<s:else>
									<td><s:text name="label.modulVentilation" /></td>
									<td>
										<button type="button" class="button info"
											id="btnShowAlllab008ModuleVentilationLst">Show All Module
											Ventilation</button>
									</td>
								</s:else>
							</tr>
							<tr>
								<s:if
									test="%{numConfigLab008V2.moduleWaterLst == null || numConfigLab008V2.moduleWaterLst.size() == 0}">
									<td><s:text name="label.moduleWater" /></td>
									<td><s:if
											test="%{numConfigLab008V2.moduleWaterLst == null || numConfigLab008V2.moduleWaterLst.size() == 0}">
											<s:text name="label.empty" />
										</s:if>
										<button type="button" id="btncreateModuleWaterECS"
											class="button info block-shadow-info text-shadow">
											<s:text name="label.button.createModuleWaterLst" />
										</button></td>
								</s:if>
								<s:else>
									<td><s:text name="label.moduleWater" /></td>
									<td>
										<button type="button" class="button info"
											id="btnShowAlllab008ModuleWateLst">Show All Module
											Water</button>
									</td>
								</s:else>
							</tr>
							<tr>
								<s:if
									test="%{numConfigLab008V2.lab008ConsommationChauffageLst == null || numConfigLab008V2.lab008ConsommationChauffageLst.size() == 0}">
									<td><s:text name="label.consommationChauffage" /></td>
									<td><s:if
											test="%{numConfigLab008V2.lab008ConsommationChauffageLst == null || numConfigLab008V2.lab008ConsommationChauffageLst.size() == 0}">
											<s:text name="label.empty" />
										</s:if>
										<button type="button"
											id="btncreateLab008ConsommationChauffageLst"
											class="button info block-shadow-info text-shadow">
											<s:text
												name="label.button.createLab008ConsommationChauffageLst" />
										</button></td>
								</s:if>
								<s:else>
									<td><s:text name="label.consommationChauffage" /></td>
									<td>
										<button type="button" class="button info"
											id="btnShowAlllab008ConsommationChauffageLst">Show
											All Consommation Chauffage</button>
									</td>
								</s:else>
							</tr>
							<tr>
								<s:if
									test="%{numConfigLab008V2.lab008ConsommationECSLst == null || numConfigLab008V2.lab008ConsommationECSLst.size() == 0}">
									<td><s:text name="label.consommationECS" /></td>
									<td><s:if
											test="%{numConfigLab008V2.lab008ConsommationECSLst == null || numConfigLab008V2.lab008ConsommationECSLst.size() == 0}">
											<s:text name="label.empty" />
										</s:if>
										<button type="button" id="btncreateConsommationECS"
											class="button info block-shadow-info text-shadow">
											<s:text name="label.button.createConsommationECS" />
										</button></td>
								</s:if>
								<s:else>
									<td><s:text name="label.consommationECS" /></td>
									<td>
										<button type="button" class="button info"
											id="btnShowAllConsommationECSLst">Show All
											Consommation ECS</button>
									</td>
								</s:else>
							</tr>
							<tr>
								<s:if
									test="%{numConfigLab008V2.lab008TemperatureAmbianteLogementLst == null || numConfigLab008V2.lab008TemperatureAmbianteLogementLst.size() == 0}">
									<td><s:text name="label.temperatureAmbianteLogement" /></td>
									<td><s:if
											test="%{numConfigLab008V2.lab008TemperatureAmbianteLogementLst == null || numConfigLab008V2.lab008TemperatureAmbianteLogementLst.size() == 0}">
											<s:text name="label.empty" />
										</s:if>
										<button type="button"
											id="btncreateTemperatureAmbianteLogement"
											class="button info block-shadow-info text-shadow">
											<s:text name="label.button.createTemperatureAmbianteLogement" />
										</button></td>
								</s:if>
								<s:else>
									<td><s:text name="label.temperatureAmbianteLogement" /></td>
									<td>
										<button type="button" class="button info"
											id="btnShowAllTemperatureAmbianteLogementLst">Show
											All Temperature Ambiante Logement</button>
									</td>
								</s:else>
							</tr>
							<tr>
								<s:if
									test="%{numConfigLab008V2.lab008TemperatureProductionChauffageLst == null || numConfigLab008V2.lab008TemperatureProductionChauffageLst.size() == 0}">
									<td><s:text name="label.temperatureProductionChauffage" /></td>
									<td><s:if
											test="%{numConfigLab008V2.lab008TemperatureProductionChauffageLst == null || numConfigLab008V2.lab008TemperatureProductionChauffageLst.size() == 0}">
											<s:text name="label.empty" />
										</s:if>
										<button type="button"
											id="btncreateTemperatureProductionChauffage"
											class="button info block-shadow-info text-shadow">
											<s:text
												name="label.button.createTemperatureProductionChauffage" />
										</button></td>
								</s:if>
								<s:else>
									<td><s:text name="label.temperatureProductionChauffage" /></td>
									<td>
										<button type="button" class="button info"
											id="btnShowAllTemperatureProductionChauffageLst">Show
											All Temperature Production Chauffage</button>
									</td>
								</s:else>
							</tr>
							<tr>
								<s:if
									test="%{numConfigLab008V2.lab008TemperatureProductionECSLst == null || numConfigLab008V2.lab008TemperatureProductionECSLst.size() == 0}">
									<td><s:text name="label.temperatureProductionECS" /></td>
									<td><s:if
											test="%{numConfigLab008V2.lab008TemperatureProductionECSLst == null || numConfigLab008V2.lab008TemperatureProductionECSLst.size() == 0}">
											<s:text name="label.empty" />
										</s:if>
										<button type="button" id="btncreateTemperatureProductionECS"
											class="button info block-shadow-info text-shadow">
											<s:text name="label.button.createTemperatureProductionECS" />
										</button></td>
								</s:if>
								<s:else>
									<td><s:text name="label.temperatureProductionECS" /></td>
									<td>
										<button type="button" class="button info"
											id="btnShowAllTemperatureProductionECSLst">Show All
											Temperature Production ECS</button>
									</td>
								</s:else>
							</tr>
							<tr>
								<s:if
									test="%{numConfigLab008V2.lab008TemperatureRecyclageECSLst == null || numConfigLab008V2.lab008TemperatureRecyclageECSLst.size() == 0}">
									<td><s:text name="label.temperatureRecyclageECS" /></td>
									<td><s:if
											test="%{numConfigLab008V2.lab008TemperatureRecyclageECSLst == null || numConfigLab008V2.lab008TemperatureRecyclageECSLst.size() == 0}">
											<s:text name="label.empty" />
										</s:if>
										<button type="button" id="btncreateTemperatureRecyclageECS"
											class="button info block-shadow-info text-shadow">
											<s:text name="label.button.createTemperatureRecyclageECS" />
										</button></td>
								</s:if>
								<s:else>
									<td><s:text name="label.temperatureRecyclageECS" /></td>
									<td>
										<button type="button" class="button info"
											id="btnShowAllTemperatureRecyclageECSLst">Show All
											Temperature Recyclage ECS</button>
									</td>
								</s:else>
							</tr>
						</s:if>
						<!-- 						<tr> -->
						<%-- 							<s:textfield value="%{numConfigLab008V2.uriIcon}" name="uriIcon" --%>
						<%-- 								key="label.uriIcon" size="50" /> --%>
						<!-- 						</tr> -->
						<tr>
							<td></td>
							<td>
								<div class="tbl-button">
									<input type="submit" id="btnSaveBean"
										value="<s:text name="label.save"/>" class="button primary" />
									<s:if test="%{isCreate}">
										<a class="button bg-red bg-active-darkRed fg-white"
											href="<s:url action='cancelConfigLab008V2' />"><s:text
												name="label.cancel" /></a>
									</s:if>
									<s:else>
										<a class="button bg-red bg-active-darkRed fg-white"
											id="delete-subscription"
											href="
										<s:url action='deleteConfigLab008V2'>
										<s:param name="id" value="%{numConfigLab008V2.id}"></s:param>
										<s:param name="siteId" value="%{numConfigLab008V2.siteId}"></s:param>
									 	</s:url>">
											<s:text name="label.delete" />
										</a>
									</s:else>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</s:if>
			<s:else>
				<s:text name="text.nodata" />
			</s:else>
		</div>
	</div>
	
	

	<div data-role="dialog" data-overlay="true"
		data-overlay-color="op-dark" data-close-button="true"
		id="listLab008ConsommationChauffageDialog">
		<table class="table hovered bordered">
			<thead>
				<tr>
					<th>Id</th>
					<th>ECS Name</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="numConfigLab008V2.lab008ConsommationChauffageLst">
					<tr>
						<td><s:property value="id" /></td>
						<td><s:property value="name" /></td>
						<td>
							<button type="button"
								id="btnEditLab008ConsommationChauffage<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnEditLab008ConsommationChauffage cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
								id="btnRemoveLab008ConsommationChauffage<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnRemoveLab008ConsommationChauffage cycle-button large-button">
								<span class="mif-bin mif-ani-hover-pass"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="3">
						<button type="button" id="btncreateLab008ConsommationChauffageLst"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div data-role="dialog" data-overlay="true"
		data-overlay-color="op-dark" data-close-button="true"
		id="listConsommationECSDialog">
		<table class="table hovered bordered">
			<thead>
				<tr>
					<th>Id</th>
					<th>ECS Name</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="numConfigLab008V2.lab008ConsommationECSLst">
					<tr>
						<td><s:property value="id" /></td>
						<td><s:property value="name" /></td>
						<td>
							<button type="button"
								id="btnEditLab008ConsommationECSLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnEditLab008ConsommationECSLst cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
								id="btnRemoveLab008ConsommationECSLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnRemoveLab008ConsommationECSLst cycle-button large-button">
								<span class="mif-bin mif-ani-hover-pass"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="3">
						<button type="button" id="btncreateConsommationECS"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div data-role="dialog" data-overlay="true"
		data-overlay-color="op-dark" data-close-button="true"
		id="listTemperatureAmbianteLogementDialog">
		<table class="table hovered bordered">
			<thead>
				<tr>
					<th>Id</th>
					<th>ECS Name</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator
					value="numConfigLab008V2.lab008TemperatureAmbianteLogementLst">
					<tr>
						<td><s:property value="id" /></td>
						<td><s:property value="name" /></td>
						<td>
							<button type="button"
								id="btnEditLab008TemperatureAmbianteLogementLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnEditLab008TemperatureAmbianteLogementLst cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
								id="btnRemoveLab008TemperatureAmbianteLogementLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnRemoveLab008TemperatureAmbianteLogementLst cycle-button large-button">
								<span class="mif-bin mif-ani-hover-pass"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="3">
						<button type="button" id="btncreateTemperatureAmbianteLogement"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div data-role="dialog" data-overlay="true"
		data-overlay-color="op-dark" data-close-button="true"
		id="listTemperatureProductionChauffageDialog">
		<table class="table hovered bordered">
			<thead>
				<tr>
					<th>Id</th>
					<th>ECS Name</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator
					value="numConfigLab008V2.lab008TemperatureProductionChauffageLst">
					<tr>
						<td><s:property value="id" /></td>
						<td><s:property value="name" /></td>
						<td>
							<button type="button"
								id="btnEditLab008TemperatureProductionChauffageLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnEditLab008TemperatureProductionChauffageLst cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
								id="btnRemoveLab008TemperatureProductionChauffageLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnRemoveLab008TemperatureProductionChauffageLst cycle-button large-button">
								<span class="mif-bin mif-ani-hover-pass"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="3">
						<button type="button" id="btncreateTemperatureProductionChauffage"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div data-role="dialog" data-overlay="true"
		data-overlay-color="op-dark" data-close-button="true"
		id="listTemperatureProductionECSDialog">
		<table class="table hovered bordered">
			<thead>
				<tr>
					<th>Id</th>
					<th>ECS Name</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator
					value="numConfigLab008V2.lab008TemperatureProductionECSLst">
					<tr>
						<td><s:property value="id" /></td>
						<td><s:property value="name" /></td>
						<td>
							<button type="button"
								id="btnEditLab008TemperatureProductionECSLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnEditLab008TemperatureProductionECSLst cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
								id="btnRemoveLab008TemperatureProductionECSLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnRemoveLab008TemperatureProductionECSLst cycle-button large-button">
								<span class="mif-bin mif-ani-hover-pass"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="3">
						<button type="button" id="btncreateTemperatureProductionECS"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div data-role="dialog" data-overlay="true"
		data-overlay-color="op-dark" data-close-button="true"
		id="listTemperatureRecyclageECSDialog">
		<table class="table hovered bordered">
			<thead>
				<tr>
					<th>Id</th>
					<th>ECS Name</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator
					value="numConfigLab008V2.lab008TemperatureRecyclageECSLst">
					<tr>
						<td><s:property value="id" /></td>
						<td><s:property value="name" /></td>
						<td>
							<button type="button"
								id="btnEditLab008TemperatureRecyclageECSLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnEditLab008TemperatureRecyclageECSLst cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
								id="btnRemoveLab008TemperatureRecyclageECSLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnRemoveLab008TemperatureRecyclageECSLst cycle-button large-button">
								<span class="mif-bin mif-ani-hover-pass"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="3">
						<button type="button" id="btncreateTemperatureRecyclageECS"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div data-role="dialog" data-overlay="true"
		data-overlay-color="op-dark" data-close-button="true"
		id="listModuleWaterECSDialog">
		<table class="table hovered bordered">
			<thead>
				<tr>
					<th>Id</th>
					<th>ECS Name</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="numConfigLab008V2.moduleWaterLst">
					<tr>
						<td><s:property value="id" /></td>
						<td><s:property value="name" /></td>
						<td>
							<button type="button"
								id="btnEditLab008ModuleWaterLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnEditLab008ModuleWaterECSLst cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
								id="btnRemoveLab008ModuleWaterECSLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnRemoveLab008ModuleWaterECSLst cycle-button large-button">
								<span class="mif-bin mif-ani-hover-pass"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="3">
						<button type="button" id="btncreateModuleWaterECS"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	
	<div data-role="dialog" data-overlay="true"
		data-overlay-color="op-dark" data-close-button="true"
		id="listModuleVentilationECSDialog">
		<table class="table hovered bordered">
			<thead>
				<tr>
					<th>Id</th>
					<th>ECS Name</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="numConfigLab008V2.moduleVentilationLst">
					<tr>
						<td><s:property value="id" /></td>
						<td><s:property value="name" /></td>
						<td>
							<button type="button"
								id="btnEditLab008ModuleVentilationLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnEditLab008ModuleVentilationECSLst cycle-button large-button">
								<span class="mif-pencil mif-ani-hover-spanner"></span>
							</button>
							<button type="button"
								id="btnRemoveLab008ModuleVentilationECSLst<s:property value='id' />"
								value='<s:property value="id" />'
								class="btnRemoveLab008ModuleVentilationECSLst cycle-button large-button">
								<span class="mif-bin mif-ani-hover-pass"></span>
							</button>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="3">
						<button type="button" id="btncreateModuleVentilationECS"
							class="cycle-button large-button">
							<span class="mif-plus"></span>
						</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	


	<div data-role="dialog" data-overlay="true"
		data-overlay-color="op-dark" data-width="500" data-height="650"
		data-close-button="true" id="detailECSDialog">
		<div class="row-input">
			<div class="input-control text">
				<input type="text" id="txtECSId" disabled>
			</div>
		</div>
		<div class="row-input">
			<label for="txtECSName">ECS Name</label>
			<div class="input-control modern text">
				<input type="text" id="txtECSName" placeholder="Enter ecs name here" />
				<span class="informer">Please enter ecs name here</span>
			</div>
		</div>
		<div class="row-input">
			<label for="txtModuleNoCSM">Module CSM</label>
			<div class="input-control modern text">
				<input type="text" id="txtListModuleNoCSM"
					placeholder="Enter module csm here" /> <span class="informer">Please
					enter module number here</span>
			</div>
		</div>
		<%-- <div class="row-input">
    	<label for="selectType">Type</label>
    	<div class="input-control select">
    		<select id="selectType">
    			<option value="1">Consommation chauffage</option>
    			<option value="2">Consommation ECS</option>
    			<option value="3">Température ambiante logement</option>
    			<option value="4">Température production chauffage</option>
    			<option value="5">Température production ECS</option>
    			<option value="6">Température recyclage ECS</option>
    		</select>
    	</div>
    </div> --%>
		<input type="hidden" name=selectType id="selectType" value="">
		<div class="row-input">
			<button class="button primary" id="btnSave">Save</button>
		</div>
	</div>
	<script src="../js/initlab.js"></script>
	<script src="../js/lab008.ecs.js"></script>


	<script>
		var power = ($("#target option:selected").val());
		if (power == 1) {
			$("#subscribed").show();
			$("#energyProvider").show();
			$("#subInput").val("");
			$('label[for=subscribed], input#subscribed').show();
			$('label[for=energyProvider], input#energyProvider').show();
		} else {
			$("#subscribed").hide();
			$("#energyProvider").hide();
			$("#subInput").val("1");
			$('label[for=subscribed], input#subscribed').hide();
			$('label[for=energyProvider], input#energyProvider').hide();
		}
		$(document)
				.ready(
						function() {
							var periodVal = ($('#periodValue').val() == 0) ? 1
									: $('#periodValue').val();
							$('#period').val(periodVal);
							if (parseInt($('#periodValue').val()) == 6) {
								$('#daterangepicker').slideDown();
							}
							var fromDate = ($('input[name=fromDate]').val() != '') ? parseDate($(
									'input[name=fromDate]').val())
									: new Date();
							var toDate = ($('input[name=toDate]').val() != '') ? parseDate($(
									'input[name=toDate]').val())
									: new Date();
							var fromDpk = $('#fromDate')
									.datepicker(
											{
												preset : fromDate,
												maxDate : new Date(),
												format : 'dd/mm/yyyy',
												onSelect : function(dateString,
														dateObj) {
													if (dateObj > toDate) {
														alert("Start date can not be greater than end date");
														$('#fromDate input')
																.val(
																		$(
																				'#toDate input')
																				.val());
														fromDate = toDate;
													} else {
														fromDate = dateObj;
													}
												}
											});
							var toDpk = $('#toDate')
									.datepicker(
											{
												preset : toDate,
												maxDate : new Date(),
												format : 'dd/mm/yyyy',
												onSelect : function(dateString,
														dateObj) {
													toDate = dateObj;
													if (dateObj < fromDate) {
														$('#fromDate input')
																.val(
																		$(
																				'#toDate input')
																				.val());
														fromDate = toDate;
													}
												}
											});
							$('#period').on("change", function() {
								if ($(this).val() == 6) {
									$('#daterangepicker').slideDown();
								} else {
									$('#daterangepicker').slideUp();
								}
							});

						});
		function parseDate(dateStr) {
			var arrStr = dateStr.split('/');
			var date = arrStr[2] + '-' + arrStr[1] + '-' + arrStr[0];
			return new Date(date);
		}

		$("#target").change(function() {
			var selectedOption = $("#target option:selected");
			if (selectedOption.val() == 1) {
				$("#subscribed").show();
				$("#energyProvider").show();
				$("#subInput").val("");
				$('label[for=subscribed], input#subscribed').show();
				$('label[for=energyProvider], input#energyProvider').show();
			} else {
				$("#subscribed").hide();
				$("#energyProvider").hide();
				$("#subInput").val("1");
				$('label[for=subscribed], input#subscribed').hide();
				$('label[for=energyProvider], input#energyProvider').hide();
			}
		});
	</script>
</body>