package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controllers.LoginController;


public class LoginView{

	private JFrame frame = new JFrame("Coffee Vibes");
	private JPanel mainPanel, topPanel, botPanel, centerPanel;
	private JLabel jlTitle, jlUsername, jlPassword;
	private JTextField tfUsername, tfPassword;
	private JButton btnLogin;
	
	public LoginView() {
		setPanel();
		setFrame();
	}
	
	private void setFrame() {
		frame.setSize(400, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void setPanel() {
				
		// Set Main Panel
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new LineBorder(Color.WHITE, 4));
		// Set Top Panel
		topPanel = new JPanel();
		jlTitle = new JLabel("Login");
		
		topPanel.add(jlTitle);
		
		
		// Set Center Panel
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 2));
		
		jlUsername = new JLabel("Username: ");
		jlPassword = new JLabel("Password: ");
		tfUsername = new JTextField();
		tfPassword = new JTextField();
		
		
		centerPanel.add(jlUsername);
		centerPanel.add(tfUsername);
		centerPanel.add(jlPassword);
		centerPanel.add(tfPassword);
		
		// Set For Button
		botPanel = new JPanel();
		btnLogin = new JButton("Login");
		
		
		botPanel.add(btnLogin);
		
		// set All Panel To Main Panel
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(botPanel,  BorderLayout.SOUTH);
		mainPanel.setBackground(Color.ORANGE);
		
		frame.add(mainPanel);
		
		LoginController controller = new LoginController(tfUsername, tfPassword, btnLogin, frame);
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.validate();
			}
		});
	}

	

}
