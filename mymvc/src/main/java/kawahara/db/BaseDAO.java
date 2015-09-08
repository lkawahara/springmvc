package kawahara.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class BaseDAO<T, K> implements Idal<T, K> {
	protected SessionFactory sf;
	
	public BaseDAO(){}
	
	public BaseDAO(SessionFactory sf){
		this.sf = sf;
	}
	
	@Override
	public K create(T newItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T read(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(K key, T toUpdate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(K key) {
		// TODO Auto-generated method stub
		
	}
	
	protected Session getOpenTransactionSession(){
		Session session = sf.openSession();
		session.beginTransaction();
		return session;
	}
}
