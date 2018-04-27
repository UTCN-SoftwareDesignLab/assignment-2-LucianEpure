package service.user;

import dto.UserDto;
import entity.User;
import validators.Notification;

public interface AuthenticationService {

	Notification<Boolean> register(UserDto user,String type);

}
