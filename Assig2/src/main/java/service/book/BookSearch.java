package service.book;

import java.util.List;

import entity.Book;

public interface BookSearch {

	List<Book> searchByTitle(String title);
	
	List<Book> searchByAuthor(String title);
	
	List<Book> searchByGenre(String title);
	
	List<Book> searchByAll(String title, String author, String genre);
	
}
