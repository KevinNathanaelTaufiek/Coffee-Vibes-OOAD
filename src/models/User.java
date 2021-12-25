package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connector.Connector;
import views.ProductAdminView;

public class User {

	private Connection con = Connector.connect();
	private Employee employee;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public String checkLogin(String username, String password) {
		con = Connector.connect();
		String error="";
		try {
			Statement statement =  con.createStatement();
			int positionID =0;
			String positionName = "";
			
			String query = String.format("SELECT * FROM employee WHERE username = '%s' AND password = '%s'", username, password);
			
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
			if(employee != null) {
				if(employee.getStatus().equals("Active")) {
					if(positionName.equals("Barista")) {
						
					}else if(positionName.equals("ProductAdmin")) {
						
						ProductAdmin pa = new ProductAdmin(employee.getPositionID(), employee.getPositionName(), employee.getEmployeeID(), employee.getName(), employee.getStatus(), employee.getSalary(), employee.getUsername(), employee.getPassword());
						new ProductAdminView(pa);
						
					}else if(positionName.equals("Manager")) {
						
					}else if(positionName.equals("HRD")) {
						
					}else {
						error= "Wrong Email or Password!";				
					}						
				}else {
					error = "Your current status account is not Active!";
					
				}
			}else {
				error =  "Wrong Email or Password!";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return error;
	}
	
}
