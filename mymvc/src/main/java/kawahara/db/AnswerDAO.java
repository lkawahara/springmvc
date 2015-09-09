package kawahara.db;

import java.util.Collection;

import kawahara.models.AnswerModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AnswerDAO implements Idal<AnswerModel, Long> {
	@Autowired
	private SessionFactory sessionFactory;
	
	public AnswerDAO(){}
	
	public AnswerDAO(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long create(AnswerModel newAnswer) {
		Session session = getOpenTransactionSession();
		long id = (Long)session.save(newAnswer);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public AnswerModel read(Long key) {
		Session session = getOpenTransactionSession();
		AnswerModel got = (AnswerModel)session.get(AnswerModel.class, key);
		session.close();
		return got;
	}

	@Override
	public boolean update(Long key, AnswerModel updateAnswer) {
		AnswerModel answer = read(updateAnswer.getId());
		if(answer == null){
			return false;
		}
		Session session = getOpenTransactionSession();
		answer.copyData(updateAnswer);
		session.saveOrUpdate(answer);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	@Override
	public void delete(Long key) {
		Session session = getOpenTransactionSession();
		AnswerModel gotten = (AnswerModel)session.get(AnswerModel.class, key);
		session.delete(gotten);
		session.getTransaction().commit();
		session.close();
	}
	
	public Collection<AnswerModel> getItems() {
		Session session = getOpenTransactionSession();
		Collection<AnswerModel> ret = (Collection<AnswerModel>) session.createQuery("SELECT x FROM AnswerModel x").list();
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
