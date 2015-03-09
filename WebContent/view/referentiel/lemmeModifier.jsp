<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList"%>

<%
	String includer				=	"lemmeModifier.jsp";
	String root					=	request.getContextPath();
	Boolean giveFeedback		=	(Boolean) request.getAttribute("giveFeedback");
	if (giveFeedback == null) {giveFeedback = false; }
	String feedbackMessage		=	(String) request.getAttribute("feedbackMessage");
	if (feedbackMessage == null) {feedbackMessage = ""; }
	
	String id					=	(String) request.getParameter("id");
	String freqLivres			=	(String) request.getParameter("freqlivres");
	String freqFilms			=	(String) request.getParameter("freqfilms");
	
%>

<!DOCTYPE html>
<html>
	<head>

		<jsp:include page="../../WEB-INF/include/head.inc.jsp">
            <jsp:param name="includer" value="<%=includer %>" />
        </jsp:include>

	</head>
	<body>

		<jsp:include page="../../WEB-INF/include/navigation.inc.jsp">
            <jsp:param name="includer" value="<%=includer %>" />
        </jsp:include>
	
		<h1>Modification d'un lemme</h1>
		
		<div id="main-content" class="full-width">

			<div class="form-container">
			
				<form id="lemme-modification-form" method="post" action="<%= root %>/do/lemmeModifier">
			
					<input type="hidden" name="id" value="<%= id %>">
			
					<div id="tabs">
					
						<!-- Tabs menu -->
					
		 				<ul>
							<li><a href="#tabs-1">Lemme</a></li>
							<li><a href="#tabs-2">Attributs orthographiques</a></li>
						</ul>
						
						<!-- Main tab -->
						
						<div id="tabs-1">
							
							<div id="item-main-attributes-left">
<!-- 							
								<div class="label-control-container">
									<label id="cgram-label" for="cgram">Cat&eacute;gorie grammaticale</label> <select id="cgram" name="cgram"></select>
									<div class="clear"></div>
								</div>
								<div class="label-control-container">
									<label id="scgram-label" for="scgram">Sous-cat&eacute;gorie</label> <select id="scgram" name="scgram" disabled><option>TODO</option></select>
									<div class="clear"></div>
								</div>
								<div class="label-control-container">
									<label id="flexion-label" for="flexion">Flexion</label> <select id="flexion" name="flexion" disabled><option>TODO</option></select>
									<div class="clear"></div>
								</div>
								<div class="label-control-container">
									<label id="orthographe-label" for="orthographe">Repr&eacute;sentant orthographique</label> <input id="orthographe" name="orthographe" type="text" value="">
									<div class="clear"></div>
								</div>
								<div class="label-control-container">
									<label id="phonologie-label" for="phonologie">Repr&eacute;sentant phonologique</label><input id="phonologie" name="phonologie" type="text" value="">
									<div class="clear"></div>
								</div>
 -->
 TODO							
							</div>

							<div id="item-main-attributes-right">
							
								<fieldset>
									<legend>Formes du lemme</legend>
									
									<div style="width:50%;margin: 0 auto;text-align:center;">TODO</div>
									
								</fieldset>
							
							</div>
							
							<div class="clear"></div>
							
						</div>
						
						<!-- Orthographic tab -->
						
						<div id="tabs-2">
							
							<div id="item-orthographic-attributes-left">
							
								<div class="label-control-container">
									<label id="freqlivres-label" for="freqlivres">Fr&eacute;quence livres</label><input id="freqlivres" name="freqlivres" type="number" step="0.01" value="<%= freqLivres%>">
									<div class="clear"></div>
								</div>
								<div class="label-control-container">
									<label id="freqfilms-label" for="freqfilms">Fr&eacute;quence films</label><input id="freqfilms" name="freqfilms" type="number" step="0.01" value="<%= freqFilms%>">
									<div class="clear"></div>
								</div>
							
							</div>
							
							<div id="item-orthographic-attributes-right">
							
							</div>
							
							<div class="clear"></div>
							
						</div>
			
					</div>
					
					<div id="buttons-bar">
<!-- 						<input id="lemme-creation-submit" type="submit" value="Créer" title="Créer le lemme" disabled> -->
						<input id="lemme-modification-submit" type="submit" value="Modifier" title="Modifier le lemme">
						<button id="lemme-modification-cancel" type="submit" formaction="<%= root %>/do/annuler" title="Annuler">Annuler</button>
					</div>
				
				</form>
				
			</div>

		</div>

		<script>

			/*
				TODO A VOIR
				
				http://bassistance.de/jquery-plugins/jquery-plugin-validation/
				
				http://openclassrooms.com/courses/simplifiez-vos-developpements-javascript-avec-jquery/trouver-et-utiliser-un-plugin
			*/
		
			// Set tabs.
			$( "#tabs" ).tabs({active: 1});
			
			// Set grammatical categories options.
// 			$.get('DBInterrogationController', {
//                 cgram : "options"
//         	}, function(response) {
//                 $('#cgram').html(response);
//         	});

			// Toogle the state of the submit button as a function of the state of the form.
// 			$("#orthographe").change(function() {
// 				if (
// 					   $("#cgram option").length > 0
// 					&& lexico.isValidOrthography( $("#orthographe").val() ) === true
// 					&& lexico.isValidPhonology( $("#phonologie").val() ) === true 
// 				   ) {
// 					$("#lemme-creation-submit").removeAttr("disabled");
// 				} else {
// 					$("#lemme-creation-submit").attr("disabled", "disabled");
// 				}
				
// 			});
			
			// Toogle the state of the submit button as a function of the state of the form.
// 			$("#phonologie").change(function() {
// 				if (
// 					   $("#cgram option").length > 0
// 					&& lexico.isValidOrthography( $("#orthographe").val() ) === true
// 					&& lexico.isValidPhonology( $("#phonologie").val() ) === true 
// 				   ) {
// 					$("#lemme-creation-submit").removeAttr("disabled");
// 				} else {
// 					$("#lemme-creation-submit").attr("disabled", "disabled");
// 				}
// 			});
			
			// Check the form on submission.
// 			$("#lemme-creation-form").submit(function( event ){
// 				var status = false;
				
// 				if ( $("#cgram option").length === 0 ) {
// 					alert(lexico.warnings.cgramMissing);
// 					return false;
// 				} else if ( lexico.isValidOrthography( $("#orthographe").val() ) === false) {
// 					alert(lexico.warnings.inValidOrthography);
// 					return false;
// 				} else if ( lexico.isValidPhonology( $("#phonologie").val() ) === false) {
// 					alert(lexico.warnings.invalidPhonology);
// 					return false;
// 				} else if ( lexico.isValidFloat( $("#freqlivres").val() ) === false) {
// 					alert(lexico.warnings.freqlivresInvalid);
// 					return false;
// 				} else if ( lexico.isValidFloat( $("#freqfilms").val() ) === false) {
// 					alert(lexico.warnings.freqfilmsInvalid);
// 					return false;
// 				}
// 			});
			
			// TODO
			// Set cgram autocompletion.
			/*var cgrams = [ "c++", "java", "php", "coldfusion", "javascript", "asp", "ruby" ];
			$( "#cgram" ).autocomplete({
				source: cgrams,
				autoFocus: true,
				minLength: 0
			});
			$( "#cgram" ).autocomplete("search", "");*/
		</script>

		<%
			if (giveFeedback.booleanValue() == true) {
				out.println("<script>alert(\"" + feedbackMessage + "\");</script>");
			}
		%>

	</body>
</html>