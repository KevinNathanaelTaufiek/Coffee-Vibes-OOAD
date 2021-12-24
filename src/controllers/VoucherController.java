package controllers;

import models.Voucher;

import java.util.Vector;

public class VoucherController {
    private Voucher voucher;
    public static VoucherController voucherController=null;
    public VoucherController() {
        voucher = new Voucher();
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
        return voucher.generateVoucher(Integer.parseInt(discount));
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
}
