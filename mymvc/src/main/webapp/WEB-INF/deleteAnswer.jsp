<%@ include file="/WEB-INF/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Question Page</title>
</head>
<body>
	This Answer has been deleted
	<!--User-->
   	<img src="${pageContext.request.contextPath}/user/${model.getName()}"/>
	<label for="username">User Name:</label>
	<input id="username" name="username" type="text" value ="${model.getName()}"/>
	<!--Value-->
	<label for="value">Answer:</label>
	<input id="value" name="value" type="text" value ="${model.getAnswer()}"/>
	<!--Rating-->
	<label for="rating">Rating:</label>
	<input id="rating" name="rating" type="text" value ="${model.getRating()}" readonly="true"/>
	<a href="${pageContext.request.contextPath}/question/${model.getQuestionId()}"> continue to question </a>
</body>
</html>