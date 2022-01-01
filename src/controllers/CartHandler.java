package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import models.CartItem;
import models.Employee;
import models.Product;

public class CartHandler {

	private List<CartItem> cartItems;
	private static CartHandler controller = null;
	private String errorMessage;
	
	private CartHandler() {
		cartItems = new Vector<>();
		errorMessage = "";
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static CartHandler getInstance() {
		if(controller == null) {
			controller = new CartHandler();
		}
		return controller;
	}
	
	public List<CartItem> getCart() {
		return cartItems;
	}
	
	public Product getProduct(String productID) {
		Product product = ProductHandler.getInstance().getProduct(productID);
		
		if(product == null) {
			errorMessage = ProductHandler.getInstance().getErrorMessage();
			return null;
		}
		
		return product;
	}
	
	
	public CartItem addToCart(String productID, String quantity) {
		if(productID.isEmpty()) {
			errorMessage = "Product ID cannot be empty";
			return null;
		}
		else if (quantity.isEmpty()) {
			errorMessage = "Quantity cannot be empty";
			return null;
		}
		else {
			int quantityint = -1;
			
			try {
				quantityint = Integer.parseInt(quantity);				
			} catch (Exception e) {
				errorMessage = "Quantity must be numeric";
				return null;
			}
			
			if(quantityint < 1) {
				errorMessage = "Quantity cannot be less than one";
				return null;
			}
			
			Product product = getProduct(productID);
			if(product == null) return null;
			
			CartItem cartItem = updateCartProductQuantity(product.getProductID(),quantityint);
			
			if(cartItem == null) {
				cartItem = new CartItem(product, quantityint);
				
				boolean added = cartItems.add(cartItem);
				
				if (!added) {
					errorMessage = "Product update failed";
				}				
			}
			
			return cartItem;
		}
	}
	
	public CartItem updateCartProductQuantity(int productID, int quantity) {
		for (CartItem cartItem : cartItems) {
			if(cartItem.getProduct().getProductID() == productID) {
				cartItem.setQuantity(quantity);
				return cartItem;
			}
		}
		return null;
	}
	
	public boolean removeProductFromCart(String productID) {
		if(productID.isEmpty()) {
			errorMessage = "Product ID cannot be empty";
			return false;
		}
		
		int idint=-1;
		try {
			idint = Integer.parseInt(productID);				
		} catch (Exception e) {
			errorMessage = "Product Id must be numeric";
			return false;
		}
		
		for (CartItem cartItem : cartItems) {
			if(cartItem.getProduct().getProductID() == idint) {
				return cartItems.remove(cartItem);
			}
		}
		return false;
	}
	
	public void clearCart() {
		cartItems = new Vector<>();
	}

}
