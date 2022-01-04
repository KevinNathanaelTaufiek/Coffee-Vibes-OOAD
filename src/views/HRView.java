package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import connector.Connector;
import controllers.EmployeeHandler;
import models.Employee;
import models.Position;

public class HRView {
	
	private Connector con = Connector.connect();
	private JFrame frame = new JFrame("Human Resource");
	private Employee emp;
	private JLabel jlName, jlEmpID, jlEmpPos, jlEmpName, jlEmpStatus, jlEmpSalary, jlEmpUsername, jlEmpPassword;
	private JTextField tfSearch, tfEmpID, tfEmpName, tfEmpSalary, tfEmpUsername;
	private JPasswordField jpEmpPassword;
	private JButton btnSearch, btnInsert, btnUpdate, btnDelete, btnLogout;
	private JTable table;
	private DefaultTableModel modelTable;
	private JComboBox<String> jcPos, jcStatus;
	
	public HRView(Employee emp) {
		this.emp = emp;
		init();
		setPanel();
		loadData();
	}

	private void init() {
		frame.setSize(900, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	private void setPanel() {
		JPanel content = new JPanel();
		content.setBorder(new LineBorder(Color.WHITE, 4));
		content.setLayout(new BorderLayout());
		
		JPanel navPanel = new JPanel();
		navPanel.setLayout(new GridLayout(2, 2));
		
		jlName = new JLabel("Username: " + emp.getUsername());
		btnLogout = new JButton("Log Out");
		tfSearch = new JTextField();
		btnSearch = new JButton("Search By Name");
		
		navPanel.add(jlName);
		navPanel.add(btnLogout);
		navPanel.add(tfSearch);
		navPanel.add(btnSearch);
		
		content.add(navPanel, BorderLayout.NORTH);
		
//		=========================================================
		
		JScrollPane jspanel = new JScrollPane();
		table = new JTable(modelTable){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
		};
		
		jspanel.setViewportView(table);
		content.add(jspanel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(17, 1));
		
		JPanel gap1 = new JPanel();
		bottomPanel.add(gap1);
		
		jlEmpID = new JLabel("Employee ID [ Update | Delete]:");		
		tfEmpID = new JTextField();
		bottomPanel.add(jlEmpID);
		bottomPanel.add(tfEmpID);
		
		jlEmpPos = new JLabel("Employee Position:");
		bottomPanel.add(jlEmpPos);
		
		List<Position> pos = new Position().getAllPosition();
		String[] positionChoose = new String[pos.size()];
		int i=0;
		
		for (Position p : pos) {
			positionChoose[i++] = p.getPositionName();
		}
		
		jcPos = new JComboBox<String>(positionChoose);
		bottomPanel.add(jcPos);
		
		jlEmpName = new JLabel("Employee Name:");
		tfEmpName = new JTextField();
		bottomPanel.add(jlEmpName);
		bottomPanel.add(tfEmpName);
		
		jlEmpStatus = new JLabel("Employee Status:");
		bottomPanel.add(jlEmpStatus);
		String[] statusChoose = {"Active", "Inactive"};
		jcStatus = new JComboBox<String>(statusChoose);
		bottomPanel.add(jcStatus);
		
		jlEmpSalary = new JLabel("Employee Salary:");
		tfEmpSalary = new JTextField();
		bottomPanel.add(jlEmpSalary);
		bottomPanel.add(tfEmpSalary);
		
		jlEmpUsername = new JLabel("Employee Username:");
		tfEmpUsername = new JTextField();
		bottomPanel.add(jlEmpUsername);
		bottomPanel.add(tfEmpUsername);
		
		jlEmpPassword = new JLabel("Employee Password:");
		bottomPanel.add(jlEmpPassword);
		jpEmpPassword = new JPasswordField();
		bottomPanel.add(jpEmpPassword);
		
		JPanel gap2 = new JPanel();
		bottomPanel.add(gap2);
		
		JPanel btn_panel = new JPanel();
		
		if(this.emp.getPositionID()==4) { //HRD
			btn_panel.setLayout(new GridLayout(1,3,2,0));
			btnInsert = new JButton("Insert Employee");
			btnUpdate = new JButton("Update Employee");
			btnDelete = new JButton("Delete Employee");
			btn_panel.add(btnInsert);
			btn_panel.add(btnUpdate);
			btn_panel.add(btnDelete);
		}else if(this.emp.getPositionID() == 3) {//manager
			btn_panel.setLayout(new GridLayout(1, 1));
			btnDelete = new JButton("Fire Employee");
			btn_panel.add(btnDelete);
		}
	
		
		bottomPanel.add(btn_panel);
		content.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.setContentPane(content);
		
		eventListener();
		
	}
	
	private void eventListener() {
		EmployeeHandler controller = EmployeeHandler.getInstance();
		
		List<Position> pos = new Position().getAllPosition();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getData();
			}
			private void getData() {
				int row = table.getSelectedRow();
				int eId = (int) table.getValueAt(row, 0);
				String ePos = (String) table.getValueAt(row, 1);
				String eName = (String) table.getValueAt(row, 2);
				String eStatus = (String) table.getValueAt(row, 3);
				int eSalary = (int) table.getValueAt(row, 4);
				String eUsername = (String) table.getValueAt(row, 5);
				
				tfEmpID.setText(eId + "");
				jcPos.setSelectedItem(ePos + "");
				tfEmpName.setText(eName + "");
				jcStatus.setSelectedItem(eStatus + "");
				tfEmpSalary.setText(eSalary + "");
				tfEmpUsername.setText(eUsername + "");
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfSearch.getText().equals("")) {
					loadData();
				}else {
					String header[] = {
							"Employee ID", "Position ID", "Name",
							"Status", "Salary" , "Username", "Password"
					};
					
					modelTable = new DefaultTableModel(header, 0);

					String searchEmp = tfSearch.getText();
					List<Employee> emps = controller.searchEmployee(searchEmp);
					
					for (Employee emp : emps) {
						Vector<Object> row = new Vector<>();
						row.add(emp.getEmployeeID());
						row.add(emp.getPositionNameById());
						row.add(emp.getName());
						row.add(emp.getStatus());
						row.add(emp.getSalary());
						row.add(emp.getUsername());
						row.add(emp.getBulletPassword());
						modelTable.addRow(row);
					}
					
					table.setModel(modelTable);
				}
			}
		});
		if(this.emp.getPositionID()==4) {
			btnInsert.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String name = tfEmpName.getText();
					String position = jcPos.getSelectedItem().toString();
					String status = jcStatus.getSelectedItem().toString();
					String salary = tfEmpSalary.getText();
					String username = tfEmpUsername.getText();
					String password = String.valueOf(jpEmpPassword.getPassword());
					
					boolean inserted = controller.insertEmployee(name, position, salary, status, username, password);
					
					if(inserted) {
						clearTextField();
						JOptionPane.showMessageDialog(frame, "Success Insert New Employee");
						loadData();
					}else {
						JOptionPane.showMessageDialog(frame, controller.getErrorMessage());
					}
					
				}
			});
			
			btnUpdate.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int confrim = confirmationDialog("update");
					
					if(confrim == 1) {
						String employeeID = tfEmpID.getText();
						String name = tfEmpName.getText();
						String position = jcPos.getSelectedItem().toString();
						String status = jcStatus.getSelectedItem().toString();
						String salary = tfEmpSalary.getText();
						String username = tfEmpUsername.getText();
						String password = String.valueOf(jpEmpPassword.getPassword());
						
						boolean updated = controller.updateEmployee(employeeID, name, position, salary, status, username, password);
						
						if(updated) {
							clearTextField();
							JOptionPane.showMessageDialog(frame, "Success Update Employee");
							loadData();
						}else {
							JOptionPane.showMessageDialog(frame, controller.getErrorMessage());
						}
					}else {
						JOptionPane.showMessageDialog(frame, "Update Employee Canceled!");
					}
				
				}
			});
		}
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int confrim = confirmationDialog("delete");
				
				if(confrim == 1) {
					String employeeID = tfEmpID.getText();
					
					boolean deleted = controller.fireEmployee(employeeID);
					
					if(deleted) {
						clearTextField();
						JOptionPane.showMessageDialog(frame, "Success Delete Employee");
						loadData();
					}else {
						JOptionPane.showMessageDialog(frame, controller.getErrorMessage());
					}
				}else {
					JOptionPane.showMessageDialog(frame, "Delete Employee Canceled!");
				}
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnLogout) {
					new LoginView();
					frame.setVisible(false);
				}
			}
		});
	}
	
	private void clearTextField() {
		tfEmpID.setText("");
		tfEmpName.setText("");
		jcStatus.setSelectedItem("Barista");
		tfEmpSalary.setText("");
		jcStatus.setSelectedItem("Active");
		tfEmpUsername.setText("");
		tfSearch.setText("");
		jpEmpPassword.setText("");
	}
	
	private void loadData() {
		String header[] = {
				"employeeID", "positionID", "name",
				"status", "salary" , "username", "password"
		};
		DefaultTableModel modelTable;
		modelTable = new DefaultTableModel(header, 0);
		
		Vector<Employee> employees = (Vector<Employee>) EmployeeHandler.getInstance().getAllEmployees();
		
		for (Employee emp : employees) {
			Vector<Object> row = new Vector<>();
			row.add(emp.getEmployeeID());
			row.add(emp.getPositionNameById());
			row.add(emp.getName());
			row.add(emp.getStatus());
			row.add(emp.getSalary());
			row.add(emp.getUsername());
			row.add(emp.getBulletPassword());
			modelTable.addRow(row);
		}
		
		table.setModel(modelTable);
	}
	
	public int confirmationDialog(String dialog) {
		JFrame frame = new JFrame("Confirmation Dialog");
	    JPanel panel = new JPanel();
	    LayoutManager layout = new FlowLayout();  
	    panel.setLayout(layout);       

	    
	    int result = JOptionPane.showConfirmDialog(frame,"Are you sure you want "+dialog+" this product?", "Confirmation Dialog",
	               JOptionPane.YES_NO_OPTION,
	               JOptionPane.QUESTION_MESSAGE);
	    if(result == JOptionPane.YES_OPTION){
	    	return 1;
	    }else if (result == JOptionPane.NO_OPTION){
	    	return 0;
	    }

	    return 0;
	}

}
