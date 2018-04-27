package service.user;

import java.util.List;

import dto.UserDto;
import entity.User;
import validators.Notification;

public interface UserService {

	Notification<Boolean> register(UserDto user,String type);
	
	public List<User> findAll();
	
	public User findById(int id);
	
	public void fireById(int id);
	
	public Notification<Boolean> update(UserDto user);
	
	public void removeAll();
}
