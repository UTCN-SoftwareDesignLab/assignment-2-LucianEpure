package service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportFactory {

	public static final String TYPECSV = "CSV";
	public static final String TYPEPDF = "PDF";
	ReportService reportServiceCSV;
	ReportService reportServicePDF;
	
	
	@Autowired
	public ReportFactory(){
		reportServiceCSV = new ReportServiceCSV();
		reportServicePDF = new ReportServicePDF();
	}
	
	public ReportService getReport(String type){
		if(type.equalsIgnoreCase(TYPECSV))
			return new ReportServiceCSV();
		else if (type.equalsIgnoreCase(TYPEPDF))
			return new ReportServicePDF();
		else
			return null;
	}
}
