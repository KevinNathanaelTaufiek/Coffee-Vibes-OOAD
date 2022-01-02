package views;

import controllers.CartHandler;
import models.CartItem;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class CartManagementForm {
    private JFrame frame = new JFrame();
    
    private JTable table;
    private DefaultTableModel dtm;
    private JScrollPane scrollPane;
    
    private JPanel mainPanel,buttonPanel;
    private JButton checkOutButton,removeButton;
    
    public CartManagementForm() {
        setPanel();
        setFrame();
    }
    
    private void setPanel() {
        mainPanel=new JPanel(new BorderLayout());
        mainPanel.setBorder(new LineBorder(Color.WHITE,4));

        table=new JTable(dtm){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        table.setRowSelectionAllowed(false);
        loadData();
        scrollPane = new JScrollPane(table);

        mainPanel.add(scrollPane,BorderLayout.CENTER);
        setBottomBtn();
        frame.add(buttonPanel,BorderLayout.SOUTH);
        frame.add(mainPanel,BorderLayout.NORTH);
    }
    
    private void setBottomBtn(){
        buttonPanel=new JPanel();
        checkOutButton=new JButton("Checkout");
        removeButton=new JButton("Remove form Cart");
        
        
        removeButton.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				int confrim = confirmationDialog("remove");
				CartHandler cartHandler = CartHandler.getInstance();
				if(confrim == 1) {
					int row = table.getSelectedRow();
					int id = -1;
					try {
						id = (int) table.getValueAt(row, 0);
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(frame, "Please Select An Item First!");
						return;
					}
					boolean removed = cartHandler.removeProductFromCart(String.valueOf(id));
				
					if(removed) {
						JOptionPane.showMessageDialog(frame, "Success Remove Cart Item");
						loadData();
					}else {
						JOptionPane.showMessageDialog(frame, cartHandler.getErrorMessage());
					}
				}else {
					JOptionPane.showMessageDialog(frame, "Cart Item Removal Canceled!");
				}
			}
			
			
		});
        
        buttonPanel.add(checkOutButton);
        buttonPanel.add(removeButton);
    }
    
    private void loadData(){
        String[] headers={"Product ID","Product Name","Quantity"};
        dtm=new DefaultTableModel(headers,0);
        Vector<CartItem> cart= (Vector<CartItem>) CartHandler.getInstance().getCart();
        for(CartItem cartItem: cart){
            Vector<Object> row =new Vector<>();
            row.add(cartItem.getProduct().getProductID());
            row.add(cartItem.getProduct().getName());
            row.add(cartItem.getQuantity());
            dtm.addRow(row);
        }
        table.setModel(dtm);
    }

    public void setFrame(){
        frame.setSize(400, 600);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Cart Management");
    }
    
    public int confirmationDialog(String dialog) {
		JFrame frame = new JFrame("Confirmation Dialog");
	    JPanel panel = new JPanel();
	    LayoutManager layout = new FlowLayout();  
	    panel.setLayout(layout);       

	    
	    final JLabel label = new JLabel();
	    int result = JOptionPane.showConfirmDialog(frame,"Are you sure you want "+dialog+" this item?", "Confirmation Dialog",
	               JOptionPane.YES_NO_OPTION,
	               JOptionPane.QUESTION_MESSAGE);
	    if(result == JOptionPane.YES_OPTION){
	    	return 1;
	    }else if (result == JOptionPane.NO_OPTION){
	    	return 0;
	    }

	    panel.add(label);
	    frame.getContentPane().add(panel, BorderLayout.CENTER);    
	    frame.setSize(560, 200);      
	    frame.setLocationRelativeTo(null);  
	    frame.setVisible(true);
	    return 0;
	}

}
