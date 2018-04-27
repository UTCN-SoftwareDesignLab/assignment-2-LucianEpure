package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.RequestEntity;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dto.UserDto;
import entity.User;
import service.user.AuthenticationService;
import validators.Notification;

@Controller
@RequestMapping("/login")
public class LoginController implements WebMvcConfigurer{
	
	
	
	private AuthenticationService authenticationService;
	
	@Autowired
	public LoginController (AuthenticationService authenticationService){
		this.authenticationService = authenticationService;
	}
	
	
	@GetMapping()
	@Order(value = 1)
    public String displayMenu(Model model) {
		model.addAttribute(new UserDto());
        return "login";
	}

	
	@PostMapping(params="login")
    public String login(HttpServletRequest request, HttpServletResponse response,@ModelAttribute UserDto user,
                        BindingResult result) throws ServletException {
        try {
        	RequestCache requestCache = new HttpSessionRequestCache();
            request.login(user.getUsername(),user.getPassword());
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            if (savedRequest != null) {
                return "redirect:" + savedRequest.getRedirectUrl();
            } else {
                return "redirect:/";
            }

        } catch (ServletException authenticationFailed) {
            result.rejectValue(null, "authentication.failed");
            return "login";
        }
}
	/*
	@PostMapping(params="register")
    public String register( @ModelAttribute UserDto user, Model model) {

		
		Notification<Boolean> notification = authenticationService.register(user,"administrator");
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully registered!");
        return "login";
	}
	*/

	
	
}
