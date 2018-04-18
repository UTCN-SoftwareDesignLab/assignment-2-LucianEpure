package service.book;


import java.util.List;

import model.Book;



public interface BookService {

	 List<Book> findAll();
	 
	 Book save(Book book);
}
