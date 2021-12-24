package models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import connector.Connector;



public class ProductAdmin extends Employee{

	public ProductAdmin(int positionID, String positionName, int employeeID, String name, String status, int salary,
			String username, String password) {
		super(positionID, positionName, employeeID, name, status, salary, username, password);
	}

	public boolean insertProduct(Product product) {
		Connection con = Connector.connect();
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
	
	public boolean deleteProduct(int ProductID) {
		Connection con = Connector.connect();
		try {
			Statement stat = con.createStatement();
			String query = String.format("DELETE FROM Product WHERE productID = %d", ProductID);
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
		Connection con = Connector.connect();
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
	
	
	
}
