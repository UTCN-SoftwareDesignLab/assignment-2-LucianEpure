package service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dto.UserDto;
import entity.User;
import repository.UserRepository;
import validators.IValidator;
import validators.Notification;
import validators.UserValidator;
@Service
public class UserServiceImplementation implements UserService{

	private UserRepository userRepository;
	private IValidator validator;
	
	@Autowired
	public UserServiceImplementation(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	@Override
	public List<User> findAll() {

		final Iterable<User> users = userRepository.findAll();
		List<User> result = new ArrayList<User>();
		users.forEach(result::add);
		return result;
	}

	@Override
	public User findById(int id) {
		return userRepository.getOne(id);
	}

	@Override
	public void fireById(int id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public Notification<Boolean> update(UserDto user) {
		
		
		validator = new UserValidator(user); 
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		boolean userValid = validator.validate();
		Notification<Boolean> userRegisterNotification = new Notification<>();
		if(!userValid){
			validator.getErrors().forEach(userRegisterNotification::addError);
			System.out.println(userRegisterNotification.getFormattedErrors());
			userRegisterNotification.setResult(Boolean.FALSE);	
		}	
		else{
			User dbUser = userRepository.getOne(user.getId());
			dbUser.setUsername(user.getUsername());
			dbUser.setPassword(enc.encode(user.getPassword()));
			userRepository.save(dbUser);
			userRegisterNotification.setResult(Boolean.TRUE);
		}
		
		return userRegisterNotification;
	}
}
