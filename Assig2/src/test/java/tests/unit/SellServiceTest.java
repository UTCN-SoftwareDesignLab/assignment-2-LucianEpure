package tests.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dto.SaleDto;
import entity.Book;
import entity.builder.BookBuilder;
import repository.BookRepository;
import repository.SaleRepository;
import service.order.SaleBook;
import service.order.SaleBookImplementation;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class SellServiceTest {
	
	SaleBook saleBook;
	
	@Mock
	BookRepository bookRepository;
	
	@Mock
	SaleRepository saleRepository;
	
	@Before
	public void setup(){
		this.saleBook = new SaleBookImplementation(saleRepository, bookRepository);
		Book book = new BookBuilder().setTitle("A").seAuthor("B").setGenre("C").setPrice(25).setQuantity(5).build();
		Mockito.when(bookRepository.getOne(1)).thenReturn(book);
	}
	
	@Test
	public void sellBook(){
		
		SaleDto sale = new SaleDto();
		sale.setBookId(1);
		sale.setQuantity(3);
	
		Assert.assertTrue(saleBook.addSale(sale).getResult());
	}
}
