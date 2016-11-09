<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Sub Category</title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
</head>
<style type="text/css">
	.panel>.heading>button {
		
	}
</style>
<body>
		<div class="main padding20" style="padding-bottom: 50px">
				<input type="hidden" id="message" value="<s:property value='message'/>" />
		
		<div class="menu">
			<a class="button bg-teal bg-active-darkTeal fg-white"
				href="redirectLab010IdexSite.action"><s:text name="label.back" /></a> 
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
			<div class="grid">
				  <div class="cell">
                        <div class="panel">
                            <div class="heading" style="padding: .2rem 0;height:100%;">
                            <div style="width: 49%; display:inline-block;padding-left: 10px"><span class="title">Sub Category</span>
                            </div>
                            <div style="width: 50%; display:inline-block; text-align:right">
                                <button id="btnCreateLab010SubCategory" class="cycle-button default-button"
                                onclick="showDialog('#dialogCreateLab010SubCategory')"><span class="mif-plus"></span>
								</button>
								</div>
                            </div>
                            <div class="content padding10">
                            
                                <div class="grid">
                					<div class="row cells3">
                					<div class="cell">
                    				<label class="input-control radio small-check">
			                            <input type="radio" name="selectTypeToImport" checked value="typeEnergyImport">
			                            <span class="check"></span>
			                            <span class="caption">Select Energy Supplier</span>
			                        </label>
			                        <label class="input-control radio small-check">
			                            <input type="radio" name="selectTypeToImport" value="typeSiteImport">
			                            <span class="check"></span>
			                            <span class="caption">Select Site</span>
			                        </label> 
                    				<div class="input-control full-size" id="selectEnergyArea">
									<select onchange="selectCategoryChange()" name="" id="selectEnergyImport" style="display: block" class="filterSelectValue">
										<s:iterator value="numConfigLab010IdexCost.idexEnergySupplierList">
											<option value="<s:property value="idexEnergySupplierId" />"><s:property
													value="name" /></option>
										</s:iterator>
									</select>
									</div>               					
                    				<div class="input-control full-size" id="selectSiteArea" style="display: none" class="filterSelectValue">
									<select onchange="selectCategoryChange()" name="" id="selectSiteImport">
										<s:iterator value="numConfigLab010IdexCost.idexSiteList">
											<option value="<s:property value="idexSiteId" />"><s:property
													value="name" /></option>
										</s:iterator>
									</select>
									</div>
                    				</div>
                    				<div class="cell">
                    				<h4 class="sub-header">Select Sub Category</h4>
                    				<div class="input-control full-size">
									<select name="selectIdexCost" id="selectIdexCost" style="display: block">
										<option value="0" disabled="disabled" />Select A Sub Category</option>
										<s:iterator value="idexCostList">
											<option  style="display:hidden" value="<s:property value="idexCostId"/>" 
											data-energy="<s:property value="idexEnergySupplier.idexEnergySupplierId"/>" 
											data-site="<s:property value="idexSiteIn.idexSiteId"/>"><s:property
													value="name" /></option>
										</s:iterator>
									</select>
									</div>
									<form action="deleteLab010SubCategory.action" method="post" data-role="validator" >
										<input type="hidden" id="idexInstallationId"
											value="<s:property value="idexInstallationId" />" name="idexInstallationId"/>
                        				<input type="hidden" class="selectIdexCostValue" name="idexCostId" data-validate-func="required" />				
                        				<button type="submit" class="button primary" id="btnDeleteSubCategory">Delete</button>
                        				</form>
                        				
                    				</div>
                    				<div class="cell">
                    				    <h4 class="sub-header">Select File</h4>
                       					<form action="importLab010SubCategoryDetailCSV.action" method="post" 
                       					enctype="multipart/form-data" data-role="validator" data-hint-mode="hint" >
                        				<input type="hidden" id="idexInstallationId"
											value="<s:property value="idexInstallationId" />" name="idexInstallationId"/>
                        				<div class="input-control file full-size" data-role="input">
                        					<input type="hidden" class="selectIdexCostValue" name="idexCostId" data-validate-func="required"/>
                           					<input type="file" name="importCSVFile" accept=".csv" id="importCsvFile" 
                           					data-validate-func="required" data-validate-hine="This field can not be empty!">
                            				<button class="button"><span class="mif-folder"></span></button>
                       					</div>
                        				<button type="submit" class="button primary">Import</button>
                        				</form>
                        				<form action="exportSubCategoryDetailByIdToCSV.action" method="post" 
                        				enctype="multipart/form-data" data-role="validator" >
                        				<input type="hidden" class="selectIdexCostValue" name="idexCostId" data-validate-func="required" />				
                        				<button type="submit" class="button primary" id="exportById">Export</button>
                        				</form>
                        				<form action="exportAllSubCategoryDetailToCSV.action" method="post" enctype="multipart/form-data">
                        				<button type="submit" class="button primary">Export All</button>
                        				</form>                    					
                    				</div>
                    				</div>
                    			</div>
                            </div>
                        </div>
                    </div>
			</div>
					<!-- Create Lab010 SubCategory Dialog -->
	<div class="padding20"
		 data-role="dialog" data-overlay="true" data-width="500"
			data-overlay-color="op-dark" data-close-button="true" id="dialogCreateLab010SubCategory">
			<div class="grid">
                    				<div class="cell">
                    				<label class="input-control radio small-check">
			                            <input type="radio" name="selectType" checked value="typeEnergy">
			                            <span class="check"></span>
			                            <span class="caption">Select Energy Supplier</span>
			                        </label>
			                        <label class="input-control radio small-check">
			                            <input type="radio" name="selectType" value="typeSite">
			                            <span class="check"></span>
			                            <span class="caption">Select Site</span>
			                        </label>
			</div>
			<form action="createLab010SubCategory.action" method="post" data-role="validator" data-hint-mode="hint" id="formByEnergySupplier">
			<input type="hidden" id="idexInstallationId"
					value="<s:property value="idexInstallationId" />" name="idexInstallationId"/>
                    				<div class="input-control full-size" id="selectArea">
									<select data-validate-func="required" name="numConfigLab010IdexCost.idexEnergySupplierId" id="selectEnergySupplier" style="display: block" 
            							data-validate-hine="This field can not be empty!">
										<option value="0" disabled style="display:none">Select a Energy Supplier</option>
										<s:iterator value="numConfigLab010IdexCost.idexEnergySupplierList">
											<option value="<s:property value="idexEnergySupplierId" />"><s:property
													value="name" /></option>
										</s:iterator>
									</select>
                    				</div>
                    				<div class="input-control text full-size">
                            			<input type="text" placeholder="IdexSubCategory Name" data-validate-func="required"
            							data-validate-hine="This field can not be empty!" name="numConfigLab010IdexCost.idexCostName">
                        			</div>
                    				
			<div class="row-input">
				<button type="submit" class="button primary">Save</button>
			</div>
			</form>
			<form action="createLab010SubCategory.action" method="post" data-role="validator" data-hint-mode="hint" id="formBySite"  style="display: none" >
			<input type="hidden" id="idexInstallationId"
					value="<s:property value="idexInstallationId" />" name="idexInstallationId"/>
                    				<div class="input-control full-size" id="selectArea">
 									<select data-validate-func="required" name="numConfigLab010IdexCost.idexSiteId" id="selectSite"
            							data-validate-hine="This field can not be empty!">
										<option value="0" disabled style="display:none">Select a Site</option>
										<s:iterator value="numConfigLab010IdexCost.idexSiteList">
											<option value="<s:property value="idexSiteId" />"><s:property
													value="name" /></option>
										</s:iterator>
									</select>
                    				</div>
                    				<div class="input-control text full-size">
                            			<input type="text" placeholder="IdexSubCategory Name" data-validate-func="required"
            							data-validate-hine="This field can not be empty!" name="numConfigLab010IdexCost.idexCostName">
                        			</div>
							<div class="row-input">
								<button type="submit" class="button primary">Save</button>
							</div>
			</form>
			                    				</div>
			
			</div>
		</div>
	<script src="../js/jquery-1.11.3.min.js"></script>
	<script src="../js/jquery.dataTables.min.js"></script>	
	<script src="../metro/js/metro.min.js"></script>
	<script src="../js/lab010IdexCost.js"></script>	
</body>
</html>