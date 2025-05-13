package com.example.quanlychitieucanhan;

import android.net.Uri;

public class clsPhanLoaiBaoCao {
    private Uri imageID;
    private String PhanLoai;
    private String DauKy , CuoiKy, Kieu;
    private Double TongTienChi;
    private Double PhanTramChi;


    public clsPhanLoaiBaoCao(Uri imageID, String PhanLoai, Double TongTienChi,Double PhanTramChi,String dauKy,String cuoiKy,String kieu){
        this.imageID = imageID;
        this.PhanLoai = PhanLoai;
        this.TongTienChi = TongTienChi;
        this.PhanTramChi = PhanTramChi;
        this.DauKy = dauKy;
        this.CuoiKy = cuoiKy;
        this.Kieu = kieu;

    }

    public Uri getImageID() {
        return imageID;
    }

    public void setImageID(Uri imageID) {
        this.imageID = imageID;
    }

    public String getPhanLoai() {
        return PhanLoai;
    }

    public void setPhanLoai(String phanLoai) {
        PhanLoai = phanLoai;
    }

    public String getDauKy() {
        return DauKy;
    }

    public void setDauKy(String dauKy) {
        DauKy = dauKy;
    }

    public String getCuoiKy() {
        return CuoiKy;
    }

    public void setCuoiKy(String cuoiKy) {
        CuoiKy = cuoiKy;
    }

    public String getKieu() {
        return Kieu;
    }

    public void setKieu(String kieu) {
        Kieu = kieu;
    }

    public Double getTongTienChi() {
        return TongTienChi;
    }

    public void setTongTienChi(Double tongTienChi) {
        TongTienChi = tongTienChi;
    }

    public Double getPhanTramChi() {
        return PhanTramChi;
    }

    public void setPhanTramChi(Double phanTramChi) {
        PhanTramChi = phanTramChi;
    }
}
