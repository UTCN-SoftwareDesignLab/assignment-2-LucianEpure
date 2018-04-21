package service.report;

public class ReportFactory {

	public static final String TYPECSV = "CSV";
	public static final String TYPEPDF = "PDF";
	public static ReportService getReport(String type){
		if(type.equalsIgnoreCase(TYPECSV))
			return new ReportServiceCSV();
		else if (type.equalsIgnoreCase(TYPEPDF))
			return new ReportServicePDF();
		else
			return null;
	}
}
