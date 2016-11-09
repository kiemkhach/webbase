<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Idex Releves</title>
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../metro/css/metro-icons.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
</head>
<body>
<div class="main padding20" style="padding-bottom: 50px">
				<input type="hidden" id="messageReleves" value="<s:property value='messageReleves'/>" />
		
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
                            <div class="heading">
                            <div style="width: 49%; display:inline-block;padding-left: 10px"><span class="title">Import/Export Releves</span>
                            </div>
                            </div>
                            <div class="content padding10">
                            
                                <div class="grid">
                					<div class="row cells3">
                    				<div class="cell">
                    				<h4 class="sub-header">Select Counter</h4>
                    				<div class="input-control full-size">
									<select name="selectIdexCounter" id="selectIdexCounter" style="display: block">
										<s:iterator value="idexCounterList">
											<option  value="<s:property value="idexCounterId"/>" >
											<s:property value="name" /></option>
										</s:iterator>
									</select>
									</div>               				
                    				</div>
                    				<div class="cell">
                    				    <h4 class="sub-header">Select File</h4>
                       					<form action="importRelevesCSV.action" method="post" 
                       					enctype="multipart/form-data" data-role="validator" data-hint-mode="hint" >
                        				<input type="hidden" id="idexInstallationId"
											value="<s:property value="idexInstallationId" />" name="idexInstallationId"/>
                        				<div class="input-control file full-size" data-role="input">
                        					<input type="hidden" class="selectCounterValue" name="idexCounterId" data-validate-func="required"/>
                           					<input type="file" name="importRelevesCSVFile" accept=".csv" id="importRelevesCSVFile" 
                           					data-validate-func="required" data-validate-hine="This field can not be empty!">
                            				<button class="button"><span class="mif-folder"></span></button>
                       					</div>
                        				<button type="submit" class="button primary">Import</button>
                        				</form>
                        				<form action="exportRelevesByCounterIdToCSV.action" method="post" 
                        				enctype="multipart/form-data" data-role="validator" >
                        				<input type="hidden" class="selectCounterValue" name="idexCounterId" data-validate-func="required" />				
                        				<button type="submit" class="button primary" id="exportById">Export</button>
                        				</form>
                        				<form action="exportAllRelevesToCSV.action" method="post" enctype="multipart/form-data">
                        				<button type="submit" class="button primary">Export All</button>
                        				</form>                    					
                    				</div>
                    				</div>
                    			</div>
                            </div>
                        </div>
                    </div>
			</div>
						<div class="grid">
				  <div class="cell">
                        <div class="panel">
                            <div class="heading">
                            <div style="width: 49%; display:inline-block;padding-left: 10px"><span class="title">Import/Export DJU</span>
                            </div>
                            </div>
                            <div class="content padding10">
                                <div class="grid">
                					<div class="row cells3">
                    				<div class="cell">
                    				    <h4 class="sub-header">Select File</h4>
                       					<form action="importLab010DJUByInstallation.action" method="post" 
                       					enctype="multipart/form-data" data-role="validator" data-hint-mode="hint" >
                        				<input type="hidden" id="idexInstallationId"
											value="<s:property value="idexInstallationId" />" name="idexInstallationId"/>
                        				<div class="input-control file full-size" data-role="input">
                           					<input type="file" name="importDJUCSVFile" accept=".csv" id="importDJUCSVFile" 
                           					data-validate-func="required" data-validate-hine="This field can not be empty!">
                            				<button class="button"><span class="mif-folder"></span></button>
                       					</div>
                        				<button type="submit" class="button primary">Import</button>
                        				</form>
                        				<form action="exportLab010DJUByInstallationToCSV.action" method="post" 
                        				enctype="multipart/form-data">
                        				<input type="hidden" id="idexInstallationId"
											value="<s:property value="idexInstallationId" />" name="idexInstallationId"/>
                        				<button type="submit" class="button primary" id="exportById">Export</button>
                        				</form>
                        				<form action="exportAllLab010DJUToCSV.action" method="post" enctype="multipart/form-data">
                        				<button type="submit" class="button primary">Export All</button>
                        				</form>                    					
                    				</div>
                    				</div>
                    			</div>
                            </div>
                        </div>
                    </div>
			</div>
		</div>
	<script src="../js/jquery-1.11.3.min.js"></script>
	<script src="../js/jquery.dataTables.min.js"></script>	
	<script src="../metro/js/metro.min.js"></script>
	<script src="../js/lab010IdexReleves.js"></script>
</body>
