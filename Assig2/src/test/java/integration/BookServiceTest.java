package integration;

import java.util.ArrayList;
import java.util.List;

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
import dto.SaleDto;
import entity.Book;
import entity.Sale;
import service.book.BookSearch;
import service.book.BookService;
import service.report.ReportFactory;
import service.report.ReportService;
import service.sale.SaleBook;

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
	
	@Autowired
	private SaleBook saleBook;
	
	@Autowired
	private BookSearch bookSearch;
	
	@Autowired
	private ReportFactory reportFactory;
	
	private final static String TITLE = "Amiral";
	private final static String AUTHOR = "Bunicu";
	private final static String GENRE = "Cool";
	private final static double PRICE = 10;
	private final static int QUANTITY = 9;
	private final static int QUANTITY_EMPTY = 0;
	private final static String TITLE_2 = "Zoro";
	private final static String AUTHOR_2 ="Hick";
	private final static String GENRE_2 = "History";


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
		book2.setTitle(TITLE_2);
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
	@Test
	public void findOutOfStock(){
		BookDto book = new BookDto();
		book.setTitle(TITLE);
		book.setAuthor(AUTHOR);
		book.setGenre(GENRE);
		book.setQuantity(QUANTITY);
		book.setPrice(PRICE);
		bookService.save(book);
		BookDto book2 = new BookDto();
		book2.setTitle(TITLE);
		book2.setAuthor(AUTHOR);
		book2.setGenre(GENRE);
		book2.setQuantity(QUANTITY_EMPTY);
		book2.setPrice(PRICE);
		bookService.save(book2);
		List<Book> books = bookService.findOutOfStock();
		
		Assert.assertTrue(books.size()==1);
	}
	
	@Test
	public void findTitle(){
		BookDto book = new BookDto();
		book.setTitle(TITLE);
		book.setAuthor(AUTHOR);
		book.setGenre(GENRE);
		book.setQuantity(QUANTITY);
		book.setPrice(PRICE);
		bookService.save(book);
		Assert.assertNotNull(bookService.findByTitle(TITLE));
	}
	
	@Test
	public void sell(){
		BookDto book = new BookDto();
		book.setTitle(TITLE);
		book.setAuthor(AUTHOR);
		book.setGenre(GENRE);
		book.setQuantity(QUANTITY);
		book.setPrice(PRICE);
		bookService.save(book);
		SaleDto sale = new SaleDto();
		sale.setBookId(bookService.findByTitle(TITLE).get(0).getId());
		sale.setQuantity(3);
		saleBook.addSale(sale);
		Sale saleDb;
		saleDb = saleBook.findAll().get(0);
		Assert.assertTrue(saleDb.getPrice() == (bookService.findByTitle(TITLE).get(0).getPrice()*saleDb.getQuantity()));
	}
	
	@Test
	public void searchBy(){
		BookDto book = new BookDto();
		book.setTitle(TITLE);
		book.setAuthor(AUTHOR);
		book.setGenre(GENRE);
		book.setQuantity(QUANTITY);
		book.setPrice(PRICE);
		bookService.save(book);
		
		BookDto book2 = new BookDto();
		book2.setTitle(TITLE_2);
		book2.setAuthor(AUTHOR_2);
		book2.setGenre(GENRE_2);
		book2.setQuantity(QUANTITY);
		book2.setPrice(PRICE);
		bookService.save(book2);
		
		Assert.assertTrue(bookSearch.searchBy(TITLE).size()==1);
	}
	
	@Test 
	public void report(){
		BookDto book = new BookDto();
		book.setTitle(TITLE);
		book.setAuthor(AUTHOR);
		book.setGenre(GENRE);
		book.setQuantity(QUANTITY);
		book.setPrice(PRICE);
		bookService.save(book);
		
		ReportService reportServiceCSV = reportFactory.getReport("CSV");
		ReportService reportServicePDF = reportFactory.getReport("PDF");
		Assert.assertNotNull(reportServiceCSV.generateReport(bookService.findOutOfStock()));
		Assert.assertNotNull(reportServicePDF.generateReport(bookService.findOutOfStock()));
	}
	
	
}
