package kawahara.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kawahara.db.Idal;
import kawahara.models.AnswerModel;

public class AnswerService {
	@Autowired
	private Idal<AnswerModel, Long> dao;
	
	public AnswerService(){}
	
	public AnswerService(Idal<AnswerModel, Long> dao){
		this.dao = dao;
	}
	
	public List<AnswerModel> getsAnswersForQuestion(long id){
		List<AnswerModel> ret = new ArrayList<AnswerModel>();
		Collection<AnswerModel> allAnswers  = dao.getItems();
		for(AnswerModel model : allAnswers){
			if(model.getQuestionId() == id){
				ret.add(model);
			}
		}
		
		return ret;
	}

	public void vote(long id, boolean up) {
		AnswerModel answer = get(id);
		if(answer != null){
			if(up){
				answer.upVote();
			}else{
				answer.downVote();
			}
			update(answer);
		}		
	}
	
	public long add(AnswerModel answer) {
		return dao.create(answer);
	}

	public boolean contains(AnswerModel answer) {
		return ( get(answer.getId()) != null);
	}

	public AnswerModel get(long id) {
		return dao.read(id);
	}

	public boolean update(AnswerModel updateAnswer) {
		return dao.update(updateAnswer.getId(), updateAnswer);
	}

	public void delete(long id) {
		dao.delete(id);
	}

	public Collection<AnswerModel> getAnswers() {
		return dao.getItems();
	}
	
	public void setDao(Idal<AnswerModel, Long> dao){
		this.dao = dao;
	}
}