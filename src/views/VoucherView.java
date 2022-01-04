package views;

import controllers.VoucherHandler;
import models.Voucher;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.Vector;

public class VoucherView {
    private JFrame frame = new JFrame("Coffee Vibes");
    private JTable voucherTable;
    private DefaultTableModel dtm;
    private JPanel mainPanel,buttonPanel;
    private JScrollPane scrollPane;
    private JButton generateButton,deleteButton;
    
    public VoucherView() {
        setPanel();
        setFrame();
    }
    
    private void setPanel() {
        mainPanel=new JPanel(new BorderLayout());
        mainPanel.setBorder(new LineBorder(Color.WHITE,4));

        voucherTable=new JTable(dtm){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        voucherTable.setRowSelectionAllowed(false);
        getData();
        scrollPane = new JScrollPane(voucherTable);

        mainPanel.add(scrollPane,BorderLayout.CENTER);
        setBottomBtn();
        frame.add(buttonPanel,BorderLayout.SOUTH);
        frame.add(mainPanel,BorderLayout.CENTER);
    }
    private void setBottomBtn(){
        buttonPanel=new JPanel();
        generateButton=new JButton("Generate Voucher");
        deleteButton=new JButton("Delete Voucher");
        generateButton.addActionListener(e -> {
            String discount="";
            discount = JOptionPane.showInputDialog("Input Discount");
            VoucherHandler voucherController= VoucherHandler.getInstance();
            String msg=voucherController.cekInputGenerate(discount);
            if(msg.equals("success")){
                Voucher voucher=voucherController.insertVoucher(discount);
                if(voucher==null){
                    JOptionPane.showMessageDialog(null, "Error" ,"Error",JOptionPane.YES_OPTION);
                }else{
                    JOptionPane.showMessageDialog(null, "Voucher generate with Voucher ID : " +voucher.getVoucherID(),"Success",JOptionPane.INFORMATION_MESSAGE);
                    getData();
                }
            }else {
                JOptionPane.showMessageDialog(null, msg ,"Error",JOptionPane.YES_OPTION);
            }
        });
        deleteButton.addActionListener(e -> {
            String voucherId="";
            voucherId = JOptionPane.showInputDialog("Input Voucher Id");
            VoucherHandler voucherController=VoucherHandler.getInstance();
            String id=voucherId;
            String msg=voucherController.cekInputDelete(voucherId);
            if(msg.equals("success")){
                boolean status=voucherController.deleteVoucher(id);
                if(status){
                    JOptionPane.showMessageDialog(null, "Voucher delete with Voucher ID : " +id,"Success",JOptionPane.INFORMATION_MESSAGE);
                    getData();
                }else{
                    JOptionPane.showMessageDialog(null, "Id : "+id+" Not Found or Error Delete","Error",JOptionPane.YES_OPTION);
                }
            }else {
                JOptionPane.showMessageDialog(null, msg ,"Error",JOptionPane.YES_OPTION);
            }

        });
        buttonPanel.add(generateButton);
        buttonPanel.add(deleteButton);
    }
    private void getData(){
        String[] headers={"Voucher Id","Discount","Status"};
        dtm=new DefaultTableModel(headers,0);
        Vector<Voucher> vouchers= VoucherHandler.getInstance().getAllVouchers();
        for(Voucher voucher: vouchers){
            Vector<Object> row =new Vector<>();
            row.add(voucher.getVoucherID());
            row.add(voucher.getDiscount()+" %");
            row.add(voucher.getStatus());
            dtm.addRow(row);
        }
        voucherTable.setModel(dtm);
    }

    public void setFrame(){
        frame.setSize(400, 600);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Manage Voucher");
    }

}
