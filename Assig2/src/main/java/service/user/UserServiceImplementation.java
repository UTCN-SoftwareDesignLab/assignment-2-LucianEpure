package service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Role;
import model.User;
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
		return null;//return userRepository.findById(id);
	}

	@Override
	public void fireById(int id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public Notification<Boolean> update(int id, String newUsername) {
		
		User user = userRepository.getOne(id);
		user.setUsername(newUsername);
		validator = new UserValidator(user); 
		System.out.println("USER"+user.getUsername());
		System.out.println("PASS"+user.getPassword());
		boolean userValid = validator.validate();
		Notification<Boolean> userRegisterNotification = new Notification<>();
		/*if(!userValid){
			validator.getErrors().forEach(userRegisterNotification::addError);
			System.out.println(userRegisterNotification.getFormattedErrors());
			userRegisterNotification.setResult(Boolean.FALSE);	
		}	
		else{*/
			
			userRepository.save(user);
		//	userRegisterNotification.setResult(Boolean.TRUE);
		//}
		//System.out.println("USEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEr"+userValid);
		return userRegisterNotification;
	}

}
