package service.user;

import model.User;
import validators.Notification;

public interface AuthenticationService {

	public Notification<Boolean> registerAdmin(User user);
	
	public Notification<Boolean> registerUser(User user);
	
	public User login(User user);
}
