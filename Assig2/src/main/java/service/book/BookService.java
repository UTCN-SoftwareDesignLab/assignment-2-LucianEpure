package service.book;


import java.util.List;

import dto.BookDto;
import entity.Book;
import validators.Notification;



public interface BookService {

	 List<Book> findAll();
	 
	 Notification<Boolean> save(BookDto book);
	 
	 Notification<Boolean> update(BookDto book);
	 
	 Book findById(int id);
	 
	 List<Book> findByTitle(String title);
	 
	 void deleteById(int id);
	 
	 void removeAll();
	 
	 List<Book> findOutOfStock();
}
