package service.book;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Book;
import model.builder.BookBuilder;
import repository.BookRepository;

@Service
public class BookServiceImplementation implements BookService{

	private BookRepository bookRepository;
	
	@Autowired
	public BookServiceImplementation(BookRepository bookRepository){
		this.bookRepository = bookRepository;
		Book book = new BookBuilder().seAuthor("Steinbeck").setTitle("East of Eden").setGenre("drama").setPrice(50).setQuantity(20).build();
		bookRepository.save(book);
	}
	@Override
	public List<Book> findAll() {
		
	        final Iterable<Book> items = bookRepository.findAll();
	        List<Book> result = new ArrayList<Book>();
	        items.forEach(result::add);
	        return result;
	    }
	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}

}
