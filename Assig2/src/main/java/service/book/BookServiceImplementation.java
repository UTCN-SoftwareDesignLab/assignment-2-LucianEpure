package service.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.BookDto;
import entity.Book;
import entity.builder.BookBuilder;
import repository.BookRepository;
import validators.BookValidator;
import validators.IValidator;
import validators.Notification;

@Service
public class BookServiceImplementation implements BookService{

	private BookRepository bookRepository;
	private IValidator  validator;
	@Autowired
	public BookServiceImplementation(BookRepository bookRepository){
		this.bookRepository = bookRepository;
	}
	@Override
	public List<Book> findAll() {
		
	        final Iterable<Book> items = bookRepository.findAll();
	        List<Book> result = new ArrayList<Book>();
	        items.forEach(result::add);
	        return result;
	    }
	@Override
	public Notification<Boolean> save(BookDto book) {
		validator = new BookValidator(book);
		boolean bookValid = validator.validate();
		Notification<Boolean> addBookNotification = new Notification<Boolean>();
		if(!bookValid){
			validator.getErrors().forEach(addBookNotification::addError);
			addBookNotification.setResult(Boolean.FALSE);
		}
		else{
			Book dbBook = new BookBuilder().setTitle(book.getTitle()).seAuthor(book.getAuthor()).setGenre(book.getGenre()).setPrice(book.getPrice()).build();
					
					//new Book(book.getTitle(),book.getAuthor(),book.getGenre(),book.getPrice(),book.getQuantity());
			bookRepository.save(dbBook);
			addBookNotification.setResult(Boolean.TRUE);
		}
		return addBookNotification;
	}
	@Override
	public void deleteById(int id) {
		bookRepository.deleteById(id);
	}
	@Override
	public Notification<Boolean> update(BookDto book) {
		//Add reflection here to choose just the not null fields
		validator = new BookValidator(book);

		boolean bookValid = validator.validate();
		Notification<Boolean> updateBookNotification = new Notification<Boolean>();
		if(!bookValid){
			validator.getErrors().forEach(updateBookNotification::addError);
			updateBookNotification.setResult(Boolean.FALSE);
		}
		else{
		//	Book dbBook = bookRepository.getOne(book.getId());
			Book dbBook = bookRepository.getOne(book.getId());
			dbBook.setAuthor(book.getAuthor());
			dbBook.setTitle(book.getTitle());
			dbBook.setGenre(book.getGenre());
			dbBook.setQuantity(book.getQuantity());
			dbBook.setPrice(book.getPrice());
			bookRepository.save(dbBook);
			updateBookNotification.setResult(Boolean.TRUE);
		}
		
		return updateBookNotification;
	}
	@Override
	public List<Book> findOutOfStock() {
		return bookRepository.findByQuantity(0);
	}
	@Override
	public Book findById(int id) {
		return bookRepository.getOne(id);
	}
	@Override
	public void removeAll() {
		bookRepository.deleteAll();
	}
	@Override
	public List<Book> findByTitle(String title) {
		return bookRepository.findByTitle(title);
	}

}
