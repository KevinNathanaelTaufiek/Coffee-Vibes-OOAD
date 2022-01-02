package controllers;

import java.util.List;

import models.Employee;

public class EmployeeHandler {

	private static EmployeeHandler controller = null;
	private String errorMessage;
	private Employee employee;
	
	private EmployeeHandler() {
		employee = new Employee();
		errorMessage = "";
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static EmployeeHandler getInstance() {
		if(controller == null) {
			controller = new EmployeeHandler();
		}
		return controller;
	}
	
	public List<Employee> getAllEmployees() {
		return employee.getAllEmployees();
	}
	
	public List<Employee> searchEmployee(String name) {
		return employee.searchEmployee(name);
	}
	
	public Employee getEmployee(String employeeID) {
		
		if(employeeID.isEmpty()) {
			errorMessage = "Employee ID cannot be empty";
			return null;
		}
		
		int empIDInt = -1;
		try {
			empIDInt = Integer.parseInt(employeeID);
		} catch (Exception e) {
			errorMessage = "Employee ID cannot be empty";
			return null;
		}
		
		return employee.getEmployee(empIDInt);
	}
	
	private int getPositionByName(String position) {
		int positionInt = -1;
		switch (position.toLowerCase()) {
			case "barista":
				positionInt = 1;
				break;
			
			case "productadmin":
				positionInt = 2;
				break;
		
			case "manager":
				positionInt = 3;
				break;
				
			case "hrd":
				positionInt = 4;
				break;
				
			default:
				positionInt = -1;
				break;
		}
		
		return positionInt;
	}
	
	public boolean insertEmployee(String name, String position, String salary, String status, String username, String password) {
		
		if(name.isEmpty()) {
			errorMessage = "Name cannot be empty";
			return false;
		}else if(position.isEmpty()) {
			errorMessage = "Position cannot be empty";
			return false;
		}else if(salary.isEmpty()) {
			errorMessage = "Salary cannot be empty";
			return false;
		}else if(status.isEmpty()) {
			errorMessage = "Status cannot be empty";
			return false;
		}else if(username.isEmpty()) {
			errorMessage = "Username cannot be empty";
			return false;
		}else if(password.isEmpty()) {
			errorMessage = "Password cannot be empty";
			return false;
		}else {
			int positionID = getPositionByName(position), salaryInt = -1;
			
			try {
				salaryInt = Integer.parseInt(salary);
			} catch (Exception e) {
				errorMessage = "Salary must be numeric";
				return false;
			}
			
			if(salaryInt < 0) {
				errorMessage = "Salary cannot be less than zero";
				return false;
			}
			
			employee = new Employee();
			employee.setName(name);
			employee.setPositionID(positionID);
			employee.setSalary(salaryInt);
			employee.setStatus(status);
			employee.setUsername(username);
			employee.setPassword(password);
		}
		boolean inserted = employee.insertEmployee();
		
		if(!inserted) {
			errorMessage = "Failed inserting employee";
		}
		
		return inserted;
	}
	
	public boolean updateEmployee(String employeeID, String name, String position, String salary, String status, String username, String password) {
		
		if(employeeID.isEmpty()) {
			errorMessage = "Employee ID cannot be empty";
			return false;
		}else if(name.isEmpty()) {
			errorMessage = "Name cannot be empty";
			return false;
		}else if(position.isEmpty()) {
			errorMessage = "Position cannot be empty";
			return false;
		}else if(salary.isEmpty()) {
			errorMessage = "Salary cannot be empty";
			return false;
		}else if(status.isEmpty()) {
			errorMessage = "Status cannot be empty";
			return false;
		}else if(username.isEmpty()) {
			errorMessage = "Username cannot be empty";
			return false;
		}else if(password.isEmpty()) {
			errorMessage = "Name cannot be empty";
			return false;
		}else {
			int empIDInt = -1, positionID = getPositionByName(position), salaryInt = -1;
			
			try {
				empIDInt = Integer.parseInt(employeeID);
			} catch (Exception e) {
				errorMessage = "Employee ID must be numeric";
				return false;
			}
			
			try {
				salaryInt = Integer.parseInt(salary);
			} catch (Exception e) {
				errorMessage = "Salary must be numeric";
				return false;
			}
			
			if(salaryInt < 0) {
				errorMessage = "Salary cannot be less than zero";
				return false;
			}
			
			employee = new Employee(empIDInt, positionID, name, status, salaryInt, username, password);
		}
		boolean updated = employee.updateEmployee();
		
		if(!updated) {
			errorMessage = "Failed updating employee";
		}
		
		return updated;
	}
	
	public boolean fireEmployee(String id) {
		if(id.isEmpty()) {
			errorMessage = "Employe ID cannot be empty";
			return false;
		}
		else {
			int idint=-1;
			try {
				idint = Integer.parseInt(id);				
			} catch (Exception e) {
				errorMessage = "Employee ID must be numeric";
				return false;
			}
			employee = new Employee();
			employee.setEmployeeID(idint);
			boolean deleted = employee.fireEmployee(idint);
			
			if (!deleted) {
				errorMessage = "Failed deleting employee";
			}
			return deleted;
		}
	}
	
}
