package controllers;

import models.Employee;
import views.LoginView;

public class LoginHandler {

	private Employee employee;
	public static LoginHandler controller = null;
	private String errorMessage;
	
	private LoginHandler() {
		employee = new Employee();
		errorMessage = "";
	}
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static void LoginView() {
		new LoginView();
	}
	
	public static LoginHandler getInstance() {
		if(controller == null) {
			controller = new LoginHandler();
		}
		return controller;
	}
	
	public boolean validate(String username, String password) {
		String temp = employee.checkLogin(username, password);
		if(username.equals("") && password.equals("")) {
			errorMessage = "Username and Password cannot be null!";
			return false;
		}else if(username.equals("")) {
			errorMessage = "Username cannot be null!";
			return false;
		}else if(password.equals("")) {
			errorMessage = "Password cannot be null!";
			return false;
		}else if(!temp.equals("")) {
			errorMessage = temp;
			return false;
		}else {
			return true;
		}
	}

}
