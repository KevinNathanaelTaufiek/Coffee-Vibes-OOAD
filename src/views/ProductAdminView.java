package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import controllers.LoginController;
import controllers.ProductController;
import controllers.VoucherController;
import models.Product;
import models.ProductAdmin;



public class ProductAdminView {
	private Connection con = Connector.connect();
	private JFrame frame = new JFrame("Product Management");
	private ProductAdmin pa;
	private JLabel jlName, jlProductID, jlProductName, jlProductPrice, jlProductStock, jlProductDesc;
	private JTextField tfSearch, tfProductID, tfProductName, tfProductPrice, tfProductStock, tfProductDesc;
	private JButton btnSearch, btnInsert, btnUpdate, btnDelete, btnLogout ,btnVoucher;
	
	private JTable table;
	private DefaultTableModel modelTable;
	
	public ProductAdminView(ProductAdmin pa) {
		this.pa = pa;
		framePlay();
		setPanel();
		
		//Set Table Model
		modelTable = new DefaultTableModel(new String[] {
				"productID",
				"name",
				"description",
				"price",
				"stock"
			}, 0);
			table.setModel(modelTable);
				
		//Refresh
		modelTable.setRowCount(0);
		try {
			Statement stat = con.createStatement();
			String query = String.format("SELECT * FROM product");
			
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				modelTable.addRow(new Object[] {
						res.getInt("productID"),
						res.getString("name"),
						res.getString("description"),
						res.getInt("price"),
						res.getInt("stock")
				});
			}
			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void framePlay() {
		frame.setSize(900, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	private void setTableModel() {
		modelTable = new DefaultTableModel(new String[] {
			"productId",
			"name",
			"description",
			"price",
			"stock"
		}, 0);
		table.setModel(modelTable);
	}
	
	
	private void setPanel() {
		// Content Panel
		JPanel content = new JPanel();
		content.setBorder(new LineBorder(Color.WHITE, 4));
		content.setLayout(new BorderLayout());
		
		// Title Panel
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(2, 2));
		jlName = new JLabel("Username: " + pa.getUsername());
		
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
		
		ProductController controller = new ProductController();
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfSearch.getText().equals("")) {
					setTableModel();
					controller.getInstance().refreshData(modelTable);
					tfSearch.setText("");
					tfProductID.setText("");
					tfProductName.setText("");
					tfProductDesc.setText("");
					tfProductPrice.setText("");
					tfProductStock.setText("");
				}else {
					try {
						modelTable.setRowCount(0);
						Statement stat = con.createStatement();
						String query = controller.searching(tfSearch.getText());
						ResultSet res = stat.executeQuery(query);
						
						while(res.next()) {
							modelTable.addRow(new Object[] {
									res.getInt("productID"),
									res.getString("name"),
									res.getString("description"),
									res.getInt("price"),
									res.getInt("stock")
							});
							tfProductID.setText(String.valueOf(res.getInt("productID")));
							tfProductName.setText(res.getString("name"));
							tfProductDesc.setText(res.getString("description"));
							tfProductPrice.setText(String.valueOf(res.getInt("price")));
							tfProductStock.setText(String.valueOf(res.getInt("stock")));
						}				
					}catch(SQLException e1) {
						e1.printStackTrace();
					}
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
				int price = 0;
				int stock = 0;
				String priceStr = tfProductPrice.getText();
				String stockStr = tfProductStock.getText();
				int flag =0;
				
				if(!priceStr.isEmpty()) {
					try {
						price = Integer.valueOf(tfProductPrice.getText());
					} catch (Exception e1) {
						flag = 1;
						JOptionPane.showMessageDialog(frame, "Product price must be numeric!", "Product Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}else if(!stockStr.isEmpty()) {
					try {
						 stock = Integer.valueOf(tfProductStock.getText());
					} catch (Exception e1) {
						flag = 1;
						JOptionPane.showMessageDialog(frame, "Product stock must be numeric!", "Product Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			
				setTableModel();
				boolean validate = controller.getInstance().validate(name,description,price,stock, priceStr, stockStr);
				JOptionPane.showMessageDialog(frame, ProductController.getInstance().getErrorMessage(), "Product Message", JOptionPane.INFORMATION_MESSAGE);
				if(validate && flag != 1) {
					boolean insert = controller.insertProduct(new Product(0,name, description, price, stock));
					if(insert) {
						tfSearch.setText("");
						tfProductID.setText("");
						tfProductName.setText("");
						tfProductDesc.setText("");
						tfProductPrice.setText("");
						tfProductStock.setText("");
						controller.refreshData(modelTable);
						JOptionPane.showMessageDialog(frame, "Success Insert New Product");
						
					}else {
						JOptionPane.showMessageDialog(frame, "Failed Insert New Product");
					}
				}
				
				controller.refreshData(modelTable);
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = tfProductName.getText();
				String description = tfProductDesc.getText();
				int price = Integer.valueOf(tfProductPrice.getText());
				int stock = Integer.valueOf(tfProductStock.getText());
				int productID = Integer.valueOf(tfProductID.getText());
				String priceStr = tfProductPrice.getText();
				String stockStr = tfProductStock.getText();
				int flag =0;
				
				if(!tfProductID.getText().isEmpty()) {
					try {
						productID = Integer.valueOf(tfProductID.getText());
					} catch (Exception e1) {
						flag = 1;
						JOptionPane.showMessageDialog(frame, "Product ID must be numeric!", "Product Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}else if(!priceStr.isEmpty()) {
					try {
						price = Integer.valueOf(tfProductPrice.getText());
					} catch (Exception e1) {
						flag = 1;
						JOptionPane.showMessageDialog(frame, "Product price must be numeric!", "Product Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}else if(!stockStr.isEmpty()) {
					try {
						 stock = Integer.valueOf(tfProductStock.getText());
					} catch (Exception e1) {
						flag = 1;
						JOptionPane.showMessageDialog(frame, "Product stock must be numeric!", "Product Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
				setTableModel();
//				boolean update = controller.updateProduct(new Product(productID, name, description, price, stock));
//				if(update) {
//					controller.refreshData(modelTable);
//					tfSearch.setText("");
//					tfProductID.setText("");
//					tfProductName.setText("");
//					tfProductDesc.setText("");
//					tfProductPrice.setText("");
//					tfProductStock.setText("");
//					JOptionPane.showMessageDialog(frame, "Success Update Product");
//					
//				}else {
//					JOptionPane.showMessageDialog(frame, "Failed Update Product");
//
//				}
				boolean validate = controller.getInstance().validate(name,description,price,stock, priceStr, stockStr);
				if(validate && flag != 1) {
					boolean update = controller.updateProduct(new Product(productID, name, description, price, stock));
					if(update) {
						controller.refreshData(modelTable);
						
						tfSearch.setText("");
						tfProductID.setText("");
						tfProductName.setText("");
						tfProductDesc.setText("");
						tfProductPrice.setText("");
						tfProductStock.setText("");
						JOptionPane.showMessageDialog(frame, "Success Update Product");
						
					}else {
						JOptionPane.showMessageDialog(frame, "Failed Update Product");

					}
				}
				controller.refreshData(modelTable);
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setTableModel();
				int productID = Integer.valueOf(tfProductID.getText());
				
				boolean delete = controller.deleteProduct(productID);
				if(delete) {
					controller.refreshData(modelTable);
					tfSearch.setText("");
					tfProductID.setText("");
					tfProductName.setText("");
					tfProductDesc.setText("");
					tfProductPrice.setText("");
					tfProductStock.setText("");
					JOptionPane.showMessageDialog(frame, "Success Delete Product");
				}else {
					JOptionPane.showMessageDialog(frame, "Failed Delete Product");
				}
				controller.refreshData(modelTable);
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
