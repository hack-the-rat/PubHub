	<head>
		<style>
			table {
				align-content: left;
			}
			li	{
				margin-bottom: 0.7 em; 
				margin-top: 0.7 em;
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
			<c:choose>
				<c:when test="${not empty message }">
	  				<p class="alert ${messageClass}">${message }</p>
	<%	
	  session.setAttribute("message", null);
	  session.setAttribute("messageClass", null); 			
	%>
				</c:when>
			</c:choose>
		
			<h1>PUBHUB <small>Book Details - ${book.isbn13 }</small></h1>
			<hr class="book-primary">
		
			<form action="UpdateBook" method="post" class="form-horizontal">
		  		<input type="hidden" class="form-control" id="isbn13" name="isbn13" required="required" value="${book.isbn13 }" />
		  
		  		<div class="form-group">
		    		<label for="title" class="col-sm-4 control-label">Title</label>
		    		<div class="col-sm-5">
		      			<input type="text" class="form-control" id="title" name="title" placeholder="Title" required="required" value="${book.title }" />
		    		</div>
		  		</div>
		 		<div class="form-group">
		    		<label for="author" class="col-sm-4 control-label">Author</label>
		    		<div class="col-sm-5">
		      			<input type="text" class="form-control" id="author" name="author" placeholder="Author" required="required" value="${book.author }" />
		    		</div>
		  		</div>
		  		<div class="form-group">
		    		<label for="price" class="col-sm-4 control-label">Price</label>
		    		<div class="col-sm-5">
		      			<input type="number" step="0.01" class="form-control" id="price" name="price" placeholder="Price" required="required" value="${book.price }" />
		    		</div>
		  		</div>
		    	<div class="form-group">
		  			<label for="tags" class="col-sm-4 control-label">Tags</label>
		  			<div class="col-sm-5">
		  				<ul class="list-inline">
							<c:forEach items="${tags}" var="tag">
								<li class="list-inline-item">
									<input type="checkbox" name="tags" value="${tag.name}" ${tag.javacheck}>
									<c:out value= "${tag.name} " />
								</li>	   									
			    			</c:forEach> 
			   			</ul>
			 		</div>
			 	</div>
				<div class = "form-group">	
					<label for="tags" class = "col-sm-4 control-label">Custom Tags (optional):</label>
				 	<div class = "col-sm-5">
				 		<input type = "text" class = "form-control" id="ttags" name="ttags" placeholder="Use commas to separate (tag1, tag2, tag3)"/>	
				 	</div>
				</div>
		     	<div class="form-group">
		   	 		<div class="col-sm-offset-4 col-sm-1">
		      			<button type="submit" class="btn btn-info">Update Book Details</button>
		    		</div>
		  		</div>
		  	</form>
		</div>
	</header>
	
	<!-- Footer -->
	<jsp:include page="footer.jsp" />