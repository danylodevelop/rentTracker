package rentTracker;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class tenantManager {
	private List<tenant> tenants;
	
	public tenantManager() {
		this.tenants = new ArrayList<>();
	}
	
	//take the tenant info from tenantinfo file and creates an object for each tenant then adds this to an arraylist and outputs it
	public String loadTenants() throws FileNotFoundException {
		StringBuilder tenantInfo = new StringBuilder();
		
		File inputFile = new File("C:\\Users\\Daniel\\Desktop\\tenantInfo.txt");
		Scanner fileScanner = new Scanner(inputFile);
		
		while(fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String [] parts = line.split(",");
			
			if(parts.length >=7 ) {
				String fName = parts[0].trim();
				String lName = parts[1].trim();
				String refName = parts[2].trim().toUpperCase();
				properties property = properties.valueOf(parts[3].trim().toUpperCase());
				int rentAmount = Integer.parseInt(parts[4].trim());
				String phoneNum = parts[5].trim();
				tenantStatus tenantStat = tenantStatus.valueOf(parts[6].trim().toUpperCase());
			
				tenant tenantObj = new tenant(fName,lName,refName,property,rentAmount,phoneNum,tenantStat);
				
				tenants.add(tenantObj);
			
			}	
		}
		fileScanner.close();
		/* loops through the array list printing out all the tenant objects with their info
		for(tenant tenantObj: tenants) {
			System.out.println(tenantObj.toString());		
		}
		*/
		for(tenant tenantObj: tenants) {
			tenantInfo.append(tenantObj);
		}
		return tenantInfo.toString();
	}
	
	public void loadTenantsVOID() throws FileNotFoundException {
		
		File inputFile = new File("C:\\Users\\Daniel\\Desktop\\tenantInfo.txt");
		Scanner fileScanner = new Scanner(inputFile);
		
		while(fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String [] parts = line.split(",");
			
			if(parts.length >=7 ) {
				String fName = parts[0].trim();
				String lName = parts[1].trim();
				String refName = parts[2].trim().toUpperCase();
				properties property = properties.valueOf(parts[3].trim().toUpperCase());
				int rentAmount = Integer.parseInt(parts[4].trim());
				String phoneNum = parts[5].trim();
				tenantStatus tenantStat = tenantStatus.valueOf(parts[6].trim().toUpperCase());
			
				tenant tenantObj = new tenant(fName,lName,refName,property,rentAmount,phoneNum,tenantStat);
				
				this.tenants.add(tenantObj);
			
			}	
		}
		fileScanner.close();

	}
	
	// the tenant reference name is passed is and search through tenant array list and if found the tenant information is returned otherwise an error message is returned
	public String findTenant(String tenantRefName) {
		StringBuilder tenantDetails = new StringBuilder();
		boolean found = false;
		for(tenant tenant: tenants) {
			if(tenant.getRefTenantName().replaceAll("\\s+","").equals(tenantRefName.replaceAll("\\s+","").toUpperCase())){
				System.out.println(tenant.toString());
				found = true;
				tenantDetails.append(tenant);
				break;
			}
		}
		
		if(!found) {
			JOptionPane.showMessageDialog(null, "The Tenant With Reference Name '"+tenantRefName + "' Has Not Been Found", "Error" ,JOptionPane.ERROR_MESSAGE);
		}
		
		return tenantDetails.toString();
		
	}
	
	// adds a new tenant object to the tenantInfo.txt 
	public boolean addTenant(String fName, String lName, String refTenantName,properties property, int rentAmount, String phoneNumber, tenantStatus currentTenantStatus){
		//validate inputs
		boolean successAddition = false;
		
		if (fName == "" || lName == "" || refTenantName == ""|| property == null || phoneNumber == "" || currentTenantStatus == null) {
			JOptionPane.showMessageDialog(null, "All tenant details must be provided", "Error", JOptionPane.ERROR_MESSAGE);
			return successAddition;
        }
		
		refTenantName = refTenantName.trim().toUpperCase();
		//add tenant info the correct position on the tenantInfo.txt
		try {
			File inputFile = new File("C:\\Users\\Daniel\\Desktop\\tenantInfo.txt");
			Scanner fileScanner = new Scanner(inputFile);
			
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String [] parts = line.split(",");
				if (parts.length < 3) {
                    continue; // Skip lines that don't have enough data
                }
				String refName = parts[2].trim().toUpperCase();
				if(refName.equals(refTenantName)) {
					JOptionPane.showMessageDialog(null, fName +" "+ lName + "Is Already In Our Database", "Error", JOptionPane.ERROR_MESSAGE);
					return successAddition;
				}
			}
			fileScanner.close();
			
			//create tenant obj now as this is a unique tenant
			tenant addedTenant = new tenant(fName,lName, refTenantName, property, rentAmount, phoneNumber, currentTenantStatus);
			
			//append tenant to file
			FileWriter outputFile = new FileWriter("C:\\Users\\Daniel\\Desktop\\tenantInfo.txt",true);
			BufferedWriter bw = new BufferedWriter(outputFile);
			
			bw.write(addedTenant.toCSV());
			bw.newLine();
			
			bw.close();
			//add tenant to arrayList of tenant objs
			this.tenants.add(addedTenant);
			successAddition = true;
			
		}catch(IOException e) {
			System.out.println("tenantInfo.txt catch block");
			e.printStackTrace();
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please Enter Valid Tenant Details", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return successAddition;
	}
	
	//getter method
	public List<tenant> getTenants(){
		return this.tenants;
	}
}
