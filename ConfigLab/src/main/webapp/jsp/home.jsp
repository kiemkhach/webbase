<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Config Lab</title>
<link href="../css/lab.css" rel="stylesheet" />
<link href="../metro/css/metro.min.css" rel="stylesheet" />
<link href="../css/lab.css" rel="stylesheet" />
<style>
.lab001 {
	background: url('../img/logos-carrefour.png') no-repeat center;
}

.lab002 {
	background: url('../img/logos-contruction.png') no-repeat center;
}

.lab003 {
	background: url('../img/logos-leclerc.png') no-repeat center;
}

.lab004 {
	background: url('../img/logos-yoplait.png') no-repeat center;
}

.lab005 {
	background: url('../img/logos-Grand-oulin.png') no-repeat center;
}

.lab006 {
	background: url('../img/logos-immeuble.png') no-repeat center;
}
.lab007 {
	background: url('../img/logos-la-courneuve.png') no-repeat center;
}
.lab008 {
	background: url('../img/logos-boilers-monitor.png') no-repeat center;
	background-size:contain;
}
.lab010 {
	background: url('../img/logos-IDEX.jpg') no-repeat center;
	background-size:contain;
}
.lab011 {
	background: url('../img/logos-caloon.png') no-repeat center;
	background-size:contain;
}
.labmonitor {
	text-align: center;
	padding-top: 50px;
}

.labmonitor a {
	font-size: 30px;
	font-stretch: extra-expanded;
}

.lab009{
text-align: center;
padding-top: 50px;
}
.lab009 a{
font-size: 30px;
font-stretch: extra-expanded;}
</style>
</head>
<body>
	<div class="menu">
		<a class="button bg-teal bg-active-darkTeal fg-white"
			href="<s:url action='logOut' />">Log Out</a>
	</div>
	<div id="wrapper" class="container">
		<div class="center">
			<div class="logo-img labmonitor">
				<a href='<s:url action="configLab"/>'>
					<s:text name="label.configLab"></s:text>
				</a>
			</div>
			<div class="logo-img lab001" id="logo-lab001"
				onclick="javascript:location.href='redirectLab001.action';"></div>
			<div class="logo-img lab002" id="logo-lab002"
				onclick="javascript:location.href='redirectLab002.action';"></div>
			<div class="logo-img lab003" id="logo-lab003"
				onclick="javascript:location.href='redirectLab003.action';"></div>
			<div class="logo-img lab004" id="logo-lab004"
				onclick="javascript:location.href='redirectLab004.action';"></div>
			<div class="logo-img lab005" id="logo-lab005"
				onclick="javascript:location.href='redirectLab005.action';"></div>
 			<div class="logo-img lab006" id="logo-lab006"
 				onclick="javascript:location.href='redirectLab006.action';"></div>
			<div class="logo-img lab007" id="logo-lab007"
				onclick="javascript:location.href='redirectLab007.action';"></div>
			<div class="logo-img labmonitor">
				<a href="<s:url action='redirectMonitor'/>"> <s:text
						name="label.monitor" />
				</a>
			</div>
			<!-- <div class="logo-img lab008" id="logo-lab008"
				onclick="javascript:location.href='redirectLab008.action';"></div> -->
			<div class="logo-img lab008" id="logo-lab008"
				onclick="javascript:location.href='redirectLab008V2.action';">
			</div>
			<div class = "logo-img lab009">
				<a href="<s:url action = 'redirectLab009'/>"> <s:text
				name = "label.perial"/>
				</a>
			</div>
			<div class="logo-img lab010" id="logo-lab010"
				onclick="javascript:location.href='redirectLab010IdexSite.action';">
			</div>
			<div class="logo-img lab011" id="logo-lab011"
				onclick="javascript:location.href='redirectCaloon.action';">
			</div>
		</div>
	</div>
</body>
</html>