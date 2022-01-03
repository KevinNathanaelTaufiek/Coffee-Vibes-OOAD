package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controllers.CartHandler;
import controllers.ProductHandler;
import controllers.TransactionHandler;
import controllers.VoucherController;
import models.CartItem;
import models.Employee;
import models.Product;
import models.Transaction;
import models.TransactionItem;
import models.Voucher;

public class TransactionManagementForm extends JFrame implements ActionListener{

	private JFrame frame = new JFrame("Transaction Management");
	private JPanel contentPanel, buttonPanel;
	private JButton checkoutButton, useVoucherButton, btnLogout, btnEmployee, btnSearch, btnInsert;
	private JLabel titleLabel, voucherLabel, totalPriceLabel, jlName, jlTransactionID, jlProductID, jlQuantity;
	private JTextField voucherField, tfSearch, tfTransactionID, tfProductID, tfQuantity;
	private int totalPrice = 0;
	private Employee emp;
	private JTable table;
	private DefaultTableModel modelTable;
	
	public TransactionManagementForm(Employee emp) {
		this.emp = emp;
		
		if(this.emp.getPositionID()==1) {//barista
			contentPanel = new JPanel(new GridLayout(4,1));
			buttonPanel = new JPanel(new GridLayout(1,2));
			
			
			titleLabel = new JLabel("Checkout Form");
			voucherLabel = new JLabel("Voucher ID [Optional] : ");
			totalPriceLabel = new JLabel("Total Price : " + updateTotalPrice(""));
			
			voucherField = new JTextField();
			
			checkoutButton = new JButton("CheckOut");
			useVoucherButton = new JButton("Use Voucher");
			
			checkoutButton.addActionListener(this);
			useVoucherButton.addActionListener(this);
			
			contentPanel.add(titleLabel);
			contentPanel.add(voucherLabel);
			contentPanel.add(voucherField);
			contentPanel.add(totalPriceLabel);
			
			buttonPanel.add(checkoutButton);
			buttonPanel.add(useVoucherButton);
			
			add(contentPanel, BorderLayout.CENTER);
			add(buttonPanel, BorderLayout.SOUTH);
			
			setTitle("Staff");
			setSize(500,300);
			setResizable(false);
			setLocationRelativeTo(null);
			setVisible(true);
		}else if(this.emp.getPositionID()==3) { //Manager
			init();
			setPanel();
			loadData();
		}
		
	}
	
	private void init() {
		frame.setSize(900, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	
	private void setPanel() {
		JPanel content = new JPanel();
		content.setBorder(new LineBorder(Color.WHITE, 4));
		content.setLayout(new BorderLayout());
		
		// Title Panel
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(2, 2));
		jlName = new JLabel("Username: " + emp.getUsername());
		
		JPanel navbarPanel = new JPanel();
		navbarPanel.setLayout(new GridLayout(1,2));
		
		btnEmployee = new JButton("List Employee");
		navbarPanel.add(btnEmployee);
		btnLogout = new JButton("Log Out");
		navbarPanel.add(btnLogout);
		
		
		tfSearch = new JTextField();
		btnSearch = new JButton("Search By ID");
		titlePanel.add(jlName);
		titlePanel.add(navbarPanel);
		titlePanel.add(tfSearch);
		titlePanel.add(btnSearch);
		content.add(titlePanel, BorderLayout.NORTH);
		
		// Scroll Panel ( Tabel )
		JScrollPane jspanel = new JScrollPane();
//		table = new JTable();
		table=new JTable(modelTable){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
		};
		
		jspanel.setViewportView(table);
		content.add(jspanel, BorderLayout.CENTER);
		
		// Bottom Panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(9, 1));
		
		JPanel pan1 = new JPanel();
		bottomPanel.add(pan1);
		
		//Information
		JLabel jlInfo = new JLabel("Transaction Detail",JLabel.CENTER);
		bottomPanel.add(jlInfo);
		
		// Transaction ID
		jlTransactionID = new JLabel("Transaction ID");
		bottomPanel.add(jlTransactionID);
					
		//ProductID
		jlProductID= new JLabel("Product ID");
		bottomPanel.add(jlProductID);
					
		// Quantity
		jlQuantity= new JLabel("Quantity");
		bottomPanel.add(jlQuantity);
		
		JPanel pan = new JPanel();
		bottomPanel.add(pan);
		

		content.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.setContentPane(content);
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getData();
			}
			private void getData() {
				int row = table.getSelectedRow();
				int transactionID = (int) table.getValueAt(row, 0);
				
				List<TransactionItem> listTI = TransactionHandler.getInstance().getAllTransactionDetail(transactionID);
				
				jlTransactionID.setText("Transaction ID : "+transactionID);	
				
				for (TransactionItem transactionItem : listTI) {
					int productID = transactionItem.getProductID();
					int quantity = transactionItem.getQuantity();
					jlProductID.setText("Product ID : "+ productID);
					jlQuantity.setText("Product quantity : "+quantity);
				}	
  
			}
		});
		
		ProductHandler controller = ProductHandler.getInstance();
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfSearch.getText().equals("")) {
					loadData();
				}else {
					//Search
					String header[] = {
							"transactionID", 
							"purchaseDate",
							"voucherID",
							"employeeID",
							"totalPrice"
					};
					
					modelTable = new DefaultTableModel(header, 0);

					String transactionID = tfSearch.getText();
					Transaction transaction = TransactionHandler.getInstance().getTransaction(transactionID);
					
					if(transaction!=null) {
						Vector<Object> row = new Vector<>();
						row.add(transaction.getTransactionID());
						row.add(transaction.getPurchaseDate());
						row.add(transaction.getVoucherID());
						row.add(transaction.getEmployeeID());
						row.add(transaction.getTotalPrice());
						modelTable.addRow(row);
					}
					
					table.setModel(modelTable);
				}
			}
		});
		
		btnEmployee.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnEmployee) {
					frame.setVisible(false);
					new HRView(emp);
				}
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnLogout) {
					new LoginView();
					frame.setVisible(false);
				}
			}
		});
	}
	
	private void loadData() {
		//Set Table Model
		String header[] = {
				"transactionID", 
				"purchaseDate",
				"voucherID",
				"employeeID",
				"totalPrice"
		};
		DefaultTableModel modelTable;
		modelTable = new DefaultTableModel(header, 0);
		
		Vector<Transaction> transactions = (Vector<Transaction>) TransactionHandler.getInstance().getAllTransactions();
		
		for (Transaction transaction: transactions) {
			Vector<Object> row = new Vector<>();
			row.add(transaction.getTransactionID());
			row.add(transaction.getPurchaseDate());
			row.add(transaction.getVoucherID());
			row.add(transaction.getEmployeeID());
			row.add(transaction.getTotalPrice());
			modelTable.addRow(row);
		}
		
		table.setModel(modelTable);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent a) {
		
		String voucherID = voucherField.getText().toString();
		
		if(a.getSource() == checkoutButton) {
			
			int confrim = confirmationDialog("Check Out");
			if(confrim == 1) {
				if(voucherID.isEmpty()) voucherID = "0";
				Transaction transaction = TransactionHandler.getInstance().insertTransaction(voucherID, String.valueOf(emp.getEmployeeID()), totalPrice);
				JOptionPane.showMessageDialog(this, TransactionHandler.getInstance().getErrorMessage());
				if (transaction != null) {
					this.dispose();
				}
				else {
					voucherField.setText("");
				}
			}else {
				JOptionPane.showMessageDialog(this, "Check Out Canceled!");
				this.dispose();
			}
		}
		else if (a.getSource() == useVoucherButton) {
			totalPriceLabel.setText("Total Price : " + updateTotalPrice(voucherID));
		}
		
	}
	
	private int updateTotalPrice(String voucherID){
		int totalPrice = 0;
		List<CartItem> cart = CartHandler.getInstance().getCart();
		
		for (CartItem cartItem : cart) {
			totalPrice += (cartItem.getQuantity() * cartItem.getProduct().getPrice());
		}
		
		if(!voucherID.equals("")) {
			
			try {
				int voucherIDint = Integer.parseInt(voucherID);
				Voucher voucher = VoucherController.getInstance().getVoucher(voucherIDint);
				totalPrice = totalPrice - (int) (totalPrice * (voucher.getDiscount() / 100.0));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Failed Use Voucher");
				voucherField.setText("");
			}
		}
		
		this.totalPrice = totalPrice;
		return totalPrice;
	}
	
	
	public int confirmationDialog(String dialog) {
		JFrame frame = new JFrame("Confirmation Dialog");
	    JPanel panel = new JPanel();
	    LayoutManager layout = new FlowLayout();  
	    panel.setLayout(layout);       

	    
	    int result = JOptionPane.showConfirmDialog(frame,"Are you sure you want "+dialog+" this product?", "Confirmation Dialog",
	               JOptionPane.YES_NO_OPTION,
	               JOptionPane.QUESTION_MESSAGE);
	    if(result == JOptionPane.YES_OPTION){
	    	return 1;
	    }else if (result == JOptionPane.NO_OPTION){
	    	return 0;
	    }

	    return 0;
	}

}
