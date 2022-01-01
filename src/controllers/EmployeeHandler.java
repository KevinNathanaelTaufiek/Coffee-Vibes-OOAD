package controllers;

import models.Employee;
import models.Product;

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
				errorMessage = "Employee Id must be numeric";
				return false;
			}
			employee = new Employee();
			employee.setEmployeeID(idint);
			boolean deleted = employee.fireEmployee(idint);
			
			if (!deleted) {
				errorMessage = "Employee delete failed";
			}
			return deleted;
		}
	}
	

}
