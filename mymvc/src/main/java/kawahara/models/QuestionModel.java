package kawahara.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="QUESTIONS")
@SequenceGenerator(name="seq", initialValue=0, allocationSize=1000)
public class QuestionModel {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@Column(name="QUESTION_ID")
	private long id;
	private String heading;
	private String name;
	private String question; //used for question/answer/nothing
	private int rating;
	
	private long timeAsked;
	@Transient
	private List<String> errorMessages = new ArrayList<String>();
	private StringBuilder sb = new StringBuilder();
	
	@OneToMany
    @ElementCollection(targetClass=AnswerModel.class)
	private Set<AnswerModel>answers;
	
	public Set<AnswerModel> getAnswers() {
		return this.answers;
	}
	public QuestionModel(){
		
	}
	public QuestionModel(String username, String heading, String question){
		QuestionHelper(username, heading, question, System.currentTimeMillis(), 0);
	}
	public QuestionModel(Long id, String username, String heading, String question){
		QuestionHelper(id, username, heading, question, System.currentTimeMillis(), 0);
	}
	public QuestionModel(String username, String heading, String question, long time){
		QuestionHelper(username, heading, question, time, 0);
	}
	public QuestionModel(String username, String heading, String question, long time, int rating){
		QuestionHelper(username, heading, question, time, rating);
	}
	private void QuestionHelper(String username, String heading, String question, long time, int rating){
		this.id = -1;//initially set until set in db
		BaseHelper(username, heading, question, time, rating);
	}
	private void QuestionHelper(Long id, String username, String heading, String question, long time, int rating){
		this.id = id;
		BaseHelper(username, heading, question, time, rating);
	}
	private void BaseHelper(String username, String heading, String question, long time, int rating){
		this.name = username;
		this.heading = heading;
		this.question = question;
		this.timeAsked = time;
		this.rating = rating;
	}
	
	
	public final List<String> getErrorMessages(){
		return errorMessages;
	}
	public String getHeading() {
		return heading;
	}
	public long getId() {
		return this.id;
	}
	public String getName() {
		return name;
	}
	public String getQuestion() {
		return question;
	}
	public int getRating() {
		return rating;
	}
	public long getTimeAsked(){
		return this.timeAsked;
	}
	public int getDaysSinceAsked(){
		long millisSinceAsked = System.currentTimeMillis() - timeAsked;
		return (int)TimeUnit.DAYS.convert(millisSinceAsked, TimeUnit.MILLISECONDS);
	}
	public int getHoursSinceAsked(){
		long millisSinceAsked = System.currentTimeMillis() - timeAsked;
		millisSinceAsked -= TimeUnit.MILLISECONDS.convert(getDaysSinceAsked(), TimeUnit.DAYS);
		return (int) TimeUnit.HOURS.convert(millisSinceAsked, TimeUnit.MILLISECONDS);
	}
	public int getMinutesSinceAsked(){
		long millisSinceAsked = System.currentTimeMillis() - timeAsked;
		millisSinceAsked -= TimeUnit.MILLISECONDS.convert(getDaysSinceAsked(), TimeUnit.DAYS);
		millisSinceAsked -= TimeUnit.MILLISECONDS.convert(getHoursSinceAsked(), TimeUnit.HOURS);
		return (int) TimeUnit.MINUTES.convert(millisSinceAsked, TimeUnit.MILLISECONDS);
	}
	public int getSecondsSinceAsked(){
		long millisSinceAsked = System.currentTimeMillis() - timeAsked;
		millisSinceAsked = millisSinceAsked - TimeUnit.MILLISECONDS.convert(getDaysSinceAsked(), TimeUnit.DAYS);
		millisSinceAsked -= TimeUnit.MILLISECONDS.convert(getHoursSinceAsked(), TimeUnit.HOURS);
		millisSinceAsked = millisSinceAsked - TimeUnit.MILLISECONDS.convert(getMinutesSinceAsked(), TimeUnit.MINUTES);
		return (int) TimeUnit.SECONDS.convert(millisSinceAsked, TimeUnit.MILLISECONDS);
	}
	
		
	public void setHeading(String heading){
		this.heading = heading;
	}	
	public void setId(long id){
		this.id = id;
	}	
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setUserName(String question){
		this.name = question;
	}
		

	public String contains(String test){
		String lowerTest = test.toLowerCase();
		if( question.toLowerCase().contains(lowerTest) ){
			return question;
		}else if( name.toLowerCase().contains(lowerTest) ){
			return name;
		}else if( heading.toLowerCase().contains(lowerTest) ){
			return heading;
		}else{
			return null;
		}
	}
	//copies all data except for id
	public void copyData(QuestionModel toCopy){
		this.rating = toCopy.getRating();
		this.heading = toCopy.getHeading();
		this.timeAsked = toCopy.getTimeAsked();
		this.name = toCopy.getName();
		this.question = toCopy.getQuestion();
		this.errorMessages = toCopy.getErrorMessages();
	}	
	public void upVote(){
		this.rating ++;
	}
	public void downVote(){
		this.rating --;
	}

	
	//ERROR STUFF
	public void addErrorMessage(String error){
		errorMessages.add(error);
	}
	public void clearErrors(){
		errorMessages.clear();
	}
	public int getNumErrors(){
		return errorMessages.size();
	}
		
	@Override
	public String toString(){
		sb.setLength(0);
		String line = System.lineSeparator();
		sb.append("////////////START QuestionModel START///////////////)").append(line)
		.append("id: ").append(id).append(line)
		.append("name: ").append(name).append(line)
		.append("question: ").append(question).append(line)
		.append("rating: ").append(rating).append(line)
		.append("time asked: ").append(timeAsked).append(line)
		.append("////////////END QuestionModel END///////////////)");
		return sb.toString();
	}

	@Override
	public boolean equals(Object other){
		if(!(other instanceof QuestionModel))
			return false;
		QuestionModel compare = (QuestionModel)other;
		return (this.id == compare.getId() &&
				this.name.equals(compare.getName()) &&
				this.question.equals(compare.getQuestion()) &&
				this.rating == compare.getRating() &&
				this.timeAsked == compare.getTimeAsked());
	}
}