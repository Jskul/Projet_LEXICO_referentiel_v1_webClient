<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String includer				=	"erreur.jsp";
	String root					=	request.getContextPath();
	String errorMessage			=	(String) request.getAttribute("errorMessage");
	if (errorMessage == null) {errorMessage = "";}
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
	
		<h1>Erreur</h1>
		
		<p id="main-content" class="full-width"><%= errorMessage %></p>

	</body>
</html>