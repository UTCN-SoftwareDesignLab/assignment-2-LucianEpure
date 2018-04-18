package service;

import model.User;
import validators.Notification;

public interface AuthenticationService {

	public Notification<Boolean> register(User user);
	
	public User login(User user);
}
