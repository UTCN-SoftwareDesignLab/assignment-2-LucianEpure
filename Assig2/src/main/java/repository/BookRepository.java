package repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import entity.Book;

public interface BookRepository extends JpaRepository<Book,Integer>{

	List<Book> findByGenre(String genre);
	
	List<Book> findByTitle(String title);
	
	List<Book> findByAuthor(String author);
	
	List<Book> findByTitleAndAuthorAndGenre(String title, String author, String genre);

	List<Book> findByQuantity(int quantity);
}
