package controller;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import model.User;

@Controller
@RequestMapping(value = "/regUser")
public class RegUserController {

	
	@GetMapping()
	@Order(value = 1)
    public String displayMenu(Model model) {
		
        return "regUser";
	}
}
