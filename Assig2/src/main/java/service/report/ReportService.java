package service.report;

import java.util.List;

import dto.BookDto;
import entity.Book;


public interface ReportService {

	public void generateReport(List<Book> books);
}
