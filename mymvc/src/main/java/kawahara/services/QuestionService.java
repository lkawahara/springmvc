package kawahara.services;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import kawahara.db.Idal;
import kawahara.models.QuestionModel;

public class QuestionService {
	@Autowired
	private Idal<QuestionModel, Long> dao;
	
	public QuestionService(){}
	
	public QuestionService(Idal<QuestionModel, Long> dao){
		this.dao = dao;
	}
	
	public void vote(long id, boolean up){
		QuestionModel question = dao.read(id);
		if(question != null){
			if(up){
				question.upVote();
			}else{
				question.downVote();
			}
			update(question);
		}
	}

	public long add(QuestionModel question) {
		return dao.create(question);
	}

	public boolean contains(QuestionModel question) {
		return ( get(question.getId()) != null);
	}

	public QuestionModel get(long id) {
		return dao.read(id);
	}

	public boolean update(QuestionModel updateQuestion) {
		return dao.update(updateQuestion.getId(), updateQuestion);
	}

	public void delete(long id) {
		dao.delete(id);
	}
	
	public Collection<QuestionModel> getQuestions(){
		return dao.getItems();
	}
	
	public void setDao(Idal<QuestionModel, Long> dao){
		this.dao = dao;
	}
	
}