package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import Connector.Connector;
import models.Employee;
import views.LoginView;


public class LoginController {

	private JTextField tfUsername, tfPassword;
	private JButton btnLogin;
	private JFrame frame;
	private Connection con;
	private Employee employee;
	
	public LoginController(JTextField tfUsername, JTextField tfPassword, JButton btnLogin, JFrame frame) {
		super();
		this.tfUsername = tfUsername;
		this.tfPassword = tfPassword;
		this.btnLogin = btnLogin;
		this.frame = frame;
	}
	
	public static void LoginView() {
		new LoginView();
	}
	
	public void validate() {
		con = Connector.connect();
		
		if(tfUsername.getText().equals("") && tfPassword.getText().equals("")) {
			 JOptionPane.showMessageDialog(frame, "Username and Password cannot be null!", "Login Message", JOptionPane.INFORMATION_MESSAGE);
		}else if(tfUsername.getText().equals("")) {
			JOptionPane.showMessageDialog(frame, "Username cannot be null!", "Login Message", JOptionPane.INFORMATION_MESSAGE);
		}else if(tfPassword.getText().equals("")) {
			JOptionPane.showMessageDialog(frame, "Password cannot be null!", "Login Message", JOptionPane.INFORMATION_MESSAGE);
		}else {
			try {
				Statement statement =  con.createStatement();
				int positionID =0;
				String username = tfUsername.getText(); 
				String password = tfPassword.getText();
				String positionName = "";
				
				String query = String.format("select * from employee WHERE username = '%s' AND password = '%s'", username, password);
				ResultSet res = statement.executeQuery(query);
				
				while(res.next()) {
					positionID = res.getInt("positionID");
					employee = new Employee(positionID, positionName, res.getInt("employeeID"), res.getString("name"), res.getString("status"), res.getInt("salary"), res.getString("username"), res.getString("password"));
				}
				
				String queryEmp = String.format("SELECT * FROM position WHERE positionID = %d", positionID);
				ResultSet res1 = statement.executeQuery(queryEmp);
				while(res1.next()) {
					positionName = res1.getString("name");
				}
				
				if(employee.getStatus().equals("Active")) {
					if(positionName.equals("Barista")) {
						
						
					}else if(positionName.equals("Product Admin")) {
						
						
					}else if(positionName.equals("Manager")) {
						
						
					}else if(positionName.equals("HRD")) {
						
					}else {
						JOptionPane.showMessageDialog(frame, "Wrong Email or Password!", "Failed Login", JOptionPane.ERROR_MESSAGE);
					}						
				}else {
					JOptionPane.showMessageDialog(frame, "Your current status account is not Active!", "Failed Login", JOptionPane.ERROR_MESSAGE);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
