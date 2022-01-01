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
	private JLabel jlName, jlProductID, jlProductName, jlProductPrice, jlProductStock, jlProductDesc, jlProductQuantity;
	private JTextField tfSearch, tfProductID, tfProductName, tfProductPrice, tfProductStock, tfProductDesc, tfProductQuantity;
	private JButton btnSearch, btnInsert, btnUpdate, btnDelete, btnLogout ,btnVoucher, btnAddToCart, btnCart;
	
	private JTable table;
	private DefaultTableModel modelTable;
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
		navbarPanel.setLayout(new GridLayout(1,2));
		
		if(this.emp.getPositionID() == 2) { //productAdmin
			btnVoucher = new JButton("Voucher");
			navbarPanel.add(btnVoucher);
		}else if(this.emp.getPositionID() == 1) { //barista
			btnCart = new JButton("MyCart");
			navbarPanel.add(btnCart);
		}
		
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
		
		
		
		if(this.emp.getPositionID() == 2) { //productAdmin
			
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
		}else if(this.emp.getPositionID() == 1) { //barista
			// Bottom Panel
			JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(new GridLayout(9, 1));
			
			JPanel pan1 = new JPanel();
			bottomPanel.add(pan1);
			
			//Information
			JLabel jlInfo = new JLabel("Product Information",JLabel.CENTER);
			bottomPanel.add(jlInfo);
			
			// ProductName
			jlProductName = new JLabel("Product Name");
			bottomPanel.add(jlProductName);
						
			//ProductDesc
			jlProductDesc= new JLabel("Product Description");
			bottomPanel.add(jlProductDesc);
						
			// Product Price
			jlProductPrice= new JLabel("Product Price");
			bottomPanel.add(jlProductPrice);
						
				
			// Product Quantity
			jlProductQuantity= new JLabel("Input Quantity");
			bottomPanel.add(jlProductQuantity);
					
			JPanel pan = new JPanel();
			bottomPanel.add(pan);
			
			// Button Add To Cart
			JPanel btn_panel = new JPanel();
			btn_panel.setLayout(new GridLayout(1, 1));
			btnAddToCart = new JButton("Add To Cart");
			btn_panel.add(btnAddToCart);
			
			bottomPanel.add(btn_panel);
			content.add(bottomPanel, BorderLayout.SOUTH);
		}
		
		
		
		frame.setContentPane(content);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getData();
			}
			private void getData() {
				int row = table.getSelectedRow();
				if(emp.getPositionID()==2) {//product Admin
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
					
				}else if(emp.getPositionID()==1) {//barista
					String name = (String) table.getValueAt(row, 1);
					String desc = (String) table.getValueAt(row, 2);
					int price = (int) table.getValueAt(row, 3);
					
					jlProductName.setText("Product Name : "+name);	
					jlProductDesc.setText("Product Description : "+desc);
					jlProductPrice.setText("Product Price : "+price);
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
							"productID", "name",
							"description",
							"price",
							"stock"
					};
					
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
		
		if(this.emp.getPositionID() == 2) {
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
		}else if(this.emp.getPositionID() == 2) {
			btnAddToCart.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
			btnCart.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
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
