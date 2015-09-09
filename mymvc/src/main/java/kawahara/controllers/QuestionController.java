package kawahara.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kawahara.models.AnswerModel;
import kawahara.models.QuestionModel;
import kawahara.services.AnswerService;
import kawahara.services.QuestionService;
import kawahara.services.SearchService;
import kawahara.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionController{
	private static final String DEFAULT_USERNAME = "Enter your username here.";
	private static final String DEFAULT_HEADING = "Enter your question's heading or title here.";
	private static final String DEFAULT_VALUE = "Enter your question here.";
	private static final String DEFAULT_ANSWER = "Enter your answer here.";
	
	private static final Pattern VALID_HEADING_USERNAME = Pattern.compile("([^@%^()<>/\\|&*#$!;]){1,50}"); //no special characters, only 50 chars
	//(<script)([. \n\r\t\d\w\(\)\{\}\=\'\:\/\[\]\/\-\+\;\*\,\|\!\&\$\#\<\>\"]+)(<\/script>)
	private static final Pattern JS_CAPTURE = Pattern.compile("(<script)([. \\n\\r\\t\\d\\w\\(\\)\\{\\}\\=\\'\\:\\/\\[\\]\\/\\-\\+\\;\\*\\,\\|\\!\\&\\$\\#\\<\\>\"]+)(<\\/script>)");
	private StringBuilder sb = new StringBuilder();

	@Autowired
	QuestionService questionService;
	@Autowired
	AnswerService answerService;
	@Autowired
	UserService userService;
	@Autowired 
	SearchService searchService;
	
	public QuestionController(){
		
	}
	public QuestionController(QuestionService questionService, AnswerService answerService, UserService userService, SearchService searchService){
		this.questionService = questionService;
		this.answerService = answerService;
		this.userService = userService;
		this.searchService = searchService;
	}
	
	private void incrementTime(Calendar cal){
		cal.add(Calendar.DATE, -7);
		cal.add(Calendar.MINUTE, -24);
	}
	private void fillDb(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);

		QuestionModel patrickQ = new QuestionModel("patrick", "Missing Chocolate Bar", "Hey did anyone see my bar of chocolate?", cal.getTime().getTime());
		questionService.add(patrickQ);
		answerService.add(new AnswerModel(patrickQ, "Patrick, it's all over your mouth.", "bob"));
		answerService.add(new AnswerModel(patrickQ, "What time did I eat it?", "patrick"));
		answerService.add(new AnswerModel(patrickQ, "I'm dealing with a bunch of idiots.", "sandy"));

		incrementTime(cal);
		QuestionModel bobQ = new QuestionModel("bob", "Wait I'm a little concerned", "Mr.Krabs no one's ordered a Krabby Patty in ages! What's going on?", cal.getTime().getTime());
		questionService.add(bobQ);
		answerService.add(new AnswerModel(bobQ, "We've changed the menu. There will be no more Krabby Patties.", "krabs"));
		answerService.add(new AnswerModel(bobQ, "Healthier times are a comin!", "sandy"));
		answerService.add(new AnswerModel(bobQ, "I would like one krabby patty please!", "patrick"));
		
		incrementTime(cal);
		QuestionModel squidQ = new QuestionModel("squid", "What's the big deal", "With Krabby Patties?", cal.getTime().getTime());
		questionService.add(squidQ);
		answerService.add(new AnswerModel(squidQ, "You like Krabby Patties, don't you Squidward?", "bob"));
		answerService.add(new AnswerModel(squidQ, "More money for the krabster.", "krabs"));
		
		incrementTime(cal);
		QuestionModel krabsQ = new QuestionModel("krabs", "Lost Quarter", "I'm lookin here fer ye lost quarter.  Anyone seen it?", cal.getTime().getTime());
		questionService.add(krabsQ);
		answerService.add(new AnswerModel(krabsQ, "Ohhhhh. I thought that was a chocolate coin.  No wonder my teeth hurt.", "patrick"));
		answerService.add(new AnswerModel(krabsQ, "That sure was a beautiful coin.. stop TAKING ME MONEY", "krabs"));
		answerService.add(new AnswerModel(krabsQ, "I'd pay to see that a second time.", "squid"));
		answerService.add(new AnswerModel(krabsQ, "Don't worry about it patrick, you don't have any teeth.", "bob"));
		
		incrementTime(cal);
		QuestionModel sandyQ = new QuestionModel("sandy", "Dear Space Command", "How did I get here anyway?", cal.getTime().getTime());
		questionService.add(sandyQ);
		answerService.add(new AnswerModel(sandyQ, "Guess I'll never know", "sandy"));
		answerService.add(new AnswerModel(sandyQ, "In soon time my past self.", "sandy"));
		answerService.add(new AnswerModel(sandyQ, "whaaaaaa", "squid"));
		
		incrementTime(cal);
		QuestionModel question6 = new QuestionModel("bob", "Are you ready", "Because I definitely am most ready?", cal.getTime().getTime());
		questionService.add(question6);
		answerService.add(new AnswerModel(question6, "I'm not ready...not this time.  You'll need to pay me more money.", "squid"));
		answerService.add(new AnswerModel(question6, "The beauty is being ready all the time.", "bob"));
		answerService.add(new AnswerModel(question6, "I'm ready. For me money", "krabs"));
		answerService.add(new AnswerModel(question6, "Why are you always talking about money all the time, mr. Krabs?", "patrick"));
		
		incrementTime(cal);
		QuestionModel question7 = new QuestionModel("squid", "What would I look like as an octopus?", "Probably beautiful.", cal.getTime().getTime());
		answerService.add(new AnswerModel(question6, "The world isn't ready for that kind of beauty yet.", "squid"));
		answerService.add(new AnswerModel(question6, "It's time for you to grow more legs Squidward!", "bob"));
		answerService.add(new AnswerModel(question6, "I'd reckon you'd look like that the next time around.", "sandy"));
		answerService.add(new AnswerModel(question6, "sometimes I wonder the same thing...", "patrick"));
		questionService.add(question6);
	}

	public ModelAndView handle404(String path, String message){
		ModelAndView mv = new ModelAndView();
		sb.setLength(0);
		sb.append("Message: ").append(message);
		
		mv.addObject("message", sb.toString());
		mv.addObject("url", path);
		mv.setViewName("error");
		
		return mv;
	}
	@RequestMapping(value = "/question/{id}", method = RequestMethod.GET)
	private ModelAndView handleGetQuestion(@PathVariable("id") int id, 
			HttpServletRequest request){
		QuestionModel model = questionService.get(Long.parseLong(String.valueOf(id)));
		
		if(model != null){
			ModelAndView mv = new ModelAndView();
			mv.setViewName("question");
			mv.addObject("model", model);
			mv.addObject("questionAnswers", answerService.getsAnswersForQuestion(model.getId()));
			mv.addObject("userimg", request.getContextPath() + "/user/" + model.getName());
			return mv;
		}else{
			return handle404( "", "question not found");
		}
	}
	
	@RequestMapping(value = "/question/add", method = RequestMethod.GET)
	private ModelAndView handleAddQuestion(){
		searchService.clearCache();
		QuestionModel newQuestion = new QuestionModel(DEFAULT_USERNAME, DEFAULT_HEADING, DEFAULT_VALUE);
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("editQuestion");
		mv.addObject("users", userService.getUsers());
		mv.addObject("model",  newQuestion);
		
		return mv; 
	}
	
	@RequestMapping(value = "/question/{id}/edit", method = RequestMethod.GET)
	private ModelAndView handleEditQuestion(@PathVariable("id") String id){
		QuestionModel currentQuestion = questionService.get(Long.parseLong(id));
		ModelAndView mv = new ModelAndView();

		mv.setViewName("editQuestion");
		mv.addObject("users", userService.getUsers());
		mv.addObject("model",  currentQuestion);
		
		return mv; 
	}
	
	@RequestMapping(value = "/question/{id}/delete", method = RequestMethod.GET)
	private ModelAndView handleDeleteQuestion(@PathVariable("id") String id, HttpServletRequest request){
		searchService.clearCache();
		ModelAndView mv = new ModelAndView();
		long idL = Long.parseLong(id);
		QuestionModel deleteQuestion = questionService.get(idL);
		questionService.delete(idL);
		

		mv.setViewName("deleteQuestion");
		mv.addObject("model", deleteQuestion);
		mv.addObject("userimg", request.getContextPath() + "/user/" + deleteQuestion.getName());
		
		return mv; 
	}
	
	@RequestMapping(value = "/question/{id}/answer/add", method = RequestMethod.GET)
	private ModelAndView handleAddAnswer(@PathVariable("id") String id){
		AnswerModel newAnswerModel = new AnswerModel(questionService.get(Long.parseLong(id)), DEFAULT_ANSWER, DEFAULT_USERNAME);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("editAnswer");

		mv.addObject("users", userService.getUsers());
		mv.addObject("model",  newAnswerModel);
		return mv; 
	}
	
	@RequestMapping(value = "/question/{qid}/answer/{aid}/edit", method = RequestMethod.GET)
	private ModelAndView handleEditAnswer(@PathVariable("aid") String aid){
		AnswerModel currentQuestion = answerService.get(Long.parseLong(aid));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("editAnswer");
		
		mv.addObject("users", userService.getUsers());
		mv.addObject("model",  currentQuestion);
		return mv; 
	}
	
	@RequestMapping(value = "/question/{qid}/answer/{aid}/delete", method = RequestMethod.GET)
	private ModelAndView handleDeleteAnswer(@PathVariable("aid") String aid){
		searchService.clearCache();
		long id = Long.parseLong(aid);
		AnswerModel deleteAnswer = answerService.get(id);
		answerService.delete(id);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("deleteAnswer");
		mv.addObject("model",  deleteAnswer);
		
		return mv; 
	}
	
	//*****************VALIDATION********************//
	private String validateNoJS(String noJSValue){
		Matcher matcher = JS_CAPTURE.matcher(noJSValue);
		while(matcher.find()){
			noJSValue = matcher.replaceAll("");
			matcher = JS_CAPTURE.matcher(noJSValue);
		}
		return noJSValue;
	}
	private QuestionModel validateQuestion(QuestionModel model){
		//check username
		if(!userService.userExists(model.getName())){//only answer ability to change user
			model.addErrorMessage("This user does not exist");
		}Matcher matcher = VALID_HEADING_USERNAME.matcher((model).getHeading());
		if( !matcher.matches() ){
			model.addErrorMessage("invalid heading: cannot contain special characters (@%^()<>/\\|&*#$!;) and must be less than 50 chars");
		}
		model.setQuestion(validateNoJS(model.getQuestion()));
		return model;
	}
	private AnswerModel validateAnswer(AnswerModel model){
		//check username
		if(!userService.userExists(model.getName())){//only answer ability to change user
			model.addErrorMessage("This user does not exist");
		}
		model.setAnswer(validateNoJS(model.getAnswer()));
		return model;
	}
	//*****************END VALIDATION********************//
	
	private ModelAndView getErrorMV(AnswerModel model) throws ServletException, IOException{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("editAnswer");
		mv.addObject("model", model);
		return mv;
	}
	
	@RequestMapping(value = "/question/{qid}/edit", method = RequestMethod.POST)
	private ModelAndView handleQuestionPost(HttpServletRequest request, HttpServletResponse response, @PathVariable("qid") String questionid) throws ServletException, IOException{
		searchService.clearCache();
		ModelAndView mv = new ModelAndView();
		//can be add or edit depending on id
		long id = Long.parseLong(questionid);
		QuestionModel model = questionService.get(id);
		String heading = request.getParameter("heading");
		String username = request.getParameter("username");
		String value = request.getParameter("value");
		
		if(model == null){
			model = new QuestionModel(username, heading, value);
		}else{
			model.setHeading(heading);
			model.setUserName(username);
			model.setQuestion(value);
		}
		model = validateQuestion(model);
		
		if(model.getNumErrors() > 0){
			mv.setViewName("editQuestion");
			mv.addObject("model", model);
		}else{
			if(id < 0){
				id = questionService.add(model);
			}else{
				questionService.update(model);
			}
			mv = handleGetQuestion((int)id, request);
		}
		return mv;
	}
	
	@RequestMapping(value = "/question/{qid}/answer/{aid}/edit", method = RequestMethod.POST)
	private ModelAndView handleAnswerPost(@PathVariable("aid") String aid, @PathVariable("qid") String qid, HttpServletRequest request) throws ServletException, IOException{
		searchService.clearCache();
		ModelAndView mv = new ModelAndView();
		long questionId = Long.parseLong(qid);
		long answerId = Long.parseLong(aid);
		boolean newAnswer = false;
		
		AnswerModel model = answerService.get(answerId);
		String value = request.getParameter("value");
		String username = request.getParameter("username");
		if(model == null){
			model = new AnswerModel(questionService.get(questionId), value, username);
			newAnswer = true;
		}else{
//			model.setQuestionId(questionId);
			model.setAnswer(value);
			model.setUsername(username);
		}
		model = validateAnswer(model);

		if(model.getNumErrors() > 0){
			mv = getErrorMV(model);
		}else{
			if(newAnswer){
				answerService.add(model);
			}else{
				answerService.update(model);
			}
			mv = handleGetQuestion((int)questionId, request);
		}
		return mv;
	}
	
	@RequestMapping(value = "/question/{qid}/{vote}", method = RequestMethod.POST)
	private ModelAndView handleQuestionVote(@PathVariable("qid")String qid, @PathVariable("vote")String vote,
			HttpServletRequest request) throws IOException{
		ModelAndView mv = new ModelAndView();
		long questionId = Long.parseLong(qid);
		QuestionModel model = questionService.get(questionId);
		questionService.vote(model.getId(), (vote.equals("vote-up")));
		mv = handleGetQuestion((int)questionId, request);
		
		return mv;
	}
	
	@RequestMapping(value = "/question/{qid}/answer/{aid}/{vote}", method = RequestMethod.POST)
	private ModelAndView handleAnswerVote(@PathVariable("qid") String qid, @PathVariable("aid") String aid, @PathVariable("vote")String vote,
			HttpServletRequest request) throws IOException{
		ModelAndView mv = new ModelAndView();
		long questionId = Long.parseLong(qid);
		QuestionModel model = questionService.get(questionId);
		
		answerService.vote(Long.parseLong(aid), (vote.equals("vote-up")));
		mv = handleGetQuestion((int)questionId, request);
		
		return mv;
	}
	
	public void setAnswerService(AnswerService answerService){
		this.answerService = answerService;
		if(this.questionService != null && this.answerService != null){
			if(this.questionService.getQuestions().isEmpty()){
				System.out.println("FILLING DB IN SETANSWERSERVICE");
				System.out.println("FILLING DB IN SETANSWERSERVICE");
				System.out.println("FILLING DB IN SETANSWERSERVICE");
				System.out.println("FILLING DB IN SETANSWERSERVICE");
				System.out.println("FILLING DB IN SETANSWERSERVICE");
				System.out.println("FILLING DB IN SETANSWERSERVICE");
				System.out.println("FILLING DB IN SETANSWERSERVICE");
				System.out.println("FILLING DB IN SETANSWERSERVICE");
				fillDb();
			}
		}
	}
	
	public void setQuestionService(QuestionService questionService){
		this.questionService = questionService;
	}
	
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	
	public void setSearchService(SearchService searchService){
		this.searchService = searchService;
	}
	
}