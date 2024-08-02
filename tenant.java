package rentTracker;

public class tenant {
	private String firstName;
	private String lastName;
	private String refTenantName;
	private properties property;
	private int rentAmount;
	private String phoneNumber;
	private tenantStatus currentTenantStatus;
	
	//constructor for tenant with first name,second name ,property , rent amount , phone number, current tenant status 
	public tenant(String firstName, String lastName, String refTenantName,properties property, int rentAmount,String phoneNumber, tenantStatus currentTenantStatus) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.refTenantName = refTenantName;
		this.property = property;
		this.rentAmount = rentAmount;
		this.phoneNumber = phoneNumber;
		this.currentTenantStatus = currentTenantStatus;
	}
	
	
	// getter methods for tenants
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getRefTenantName() {
		return this.refTenantName;
	}
	
	public properties getProperty() {
		return this.property;
	}
	
	public int getRentAmount() {
		return this.rentAmount;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public tenantStatus getTenantStatus() {
		return this.currentTenantStatus;
	}
	
	@Override
	public String toString() {
		return "TENANT INFO:  \n" +
				" First Name: " + this.firstName + ", \n"+
                " Last Name: " + this.lastName  + ", \n"+
                " Reference Name: " + this.refTenantName  + ", \n"+
                " Property: " + this.property +", \n"+
                " Rent Amount: " + this.rentAmount + ", \n"+
                " Phone Number: " + this.phoneNumber + ", \n"+
                " Tenant Status: " + this.currentTenantStatus  +"\n \n";
	}
	
	//for adding to the file
	public String toCSV() {
        return firstName + ", " + lastName + ", " + refTenantName + ", " + property + ", " + rentAmount + ", " + phoneNumber + ", " + currentTenantStatus;
    }
	
}
