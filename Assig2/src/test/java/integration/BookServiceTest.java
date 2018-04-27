package integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import dto.BookDto;
import service.book.BookService;

@RunWith(SpringRunner.class)
@Configuration
@PropertySource("classpath:tests.properties")
@EnableJpaRepositories(basePackages = {"repository"})
@ComponentScan({"configuration","entity","dto","repository","service","controller","main","validators"})
@EntityScan(basePackages ={"entity"})
@Transactional


public class BookServiceTest {


	@Autowired 
	private BookService bookService;
	private final static String TITLE = "A";
	private final static String AUTHOR = "B";
	private final static String GENRE = "C";
	private final static double PRICE = 10;
	private final static int QUANTITY = 5;
	private final static String UPDATED_TITLE = "Z";


	@Before
	public void setup() throws Exception {
	   bookService.removeAll();
	}
	
	
	@Test 
	public void addBook(){
		BookDto b = new BookDto();
		b.setTitle(TITLE);
		b.setAuthor(AUTHOR);
		b.setGenre(GENRE);
		b.setQuantity(QUANTITY);
		b.setPrice(PRICE);
		bookService.save(b).getResult();
		Assert.assertTrue(bookService.findAll().size() == 1);
	}
	
	@Test
	public void findAll(){
	
		Assert.assertTrue(bookService.findAll().isEmpty() );
	}
	
	@Test
	public void  updateBook(){
		BookDto book = new BookDto();
		book.setTitle(TITLE);
		book.setAuthor(AUTHOR);
		book.setGenre(GENRE);
		book.setQuantity(QUANTITY);
		book.setPrice(PRICE);
		bookService.save(book);
		int id = bookService.findByTitle(TITLE).get(0).getId();
		BookDto book2 = new BookDto();
		book2.setId(id);
		book2.setTitle(UPDATED_TITLE);
		book2.setAuthor(AUTHOR);
		book2.setGenre(GENRE);
		book2.setQuantity(QUANTITY);
		book2.setPrice(PRICE);
		bookService.update(book2);
		Assert.assertTrue(bookService.findByTitle(TITLE).isEmpty());
	}
	
	@Test
	public void deleteBook(){
		BookDto book = new BookDto();
		book.setTitle(TITLE);
		book.setAuthor(AUTHOR);
		book.setGenre(GENRE);
		book.setQuantity(QUANTITY);
		book.setPrice(PRICE);
		bookService.save(book);
		int id = bookService.findByTitle(TITLE).get(0).getId();
		bookService.deleteById(id);
		Assert.assertTrue(bookService.findAll().isEmpty());
	}
	
	
}
