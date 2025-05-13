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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabLichSuTatCa extends Fragment {

    ListView listview_MucTieu;
    List<clsMucTieu> danhsach_MucTieu = new ArrayList<>();
    MucTieu_Adapter mucTieu_adapter;
    tabLichSuChiTieu tabLichSuChiTieu;
    ImageButton btnXemBaoCao;
    File[] files;
    LichSuActivity lichSuActivity;
    static String btnTu = "---";
    static String btnDen = "---";
    SearchView search_bar;
    int mYear, mMonth, mDay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_lich_su_tat_ca, container, false);
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String Ngay = sdf.format(new Date());
        btnDen = Ngay;
        String Nam =Ngay.split("-")[2];
        btnTu = "1-1-" + Nam;
        AnhXa(v);
        LayDuongDanFile(btnTu, btnDen);
        SuKien();
        lichSuActivity.tvTu.setText(btnTu.trim());
        lichSuActivity.tvDen.setText(btnDen.trim());
        return v;
    }

    private void SuKien() {
        btnXemBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBaoCao(lichSuActivity.tvTu.getText().toString().trim(),lichSuActivity.tvDen.getText().toString().trim());
            }
        });
        listview_MucTieu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout lnLong = view.findViewById(R.id.lnLong);
                LinearLayout lnShort = view.findViewById(R.id.lnShort);
                if (lnLong.getVisibility() == View.GONE){
                    lnLong.setVisibility(View.VISIBLE);
                    lnShort.setVisibility(View.GONE);
                }else {
                    lnLong.setVisibility(View.GONE);
                    lnShort.setVisibility(View.VISIBLE);
                }
            }
        });
        lichSuActivity.btnBoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBoLoc();
            }
        });
        listview_MucTieu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                danhsach_MucTieu.get(position).getTenFile();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Bạn muốn xóa thư mục này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File DataAppThuChi = new File(modun.ThuNhap, danhsach_MucTieu.get(position).getLoai());
                        if (danhsach_MucTieu.get(position).getPhanLoai().trim().equals("thu")){
                            DataAppThuChi = new File(modun.ThuNhap, danhsach_MucTieu.get(position).getLoai());
                        }else {
                            DataAppThuChi = new File(modun.ChiTieu, danhsach_MucTieu.get(position).getLoai());
                        }

                        File DataFile = new File(DataAppThuChi, danhsach_MucTieu.get(position).getTenFile());
                        DataFile.delete();
                        LayDuongDanFile("---","---");
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        if (!DataFile.exists()) {
                            Toast.makeText(getContext(), "Bạn đã xóa thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Bạn đã xóa không thành công.", Toast.LENGTH_SHORT).show();
                        }
                        intent.putExtra("HanhDong", "truyen2");
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
                return false;
            }
        });
    }


    public void LayDuongDanFile(String ngaymins, String ngaymaxs) {
        danhsach_MucTieu.clear();
        Toast.makeText(getContext(), ngaymaxs + ngaymins, Toast.LENGTH_SHORT).show();
        File Template = modun.ChiTieu;
        File[] DanhsachPhanloaiDaTao = Template.listFiles();
        /**LẤY TỔNG SÓ SỐ ĐÃ CHI THEO TỪNG PHÂN LOẠI*/
        for (File f : DanhsachPhanloaiDaTao) {
            LayDuLieu_MucTieu_Chi(f);
        }
        Template = modun.ThuNhap;
        File[] DanhsachPhanloaiDaTao2 = Template.listFiles();
        /**LẤY TỔNG SÓ SỐ ĐÃ CHI THEO TỪNG PHÂN LOẠI*/
        for (File f : DanhsachPhanloaiDaTao2) {
            LayDuLieu_MucTieu_Chi(f);
        }
        List<clsMucTieu> clsTest = new ArrayList<>();
        mucTieu_adapter = new MucTieu_Adapter(clsTest, getContext(), R.layout.layout_item_muctieu);
        //danhsach_MucTieu = mucTieu_adapter.sort(danhsach_MucTieu, lichSuActivity.tvTu.getText().toString().trim(), lichSuActivity.tvDen.getText().toString().trim());
        mucTieu_adapter = new MucTieu_Adapter(mucTieu_adapter.sort(danhsach_MucTieu, ngaymins, ngaymaxs), getContext(), R.layout.layout_item_muctieu);
        danhsach_MucTieu = clsMucTieu.sort(danhsach_MucTieu,ngaymins,ngaymaxs);
        listview_MucTieu.setAdapter(null);
        listview_MucTieu.setAdapter(mucTieu_adapter);
    }

    private void AnhXa(View v) {
        btnXemBaoCao = v.findViewById(R.id.btnXemBaoCao);
        listview_MucTieu = v.findViewById(R.id.listview_MucTieu);
        lichSuActivity = (LichSuActivity) getActivity();
    }

    public void DialogBoLoc() {
        final Dialog dialogthongso = new Dialog(getContext(), R.style.PauseDialog);
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.MyDatePickerDialogTheme,
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.MyDatePickerDialogTheme,
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.MyDatePickerCalendarDialogTheme,
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.MyDatePickerCalendarDialogTheme,
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
                //Toast.makeText(getContext(), "nandjasndjsandas", Toast.LENGTH_SHORT).show();
                // mucTieu_adapter.sort(danhsach_MucTieu, lichSuActivity.getTvTu().getText().toString().trim(), lichSuActivity.getTvDen().getText().toString().trim());
                //tabLichSuTatCa.mucTieu_adapter = new MucTieu_Adapter( tabLichSuTatCa.mucTieu_adapter.sort(tabLichSuTatCa.danhsach_MucTieu, tabLichSuTatCa.lichSuActivity.tvTu.getText().toString().trim(), tabLichSuTatCa.lichSuActivity.tvDen.getText().toString().trim()),tabLichSuTatCa.getContext(), R.layout.layout_item_muctieu);
                //listview_MucTieu.setAdapter(null);
                lichSuActivity.tvTu.setText(btnTu.getText().toString().trim());
                lichSuActivity.tvDen.setText(btnDen.getText().toString().trim());
                LayDuongDanFile(btnTu.getText().toString().trim(), btnDen.getText().toString().trim());
                dialogthongso.dismiss();
            }
        });
    }

    private void DialogBaoCao(String ngaymins, String ngaymaxs) {
        int bien_tinhtongchi = 0;
        int bien_tinhtongthu = 0;
        final Dialog dialogthongso = new Dialog(getContext(), R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_baocaothuchi);
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
        final TextView tvTu = dialogthongso.findViewById(R.id.tvTu);
        final TextView tvDen = dialogthongso.findViewById(R.id.tvDen);
        final TextView tvTongChi = dialogthongso.findViewById(R.id.tvTongChi);
        final TextView tvTongThu = dialogthongso.findViewById(R.id.tvTongThu);
        final ListView listViewBaoCaoChi = dialogthongso.findViewById(R.id.gridBaoCaoChi);
        final ListView listViewBaoCaoThu = dialogthongso.findViewById(R.id.gridBaoCaoThu);
        final List<clsMucTieu> danhsach_chi = new ArrayList<>();
        final List<clsMucTieu> danhsach_thu = new ArrayList<>();
        final ImageButton btnThoat = dialogthongso.findViewById(R.id.btnThoat);

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogthongso.dismiss();
            }
        });

        tvTu.setText(ngaymins);
        tvDen.setText(ngaymaxs);

        LayDuongDanFile(ngaymins, ngaymaxs);

        List<clsMucTieu> danhsach = danhsach_MucTieu;
        for (clsMucTieu doituong : danhsach_MucTieu) {
            if (doituong.getPhanLoai().trim().equals("chi")) {
                bien_tinhtongchi += Integer.valueOf(doituong.getSoTien());
                danhsach_chi.add(doituong);
            } else {
                danhsach_thu.add(doituong);
                bien_tinhtongthu += Integer.parseInt(doituong.getSoTien());
            }
        }
        tvTongChi.setText(String.format("%,d",Long.parseLong((String.valueOf(bien_tinhtongchi)).replace(",",",").replace(".",","))));
        tvTongThu.setText(String.valueOf(bien_tinhtongthu));
        BaoCao_Adapter baoCao_adapter = new BaoCao_Adapter(danhsach_chi,getContext(),R.layout.layout_item_baocaochi,bien_tinhtongchi);
        listViewBaoCaoChi.setAdapter(baoCao_adapter);
        BaoCao_Adapter baoCao_adapter1 = new BaoCao_Adapter(danhsach_thu,getContext(),R.layout.layout_item_baocaochi,bien_tinhtongthu);
        listViewBaoCaoThu.setAdapter(baoCao_adapter1);
    }

    private void LayDuLieu_MucTieu_Chi(File duongDanPhanLoaiChiTieu) {
        double tongsotien_chi = 0;
        double bien_PhanTramChi = 0;
        if (duongDanPhanLoaiChiTieu.isDirectory()) { /**KIỂM TRA CÓ PHẢI LÀ THƯ MỤC HAY KHÔNG*/
            File[] DanhsachMucTieuDaTao = duongDanPhanLoaiChiTieu.listFiles();/**lấy danh sách mục tiêu chi*/
            Arrays.sort(DanhsachMucTieuDaTao, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            for (File f : DanhsachMucTieuDaTao) {
                String data = modun.readText(f);
                String[] mangData = data.split("@");
                String NgayThangNam = mangData[0];
                String Ngay = NgayThangNam.split("-")[0];
                String ThangNam = NgayThangNam.split("-")[1] + " " + NgayThangNam.split("-")[2];
                double sotien_Chi = Double.valueOf(mangData[2]);
                String Gio = mangData[3];
                String MucTieu = mangData[1];
                String MucTieuSuDung = mangData[4].trim();
                Uri uriImage = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.icon1);

                if (MucTieuSuDung.trim().equals("thu")) {
                    File DuongDanIcon = new File(modun.ThuThap_Icon, duongDanPhanLoaiChiTieu.getName().trim() + ".txt");
                    uriImage = Uri.parse(modun.readText(DuongDanIcon).trim());
                } else if (MucTieuSuDung.trim().equals("chi")) {
                    File DuongDanIcon = new File(modun.ChiTieu_Icon, duongDanPhanLoaiChiTieu.getName().trim() + ".txt");
                    uriImage = Uri.parse(modun.readText(DuongDanIcon).trim());
                }

                tongsotien_chi = tongsotien_chi + sotien_Chi;
                clsMucTieu doituong_MucTieu = new clsMucTieu(uriImage, MucTieu, Ngay, ThangNam, Gio, String.valueOf(sotien_Chi), MucTieuSuDung, duongDanPhanLoaiChiTieu.getName().trim(), f.getName() );
                danhsach_MucTieu.add(doituong_MucTieu);

            }
        }

    }
}
