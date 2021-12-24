package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import connector.Connector;
import controllers.ProductController;
import models.ProductAdmin;



public class ProductAdminView {
	private JFrame frame = new JFrame("Product Management");
	private ProductAdmin pa;
	private JLabel jlName, jlProductID, jlProductName, jlProductPrice, jlProductStock, jlProductDesc;
	private JTextField tfSearch, tfProductID, tfProductName, tfProductPrice, tfProductStock, tfProductDesc;
	private JButton btnSearch, btnInsert, btnUpdate, btnDelete, btnLogout;
	
	private JTable table;
	private DefaultTableModel modelTable;
	
	public ProductAdminView(ProductAdmin pa) {
		this.pa = pa;
		framePlay();
		setPanel();
		
		//Set Table Model
		modelTable = new DefaultTableModel(new String[] {
				"productID",
				"name",
				"description",
				"price",
				"stock"
			}, 0);
			table.setModel(modelTable);
				
		//Refresh
		modelTable.setRowCount(0);
		Connection con = Connector.connect();
		try {
			Statement stat = con.createStatement();
			String query = String.format("SELECT * FROM product");
			
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				modelTable.addRow(new Object[] {
						res.getInt("ProductID"),
						res.getString("name"),
						res.getString("description"),
						res.getInt("price"),
						res.getInt("stock")
				});
			}
			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void framePlay() {
		frame.setSize(900, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	private void setPanel() {
		// Content Panel
		JPanel content = new JPanel();
		content.setBorder(new LineBorder(Color.WHITE, 4));
		content.setLayout(new BorderLayout());
		
		// Title Panel
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(2, 2));
		jlName = new JLabel("Username: " + pa.getUsername());
		btnLogout = new JButton("Log Out");
		
		tfSearch = new JTextField();
		btnSearch = new JButton("Search By ID");
		titlePanel.add(jlName);
		titlePanel.add(btnLogout);
		titlePanel.add(tfSearch);
		titlePanel.add(btnSearch);
		content.add(titlePanel, BorderLayout.NORTH);
		
		// Scroll Panel ( Tabel )
		JScrollPane jspanel = new JScrollPane();
		table = new JTable();
		jspanel.setViewportView(table);
		content.add(jspanel, BorderLayout.CENTER);
		
		// Bottom Panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(13, 1));
		
		JPanel pan1 = new JPanel();
		bottomPanel.add(pan1);
		
		// ProductID
		jlProductID = new JLabel("Product ID [ Update | Delete]");
		tfProductID = new JTextField();
		bottomPanel.add(jlProductID);
		bottomPanel.add(tfProductID);
		
		// ProductName
		jlProductName = new JLabel("Product Name");
		tfProductName = new JTextField();
		bottomPanel.add(jlProductName);
		bottomPanel.add(tfProductName);
		
		//ProductDesc
		jlProductDesc= new JLabel("Product Description");
		tfProductDesc = new JTextField();
		bottomPanel.add(jlProductDesc);
		bottomPanel.add(tfProductDesc);
		
		// Product Price
		jlProductPrice= new JLabel("Product Price");
		tfProductPrice = new JTextField();
		bottomPanel.add(jlProductPrice);
		bottomPanel.add(tfProductPrice);
		
		// Product Stock
		jlProductStock= new JLabel("Product Stocks");
		tfProductStock = new JTextField();
		bottomPanel.add(jlProductStock);
		bottomPanel.add(tfProductStock);
		
		JPanel pan = new JPanel();
		bottomPanel.add(pan);
		
		// Button Insert Delete Update
		JPanel btn_panel = new JPanel();
		btn_panel.setLayout(new GridLayout(1, 3));
		btnInsert = new JButton("Insert Product");
		btnUpdate = new JButton("Update Product");
		btnDelete = new JButton("Delete Product");
		btn_panel.add(btnInsert);
		btn_panel.add(btnUpdate);
		btn_panel.add(btnDelete);
		
		bottomPanel.add(btn_panel);
		content.add(bottomPanel, BorderLayout.SOUTH);
		frame.setContentPane(content);
		
		ProductController controller = new ProductController(frame, tfSearch, tfProductID, tfProductName, tfProductPrice, tfProductStock, tfProductDesc, btnSearch, btnInsert, btnUpdate, btnDelete, table, modelTable, pa, btnLogout);
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.btnSearch(e);
				
			}
		});
		

		btnInsert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.btnInsert(e);
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.btnUpdate(e);
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.btnDelete(e);
			}
		});
		btnLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.btnLogout(e);
			}
		});
	}
	
	
	
}
