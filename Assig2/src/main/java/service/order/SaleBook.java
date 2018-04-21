package service.order;

import dto.SaleDto;
import entity.Sale;
import validators.Notification;

public interface SaleBook {

	Notification<Boolean> addSale(SaleDto sale);
	/*
	void addToCart(SaleDto sale);
	
	void showCart();*/
}
