package service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.SaleDto;
import entity.Book;
import entity.Sale;
import repository.BookRepository;
import repository.SaleRepository;
import validators.IValidator;
import validators.Notification;
import validators.SaleValidator;

@Service
public class SaleBookImplementation implements SaleBook{

	//private List<SaleDto> cart;
	//private Map<Integer,Integer> store;
	private SaleRepository saleRepository;
	private BookRepository bookRepository;
	private IValidator validator;
	@Autowired
	public SaleBookImplementation(SaleRepository saleRepository, BookRepository bookRepository){
		this.saleRepository = saleRepository;
		this.bookRepository = bookRepository;
	//	cart = new ArrayList<SaleDto>();
	//	store = new HashMap<Integer, Integer>();
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
			Sale dbSale = new Sale(book,sale.getQuantity());
			if(sale.getQuantity() > book.getQuantity()){
				saleNotification.addError(" Not enough books! ");
			}
			else{	
				book.setQuantity(book.getQuantity()-sale.getQuantity());
				bookRepository.save(book);//update book
				dbSale.setPrice(book.getPrice()*sale.getQuantity());
				saleRepository.save(dbSale);
			}	
		}
		return saleNotification;
	}
	/*
	public void addToCart(SaleDto sale){
		cart.add(sale);
		if(store.get(sale.getId())==null)
			store.put(sale.getId(), new Integer(sale.getQuantity()));
		else
			store.put(sale.getBookId(),store.get(sale.getBookId())+sale.getQuantity());
		
	}
	
	public void showCart(){
		 Set set = store.entrySet();
		Iterator i = set.iterator();
	      
	      // Display elements
	      while(i.hasNext()) {
	         Map.Entry me = (Map.Entry)i.next();
	         System.out.print(me.getKey() + ": ");
	         System.out.println(me.getValue());
	      }
	      System.out.println("hhhhhhhhhhhhhhhhhh");
	}
	
	public void checkout(){
		for(SaleDto sale:cart){
			
		}
	}*/

}
