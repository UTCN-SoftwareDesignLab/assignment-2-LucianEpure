package service.user;

import java.util.List;

import model.User;
import validators.Notification;

public interface UserService {

	public List<User> findAll();
	
	public User findById(int id);
	
	public void fireById(int id);
	
	public Notification<Boolean> update(User user);
}
