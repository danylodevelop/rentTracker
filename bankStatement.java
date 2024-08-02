package rentTracker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class bankStatement {
	private String date;
	private String description;
	private double moneyIn;
	private double moneyOut;
	
	// Date formatter for 'dd/MM/yyyy' format
    //private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	//Represents a bank statement entry with date, description, money in, and money out.
	public bankStatement(String date, String description, double moneyIn, double moneyOut){
		this.date = date;
		this.description = description;
		this.moneyIn = moneyIn;
		this.moneyOut = moneyOut;	
	}
	
	// Method to convert date string to LocalDate
	/*
    private LocalDate convertStringToDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }
	*/
	
	
	// getter method for bankStatement
	public String getDate() {
		return this.date;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public double getMoneyIn() {
		return this.moneyIn;
	}
	
	public double getMoneyOut() {
		return this.moneyOut;
	}
	
	// Method to convert date string to LocalDate
	public Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(dateString);
    }
	
	@Override
	public String toString() {
		return "BANK STATEMENT " + " \n"+
				" Date: " + this.date + ", \n"+
				" Description: " + this.description + ", \n"+
				" Money In: £" + this.moneyIn + ", \n"+
				" Money out: £"+ this.moneyOut + " \n \n" ;
	}
}
