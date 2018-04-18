package service;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.User;
import repository.UserRepository;
import validators.IValidator;
import validators.Notification;
import validators.UserValidator;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService{

	private UserRepository userRepository;
	private IValidator validator;
	@Autowired
	public AuthenticationServiceImplementation(UserRepository userRepository){
		this.userRepository = userRepository;
	}

	@Override
	public Notification<Boolean> register(User user) {
		validator = new UserValidator(user); 
		boolean userValid = validator.validate();
		Notification<Boolean> userRegisterNotification = new Notification<>();
		if(!userValid){
			validator.getErrors().forEach(userRegisterNotification::addError);
			userRegisterNotification.setResult(Boolean.FALSE);	
		}	
		else{
			user.setPassword(encodePassword(user.getPassword()));
			userRepository.save(user);
			userRegisterNotification.setResult(Boolean.TRUE);
		}
		System.out.println("USEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEr"+userValid);
		return userRegisterNotification;
	}

	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		return null;
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
