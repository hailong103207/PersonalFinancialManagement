package com.example.quanlychitieucanhan;

import java.util.List;

public class clsMucTieuPhanLoai {
    PhanLoai phanLoai;
    List<clsMucTieu> clsMucTieuList;
    public clsMucTieuPhanLoai(PhanLoai phanLoai, List<clsMucTieu> clsMucTieuList){
        this.phanLoai = phanLoai;
        this.clsMucTieuList = clsMucTieuList;
    }

    public PhanLoai getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(PhanLoai phanLoai) {
        this.phanLoai = phanLoai;
    }

    public List<clsMucTieu> getClsMucTieuList() {
        return clsMucTieuList;
    }

    public void setClsMucTieuList(List<clsMucTieu> clsMucTieuList) {
        this.clsMucTieuList = clsMucTieuList;
    }
}
