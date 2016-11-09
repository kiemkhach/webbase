<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login</title>
<link href="../css/login.css" rel="stylesheet">
</head>
<body>
	<form name="authentification" id="authentification"
		action="authentification.action" method="post">
		<div id="loginForm">
			<div id="background_top"></div>
			<div id="loginInputArea">
				<div id="login_logo" class="logo1"></div>
				<div class="login_mess mess_error">
					<s:if test="%{mess!=null}">
						<s:text name="error.login" />
					</s:if>
				</div>
				<div>
					<div id="usernameField">
						<div class="username_icon"></div>
						<s:textfield name="userName" autocomplete="off" theme="simple" />
					</div>
				</div>
				<div>
					<div id="passwordField">
						<div class="password_icon"></div>
						<s:password name="password" autocomplete="off" theme="simple" />
						<br>
					</div>
				</div>
				<s:submit id="authentification_button" theme="simple" value="" />
				<div id="login_footer">
					<div id="login_logo_bottom" class="logo2 "></div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>

