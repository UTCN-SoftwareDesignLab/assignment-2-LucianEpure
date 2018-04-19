package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import dto.UserDto;
import entity.User;
import main.Bootstrap;
import service.user.AuthenticationService;
import validators.Notification;

@Controller

public class LoginController {
	
	private AuthenticationService authenticationService;
	private Notification<Boolean> notification;
	@Autowired
	public LoginController(AuthenticationService authenticationService){
		this.authenticationService = authenticationService;
	}
	
	
	@GetMapping(value = "/register")
	@Order(value = 1)
    public String displayMenu(Model model) {
		model.addAttribute("user",new UserDto());
		model.addAttribute("valid", new String());
        return "loginreg";
	}
	
	@PostMapping(value = "/register",params="register")
    public String register( @ModelAttribute UserDto user, Model model) {

		model.addAttribute("user",new UserDto());
		notification = authenticationService.registerAdmin(user);
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully registered!");
        return "loginreg";
	}
	
	@PostMapping(value = "/register",params="login")
    public String login(@ModelAttribute UserDto user, Model model) {
		model.addAttribute("user",new UserDto());
		User loggedUser = authenticationService.login(user);
		if(loggedUser != null){
			return decidePage(loggedUser);
		}
		else{
			model.addAttribute("valid", "Not logged!");
			return "loginreg";
		}
	}
	
	private String decidePage(User user){
		if(user.getRoles().get(0).getRoleName().equalsIgnoreCase("Administrator"))
			return "redirect:/admin";
		if (user.getRoles().get(0).getRoleName().equalsIgnoreCase("regUser"))
			return "redirect:/regUser";
		return null;
	}
	
	
}
