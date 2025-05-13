package com.example.quanlychitieucanhan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XemViActivity extends AppCompatActivity {
    ListView listview_MucTieu;
    ImageButton btn_ThemVi;
    TextView tvPhanLoai, tvTongTienChi;
    ImageButton btnGrid;
    private ViewStub stubGrid;
    IconAdapter iconAdapter;
    TextView tvTongChi;
    private ViewStub stubList;
    final int VIEW_MODE_LISTVIEW = 0;
    final int VIEW_MODE_GRIDVIEW = 1;
    ListView listView;
    int curnntView = VIEW_MODE_GRIDVIEW;
    private List<PhanLoai> danhsach_Phanloai;

    GridView girdView;
    List<clsMucTieu> danhsach_MucTieu = new ArrayList<>();
    MucTieu_Adapter mucTieu_adapter;
    SearchView search_bar;
    private ListviewAdapter listViewAdapter;
    private GridviewAdapter gridviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemvi);
        stubGrid = findViewById(R.id.stub_grid);
        stubList = findViewById(R.id.stub_list);
        stubList.inflate();
        stubGrid.inflate();
        girdView = (GridView) findViewById(R.id.myGrid);
        listView = (ListView) findViewById(R.id.mylistView);
        AnhXa();
        LayDuLieu_PhanLoai_Chi();
        SuKien();
        switchView();
    }

    private void SuKien() {
        listView.setOnItemClickListener(onItemClickListener);
        listView.setOnItemLongClickListener(onItemLongClickListener);
        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListView();
            }
        });
        girdView.setOnItemClickListener(onItemClickListener);
        girdView.setOnItemLongClickListener(onItemLongClickListener);
        btn_ThemVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemVi();
            }
        });
/*        listview_MucTieu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                Toast.makeText(XemViActivity.this, danhsach_MucTieu.get(position).getPhanLoai(), Toast.LENGTH_SHORT).show();

                danhsach_MucTieu.get(position).getTenFile();
                AlertDialog.Builder builder = new AlertDialog.Builder(XemViActivity.this);
                builder.setTitle("Bạn muốn xóa thư mục này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                final Intent intent2 = getIntent();
                String duongdan = intent2.getStringExtra("DuongDan");
                final String pathPhanLoai = intent2.getStringExtra("PhanLoai");// add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File DataAppThuChi = new File(modun.ThuNhap, danhsach_MucTieu.get(position).getLoai());
                        if (danhsach_MucTieu.get(position).getPhanLoai().trim().equals("thu")){
                            DataAppThuChi = new File(modun.ThuNhap, pathPhanLoai.trim());
                        }else {
                            DataAppThuChi = new File(modun.ChiTieu, pathPhanLoai.trim());
                        }

                        File DataFile = new File(DataAppThuChi, danhsach_MucTieu.get(position).getTenFile().trim());

                        DataFile.delete();
                        if (!DataFile.exists()) {

                            Toast.makeText(XemViActivity.this, "Bạn đã xóa thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(XemViActivity.this, "Bạn đã xóa không thành công.", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(XemViActivity.this, MainActivity.class);
                        intent.putExtra("DuongDan",intent2.getStringExtra("DuongDan"));
                        intent.putExtra("PhanLoai",intent2.getStringExtra("PhanLoai"));
                        intent.putExtra("HanhDong", "truyen4");
                        startActivity(intent);
                        *//*danhsach_HocSinh.remove(position);
                        hocSinh_adapter = new HocSinh_Adapter(danhsach_HocSinh,MainActivity.this,R.layout.my_layout_item);
                        listview_HocSinh.setAdapter(hocSinh_adapter);*//*

                    }
                });
                builder.setNegativeButton("không", null);
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();


                return false;
            }
        });*/
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String PhanLoai = danhsach_Phanloai.get(i).getPhanLoai().trim();
            DialogSoTien(PhanLoai);
        }
    };
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            DialogMenu(position);
            return true;
        }
    };

    private void DialogMenu(final int position) {
        final Dialog dialogthongso = new Dialog(XemViActivity.this, R.style.PauseDialog);
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
                Intent intent = new Intent(XemViActivity.this, LichSuViActivity.class);
                intent.putExtra("PhanLoai", danhsach_Phanloai.get(position).getPhanLoai());
                intent.putExtra("DuongDan", modun.Vi.getPath());
                startActivity(intent);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(XemViActivity.this);
                builder.setTitle("Bạn muốn xóa thư mục này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File DataAppThuChi = new File(modun.Vi, danhsach_Phanloai.get(position).getPhanLoai());
                        File DataIcon = new File(modun.Vi_Icon, danhsach_Phanloai.get(position).getPhanLoai() + ".txt");
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
                            Toast.makeText(XemViActivity.this, "Bạn đã xóa thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(XemViActivity.this, "Bạn đã xóa không thành công.", Toast.LENGTH_SHORT).show();
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

    private void DialogSuaTen(final int position) {
        final Dialog dialogthongso = new Dialog(XemViActivity.this);
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
                    File filemoi = new File(modun.Vi, edtChinhSua.getText().toString());//Đường dẫn đến thư mục muốn đổi mới
                    File filecu = new File(modun.Vi, danhsach_Phanloai.get(position).getPhanLoai());//Đường đẫn đến thư mục cũ cần đổi tên
                    if (!filemoi.exists()) {
                        File fileIconCu = new File(modun.Vi_Icon, danhsach_Phanloai.get(position).getPhanLoai().trim() + ".txt");
                        File fileIconMoi = new File(modun.Vi_Icon, edtChinhSua.getText().toString().trim() + ".txt");
                        fileIconCu.renameTo(fileIconMoi);
                        filecu.renameTo(filemoi);
                        LayDuLieu_PhanLoai_Chi();
                        dialogthongso.dismiss();
                    } else
                        Toast.makeText(XemViActivity.this, "Bạn đã có phân loại này!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void DialogThemVi() {
        final Dialog dialogthongso = new Dialog(XemViActivity.this);
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
                    Toast.makeText(XemViActivity.this, R.string.thongbao, Toast.LENGTH_SHORT).show();

                //KIỂM TRA TOÀN BỘ
                if (!bien_PhanLoai.trim().equals("")) {
                    File ThuMucPhanLoai = new File(modun.Vi, bien_PhanLoai.trim());///TẠO RA ĐƯỜNG DẪN THƯ MỤC MUỐN VD:"C:/DATATAAPPTHUCHI/PHANLOAI
                    if (ThuMucPhanLoai.exists()) {
                        Toast.makeText(XemViActivity.this, "Bạn đã có phân loại này", Toast.LENGTH_SHORT).show();
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

    private void LayDuLieu_PhanLoai_Chi() {
        danhsach_Phanloai = new ArrayList<>();
        File Template = modun.Vi;
        if (Template.exists()) {
            /**KHAI BÁO BIẾN*/
            double bien_TongTienChi = 0;
            Double bien_PhanTramChi = 0.0;
            Double TinhTongChi = 0.0;
            File[] DanhsachPhanloaiDaTao = Template.listFiles();
            /**LẤY TỔNG SÓ SỐ ĐÃ CHI THEO TỪNG PHÂN LOẠI*/
            for (File f : DanhsachPhanloaiDaTao) {
                if (!f.getName().equals("Icon")) {
                    bien_TongTienChi = LayDuLieu_SoTIen_Vi(f);
                    TinhTongChi = TinhTongChi + bien_TongTienChi;
                    File DataIcon = new File(modun.Vi_Icon, f.getName().trim() + ".txt");
                    Uri ImageData = Uri.parse(modun.readText(DataIcon).trim());
                    PhanLoai doituong_PhanLoai = new PhanLoai(ImageData, f.getName().trim(), bien_TongTienChi, bien_PhanTramChi,"");
                    danhsach_Phanloai.add(doituong_PhanLoai);
                }
            }
            for (Integer i = 0; i < DanhsachPhanloaiDaTao.length; i++) {
                bien_PhanTramChi = danhsach_Phanloai.get(i).getTongTienChi() / TinhTongChi * 100;
                danhsach_Phanloai.get(i).setPhanTramChi(modun.round(bien_PhanTramChi, 1));
            }
            tvTongChi.setText((String.format("%,d", Long.parseLong(String.valueOf(Math.round(TinhTongChi))))).replace(".", ",").replace(",", ","));
            /**HIỂN THỊ RA MÀN HÌNH*/
            listViewAdapter = new ListviewAdapter(danhsach_Phanloai, XemViActivity.this, R.layout.list_item);
            listView.setAdapter(listViewAdapter);
            gridviewAdapter = new GridviewAdapter(danhsach_Phanloai, XemViActivity.this, R.layout.list_item);
            girdView.setAdapter(gridviewAdapter);
        }
    }

    private double LayDuLieu_SoTIen_Vi(File f) {
        double tongsotien_chi = 0;
        if (f.isDirectory()) { /**KIỂM TRA CÓ PHẢI LÀ THƯ MỤC HAY KHÔNG*/
            File[] DanhsachMucTieuDaTao = f.listFiles();/**lấy danh sách mục tiêu chi*/
            for (File file : DanhsachMucTieuDaTao) {
                if (!file.getName().trim().equals("SoTienTrongVi.txt")){
                    String data = modun.readText(file);
                    String[] mangData = data.split("@");
                    double sotien_Chi = Double.valueOf(mangData[2]);
                    tongsotien_chi = tongsotien_chi + sotien_Chi;
                }else {
                    String data = modun.readText(file);
                    double sotien_Chi = Double.valueOf(data.trim());
                    tongsotien_chi = tongsotien_chi + sotien_Chi;
                }

            }
        } else return 0;
        return tongsotien_chi;
    }

    private void DialogThemIcon(final String PhanLoai) {
        final Dialog dialogthongso = new Dialog(XemViActivity.this);
        dialogthongso.setContentView(R.layout.dialog_themicon);
        dialogthongso.show();
        iconAdapter = new IconAdapter(XemViActivity.this, R.layout.grid_item_icon);
        final GridView gridView_icon = dialogthongso.findViewById(R.id.myGrid);
        gridView_icon.setAdapter(iconAdapter);
        dialogthongso.setCanceledOnTouchOutside(false);
        gridView_icon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                modun.saveTextFile(PhanLoai, iconAdapter.myObject.get(position).toString(), modun.Vi_Icon);
                File file = new File(modun.Vi, PhanLoai);
                modun.saveTextFile("SoTienTrongVi", "0", file);
                DialogSoTien(PhanLoai);
                dialogthongso.dismiss();
            }
        });
    }

    private void DialogSoTien(final String PhanLoai) {
        final Dialog dialogthongso = new Dialog(XemViActivity.this);
        dialogthongso.setContentView(R.layout.dialog_sotientrongvi);
        dialogthongso.show();
        dialogthongso.setCanceledOnTouchOutside(false);
        final EditText edtPhanLoai = dialogthongso.findViewById(R.id.edt_PhanLoai);
        Button btnLuuThongSo = dialogthongso.findViewById(R.id.btnLuuThongSo);
        final File SoTienTrongVi = new File(modun.Vi, PhanLoai);
        File SoTienThat = new File(SoTienTrongVi, "SoTienTrongVi.txt");
        final String Sotien = modun.readText(SoTienThat).trim();
        edtPhanLoai.setText(Sotien);
        btnLuuThongSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPhanLoai.getText().toString().trim().equals("")){
                    Toast.makeText(XemViActivity.this,"Vui lòng nhập đủ dữ liệu",Toast.LENGTH_SHORT).show();
                }else {
                    modun.saveTextFile("SoTienTrongVi", edtPhanLoai.getText().toString().trim(), SoTienTrongVi);
                    LayDuLieu_PhanLoai_Chi();
                    dialogthongso.dismiss();
                }

            }
        });
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

    private void AnhXa() {
        tvTongChi = findViewById(R.id.tvTongChi);
        btn_ThemVi = findViewById(R.id.btn_ThemVi);
        btnGrid = findViewById(R.id.btnGird);
        listview_MucTieu = findViewById(R.id.listview_MucTieu);
        tvPhanLoai = findViewById(R.id.tvPhanLoai);
        tvTongTienChi = findViewById(R.id.tvTongChi);
    }

    public void setAdapters() {
        if (VIEW_MODE_LISTVIEW == curnntView) {
            listViewAdapter = new ListviewAdapter(danhsach_Phanloai, XemViActivity.this, R.layout.list_item);
            listView.setAdapter(listViewAdapter);
        } else {
            gridviewAdapter = new GridviewAdapter(danhsach_Phanloai, XemViActivity.this, R.layout.grid_item);
            girdView.setAdapter(gridviewAdapter);
        }
    }

    public void updateListView() {
        if (VIEW_MODE_LISTVIEW == curnntView) {
            curnntView = VIEW_MODE_GRIDVIEW;
            btnGrid.setBackgroundResource(R.drawable.ic_view_module_black_24dp);
        } else {
            curnntView = VIEW_MODE_LISTVIEW;
            btnGrid.setBackgroundResource(R.drawable.ic_view_list_black_24dp);
        }
        //Switch view
        switchView();
    }

}

