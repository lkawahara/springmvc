package kawahara.db;

import java.util.Collection;

import kawahara.models.QuestionModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class QuestionDAO implements Idal<QuestionModel, Long> {
	private SessionFactory sessionFactory;
	
	public QuestionDAO(){}
	
	public QuestionDAO(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long create(QuestionModel newQuestion) {
		Session session = getOpenTransactionSession();
		long id = (Long)session.save(newQuestion);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public QuestionModel read(Long key) {
		Session session = getOpenTransactionSession();
		QuestionModel got = (QuestionModel)session.get(QuestionModel.class, key);
		session.close();
		return got;
	}

	@Override
	public boolean update(Long key, QuestionModel updateQuestion) {
		QuestionModel answer = read(updateQuestion.getId());
		if(answer == null){
			return false;
		}
		Session session = getOpenTransactionSession();
		answer.copyData(updateQuestion);
		session.saveOrUpdate(answer);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	@Override
	public void delete(Long key) {
		Session session = getOpenTransactionSession();
		QuestionModel gotten = (QuestionModel)session.get(QuestionModel.class, key);
		session.delete(gotten);
		session.getTransaction().commit();
		session.close();
	}
	
	public Collection<QuestionModel> getItems() {
		Session session = getOpenTransactionSession();
		Collection<QuestionModel> ret = (Collection<QuestionModel>) session.createQuery("SELECT x FROM QuestionModel x").list();
		session.close();
		return ret;
	}

	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	private Session getOpenTransactionSession(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		return session;
	}
}
