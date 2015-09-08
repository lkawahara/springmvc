package kawahara.db;

import java.util.Collection;

import kawahara.models.UserModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDAO implements Idal<UserModel, Long> {
	private SessionFactory sessionFactory;
	
	public UserDAO(){}
	
	public UserDAO(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long create(UserModel newUser) {
		Session session = getOpenTransactionSession();
		long id = (Long)session.save(newUser);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public UserModel read(Long key) {
		Session session = getOpenTransactionSession();
		UserModel got = (UserModel)session.get(UserModel.class, key);
		session.close();
		return got;
	}

	@Override
	public boolean update(Long key, UserModel updateUser) {
		UserModel answer = read(updateUser.getId());
		if(answer == null){
			return false;
		}
		Session session = getOpenTransactionSession();
		answer.copyData(updateUser);
		session.saveOrUpdate(answer);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	@Override
	public void delete(Long key) {
		Session session = getOpenTransactionSession();
		UserModel gotten = (UserModel)session.get(UserModel.class, key);
		session.delete(gotten);
		session.getTransaction().commit();
		session.close();
	}
	
	public Collection<UserModel> getItems() {
		Session session = getOpenTransactionSession();
		Collection<UserModel> ret = (Collection<UserModel>) session.createQuery("SELECT x FROM UserModel x").list();
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
