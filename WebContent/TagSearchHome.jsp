<!--
  ____                 _                  
 |  _ \ _____   ____ _| |_ _   _ _ __ ___ 
 | |_) / _ \ \ / / _` | __| | | | '__/ _ \
 |  _ <  __/\ V / (_| | |_| |_| | | |  __/
 |_| \_\___| \_/ \__,_|\__|\__,_|_|  \___|
 
-->
	<head>
		<style type="text/css">
			li {
				margin-bottom: 0.7 em;
			}
		</style>
	</head>

	<!-- Header -->
	<jsp:include page="header.jsp" />
	
	<!-- JSTL includes -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
	<%@ page import="java.util.List" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="examples.pubhub.model.Tag" %>
	
<!-- 	Just some stuff you need -->
	<header>
	  <div class="container">
		<%
			//display message if present
			if (request.getAttribute("message") != null) {
				out.println("<p class=\"alert alert-danger\">" + request.getAttribute("message") + "</p>");
			}	
		%>
		<h1>PUBHUB <small>Browse by tag</small></h1>
		
		<!--  Display db of tags available as buttons-->
		
		<div class="float">
			<br/>	
			<ul class ="list-inline">
				<c:forEach var="tag" items="${tags}">
					<li class = "list-inline-item">
				 		<form action="TagSearch?tag=${tag.name}" class = "form-horizontal">
				 			<input type="hidden" name = "tag" value ="${tag.name}">
							<button class="btn btn-primary"><c:out value = "${tag.name}"/></button>
						</form>
					</li>
				</c:forEach>
			</ul>
		</div>	
	  </div>
	</header>
	
	<!-- Footer -->
	<jsp:include page="footer.jsp" />