package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import connector.Connector;
import models.Employee;
import models.ProductAdmin;
import models.User;
import views.LoginView;
import views.ProductAdminView;


public class LoginController {

	private User user;
	public static LoginController controller = null;
	private String errorMessage;
	
	public LoginController() {
		user = new User();
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
		if(username.equals("") && password.equals("")) {
			errorMessage = "Username and Password cannot be null!";
			return false;
		}else if(username.equals("")) {
			errorMessage = "Username cannot be null!";
			return false;
		}else if(password.equals("")) {
			errorMessage = "Password cannot be null!";
			return false;
		}else {
			errorMessage = user.checkLogin(username, password);
		}
		return false;
	}

}
