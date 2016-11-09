<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="../css/login.css" rel="stylesheet">
<form  name="authentification" id="authentification"
	action="authentification.action" method="post">
	<div class="container-fluid">
		<div class="header"><img class="logo" src="../img/Logo-Caloon.png" /></div>
		<div class="main-login">
			<div class="wrapper-main">
				<div class="login-title">
					<div class="lock"><img class="lock-img" src="../img/Lock-icon-2.png" /></div>
					<div class="title-lock">Connectez-vous à votre espace client</div>
				</div>
				<div class="login-form">
					<div class="wrapper-user-name">
						<div class="wrapper-user"><div class="lable-identifiant">IDENTIFIANT</div>
							<div class="indentifiant"><input type="text" name="userName" value="" id="userName" autocomplete="off"></div>
						</div>
					</div>
					<div class="wrapper-user-pass">
						<div class="lable-pass">MOT DE PASSE</div>
							<div class="motdepasse"><input type="password" name="password" id="password" autocomplete="off">
						</div>
					</div>
					<div class="connextion"><button type="submit" class="submit">CONNEXION</button></div>
				</div>		
			</div>
		</div>
	</div>	
</form>

