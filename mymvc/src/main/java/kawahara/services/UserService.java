package kawahara.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import kawahara.db.Idal;
import kawahara.models.UserModel;

public class UserService {	
	@Autowired
	private Idal<UserModel, Long> dao;
	
	public UserService(){}
	
	public UserService(Idal<UserModel, Long> dao){
		this.dao = dao;
	}
	
	public UserModel get(String username){
		UserModel ret = null;
		Collection<UserModel> users = dao.getItems();
		for(UserModel u : users){
			if(u.getUsername().equals(username)){
				ret = u;
				break;
			}
		}
		return ret;
	}
	
	public boolean userExists(String username){
		boolean ret = false;

		Collection<UserModel> users = dao.getItems();
		for(UserModel u : users){
			if(u.getUsername().equals(username)){
				ret = true;
				break;
			}
		}
		return ret;
	}

	public void add(UserModel user) {
		dao.create(user);
	}

	public boolean contains(UserModel user) {
		return ( get(user.getId()) != null);
	}

	public UserModel get(long id) {
		return dao.read(id);
	}

	public boolean update(UserModel updateUser) {
		return dao.update(updateUser.getId(), updateUser);
	}

	public void delete(long id) {
		dao.delete(id);
	}

	public Collection<UserModel> getUsers() {
		return dao.getItems();
	}
	
	public void setDao(Idal<UserModel, Long> dao){
		this.dao = dao;
	}

	public boolean isEmpty() {
		return this.dao.getItems().isEmpty();
	}
}