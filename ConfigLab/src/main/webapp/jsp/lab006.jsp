<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <s:if test="%{!isCreate}">
        <title><s:property value="%{numConfigLab006.siteName}"/></title>
    </s:if>
    <s:else>
        <title><s:property value="lab.name"/> - <s:text name="label.create.siteId"/></title>
    </s:else>
    <link href="../metro/css/metro.min.css" rel="stylesheet"/>
    <link href="../metro/css/metro-icons.min.css" rel="stylesheet"/>
    <link href="../css/lab.css" rel="stylesheet"/>
    <script src="../js/jquery-1.11.3.min.js"></script>
    <script src="../metro/js/metro.min.js"></script>
    <style type="text/css">
        #detailClientDialog{
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
        .row-input{
            width: 90%;
        }
        .row-input .input-control{
            width: 100%;
        }
    </style>
</head>
<body>
<div class="main">
    <input type="hidden" id="subscriptionId" value="${numConfigLab006.id}"/>
    <div class="menu">
        <a class="button bg-teal bg-active-darkTeal fg-white" href="<s:url action='home' />"><s:text
                name="label.home"/></a>
        <a class="button bg-teal bg-active-darkTeal fg-white" href="<s:url action='logOut' />"><s:text
                name="label.logout"/></a>
    </div>
    <s:if test="%{!isCreate}">
        <a class="" href="<s:url action='createConfigLab006' />" style="margin-left: 15px;"><s:text
                name="label.create.siteId"/></a>
        <s:if test="%{listAllConfig != null && listAllConfig.size > 0}">
            <form action="selectSiteLab006">
                <s:select class="input-control select" list="listAllConfig"
                          listKey="siteId" listValue="siteName" name="siteId"
                          value="%{numConfigLab006.siteId}" onchange="this.form.submit()" cssClass="select-tag"/>
            </form>
        </s:if>
    </s:if>
    <input type="hidden" id="message" value="<s:property value='mes'/>"/>
    <div class="lab-name">
        <img src="../img/logos-immeuble.png">
        <div class="text-lab-name">
            <s:property value="%{lab.name}"/>
        </div>
    </div>
    <div class="config-site">
        <s:if test="%{isCreate}">
            <div class="sub-leader">
                <s:text name="label.create.siteId"/>
            </div>
        </s:if>
        <s:else>
            <s:if test="%{listAllConfig != null && listAllConfig.size > 0}">
                <div class="sub-leader">
                    <s:text name="label.siteId"/>
                    :
                    <s:property value="%{numConfigLab006.siteId}"/>
                </div>
                <div class="unregister-user">
                    <form action="unregisterUserForLab006">
                        <s:if test="%{listUsers != null && listUsers.size > 0}">
                            <s:iterator value="listUsers" status="rows">
                                <label class="input-control checkbox small-check">
                                    <s:if test="%{listUsers[#rows.index] == username_lab_service}">
                                        <input type="checkbox" value="<s:property/>" disabled="disabled"/>
                                    </s:if>
                                    <s:else>
                                        <input type="checkbox" value="<s:property/>" name="listUser"/>
                                    </s:else>
                                    <span class="check"></span>
                                    <span class="caption"><s:property/></span>
                                </label>
                            </s:iterator>
                            <input type="submit" class="button primary" value="<s:text name="label.unregister"/>"/>
                        </s:if>
                    </form>
                </div>
                <div class="register-user">
                    <form action="registerUserForLab006">
                        <input type="text" name="listUserRegist" size="50"
                               placeholder="<s:text name="placeholder.user.list"/>"/>
                        <input type="submit" class="button primary" value="<s:text name="label.register"/>"/>
                    </form>
                </div>
            </s:if>
        </s:else>
        <s:if test="%{(!isCreate && listAllConfig != null && listAllConfig.size > 0) || isCreate}">
            <form action="configLab006" method="post" enctype="multipart/form-data">
                <s:hidden name="isCreate" value="%{isCreate}"/>
                <s:hidden name="reportName" value="%{numConfigLab006.reportName}"/>
                <table>
                    <s:if test="%{!isCreate}">
                        <s:hidden name="id" value="%{numConfigLab006.id}"/>
                        <tr>
                            <s:textfield value="%{numConfigLab006.siteId}" name="siteId" key="label.siteId"/>
                        </tr>
                    </s:if>
                    <tr>
                        <s:textfield value="%{numConfigLab006.siteName}" name="siteName"
                                     key="label.siteName" size="50"/>
                    </tr>
                    <tr>
                        <s:if
                                test="%{numConfigLab006.commonPortionModuleId == null || numConfigLab006.commonPortionModuleId == ''}">
                            <td><s:text name="label.commonPortionModuleId"/></td>
                            <td><input type="text" name="commonPortionModuleId"
                                       placeholder='<s:text name="placeholder.module.list" />' size="50"/></td>
                        </s:if>
                        <s:else>
                            <s:textfield value="%{numConfigLab006.commonPortionModuleId}" name="commonPortionModuleId"
                                         key="label.commonPortionModuleId" size="50"/>
                        </s:else>
                    </tr>
                    <tr>
                        <s:if
                                test="%{numConfigLab006.energyConsumptionModuleId == null || numConfigLab006.energyConsumptionModuleId == ''}">
                            <td><s:text name="label.energyConsumptionModuleId"/></td>
                            <td><input type="text" name="energyConsumptionModuleId"
                                       placeholder='<s:text name="placeholder.module.list" />' size="50"/></td>
                        </s:if>
                        <s:else>
                            <s:textfield value="%{numConfigLab006.energyConsumptionModuleId}"
                                         name="energyConsumptionModuleId" key="label.energyConsumptionModuleId"
                                         size="50"/>
                        </s:else>
                    </tr>
                    <tr>
                        <s:if
                                test="%{numConfigLab006.summerStartDate == null || numConfigLab006.summerStartDate == ''}">
                            <td><s:text name="label.summerStartDate"/></td>
                            <td>
                                <div class="input-control text" id="dpkSummerStartDate"
                                    data-role="datepicker"
                                     data-locale="fr"
                                     data-format="dd/mm"
                                >
                                    <input type="text" name="summerStartDate"
                                           placeholder='dd/MM' size="50"/>
                                    <button class="button"><span class="mif-calendar"></span></button>
                                </div>
                            </td>
                        </s:if>
                        <s:else>
                            <td><s:text name="label.summerStartDate"/></td>
                            <td>
                                <div class="input-control text" id="dpkSummerStartDate"
                                     data-role="datepicker"
                                     data-locale="fr"
                                     data-format="dd/mm"
                                >
                                    <input type="text" name="summerStartDate" placeholder='dd/MM' size="50"
                                        value="${numConfigLab006.summerStartDate}"/>
                                    <%--<s:textfield value="%{numConfigLab006.summerStartDate}"--%>
                                             <%--name="summerStartDate" key="label.summerStartDate"--%>
                                             <%--size="50"/>--%>
                                    <button type="button" class="button"><span class="mif-calendar"></span></button>
                                </div>
                            </td>
                        </s:else>
                    </tr>
                    <tr>
                        <s:if
                                test="%{numConfigLab006.winterStartDate == null || numConfigLab006.winterStartDate == ''}">
                            <td><s:text name="label.winterStartDate"/></td>
                            <td>
                                <div class="input-control text" id="dpkWinterStartDate"
                                     data-role="datepicker"
                                     data-locale="fr"
                                     data-format="dd/mm"
                                >
                                    <input type="text" name="winterStartDate"
                                           placeholder='dd/MM' size="50"/>
                                    <button type="button" class="button"><span class="mif-calendar"></span></button>
                                </div>
                            </td>
                        </s:if>
                        <s:else>
                            <td><s:text name="label.winterStartDate"/></td>
                            <td>
                                <div class="input-control text" id="dpkWinterStartDate"
                                     data-role="datepicker"
                                     data-locale="fr"
                                     data-format="dd/mm"
                                >
                                    <input type="text" placeholder='dd/MM' name="winterStartDate"
                                           value="${numConfigLab006.winterStartDate}" size="50">
                                    <button type="button" class="button"><span class="mif-calendar"></span></button>
                                </div>
                            </td>
                            <%--<s:textfield value="%{numConfigLab006.winterStartDate}"--%>
                                         <%--name="winterStartDate" key="label.winterStartDate"--%>
                                         <%--size="50"/>--%>
                        </s:else>
                    </tr>
                    <tr>
                        <s:if
                                test="%{numConfigLab006.startHourHC == null}">
                            <td><s:text name="label.startHourHC"/></td>
                            <td><input type="text" name="startHourHC"
                                       placeholder='Enter start hour HC' size="50"/></td>
                        </s:if>
                        <s:else>
                            <s:textfield value="%{numConfigLab006.startHourHC}"
                                         name="startHourHC" key="label.startHourHC"
                                         size="50"/>
                        </s:else>
                    </tr>
                    <tr>
                        <s:if
                                test="%{numConfigLab006.startHourHP == null}">
                            <td><s:text name="label.startHourHP"/></td>
                            <td><input type="text" name="startHourHP"
                                       placeholder='Enter start hour HP' size="50"/></td>
                        </s:if>
                        <s:else>
                            <s:textfield value="%{numConfigLab006.startHourHP}"
                                         name="startHourHP" key="label.startHourHP"
                                         size="50"/>
                        </s:else>
                    </tr>
                    <tr>
                        <%-- <s:if
                                test="%{numConfigLab006.listClients == null || numConfigLab006.listClients.size() == 0}">
                            <td><s:text name="label.listClients"/></td>
                            <td>
                                <s:text name="label.empty"/>
                                <button type="button" id="btnCreateClient"
                                    class="button info block-shadow-info text-shadow"><s:text
                                        name="label.button.createClient"/></button>
                            </td>
                        </s:if> --%>
                        <s:if
                                test="%{numConfigLab006.listClients == null || numConfigLab006.listClients.size() == 0}">
                            <td><s:text name="label.listClients"/></td>
                            <td>
                            	<s:if test="%{numConfigLab006.listClients == null || numConfigLab006.listClients.size() == 0}">
                                	<s:text name="label.empty"/>
                                </s:if>
                                <button type="button" id="btnCreateClient"
                                    class="button info block-shadow-info text-shadow"><s:text
                                        name="label.button.createClient"/></button>
                            </td>
                        </s:if>
                        <s:else>
                            <td><s:text name="label.listClients"/></td>
                            <td>
                                <button type="button" class="button info" id="btnShowAllClients">Show All Clients</button>
                            </td>
                        </s:else>
                    </tr>
                    <!-- 						<tr> -->
                        <%-- 						<s:textfield value="%{numConfigLab006.logo}" name="logo" --%>
                        <%-- 							key="label.logo" size="50" /> --%>
                    <!-- 						</tr> -->
                    <tr>
                        <s:textfield value="%{numConfigLab006.uriIcon}" name="uriIcon"
                                     key="label.uriIcon" size="50"/>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <div class="tbl-button">
                                <input type="submit" value="<s:text name="label.save"/>" class="button primary"/>
                                <s:if test="%{isCreate}">
                                    <a class="button bg-red bg-active-darkRed fg-white"
                                       href="<s:url action='cancelConfigLab006' />"><s:text name="label.cancel"/></a>
                                </s:if>
                                <s:else>
                                    <a class="button bg-red bg-active-darkRed fg-white" id="delete-subscription" href="
										<s:url action='deleteConfigLab006'>
										<s:param name="id" value="%{numConfigLab006.id}"></s:param>
										<s:param name="siteId" value="%{numConfigLab006.siteId}"></s:param>
									 	</s:url>">
                                        <s:text name="label.delete"/>
                                    </a>
                                </s:else>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </s:if>
        <s:else>
            <s:text name="text.nodata"/>
        </s:else>
    </div>
</div>
<div data-role="dialog" data-overlay="true" data-overlay-color="op-dark"
     data-close-button="true"
     id="listClientDialog">
    <table class="table hovered bordered">
        <thead>
        <tr>
            <th>Id</th>
            <th>Client Name</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="numConfigLab006.listClients">
            <tr>
                <td><s:property value="id"/></td>
                <td><s:property value="clientName"/></td>
                <td>
                    <button type="button" id="btnEditClient<s:property value='id' />" value='<s:property value="id" />'
                            class="btnEditClient cycle-button large-button"><span
                            class="mif-pencil mif-ani-hover-spanner"></span></button>
                    <button type="button" id="btnRemoveClient<s:property value='id' />"
                            value='<s:property value="id" />' class="btnRemoveClient cycle-button large-button"><span
                            class="mif-bin mif-ani-hover-pass"></span></button>
                </td>
            </tr>
        </s:iterator>
        <tr>
            <td colspan="3">
                <button type="button" id="btnCreateClient" class="cycle-button large-button"><span
                        class="mif-plus"></span></button>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<div data-role="dialog" data-overlay="true" data-overlay-color="op-dark"
     data-width="500"
     data-height="650"
     data-close-button="true"
     id="detailClientDialog">
    <div class="row-input">
        <div class="input-control text">
            <input type="text" id="txtClientId" disabled>
        </div>
    </div>
    <div class="row-input">
        <label for="txtClientName">Client Name</label>
        <div class="input-control modern text">
            <input type="text" id="txtClientName" placeholder="Enter client name here"/>
            <%--<span class="label">Client name</span>--%>
            <span class="informer">Please enter client name here</span>
            <%--<span class="placeholder">Enter client name here</span>--%>
        </div>
    </div>
    <div class="row-input">
        <label for="txtListModuleHCE">Module number HCE</label>
        <div class="input-control modern text">
            <input type="text" id="txtListModuleHCE" placeholder="Enter module HCE here"/>
            <span class="informer">Please enter module number here</span>
            <%--<span class="placeholder">Enter list module HCE here</span>--%>
        </div>
    </div>
    <div class="row-input">
        <label for="txtListModuleHPE">Module number HPE</label>
        <div class="input-control modern text">
            <input type="text" id="txtListModuleHPE" placeholder="Enter module HPE here"/>
            <span class="informer">Please enter module number here</span>
            <%--<span class="placeholder">Enter list module HPE here</span>--%>
        </div>
    </div>
    <div class="row-input">
        <label for="txtListModuleHCH">Module number HCH</label>
        <div class="input-control modern text">
            <input type="text" id="txtListModuleHCH" placeholder="Enter module HCH here"/>
            <span class="informer">Please enter module number here</span>
            <%--<span class="placeholder">Enter list module HCH here</span>--%>
        </div>
    </div>
    <div class="row-input">
        <label for="txtListModuleHPH">Module number HPH</label>
        <div class="input-control modern text">
            <input type="text" id="txtListModuleHPH" placeholder="Enter module HPH here"/>
            <span class="informer">Please enter module number here</span>
            <%--<span class="placeholder">Enter list module HPH here</span>--%>
        </div>
    </div>
    <div class="row-input">
        <label for="txtImageClient">Upload picture</label>
        <input type="text" id="txtImageClient" disabled="true" size="20"/>
        <input type="checkbox" id="useDefaultClientImage" name="clientImage" value="clientImage"> Use default Image
        <div class="input-control file" data-role="input" style="width: 235px;">
            <input id="uploadClientImage" type="file" name="fileImage" accept="image/png,image/jpeg"
                   placeholder="<s:text name="label.upload"/>">
            <button class="button">
                <img src="../img/Blank_Folder.png"/>
            </button>
        </div>
    </div>
    <div class="row-input">
        <button class="button primary" id="btnSave">Save</button>
    </div>
</div>
<script src="../js/initlab.js"></script>
<script src="../js/lab006.client.js"></script>
</body>