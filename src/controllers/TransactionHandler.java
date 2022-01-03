package controllers;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import models.CartItem;
import models.Employee;
import models.Product;
import models.Transaction;
import models.TransactionItem;
import models.Voucher;
import views.TransactionManagementForm;

public class TransactionHandler {

	private static TransactionHandler controller = null;
	private String errorMessage;
	private Transaction transaction;
	private TransactionItem transactionItem;
	
	private TransactionHandler() {
		transaction = new Transaction();
		errorMessage = "";
	}
	
	public static TransactionHandler getInstance() {
		if(controller == null) {
			controller = new TransactionHandler();
		}
		return controller;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void viewTransactionManagementForm(Employee emp) {
		new TransactionManagementForm(emp);
	}
	
	public List<Transaction> getAllTransactions() {
		return transaction.getAllTransactions();
	}
	
	public List<TransactionItem> getAllTransactionDetail(int id){
		return transactionItem.getAllDetailItem(id);
	}
	
	public Transaction getTransaction(String transactionID) {
		if(transactionID.isEmpty()) {
			errorMessage = "Transaction ID cannot be empty";
			return null;
		}
		else {
			int idint = -1;
			try {
				idint = Integer.parseInt(transactionID);				
			} catch (Exception e) {
				errorMessage = "Transaction Id must be numeric";
				return null;
			}
			return transaction.getTransaction(idint);
		}
	}
	
	public Transaction insertTransaction(String voucherID, String employeeID, int totalPayment) {
		
		if (employeeID.isEmpty()) {
			errorMessage = "Quantity cannot be empty";
			return null;
		}
		else {
			
			int employeeIDint = 0;
			int voucherIDint = 0;
			try {
				employeeIDint = Integer.parseInt(employeeID);				
			} catch (Exception e) {
				errorMessage = "Employee ID must be numeric";
				return null;
			}
			
			if(!voucherID.isEmpty()) {
				
				try {
					voucherIDint = Integer.parseInt(voucherID);				
				} catch (Exception e) {
					errorMessage = "Voucher ID must be numeric";
					return null;
				}
				
				VoucherController voucherController = VoucherController.getInstance();
				
				Voucher voucherUsed = voucherController.getVoucher(voucherIDint);
				
				 if(voucherUsed == null) {
					 if(!voucherID.equals("0")) {
						 errorMessage = "Voucher not found";
						 return null;						 
					 }
				 }else if(voucherUsed.getStatus().equals("InValid")) {
					 errorMessage = "Voucher was used";
					 return null;
				 }					 
				
				voucherController.deleteVoucher(voucherID);
			}
			
			CartHandler cartHandler = CartHandler.getInstance();
			List<CartItem> cart = cartHandler.getCart();
			List<TransactionItem> transactionItems = new Vector<>();
			
			for (CartItem cartItem : cart) {
				Product product = cartItem.getProduct();
				ProductHandler.getInstance().updateProductStock(
						product.getProductID(), 
						product.getStock() - cartItem.getQuantity()
						);
				
				TransactionItem newTransactionItem = new TransactionItem();
				newTransactionItem.setProductID(product.getProductID());
				newTransactionItem.setQuantity(cartItem.getQuantity());
				
				transactionItems.add(newTransactionItem);
			}
			
			Transaction transaction = new Transaction();
			transaction.setListTransactionItem(transactionItems);
			transaction.setPurchaseDate(new Date(Calendar.getInstance().getTime().getTime()));
			
			if(transaction.insertTransaction(voucherIDint, employeeIDint, totalPayment)) {
				cartHandler.clearCart();
				errorMessage = "Insert Transaction Success!";
				return transaction;
			}
			errorMessage = "Insert Transaction Failed!";
			return null;
		}
	}

}
