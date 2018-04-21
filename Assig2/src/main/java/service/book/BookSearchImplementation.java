package service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Book;
import repository.BookRepository;
import validators.IValidator;

@Service
public class BookSearchImplementation implements BookSearch{

	
	private BookRepository bookRepository;
	
	@Autowired
	public BookSearchImplementation(BookRepository bookRepository){
		this.bookRepository = bookRepository;
	}
	
	@Override
	public List<Book> searchByTitle(String title) {
		return bookRepository.findByTitle(title);
		
	}

	@Override
	public List<Book> searchByAuthor(String author) {
		return bookRepository.findByAuthor(author);
		
	}

	@Override
	public List<Book> searchByGenre(String genre) {
		return bookRepository.findByGenre(genre);
		
	}

	@Override
	public List<Book> searchByAll(String title, String author, String genre) {
	
		return bookRepository.findByTitleAndAuthorAndGenre(title, author, genre);
	}

	
}
