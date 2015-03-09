<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Lemme"%>

<%
	String includer				=	"/do/formeslister";
	String root					=	request.getContextPath();
	Boolean giveFeedback		=	(Boolean) request.getAttribute("giveFeedback");
	if (giveFeedback == null) {giveFeedback = false; }
	String feedbackMessage		=	(String) request.getAttribute("feedbackMessage");
	if (feedbackMessage == null) {feedbackMessage = ""; }
	
	List<Lemme> result			= 	(List<Lemme>) request.getAttribute("result");
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
	
		<h1>R&eacute;sultats</h1>
		
		<div id="main-content" class="full-width">
		
			<div id="result-main-toolbar" class="form-container">
				
				<div>
					<form id="form-creation-call-form" method="post" action="<%= root %>/do/formeAppelerCreation">
						<input id="form-creation-call-submit" type="submit" value="Nouvelle forme" title="Aller à la page de création d'une forme">
					</form>
				</div>
				
				<div>
					<form id="lemme-creation-call-form" method="post" action="<%= root %>/do/lemmeAppelerCreation">
						<input id="lemme-creation-call-submit" type="submit" value="Nouveau lemme" title="Aller à la page de création d'un lemme">
					</form>
				</div>
<!--			
				<div>
					<form id="database-initialization-form" method="post" action="<%= root %>/do/bdInitialiser">
						<input id="database-initialization-submit" type="submit" class="warn" value="INITIALISEZ LA BASE !" title="Initialiser les éléments de persistance nécessaires">
					</form>
				</div>
-->		
			</div>
			
			<div class="clear"></div>
			
			<div id="search-result-container">
			
				<fieldset>
					<legend>Formes</legend>
					
					<div>
						
						<%
							if (result == null || result.size() == 0) {
								
								out.println("<p id=\"no-result-placeholder\">Aucun r&eacute;sultat</p>");
								
							} else {
								
								Lemme l;
								out.println("<table>");
								
								out.println("<tr>");
								out.println("<th>Id lemme</th>");
								out.println("<th>Frequence livres</th>");
								out.println("<th>Frequence films</th>");
								
								for (int i=0; i<result.size(); i++) {
									l = result.get(i);

									out.println("<tr>");
									
									out.println("<td>");
									out.println(l.getId());
									out.println("</td>");
									
									out.println("<td>");
									out.println(l.getFrequenceLivres());
									out.println("</td>");
									
									out.println("<td>");
									out.println(l.getFrequenceFilms());
									out.println("</td>");
									
									out.println("<td>");
									out.println("<form id=\"lemme-edition-call-form-" + l.getId() + "\" action=\"" + root + "/do/lemmeAppelerModification\" method=\"post\">");
									out.println("<input type=\"hidden\" id=\"id-" + l.getId() + "\" name=\"id\" value=\"" + l.getId() + "\" />");
									out.println("<input type=\"hidden\" id=\"id-" + l.getId() + "\" name=\"freqlivres\" value=\"" + l.getFrequenceLivres() + "\" />");
									out.println("<input type=\"hidden\" id=\"id-" + l.getId() + "\" name=\"freqfilms\" value=\"" + l.getFrequenceFilms() + "\" />");
									out.println("<input id=\"lemme-edition-call-submit-" + l.getId() + "\" type=\"submit\" title=\"Editer...\" value=\"Editer\">");
									out.println("</form>");
									out.println("</td>");
									
									out.println("<td>");
									out.println("<form id=\"lemme-deletion-call-form-" + l.getId() + "\" action=\"" + root + "/do/lemmeAppelerSuppression\" method=\"post\">");
									out.println("<input type=\"hidden\" id=\"id-" + l.getId() + "\" name=\"id\" value=\"" + l.getId() + "\" />");
									out.println("<input id=\"lemme-deletion-call-submit-" + l.getId() + "\" type=\"submit\" title=\"Supprimer...\" value=\"Supprimer\">");
									out.println("</form>");
									out.println("</td>");
									
									out.println("</tr>");
									
								}
								out.println("</table>");
							}
						%>

					</div>
					
				</fieldset>
				
			</div>
		
		</div>

		<%
			if (giveFeedback.booleanValue() == true) {
				out.println("<script>alert(\"" + feedbackMessage + "\");</script>");
			}
		%>

	</body>
</html>