package rentTracker;
import java.io.FileNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class MainGUI extends JFrame{
	
	private bankStatementProcessor bankStatementProcessor;
    private tenantManager tenantManager;
    private String tenantData;
	
	public MainGUI(bankStatementProcessor bankStatementProcessor,tenantManager tenantManager) {
		this.bankStatementProcessor = bankStatementProcessor;
        this.tenantManager = tenantManager;
		initComponenets(bankStatementProcessor,tenantManager);
		
		initialise();
	}
	
	public void initialise() {
        try {
            bankStatementProcessor.loadBankStatements();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private void initComponenets(bankStatementProcessor bankStatementProcessor,tenantManager tenantManager) {
		
		setTitle("Tenant Manager Interface");
		setSize(400,400);
		setVisible(true);
		
		JButton viewAllTenants = new JButton("View All Tenants");
		JButton viewSelectMonthExpense = new JButton("View Expenses For Selected Month"); //was filter money in 
		JButton addTenant = new JButton("Add A New Tenant");
		JButton viewTenantInfo = new JButton("View Tenant Info");
		JButton viewTenantRentPayment = new JButton("View Tenant Rent Payments");
		JButton viewSelectMonthPayments = new JButton("View A Selected Month Rent Payments");
		
		JTextArea outputTextArea = new JTextArea(30,60);
		outputTextArea.setEditable(false);//make it non-editable
        outputTextArea.setMargin(new Insets(10, 10, 10, 10));
		JScrollPane scrollPane = new JScrollPane(outputTextArea);
		
		JPanel buttonPanel = new JPanel(new GridLayout(4,2));
		buttonPanel.add(viewAllTenants);
		buttonPanel.add(viewSelectMonthExpense);
		buttonPanel.add(addTenant);
		buttonPanel.add(viewTenantInfo);
		buttonPanel.add(viewTenantRentPayment);
		buttonPanel.add(viewSelectMonthPayments);
		
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
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        
        viewTenantInfo.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		//create input field for tenant ref name
        		JComboBox<String> refTenantBox = new JComboBox<> (tenantRefNameList().toArray(new String[0]));
        		
        		JPanel inputPanel = new JPanel(new GridLayout(0,2));
        		inputPanel.add(new JLabel("Select Tenant's Surname / Reference Name: "));
        		inputPanel.add(refTenantBox);
        		
        		// if confirmed perform the findTenant function with the inputted ref name
        		int result = JOptionPane.showConfirmDialog(null, inputPanel, "Find Tenant", JOptionPane.OK_CANCEL_OPTION);
        		if(result == JOptionPane.OK_OPTION) {
        			try {
        				String selectTenant = refTenantBox.getSelectedItem().toString();
						tenantManager.loadTenantsVOID();
						outputTextArea.setText(tenantManager.findTenant(selectTenant));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        	}
        });
       
        viewSelectMonthExpense.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		MaskFormatter dateFormatter;
				try {
					dateFormatter = new MaskFormatter("##/####");
		            dateFormatter.setPlaceholderCharacter('_');
		            JFormattedTextField txtDate = new JFormattedTextField(dateFormatter);
					
					JPanel inputPanel = new JPanel(new GridLayout(0,2));
					inputPanel.add(new JLabel("Enter The Date For the Month (MM/yyyy): "));
					inputPanel.add(txtDate);
		    		
					int result = JOptionPane.showConfirmDialog(null, inputPanel, "Find Expenses For Selected Month", JOptionPane.OK_CANCEL_OPTION);
		    		if(result == JOptionPane.OK_OPTION) {
	    				String dateInput = txtDate.getText();
	    				outputTextArea.setText(bankStatementProcessor.findSelectedMonthExpense(dateInput));
		    			
		    		}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        
        viewTenantRentPayment.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		//create input field for tenant ref name
        		//JTextField refTenantField = new JTextField(15);
        		JComboBox<String> refTenantBox = new JComboBox<> (tenantRefNameList().toArray(new String[0]));
        		
        		JPanel inputPanel = new JPanel(new GridLayout(0,2));
        		inputPanel.add(new JLabel("Select Tenant's Surname / Reference Name: "));
        		//inputPanel.add(refTenantField);
        		inputPanel.add(refTenantBox);
        		
        		// if confirmed perform the findTenant function with the inputted ref name
        		int result = JOptionPane.showConfirmDialog(null, inputPanel, "Find Tenant", JOptionPane.OK_CANCEL_OPTION);
        		if(result == JOptionPane.OK_OPTION) {
        			try {
        				String selectTenant = refTenantBox.getSelectedItem().toString();
						outputTextArea.setText(bankStatementProcessor.findPaymentsForTenant(selectTenant));
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
        				else {
        					JOptionPane.showMessageDialog(null, "Invalid Details Please Try Again", "Error", JOptionPane.ERROR_MESSAGE);
        				}
        				
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
        	}
        });
        
        viewSelectMonthPayments.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
 
    			
        		MaskFormatter dateFormatter;
				try {
					dateFormatter = new MaskFormatter("##/####");
		            dateFormatter.setPlaceholderCharacter('_');
		            JFormattedTextField txtDate = new JFormattedTextField(dateFormatter);
					
					JPanel inputPanel = new JPanel(new GridLayout(0,2));
					inputPanel.add(new JLabel("Enter The Date For the Month (MM/yyyy): "));
					inputPanel.add(txtDate);
		    		
					int result = JOptionPane.showConfirmDialog(null, inputPanel, "Find Rent Payments For Selected Month", JOptionPane.OK_CANCEL_OPTION);
		    		if(result == JOptionPane.OK_OPTION) {
		    			try {
		    				String dateInput = txtDate.getText();
		    				outputTextArea.setText(bankStatementProcessor.findSelectedMonthRent(dateInput));
		    				
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    		}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        
        
	}
	
	private List<String> tenantRefNameList(){
		ArrayList<String> tenantRefName = new ArrayList<>();
		
		try {
			tenantManager.loadTenantsVOID();
			for(tenant tenant: tenantManager.getTenants()) {
				tenantRefName.add(tenant.getRefTenantName().trim().toUpperCase());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tenantRefName;
	}
	
	
		
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
