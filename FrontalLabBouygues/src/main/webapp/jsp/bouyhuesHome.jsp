<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="../css/bouygues-style.css" rel="stylesheet" />
	<script src="../js/jquery-1.11.3.min.js"></script>
	<script src="../js/jquery.textfill.min.js"></script>
	<script type="text/javascript">
		/*reload if caching*/
		$(window).bind("pageshow", function(event) {
			var cacheTest = $('#cacheTest').val();
			if (cacheTest == '') {
				$('#cacheTest').val('cached');
			}else{
				window.location.href = window.location.href;
			}
		});
	</script>
	<title>${bouyguesBean.siteName}</title>
</head>
<body>
<input type="hidden" id="cacheTest" value="" />
<!-- <div onclick="javascript:location.href='logOut.action';" id="logout"> -->
<%-- 		<img src="../img/icon-deconnexion.png"> <s:text name="label.logout" /> --%>
<!-- </div> -->
<div class="wrapper">
	<div id="logo-bouygues"></div>
	<div id="telecom">
		<div id="telecom-content">
			<div><s:text name="label.debut"/> : <span>${bouyguesBean.startDate}</span></div>
			<div><s:text name="label.permision"/> : <span>${bouyguesBean.numberPermit}</span></div>
		</div>
	</div>
	<div class="row" id="row1">
		<div class="block-inline" id="bloc-grues-chantier">
			<div id="grues" class="block-inline-title grues">
				<s:text name="label.grues" />
			</div>
			<div class="value">
				<span>
					<s:if test="%{bouyguesBean.gruesChantier == null}">
						---
						</s:if>
					<s:else>
						<s:property
							value="%{getText('format.number',{bouyguesBean.gruesChantier})}" />
					</s:else>
					<s:text name="unit.kWh"></s:text>
					<span class="grues">/ <s:text name="unit.uo" /></span>
				</span>
			</div>
			<div class="coefficient grues">x <s:property value="bouyguesBean.numberGrues" /></div>
		</div>
		<div class="block-inline" id="bloc-pied-de-colonne">
			<div id="pied" class="block-inline-title pied">
				<s:text name="label.pieds" />
			</div>
			<div class="value">
				<span>
					<s:if test="%{bouyguesBean.piedsColonnes == null}">
						---
						</s:if>
					<s:else>
						<s:property
							value="%{getText('format.number',{bouyguesBean.piedsColonnes})}" />
					</s:else>
					<s:text name="unit.kWh"></s:text>
					<span class="pied">/ <s:text name="unit.uo" /></span>
				</span>
			</div>
			<div class="coefficient pied">x <s:property value="bouyguesBean.numberPied" /></div>
		</div>
		<div class="block-inline" id="bloc-cantennement">
			<div id="canten" class="block-inline-title canten">
				<s:text name="label.cantennement" />
			</div>
			<div class="value">
				<span>
					<s:if test="%{bouyguesBean.cantonement == null}">
						---
						</s:if>
					<s:else>
						<s:property
							value="%{getText('format.number',{bouyguesBean.cantonement})}" />
					</s:else>
					<s:text name="unit.kWh"></s:text>
					<span class="canten">/ <s:text name="unit.uo" /></span>
				</span>
			</div>
			<div class="coefficient canten">x <s:property value="bouyguesBean.numberCanton" /></div>
		</div>
	</div>
	<div class="row" id="row2">
		<div class="block-inline" id="grues-illustration"></div>
		<div class="block-inline" id="pied-de-colonne-ilustration"></div>
		<div class="block-inline" id="cantonnement-illustration"></div>
	</div>
	<div id="annotation">
		<div class="line"></div><span> <s:text name="label.annotation"/> </span><div class="line"></div>
	</div>
	<s:if test="%{isReport}">
		<s:a action="exportReport">
			<div id="export"></div>
			<s:param name="linkReport" value="%{linkReport}" />
		</s:a>
	</s:if>
	<s:else>
		<div id="export"></div>
	</s:else>
	<div class="footer">
		<div class="logo-footer">
			<div id="logo1" class="logo-company">
				<img width="150px" height="62px" src="../img/logo-1.png" />
			</div>
			<div id="logo2" class="logo-company">
				<img width="150px" height="62px" src="../img/logo-2.png" />
			</div>
			<div id="logo3" class="logo-company">
				<img width="150px" height="62px" src="../img/logo-3.png" />
			</div>
		</div>
	</div>
</div>
<script src="../js/initlab.js"></script>
</body>
</html>