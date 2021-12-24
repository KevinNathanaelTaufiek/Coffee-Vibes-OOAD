package controllers;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connector.Connector;
import models.Product;
import models.ProductAdmin;
import views.LoginView;


public class ProductController {

	private JFrame frame = new JFrame("Product Management");
	private JTextField TF_search, TF_ProductID, TF_ProductName, TF_ProductPrice, TF_ProductStock, TF_ProductDesc;
	private JButton btn_search, btn_insert, btn_update, btn_delete, btn_logout;
	private JTable table;
	private DefaultTableModel model_table;
	private ProductAdmin pa;
	public ProductController(JFrame frame, JTextField tF_search, JTextField tF_ProductID,
			JTextField tF_ProductName, JTextField tF_ProductPrice, JTextField tF_ProductStock,
			JTextField tF_ProductDesc, JButton btn_search, JButton btn_insert, JButton btn_update, JButton btn_delete,
			JTable table, DefaultTableModel model_table, ProductAdmin pa, JButton btn_logout) {
		super();
		this.frame = frame;
		TF_search = tF_search;
		TF_ProductID = tF_ProductID;
		TF_ProductName = tF_ProductName;
		TF_ProductPrice = tF_ProductPrice;
		TF_ProductStock = tF_ProductStock;
		TF_ProductDesc = tF_ProductDesc;
		this.btn_search = btn_search;
		this.btn_insert = btn_insert;
		this.btn_update = btn_update;
		this.btn_delete = btn_delete;
		this.table = table;
		this.model_table = model_table;
		this.pa = pa;
		this.btn_logout = btn_logout;
	}
	
	private void setTableModel() {
		model_table = new DefaultTableModel(new String[] {
			"ID",
			"Product Name",
			"Product Description",
			"Product Price",
			"Product Stocks"
		}, 0);
		table.setModel(model_table);
	}
	
	private void refreshData() {
		model_table.setRowCount(0);
		Connection con = Connector.connect();
		try {
			Statement stat = con.createStatement();
			String query = String.format("SELECT * FROM Product");
			
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				model_table.addRow(new Object[] {
						res.getInt("ProductID"),
						res.getString("ProductName"),
						res.getString("ProductDescription"),
						res.getInt("ProductPrice"),
						res.getInt("ProductStock")
				});
			}
			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public void btnSearch(ActionEvent e) {
		if(e.getSource() == btn_search) {
			Connection con = Connector.connect();
			setTableModel();
			
			if(TF_search.getText().equals("")) {
				refreshData();
				TF_search.setText("");
				TF_ProductID.setText("");
				TF_ProductName.setText("");
				TF_ProductDesc.setText("");
				TF_ProductPrice.setText("");
				TF_ProductStock.setText("");
			}else {
				try {
					
					model_table.setRowCount(0);
					Statement stat = con.createStatement();
					String query = String.format("SELECT * FROM Product WHERE ProductID = %d", Integer.valueOf(TF_search.getText()));
					
					ResultSet res = stat.executeQuery(query);
					while(res.next()) {
						model_table.addRow(new Object[] {
								res.getInt("ProductID"),
								res.getString("ProductName"),
								res.getString("ProductDescription"),
								res.getInt("ProductPrice"),
								res.getInt("ProductStock")
						});
						TF_ProductID.setText(String.valueOf(res.getInt("ProductID")));
						TF_ProductName.setText(res.getString("ProductName"));
						TF_ProductDesc.setText(res.getString("ProductDescription"));
						TF_ProductPrice.setText(String.valueOf(res.getInt("ProductPrice")));
						TF_ProductStock.setText(String.valueOf(res.getInt("ProductStock")));
					}				
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		}
	}
	
	public void btnInsert(ActionEvent e) {
		if(e.getSource() == btn_insert) {
			String Name = TF_ProductName.getText(), Desc = TF_ProductDesc.getText();
			int  Price = Integer.valueOf(TF_ProductPrice.getText()), Stock = Integer.valueOf(TF_ProductStock.getText());
			
			setTableModel();
			
			if(pa.insertProduct(new Product(0, Name,Desc, Price, Stock))) {
				TF_search.setText("");
				TF_ProductID.setText("");
				TF_ProductName.setText("");
				TF_ProductDesc.setText("");
				TF_ProductPrice.setText("");
				TF_ProductStock.setText("");
				refreshData();
				
				JOptionPane.showMessageDialog(frame, "Success Insert New Product");
				
			}else {
				JOptionPane.showMessageDialog(frame, "Failed Insert New Product");
			}
			
			
		}
	}

	public void btnUpdate(ActionEvent e) {
		if(e.getSource() == btn_update) {
			String Name = TF_ProductName.getText(), Desc = TF_ProductDesc.getText();
			int  Price = Integer.valueOf(TF_ProductPrice.getText()), Stock = Integer.valueOf(TF_ProductStock.getText());
			int ProductID = Integer.valueOf(TF_ProductID.getText());
			
			setTableModel();
			if(pa.updateProduct(new Product(ProductID, Name, Desc, Price, Stock))) {
				refreshData();
				
				TF_search.setText("");
				TF_ProductID.setText("");
				TF_ProductName.setText("");
				TF_ProductDesc.setText("");
				TF_ProductPrice.setText("");
				TF_ProductStock.setText("");
				JOptionPane.showMessageDialog(frame, "Success Update Product");
				
			}else {
				JOptionPane.showMessageDialog(frame, "Failed Update Product");

			}
			
		}
	}
	
	public void btnDelete(ActionEvent e) {
		if(e.getSource() == btn_delete) {
			setTableModel();
			int ProductID = Integer.valueOf(TF_ProductID.getText());
			
			
			if(pa.deleteProduct(ProductID)) {
				refreshData();
				TF_search.setText("");
				TF_ProductID.setText("");
				TF_ProductName.setText("");
				TF_ProductDesc.setText("");
				TF_ProductPrice.setText("");
				TF_ProductStock.setText("");
				JOptionPane.showMessageDialog(frame, "Success Delete Product");
			}else {
				JOptionPane.showMessageDialog(frame, "Failed Delete Product");
			}
		}
	}
	
	public void btnLogout(ActionEvent e) {
		if(e.getSource() == btn_logout) {
			new LoginView();
			frame.setVisible(false);
		}
	}


}
