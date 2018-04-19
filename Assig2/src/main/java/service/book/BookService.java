package service.book;


import java.util.List;

import dto.BookDto;
import model.Book;
import validators.Notification;



public interface BookService {

	 List<Book> findAll();
	 
	 Notification<Boolean> save(BookDto book);
	 
	 Notification<Boolean> update(BookDto book);
	 
	 void deleteById(int id);
}
