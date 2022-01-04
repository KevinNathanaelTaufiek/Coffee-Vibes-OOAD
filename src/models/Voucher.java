package models;


import java.sql.*;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import connector.Connector;

public class Voucher {
    private Integer voucherID;
    private Integer discount;
    private String status;
	private Connector con= Connector.connect();
    private String table="voucher";

    public Voucher(){}
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
        boolean cek=false;
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int voucher=0;
        while (!cek){
            voucher=rand.nextInt(100000,999999);
            if(getVoucher(voucher)==null)cek=true;
        }
        String query="Insert Into "+this.table+" Values(?,?,?)";
        try {
            PreparedStatement ps= con.preparedStatement(query);
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
            ResultSet rs= con.executeQuery(query);
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
            ResultSet rs=Connector.connect().executeQuery(query);
            if(rs.next()==false) return null;
            return map(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Integer useVoucher(Integer voucherID,Integer totalPrice){
        String query="Select * from "+this.table+" Where voucherID= "+voucherID +" and status = 'Valid'";
        try {
            ResultSet rs=Connector.connect().executeQuery(query);
            if(rs.next()==false) return 0;
            return totalPrice-((map(rs).getDiscount()/100)*totalPrice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean deleteVoucher(Integer voucherID){
    	String query = String.format("UPDATE %s SET status = 'InValid' WHERE voucherID = ?",this.table);
		PreparedStatement ps = con.preparedStatement(query);
		try {
			ps.setInt(1, voucherID);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    }
    public String deleteVoucherById(Integer voucherID){
        String query = String.format("Delete from %s WHERE voucherID = ? and status='Valid'",this.table);
        PreparedStatement ps = con.preparedStatement(query);
        try {
            ps.setInt(1, voucherID);
            if(ps.executeUpdate() == 1){
                return "Success";
            }else{
                return "Voucher not Found or vouchers in use";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error";
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
