package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import connector.Connector;
import models.Product;
import models.ProductAdmin;


public class ProductController {

	
	public static ProductController controller = null;
	private String errorMessage;
	private ProductAdmin pa;
	
	public ProductController() {
		pa = new ProductAdmin(0,0,"","",0,"","");
		errorMessage = "";
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static ProductController getInstance() {
		if(controller == null) {
			controller = new ProductController();
		}
		return controller;
	}
	
	
	public void refreshData(DefaultTableModel model_table) {
		model_table.setRowCount(0);
		Connection con = Connector.connect();
		try {
			Statement stat = con.createStatement();
			String query = String.format("SELECT * FROM Product");
			
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				model_table.addRow(new Object[] {
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
	
	public String searching(String search) {
		return pa.searching(search);

	}
	
	public boolean insertProduct(Product product) {
		return pa.insertProduct(product);
	}

	public boolean updateProduct(Product product) {
		return pa.updateProduct(product);
	}
	
	public boolean deleteProduct(int productID) {
		return pa.deleteProduct(productID);
	}
	
	public boolean validate(String name, String description, int price, int stock, String priceStr, String stockStr) {
		
		if(name.isEmpty() || name.equals("")) {
			errorMessage = "Product name cannot be empty!";
			return false;
		}else if (description.isEmpty() || description.equals("")) {
			errorMessage = "Product description cannot be empty!";
			return false;
		}else if(priceStr.isEmpty()) {
			errorMessage = "Product price cannot be empty!";
			return false;
		}else if(price < 1){
			errorMessage = "Product price cannot be less than one!";
			return false;
		}else if(stockStr.isEmpty()) {
			errorMessage = "Product stock cannot be empty!";
			return false;
		}else if(stock < 1){
			errorMessage = "Product stock cannot be less than one!";
			return false;
		}else {
			return true;
		}
	}

}
