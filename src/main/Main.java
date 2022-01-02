package main;

import controllers.LoginController;
import views.TransactionManagementForm;

public class Main {

	public Main() {
		LoginController.LoginView();
//		new TransactionManagementForm();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
