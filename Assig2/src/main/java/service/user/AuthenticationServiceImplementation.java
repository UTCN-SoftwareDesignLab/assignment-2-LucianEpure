package service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dto.UserDto;
import entity.Role;
import entity.User;
import entity.builder.UserBuilder;
import repository.RoleRepository;
import repository.UserRepository;
import validators.IValidator;
import validators.Notification;
import validators.UserValidator;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService{

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private IValidator validator;
	@Autowired
	public AuthenticationServiceImplementation(UserRepository userRepository, RoleRepository roleRepository){
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public Notification<Boolean> register(UserDto user, String type) {
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		validator = new UserValidator(user); 
		boolean userValid = validator.validate();
		Notification<Boolean> userRegisterNotification = new Notification<Boolean>();
		if(!userValid){
			validator.getErrors().forEach(userRegisterNotification::addError);
			userRegisterNotification.setResult(Boolean.FALSE);	
		}	
		else{
			User dbUser = new UserBuilder().setUsername(user.getUsername()).setPassword(enc.encode(user.getPassword())).build();
			
			List<Role> userRoles = dbUser.getRoles();
			userRoles.add(roleRepository.findByRoleName(type));
			dbUser.setRoles(userRoles);
			userRepository.save(dbUser);
			userRegisterNotification.setResult(Boolean.TRUE);
		}
		return userRegisterNotification;
	}

	
	
}
