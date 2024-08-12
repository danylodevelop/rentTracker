package rentTracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class bankStatementProcessor {
	private List<bankStatement> bankStatements;
	
	public bankStatementProcessor() {
		this.bankStatements = new ArrayList<>();
	}
	
	//using apache load data from xls sheet into a array list of bankStatements
	public String loadBankStatements() throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder bankStatementsDetails = new StringBuilder();
		try {
			//when importing the xlsx file from bank make sure to clean the file and delete empty columns otherwise it will not read the file correctly
			FileInputStream xlsFile = new FileInputStream("C:\\Users\\Daniel\\Desktop\\2024innovativeLiving.xlsx");
			Workbook wb = WorkbookFactory.create(xlsFile);
			Sheet sheet = wb.getSheetAt(0);
			
			//used to skip the first row of header
			boolean skipHeader = true;
			for(Row row: sheet) {
				
				if(skipHeader) {
					skipHeader = false;
					continue;
				}
				
				String date = "";
				String description = "";
				double moneyIn = 0;
				double moneyOut = 0;
				
				//iterate through each cell and assign it to the correct attribute for the bankStatement class
				for(Cell cell: row) {
					
					switch(cell.getColumnIndex()) {
						case 0:{
							if (cell.getCellType() == CellType.NUMERIC) {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    // Handle date formatted cells
                                    Date cellDate = cell.getDateCellValue();
                                    date = dateFormat.format(cellDate);
                                } else {
                                    // Handle numeric values representing dates
                                    double numericDate = cell.getNumericCellValue();
                                    Date cellDate = DateUtil.getJavaDate(numericDate);
                                    date = dateFormat.format(cellDate);
                                }
                            } else if (cell.getCellType() == CellType.STRING) {
                                // Handle string formatted dates
                                date = cell.getStringCellValue();
                            }
							break;
						}
						case 1:{
							if (cell.getCellType() == CellType.STRING) {
                                description = cell.getStringCellValue();
                            } else {
                                description = "Unknown";
                            }

							break;
						}
						case 2: {
							if (cell.getCellType() == CellType.NUMERIC) {
                                moneyIn = cell.getNumericCellValue();
                            } else {
                                moneyIn = 0; // Handle unexpected cell types
                            }
							break;
						}
						case 3: {
							if (cell.getCellType() == CellType.NUMERIC) {
                                moneyOut = cell.getNumericCellValue();
                            } else {
                                moneyOut = 0; // Handle unexpected cell types
                            }
							break;
						}
						case 4:{
							break;
							//balance is ignored as is not needed
						}
					
					}
					
				}
				// Skip empty rows or invalid rows which could be due to the formatting of the file
	            if (date.isEmpty() && description.isEmpty() && moneyIn == 0 && moneyOut == 0) {
	                continue;
	            }
				
				
				bankStatement statement = new bankStatement(date,description,moneyIn,moneyOut);
				bankStatements.add(statement);
			}
			
			xlsFile.close();
			
			//test iteration to check that bankStatments are correctly added to arraylist and shown in the correct format
			
			/*
			for(bankStatement statement: bankStatements) {
				if(statement.getMoneyIn()>0 && statement.getMoneyOut()==0 && statement.getDescription().contains("MAKAPE")) {
				System.out.println(statement.toString());
				}
			}  
			*/
			for(bankStatement statements:bankStatements) {
				bankStatementsDetails.append(statements);
			}
	
			
		}catch(Exception e) {
			System.out.println("readexcel catch block");
			e.printStackTrace();
		}
		return bankStatementsDetails.toString();
	}
	
	//takes the statements of a selected month and only returns the expenses (money out of that month)
	public String findSelectedMonthExpense(String selectedDate){
		StringBuilder ExpenseList = new StringBuilder();
		try {
			
			for (bankStatement statement : bankStatements) {
				String statementMonthYear = statement.getDate().substring(3,10);
				
				if (statementMonthYear.equals(selectedDate) && statement.getMoneyOut() >0) {
					ExpenseList.append(statement);
				}
			}
			}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		if (ExpenseList.length() == 0) {
			JOptionPane.showMessageDialog(null,"No Expenses Have Been Found For This Month", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return ExpenseList.toString();
	}
	
	//a reference name (usually their surname) of tenant is passed in an compared to the list of bank statements and bank statements with the corresponding tenant name in the reference are added to payments list and then returned
	public String findPaymentsForTenant(String refTenantName){
		refTenantName = refTenantName.toUpperCase().replaceAll("\\s+",""); // make uppercase and remove all whitespace
		
		if (refTenantName.equals(" ") || refTenantName.equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter A Valid Reference Name", "Error", JOptionPane.ERROR_MESSAGE);
			return "";
		}
		
		List<bankStatement> payments = new ArrayList<>();
		StringBuilder tenantPaymentList = new StringBuilder();
		
		for(bankStatement statement: bankStatements) {
			if((statement.getDescription().replaceAll("\\s+","")).contains(refTenantName)) {
				payments.add(statement);
				tenantPaymentList.append(statement);
			}
		}
		//check if the list is empty if so either invalid tenant or no payments from tenant
		if(payments.size() == 0) {
			JOptionPane.showMessageDialog(null, refTenantName + " Has Not Been Found In Our Bank Statements.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return tenantPaymentList.toString();
	}
	
	// date input is passed in and only months from that date is selected with money IN and only being money in from tenants (rental payments) will be outputted 
	public String findSelectedMonthRent(String selectedDate) {
		StringBuilder monthRentList = new StringBuilder();
		try {
			tenantManager tm = new tenantManager();
			tm.loadTenantsVOID();
			ArrayList<String> refNames = new ArrayList<String>();
			// Collect all tenant reference names
			for(tenant tenant : tm.getTenants()) {
				refNames.add(tenant.getRefTenantName().toUpperCase());
			}
			
			
			for (bankStatement statement : bankStatements) {
				String statementMonthYear = statement.getDate().substring(3,10);
				// Check if the statement matches the selected month and contains money in
				if (statement.getMoneyIn()> 0 && statementMonthYear.equals(selectedDate)) {
					// Check if the statement's description contains any tenant reference name
					for(String refName : refNames) {
						if (statement.getDescription().toUpperCase().contains(refName)) {
							monthRentList.append(statement);
							
						}
					}
				}
			}
			}catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		if (monthRentList.length() == 0) {
			JOptionPane.showMessageDialog(null,"No Rent Payments Have Been Found For This Month", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return monthRentList.toString();
	}
	
	//getter methods
	public List<bankStatement> getBankStatements(){
		return this.bankStatements;
	}
	
}
