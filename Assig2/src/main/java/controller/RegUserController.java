package controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.SaleDto;
import entity.Book;
import service.book.BookSearch;
import service.order.SaleBook;
import validators.Notification;

@Controller
@RequestMapping(value = "/regUser")
public class RegUserController {

	 private BookSearch bookSearch;
	 private SaleBook saleBook;

	// private List<String>  
	 @Autowired
	  public RegUserController(BookSearch bookSearch, SaleBook saleBook ){
		 this.bookSearch = bookSearch;
		 this.saleBook = saleBook;
	  }
	
	@GetMapping()
	@Order(value = 1)
    public String displayMenu(Model model, HttpSession session) {
		if((boolean) session.getAttribute("loggedUser")){
			model.addAttribute(new SaleDto());
			return "regUser";
		}
		else{
			return "loginError";
		}
		
		
	}

	
	@PostMapping(value = "/showBooks", params= "searchAll")
	public String searchByAll(@RequestParam("title") String title,@RequestParam("author") String author,@RequestParam("genre") String genre, Model model){
		
		List<Book> books = bookSearch.searchByAll(title, author, genre);
		if(books == null){
			model.addAttribute("valid", "No books found");
			return "redirect:/regUser";
		}
		else{
			model.addAttribute("books", books);
			return "showBooks";
		}
	}
	
	@PostMapping(params = "sell")
	public String sellBook(@ModelAttribute SaleDto sale, Model model){
	
		Notification<Boolean> notification = saleBook.addSale(sale);
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully sold!");
		return "redirect:/regUser";
		
	}
	
	@PostMapping(value = "/showBooks", params= "searchBy")
	public String searchBy(@RequestParam("word") String word, Model model){
		
		List<Book> books = bookSearch.searchBy(word);
		//System.out.println(books.get(0).getTitle());
		if(books == null){
			model.addAttribute("valid", "No books found");
			return "redirect:/regUser";
		}
		else{
			model.addAttribute("books", books);
			return "showBooks";
		}
	}
	
	@PostMapping(params = "logout")
	public String logout(HttpSession session){
		session.setAttribute("loggedUser",false);
		return "redirect:/register";
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
