package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import connector.Connector;

public class TransactionItem {

	private int transactionID;
	private int productID;
	private int quantity;
	private Connector con = Connector.connect();
	private String tableDetail = "transactionItem";
	
	public TransactionItem() {
	}
	
	
	public TransactionItem(int transactionID, int productID, int quantity) {
		this.transactionID = transactionID;
		this.productID = productID;
		this.quantity = quantity;
	}
	
	private TransactionItem map(ResultSet rs) {
		try {
			int transactionID = rs.getInt("transactionID");
			int productID = rs.getInt("productID");
			int quantity = rs.getInt("quantity");
			return new TransactionItem(transactionID, productID, quantity);
		} catch (SQLException e) {
		}
		return null;
	}
	
	public List<TransactionItem> getAllDetailItem(int id){
		String query = String.format("SELECT * FROM " + this.tableDetail + " WHERE transactionID = " + id);
		ResultSet rs = con.executeQuery(query);
		
		try {
			Vector<TransactionItem> transactionItems = new Vector<>();
			while(rs.next()) {
				TransactionItem transactionItem = map(rs);
				transactionItems.add(transactionItem);
			}
			return transactionItems;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	
}
