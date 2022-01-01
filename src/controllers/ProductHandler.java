package controllers;

import java.util.List;
import models.Product;


public class ProductHandler {

	private static ProductHandler controller = null;
	private String errorMessage;
	private Product product;
	
	private ProductHandler() {
		product = new Product();
		errorMessage = "";
	}
	
	public static ProductHandler getInstance() {
		if(controller == null) {
			controller = new ProductHandler();
		}
		return controller;
	}
	
	public List<Product> getAllproducts() {
		return product.getAllProducts();
	}
	
	public Product getProduct(String productID) {
		if(productID.isEmpty()) {
			errorMessage = "Product ID cannot be empty";
			return null;
		}
		else {
			int idint = -1;
			try {
				idint = Integer.parseInt(productID);				
			} catch (Exception e) {
				errorMessage = "Product Id must be numeric";
				return null;
			}
			return product.getProduct(idint);
		}
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public boolean insertProduct(String name, String description, String price, String stock) {
		
		if(name.isEmpty()) {
			errorMessage = "Product Name cannot be empty";
			return false;
		}
		else if(description.isEmpty()) {
			errorMessage = "Product Description cannot be empty";
			return false;
		}
		else if(price.isEmpty()) {
			errorMessage = "Product Price cannot be empty";
			return false;
		}
		else if(stock.isEmpty()) {
			errorMessage = "Product Stock cannot be empty";
			return false;
		}
		else {
			int priceint = -1;
			int stockint = -1;
			
			try {
				priceint = Integer.parseInt(price);				
			} catch (Exception e) {
				errorMessage = "Product Price must be numeric";
				return false;
			}
			if(priceint < 1) {
				errorMessage = "Product Price cannot be less than one";
				return false;
			}
			
			try {
				stockint = Integer.parseInt(stock);				
			} catch (Exception e) {
				errorMessage = "Product Stock must be numeric";
				return false;
			}
			if(stockint < 0) {
				errorMessage = "Product Stock cannot be less than zero";
				return false;
			}
			
			product = new Product();
			product.setName(name);
			product.setDescription(description);
			product.setPrice(priceint);
			product.setStock(stockint);
			boolean inserted = product.insertProduct();
			
			if (!inserted) {
				errorMessage = "Product insert failed";
			}
			return inserted;
		}
	}
	
	public boolean updateProduct(String productID, String name, String description, String price, String stock) {
		if(productID.isEmpty()) {
			errorMessage = "Product ID cannot be empty";
			return false;
		}
		if(name.isEmpty()) {
			errorMessage = "Product Name cannot be empty";
			return false;
		}
		else if(description.isEmpty()) {
			errorMessage = "Product Description cannot be empty";
			return false;
		}
		else if(price.isEmpty()) {
			errorMessage = "Product Price cannot be empty";
			return false;
		}
		else if(stock.isEmpty()) {
			errorMessage = "Product Stock cannot be empty";
			return false;
		}
		else {
			int idint = -1;
			int priceint = -1;
			int stockint = -1;
			
			try {
				idint = Integer.parseInt(productID);				
			} catch (Exception e) {
				errorMessage = "Product Id must be numeric";
				return false;
			}
			
			try {
				priceint = Integer.parseInt(price);				
			} catch (Exception e) {
				errorMessage = "Product Price must be numeric";
				return false;
			}
			if(priceint < 1) {
				errorMessage = "Product Price cannot be less than one";
				return false;
			}
			
			try {
				stockint = Integer.parseInt(stock);				
			} catch (Exception e) {
				errorMessage = "Product Stock must be numeric";
				return false;
			}

			if(stockint < 0) {
				errorMessage = "Product Stock cannot be less than zero";
				return false;
			}
			
			product = new Product(idint, name, description, priceint, stockint);
			boolean updated = product.updateProduct();
			
			if (!updated) {
				errorMessage = "Product update failed";
			}
			return updated;
		}
	}
	
	public boolean updateProductStock(int productID, int stock) {
		
		if(stock < 0) {
			errorMessage = "Product Stock cannot be less than zero";
			return false;
		}
		else {
			
			product = new Product().getProduct(productID);
			
			product.setStock(stock);
			
			boolean updated = product.updateProduct();
			
			if (!updated) {
				errorMessage = "Product update stock failed";
			}
			return updated;
		}
	}
	
	public boolean deleteProduct(String productID) {
		if(productID.isEmpty()) {
			errorMessage = "Product ID cannot be empty";
			return false;
		}
		else {
			int idint=-1;
			try {
				idint = Integer.parseInt(productID);				
			} catch (Exception e) {
				errorMessage = "Product Id must be numeric";
				return false;
			}
			product = new Product();
			product.setProductID(idint);
			boolean deleted = product.deleteProduct(idint);
			
			if (!deleted) {
				errorMessage = "Product delete failed";
			}
			return deleted;
		}
	}
	
}
