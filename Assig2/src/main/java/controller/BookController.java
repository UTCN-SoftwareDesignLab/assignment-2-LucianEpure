package controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.BookDto;
import model.Book;
import model.User;
import service.book.BookService;

@Controller
@RequestMapping(value = "/book")
public class BookController {

	 private BookService bookService;
	
	  @Autowired
	  public BookController(BookService bookService){
		 this.bookService = bookService;
	  }
	  @GetMapping()
			public String register(Model model){
				model.addAttribute(new Book());	
				return "book";
			}
	  
	  
	  @PostMapping(value = "/books", params= "showBooks")
	  public String findAll(Model model) {
	        // returneaza fisieru html pe care il vrem in browser
	        final List<Book> books = bookService.findAll();
	        model.addAttribute("books", books);
	        System.out.println("BOOKS"+ books.size());
	        return "showBooks";
	    }
	 
	
		
	   @PostMapping(value="/addBook", params="addBook")
		    public String bookSubmit(@ModelAttribute Book book, Model model) {
		         
		        model.addAttribute("book", book);
		        bookService.save(book);
		        return "redirect:/book";
		    }
	 
}
