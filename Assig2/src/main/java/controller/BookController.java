package controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.BookDto;
import model.Book;
import model.User;
import service.book.BookService;

@Controller

public class BookController {

	 private BookService bookService;
	
	 @Autowired
	 public BookController(BookService bookService){
		 this.bookService = bookService;
	 }
	 
	 @GetMapping("/views")
	 public String findAll(Model model) {
	        // returneaza fisieru html pe care il vrem in browser
	        final List<Book> books = bookService.findAll();
	        model.addAttribute("books", books);
	        return "view";
	    }
	 
	   @RequestMapping(value="/addBook", method = RequestMethod.GET)
		public String register(Model model){
			model.addAttribute("book",new Book());
			
			return "addBook";
		}
		
	   @RequestMapping(value="/addBook", method=RequestMethod.POST)
		    public String bookSubmit(@ModelAttribute Book book, Model model) {
		         
		        model.addAttribute("book", book);
		        bookService.save(book);
		        return "result";
		    }
	 
}
