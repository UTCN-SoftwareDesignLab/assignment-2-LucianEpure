package service.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.SaleDto;
import entity.Book;
import entity.Sale;
import entity.builder.SaleBuilder;
import repository.BookRepository;
import repository.SaleRepository;
import validators.IValidator;
import validators.Notification;
import validators.SaleValidator;

@Service
public class SaleBookImplementation implements SaleBook{


	private SaleRepository saleRepository;
	private BookRepository bookRepository;
	private IValidator validator;
	@Autowired
	public SaleBookImplementation(SaleRepository saleRepository, BookRepository bookRepository){
		this.saleRepository = saleRepository;
		this.bookRepository = bookRepository;
	
	}
	
	@Override
	public Notification<Boolean> addSale(SaleDto sale) {
		
		validator = new SaleValidator(sale);
		
		boolean saleValid = validator.validate();
		Notification<Boolean> saleNotification = new Notification<Boolean>();
		if(!saleValid){	
			validator.getErrors().forEach(saleNotification::addError);
			saleNotification.setResult(Boolean.FALSE);
		}
		else{	
			Book book = bookRepository.getOne(sale.getBookId());
			if(book==null)
				saleNotification.addError("Not such book!");
			Sale dbSale = new SaleBuilder().setBook(book).setQuantity(sale.getQuantity()).build();
				
			if(sale.getQuantity() > book.getQuantity()){
				saleNotification.addError(" Not enough books! ");
				saleNotification.setResult(Boolean.FALSE);
			}
			else{	
				book.setQuantity(book.getQuantity()-sale.getQuantity());
				bookRepository.save(book);//update book
				dbSale.setPrice(book.getPrice()*sale.getQuantity());
				saleRepository.save(dbSale);
				saleNotification.setResult(Boolean.TRUE);
			}	
		}
		return saleNotification;
	}

	@Override
	public List<Sale> findAll() {
		return saleRepository.findAll();
	}

}
