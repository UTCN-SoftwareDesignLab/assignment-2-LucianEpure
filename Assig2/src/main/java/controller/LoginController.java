package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dto.UserDto;
import entity.User;
import service.user.AuthenticationService;
import validators.Notification;

@Controller
@RequestMapping("/register")
public class LoginController {
	
	
	
	private AuthenticationService authenticationService;
	
	@Autowired
	public LoginController(AuthenticationService authenticationService){
		this.authenticationService = authenticationService;
	}
	
	
	@GetMapping()
	@Order(value = 1)
    public String displayMenu(Model model,HttpServletRequest request, HttpSession session) {
		
		session.invalidate();
		session = request.getSession();
		session.setAttribute("loggedUser", false);
		session.setAttribute("loggedAdmin", false);
		model.addAttribute(new UserDto());
		
        return "loginreg";
	}
	
	@PostMapping(params="register")
    public String register( @ModelAttribute UserDto user, Model model) {

		//model.addAttribute("user",new UserDto());
		Notification<Boolean> notification = authenticationService.registerAdmin(user);
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully registered!");
        return "loginreg";
	}
	
	@PostMapping(params="login")
    public String login(@ModelAttribute UserDto user, Model model, HttpSession session) {
		//model.addAttribute("user",new UserDto());
		User loggedUser = authenticationService.login(user);
		if(loggedUser != null){
			
			
			return decidePage(loggedUser,session);
		}
		else{
			
		   
			model.addAttribute("valid", "Not logged!");
			//System.out.println(arg0);
			
			return "loginreg";
		}
	}
	
	
	
	private String decidePage(User user,HttpSession session){
		if(user.getRoles().get(0).getRoleName().equalsIgnoreCase("Administrator")){
			session.setAttribute("loggedAdmin", true);
			return "redirect:/admin";
		}	
		if (user.getRoles().get(0).getRoleName().equalsIgnoreCase("regUser")){
			session.setAttribute("loggedUser", true);
			return "redirect:/regUser";
		}
			
		return null;
	}
	
	
}
