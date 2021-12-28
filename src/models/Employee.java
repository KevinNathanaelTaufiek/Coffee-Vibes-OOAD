package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connector.Connector;
import views.ProductAdminView;

public class Employee{

	private Connection con = Connector.connect();
	private int employeeID;
	private String name;
	private String status;
	private int salary;
	private String username;
	private String password;
	private int positionID;
	private Employee employee;
	
	
	public Employee() {
		
	}
	
	public Employee(int positionID, int employeeID, String name, String status, int salary,
			String username, String password) {
		this.employeeID = employeeID;
		this.name = name;
		this.status = status;
		this.salary = salary;
		this.username = username;
		this.positionID = positionID;
		this.password = password;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	
	public int getPositionID() {
		return positionID;
	}

	public void setPositionID(int positionID) {
		this.positionID = positionID;
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
				employee = new Employee(positionID, res.getInt("employeeID"), res.getString("name"), res.getString("status"), res.getInt("salary"), res.getString("username"), res.getString("password"));
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
						
						ProductAdmin pa = new ProductAdmin(employee.getPositionID(),employee.getEmployeeID(), employee.getName(), employee.getStatus(), employee.getSalary(), employee.getUsername(), employee.getPassword());
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
