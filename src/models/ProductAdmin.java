package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connector.Connector;



public class ProductAdmin extends Employee{

	private Connection con = Connector.connect();
	public ProductAdmin(int positionID, int employeeID, String name, String status, int salary,
			String username, String password) {
		super(positionID,  employeeID, name, status, salary, username, password);
	}

	public boolean insertProduct(Product product) {
		try {
			Statement stat = con.createStatement();
			String query = String.format("INSERT INTO Product(name, description, price, stock) VALUES('%s', '%s', %d, %d)", product.getProductName() ,product.getProductDescription(), product.getProductPrice(), product.getProductStock());
			if(stat.executeUpdate(query) == 0) {
				return false;
			}
			else {
				return true;
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteProduct(int productID) {
		try {
			Statement stat = con.createStatement();
			String query = String.format("DELETE FROM Product WHERE productID = %d", productID);
			if(stat.executeUpdate(query) == 0) {
				return false;
			}
			else {
				return true;
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public boolean updateProduct(Product product) {
		try {
			Statement stat = con.createStatement();
			String query = String.format("UPDATE Product SET name = '%s', description = '%s', price = %d, stock = %d WHERE productID = %d", product.getProductName(), product.getProductDescription(), product.getProductPrice(), product.getProductStock(), product.getProductID());
			if(stat.executeUpdate(query) == 0) {
				return false;
			}
			else {
				return true;
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	
	public ResultSet getAllProducts() {
		Statement stat;
		ResultSet res = null;
		try {
			stat = con.createStatement();
			String query = String.format("SELECT * FROM product");
			res = stat.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public String searching(String search) {
		String query = String.format("SELECT * FROM Product WHERE ProductID = %d", Integer.valueOf(search));
		return query;
	}
	
	
	
}
