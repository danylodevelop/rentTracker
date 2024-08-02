package rentTracker;
import java.io.FileNotFoundException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame{
	
	private bankStatementProcessor bankStatementProcessor;
    private tenantManager tenantManager;
	
	public MainGUI(bankStatementProcessor bankStatementProcessor,tenantManager tenantManager) {
		this.bankStatementProcessor = bankStatementProcessor;
        this.tenantManager = tenantManager;
		initComponenets(bankStatementProcessor,tenantManager);
	}
	
	private void initComponenets(bankStatementProcessor bankStatementProcessor,tenantManager tenantManager) {
		
		setTitle("Tenant Manager Interface");
		setSize(400,400);
		setVisible(true);
		
		JButton viewAllTenants = new JButton("View All Tenants");
		JButton viewAllBankStatements = new JButton("View All Bank Statements");
		JButton viewMoneyIn = new JButton("Filter Money In Bank Statements");
		JButton addTenant = new JButton("Add A New Tenant");
		JButton viewTenantInfo = new JButton("View Tenant Info");
		JButton viewTenantRentPayment = new JButton("View Tenant Rent Payments");
		
		
		JTextArea outputTextArea = new JTextArea(30,60);
		outputTextArea.setEditable(false);//make it non-editable
        outputTextArea.setMargin(new Insets(10, 10, 10, 10));
		JScrollPane scrollPane = new JScrollPane(outputTextArea);
		
		JPanel buttonPanel = new JPanel(new GridLayout(4,2));
		buttonPanel.add(viewAllTenants);
		buttonPanel.add(viewAllBankStatements);
		buttonPanel.add(viewMoneyIn);
		buttonPanel.add(addTenant);
		buttonPanel.add(viewTenantInfo);
		buttonPanel.add(viewTenantRentPayment);
		
		// Add the panels to the frame
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        pack(); // Adjust the window size based on the components
        setVisible(true);
        
        viewAllTenants.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		try {
					outputTextArea.setText(tenantManager.loadTenants());
				} catch (FileNotFoundException err) {
					err.printStackTrace();
				}
        	}
        });
        
        viewTenantInfo.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		//create input field for tenant ref name
        		JTextField refTenantField = new JTextField(15);
        		
        		JPanel inputPanel = new JPanel(new GridLayout(0,2));
        		inputPanel.add(new JLabel("Enter Tenant's Surname / Reference Name: "));
        		inputPanel.add(refTenantField);
        		
        		// if confirmed perform the findTenant function with the inputted ref name
        		int result = JOptionPane.showConfirmDialog(null, inputPanel, "Find Tenant", JOptionPane.OK_CANCEL_OPTION);
        		if(result == JOptionPane.OK_OPTION) {
        			try {
						tenantManager.loadTenants();
						outputTextArea.setText(tenantManager.findTenant(refTenantField.getText()));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        	}
        });
        
        viewAllBankStatements.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		try {
					outputTextArea.setText(bankStatementProcessor.loadBankStatements());
				} catch (FileNotFoundException err) {
					err.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        
        viewMoneyIn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		try {
        			bankStatementProcessor.loadBankStatements();
					outputTextArea.setText(bankStatementProcessor.filterMoneyIn());
				} catch (FileNotFoundException err) {
					err.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        
        viewTenantRentPayment.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		//create input field for tenant ref name
        		JTextField refTenantField = new JTextField(15);
        		
        		JPanel inputPanel = new JPanel(new GridLayout(0,2));
        		inputPanel.add(new JLabel("Enter Tenant's Surname / Reference Name: "));
        		inputPanel.add(refTenantField);
        		
        		// if confirmed perform the findTenant function with the inputted ref name
        		int result = JOptionPane.showConfirmDialog(null, inputPanel, "Find Tenant", JOptionPane.OK_CANCEL_OPTION);
        		if(result == JOptionPane.OK_OPTION) {
        			try {
						bankStatementProcessor.loadBankStatements();
						tenantManager.loadTenants();
						outputTextArea.setText(bankStatementProcessor.findPaymentsForTenant(refTenantField.getText()));
					} catch (Exception e1) {
						//Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        	}
        });
        
        addTenant.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		//create input fields for tenant info
        		JTextField fNameField = new JTextField(15);
        		JTextField lNameField = new JTextField(15);
        		JTextField refTenantField = new JTextField(15);
        		JComboBox<String> propertyComboBox = new JComboBox<>(new String[] {"BURNHAM", "WEMBLEY", "SLOUGH"});
        		JTextField rentAmountField = new JTextField(15);
        		JTextField phoneNumField = new JTextField(11);
        		JComboBox<String> tenantStatusComboBox = new JComboBox<>(new String[] {"CURRENTLY_STAYING", "MOVED_OUT"});
        		
        		JPanel inputPanel = new JPanel(new GridLayout(0,2));
        		inputPanel.add(new JLabel("Enter Tenant's First Name: "));
        		inputPanel.add(fNameField);
        		inputPanel.add(new JLabel("Enter Tenant's Last Name: "));
        		inputPanel.add(lNameField);
        		inputPanel.add(new JLabel("Enter Tenant's Reference Name (Usaully Surname): "));
        		inputPanel.add(refTenantField);
        		inputPanel.add(new JLabel("Select The Property Tenant Is Living In : "));
        		inputPanel.add(propertyComboBox);
        		inputPanel.add(new JLabel("Enter Tenant's Expected Monthly Rent: "));
        		inputPanel.add(rentAmountField);
        		inputPanel.add(new JLabel("Enter Tenant's Phone Number: "));
        		inputPanel.add(phoneNumField);
        		inputPanel.add(new JLabel("Select Tenant's Status: "));
        		inputPanel.add(tenantStatusComboBox);
        		
        		// if confirmed perform the findTenant function with the inputted ref name 
        		int result = JOptionPane.showConfirmDialog(null, inputPanel, "Add Tenant", JOptionPane.OK_CANCEL_OPTION);
        		if(result == JOptionPane.OK_OPTION) {
        			try {
        				String fName = fNameField.getText().trim();
        				String lName = lNameField.getText().trim();
        				String refName = refTenantField.getText();
        				properties property = properties.valueOf((String) propertyComboBox.getSelectedItem());
        				int rentAmount = Integer.parseInt(rentAmountField.getText());
        				String phoneNum = phoneNumField.getText();
        				tenantStatus selectTenantStatus = tenantStatus.valueOf((String) tenantStatusComboBox.getSelectedItem());
        				
        				if(tenantManager.addTenant(fName,lName,refName,property,rentAmount,phoneNum,selectTenantStatus)) {
        					outputTextArea.setText(fName +" "+ lName + " Has Been Successfully Added");
        				}
        				
        				
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        	}
        });
        
        
	}	
		
		/*//Testing for different functions 
		bankStatement b1 = new bankStatement("17/04/2024","Rent deez",1200,0);
		//System.out.println(b1.toString());
		
		
		tenant t1 = new tenant("John", "Doe", "J Doe",properties.BURNHAM,500,"12345678901", tenantStatus.CURRENTLY_STAYING);
		//System.out.println(t1.toString());
		
		tenantManager tm = new tenantManager();
		tm.addTenant("John", "Doe", "JD123", properties.BURNHAM, 1000, "123-456-7890", tenantStatus.CURRENTLY_STAYING);
		
        try {
            tm.loadTenants();
            tm.findTenant("mattey");
        } catch (FileNotFoundException e) {
            System.err.println("Tenant file not found: " + e.getMessage());
        } 
        
		
		bankStatementProcessor processor = new bankStatementProcessor();
		
		try {
			processor.loadBankStatements();
			//System.out.println(processor.filterMoneyIn());
			//System.out.println(processor.findPaymentsForTenant("PANESAR"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		*/
	
	public static void main(String[] args) {
		try {
	        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	            if ("Nimbus".equals(info.getName())) {
	                UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		
		tenantManager tm = new tenantManager();
		bankStatementProcessor bsp = new bankStatementProcessor();
		
		new MainGUI(bsp,tm);
	}
}
