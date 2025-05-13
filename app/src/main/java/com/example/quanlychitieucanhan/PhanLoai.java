package com.example.quanlychitieucanhan;

import android.net.Uri;

public class PhanLoai {
    private Uri imageID;
    private String PhanLoai;
    private Double TongTienChi;
    private Double PhanTramChi;
    private String Kieu;


    public PhanLoai(Uri imageID, String phanLoai, Double tongTienChi, Double phanTramChi, String Kieu) {
        this.imageID = imageID;
        PhanLoai = phanLoai;
        TongTienChi = tongTienChi;
        this.Kieu = Kieu;
        PhanTramChi = phanTramChi;
    }

    public String getKieu() {
        return Kieu;
    }

    public void setKieu(String kieu) {
        Kieu = kieu;
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
