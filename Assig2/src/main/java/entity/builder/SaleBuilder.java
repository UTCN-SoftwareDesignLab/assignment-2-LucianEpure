package entity.builder;

import entity.Book;
import entity.Sale;

public class SaleBuilder {

	private Sale sale;
	
	public SaleBuilder(){
		this.sale = new Sale();
	}
	
	public SaleBuilder setBook(Book book){
		sale.setBook(book);
		return this;
	}
	
	public SaleBuilder setQuantity(int quantity){
		sale.setQuantity(quantity);
		return this;
	}
	
	public SaleBuilder setPrice(double price){
		sale.setPrice(price);
		return this;
	}
	
	public Sale build(){
		return this.sale;
	}
}
