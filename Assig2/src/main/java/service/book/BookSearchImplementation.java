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
	public List<Book> searchBy(String word) {
		System.out.println("AAAAAAAAAAA"+ word);
		return bookRepository.findByWord(word);
	}

	
}
