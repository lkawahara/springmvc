<%@ include file="/WEB-INF/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Hello :: Spring Application</title>
</head>
<body>
	<h1>Hello - Spring Application</h1>
	<p>
		Greetings, it is now
		<c:out value="${now}" />
	</p>
</body>
</html>