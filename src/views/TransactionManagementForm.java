package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import controllers.CartHandler;
import controllers.TransactionHandler;
import controllers.VoucherController;
import models.CartItem;
import models.Employee;

public class TransactionManagementForm extends JFrame implements ActionListener{

	private JPanel contentPanel, buttonPanel;
	private JButton checkoutButton, useVoucherButton;
	private JLabel titleLabel, voucherLabel, totalPriceLabel;
	private JTextField voucherField;
	private int totalPrice = 0;
	private Employee emp;
	
	//baru Barista Only yaa gess
	public TransactionManagementForm(Employee emp) {
		this.emp = emp;
		contentPanel = new JPanel(new GridLayout(4,1));
		buttonPanel = new JPanel(new GridLayout(1,2));
		
		
		titleLabel = new JLabel("Checkout Form");
		voucherLabel = new JLabel("Voucher ID [Optional] : ");
		totalPriceLabel = new JLabel("Total Price : " + updateTotalPrice("0"));
		
		voucherField = new JTextField();
		
		checkoutButton = new JButton("CheckOut");
		useVoucherButton = new JButton("Use Voucher");
		
		checkoutButton.addActionListener(this);
		useVoucherButton.addActionListener(this);
		
		contentPanel.add(titleLabel);
		contentPanel.add(voucherLabel);
		contentPanel.add(voucherField);
		contentPanel.add(totalPriceLabel);
		
		buttonPanel.add(checkoutButton);
		buttonPanel.add(useVoucherButton);
		
		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		setTitle("Staff");
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(500,300);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		
		String voucherID = voucherField.getText().toString();
		
		if(a.getSource() == checkoutButton) {
			TransactionHandler.getInstance().insertTransaction(voucherID, String.valueOf(emp.getEmployeeID()), totalPrice);
			JOptionPane.showMessageDialog(this, TransactionHandler.getInstance().getErrorMessage());
			this.dispose();
		}
		else if (a.getSource() == useVoucherButton) {
			totalPriceLabel.setText("Total Price : " + updateTotalPrice(voucherID));
		}
		
	}
	
	private int updateTotalPrice(String voucherID){
		int totalPrice = 0;
		List<CartItem> cart = CartHandler.getInstance().getCart();
		
		for (CartItem cartItem : cart) {
			totalPrice += (cartItem.getQuantity() * cartItem.getProduct().getPrice());
		}
		
		if(!voucherID.equals("0")) {
			
			try {
				totalPrice = totalPrice - (int) (totalPrice * (VoucherController.getInstance().getVoucher(Integer.parseInt(voucherID)).getDiscount() / 100.0));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Failed Use Voucher");
			}
		}
		
		this.totalPrice = totalPrice;
		return totalPrice;
	}

}
