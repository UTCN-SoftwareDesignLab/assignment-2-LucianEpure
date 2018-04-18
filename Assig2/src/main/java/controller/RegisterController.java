package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import model.User;
import service.user.AuthenticationService;
import validators.Notification;

@Controller

public class RegisterController {
	
	private AuthenticationService authenticationService;
	private Notification<Boolean> notification;
	@Autowired
	public RegisterController(AuthenticationService authenticationService){
		this.authenticationService = authenticationService;
	}
	
	
	@GetMapping(value = "/register")
	@Order(value = 1)
    public String displayMenu(Model model) {
		model.addAttribute("user",new User());
		model.addAttribute("valid", new String());
        return "loginreg";
	}
	
	@PostMapping(value = "/register",params="register")
    public String register( @ModelAttribute User user, Model model) {

		model.addAttribute("user",new User());
		notification = authenticationService.registerAdmin(user);
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully registered!");
        return "loginreg";
	}
	
	@PostMapping(value = "/register",params="login")
    public String login(@ModelAttribute User user, Model model) {
		model.addAttribute("user",user);
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
