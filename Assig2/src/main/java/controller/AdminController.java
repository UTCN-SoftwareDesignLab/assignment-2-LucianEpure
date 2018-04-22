package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.UserDto;
import entity.User;
import service.user.AuthenticationService;
import service.user.UserService;
import validators.Notification;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	private UserService userService;
	private AuthenticationService authenticationService;
	
	@Autowired
	public AdminController(UserService userService, AuthenticationService authenticationService){
		this.userService = userService;
		this.authenticationService = authenticationService;
	}
	
	@GetMapping()
	@Order(value = 1)
	 public String displayMenu( Model model) {
			model.addAttribute(new UserDto());	
			System.out.println("Admin logged is "+LoginController.loggedAdmin);
			if(LoginController.loggedAdmin)
				return "administrator";
			else
				return "loginError";
	    }
	@PostMapping(value = "/showUsers",params="showUsers")
	 public String findAll(Model model) {
	        List<User> users = userService.findAll();
	        model.addAttribute("users", users);
	        return "showUsers";
	    }
	
	@PostMapping(params="deleteUser")
	 public String delete( @RequestParam("deleteId") String deleteId, Model model) {
			userService.fireById(Integer.parseInt(deleteId));
			return "redirect:/admin";
	    }
	
	@PostMapping(params = "addUser")
	public String addUser(@ModelAttribute UserDto user, Model model){
		Notification<Boolean> notification = authenticationService.registerUser(user);
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully registered!");
		return "redirect:/admin";
	}
	
	@PostMapping(params = "updateUser")
	//public String updateUser(@RequestParam("updateId") String updateId,@RequestParam("newUsername") String newUsername,@RequestParam Model model){
	public String updateUser(@ModelAttribute UserDto user, Model model){
		Notification<Boolean> notification = userService.update(user);
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully registered!");
		return "rediredr:/admin";
	}
	@PostMapping(params = "logout")
	public String logout(){
		LoginController.loggedAdmin = false;
		return "redirect:/register";
	}
	
	
	
	
	
	
}
