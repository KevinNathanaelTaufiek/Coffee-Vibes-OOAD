package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import connector.Connector;
import views.ProductManagementForm;

public class Employee{

	private int employeeID;
	private int positionID;
	private String name;
	private String status;
	private int salary;
	private String username;
	private String password;
	
	private Connector con = Connector.connect();
	private String table = "employee";
	
	private Employee map(ResultSet rs) {
		try {
			int employeeID = rs.getInt("employeeID");
			int positionID = rs.getInt("positionID");
			String name = rs.getString("name");
			String status = rs.getString("status");
			int salary = rs.getInt("salary");
			String username = rs.getString("username");
			String password = rs.getString("password");
			return new Employee(employeeID, positionID, name, status, salary, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Employee> getAllEmployees() {
		String query = String.format("SELECT * FROM " + this.table);
		
		ResultSet rs = con.executeQuery(query);
		
		try {
			Vector<Employee> employees = new Vector<>();
			while(rs.next()) {
				Employee employee = map(rs);
				employees.add(employee);
			}
			return employees;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Employee getEmployee (int employeeID) {
		String query = "SELECT * FROM " + this.table 
				+ " WHERE productID = " + employeeID 
				+ " LIMIT 1";
		ResultSet rs = con.executeQuery(query);
		try {
			rs.next();
			return map(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insertEmployee() {
		String query = String.format("INSERT INTO %s VALUES (?,?,?,?,?,?)", this.table);
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setInt(1, positionID);
			ps.setString(2, name);
			ps.setString(3, status);
			ps.setInt(4, salary);
			ps.setString(5, username);
			ps.setString(6, password);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean fireEmployee(int employeeID) {
		String query = String.format("DELETE FROM %s WHERE employeeID = ?", this.table);
		
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setInt(1, employeeID);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	//
	public String checkLogin(String username, String password) {
		String error="";
		String query = String.format("SELECT * FROM employee WHERE username = '%s' AND password = '%s' LIMIT 1", username, password);
		ResultSet rs = con.executeQuery(query);
		try {
			if(rs.next())
			{
				Employee employee = map(rs);
				String queryPosition = String.format("SELECT * FROM position WHERE positionID = %d", employee.positionID);
				ResultSet rs1 = con.executeQuery(queryPosition);
				rs1.next();
				String positionName = rs1.getString("name");
			
				if(employee.getStatus().equals("Active")) {
					if(positionName.equals("Barista")) {
						
						new ProductManagementForm(employee);
						
					}else if(positionName.equals("ProductAdmin")) {
						
						new ProductManagementForm(employee);
						
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
	
	
	public Employee(int employeeID, int positionID, String name, String status, int salary, String username,
			String password) {
		this.employeeID = employeeID;
		this.positionID = positionID;
		this.name = name;
		this.status = status;
		this.salary = salary;
		this.username = username;
		this.password = password;
	}

	public Employee() {}


	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public int getPositionID() {
		return positionID;
	}

	public void setPositionID(int positionID) {
		this.positionID = positionID;
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
	
	
	
}
