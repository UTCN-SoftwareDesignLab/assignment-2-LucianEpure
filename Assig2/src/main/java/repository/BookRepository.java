package repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import model.Book;

public interface BookRepository extends CrudRepository<Book,Long>{

}
