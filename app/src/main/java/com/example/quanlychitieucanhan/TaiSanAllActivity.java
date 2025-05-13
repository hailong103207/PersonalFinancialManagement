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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaiSanAllActivity extends AppCompatActivity {
    ListView listview_MucTieu;
    TextView tvPhanLoai, tvTongTaiSan, tvDaMat, tvTrongThang, tvGiaTriHienTai;
    List<clsTaiSan> danhsach_MucTieu = new ArrayList<>();
    BaoCaoTaiSan_Adapter mucTieu_adapter;
    SearchView search_bar;
    ImageButton btnBoLoc;
    static final String btnTu = "---";
    static final String btnDen = "---";
    TabTaiSan tabTaiSan;
    TextView tvTu, tvDen;
    int bien_tongtaisan = 0;
    int bien_trongthang = 0;
    int bien_gthientai = 0;
    int bien_damat = 0;
    int mMonth, mYear, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taisanall);
        AnhXa();
        LayDuongDanFile(btnTu, btnDen);
        SuKien();
    }

    private void SuKien() {

        listview_MucTieu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TaiSanAllActivity.this, danhsach_MucTieu.get(position).getTenFile(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(TaiSanAllActivity.this, "Chức năng đang phát triển", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void DiaLogSuaXoa(final int position) {
        final Dialog dialogSuaXoa = new Dialog(TaiSanAllActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TaiSanAllActivity.this);
                builder.setTitle("Bạn muốn xóa thư mục này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File DataAppThuChi = new File(modun.TaiSan, danhsach_MucTieu.get(position).getLoai());
                        File DataFile = new File(DataAppThuChi, danhsach_MucTieu.get(position).getTenFile());
                        DataFile.delete();
                        LayDuLieu_MucTieu_Chi(DataAppThuChi);
                        SetKetQua();
                        if (!DataFile.exists()) {

                            dialogSuaXoa.dismiss();
                            LayDuLieu_MucTieu_Chi(DataAppThuChi);
                            SetKetQua();
                            Toast.makeText(TaiSanAllActivity.this, "Bạn đã xóa thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TaiSanAllActivity.this, "Bạn đã xóa không thành công.", Toast.LENGTH_SHORT).show();
                            dialogSuaXoa.dismiss();
                        }
                        LayDuLieu_MucTieu_Chi(DataAppThuChi);
                        SetKetQua();
                        Intent intent = new Intent(TaiSanAllActivity.this, MainActivity.class);
                        intent.putExtra("HanhDong", "truyen3");
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
        final Dialog dialogSuaTaiSan = new Dialog(TaiSanAllActivity.this);
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
        modun.setPopUp(TaiSanAllActivity.this, edtSoTienChi, modun.listSoTien);
        /** SET TEXT */
        btnNgayMua.setText(danhsach_MucTieu.get(i).getNgayThangNam());
        edtMucTieuChi.setText(danhsach_MucTieu.get(i).getTenMucTieu());
        edtSoTienChi.setText(danhsach_MucTieu.get(i).getSoTien());
        edtThoiHanSuDung.setText(danhsach_MucTieu.get(i).getDuKien());
        edtLangPhat.setText(danhsach_MucTieu.get(i).getLangPhat().trim());
        if (!danhsach_MucTieu.get(i).getThiTruong2().trim().equals("auto"))
            edtGiaTriHienTai.setText(danhsach_MucTieu.get(i).getThiTruong2());

        btnNgayMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(TaiSanAllActivity.this, R.style.MyDatePickerDialogTheme,
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
                    saveDialog();
                } else
                    Toast.makeText(TaiSanAllActivity.this, "Vui lòng nhập đúng ngày tháng (dd-mm-yyyy)", Toast.LENGTH_SHORT).show();
            }

            private void saveDialog() {
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
                Toast.makeText(TaiSanAllActivity.this, "đã tạo", Toast.LENGTH_SHORT).show();
                LayDuLieu_MucTieu_Chi(DataAppThuChi);
                SetKetQua();
                dialogSuaTaiSan.dismiss();
                Intent intent = new Intent(TaiSanAllActivity.this, MainActivity.class);
                intent.putExtra("HanhDong", "truyen3");
                startActivity(intent);
            }


        });


    }

    private void AnhXa() {
        listview_MucTieu = findViewById(R.id.listview_MucTieu);
        tvPhanLoai = findViewById(R.id.tvPhanLoai);
        tvDaMat = findViewById(R.id.tvDaMat);
        tvTongTaiSan = findViewById(R.id.tvTongTaiSan);
        tvTrongThang = findViewById(R.id.tvTrongThang);
        search_bar = findViewById(R.id.search_bar);
        tvTu = findViewById(R.id.tvTu);
        tvDen = findViewById(R.id.tvDen);
        tvGiaTriHienTai = findViewById(R.id.tvGiaTriHienTai);
    }

    public void DialogBoLoc() {
        final Dialog dialogthongso = new Dialog(TaiSanAllActivity.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_boloc);
        dialogthongso.show();
        dialogthongso.setCanceledOnTouchOutside(true);
        final Button btnTu = dialogthongso.findViewById(R.id.btn_NgayChi);
        final Button btnDen = dialogthongso.findViewById(R.id.btn_NgayChiDen);
        final ImageButton btnLichTu = dialogthongso.findViewById(R.id.btnCalendar);
        final ImageButton btnLichDen = dialogthongso.findViewById(R.id.btnCalendarDen);
        final ImageButton btnBoGioiHanTu = dialogthongso.findViewById(R.id.btnBoGioiHan);
        final ImageButton btnBoGioiHanDen = dialogthongso.findViewById(R.id.btnBoGioiHanDen);
        final Button btnLuuThongSo = dialogthongso.findViewById(R.id.btnLuuThongSo);
        final Button btnThoat = dialogthongso.findViewById(R.id.btnThoat);

        /**SETTEXT*/

        btnTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(TaiSanAllActivity.this, R.style.MyDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                btnTu.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(TaiSanAllActivity.this, R.style.MyDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                btnDen.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btnLichTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(TaiSanAllActivity.this, R.style.MyDatePickerCalendarDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                btnTu.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnLichDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(TaiSanAllActivity.this, R.style.MyDatePickerCalendarDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                btnDen.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btnBoGioiHanTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTu.setText("---");
            }
        });
        btnBoGioiHanDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDen.setText("---");
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogthongso.dismiss();
            }
        });

        btnLuuThongSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TaiSanAllActivity.this, "nandjasndjsandas", Toast.LENGTH_SHORT).show();
                // mucTieu_adapter.sort(danhsach_MucTieu, lichSuActivity.getTvTu().getText().toString().trim(), lichSuActivity.getTvDen().getText().toString().trim());
                //tabLichSuTatCa.mucTieu_adapter = new MucTieu_Adapter( tabLichSuTatCa.mucTieu_adapter.sort(tabLichSuTatCa.danhsach_MucTieu, tabLichSuTatCa.lichSuActivity.tvTu.getText().toString().trim(), tabLichSuTatCa.lichSuActivity.tvDen.getText().toString().trim()),tabLichSuTatCa.TaiSanAllActivity.this, R.layout.layout_item_muctieu);
                //listview_MucTieu.setAdapter(null);
                tvTu.setText(btnTu.getText().toString().trim());
                tvDen.setText(btnDen.getText().toString().trim());
                LayDuongDanFile(btnTu.getText().toString().trim(), btnDen.getText().toString().trim());
                dialogthongso.dismiss();
            }
        });
    }

    public void LayDuongDanFile(String ngaymins, String ngaymaxs) {
        double bien_tinhtonghientai = 0;
        double bien_tinhtongdamat = 0;
        danhsach_MucTieu.clear();
        File Template = modun.TaiSan;
        File[] DanhsachPhanloaiDaTao = Template.listFiles();
        /**LẤY TỔNG SÓ SỐ ĐÃ CHI THEO TỪNG PHÂN LOẠI*/
        for (File f : DanhsachPhanloaiDaTao) {
            LayDuLieu_MucTieu_Chi(f);
        }
        danhsach_MucTieu = clsTaiSan.sort(danhsach_MucTieu, ngaymins, ngaymaxs);
        for (clsTaiSan doituong : danhsach_MucTieu) {
            bien_tinhtonghientai += Double.valueOf(doituong.getSoTien());
            bien_tinhtongdamat += Double.valueOf(doituong.getTongMat());
        }
        //mucTieu_adapter = new MucTieu_Adapter(clsMucTieu.sort(danhsach_MucTieu), TaiSanAllActivity.this, R.layout.layout_item_muctieu);
        //listview_MucTieu.setAdapter(mucTieu_adapter);
        mucTieu_adapter = new BaoCaoTaiSan_Adapter(danhsach_MucTieu, TaiSanAllActivity.this, R.layout.layout_item_baocaophanloaitaisan, bien_tinhtonghientai, bien_tinhtongdamat);
        SetKetQua();
        listview_MucTieu.setAdapter(mucTieu_adapter);
    }

    private Integer LayDuLieu_MucTieu_Chi(File duongDanPhanLoai) {
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
                tongsotien_chi = tongsotien_chi + sotien_Chi;
                String ThiTruong = mangData[5];
                String LangPhat = mangData[6];
                Uri uriImage = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.icon1);
                File DataIcon = new File(modun.TaiSan_Icon, duongDanPhanLoai.getName().trim() + ".txt");
                uriImage = Uri.parse(modun.readText(DataIcon).trim());
                clsTaiSan doituong_MucTieu = new clsTaiSan(uriImage, MucTieu, ThangNam, String.valueOf(sotien_Chi), DuKien, Loai, f.getName(), NgayThangNam, ThiTruong, LangPhat);
                danhsach_MucTieu.add(doituong_MucTieu);


            }
        } else return 0;
        return tongsotien_chi;
    }

    public void SetKetQua() {
        bien_damat = 0;
        bien_trongthang = 0;
        bien_tongtaisan = 0;
        bien_gthientai = 0;
        for (clsTaiSan x : danhsach_MucTieu) {
            bien_trongthang += Double.valueOf(x.getMoiThangMat());
            bien_tongtaisan += Double.valueOf(x.getSoTien());
            bien_damat += Double.valueOf(x.getTongMat());
            bien_gthientai += Double.valueOf(x.getHienTai());

        }
        bien_trongthang = Math.round(bien_trongthang);
        bien_damat = Math.round(bien_damat);
        bien_tongtaisan = Math.round(bien_tongtaisan);
        bien_gthientai = Math.round(bien_gthientai);
        tvTongTaiSan.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_tongtaisan)))).replace(".", ",").replace(",", ","));
        tvDaMat.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_damat)))).replace(".", ",").replace(",", ","));
        tvTrongThang.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_trongthang)))).replace(".", ",").replace(",", ","));
        tvGiaTriHienTai.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_gthientai)))).replace(".", ",").replace(",", ","));

    }

}
















    