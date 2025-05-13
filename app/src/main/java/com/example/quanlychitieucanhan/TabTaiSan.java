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
import android.text.format.Time;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TabTaiSan extends Fragment {
    ImageButton btn_ThemHocSinh;
    AutoCompleteTextView search_bar;
    private ViewStub stubGrid;
    TextView tvPhanLoai, tvTongTaiSan, tvDaMat, tvTrongThang, tvGiaTriHienTai;
    private ViewStub stubList;
    LinearLayout layoutSearch;
    int bien_tongtaisan = 0;
    int bien_trongthang = 0;
    int bien_gthientai = 0;
    int bien_damat = 0;

    List<clsTaiSan> danhsach_MucTieu = new ArrayList<>();
    TextView tvViChiem;
    IconAdapter iconAdapter;
    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    ImageButton btnXemBaoCao;
    ListView listView;
    private GridView girdView;
    int mMonth, mYear, mDay;
    private ListviewAdapter listViewAdapter;
    private GridviewAdapter gridviewAdapter;
    ViewGroup viewgroup;
    private List<PhanLoai> danhsach_Phanloai;
    public int curnntView = VIEW_MODE_GRIDVIEW;

    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_tai_san, container, false);
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        modun.TaoThuMuc(modun.DataAppThuChi);
        AnhXa(view);
        stubGrid = view.findViewById(R.id.stub_grid);
        stubList = view.findViewById(R.id.stub_list);
        stubList.inflate();
        stubGrid.inflate();
        girdView = (GridView) view.findViewById(R.id.myGrid);
        listView = (ListView) view.findViewById(R.id.mylistView);
        LayDuLieu_PhanLoai_TaiSan();
        switchView();
        SuKien();
        LayDuongDanFile("---", "---");
        return view;

    }

    public void switchView() {
        if (VIEW_MODE_LISTVIEW == curnntView) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.VISIBLE);
            //Display gridview
            stubGrid.setVisibility(View.GONE);
        }
        setAdapters();
    }

    public void LayDuLieu_PhanLoai_TaiSan() {
        danhsach_Phanloai = new ArrayList<>();
        File Template = modun.TaiSan;
        if (Template.exists()) {
            /**KHAI BÁO BIẾN*/
            double bien_TongTienChi = 0;
            Double bien_PhanTramChi = 0.0;
            Double TinhTongChi = 0.0;
            File[] DanhsachPhanloaiDaTao = Template.listFiles();
            /**LẤY TỔNG SÓ SỐ ĐÃ CHI THEO TỪNG PHÂN LOẠI*/
            for (File f : DanhsachPhanloaiDaTao) {
                if (!f.getName().equals("Icon")) {
                    bien_TongTienChi = LayDuLieu_MucTieu_TaiSan(f);
                    TinhTongChi = TinhTongChi + bien_TongTienChi;
                    File DataIcon = new File(modun.TaiSan_Icon, f.getName().trim() + ".txt");
                    Uri ImageData = Uri.parse(modun.readText(DataIcon).trim());
                    PhanLoai doituong_PhanLoai = new PhanLoai(ImageData, f.getName().trim(), bien_TongTienChi, bien_PhanTramChi, "ts");
                    danhsach_Phanloai.add(doituong_PhanLoai);
                }

            }
            for (Integer i = 0; i < DanhsachPhanloaiDaTao.length; i++) {
                bien_PhanTramChi = danhsach_Phanloai.get(i).getTongTienChi() / TinhTongChi * 100;
                danhsach_Phanloai.get(i).setPhanTramChi(modun.round(bien_PhanTramChi, 1));
            }
            /**TÍNH PHẦN TRĂM*/

            /**HIỂN THỊ RA MÀN HÌNH*/
            listViewAdapter = new ListviewAdapter(danhsach_Phanloai, getContext(), R.layout.list_item);
            listView.setAdapter(listViewAdapter);
            gridviewAdapter = new GridviewAdapter(danhsach_Phanloai, getContext(), R.layout.list_item);
            girdView.setAdapter(gridviewAdapter);
        }
    }

    private double LayDuLieu_MucTieu_TaiSan(File duongDanPhanLoai) {
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
                File DataIcon = new File(modun.TaiSan_Icon, duongDanPhanLoai.getName().trim() + ".txt");
                Uri uriImage = Uri.parse(modun.readText(DataIcon).trim());
                clsTaiSan doituong_MucTieu = new clsTaiSan(uriImage, MucTieu, ThangNam, String.valueOf(sotien_Chi), DuKien, Loai, f.getName(), NgayThangNam, ThiTruong, LangPhat);
                tongsotien_chi = tongsotien_chi + Integer.parseInt(doituong_MucTieu.getHienTai());

            }
        } else return 0;
        return tongsotien_chi;
    }

    public void setAdapters() {
        if (VIEW_MODE_LISTVIEW == curnntView) {
            listViewAdapter = new ListviewAdapter(danhsach_Phanloai, getContext(), R.layout.list_item);
            listView.setAdapter(listViewAdapter);
        } else {
            gridviewAdapter = new GridviewAdapter(danhsach_Phanloai, getContext(), R.layout.grid_item);
            girdView.setAdapter(gridviewAdapter);
        }
    }

    private void DialogThemKhoanTaiSan(final int position) {
        final Dialog dialogthemkhoanchi = new Dialog(getContext());
        dialogthemkhoanchi.setContentView(R.layout.dialog_themkhoantaisan);
        dialogthemkhoanchi.show();
        /**Lấy ngày hiện tại*/
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String Ngay = sdf.format(new Date());
        String PhanLoai = danhsach_Phanloai.get(position).getPhanLoai().trim();
        String Thuoctinh = "chưa rõ";
        File ThuocTinh = new File(modun.TaiSan_ThuocTinh, danhsach_Phanloai.get(position).getPhanLoai().trim() + ".txt");
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
        final String Gio = today.format("%k:%M:%S");
        /***/
        final AutoCompleteTextView edtMucTieuChi = dialogthemkhoanchi.findViewById(R.id.edt_MucTieuChi);
        final AutoCompleteTextView edtSoTienChi = dialogthemkhoanchi.findViewById(R.id.edt_SoTienChi);
        final AutoCompleteTextView edtThoiHanSuDung = dialogthemkhoanchi.findViewById(R.id.edt_ThoiHanSuDung);
        final AutoCompleteTextView edtGiaTriHienTai = dialogthemkhoanchi.findViewById(R.id.edt_GiaTriHienTai);
        final AutoCompleteTextView edtLangPhat = dialogthemkhoanchi.findViewById(R.id.edt_LangPhat);
        final Button btnLuuThongSo = dialogthemkhoanchi.findViewById(R.id.btnLuuThongSo);
        final Button btnThoat = dialogthemkhoanchi.findViewById(R.id.btnThoat);
        final TextView tvTaiSan = dialogthemkhoanchi.findViewById(R.id.tvTaiSan);
        final TextView tvKieu = dialogthemkhoanchi.findViewById(R.id.tvKieu);
        final Button btnNgayChi = dialogthemkhoanchi.findViewById(R.id.btn_NgayChi);
        btnNgayChi.setText(Ngay);
        final ImageButton btnCalendar = dialogthemkhoanchi.findViewById(R.id.btnCalendar);
        tvTaiSan.setText(PhanLoai);
        tvKieu.setText(Thuoctinh);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.MyDatePickerCalendarDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                btnNgayChi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnNgayChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.MyDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                btnNgayChi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogthemkhoanchi.dismiss();
            }
        });
        btnLuuThongSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSoTienChi.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Bạn chưa nhập đủ dữ liệu.", Toast.LENGTH_SHORT).show();
                }
                if (edtMucTieuChi.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Bạn chưa nhập đủ dữ liệu.", Toast.LENGTH_SHORT).show();
                }
                if (btnNgayChi.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Bạn chưa nhập đủ dữ liệu.", Toast.LENGTH_SHORT).show();
                }
                if (edtThoiHanSuDung.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Bạn chưa nhập đủ dữ liệu.", Toast.LENGTH_SHORT).show();
                }
                if (edtThoiHanSuDung.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập đúng sử dụng dự kiến", Toast.LENGTH_SHORT).show();
                }
                if (!edtThoiHanSuDung.getText().toString().trim().equals("")) {
                    if (Double.valueOf(edtThoiHanSuDung.getText().toString().trim()) <= 0)
                        Toast.makeText(getContext(), "Lưu ý: thời hạn sử dụng luôn hơn 0 năm", Toast.LENGTH_SHORT).show();

                }

                if (!edtMucTieuChi.getText().toString().trim().equals("") &&
                        !btnNgayChi.getText().toString().trim().equals("") &&
                        !edtSoTienChi.getText().toString().trim().equals("") &&
                        !edtThoiHanSuDung.getText().toString().trim().equals("") &&
                        Double.valueOf(edtThoiHanSuDung.getText().toString().trim()) != 0) {
                    String giatrihientai = "auto";
                    if (!edtGiaTriHienTai.getText().toString().trim().equals("")) {
                        giatrihientai = edtGiaTriHienTai.getText().toString().trim();
                    } else giatrihientai = "auto";
                    String langphat = "0";
                    if (!edtLangPhat.getText().toString().trim().equals("")) {
                        langphat = edtLangPhat.getText().toString().trim();
                    } else langphat = "0";
                    //TẠO NỘI DUNG
                    String NoiDung_MucTieu_Chi = btnNgayChi.getText().toString() + "@" + edtMucTieuChi.getText().toString()
                            + "@" + edtSoTienChi.getText().toString().trim() + "@" + edtThoiHanSuDung.getText().toString() + "@" + danhsach_Phanloai.get(position).getPhanLoai() + "@" + giatrihientai + "@" + langphat;
                    String TenFile = btnNgayChi.getText().toString().replace("-", "") + "@" + Gio.replace(":", "");
                    File DataAppThuChi = new File(modun.TaiSan, danhsach_Phanloai.get(position).getPhanLoai());
                    modun.saveTextFile(TenFile, NoiDung_MucTieu_Chi, DataAppThuChi);
                    LayDuLieu_PhanLoai_TaiSan();
                    LayDuongDanFile("---", "---");
                    dialogthemkhoanchi.dismiss();
                }
            }
        });
    }

    private void SuKien() {
        btn_ThemHocSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogThemPhanLoaiTaiSan();
            }
        });
        listView.setOnItemClickListener(onItemClickListener);
        girdView.setOnItemClickListener(onItemClickListener);
        listView.setOnItemLongClickListener(onItemLongClickListener);
        girdView.setOnItemLongClickListener(onItemLongClickListener);
        btnXemBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SplashScreen.class);
                intent.putExtra("HanhDong","MainTaiSanAll");
                startActivity(intent);
            }
        });
        mainActivity.getSearchBarTS().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listViewAdapter.filter(mainActivity.getSearchBarTS().getQuery().toString().trim());
                gridviewAdapter.filter(mainActivity.getSearchBarTS().getQuery().toString().trim());
                return false;
            }
        });
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            DialogThemKhoanTaiSan(i);
        }
    };
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            DialogMenu(i);
            return true;
        }
    };

    private void DialogMenu(final int position) {
        final Dialog dialogthongso = new Dialog(getContext(), R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_menu);
        Window window = dialogthongso.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();
        dialogthongso.setCanceledOnTouchOutside(true);
        ImageButton btnThoat = dialogthongso.findViewById(R.id.btnThoat);
        Button btnSuaLoai = dialogthongso.findViewById(R.id.btnSuaLoai);
        btnSuaLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSuaTen(position);
                dialogthongso.dismiss();
            }
        });
        Button btnXoa = dialogthongso.findViewById(R.id.btnXoa);
        Button btnLichSu = dialogthongso.findViewById(R.id.btnXemLichSu);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogthongso.dismiss();
            }
        });
        btnLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TaiSanActivity.class);
                intent.putExtra("PhanLoai", danhsach_Phanloai.get(position).getPhanLoai());
                intent.putExtra("DuongDan", modun.TaiSan.getPath());
                startActivity(intent);
                dialogthongso.dismiss();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Bạn muốn xóa thư mục này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File DataAppThuChi = new File(modun.TaiSan, danhsach_Phanloai.get(position).getPhanLoai());
                        File DataIcon = new File(modun.TaiSan_Icon, danhsach_Phanloai.get(position).getPhanLoai() + ".txt");
                        if (DataAppThuChi.isDirectory()) {
                            String[] children = DataAppThuChi.list();
                            for (int ii = 0; ii < children.length; ii++) {
                                new File(DataAppThuChi, children[ii]).delete();
                            }
                        }
                        DataAppThuChi.delete();
                        DataIcon.delete();

                        if (!DataAppThuChi.exists()) {
                            LayDuLieu_PhanLoai_TaiSan();
                            dialogthongso.dismiss();
                            Toast.makeText(getContext(), "Bạn đã xóa thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Bạn đã xóa không thành công.", Toast.LENGTH_SHORT).show();
                            dialogthongso.dismiss();
                        }
                        LayDuLieu_PhanLoai_TaiSan();
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

    private void DialogSuaTen(final int position) {
        final Dialog dialogthongso = new Dialog(getContext());
        dialogthongso.setContentView(R.layout.dialog_themphanloaits);
        dialogthongso.show();
        dialogthongso.setCanceledOnTouchOutside(true);
        File ThuocTinh = new File(modun.TaiSan_ThuocTinh, danhsach_Phanloai.get(position).getPhanLoai().trim() + ".txt");
        String strThuocTinh = modun.readText(ThuocTinh).trim();
        final RadioButton radioDauTu = dialogthongso.findViewById(R.id.radioDauTu);
        final RadioButton radioTieuDung = dialogthongso.findViewById(R.id.radioTieuDung);
        final RadioButton radioTienMat = dialogthongso.findViewById(R.id.radioTienMat);
        final RadioButton radioNo = dialogthongso.findViewById(R.id.radioNo);
        final RadioGroup radioGroup = dialogthongso.findViewById(R.id.radioGroup);
        final EditText edtChinhSua = dialogthongso.findViewById(R.id.edt_PhanLoai);
        final Button btnChinhSua = dialogthongso.findViewById(R.id.btnLuuThongSo);
        final ImageView imgHide = dialogthongso.findViewById(R.id.imgHide);
        switch (strThuocTinh){
            case "tsdt":
                radioDauTu.setChecked(true);
                break;
            case "tstd":
                radioTieuDung.setChecked(true);
                break;
            case "tstm":
                radioTienMat.setChecked(true);
                break;
            case "tsno":
                radioNo.setChecked(true);
                break;
        }
        imgHide.setVisibility(View.GONE);
        edtChinhSua.setText(danhsach_Phanloai.get(position).getPhanLoai());
        btnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtChinhSua.getText().equals("")) {
                    File filemoi = new File(modun.TaiSan, edtChinhSua.getText().toString().trim());//Đường dẫn đến thư mục muốn đổi mới
                    File filecu = new File(modun.TaiSan, danhsach_Phanloai.get(position).getPhanLoai().trim());//Đường đẫn đến thư mục cũ cần đổi tên
                    File fileThuocTinhCu = new File(modun.TaiSan_ThuocTinh, danhsach_Phanloai.get(position).getPhanLoai().trim() + ".txt");
                    File fileThuocTinhMoi = new File(modun.TaiSan_ThuocTinh, edtChinhSua.getText().toString().trim() + ".txt");
                    File[] DanhSachPhanLoaiDaTao = filecu.listFiles();
                    if (!filemoi.exists() || filemoi.getName().trim().equals(filecu.getName().trim())) {
                        for (File f : DanhSachPhanLoaiDaTao) {
                            String data = modun.readText(f);
                            String[] mangData = data.split("@");
                            String NgayThangNam = mangData[0];
                            String ThangNam = NgayThangNam.split("-")[1] + " " + NgayThangNam.split("-")[2];
                            String sotien_Chi = mangData[2];
                            String MucTieu = mangData[1];
                            String DuKien = mangData[3];
                            String Loai = edtChinhSua.getText().toString().trim();
                            String ThiTruong = mangData[5];
                            String LangPhat = mangData[6];
                            String NoidungMucTieuMoi = NgayThangNam + "@"
                                    + MucTieu + "@"
                                    + sotien_Chi + "@"
                                    + DuKien + "@"
                                    + Loai + "@"
                                    + ThiTruong + "@"
                                    + LangPhat;
                            modun.saveTextFile(f.getName().replace(".txt", ""), NoidungMucTieuMoi, filecu);

                        }
                        File fileIconCu = new File(modun.TaiSan_Icon, danhsach_Phanloai.get(position).getPhanLoai().trim() + ".txt");
                        File fileIconMoi = new File(modun.TaiSan_Icon, edtChinhSua.getText().toString().trim() + ".txt");
                        fileThuocTinhCu.renameTo(fileThuocTinhMoi);
                        fileIconCu.renameTo(fileIconMoi);
                        filecu.renameTo(filemoi);
                        switch (radioGroup.getCheckedRadioButtonId()){
                            case R.id.radioDauTu:
                                modun.saveTextFile(edtChinhSua.getText().toString().trim(),"tsdt",modun.TaiSan_ThuocTinh);
                                break;
                            case R.id.radioTieuDung:
                                modun.saveTextFile(edtChinhSua.getText().toString().trim(),"tstd",modun.TaiSan_ThuocTinh);
                                break;
                            case R.id.radioTienMat:
                                modun.saveTextFile(edtChinhSua.getText().toString().trim(),"tstm",modun.TaiSan_ThuocTinh);
                                break;
                            case R.id.radioNo:
                                modun.saveTextFile(edtChinhSua.getText().toString().trim(),"tsno",modun.TaiSan_ThuocTinh);
                                break;
                        }
                        DialogThemIcon(edtChinhSua.getText().toString().trim());

                        LayDuLieu_PhanLoai_TaiSan();
                        dialogthongso.dismiss();
                    } else
                        Toast.makeText(getContext(), "Bạn đã có phân loại này!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    protected void AnhXa(View v) {
        //region Anh Xa
        btn_ThemHocSinh = v.findViewById(R.id.btn_ThemHocSinh);
        mainActivity = (MainActivity) getActivity();
        viewgroup = v.findViewById(R.id.viewgroup);
        search_bar = v.findViewById(R.id.search_bar);
        layoutSearch = v.findViewById(R.id.layoutSearch);
        tvDaMat = v.findViewById(R.id.tvDaMat);
        tvTongTaiSan = v.findViewById(R.id.tvTongTaiSan);
        tvTrongThang = v.findViewById(R.id.tvTrongThang);
        tvGiaTriHienTai = v.findViewById(R.id.tvGiaTriHienTai);
        btnXemBaoCao = v.findViewById(R.id.btn_XemBaoCao);
        //endregion
    }

    private void DialogThemPhanLoaiTaiSan() {
        final Dialog dialogthongso = new Dialog(getContext());
        dialogthongso.setContentView(R.layout.dialog_themphanloaits);
        dialogthongso.show();
        //Anh Xa
        final EditText edtPhanLoai = dialogthongso.findViewById(R.id.edt_PhanLoai);
        final Button btnLuuThongSo = dialogthongso.findViewById(R.id.btnLuuThongSo);
        final RadioButton radioDauTu = dialogthongso.findViewById(R.id.radioDauTu);
        final ImageButton btnThoat = dialogthongso.findViewById(R.id.btnThoat);
        final RadioButton radioTieuDung = dialogthongso.findViewById(R.id.radioTieuDung);
        final RadioButton radioTienMat = dialogthongso.findViewById(R.id.radioTienMat);
        final RadioButton radioNo = dialogthongso.findViewById(R.id.radioNo);
        final RadioGroup radioGroup = dialogthongso.findViewById(R.id.radioGroup);
        //Su Kien
        btnLuuThongSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bien_PhanLoai = edtPhanLoai.getText().toString();
                if (bien_PhanLoai.trim().equals(""))
                    Toast.makeText(getContext(), R.string.thongbao, Toast.LENGTH_SHORT).show();
                if (!bien_PhanLoai.trim().equals("")) {
                    File ThuMucPhanLoai = new File(modun.TaiSan, bien_PhanLoai.trim());///TẠO RA ĐƯỜNG DẪN THƯ MỤC MUỐN VD:"C:/DATATAAPPTHUCHI/PHANLOAI
                    if (ThuMucPhanLoai.exists()) {
                        Toast.makeText(getContext(), "Bạn đã có phân loại này", Toast.LENGTH_SHORT).show();
                    } else {
                        File ThuocTinh = modun.TaiSan_ThuocTinh;
                        RadioButton radioCheck = dialogthongso.findViewById(radioGroup.getCheckedRadioButtonId());
                        if (radioCheck == radioDauTu) {
                            modun.saveTextFile(bien_PhanLoai.trim(), "tsdt", ThuocTinh);
                        } else if (radioCheck == radioTienMat) {
                            modun.saveTextFile(bien_PhanLoai.trim(), "tstm", ThuocTinh);
                        } else if (radioCheck == radioTieuDung) {
                            modun.saveTextFile(bien_PhanLoai.trim(), "tstd", ThuocTinh);
                        } else if (radioCheck == radioNo) {
                            modun.saveTextFile(bien_PhanLoai.trim(), "tsno", ThuocTinh);
                        }
                        modun.TaoThuMuc(ThuMucPhanLoai);
                        DialogThemIcon(bien_PhanLoai.trim());
                        LayDuLieu_PhanLoai_TaiSan();

                        dialogthongso.dismiss();
                    }


                }

            }

        });


    }

    private void DialogThemIcon(final String PhanLoai) {
        final Dialog dialogthongso = new Dialog(getContext());
        dialogthongso.setContentView(R.layout.dialog_themicon);
        dialogthongso.show();
        iconAdapter = new IconAdapter(getContext(), R.layout.grid_item_icon);
        dialogthongso.setCancelable(false);
        final GridView gridView_icon = dialogthongso.findViewById(R.id.myGrid);
        gridView_icon.setAdapter(iconAdapter);
        dialogthongso.setCanceledOnTouchOutside(false);
        gridView_icon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                modun.saveTextFile(PhanLoai, iconAdapter.myObject.get(position).toString(), modun.TaiSan_Icon);
                LayDuLieu_PhanLoai_TaiSan();
                dialogthongso.dismiss();
            }
        });
    }

    public void updateListView() {
        if (VIEW_MODE_LISTVIEW == curnntView) {
            curnntView = VIEW_MODE_GRIDVIEW;
        } else {
            curnntView = VIEW_MODE_LISTVIEW;
        }
        //Switch view
        switchView();
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
        SetKetQua();

    }

    private double LayDuLieu_SoTIen_Vi(File f) {
        double tongsotien_chi = 0;
        if (f.isDirectory()) { /**KIỂM TRA CÓ PHẢI LÀ THƯ MỤC HAY KHÔNG*/
            File[] DanhsachMucTieuDaTao = f.listFiles();/**lấy danh sách mục tiêu chi*/
            for (File file : DanhsachMucTieuDaTao) {
                if (!file.getName().trim().equals("SoTienTrongVi.txt")) {
                    String data = modun.readText(file);
                    String[] mangData = data.split("@");
                    double sotien_Chi = Double.valueOf(mangData[2]);
                    tongsotien_chi = tongsotien_chi + sotien_Chi;
                } else {
                    String data = modun.readText(file);
                    double sotien_Chi = Double.valueOf(data.trim());
                    tongsotien_chi = tongsotien_chi + sotien_Chi;
                }

            }
        } else return 0;
        return tongsotien_chi;
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
                String ThiTruong = mangData[5];
                String LangPhat = mangData[6];
                File DataIcon = new File(modun.TaiSan_Icon, duongDanPhanLoai.getName().trim() + ".txt");
                Uri uriImage = Uri.parse(modun.readText(DataIcon).trim());
                clsTaiSan doituong_MucTieu = new clsTaiSan(uriImage, MucTieu, ThangNam, String.valueOf(sotien_Chi), DuKien, Loai, f.getName(), NgayThangNam, ThiTruong, LangPhat);
                danhsach_MucTieu.add(doituong_MucTieu);
                tongsotien_chi = tongsotien_chi + Integer.parseInt(doituong_MucTieu.getHienTai());


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
        tvGiaTriHienTai.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_gthientai)))).replace(".", ",").replace(",", ","));
        tvTongTaiSan.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_tongtaisan)))).replace(".", ",").replace(",", ","));
        tvDaMat.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_damat)))).replace(".", ",").replace(",", ","));
        tvTrongThang.setText((String.format("%,d", Long.parseLong(String.valueOf(bien_trongthang)))).replace(".", ",").replace(",", ","));
        mainActivity.truyen_tvTaiSan().setText(tvGiaTriHienTai.getText().toString().trim());
    }
}