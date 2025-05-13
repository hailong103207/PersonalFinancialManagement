package com.example.quanlychitieucanhan;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class clsMucTieu {
    private Uri icon;
    private String TenMucTieu;
    private String Ngay;
    private String ThangNam;
    private String Gio;
    private String SoTien;
    private String PhanLoai, Loai;
    private String TenFile;

    public clsMucTieu(Uri icon, String tenMucTieu, String ngay, String thangNam, String gio, String soTien, String phanLoai, String loai, String tenFile) {
        this.icon = icon;
        TenMucTieu = tenMucTieu;
        Ngay = ngay;
        ThangNam = thangNam;
        Gio = gio;
        String sotien2 = String.valueOf(Math.round(Double.valueOf(soTien)));
        SoTien = sotien2;
        PhanLoai = phanLoai;
        Loai = loai;
        TenFile = tenFile;
    }

    public Uri getIcon() {
        return icon;
    }

    public String getTenFile() {
        return TenFile;
    }

    public void setTenFile(String tenFile) {
        TenFile = tenFile;
    }


    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public void setIcon(Uri icon) {
        this.icon = icon;
    }

    public String getPhanLoai() {
        return PhanLoai;
    }

    public void setPhanLoai(String phanLoai) {
        PhanLoai = phanLoai;
    }

    public String getTenMucTieu() {
        return TenMucTieu;
    }

    public void setTenMucTieu(String tenMucTieu) {
        TenMucTieu = tenMucTieu;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getThangNam() {
        return ThangNam;
    }

    public void setThangNam(String thangNam) {
        ThangNam = thangNam;
    }

    public String getGio() {
        return Gio;
    }

    public void setGio(String gio) {
        Gio = gio;
    }

    public String getSoTien() {
        return SoTien;
    }

    public void setSoTien(String soTien) {
        SoTien = soTien;
    }

    public static List<clsMucTieu> sort(List<clsMucTieu> clsMucTieuList) {
        List<clsMucTieu> clsMucTieudasapxep = new ArrayList<>();
        clsMucTieudasapxep.clear();
        int ngaymax = 0;
        for (clsMucTieu doituong : clsMucTieuList) {
            int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
            if (songay > ngaymax) {
                ngaymax = songay;
            }
        }
        int ngaymin = ngaymax;
        for (clsMucTieu doituong : clsMucTieuList) {
            int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
            if (songay < ngaymin) {
                ngaymin = songay;
            }
        }
        for (int i = ngaymax; i >= ngaymin; i--) {
            for (clsMucTieu doituong : clsMucTieuList) {
                int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                if (songay == i) {
                    clsMucTieudasapxep.add(doituong);
                }
            }
        }
        return clsMucTieudasapxep;
    }

    public static List<clsMucTieu> sort(List<clsMucTieu> clsMucTieuList, String ngaymins, String ngaymaxs) {
        List<clsMucTieu> clsMucTieudasapxep = new ArrayList<>();
        int ngaymin = 10000000;
        int ngaymax = 0;
        if (ngaymins.trim().equals("---")) {
            if (ngaymaxs.trim().equals("---")) { //CA 2 DEU KHONG
                return sort(clsMucTieuList);
            } else { //1 KHONG 1 CO
                ngaymax = Integer.parseInt(ngaymaxs.split("-")[2]) * 372 + Integer.parseInt(ngaymaxs.split("-")[1]) * 31 + Integer.parseInt(ngaymaxs.split("-")[0]);
                for (clsMucTieu doituong : clsMucTieuList) {
                    int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                    if (songay < ngaymin) {
                        ngaymin = songay;
                    }
                }
                for (int i = ngaymax; i >= ngaymin; i--) {
                    for (clsMucTieu doituong : clsMucTieuList) {
                        int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                        if (songay == i) {
                            clsMucTieudasapxep.add(doituong);
                        }
                    }
                }
                return clsMucTieudasapxep;
            }
        } else {
            ngaymin = Integer.parseInt(ngaymins.split("-")[2]) * 372 + Integer.parseInt(ngaymins.split("-")[1]) * 31 + Integer.parseInt(ngaymins.split("-")[0]);
            if (ngaymaxs.trim().equals("---")) { //1 CO 1 KHONG
                for (clsMucTieu doituong : clsMucTieuList) {
                    int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                    if (songay > ngaymax) {
                        ngaymax = songay;
                    }
                }
                for (int i = ngaymax; i >= ngaymin; i--) {
                    for (clsMucTieu doituong : clsMucTieuList) {
                        int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                        if (songay == i) {
                            clsMucTieudasapxep.add(doituong);
                        }
                    }
                }
                return sort(clsMucTieudasapxep); //return ham sort trong danh sach muc tieu da sap sep
            } else {//CA HAI CUNG CO
                ngaymax = Integer.parseInt(ngaymaxs.split("-")[2]) * 372 + Integer.parseInt(ngaymaxs.split("-")[1]) * 31 + Integer.parseInt(ngaymaxs.split("-")[0]);
                ngaymin = Integer.parseInt(ngaymins.split("-")[2]) * 372 + Integer.parseInt(ngaymins.split("-")[1]) * 31 + Integer.parseInt(ngaymins.split("-")[0]);
                if (ngaymax > ngaymin) {
                    int abc = Integer.parseInt(clsMucTieuList.get(0).getThangNam().split(" ")[1]) * 372 + Integer.parseInt(clsMucTieuList.get(0).getThangNam().split(" ")[0]) * 31 + Integer.parseInt(clsMucTieuList.get(0).getNgay());
                    for (int i = ngaymax; i >= ngaymin; i--) {
                        for (clsMucTieu doituong : clsMucTieuList) {
                            String ngay = doituong.getThangNam();
                            String thang = doituong.getNgay();
                            int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                            if (songay == i) {
                                clsMucTieudasapxep.add(doituong);
                            }
                        }
                    }
                    return clsMucTieudasapxep;//return cls muc tieu danh sach da sap sep
                } else {
                    clsMucTieudasapxep = new ArrayList<>();
                    return clsMucTieudasapxep;
                }
            }

        }

    }
}
