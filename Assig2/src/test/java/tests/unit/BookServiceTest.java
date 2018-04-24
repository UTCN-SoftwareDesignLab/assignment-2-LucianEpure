package tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import dto.BookDto;
import entity.Book;
import entity.builder.BookBuilder;
import repository.BookRepository;
import service.book.BookService;
import service.book.BookServiceImplementation;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")

//@DataJpaTest

public class BookServiceTest {
	

	BookService bookService;
	@Mock
	BookRepository bookRepository;

	
	@Before
	public void setup(){
		
		bookService = new BookServiceImplementation(bookRepository);
		Book book = new BookBuilder().setTitle("A").seAuthor("B").setGenre("C").setPrice(25).setQuantity(0).build();
		List<Book> books = new ArrayList<Book>();
		books.add(book);
		Mockito.when(bookRepository.getOne(1)).thenReturn(book);
		Mockito.when(bookRepository.findByQuantity(0)).thenReturn(books);
		//Mockito.when(bookRepository.save(book)).thenReturn(true);
		
	}
	@Test()
	public void addBook(){
		//Book book = new BookBuilder().setTitle("A").seAuthor("B").setGenre("C").setPrice(25).setQuantity(2).build();
		BookDto book = new BookDto();
		book.setTitle("A");
		book.setAuthor("B");
		book.setGenre("C");
		book.setPrice(10);
		book.setQuantity(3);
		System.out.println(book.getTitle());
		Assert.assertTrue(bookService.save(book).getResult());
	}
	@Test()
	public void findBook(){
		List<Book> books = bookService.findAll();
		Assert.assertNotNull(books);
		Assert.assertEquals(books.size(), 0);
	}
	@Test()
	public void updateBook(){
		BookDto book = new BookDto();
		book.setId(1);
		book.setTitle("A");
		book.setAuthor("B");
		book.setGenre("C");
		book.setPrice(10);
		book.setQuantity(3);
		Assert.assertTrue(bookService.update(book).getResult());
	}

	@Test()
	public void findOutOfStock(){
		List<Book> books = bookService.findOutOfStock();
		Assert.assertNotNull(books);
		Assert.assertEquals(books.size(), 1);
	}
	
	
	
	
}
