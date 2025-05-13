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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TabChiTieu extends Fragment {
    ImageButton btn_ThemHocSinh;
    Button btnupdate;
    private ViewStub stubGrid;
    ImageButton btnXemLichSu;
    private ViewStub stubList;
    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    ChonViAdapter chonViAdapter;
    ListView listView;
    GridView girdView;
    IconAdapter iconAdapter;



    private ListviewAdapter listViewAdapter;
    private GridviewAdapter gridviewAdapter;
    ViewGroup viewgroup;
    private List<PhanLoai> danhsach_Phanloai;
    public int curnntView = VIEW_MODE_GRIDVIEW;
    int mMonth, mYear, mDay;

    PieChart pieChart;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    PieDataSet pieDataSet;
    PieData pieData;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_chi_tieu, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        AnhXa(view);
        // Inflate the layout for this fragment
        stubGrid = view.findViewById(R.id.stub_grid);
        stubList = view.findViewById(R.id.stub_list);
        stubList.inflate();
        stubGrid.inflate();
        girdView = (GridView) view.findViewById(R.id.myGrid);
        listView = (ListView) view.findViewById(R.id.mylistView);
        LayDuLieu_PhanLoai_Chi();
        switchView();
        setTvSoTien();
        //SharedPreferences sharedPreferences = getSharedPreferences("ViewsMode", MODE_PRIVATE);
        //curnntView = sharedPreferences.getInt("currentView", VIEW_MODE_GRIDVIEW);
        SuKien();
        return view;

    }

    private void setTvSoTien() {
        String a = mainActivity.truyen_tvThu().getText().toString();
        int sotienchi = Integer.parseInt(mainActivity.tvChi.getText().toString().trim().replace(",", ""));
        int sotienthu = Integer.parseInt(mainActivity.tvThu.getText().toString().trim().replace(",", ""));
        long sotienhientai = Long.parseLong(String.valueOf(sotienthu + sotienchi));
        mainActivity.setTvSoTien((String.format("%,d", sotienhientai)).replace(".", ",").replace(",", ","));
    }

    private void SuKien() {
        btnXemLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SplashScreen.class);
                intent.putExtra("HanhDong", "MainLichSuActivity");
                startActivity(intent);
            }
        });
        mainActivity.getSearchBarCT().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listViewAdapter.filter(mainActivity.getSearchBarCT().getQuery().toString().trim());
                gridviewAdapter.filter(mainActivity.getSearchBarCT().getQuery().toString().trim());
                return false;
            }
        });
//        btnupdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File[] ListPhanLoaiTaiSan = modun.TaiSan.listFiles();
//                for (File file : ListPhanLoaiTaiSan) {
//                    String ten = file.getName().trim();
//                    modun.saveTextFile(ten, "tstd", modun.TaiSan_ThuocTinh);
//                }
//            }
//        });
        btn_ThemHocSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogThemPhanLoaiChi();
            }
        });
        listView.setOnItemClickListener(onItemClickListener);
        girdView.setOnItemClickListener(onItemClickListener);
        listView.setOnItemLongClickListener(onItemLongClickListener);
        girdView.setOnItemLongClickListener(onItemLongClickListener);
    }

    public int battatbieudo() {
        if (pieChart.getVisibility() == View.VISIBLE) {
            pieChart.setVisibility(View.GONE);
            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(250);
            TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
            return 0;
        } else {
            pieChart.setVisibility(View.VISIBLE);
            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(150);
            TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
            return 1;
        }
    }

    public void switchView() {
        if (VIEW_MODE_LISTVIEW == curnntView) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.GONE);
            //Display gridview
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
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

    private void LayDuLieu_PhanLoai_Chi() {
        danhsach_Phanloai = new ArrayList<>();
        File Template = modun.ChiTieu;
        if (Template.exists()) {
            /**KHAI BÁO BIẾN*/
            double bien_TongTienChi = 0;
            Double bien_PhanTramChi = 0.0;
            File[] DanhsachPhanloaiDaTao = Template.listFiles();
            Double TinhTongChi = 0.0;
            Double TinhTongChiTrongNam = 0.0;
            /**LẤY TỔNG SÓ SỐ ĐÃ CHI THEO TỪNG PHÂN LOẠI*/
            for (File f : DanhsachPhanloaiDaTao) {
                if (!f.getName().equals("Icon")) {
                    bien_TongTienChi = LayDuLieu_Nam_Chi(f);
                    TinhTongChiTrongNam += LayDuLieu_Nam_Chi(f);
                    TinhTongChi = TinhTongChi + bien_TongTienChi;
                    File DataIcon = new File(modun.ChiTieu_Icon, f.getName().trim() + ".txt");
                    Uri ImageData = Uri.parse(modun.readText(DataIcon).trim());
                    PhanLoai doituong_PhanLoai = new PhanLoai(ImageData, f.getName().trim(), bien_TongTienChi, bien_PhanTramChi, "");
                    danhsach_Phanloai.add(doituong_PhanLoai);

                }
            }

            mainActivity.truyen_tvChi().setText((String.format("%,d", Long.parseLong(String.valueOf(Math.round(TinhTongChiTrongNam))))).replace(".", ",").replace(",", ","));

            /**TÍNH PHẦN TRĂM*/
            entries = new ArrayList<>();
            PieEntryLabels = new ArrayList<String>();
            for (Integer i = 0; i < DanhsachPhanloaiDaTao.length; i++) {
                bien_PhanTramChi = danhsach_Phanloai.get(i).getTongTienChi() / TinhTongChi * 100;
                danhsach_Phanloai.get(i).setPhanTramChi(modun.round(bien_PhanTramChi, 1));
                float phantram = (float) modun.round(bien_PhanTramChi, 1);
                entries.add(new BarEntry(phantram, i));
                PieEntryLabels.add(danhsach_Phanloai.get(i).getPhanLoai());
            }
            pieDataSet = new PieDataSet(entries, "");
            pieData = new PieData(PieEntryLabels, pieDataSet);
            pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            pieData.setValueTextSize(0);
            pieData.setValueTextColor(R.color.colorPrimary2);
            pieChart.setData(pieData);
            pieChart.animateY(1500);
            /**HIỂN THỊ RA MÀN HÌNH*/
            listViewAdapter = new ListviewAdapter(danhsach_Phanloai, getContext(), R.layout.list_item);
            listView.setAdapter(listViewAdapter);
            gridviewAdapter = new GridviewAdapter(danhsach_Phanloai, getContext(), R.layout.list_item);
            girdView.setAdapter(gridviewAdapter);
        }
    }

    private double LayDuLieu_MucTieu_Chi(File duongDanPhanLoai) {
        double tongsotien_chi = 0;
        if (duongDanPhanLoai.isDirectory()) { /**KIỂM TRA CÓ PHẢI LÀ THƯ MỤC HAY KHÔNG*/
            File[] DanhsachMucTieuDaTao = duongDanPhanLoai.listFiles();/**lấy danh sách mục tiêu chi*/
            for (File f : DanhsachMucTieuDaTao) {
                String data = modun.readText(f);
                String[] mangData = data.split("@");
                double sotien_Chi = Double.valueOf(mangData[2]);
                tongsotien_chi = tongsotien_chi + sotien_Chi;
            }
        } else return 0;
        return tongsotien_chi;
    }

    private double LayDuLieu_Nam_Chi(File duongDanPhanLoai) {
        double tongsotien_chi = 0;
        if (duongDanPhanLoai.isDirectory()) { /**KIỂM TRA CÓ PHẢI LÀ THƯ MỤC HAY KHÔNG*/
            File[] DanhsachMucTieuDaTao = duongDanPhanLoai.listFiles();/**lấy danh sách mục tiêu chi*/
            for (File f : DanhsachMucTieuDaTao) {
                Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String Ngay = sdf.format(new Date());
                Double Nam = Double.valueOf(Ngay.split("-")[2]);
                String data = modun.readText(f);
                String[] mangData = data.split("@");
                double sotien_Chi = Double.valueOf(mangData[2]);
                double nam_Chi = Double.valueOf(mangData[0].split("-")[2]);
                if (nam_Chi == Nam) {
                    tongsotien_chi = tongsotien_chi + sotien_Chi;
                }
            }
        } else return 0;
        return tongsotien_chi;
    }

    private void DialogSuaTen(final int position) {
        final Dialog dialogthongso = new Dialog(getContext());
        dialogthongso.setContentView(R.layout.dialog_chinhsua);
        dialogthongso.show();
        dialogthongso.setCanceledOnTouchOutside(true);
        final EditText edtChinhSua = dialogthongso.findViewById(R.id.edt_PhanLoai);
        final Button btnChinhSua = dialogthongso.findViewById(R.id.btnLuuThongSo);
        edtChinhSua.setText(danhsach_Phanloai.get(position).getPhanLoai());
        btnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtChinhSua.getText().equals("")) {
                    File filemoi = new File(modun.ChiTieu, edtChinhSua.getText().toString());//Đường dẫn đến thư mục muốn đổi mới
                    File filecu = new File(modun.ChiTieu, danhsach_Phanloai.get(position).getPhanLoai());//Đường đẫn đến thư mục cũ cần đổi tên
                    if (!filemoi.exists() || filemoi.getName().trim().equals(filecu.getName().trim())) {
                        File fileIconCu = new File(modun.ChiTieu_Icon, danhsach_Phanloai.get(position).getPhanLoai().trim() + ".txt");
                        File fileIconMoi = new File(modun.ChiTieu_Icon, edtChinhSua.getText().toString().trim() + ".txt");
                        fileIconCu.renameTo(fileIconMoi);
                        filecu.renameTo(filemoi);
                        DialogThemIcon(edtChinhSua.getText().toString().trim());
                        LayDuLieu_PhanLoai_Chi();
                        dialogthongso.dismiss();
                    } else
                        Toast.makeText(getContext(), "Bạn đã có phân loại này!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

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
                Intent intent = new Intent(getContext(), MucTieuActivity.class);
                intent.putExtra("PhanLoai", danhsach_Phanloai.get(position).getPhanLoai());
                intent.putExtra("DuongDan", modun.ChiTieu.getPath());
                startActivity(intent);
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
                        File DataAppThuChi = new File(modun.ChiTieu, danhsach_Phanloai.get(position).getPhanLoai());
                        File DataIcon = new File(modun.ChiTieu_Icon, danhsach_Phanloai.get(position).getPhanLoai() + ".txt");
                        if (DataAppThuChi.isDirectory()) {
                            String[] children = DataAppThuChi.list();
                            for (int ii = 0; ii < children.length; ii++) {
                                new File(DataAppThuChi, children[ii]).delete();
                            }
                        }
                        DataAppThuChi.delete();
                        DataIcon.delete();
                        if (!DataAppThuChi.exists()) {
                            LayDuLieu_PhanLoai_Chi();
                            dialogthongso.dismiss();
                            Toast.makeText(getContext(), "Bạn đã xóa thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Bạn đã xóa không thành công.", Toast.LENGTH_SHORT).show();
                        }

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

    private void DialogThemPhanLoaiChi() {
        final Dialog dialogthongso = new Dialog(getContext());
        dialogthongso.setContentView(R.layout.dialog_themhocsinh);
        dialogthongso.show();
        //Anh Xa
        final EditText edtPhanLoai = dialogthongso.findViewById(R.id.edt_PhanLoai);
        final Button btnLuuThongSo = dialogthongso.findViewById(R.id.btnLuuThongSo);
        //Su Kien
        btnLuuThongSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NOTE: Không được trực tiếp 1 đối tượng vào 1 đối tượng.
                String bien_PhanLoai = edtPhanLoai.getText().toString();
                if (bien_PhanLoai.trim().equals(""))
                    Toast.makeText(getContext(), R.string.thongbao, Toast.LENGTH_SHORT).show();

                //KIỂM TRA TOÀN BỘ
                if (!bien_PhanLoai.trim().equals("")) {
                    File ThuMucPhanLoai = new File(modun.ChiTieu, bien_PhanLoai.trim());///TẠO RA ĐƯỜNG DẪN THƯ MỤC MUỐN VD:"C:/DATATAAPPTHUCHI/PHANLOAI
                    if (ThuMucPhanLoai.exists()) {
                        Toast.makeText(getContext(), "Bạn đã có phân loại này", Toast.LENGTH_SHORT).show();
                    } else {
                        modun.TaoThuMuc(ThuMucPhanLoai);
                        DialogThemIcon(bien_PhanLoai.trim());
                        LayDuLieu_PhanLoai_Chi();

                        dialogthongso.dismiss();
                    }

                }

            }

        });


    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            DialogThemKhoanChi(i);
        }
    };
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            DialogMenu(i);
            return true;
        }
    };

    private void DialogThemIcon(final String PhanLoai) {
        final Dialog dialogthongso = new Dialog(getContext());
        dialogthongso.setContentView(R.layout.dialog_themicon);
        dialogthongso.show();
        dialogthongso.setCancelable(false);
        iconAdapter = new IconAdapter(getContext(), R.layout.grid_item_icon);
        final GridView gridView_icon = dialogthongso.findViewById(R.id.myGrid);
        gridView_icon.setAdapter(iconAdapter);
        dialogthongso.setCanceledOnTouchOutside(false);
        gridView_icon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                modun.saveTextFile(PhanLoai, iconAdapter.myObject.get(position).toString(), modun.ChiTieu_Icon);
                LayDuLieu_PhanLoai_Chi();
                dialogthongso.dismiss();
            }
        });
    }

    private void DialogThemKhoanChi(final int position) {
        final String Phanloai = danhsach_Phanloai.get(position).getPhanLoai().trim();
        final Dialog dialogthemkhoanchi = new Dialog(getContext());
        dialogthemkhoanchi.setContentView(R.layout.dialog_themkhoanchitieu);
        dialogthemkhoanchi.show();
        /**Lấy ngày hiện tại*/
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String Ngay = sdf.format(new Date());
        final String Gio = today.format("%k:%M:%S");
        /***/
        final EditText edtMucTieuChi = dialogthemkhoanchi.findViewById(R.id.edt_MucTieuChi);
        final EditText edtSoTienChi = dialogthemkhoanchi.findViewById(R.id.edt_SoTienChi);
        final TextView tvPhanLoai = dialogthemkhoanchi.findViewById(R.id.tvPhanLoai);
        final TextView tvTaiSan = dialogthemkhoanchi.findViewById(R.id.tvTaiSan);
        final Button btnNgayChi = dialogthemkhoanchi.findViewById(R.id.btn_NgayChi);
        btnNgayChi.setText(Ngay);
        tvPhanLoai.setText(Phanloai);
        final Button btnLuuThongSo = dialogthemkhoanchi.findViewById(R.id.btnLuuThongSo);
        final Button btnThemVi = dialogthemkhoanchi.findViewById(R.id.btnChonVi);
        final Button btnThoat = dialogthemkhoanchi.findViewById(R.id.btnThoat);
        final ImageButton btnCalendar = dialogthemkhoanchi.findViewById(R.id.btnCalendar);

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
            public void onClick(View view) {
                dialogthemkhoanchi.dismiss();
            }
        });

        btnThemVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogthemkhoanchi.dismiss();
                final Dialog thongso = new Dialog(getContext());

                thongso.setContentView(R.layout.dialog_chonvi);
                thongso.show();
                final List<PhanLoai> DanhSachVi = new ArrayList<>();
                File Template = modun.TaiSan;
                if (Template.exists()) {
                    /**KHAI BÁO BIẾN*/
                    File[] DanhsachPhanloaiDaTao = Template.listFiles();
                    /**LẤY TỔNG SÓ SỐ ĐÃ CHI THEO TỪNG PHÂN LOẠI*/
                    for (File f : DanhsachPhanloaiDaTao) {
                        if (!f.getName().equals("Icon")) {
                            File DataIcon = new File(modun.TaiSan_Icon, f.getName().trim() + ".txt");
                            Uri ImageData = Uri.parse(modun.readText(DataIcon).trim());
                            PhanLoai doituong_PhanLoai = new PhanLoai(ImageData, f.getName().trim(), 0.0, 0.0, "");
                            DanhSachVi.add(doituong_PhanLoai);
                        }

                    }
                }
                Button btnBoChon = thongso.findViewById(R.id.btnBoChon);
                btnBoChon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvTaiSan.setText("Chưa có");
                        thongso.dismiss();
                        dialogthemkhoanchi.show();
                    }
                });
                chonViAdapter = new ChonViAdapter(DanhSachVi, getContext(), R.layout.chonvi_item);
                GridView gridView = thongso.findViewById(R.id.myGrid);
                gridView.setAdapter(chonViAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tvTaiSan.setText(DanhSachVi.get(position).getPhanLoai().trim());
                        thongso.dismiss();
                        dialogthemkhoanchi.show();
                    }
                });

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
                if (!edtMucTieuChi.getText().toString().trim().equals("") &&
                        !btnNgayChi.getText().toString().trim().equals("") &&
                        !edtSoTienChi.getText().toString().trim().equals("")) {
                    //TẠO NỘI DUNG
                    String bien_sotienchi = null;
                    if (Double.valueOf(edtSoTienChi.getText().toString().trim()) > 0) {
                        bien_sotienchi = String.valueOf(Double.valueOf(edtSoTienChi.getText().toString().trim()) * -1);
                    } else bien_sotienchi = edtSoTienChi.getText().toString().trim();
                    String NoiDung_MucTieu_Chi = btnNgayChi.getText().toString() + "@" + edtMucTieuChi.getText().toString()
                            + "@" + bien_sotienchi + "@" + Gio + "@" + "chi";
//                    //Test JSON
//                    JSONObject obj = new JSONObject();
//                    obj.put("NgayChi",edtNgayChi.getText().toString());
//                    obj.put("MucTieuChi",edtNgayChi.getText().toString());
//                    obj.put("SoTien",edtNgayChi.getText().toString());
                    modun.saveTextFile("LanCuoiSuDungVi", tvTaiSan.getText().toString().trim(), modun.DataAppThuChi);
                    String TenFile = btnNgayChi.getText().toString().replace("-", "") + "@" + Gio.replace(":", "");
                    File DataAppThuChi = new File(modun.ChiTieu, danhsach_Phanloai.get(position).getPhanLoai());
                    modun.saveTextFile(TenFile, NoiDung_MucTieu_Chi, DataAppThuChi);
                    //modun.saveTextFile("TestJson",obj.toString(),DataAppThuChi);
//                    String json = (String) obj.get("TenThanhPhan");
//                    Log.d("TestJson", json);
                    LayDuLieu_PhanLoai_Chi();


                    setTvSoTien();
                    //DecimalFormat format = new DecimalFormat("0.000");

                    if (!tvTaiSan.getText().toString().trim().equals("Chưa có")) {
/*                        File SoTienTrongVi = new File(modun.Vi,tvTaiSan.getText().toString().trim());
                        File SoTien = new File(SoTienTrongVi,"SoTienTrongVi.txt");
                        String SoTienThat = String.valueOf(Integer.parseInt(modun.readText(SoTien).trim())+Integer.parseInt(edtSoTienChi.getText().toString().trim()));
                        modun.saveTextFile("SoTienTrongVi",SoTienThat,SoTienTrongVi);*/
                        String sotien = String.valueOf(Integer.parseInt(edtSoTienChi.getText().toString().trim()) * -1);
                        NoiDung_MucTieu_Chi = btnNgayChi.getText().toString() + "@" + edtMucTieuChi.getText().toString()
                                + "@" + sotien + "@" + "1" + "@" + tvTaiSan.getText().toString().trim() + "@" + sotien + "@" + "0";

                        File SoTienTrongVi = new File(modun.TaiSan, tvTaiSan.getText().toString().trim());
                        modun.saveTextFile(TenFile, NoiDung_MucTieu_Chi, SoTienTrongVi);
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    dialogthemkhoanchi.dismiss();
                }
            }
        });
    }
    protected void AnhXa(View v) {
        //region Anh Xa
        //btnSearch = v.findViewById(R.id.btnSearch);
        btn_ThemHocSinh = v.findViewById(R.id.btn_ThemHocSinh);
//        listview_HocSinh = v.findViewByI3d(R.id.listview_HocSinh);
//        layoutSearch = v.findViewById(R.id.layoutSearch);
//        search_bar = v.findViewById(R.id.search_bar);
//        search_bar.setIconifiedByDefault(false);
        btnXemLichSu = v.findViewById(R.id.btnXemLichSu);
        mainActivity = (MainActivity) getActivity();
        pieChart = (PieChart) v.findViewById(R.id.chart1);
        viewgroup = v.findViewById(R.id.viewgroup);
    }

    public void updateListView() {
        if (VIEW_MODE_LISTVIEW == curnntView) {
            curnntView = VIEW_MODE_GRIDVIEW;
            mainActivity.truyen_btnGrid().setBackgroundResource(R.drawable.ic_view_module_black_24dp);
        } else {
            curnntView = VIEW_MODE_LISTVIEW;
            mainActivity.truyen_btnGrid().setBackgroundResource(R.drawable.ic_view_list_black_24dp);

        }
        //Switch view
        switchView();
    }
}
