<%@ include file="/WEB-INF/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Question Page</title>
</head>
<body>
	 <section class="question">
	     <h1>${model.getHeading()}</h1>
	     <div class="content">
	       <p>${model.getQuestion()}</p>
	       <div class="user">
    	   	 <div>
	           <img src="${pageContext.request.contextPath}/user/${model.getName()}"/>
	           <p class="username">Asked by ${model.getName()}</p>
	         </div>
	         <p>asked
	         	<days>${model.getDaysSinceAsked()}</days> days,
	         	<hours>${model.getHoursSinceAsked()}</hours> hours,
	         	<minutes>${model.getMinutesSinceAsked()}</minutes> minutes,
	         	<seconds>${model.getSecondsSinceAsked()}</seconds> seconds ago
	         </p>
	       </div>
     	</div>
		<a href="${pageContext.request.contextPath}/question/${model.getId()}/edit">Edit Question</a>
	    <a href="${pageContext.request.contextPath}/question/${model.getId()}/delete">Delete Question</a>
     	<div class="rating">
	     	Question Rating:
	     	<form method="post" action="${pageContext.request.contextPath}/question/${model.getId()}/vote-up" display="inline">
				<button type="submit">&#9650;</button>
			</form>
	     	${model.getRating()}
	     	<form method="post" action="${pageContext.request.contextPath}/question/${model.getId()}/vote-down" display="inline">
				<button type="submit">&#9660;</button>
			</form>
	     </div>
	   </section>
	   <section class="answers">
	   		<h2>Answers</h2>
 			<a href="${pageContext.request.contextPath}/question/${model.getId()}/answer/add">Add a new answer</a>
	     	<ul class="allAnswers">
		 	<c:forEach var="currAnswer" items="${questionAnswers}">
	        	<li class="answer">
	        		<img src="${pageContext.request.contextPath}/user/${currAnswer.getName()}"/>
		       		<p>${currAnswer.getAnswer()} - Answered by ${currAnswer.getName()}</p>	  
		       		<a href="${pageContext.request.contextPath}/question/${model.getId()}/answer/${currAnswer.getId()}/edit">Edit Answer</a>
	    			<a href="${pageContext.request.contextPath}/question/${model.getId()}/answer/${currAnswer.getId()}/delete">Delete Answer</a>
		       	 	<div class="rating">
		       	 		Answer Rating: ${currAnswer.getRating()}
						<form method="post" action="${pageContext.request.contextPath}/question/${model.getId()}/answer/${currAnswer.getId()}/vote-up">
							<button type="submit">&#9650;</button>
						</form>
						<form method="post" action="${pageContext.request.contextPath}/question/${model.getId()}/answer/${currAnswer.getId()}/vote-down">
							<button type="submit">&#9660;</button>
						</form>
			     	</div> 
			 	</li>
		 	</c:forEach>
     	  	</ul>
	   	</section>
	   <script>
	   (function (){
	   		var xmlhttp;
	
		   	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
	    	 	xmlhttp=new XMLHttpRequest();
	       	}
	
		   	xmlhttp.onreadystatechange=function(){
		   		//readyState == 4: request is finished and response is ready
	   			//status == 200: Ok status
		     	if (xmlhttp.readyState==4 && xmlhttp.status==200){
		           setInterval(function(){
	                  var secondsElement = document.getElementsByTagName("seconds")[0];
	                  var seconds = parseInt(secondsElement.innerHTML, 10);
	                  seconds += 1;
	                  
	                  if(seconds > 60){
	                	  seconds = 0;
	                	  var minutesElement = document.getElementsByTagName("minutes")[0];
		                  var minutes = parseInt(minutesElement.innerHTML, 10);
		                  minutes += 1;
		                  
		                  if(minutes > 60){
		                	  minutes = 0;
		                	  var hoursElement = document.getElementsByTagName("hours")[0];
			                  var hours = parseInt(hoursElement.innerHTML, 10);
			                  hours += 1;
			                  
			                  if(hours > 24){
			                	  hours = 0;
			                	  var daysElement = document.getElementsByTagName("days")[0];
			                	  var days = parseInt(dayselement.innerHTML, 10);
			                	  days += 1; 
			                	  daysElement.innerHTML = days;
			                  }
			                  hoursElement.innerHTML = hours;
		                  }
		                  minutesElement.innerHTML = minutes;
	                  }
	                  secondsElement.innerHTML = seconds;
	                  }, 1000);
	    		}
	   		}
	   		xmlhttp.open("GET","question.jsp",true);
	   		xmlhttp.send();
	   })();
	   </script>
</body>
</html>