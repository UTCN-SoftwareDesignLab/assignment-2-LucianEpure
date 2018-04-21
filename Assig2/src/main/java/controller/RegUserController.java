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

import dto.SaleDto;
import entity.Book;
import entity.User;
import service.book.BookSearch;
import service.book.BookService;
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
    public String displayMenu(Model model) {
		
		//model.addAttribute("options", options);
		
		//model.addAttribute("valid", new String());
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
	
	@PostMapping(value = "/showBooks", params= "searchAll")
	public String searchByAll(@RequestParam("title") String title,@RequestParam("author") String author,@RequestParam("genre") String genre, Model model){
		
		List<Book> books = bookSearch.searchByAll(title, author, genre);
		if(books == null){
			model.addAttribute("valid", "No books found");
			return "regUser";
		}
		else{
			model.addAttribute("books", books);
			return "showBooks";
		}
	}
	
	@PostMapping(params = "sell")
	public String sellBook(Model model){
		
		SaleDto sale = new SaleDto();
		sale.setBookId(1);
		sale.setQuantity(3);
		
	
		Notification<Boolean> notification = saleBook.addSale(sale);
		if(notification.hasErrors())
			model.addAttribute("valid", notification.getFormattedErrors());
		else
			model.addAttribute("valid", "Succesfully sold!");
		return "regUser";
		
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