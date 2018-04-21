package validators;

import java.util.ArrayList;
import java.util.List;
import dto.BookDto;


public class BookValidator implements IValidator{

    private final BookDto book;
    private final List<String> errors;
    private static final double MIN_PRICE = 0.0;
    private static final int MIN_QUANTITY = 0;
   
    
    public BookValidator(BookDto book) {
        this.book = book;
        errors = new ArrayList<String>();
    }

    public boolean validate() {
       validateUpper(book.getTitle());
       validateUpper(book.getAuthor());
       validatePrice(book.getPrice());
       validateQuantity(book.getQuantity());
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }
    
    private void validateUpper(String toValidate){
    	if(!Character.isUpperCase(toValidate.charAt(0))){
    		errors.add(toValidate+" must begin with capital");
    	}
    }
    
    private void validatePrice(double price){
    	 if (price<MIN_PRICE) {
             errors.add("Price cannot be less than "+String.valueOf(MIN_PRICE));
         }
    
    } 
    private void validateQuantity(int quantity){
    	if (quantity<MIN_QUANTITY) {
            errors.add("Quantity cannot be less than "+String.valueOf(MIN_QUANTITY));
        }
    }
 
}
