package kawahara.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ANSWERS")
@SequenceGenerator(name = "seq", initialValue = 0, allocationSize = 1000)
public class AnswerModel {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Column(name = "ANSWER_ID")
	private long id;

	@ManyToOne
	@JoinColumn(name = "QUESTION_ID")
	QuestionModel question;

	public QuestionModel getQuestion() {
		return question;
	}

	private String name;
	private String answer;
	private int rating;
	@Transient
	private List<String> errorMessages = new ArrayList<String>();

	public AnswerModel() {
	}

	public AnswerModel(QuestionModel question, String answer, String username) {
		AnswerModelHelper(question, answer, username);
	}

	// public AnswerModel(QuestionModel question, String answer, String
	// username){
	// AnswerModelHelper(question, answer, username);
	// }
	private void AnswerModelHelper(QuestionModel question, String answer,
			String username) {
		this.question = question;
		this.answer = answer;
		this.name = username;
		this.rating = 0;
	}

	public String getAnswer() {
		return answer;
	}

	public final List<String> getErrorMessages() {
		return errorMessages;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Long getQuestionId() {
		return question.getId();
	}

	public int getRating() {
		return rating;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/*
	 * private void setQuestionId(long id){ this.questionId = id; }
	 */
	public void setUsername(String username) {
		this.name = username;
	}

	public void copyData(AnswerModel updateAnswer) {
		// this.questionId = updateAnswer.getQuestionId();
		this.rating = updateAnswer.getRating();
		this.name = updateAnswer.getName();
		this.errorMessages = updateAnswer.getErrorMessages();
		this.answer = updateAnswer.getAnswer();
	}

	public void upVote() {
		this.rating++;
	}

	public void downVote() {
		this.rating--;
	}

	public String contains(String test) {
		String lowerTest = test.toLowerCase();
		if (answer.toLowerCase().contains(lowerTest)) {
			return answer;
		} else if (name.toLowerCase().contains(lowerTest)) {
			return name;
		} else {
			return null;
		}
	}

	public void addErrorMessage(String error) {
		errorMessages.add(error);
	}

	public void clearErrors() {
		errorMessages.clear();
	}

	public int getNumErrors() {
		return errorMessages.size();
	}

}