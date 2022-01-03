package models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import connector.Connector;

public class Transaction {

	private int transactionID;
	private Date purchaseDate;
	private int voucherID;
	private int employeeID;
	private int totalPrice;
	private List<TransactionItem> listTransactionItem;
	
	private Connector con = Connector.connect();
	private String tableHeader = "transaction";
	private String tableDetail = "transactionItem";
	
//	private Transaction map(ResultSet rs) {
//		try {
//			int id = rs.getInt("transactionID");
//			Date purchaseDate = rs.getDate("purchaseDate");
//			int voucherID = rs.getInt("voucherID");
//			int employeeID = rs.getInt("employeeID");
//			int totalPrice = rs.getInt("totalPrice");
//			return new Transaction(id, purchaseDate, voucherID, employeeID, totalPrice, listTransactionItem);
//		} catch (SQLException e) {
//		}
//		return null;
//	}
//	
//	public Transaction getTransaction(int transactionID) {
//		String query = "SELECT * FROM " + this.tableHeader 
//				+ " WHERE transactionID = " + transactionID 
//				+ " LIMIT 1";
//		ResultSet rs = con.executeQuery(query);
//		try {
//			rs.next();
//			return map(rs);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public boolean insertTransaction(int voucherID, int employeeID, int totalPayment) {
		String query = String.format("INSERT INTO %s (purchaseDate, voucherID, employeeID, totalPrice) VALUES (?,?,?,?)", this.tableHeader);
		PreparedStatement ps = con.preparedStatement(query);
		try {
			ps.setDate(1, purchaseDate);
			if(voucherID != 0) {
				ps.setInt(2, voucherID);
			}
			else {
				ps.setNull(2, java.sql.Types.INTEGER);
			}
			ps.setInt(3, employeeID);
			ps.setInt(4, totalPayment);
			if(ps.executeUpdate() == 1) {
				ResultSet rs = ps.getGeneratedKeys();
				int generatedKey = 0;
				if (rs.next()) {
				    generatedKey = rs.getInt(1);
				}
				
				//insert detail
				for (TransactionItem transactionItem : listTransactionItem) {
					String queryDetail = String.format("INSERT INTO %s (transactionID, productID, quantity) VALUES (?,?,?)", this.tableDetail);
					PreparedStatement psDetail = con.preparedStatement(queryDetail);
					
					try {
						psDetail.setInt(1, generatedKey);
						psDetail.setInt(2, transactionItem.getProductID());
						psDetail.setInt(3, transactionItem.getQuantity());
						psDetail.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
		}
		
		return false;
	}
	
	
	public Transaction() {
	}
	
	
	public Transaction(int transactionID, Date purchaseDate, int voucherID, int employeeID, int totalPrice,
			List<TransactionItem> listTransactionItem) {
		this.transactionID = transactionID;
		this.purchaseDate = purchaseDate;
		this.voucherID = voucherID;
		this.employeeID = employeeID;
		this.totalPrice = totalPrice;
		this.listTransactionItem = listTransactionItem;
	}


	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public int getVoucherID() {
		return voucherID;
	}
	public void setVoucherID(int voucherID) {
		this.voucherID = voucherID;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<TransactionItem> getListTransactionItem() {
		return listTransactionItem;
	}
	public void setListTransactionItem(List<TransactionItem> listTransactionItem) {
		this.listTransactionItem = listTransactionItem;
	}
	
	

}
