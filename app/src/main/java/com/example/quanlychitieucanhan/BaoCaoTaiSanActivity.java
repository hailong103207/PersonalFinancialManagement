package com.example.quanlychitieucanhan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BaoCaoTaiSanActivity extends AppCompatActivity {
    ListView listview_MucTieu;
    TextView tvPhanLoai, tvTongTaiSan, tvDaMat, tvTrongThang, tvGiaTriHienTai,tvBienDongTrongKy;
    List<clsTaiSan> danhsach_MucTieu = new ArrayList<>();
    TaiSan_Adapter mucTieu_adapter;
    SearchView search_bar;
    TextView tvTu, tvDen;
    LinearLayout lnCuoiKyBaoCao;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    ViewPager viewPager;
    TabTaiSan tabTaiSan;
    int bien_tongtaisan = 0;
    int bien_trongthang = 0;
    int bien_gthientai = 0;
    int tongdauky = 0;
    int tongcuoiky = 0;
    int bien_damat = 0;
    int mMonth, mYear, mDay;
    String ngaymin, ngaymax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taisanbaocao);
        AnhXa();
        try {
            NhanBien();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SuKien();
    }

    private void SuKien() {
        listview_MucTieu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BaoCaoTaiSanActivity.this, danhsach_MucTieu.get(position).getTenFile(), Toast.LENGTH_SHORT).show();
                DialogSuaKhoanTaiSan(position);

            }
        });
        listview_MucTieu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DiaLogSuaXoa(position);
                return true;
            }
        });
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(BaoCaoTaiSanActivity.this, "Chức năng đang phát triển", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void DiaLogSuaXoa(final int position) {
        final Dialog dialogSuaXoa = new Dialog(BaoCaoTaiSanActivity.this);
        dialogSuaXoa.setContentView(R.layout.dialog_lichsumenu);
        Window window = dialogSuaXoa.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogSuaXoa.show();

        /** ÁNH XẠ */
        final Button btnXoa = dialogSuaXoa.findViewById(R.id.btnXoa);
        final Button btnSua = dialogSuaXoa.findViewById(R.id.btnSua);
        final ImageButton btnThoat = dialogSuaXoa.findViewById(R.id.btnThoat);

        /** SỰ KIỆN */
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuaXoa.dismiss();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSuaKhoanTaiSan(position);
                dialogSuaXoa.dismiss();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                danhsach_MucTieu.get(position).getTenFile();
                AlertDialog.Builder builder = new AlertDialog.Builder(BaoCaoTaiSanActivity.this);
                builder.setTitle("Bạn muốn xóa thư mục này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File DataAppThuChi = new File(modun.TaiSan, danhsach_MucTieu.get(position).getLoai());
                        File DataFile = new File(DataAppThuChi, danhsach_MucTieu.get(position).getTenFile());
                        DataFile.delete();
                        try {
                            LayDuLieu_MucTieu_Chi(DataAppThuChi, ngaymin, ngaymax);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            NhanBien();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (!DataFile.exists()) {

                            dialogSuaXoa.dismiss();
                            try {
                                LayDuLieu_MucTieu_Chi(DataAppThuChi, ngaymin, ngaymax);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                NhanBien();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(BaoCaoTaiSanActivity.this, "Bạn đã xóa thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BaoCaoTaiSanActivity.this, "Bạn đã xóa không thành công.", Toast.LENGTH_SHORT).show();
                            dialogSuaXoa.dismiss();
                        }
                        try {
                            LayDuLieu_MucTieu_Chi(DataAppThuChi, ngaymin, ngaymax);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            NhanBien();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(BaoCaoTaiSanActivity.this, MainActivity.class);
                        intent.putExtra("HanhDong", "truyen");
                        Intent intent2 = getIntent();
                        intent.putExtra("DuongDan", intent2.getStringExtra("DuongDan"));
                        intent.putExtra("PhanLoai", intent2.getStringExtra("PhanLoai"));
                        Toast.makeText(BaoCaoTaiSanActivity.this, "`Báo cáo tài chính từ " + intent.getStringExtra("Tu") + " đến " + intent.getStringExtra("Den"), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        /*danhsach_HocSinh.remove(position);
                        hocSinh_adapter = new HocSinh_Adapter(danhsach_HocSinh,MainActivity.this,R.layout.my_layout_item);
                        listview_HocSinh.setAdapter(hocSinh_adapter);*/

                    }
                });
                builder.setNegativeButton("không", null);
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();

            }
        });
    }

    private void DialogSuaKhoanTaiSan(final int i) {
        final Dialog dialogSuaTaiSan = new Dialog(BaoCaoTaiSanActivity.this);
        dialogSuaTaiSan.setContentView(R.layout.dialog_themkhoantaisan);
        dialogSuaTaiSan.show();

        /** ÁNH XẠ */
        final Button btnNgayMua = dialogSuaTaiSan.findViewById(R.id.btn_NgayChi);
        final AutoCompleteTextView edtMucTieuChi = dialogSuaTaiSan.findViewById(R.id.edt_MucTieuChi);
        final AutoCompleteTextView edtSoTienChi = dialogSuaTaiSan.findViewById(R.id.edt_SoTienChi);
        final AutoCompleteTextView edtThoiHanSuDung = dialogSuaTaiSan.findViewById(R.id.edt_ThoiHanSuDung);
        final AutoCompleteTextView edtGiaTriHienTai = dialogSuaTaiSan.findViewById(R.id.edt_GiaTriHienTai);
        final AutoCompleteTextView edtLangPhat = dialogSuaTaiSan.findViewById(R.id.edt_LangPhat);

        final Button btnLuuThongSo = dialogSuaTaiSan.findViewById(R.id.btnLuuThongSo);
        final Button btnThoat = dialogSuaTaiSan.findViewById(R.id.btnThoat);
        final TextView tvTaiSan = dialogSuaTaiSan.findViewById(R.id.tvTaiSan);
        final TextView tvKieu = dialogSuaTaiSan.findViewById(R.id.tvKieu);
        String PhanLoai = danhsach_MucTieu.get(i).getLoai().trim();
        String Thuoctinh = "chưa rõ";
        File ThuocTinh = new File(modun.TaiSan_ThuocTinh, danhsach_MucTieu.get(i).getLoai().trim() + ".txt");
        String strThuocTinh = modun.readText(ThuocTinh).trim();
        switch (strThuocTinh){
            case "tsdt":
                Thuoctinh = "TS đầu tư";
                break;
            case "tstd":
                Thuoctinh = "TS tiêu dùng";
                break;
            case "tstm":
                Thuoctinh = "TS tiền mặt";
                break;
            case "tsno":
                Thuoctinh = "vay, nợ";
                break;
        }
        tvTaiSan.setText(PhanLoai);
        tvKieu.setText(Thuoctinh);
        modun.setPopUp(BaoCaoTaiSanActivity.this, edtSoTienChi, modun.listSoTien);
        /** SET TEXT */
        btnNgayMua.setText(danhsach_MucTieu.get(i).getNgayThangNam());
        edtMucTieuChi.setText(danhsach_MucTieu.get(i).getTenMucTieu());
        edtSoTienChi.setText(danhsach_MucTieu.get(i).getSoTien());
        edtThoiHanSuDung.setText(danhsach_MucTieu.get(i).getDuKien());
        edtLangPhat.setText(danhsach_MucTieu.get(i).getLangPhat().trim());
        if (!danhsach_MucTieu.get(i).getThiTruong2().trim().equals("auto"))
            edtGiaTriHienTai.setText(danhsach_MucTieu.get(i).getThiTruong2());
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuaTaiSan.dismiss();
            }
        });
        btnNgayMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(BaoCaoTaiSanActivity.this, R.style.MyDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                btnNgayMua.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        /** SET HINT*/

        edtSoTienChi.setHint(danhsach_MucTieu.get(i).getSoTien());
        edtThoiHanSuDung.setHint(danhsach_MucTieu.get(i).getDuKien() + "năm");
        edtMucTieuChi.setHint(danhsach_MucTieu.get(i).getTenMucTieu());
        edtGiaTriHienTai.setHint("không viết để theo mặc định");
        edtLangPhat.setHint("% lạng phát theo năm");

        btnLuuThongSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnNgayMua.getText().toString().trim().equals(""))
                    btnNgayMua.setText(btnNgayMua.getHint().toString());
                if (edtSoTienChi.getText().toString().trim().equals(""))
                    edtSoTienChi.setText(edtSoTienChi.getHint().toString());
                if (edtMucTieuChi.getText().toString().trim().equals(""))
                    edtMucTieuChi.setText(edtMucTieuChi.getHint().toString());
                if (edtThoiHanSuDung.getText().toString().trim().equals(""))
                    edtMucTieuChi.setText(edtMucTieuChi.getHint().toString().replace("năm", ""));
                if (edtGiaTriHienTai.getText().toString().trim().equals(""))
                    edtGiaTriHienTai.setText("auto");
                if (edtLangPhat.getText().toString().trim().equals("")) edtLangPhat.setText("0");
                if (!btnNgayMua.getText().toString().trim().equals("")) {
                    try {
                        saveDialog();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(BaoCaoTaiSanActivity.this, "Vui lòng nhập đúng ngày tháng (dd-mm-yyyy)", Toast.LENGTH_SHORT).show();
            }

            private void saveDialog() throws ParseException {
                String path = danhsach_MucTieu.get(i).getTenFile().replace(".txt", "").trim();
                String NoiDungTaiSan = btnNgayMua.getText().toString().trim() + "@"
                        + edtMucTieuChi.getText().toString().trim() + "@"
                        + edtSoTienChi.getText().toString().trim() + "@"
                        + edtThoiHanSuDung.getText().toString().trim() + "@"
                        + danhsach_MucTieu.get(i).getLoai() + "@"
                        + edtGiaTriHienTai.getText().toString().trim() + "@"
                        + edtLangPhat.getText().toString().trim();
                File DataAppThuChi = new File(modun.TaiSan, danhsach_MucTieu.get(i).getLoai().trim());
                modun.saveTextFile(path, NoiDungTaiSan, DataAppThuChi);
                Toast.makeText(BaoCaoTaiSanActivity.this, "đã tạo", Toast.LENGTH_SHORT).show();
                LayDuLieu_MucTieu_Chi(DataAppThuChi, ngaymin, ngaymax);
                NhanBien();
                dialogSuaTaiSan.dismiss();
                Intent intent = new Intent(BaoCaoTaiSanActivity.this, MainActivity.class);
                intent.putExtra("HanhDong", "truyen");
                Intent intent2 = getIntent();
                intent.putExtra("DuongDan", intent2.getStringExtra("DuongDan"));
                intent.putExtra("PhanLoai", intent2.getStringExtra("PhanLoai"));
                Toast.makeText(BaoCaoTaiSanActivity.this, "`Báo cáo tài chính từ " + intent.getStringExtra("Tu") + " đến " + intent.getStringExtra("Den"), Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }


        });


    }

    private void AnhXa() {
        tvGiaTriHienTai = findViewById(R.id.tvGiaTriHienTai);
        listview_MucTieu = findViewById(R.id.listview_MucTieu);
        tvPhanLoai = findViewById(R.id.tvPhanLoai);
        tvDaMat = findViewById(R.id.tvDaMat);
        tvBienDongTrongKy = findViewById(R.id.tvBienDongTrongKy);
        tvTongTaiSan = findViewById(R.id.tvTongTaiSan);
        tvTrongThang = findViewById(R.id.tvTrongThang);
        lnCuoiKyBaoCao = findViewById(R.id.CuoiKyBaoCao);
        search_bar = findViewById(R.id.search_bar);
        tvTu = findViewById(R.id.tvTu);
        tvDen = findViewById(R.id.tvDen);
    }

    private void NhanBien() throws ParseException {
        bien_tongtaisan = 0;
        bien_trongthang = 0;
        bien_damat = 0;
        Intent intent = getIntent();
        tvPhanLoai.setText(intent.getStringExtra("PhanLoai"));
        String duongdan = intent.getStringExtra("DuongDan");
        if (intent.getStringExtra("Tu").equals("---")){
            ngaymin = "1-1-1000";
            lnCuoiKyBaoCao.setVisibility(View.GONE);
        }else {
            ngaymin = intent.getStringExtra("Tu");

        }


        ngaymax = intent.getStringExtra("Den");
        tvTu.setText(intent.getStringExtra("Tu"));
        tvDen.setText(ngaymax);
        File pathPhanLoai = new File(duongdan, intent.getStringExtra("PhanLoai"));
        List<clsTaiSan> danhsachDauKy = LayDuLieu_MucTieu_Chi(pathPhanLoai, ngaymin, ngaymax);

        int NgayMin = Integer.parseInt(ngaymin.split("-")[0]) + Integer.parseInt(ngaymin.split("-")[1]) * 30 + Integer.parseInt(ngaymin.split("-")[2]) * 365;
        int NgayMax = Integer.parseInt(ngaymax.split("-")[0]) + Integer.parseInt(ngaymax.split("-")[1]) * 30 + Integer.parseInt(ngaymax.split("-")[2]) * 365;
        int SongayDaQuaTuDauKyDenCuoiKy = NgayMax - NgayMin;
        int KhauHaoLangPhat = SetKhauHao(danhsachDauKy, SongayDaQuaTuDauKyDenCuoiKy,NgayMax);
        SetKetQua(KhauHaoLangPhat);
    }

    private int SetKhauHao(List<clsTaiSan> danhsachDauKy, int SongayDaQuaTuDauKyDenCuoiKy ,int NgayMax) {
        int TongKhauHaoTuDauKyDenCuoiKy = 0;
        for (clsTaiSan doituongMucTieu : danhsachDauKy) {
            int bien_HienTai;
                int SongayDaQuaTuDauDenDauKy = NgayMax - (Integer.parseInt(doituongMucTieu.getNgayThangNam().trim().split("-")[0]) + Integer.parseInt(doituongMucTieu.getNgayThangNam().trim().split("-")[1])*30 + Integer.parseInt(doituongMucTieu.getNgayThangNam().trim().split("-")[2])*365);
            if (doituongMucTieu.getThiTruong2().trim().equals("auto")){
                bien_HienTai = Integer.parseInt(doituongMucTieu.getSoTien()) - Integer.parseInt(doituongMucTieu.getDaMat(SongayDaQuaTuDauDenDauKy));
                if (bien_HienTai <= 0) bien_HienTai = 0;
            }else {
                bien_HienTai = Integer.parseInt(doituongMucTieu.getThiTruong2().trim());
            }

            int GiaTriDauKy = bien_HienTai;
            int KhauHaoTuDauKyDenCuoiKy = Integer.parseInt(String.valueOf(Math.round(GiaTriDauKy * 0.07 / 12 * SongayDaQuaTuDauKyDenCuoiKy / 30)));
            TongKhauHaoTuDauKyDenCuoiKy -= KhauHaoTuDauKyDenCuoiKy;

        }
        for (clsTaiSan doituongMucTieu : danhsach_MucTieu){
            TongKhauHaoTuDauKyDenCuoiKy += Integer.parseInt(doituongMucTieu.getTongMat());
        }

        return TongKhauHaoTuDauKyDenCuoiKy;
    }

    private List<clsTaiSan> LayDuLieu_MucTieu_Chi(File duongDanPhanLoai, String ngaymin, String ngaymax) throws ParseException {
        danhsach_MucTieu.clear();
        List<clsTaiSan> danhsach_DauKy = new ArrayList<>();
        Date NgayMin = format.parse(ngaymin);
        Date NgayMax = format.parse(ngaymax);
        int tongsotien_chi = 0;
        if (duongDanPhanLoai.isDirectory()) { /**KIỂM TRA CÓ PHẢI LÀ THƯ MỤC HAY KHÔNG*/
            File[] DanhsachMucTieuDaTao = duongDanPhanLoai.listFiles();/**lấy danh sách mục tiêu chi*/
            for (File f : DanhsachMucTieuDaTao) {
                String data = modun.readText(f);
                String[] mangData = data.split("@");
                String NgayThangNam = mangData[0];
                String ThangNam = NgayThangNam.split("-")[1] + " " + NgayThangNam.split("-")[2];
                int sotien_Chi = Integer.parseInt(mangData[2]);
                String MucTieu = mangData[1];
                String DuKien = mangData[3];
                String Loai = mangData[4];
                String ThiTruong = mangData[5];
                String LangPhat = mangData[6];
                Uri uriImage = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.icon1);
                tongsotien_chi = tongsotien_chi + sotien_Chi;
                clsTaiSan doituong_MucTieu = new clsTaiSan(uriImage, MucTieu, ThangNam, String.valueOf(sotien_Chi), DuKien, Loai, f.getName(), NgayThangNam, ThiTruong, LangPhat);
                Date NgayMucTieu = format.parse(NgayThangNam);

                if (!NgayMucTieu.after(NgayMax) && !NgayMucTieu.before(NgayMin)) {
                    danhsach_MucTieu.add(doituong_MucTieu);
                    mucTieu_adapter = new TaiSan_Adapter(danhsach_MucTieu, BaoCaoTaiSanActivity.this, R.layout.layout_item_taisan);
                    listview_MucTieu.setAdapter(mucTieu_adapter);
                }
                if (!NgayMucTieu.after(NgayMax)) {
                    tongcuoiky += Math.round(Double.parseDouble(doituong_MucTieu.getHienTai()));
                }
                if (!NgayMucTieu.before(NgayMin)) {
                    danhsach_DauKy.add(doituong_MucTieu);
                    tongdauky += Math.round(Double.parseDouble(doituong_MucTieu.getHienTai()));
                }
            }
        }
        return danhsach_DauKy;
    }

    public void SetKetQua(int KhauHaoLangPhat) {
        bien_damat = 0;
        bien_trongthang = 0;
        bien_tongtaisan = 0;
        bien_gthientai = 0;
        int NgayMin = Integer.parseInt(ngaymin.split("-")[0]) + Integer.parseInt(ngaymin.split("-")[1]) * 30 + Integer.parseInt(ngaymin.split("-")[2]) * 365;
        int NgayMax = Integer.parseInt(ngaymax.split("-")[0]) + Integer.parseInt(ngaymax.split("-")[1]) * 30 + Integer.parseInt(ngaymax.split("-")[2]) * 365;
        int SoNgayDaQua = NgayMax - NgayMin;
        bien_trongthang = Math.round(( KhauHaoLangPhat) / SoNgayDaQua * 30);
        bien_damat = Math.round( KhauHaoLangPhat);
        bien_tongtaisan = Math.round(tongdauky);
        bien_gthientai = Math.round(tongcuoiky);
        tvTongTaiSan.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_tongtaisan)))).replace(".", ",").replace(",", ","));
        tvGiaTriHienTai.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_gthientai)))).replace(".", ",").replace(",", ","));
        tvDaMat.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_damat)))).replace(".", ",").replace(",", ","));
        tvTrongThang.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_trongthang)))).replace(".", ",").replace(",", ","));
    }
}