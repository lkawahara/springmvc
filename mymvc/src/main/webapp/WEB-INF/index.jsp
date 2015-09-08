<%@ include file="/WEB-INF/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Module-Two!</title>
	</head>
	<body>
		<h1>Welcome to Layne Kawahara's HttpServer!</h1>
		<section class="content">
			<div>Begin searching for questions/answers already contained in the db! Search for questions or answers matching your query!</div>
			<form method="get" action="${pageContext.request.contextPath}/search">
				<input type="search" name="search" value="Enter your search here"></input>
				<button type="submit">Search</button>
			</form>
			<br/>
			<div>
				Click the link to add a new question: 
				<a href="${pageContext.request.contextPath}/question/add">Add a new Question</a>
			</div>
			<br/>
			<div>
				Navigate to ${pageContext.request.contextPath}/question/{id} to get a question by its id.
			</div>
			<br/>
			<div>
				Navigate to ${pageContext.request.contextPath}/user/{username} to get the image a specified user.
			</div>
			<br/>
			Thanks for visiting!
		</section>
	</body>
</html>