package com.example.quanlychitieucanhan;

import android.net.Uri;
import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class clsTaiSan {
    private String LangPhat;
    private String ThiTruong;
    private Uri icon;
    private String KhauHaoTrongKy = "0";
    private String TenMucTieu;
    private String ThangNam;
    private String SoTien;
    private String DuKien;
    private String Loai;
    private String damat;
    private String MatTrongThang;
    private String TenFile;
    private String NgayThangNam;


    public clsTaiSan(Uri icon, String tenMucTieu, String thangNam, String soTien, String duKien, String loai, String tenFile, String ngayThangNam, String thiTruong, String langPhat) {
        this.icon = icon;
        TenMucTieu = tenMucTieu;
        ThangNam = thangNam;
        SoTien = soTien;
        DuKien = duKien;
        Loai = loai;
        NgayThangNam = ngayThangNam;
        TenFile = tenFile;
        ThiTruong = thiTruong;
        LangPhat = langPhat;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public String getNgayThangNam() {
        return NgayThangNam;
    }

    public void setNgayThangNam(String ngayThangNam) {
        NgayThangNam = ngayThangNam;
    }

    public String getThiTruong2() {
        return ThiTruong;
    }

    //arcgis
    public String getLangPhat() {
        return LangPhat;
    }

    public void setLangPhat(String langPhat) {
        LangPhat = langPhat;
    }

    public void setThiTruong(String thiTruong) {
        ThiTruong = thiTruong;
    }


    public String getTenFile() {
        return TenFile;
    }

    public void setTenFile(String tenFile) {
        TenFile = tenFile;
    }

    public String getDuKien() {
        return DuKien;
    }

    public String getKhauHaoTrongKy() {
        return KhauHaoTrongKy;
    }

    public void setKhauHaoTrongKy(String khauHaoTrongKy) {
        KhauHaoTrongKy = khauHaoTrongKy;
    }

    public void setDuKien(String duKien) {
        DuKien = duKien;
    }


    public Uri getIcon() {
        return icon;
    }

    public void setIcon(Uri icon) {
        this.icon = icon;
    }


    public String getTenMucTieu() {

        return TenMucTieu;
    }

    public void setTenMucTieu(String tenMucTieu) {
        TenMucTieu = tenMucTieu;
    }

    public String getThangNam() {
        return ThangNam;
    }

    public void setThangNam(String thangNam) {
        ThangNam = thangNam;
    }

    public String getSoTien() {
        return SoTien;
    }

    public void setSoTien(String soTien) {
        SoTien = soTien;
    }

    public String getHienTai() {
        if (ThiTruong.trim().equals("auto")) {
            int bien_HienTai = Integer.parseInt(SoTien) - Integer.parseInt(getDaMat());
            if (bien_HienTai <= 0) bien_HienTai = 0;
            return String.valueOf(bien_HienTai);
        } else return ThiTruong;


    }

    public String getThiTruong() {

        int bien_HienTai = Integer.parseInt(getHienTai()) - Integer.parseInt(SoTien);
        return String.valueOf(bien_HienTai);
    }

    public String getThangmat() {

        int bien_MatTrongThang = Integer.parseInt(SoTien) / Integer.parseInt(DuKien) / 12;
//        bien_MatTrongThang = modun.round(bien_MatTrongThang,1);
        MatTrongThang = String.valueOf(bien_MatTrongThang);
        return MatTrongThang;
    }

    public String getMatGia() {
        double bien_langphatthang = Double.valueOf(getLangPhat().trim()) / 12;
        double bien_matmoithang = Integer.parseInt(SoTien) * bien_langphatthang / 100 * -1;
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String Ngay2 = sdf.format(new Date());
        String ThangNam2 = Ngay2.split("-")[1] + " " + Ngay2.split("-")[2];

        int bien_hientai = Integer.parseInt(ThangNam2.split(" ")[1]) * 12 + Integer.parseInt(ThangNam2.split(" ")[0]);
        int bien_thoidiemmua = Integer.parseInt(ThangNam.split(" ")[1]) * 12 + Integer.parseInt(ThangNam.split(" ")[0]);
        double bien_damat = (bien_hientai - bien_thoidiemmua) * bien_matmoithang;
        return String.valueOf(Integer.parseInt(String.valueOf(Math.round(bien_damat))));
    }

    public String getDaMat() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String Ngay2 = sdf.format(new Date());
        String ThangNam2 = Ngay2.split("-")[1] + " " + Ngay2.split("-")[2];

        int bien_hientai = Integer.parseInt(ThangNam2.split(" ")[1]) * 12 + Integer.parseInt(ThangNam2.split(" ")[0]);
        int bien_thoidiemmua = Integer.parseInt(ThangNam.split(" ")[1]) * 12 + Integer.parseInt(ThangNam.split(" ")[0]);
        int bien_damat = (bien_hientai - bien_thoidiemmua) * Integer.parseInt(getThangmat());
//        bien_damat = modun.round(bien_damat,1);
        damat = String.valueOf(bien_damat);
        return damat;
    }
    public String getDaMat(int SoNgayDaQua) {


      int bien_damat = (SoNgayDaQua /30) * Integer.parseInt(getThangmat());
//        bien_damat = modun.round(bien_damat,1);
        damat = String.valueOf((bien_damat));
        return damat;
    }
    public String getDauKy(String ngay) {

        String ThangNam2 = ngay.split("-")[1] + " " + ngay.split("-")[2];

        int bien_hientai = Integer.parseInt(ThangNam2.split(" ")[1]) * 12 + Integer.parseInt(ThangNam2.split(" ")[0]);
        int bien_thoidiemmua = Integer.parseInt(ThangNam.split(" ")[1]) * 12 + Integer.parseInt(ThangNam.split(" ")[0]);
        int bien_damat = (bien_hientai - bien_thoidiemmua) * Integer.parseInt(getThangmat());
//        bien_damat = modun.round(bien_damat,1);
        damat = String.valueOf(bien_damat);
        return damat;
    }

    public String getTongMat() {
        int KetQua = Integer.parseInt(getThiTruong().trim()) + Integer.parseInt(getMatGia().trim());
        return String.valueOf(Math.round(KetQua));
    }

    public static List<clsTaiSan> sort1(List<clsTaiSan> clsTaiSanList) {
        List<clsTaiSan> clsTaiSandasapxep = new ArrayList<>();
        clsTaiSandasapxep.clear();
        int ngaymax = 0;
        for (clsTaiSan doituong : clsTaiSanList) {
            int songay = Integer.parseInt(doituong.getNgayThangNam().split("-")[2]) * 372 + Integer.parseInt(doituong.getNgayThangNam().split("-")[1]) * 31 + Integer.parseInt(doituong.getNgayThangNam().split("-")[0]);
            if (songay > ngaymax) {
                ngaymax = songay;
            }
        }
        int ngaymin = ngaymax;
        for (clsTaiSan doituong : clsTaiSanList) {
            int songay = Integer.parseInt(doituong.getNgayThangNam().split("-")[2]) * 372 + Integer.parseInt(doituong.getNgayThangNam().split("-")[1]) * 31 + Integer.parseInt(doituong.getNgayThangNam().split("-")[0]);
            if (songay < ngaymin) {
                ngaymin = songay;
            }
        }
        for (int i = ngaymax; i >= ngaymin; i--) {
            for (clsTaiSan doituong : clsTaiSanList) {
                int songay = Integer.parseInt(doituong.getNgayThangNam().split("-")[2]) * 372 + Integer.parseInt(doituong.getNgayThangNam().split("-")[1]) * 31 + Integer.parseInt(doituong.getNgayThangNam().split("-")[0]);
                if (songay == i) {
                    clsTaiSandasapxep.add(doituong);
                }
            }
        }
        return clsTaiSandasapxep;
    }

    public static List<clsTaiSan> sort(List<clsTaiSan> clsTaiSanList, String ngaymins, String ngaymaxs) {
        List<clsTaiSan> clsTaiSandasapxep = new ArrayList<>();
        int ngaymin = 10000000;
        int ngaymax = 0;
        if (ngaymins.trim().equals("---")) {
            if (ngaymaxs.trim().equals("---")) { //CA 2 DEU KHONG
                return clsTaiSanList;
            } else { //1 KHONG 1 CO
                ngaymax = Integer.parseInt(ngaymaxs.split("-")[2]) * 372 + Integer.parseInt(ngaymaxs.split("-")[1]) * 31 + Integer.parseInt(ngaymaxs.split("-")[0]);

                for (clsTaiSan doituong : clsTaiSanList) {
                    int songay = Integer.parseInt(doituong.getNgayThangNam().split("-")[2]) * 372 + Integer.parseInt(doituong.getNgayThangNam().split(" ")[1]) * 31 + Integer.parseInt(doituong.getNgayThangNam().split("-")[0]);
                    if (songay <= ngaymax) {
                        clsTaiSandasapxep.add(doituong);
                    }
                }

                return clsTaiSandasapxep;
            }
        } else {
            ngaymin = Integer.parseInt(ngaymins.split("-")[2]) * 372 + Integer.parseInt(ngaymins.split("-")[1]) * 31 + Integer.parseInt(ngaymins.split("-")[0]);
            if (ngaymaxs.trim().equals("---")) { //1 CO 1 KHONG
                for (clsTaiSan doituong : clsTaiSanList) {
                    int songay = Integer.parseInt(doituong.getNgayThangNam().split("-")[2]) * 372 + Integer.parseInt(doituong.getNgayThangNam().split("-")[1]) * 31 + Integer.parseInt(doituong.getNgayThangNam().split("-")[0]);
                    if (songay >= ngaymin) {
                        clsTaiSandasapxep.add(doituong);
                    }
                }

                return clsTaiSandasapxep;
            } else {//CA HAI CUNG CO
                ngaymax = Integer.parseInt(ngaymaxs.split("-")[2]) * 372 + Integer.parseInt(ngaymaxs.split("-")[1]) * 31 + Integer.parseInt(ngaymaxs.split("-")[0]);
                ngaymin = Integer.parseInt(ngaymins.split("-")[2]) * 372 + Integer.parseInt(ngaymins.split("-")[1]) * 31 + Integer.parseInt(ngaymins.split("-")[0]);
                if (ngaymax > ngaymin) {
                    for (clsTaiSan doituong : clsTaiSanList) {
                        int songay = Integer.parseInt(doituong.getNgayThangNam().split("-")[2]) * 372 + Integer.parseInt(doituong.getNgayThangNam().split(" ")[1]) * 31 + Integer.parseInt(doituong.getNgayThangNam().split("-")[0]);
                        if (songay <= ngaymax && songay >= ngaymin) {
                            clsTaiSandasapxep.add(doituong);
                        }
                    }

                    return clsTaiSandasapxep;
                } else {
                    clsTaiSandasapxep = new ArrayList<>();
                    return clsTaiSandasapxep;
                }
            }

        }

    }

    public String getMoiThangMat() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String Ngay2 = sdf.format(new Date());
        String ThangNam2 = Ngay2.split("-")[1] + " " + Ngay2.split("-")[2];
        int bien_hientai = Integer.parseInt(ThangNam2.split(" ")[1]) * 12 + Integer.parseInt(ThangNam2.split(" ")[0]);
        int bien_thoidiemmua = Integer.valueOf(ThangNam.split(" ")[1]) * 12 + Integer.parseInt(ThangNam.split(" ")[0]);
        int bien_ketqua = 0;
        try {
            bien_ketqua = Math.round(Integer.parseInt(getTongMat().trim()) / Integer.parseInt(String.valueOf(bien_hientai - bien_thoidiemmua)));
        } catch (Exception e) {
            bien_ketqua = 0;
        }
        return String.valueOf(bien_ketqua);
    }
}