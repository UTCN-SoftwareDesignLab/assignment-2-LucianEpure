package validators;

import java.util.ArrayList;
import java.util.List;

import dto.SaleDto;
import entity.Sale;

public class SaleValidator implements IValidator{

	 private final SaleDto sale;
	 private final List<String> errors;
	 public static final int MIN_QUANTITY = 1;
	 public SaleValidator(SaleDto sale){
		 this.sale = sale;
		 errors = new ArrayList<String>();
	 }
	@Override
	public boolean validate() {
		validateQuantity(sale.getQuantity());
		return errors.isEmpty();
	}

	@Override
	public List<String> getErrors() { 
		return errors;
	}
	
	private void validateQuantity(int quantity){
		if(quantity<MIN_QUANTITY){
			errors.add("Quantity cannot be less than "+ MIN_QUANTITY);
		}
	}

}
