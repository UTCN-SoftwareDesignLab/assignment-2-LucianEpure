package service.user;

import dto.UserDto;
import entity.User;
import validators.Notification;

public interface AuthenticationService {

	Notification<Boolean> registerAdmin(UserDto user);
	
	Notification<Boolean> registerUser(UserDto user);
	
	String encodePassword(String password);
	
	User login(UserDto user);
}
