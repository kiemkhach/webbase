<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
 <meta http-equiv="X-UA-Compatible" content="IE=10,IE=9; IE=8; IE=7; IE=EDGE" /> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="UTF-8" />
<title><tiles:insertAttribute name="title" /></title>
<link href="../css/bouygues-style.css" rel="stylesheet" />
<script src="../js/jquery-1.11.3.min.js"></script>
</head>
<body>
	<tiles:insertAttribute name="body" />
</body>
</html>