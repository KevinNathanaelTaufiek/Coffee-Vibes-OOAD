package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controllers.LoginController;


public class LoginView{

	private JFrame frame = new JFrame("Coffee Vibes");
	private JPanel mainPanel, titlePanel, btnPanel,formPanel ;
	private JLabel jlTitle, jlUsername, jlPassword;
	private JTextField tfUsername;
	private JPasswordField tfPassword;
	private JButton btnLogin;
	
	public LoginView() {
		setPanel();
		setFrame();
	}
	
	private void setFrame() {
		frame.setSize(400, 600);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void setPanel() {
				
		// Set Main Panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new LineBorder(Color.WHITE, 4));
		// Set Top Panel
		titlePanel = new JPanel();
		jlTitle = new JLabel("Login");
		jlTitle.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		titlePanel.add(jlTitle);
		
		
		// Set Center Panel
		formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
		jlUsername = new JLabel("Username: ");
		jlUsername.setBounds(100, 8, 70, 20);
		tfUsername = new JTextField();
		tfUsername.setBounds(100, 27, 193, 28);
		tfUsername.setPreferredSize(new Dimension(200,50));
	
		
		jlPassword= new JLabel("Password: ");
		jlPassword.setBounds(100, 8, 70, 20);
		tfPassword = new JPasswordField();
		tfPassword.setBounds(100, 27, 193, 28);
		tfPassword.setPreferredSize(new Dimension(200,50));
		
		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(10, 10, 10, 10); 
		c.gridx = 0;
		c.gridy = 0;
		formPanel.add(jlUsername,c);
		
		c.gridx=1; 
		formPanel.add(tfUsername,c);
		
		c.gridy=1; 
		c.gridx=0;
		formPanel.add(jlPassword,c);
		
		c.gridx = 1; 
		formPanel.add(tfPassword,c);
		
		// Set For Button
		btnPanel = new JPanel();
		btnLogin = new JButton("Login");
		btnLogin.setBounds(100, 110, 90, 25);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.BLACK);
		btnPanel.add(btnLogin);
		
		
		// set All Panel To Main Panel
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(formPanel, BorderLayout.CENTER);
		mainPanel.add(btnPanel,  BorderLayout.SOUTH);
		
		frame.add(mainPanel);
		
		LoginController controller = new LoginController(tfUsername, tfPassword, btnLogin, frame);
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.validate();
			}
		});
	}

	
	public void a() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new LineBorder(Color.WHITE, 4));
		
		
		
		// Set Top Panel
		titlePanel = new JPanel();
		jlTitle = new JLabel("Login");
		jlTitle.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		titlePanel.setLocation(new Point(500, 300));
		frame.add(titlePanel);
		
		frame.setSize(new Dimension(400, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		formPanel = new JPanel();
		jlUsername = new JLabel("Username");
		jlUsername.setBounds(100, 8, 70, 20);
		formPanel.add(jlUsername);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(100, 27, 193, 28);
		formPanel.add(tfUsername);
		
		jlPassword= new JLabel("Password");
		jlPassword.setBounds(100, 55, 70, 20);
		formPanel.add(jlPassword);
		
		tfPassword = new JPasswordField();
		tfPassword.setBounds(100, 75, 193, 28);
		formPanel.add(tfPassword);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(100, 110, 90, 25);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.BLACK);
		btnPanel.add(btnLogin);
	}
	

}
