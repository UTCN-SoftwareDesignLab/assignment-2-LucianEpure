package controller;

import java.util.logging.Logger;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.User;
import service.AuthenticationService;

@Controller
@RequestMapping(value = "/authenticate")
public class RegisterController {
	
	private AuthenticationService authenticationService;
	
	@Autowired
	public RegisterController(AuthenticationService authenticationService){
		this.authenticationService = authenticationService;
	}
	/*
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    @Order(value = 1)
    public String log(Model model) {
		model.addAttribute("user",new User());
        return "loginreg";
	}*/
	
	@GetMapping(value = "/register")
    public String regi(Model model) {
		model.addAttribute("user",new User());
        return "loginreg";
	}
	
	@PostMapping(value = "/register",params="register")
    public String register(@ModelAttribute User user, Model model) {
		model.addAttribute("user",user);
		authenticationService.register(user);
        return "newAdmin";
	}
	@PostMapping(value = "/register",params="login")
    public String login(@ModelAttribute User user, Model model) {
		model.addAttribute("user",user);
		authenticationService.login(user);
        return "loginreg";
	}
	
	
	
}
