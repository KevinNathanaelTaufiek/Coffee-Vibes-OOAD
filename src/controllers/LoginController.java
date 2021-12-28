package controllers;

import models.Employee;
import views.LoginView;

public class LoginController {

	private Employee employee;
	public static LoginController controller = null;
	private String errorMessage;
	
	public LoginController() {
		employee = new Employee();
		errorMessage = "";
	}
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static void LoginView() {
		new LoginView();
	}
	
	public static LoginController getInstance() {
		if(controller == null) {
			controller = new LoginController();
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
