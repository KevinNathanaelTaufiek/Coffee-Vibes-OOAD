package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import connector.Connector;

public class Product {

	private int productID;
	private String name;
	private String description;
	private int price;
	private int stock;
	
	private Connector con = Connector.connect();
	private String table = "product";
	
	private Product map(ResultSet rs) {
		try {
			int id = rs.getInt("productID");
			String name = rs.getString("name");
			String description = rs.getString("description");
			int price = rs.getInt("price");
			int stock = rs.getInt("stock");
			return new Product(id, name, description, price, stock);
		} catch (SQLException e) {
		}
		return null;
	}
	
	public List<Product> getAllProducts() {
		String query = String.format("SELECT * FROM " + this.table);
		
		ResultSet rs = con.executeQuery(query);
		
		try {
			Vector<Product> products = new Vector<>();
			while(rs.next()) {
				Product product = map(rs);
				products.add(product);
			}
			return products;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Product getProduct(int productID) {
		String query = "SELECT * FROM " + this.table 
				+ " WHERE productID = " + productID 
				+ " LIMIT 1";
		ResultSet rs = con.executeQuery(query);
		try {
			rs.next();
			return map(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean insertProduct() {
		String query = String.format("INSERT INTO %s (name, description, price, stock) VALUES (?,?,?,?)", this.table);
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setInt(3, price);
			ps.setInt(4, stock);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteProduct(int productID) {
		String query = String.format("DELETE FROM %s WHERE productID = ?", this.table);
		
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setInt(1, productID);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean updateProduct() {
		String query = String.format("UPDATE %s SET name = ?, description = ?, price = ?, stock = ? WHERE productID = ?",this.table);

		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setInt(3, price);
			ps.setInt(4, stock);
			ps.setInt(5, productID);
			
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}

	
	public Product(int productID, String name, String description, int price, int stock) {
		this.productID = productID;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}
	
	public Product() {};

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	
}
