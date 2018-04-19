package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entity.Book;
import entity.User;
import service.book.BookSearch;
import service.book.BookService;

@Controller
@RequestMapping(value = "/regUser")
public class RegUserController {

	 private BookSearch bookSearch;
	// private List<String>  
	 @Autowired
	  public RegUserController(BookSearch bookSearch){
		 this.bookSearch = bookSearch;
	  }
	
	@GetMapping()
	@Order(value = 1)
    public String displayMenu(Model model) {
		
		//model.addAttribute("options", options);
		
		model.addAttribute("valid", new String());
        return "regUser";
	}
	
	@PostMapping(value = "/showBooks", params= "search")
	public String searchBy(@RequestParam("value") String value,@RequestParam("field") String field, Model model){
		
		List<Book> books = decideSearch(field,value);
		if(books == null){
			model.addAttribute("valid", "No books of such genre!");
			return "regUser";
		}
		else{
			model.addAttribute("books", books);
			return "showBooks";
		}
		
		
		
	}
	
	public List<Book> decideSearch(String searchBy, String field){
		if(searchBy.equalsIgnoreCase("genre"))
			return bookSearch.searchByGenre(field);
		else if (searchBy.equalsIgnoreCase("author"))
			return bookSearch.searchByAuthor(field);
		else if (searchBy.equalsIgnoreCase("title"))
			return bookSearch.searchByTitle(field);
		else
			return null;
	}
}
