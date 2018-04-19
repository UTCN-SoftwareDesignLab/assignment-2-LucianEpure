package validators;

import java.util.ArrayList;
import java.util.List;
import dto.BookDto;


public class BookValidator implements IValidator{

    private final BookDto book;
    private final List<String> errors;

    public List<String> getErrors() {
        return errors;
    }
    
    public BookValidator(BookDto book) {
        this.book = book;
        errors = new ArrayList<>();
    }

    public boolean validate() {
       validateUpper(book.getTitle());
       validateUpper(book.getAuthor());
       validatePrice(book.getPrice());
       validateQuantity(book.getQuantity());
        return errors.isEmpty();
    }

    private void validateUpper(String toValidate){
    	if(!Character.isUpperCase(toValidate.charAt(0))){
    		errors.add("Title must begin with capital");
    	}
    }
    
    private void validatePrice(double price){
    	 if (price<0) {
             errors.add("Invalid price");
         }
    
    } 
    private void validateQuantity(int quantity){
    	if (quantity<0) {
            errors.add("Invalid quantity");
        }
    }
 
}
