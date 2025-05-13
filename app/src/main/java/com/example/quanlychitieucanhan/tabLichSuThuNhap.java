package com.example.quanlychitieucanhan;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tabLichSuThuNhap extends Fragment {
    ListView listview_MucTieu;
    List<clsMucTieu> danhsach_MucTieu = new ArrayList<>();
    MucTieu_Adapter mucTieu_adapter;
    SearchView search_bar;
    LichSuActivity lichSuActivity;

    public tabLichSuThuNhap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_lich_su_thu_thap, container, false);
        AnhXa(v);
        LayDuongDanFile(lichSuActivity.tvTu.getText().toString().trim(),lichSuActivity.tvDen.getText().toString().trim());
        return v;
    }

    public void LayDuongDanFile(String ngaymins, String ngaymaxs) {
        danhsach_MucTieu.clear();
        File Template = modun.ThuNhap;
        File[] DanhsachPhanloaiDaTao = Template.listFiles();
        /**LẤY TỔNG SÓ SỐ ĐÃ CHI THEO TỪNG PHÂN LOẠI*/
        for (File f:DanhsachPhanloaiDaTao)
        {
            LayDuLieu_MucTieu_Chi(f);
        }
        danhsach_MucTieu = clsMucTieu.sort(danhsach_MucTieu);
        mucTieu_adapter = new MucTieu_Adapter(clsMucTieu.sort(danhsach_MucTieu), getContext(), R.layout.layout_item_muctieu);
        listview_MucTieu.setAdapter(mucTieu_adapter);
    }

    private void AnhXa(View v) {
        listview_MucTieu = v.findViewById(R.id.listview_MucTieu);
        lichSuActivity = (LichSuActivity) getActivity();
    }

    private void LayDuLieu_MucTieu_Chi(File duongDanPhanLoai) {
        double tongsotien_chi = 0;
        double bien_PhanTramChi = 0;
        if (duongDanPhanLoai.isDirectory()) { /**KIỂM TRA CÓ PHẢI LÀ THƯ MỤC HAY KHÔNG*/
            File[] DanhsachMucTieuDaTao = duongDanPhanLoai.listFiles();/**lấy danh sách mục tiêu chi*/
            for (File f : DanhsachMucTieuDaTao) {
                String data = modun.readText(f);
                String[] mangData = data.split("@");
                String NgayThangNam = mangData[0];
                String Ngay = NgayThangNam.split("-")[0];
                String ThangNam = NgayThangNam.split("-")[1] + " " + NgayThangNam.split("-")[2];
                double sotien_Chi =Double.valueOf(mangData[2]);
                String Gio = mangData[3];
                String MucTieu = mangData[1];
                String MucTieuSuDung = mangData[4];
                tongsotien_chi = tongsotien_chi + sotien_Chi;
                Uri uriImage = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.icon1);
                if (MucTieuSuDung.trim().equals("thu")){
                    File DuongDanIcon = new File(modun.ThuThap_Icon,duongDanPhanLoai.getName().trim()+".txt");
                    uriImage = Uri.parse(modun.readText(DuongDanIcon).trim());
                }else if (MucTieuSuDung.trim().equals("chi")){
                    File DuongDanIcon = new File(modun.ChiTieu_Icon,duongDanPhanLoai.getName().trim()+".txt");
                    uriImage = Uri.parse(modun.readText(DuongDanIcon).trim());
                }
                clsMucTieu doituong_MucTieu = new clsMucTieu(uriImage, MucTieu, Ngay, ThangNam, Gio, String.valueOf(sotien_Chi), "chi",duongDanPhanLoai.getName().trim(),f.getName());
                danhsach_MucTieu.add(doituong_MucTieu);

            }
        }

    }
}
