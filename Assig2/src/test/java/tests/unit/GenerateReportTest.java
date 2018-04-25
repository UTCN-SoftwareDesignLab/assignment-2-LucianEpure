package tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import entity.Book;

import service.report.ReportService;
import service.report.ReportServiceCSV;
import service.report.ReportServicePDF;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class GenerateReportTest {

ReportService generateReportCSV;
ReportService generateReportPDF;

	@Before
		public void setup(){
		this.generateReportCSV = new ReportServiceCSV();
		this.generateReportPDF = new ReportServicePDF();
	}
	
	@Test
	public void reportCSV(){
		Book b1= new Book();
		b1.setTitle("A");
		b1.setAuthor("B");
		b1.setGenre("C");
		b1.setQuantity(2);
		b1.setPrice(10);
		
		Book b2 = new Book();
		b1.setTitle("D");
		b1.setAuthor("E");
		b1.setGenre("F");
		b1.setQuantity(2);
		b1.setPrice(10);
		
		
		List<Book> books = new ArrayList<Book>();
		books.add(b1);
		books.add(b2);
		Assert.assertTrue(generateReportCSV.generateReport(books));
	}
	
	@Test
	public void reportPDF(){
		Book b1= new Book();
		b1.setTitle("A");
		b1.setAuthor("B");
		b1.setGenre("C");
		b1.setQuantity(2);
		b1.setPrice(10);
		
		Book b2 = new Book();
		b1.setTitle("D");
		b1.setAuthor("E");
		b1.setGenre("F");
		b1.setQuantity(2);
		b1.setPrice(10);
		
		List<Book> books = new ArrayList<Book>();
		books.add(b1);
		books.add(b2);
		Assert.assertTrue(generateReportPDF.generateReport(books));
	}
}
