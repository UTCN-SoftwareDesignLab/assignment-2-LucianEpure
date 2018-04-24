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

import dto.BookDto;
import entity.Book;
import main.Constants;
import service.book.BookService;
import service.report.ReportFactory;
import service.report.ReportService;
import validators.Notification;

@Controller
@RequestMapping(value = "/admin/book")
public class BookController {

	 private BookService bookService;
	 private ReportFactory reportFactory;
	
	  @Autowired
	  public BookController(BookService bookService, ReportFactory reportFactory){
		 this.bookService = bookService;
		 this.reportFactory = reportFactory;
		
	  }
	  @GetMapping()
	  @Order(value = 1)
			public String register(Model model){
		  
		  		model.addAttribute(new BookDto());	
				model.addAttribute("value",new String());
				model.addAttribute("options",Constants.TYPES);
				return "book";
	  }
	  
	  
	  @PostMapping(value = "/showBooks", params= "showBooks")
	  public String findAll(Model model) {
	        // returneaza fisieru html pe care il vrem in browser
	        final List<Book> books = bookService.findAll();
	        model.addAttribute("books", books);
	        return "showBooks";
	    }
	 
	  @PostMapping(params="deleteBook")
		 public String delete( @RequestParam("bookId") String bookId) {
				bookService.deleteById(Integer.parseInt(bookId));
				return "redirect:/admin/book";
		    }
		
	   @PostMapping( params="addBook")
		    public String bookSubmit(@ModelAttribute BookDto book, Model model) {
		         
		   	Notification<Boolean> notification = bookService.save(book);
		        if(notification.hasErrors())
					model.addAttribute("valid", notification.getFormattedErrors());
				else
					model.addAttribute("valid", "Succesfully registered!");
		        return "redirect:/admin/book";
		    }
	   
	   @PostMapping(params = "updateBook")
		//public String updateUser(@RequestParam("updateId") String updateId,@RequestParam("newUsername") String newUsername,@RequestParam Model model){
		public String updateBook(@ModelAttribute BookDto book, Model model){
			System.out.println("ID "+book.getId()+" Booook2 "+book.getTitle());
			Notification<Boolean> notification  = bookService.update(book);
			if(notification.hasErrors())
				model.addAttribute("valid", notification.getFormattedErrors());
			else
				model.addAttribute("valid", "Succesfully registered!");
			return "redirect:/admin/book";
		}
	   
	   @PostMapping(params = "generateReport")
		public String generateReport(@RequestParam("type") String type){
		  
			List<Book> books = bookService.findOutOfStock();
			ReportService reportService = reportFactory.getReport(type);
			reportService.generateReport(books);
			
			return "redirect:/admin/book";
		}

	  /* 
	   @PostMapping(params = "logout")
		public String logout(){
			LoginController.loggedAdmin = false;
			return "redirect:/register";
		}
	 */
}
