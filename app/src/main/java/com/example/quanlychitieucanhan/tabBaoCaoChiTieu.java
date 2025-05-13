package com.example.quanlychitieucanhan;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tabBaoCaoChiTieu extends Fragment {
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    BaoCaoTaiChinhActivity baoCaoTaiChinhActivity;
    ListView baocaoTaiChinh;
    BaoCaoThuChiTaiChinh_Adapter baoCaoThuChiTaiChinh_adapter;
    double tongsotien_chi;
    List<clsMucTieu> danhSachMucTieu = new ArrayList<>();

    public tabBaoCaoChiTieu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_baocaochitieu, container, false);
        AnhXa(v);
        try {
            LayDuongDanFilePhanLoai(baoCaoTaiChinhActivity.getTvTu().getText().toString().trim(), baoCaoTaiChinhActivity.getTvDen().getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SuKien();

        return v;
    }

    private void SuKien() {

    }

    public void LayDuongDanFilePhanLoai(String ngaymins, String ngaymaxs) throws ParseException {
        List<clsMucTieuPhanLoai> listmuctieuphanloai = new ArrayList<>();
        double bien_tongtienchi = 0;
        double TinhTongChi = 0;
        double bien_phantramchi = 0;
        List<PhanLoai> phanLoaiList = new ArrayList<>();
        danhSachMucTieu.clear();
        //B1: Muc đích : tạo đường dãn tới file phân loại cho hàm xử lý lần lượt sau đó truyền list clsPLMT vào
        //cho phần apdapter
        File[] DanhSachPhanLoaiChi = modun.ChiTieu.listFiles();//Lấy danh sách folder phân loại\
        for (File filePhanLoai : DanhSachPhanLoaiChi)
        {
            //filePhanLoai là folder Phân loại
            if (listmuctieuphanloai != null)
            {
            }
            List<clsMucTieu> clsMucTieuList1 = LayDuLieu_MucTieu_Chi(filePhanLoai, ngaymins, ngaymaxs);
            TinhTongChi += tongsotien_chi;//tính tổng dần của toàn bộ
            bien_tongtienchi = tongsotien_chi;
            File DataIcon = new File(modun.ChiTieu_Icon, filePhanLoai.getName().trim() + ".txt");
            Uri ImageData = Uri.parse(modun.readText(DataIcon).trim());
            //B3 : add vào lít clsPLMT
            PhanLoai doituong_PhanLoai = new PhanLoai(ImageData, filePhanLoai.getName().trim(), bien_tongtienchi, bien_phantramchi, "");
            phanLoaiList.add(doituong_PhanLoai);
            listmuctieuphanloai.add(new clsMucTieuPhanLoai(doituong_PhanLoai,clsMucTieuList1));
        }
        baoCaoTaiChinhActivity.getTvChi().setText((String.format("%,d", Long.parseLong(String.valueOf(Math.round(TinhTongChi))))).replace(".", ",").replace(",", ","));
        //TInh phan tram
        for (int i = 0; i < DanhSachPhanLoaiChi.length; i++) {
            bien_phantramchi = phanLoaiList.get(i).getTongTienChi() / TinhTongChi * 100;
            phanLoaiList.get(i).setPhanTramChi(modun.round(bien_phantramchi, 1));
            listmuctieuphanloai.get(i).setPhanLoai(phanLoaiList.get(i));
        }
        baoCaoThuChiTaiChinh_adapter = new BaoCaoThuChiTaiChinh_Adapter(listmuctieuphanloai, getContext(), R.layout.list_item_baocaochi);
        baocaoTaiChinh.setAdapter(baoCaoThuChiTaiChinh_adapter);
    }


    private void AnhXa(View v) {
        baoCaoTaiChinhActivity = (BaoCaoTaiChinhActivity) getActivity();
        baocaoTaiChinh = v.findViewById(R.id.listview_MucTieu);
    }

    private List<clsMucTieu> LayDuLieu_MucTieu_Chi(File filePhanLoai, String ngaymin, String ngaymax) throws ParseException {
        //B2: Lấy dữ liệu trong file phân loại sau đó lọc ra các mục tiêu khớp ngày tháng sau đó chèn vào Listclsmuctieu
        //ngoai ra con tra ve danhsach muctieu
        List<clsMucTieu> danhsachmuctieu = new ArrayList<>();
        File[] filesMucTieu = filePhanLoai.listFiles();//list muc tieu
        tongsotien_chi = 0;
        Date NgayMin = format.parse(ngaymin);
        Date NgayMax = format.parse(ngaymax);

        if (filesMucTieu != null) {
            for (File fileMucTieu : filesMucTieu) {
                //fileMUcTieu la tung file muc tieu
                String data = modun.readText(fileMucTieu);
                String[] mangData = data.split("@");
                String NgayThangNam = mangData[0];
                String Ngay = NgayThangNam.split("-")[0];
                String ThangNam = NgayThangNam.split("-")[1] + " " + NgayThangNam.split("-")[2];
                double sotien_Chi = Double.valueOf(mangData[2]);
                String Gio = mangData[3];
                String MucTieu = mangData[1];
                String MucTieuSuDung = mangData[4].trim();
                Date NgayMucTieu = format.parse(NgayThangNam);
                Uri uriImage = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.icon1);
                String test = filePhanLoai.getName().trim() + ".txt";
                if (MucTieuSuDung.trim().equals("thu")) {
                    File DuongDanIcon = new File(modun.ThuThap_Icon, filePhanLoai.getName().trim() + ".txt");
                    uriImage = Uri.parse(modun.readText(DuongDanIcon).trim());
                } else if (MucTieuSuDung.trim().equals("chi")) {
                    File DuongDanIcon = new File(modun.ChiTieu_Icon, filePhanLoai.getName().trim() + ".txt");
                    uriImage = Uri.parse(modun.readText(DuongDanIcon).trim());
                }
                if (!NgayMucTieu.after(NgayMax)  && !NgayMucTieu.before(NgayMin)) {
                    clsMucTieu doituong_MucTieu = new clsMucTieu(uriImage, MucTieu, Ngay, ThangNam, Gio, String.valueOf(sotien_Chi), MucTieuSuDung, "", fileMucTieu.getName());
                    tongsotien_chi = tongsotien_chi + sotien_Chi;
                    danhsachmuctieu.add(doituong_MucTieu);

                }
            }
        } else return new ArrayList<>();
        return danhsachmuctieu;
    }


}

