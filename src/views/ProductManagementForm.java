package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import connector.Connector;
import controllers.ProductHandler;
import controllers.VoucherController;
import models.Employee;
import models.Product;



public class ProductManagementForm {
	private Connector con = Connector.connect();
	private JFrame frame = new JFrame("Product Management");
	private Employee emp;
	private JLabel jlName, jlProductID, jlProductName, jlProductPrice, jlProductStock, jlProductDesc;
	private JTextField tfSearch, tfProductID, tfProductName, tfProductPrice, tfProductStock, tfProductDesc;
	private JButton btnSearch, btnInsert, btnUpdate, btnDelete, btnLogout ,btnVoucher;
	
	private JTable table;
	
	public ProductManagementForm(Employee emp) {
		this.emp = emp;
		init();
		setPanel();
		loadData();
	}
	
	private void loadData() {
		//Set Table Model
		String header[] = {
				"productID", "name",
				"description",
				"price",
				"stock"
		};
		DefaultTableModel modelTable;
		modelTable = new DefaultTableModel(header, 0);
		
		Vector<Product> products = (Vector<Product>) ProductHandler.getInstance().getAllproducts();
		
		for (Product product : products) {
			Vector<Object> row = new Vector<>();
			row.add(product.getProductID());
			row.add(product.getName());
			row.add(product.getDescription());
			row.add(product.getPrice());
			row.add(product.getStock());
			modelTable.addRow(row);
		}
		
		table.setModel(modelTable);
	}
	
	private void init() {
		frame.setSize(900, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	private void clearTextField() {
		tfSearch.setText("");
		tfProductID.setText("");
		tfProductName.setText("");
		tfProductDesc.setText("");
		tfProductPrice.setText("");
		tfProductStock.setText("");
	}
	
	
	private void setPanel() {
		// Content Panel
		JPanel content = new JPanel();
		content.setBorder(new LineBorder(Color.WHITE, 4));
		content.setLayout(new BorderLayout());
		
		// Title Panel
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(2, 2));
		jlName = new JLabel("Username: " + emp.getUsername());
		
		JPanel navbarPanel = new JPanel();
		btnLogout = new JButton("Log Out");
		btnVoucher = new JButton("Voucher");
		navbarPanel.setLayout(new GridLayout(1,2));
		navbarPanel.add(btnVoucher);
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
		table = new JTable();
		jspanel.setViewportView(table);
		content.add(jspanel, BorderLayout.CENTER);
		
		// Bottom Panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(13, 1));
		
		JPanel pan1 = new JPanel();
		bottomPanel.add(pan1);
		
		// ProductID
		jlProductID = new JLabel("Product ID [ Update | Delete]");
		tfProductID = new JTextField();
		bottomPanel.add(jlProductID);
		bottomPanel.add(tfProductID);
		
		// ProductName
		jlProductName = new JLabel("Product Name");
		tfProductName = new JTextField();
		bottomPanel.add(jlProductName);
		bottomPanel.add(tfProductName);
		
		//ProductDesc
		jlProductDesc= new JLabel("Product Description");
		tfProductDesc = new JTextField();
		bottomPanel.add(jlProductDesc);
		bottomPanel.add(tfProductDesc);
		
		// Product Price
		jlProductPrice= new JLabel("Product Price");
		tfProductPrice = new JTextField();
		bottomPanel.add(jlProductPrice);
		bottomPanel.add(tfProductPrice);
		
		// Product Stock
		jlProductStock= new JLabel("Product Stocks");
		tfProductStock = new JTextField();
		bottomPanel.add(jlProductStock);
		bottomPanel.add(tfProductStock);
		
		JPanel pan = new JPanel();
		bottomPanel.add(pan);
		
		// Button Insert Delete Update
		JPanel btn_panel = new JPanel();
		btn_panel.setLayout(new GridLayout(1, 3));
		btnInsert = new JButton("Insert Product");
		btnUpdate = new JButton("Update Product");
		btnDelete = new JButton("Delete Product");
		btn_panel.add(btnInsert);
		btn_panel.add(btnUpdate);
		btn_panel.add(btnDelete);
		
		bottomPanel.add(btn_panel);
		content.add(bottomPanel, BorderLayout.SOUTH);
		frame.setContentPane(content);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getData();
			}
			private void getData() {
			  int row = table.getSelectedRow();
			  int id = (int) table.getValueAt(row, 0);
			  String name = (String) table.getValueAt(row, 1);
			  String desc = (String) table.getValueAt(row, 2);
			  int price = (int) table.getValueAt(row, 3);
			  int stock = (int) table.getValueAt(row, 4);
			  
			  tfProductID.setText(id + "");
			  tfProductName.setText(name + "");
			  tfProductDesc.setText(desc + "");
			  tfProductPrice.setText(price + "");
			  tfProductStock.setText(stock + "");
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
							"productID", "name",
							"description",
							"price",
							"stock"
					};
					DefaultTableModel modelTable;
					modelTable = new DefaultTableModel(header, 0);

					String productID = tfSearch.getText();
					Product product = ProductHandler.getInstance().getProduct(productID);
					
					if(product!=null) {
						Vector<Object> row = new Vector<>();
						row.add(product.getProductID());
						row.add(product.getName());
						row.add(product.getDescription());
						row.add(product.getPrice());
						row.add(product.getStock());
						modelTable.addRow(row);
					}
					
					table.setModel(modelTable);
				}
			}
		});
		
		btnVoucher.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VoucherController.VoucherView();
			}
		});

		btnInsert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = tfProductName.getText();
				String description = tfProductDesc.getText();
				String price = tfProductPrice.getText();
				String stock = tfProductStock.getText();
				ProductHandler productHandler = controller.getInstance();
				
				boolean inserted = productHandler.insertProduct(name, description, price, stock);
				
				if(inserted) {
					clearTextField();
					JOptionPane.showMessageDialog(frame, "Success Insert New Product");
					loadData();
				}else {
					JOptionPane.showMessageDialog(frame, productHandler.getErrorMessage());
				}
				
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String productID = tfProductID.getText();
				String name = tfProductName.getText();
				String description = tfProductDesc.getText();
				String price = tfProductPrice.getText();
				String stock = tfProductStock.getText();
				ProductHandler productHandler = controller.getInstance();
				
				boolean updated = productHandler.updateProduct(productID,name, description, price, stock);
				
				if(updated) {
					clearTextField();
					JOptionPane.showMessageDialog(frame, "Success Update Product");
					loadData();
				}else {
					JOptionPane.showMessageDialog(frame, productHandler.getErrorMessage());
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String productID = tfProductID.getText();
				ProductHandler productHandler = controller.getInstance();
				
				boolean deleted = productHandler.deleteProduct(productID);
				
				if(deleted) {
					clearTextField();
					JOptionPane.showMessageDialog(frame, "Success Delete Product");
					loadData();
				}else {
					JOptionPane.showMessageDialog(frame, productHandler.getErrorMessage());
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
	
	
	
	
}
