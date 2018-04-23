package tests.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@ContextConfiguration
public class BookServiceTest {
	
	@Configuration
	static class EmployeeServiceTestConfiguration{
		
		@Bean
		public BookRepository bookRepository(){
			return Mockito.mock(BookRepository.class);
		}
		
		@Autowired
		private  BookRepository bookRepository;
		
		@Bean
		public BookService bookService(){
			return new BookServiceImplementation(bookRepository);
		}
	}
	
	@Autowired 
	private BookService bookService;
	

	
	@Before
	public void setup(){
		Book book = new BookBuilder().setTitle("Marea carte").seAuthor("Mare autor").setGenre("tr").setPrice(25).setQuantity(2).build();
	//	Mockito.when(bookRepository.findByTitle("Marea carte").get(0)).thenReturn(book);
		
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
	
	
	
}
