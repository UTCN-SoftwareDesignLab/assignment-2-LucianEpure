package repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import entity.Book;

public interface BookRepository extends JpaRepository<Book,Integer>{

	List<Book> findByGenre(String genre);
	
	List<Book> findByTitle(String title);
	
	List<Book> findByAuthor(String author);
	
	List<Book> findByQuantity(int quantity);
	
	@Query("SELECT b FROM Book b WHERE ((b.title) = CASE WHEN (:title) IS NULL THEN (b.title) ELSE (:title) END )AND ((b.author) = CASE WHEN (:author) IS NULL THEN (b.author) ELSE (:author) END) AND((b.genre) = CASE WHEN(:genre) IS NULL THEN (b.genre) ELSE (:genre)END)")
	List<Book> findByAny(@Param("title") String title,  @Param("author") String author, @Param("genre") String genre);
	
}
