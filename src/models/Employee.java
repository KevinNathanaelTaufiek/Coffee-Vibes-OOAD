package models;

public class Employee extends Position{

	private int employeeID;
	private String name;
	private String status;
	private int salary;
	private String username;
	private String password;
	
	public Employee(int positionID, String positionName, int employeeID, String name, String status, int salary,
			String username, String password) {
		super(positionID, positionName);
		this.employeeID = employeeID;
		this.name = name;
		this.status = status;
		this.salary = salary;
		this.username = username;
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
	


}
