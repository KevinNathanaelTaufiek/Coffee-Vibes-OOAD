package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import controllers.LoginHandler;


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
		frame.setSize(400, 300);
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
		jlTitle = new JLabel("Login Form");
		jlTitle.setFont(new Font("", Font.PLAIN, 24));
		
		titlePanel.add(jlTitle);
		
		
		// Set Center Panel
		formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
		jlUsername = new JLabel("Username: ");
		tfUsername = new JTextField();
		tfUsername.setBounds(100, 27, 193, 28);
		tfUsername.setPreferredSize(new Dimension(200,50));
	
		
		jlPassword= new JLabel("Password: ");
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
		
		LoginHandler controller = LoginHandler.getInstance();
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean flag = controller.getInstance().validate(tfUsername.getText(), tfPassword.getText());
				if(flag == true) {
					frame.setVisible(false);
				}else {
					if(!controller.getErrorMessage().equals("")) {
						JOptionPane.showMessageDialog(frame, LoginHandler.getInstance().getErrorMessage(), "Login Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
	}
}
