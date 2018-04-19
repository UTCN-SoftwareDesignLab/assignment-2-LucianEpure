package service.user;

import java.security.MessageDigest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.UserDto;
import entity.Role;
import entity.User;
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
	public Notification<Boolean> registerAdmin(UserDto user) {
		validator = new UserValidator(user); 
		boolean userValid = validator.validate();
		Notification<Boolean> userRegisterNotification = new Notification<Boolean>();
		if(!userValid){
			validator.getErrors().forEach(userRegisterNotification::addError);
			userRegisterNotification.setResult(Boolean.FALSE);	
		}	
		else{
			User dbUser = new User();
			dbUser.setUsername(user.getUsername());
			dbUser.setPassword(encodePassword(user.getPassword()));
			List<Role> userRoles = dbUser.getRoles();
			userRoles.add(roleRepository.findByRoleName("administrator"));
			dbUser.setRoles(userRoles);
			userRepository.save(dbUser);
			userRegisterNotification.setResult(Boolean.TRUE);
		}
		System.out.println("USEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEr"+userValid);
		return userRegisterNotification;
	}
	
	@Override
	public Notification<Boolean> registerUser(UserDto user) {
		validator = new UserValidator(user); 
		boolean userValid = validator.validate();
		Notification<Boolean> userRegisterNotification = new Notification<Boolean>();
		if(!userValid){
			validator.getErrors().forEach(userRegisterNotification::addError);
			userRegisterNotification.setResult(Boolean.FALSE);	
		}	
		else{
			User dbUser = new User();
			dbUser.setUsername(user.getUsername());
			dbUser.setPassword(encodePassword(user.getPassword()));
			List<Role> userRoles = dbUser.getRoles();
			userRoles.add(roleRepository.findByRoleName("regUser"));
			dbUser.setRoles(userRoles);
			userRepository.save(dbUser);
			userRegisterNotification.setResult(Boolean.TRUE);
		}
		System.out.println("USEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEr"+userValid);
		return userRegisterNotification;
	}

	@Override
	public User login(UserDto user) {
		User loggedUser =  userRepository.findByUsernameAndPassword(user.getUsername(), encodePassword(user.getPassword()));
		return loggedUser;
	}
	
	@Override
	public String encodePassword(String password) {
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
