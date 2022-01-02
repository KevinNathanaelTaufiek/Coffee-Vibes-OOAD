package main;

import java.util.List;

import controllers.EmployeeHandler;
import controllers.LoginController;
import models.Employee;
import views.HRView;

public class Main {

	public Main() {
		LoginController.LoginView();
//		new TransactionManagementForm();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
