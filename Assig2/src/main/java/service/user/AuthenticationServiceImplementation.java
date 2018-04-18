package service.user;

import java.security.MessageDigest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Role;
import model.User;
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
		roleRepository.save(new Role("administrator"));
		roleRepository.save(new Role("regUser"));
	}

	@Override
	public Notification<Boolean> registerAdmin(User user) {
		validator = new UserValidator(user); 
		boolean userValid = validator.validate();
		Notification<Boolean> userRegisterNotification = new Notification<>();
		if(!userValid){
			validator.getErrors().forEach(userRegisterNotification::addError);
			userRegisterNotification.setResult(Boolean.FALSE);	
		}	
		else{
			user.setPassword(encodePassword(user.getPassword()));
			List<Role> userRoles = user.getRoles();
			userRoles.add(roleRepository.findByRoleName("administrator"));
			user.setRoles(userRoles);
			userRepository.save(user);
			userRegisterNotification.setResult(Boolean.TRUE);
		}
		System.out.println("USEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEr"+userValid);
		return userRegisterNotification;
	}
	
	@Override
	public Notification<Boolean> registerUser(User user) {
		validator = new UserValidator(user); 
		boolean userValid = validator.validate();
		Notification<Boolean> userRegisterNotification = new Notification<>();
		if(!userValid){
			validator.getErrors().forEach(userRegisterNotification::addError);
			userRegisterNotification.setResult(Boolean.FALSE);	
		}	
		else{
			user.setPassword(encodePassword(user.getPassword()));
			List<Role> userRoles = user.getRoles();
			userRoles.add(roleRepository.findByRoleName("regUser"));
			user.setRoles(userRoles);
			userRepository.save(user);
			userRegisterNotification.setResult(Boolean.TRUE);
		}
		System.out.println("USEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEr"+userValid);
		return userRegisterNotification;
	}

	@Override
	public User login(User user) {
		User loggedUser =  userRepository.findByUsernameAndPassword(user.getUsername(), encodePassword(user.getPassword()));
		return loggedUser;
	}
	
	public static String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
	}
	
	
}
