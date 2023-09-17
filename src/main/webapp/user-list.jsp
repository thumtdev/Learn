<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>User Management</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<header><jsp:include page="header.jsp"/></header>
	<br>
	<div>
		<%! 
			String makeItLower(String data){
				return data.toLowerCase();
			}
		%>
		<span>
			<%= request.getLocale() %>
		</span>
		<form action="student-response.jsp">
			First name: <input type="text" name="firstname" />
			<br/><br/>
			Last name: <input type="text" name="lastname" />
			<br/><br/>
			<input type="submit" value="Submit" />
		</form>
	</div>
	<div class="row">
		<div class="container">
			<h3 class="text-center">List Users</h3>
			<hr>
			<div class="container text-left">
				<a href="<%=request.getContextPath()%>/user/new" class="btn btn-success">Add</a>
			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Name</th>
						<th>Email</th>
						<th>Country</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${users}">
						<tr>
							<td><c:out value="${user.name}" /></td>
							<td><c:out value="${user.email}" /></td>
							<td><c:out value="${user.country}" /></td>
							<td>
								<a href="user/edit?id=<c:out value='${user.id}' />">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp; 
								<a href="user/delete?id=<c:out value='${user.id}' />">Delete</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
	</div>
	<footer><jsp:include page="footer.jsp"/></footer>
</body>
</html>