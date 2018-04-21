package service.report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import entity.Book;


public class ReportServiceCSV implements ReportService{

	private static final String FILENAME = "OutOfStock.csv";
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String FILE_HEADER = "id,title,autho,genre,price";
	@Override
	public void generateReport(List<Book> books) {
		 FileWriter fileWriter = null;
		 
		 try {
			fileWriter = new FileWriter(FILENAME);
			fileWriter.append(FILE_HEADER);
			fileWriter.append(NEW_LINE_SEPARATOR);
			for(Book book:books){
				fileWriter.append(String.valueOf(book.getId()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(book.getTitle());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(book.getAuthor());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(book.getGenre());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(book.getPrice()));
				fileWriter.append(NEW_LINE_SEPARATOR);
				
			}
		 }
			 catch (Exception e) {
				 	            System.out.println("Error in CsvFileWriter !!!");
				 	        } finally {
				 	            try {
				 	                fileWriter.flush();
				 	                fileWriter.close();
				 	            } catch (IOException e) {
				 	                System.out.println("Error while flushing/closing fileWriter !!!");
				 	                e.printStackTrace();
				 	            }
				 	        }
				
			
		
	}

}
