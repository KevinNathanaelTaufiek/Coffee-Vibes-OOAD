package models;

import Connector.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

public class Voucher {
    private Integer voucherID;
    private Integer discount;
    private String status;
    private String table="voucher";
    private Connection con= Connector.connect();

    public Voucher(Integer voucherID, Integer discount, String status) {
        this.voucherID = voucherID;
        this.discount = discount;
        this.status = status;
    }
    public Integer getVoucherID() {
        return voucherID;
    }
    public void setVoucherID(Integer voucherID) {
        this.voucherID = voucherID;
    }
    public Integer getDiscount() {
        return discount;
    }
    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Voucher generateVoucher(Integer discount){
        Random random=new Random();
        int voucher=random.nextInt(999999);
        String query="Insert Into "+this.table+" Values(?,?,?)";
        try {
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1,voucher);
            ps.setInt(2,discount);
            ps.setString(3,"Valid");
            if(ps.executeUpdate()==1)return new Voucher(voucher,discount,"Valid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Vector<Voucher> getAllVouchers(){
        String query="Select * from "+this.table;
        Vector<Voucher> vouchers=new Vector<>();
        try {
            ResultSet rs=con.createStatement().executeQuery(query);
            while(rs.next()){
                Voucher voucher= map(rs);
                vouchers.add(voucher);
            }
            return vouchers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Voucher getVoucher(Integer voucherID){
        String query="Select * from "+this.table+" Where voucherID= "+voucherID;
        try {
            ResultSet rs=con.createStatement().executeQuery(query);
            return map(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean deleteVoucher(Integer voucherID){
        String query="Delete from "+this.table+" Where voucherID= ?";
        try {
            PreparedStatement ps= con.prepareStatement(query);
            ps.setInt(1,voucherID);
            return ps.executeUpdate()==1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Voucher map(ResultSet rs){
        try{
            int voucherID= rs.getInt("voucherID");
            int discount= rs.getInt("discount");
            String status= rs.getString("status");
            return new Voucher(voucherID,discount,status);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
