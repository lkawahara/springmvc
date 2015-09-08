<%@ include file="/WEB-INF/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Question Page</title>
</head>
<body>
	This question has been deleted
	 <section class="question">
	     <h1>${model.getHeading()}</h1>
	     <div class="rating">
	     	Question Rating: ${model.getRating()}
	     </div>
	     <div class="content">
	       <p>${model.getQuestion()}</p>
	       <div class="user">
	         <p>asked
	         	<days>${model.getDaysSinceAsked()}</days> days,
	         	<hours>${model.getHoursSinceAsked()}</hours> hours,
	         	<minutes>${model.getMinutesSinceAsked()}</minutes> minutes,
	         	<seconds>${model.getSecondsSinceAsked()}</seconds> seconds ago
	         </p>
	         <div>
	           <img src="${userimg}" align="left"/>
	           <p class="username">${model.getName()}</p>
	         </div>
	       </div>
     	</div>
   		<a href="${pageContext.request.contextPath}"> continue to index </a>
	   </section>
</body>
</html>