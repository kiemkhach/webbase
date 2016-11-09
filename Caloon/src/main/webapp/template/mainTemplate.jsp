<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=10">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><tiles:insertAttribute name="title" ignore="true" /></title>
 
<link rel="stylesheet" type="text/css" href="../css/fontUtil.css" />
<link rel="stylesheet" type="text/css" href="../css/ka-loading.css" />
<link rel="stylesheet" type="text/css" href="../css/daterangepicker.css" />
<link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="../css/syndic.css" />

<meta http-equiv="X-UA-Compatible"
	content="IE=10,IE=9; IE=8; IE=7; IE=EDGE" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script src="../js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	/*reload if caching*/
		$(window).bind("pageshow", function(event) {
    		if (event.originalEvent.persisted) {
        		window.location.reload();
    		}
		});
	</script>
</head>
<body>
	<!-- <input type="hidden" id="fromDateBottom" value="">
	<input type="hidden" id="toDateBottom" value=""> -->
	<div class="wrapper-header">
		<div class="header-content">
			<div class="caloon-icon">
				<img class="caloon-img" src="../img/caloon-icon.png" />
			</div>
		</div>
		<div class="header-bottom">
			<div class="address-wrapper">
				<div class="address"><s:property value="#session.address_name"/></div>
				<div class="address-value"><s:property value="#session.address_value"/></div>
				<div class="postal-code-value"><s:property value="#session.postal_code"/></div>
				<div class="city-value"><s:property value="#session.city"/></div>
			</div>
			<div class="welcom-client">
				<div class="welcome">BIENVENUE</div>
				<div class="welcome-client-name"><span style="text-transform: capitalize;"> <s:property value="#session.first_name_key"/></span> <s:property value="#session.last_name_key"/></div>
			</div>
			<div class="logout-btn">
				<span class="logout"> <a href="logout.action">DÉCONNEXION</a>
				</span>
			</div>
		</div>
	</div>
	<!--end wrapper-header-->

	<div class="body">
		<tiles:insertAttribute name="body" />
	</div>
</body>
</html>

