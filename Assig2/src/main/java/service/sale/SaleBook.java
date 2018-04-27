package service.sale;

import java.util.List;

import dto.SaleDto;
import entity.Sale;
import validators.Notification;

public interface SaleBook {

	Notification<Boolean> addSale(SaleDto sale);
	
	List<Sale> findAll();
}
