package controllers;

import models.Voucher;
import views.VoucherView;

import java.util.Vector;

public class VoucherController {
    private Voucher voucher;
    public static VoucherController voucherController=null;
    
    
    public VoucherController() {
        voucher = new Voucher();
    }
    
    public static void VoucherView() {
		new VoucherView(); 
	}
    
    public static VoucherController getInstance() {
        if(voucherController == null) {
            voucherController = new VoucherController();
        }
        return voucherController;
    }

    public void viewVoucherManagementForm(){

    }
    public Vector<Voucher> getAllVouchers(){
        return voucher.getAllVouchers();
    }
    public Voucher insertVoucher(String discount){
        if(discount.equalsIgnoreCase("")||discount==null){
            return null;
        }
        int disc = -1;
        try {
        	disc = Integer.parseInt(discount);
		} catch (Exception e) {
			return null;
		}
        return voucher.generateVoucher(disc);
    }
    public boolean deleteVoucher(String voucherID){
        if(voucherID.equalsIgnoreCase("")||voucherID==null){
            return false;
        }
        return voucher.deleteVoucher(Integer.parseInt(voucherID));
    }
    public Voucher getVoucher(Integer voucherID){
        return voucher.getVoucher(voucherID);
    }

    public String cekInputGenerate(String input){
        int inputInt=-1;

        if((input != null && ("".equals(input)))||input==null){
            return "Input can't be empty";
        }else{
            try{
                inputInt=Integer.parseInt(input);
                if (inputInt<1||inputInt>100){
                    return "Discount between 1-100";
                }else{
                    return "success";
                }
            }catch (Exception e) {
                return "Input Must be Integer";
            }
        }
    }
    public String cekInputDelete(String input){
        int inputInt=-1;
        if((input != null && ("".equals(input)))||input.equals(null)){
            return "Input can't be empty";
        }else{
            try{
                inputInt=Integer.parseInt(input);
                return "success";
            }catch (Exception e) {
                return "Input Must be Integer";
            }
        }
    }
}
