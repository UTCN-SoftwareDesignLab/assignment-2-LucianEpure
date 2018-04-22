package service.report;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import dto.BookDto;
import entity.Book;

public class ReportServicePDF implements ReportService{

	public static final int PAGELIMIT = 30;

	@Override
	public void generateReport(List<Book> books) {
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		int rowCount = 0;
		document.addPage(page);
		PDPageContentStream contentStream;
		try {
			contentStream = new PDPageContentStream(document, page);
		
		 
		contentStream.setFont(PDType1Font.COURIER, 12);
		contentStream.setLeading(14.5f);
		contentStream.beginText();
		contentStream.newLineAtOffset(25, 700);
		contentStream.newLine(); 
		contentStream.showText("ID Title Author Genre Price Quantity");
		for(Book book:books){
			
			contentStream.showText(book.toString());
			contentStream.newLine(); 
			rowCount++;
			if(rowCount > PAGELIMIT)
				document.addPage(new PDPage());
		}
		
		contentStream.endText();
		contentStream.close();
		 
		document.save("OutOfStock.pdf");
		document.close();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}

}
