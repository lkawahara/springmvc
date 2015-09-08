<%@ include file="/WEB-INF/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Question</title>
</head>
<body>
	<section class="errors">
		<ul>
			<c:forEach var="e" items="${model.getErrorMessages()}">
				<li style="color: red">
					${e}
				</li>
			</c:forEach>
		</ul>
	</section>
	
	<form method="post" action="${pageContext.request.contextPath}/question/${model.getId()}/edit">
		<ul>
			<!--User-->
			<li>
				<label for="username">User Name:</label>
				<select id="username" name="username" style="width:300px">
					<c:forEach var="user" items="${users}">
					<option>${user.getUsername()}</option>
					</c:forEach>
				</select>
			</li>
			<!--Heading-->
			<li>
				<label for="heading">Question Heading:</label>
				<input id="heading" name="heading" type="text" value ="${model.getHeading()}"/>
			</li>
			<!--Value-->
			<li>
				<label for="value">Question:</label>
				<input id="value" name="value" type="text" value ='${model.getQuestion()}'/>
			</li>
			<!--Date time-->
			<!--Rating-->
			<li>
				<label for="rating">Rating:</label>
				<input id="rating" name="rating" type="text" value ="${model.getRating()}" readonly="true"/>
			</li>
		</ul>
		<button type="submit">Continue</button>
	</form>
</body>
</html>