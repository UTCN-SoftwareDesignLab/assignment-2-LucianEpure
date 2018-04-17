package service;

import model.User;

public interface AuthenticationService {

	public User registerUser(User user);
	
	public User login();
}
