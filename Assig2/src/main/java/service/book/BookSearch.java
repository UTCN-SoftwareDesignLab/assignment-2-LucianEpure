package service.book;

import java.util.List;

import entity.Book;

public interface BookSearch {

	List<Book> searchByTitle(String title);

	List<Book> searchBy(String searchBy);
	
}
