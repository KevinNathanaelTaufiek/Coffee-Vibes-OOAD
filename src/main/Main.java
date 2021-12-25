package main;

import controllers.LoginController;
//import views.VoucherView;

public class Main {

	public Main() {
		LoginController.LoginView();
//		new VoucherView();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
