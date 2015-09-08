<%@ include file="/WEB-INF/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}">home</a>
	<br/>
	<form method="get" action="${pageContext.request.contextPath}/search">
		<input type="search" name="search" value="${query}"></input>
		<button type="submit">Search</button>
	</form>
	Search Results for "${query}"
	<div class="searchResultsDiv">
		${message}
		<ul>
			<c:forEach var="searchResult" items="${searchResults}">
				<li>
					<a href="${pageContext.request.contextPath}/question/${searchResult.getQuestionId()}">"${searchResult.getRelevantWords()}"</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<form method="GET" action="${pageContext.request.contextPath}/search">
		<input type="hidden" name="search" value="${searchResults[0].getQuery()}"></input>
		<input type="hidden" name="pageIndex" value="${pageIndex - 1}"></input>
		<button type="submit">Previous Page</button>
	</form>
	<form method="GET" action="${pageContext.request.contextPath}/search">
		<input type="hidden" name="search" value="${searchResults[0].getQuery()}"></input>
		<input type="hidden" name="pageIndex" value="${pageIndex + 1}"></input>
		<button type="submit">Next Page</button>
	</form>
	
	<!-- <a onClick="next()" id="next" >next</a>
	<a onClick="previous()" id="previous">previous</a> -->
</body>
</html>