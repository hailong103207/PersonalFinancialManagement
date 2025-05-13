package com.example.quanlychitieucanhan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LichSuViActivity extends AppCompatActivity {
    ListView listview_MucTieu;
    TextView tvPhanLoai,tvTongTienChi,tvHienCo,tvSoTienBanDau;
    List<clsMucTieu> danhsach_MucTieu = new ArrayList<>();
    MucTieu_Adapter mucTieu_adapter;
    SearchView search_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichsuvi);
        AnhXa();
        NhanBien();
        SuKien();
    }

    private void SuKien() {
        listview_MucTieu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                Toast.makeText(LichSuViActivity.this, danhsach_MucTieu.get(position).getPhanLoai(), Toast.LENGTH_SHORT).show();

                danhsach_MucTieu.get(position).getTenFile();
                AlertDialog.Builder builder = new AlertDialog.Builder(LichSuViActivity.this);
                builder.setTitle("Bạn muốn xóa thư mục này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                final Intent intent2 = getIntent();
                String duongdan = intent2.getStringExtra("DuongDan");
                final String pathPhanLoai = intent2.getStringExtra("PhanLoai");// add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File DataAppThuChi = new File(modun.Vi, pathPhanLoai.trim());

                        File DataFile = new File(DataAppThuChi, danhsach_MucTieu.get(position).getTenFile().trim());

                        DataFile.delete();
                        if (!DataFile.exists()) {

                            Toast.makeText(LichSuViActivity.this, "Bạn đã xóa thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LichSuViActivity.this, "Bạn đã xóa không thành công.", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(LichSuViActivity.this, MainActivity.class);
                        intent.putExtra("DuongDan",intent2.getStringExtra("DuongDan"));
                        intent.putExtra("PhanLoai",intent2.getStringExtra("PhanLoai"));
                        intent.putExtra("HanhDong", "truyen5");
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

    private void AnhXa() {
        tvHienCo= findViewById(R.id.tvTongChi);
        tvSoTienBanDau = findViewById(R.id.tvSoTienBanDau);
        listview_MucTieu = findViewById(R.id.listview_MucTieu);
        tvPhanLoai = findViewById(R.id.tvPhanLoai);
        tvTongTienChi = findViewById(R.id.tvDaChi);
    }

    private void NhanBien()
    {
        Intent intent = getIntent();
        tvPhanLoai.setText(intent.getStringExtra("PhanLoai"));
        String duongdan = intent.getStringExtra("DuongDan");
        File pathPhanLoai = new File(duongdan, intent.getStringExtra("PhanLoai"));
        File pathVi = new File(pathPhanLoai,"SoTienTrongVi.txt");
        String SoTienTrongVi = modun.readText(pathVi);
        tvSoTienBanDau.setText(String.format("%,d",Long.parseLong(SoTienTrongVi.trim())).replace(".",",").replace(",",","));

        LayDuLieu_MucTieu_Chi(pathPhanLoai);
        tvTongTienChi.setText(String.format("%,d",Long.parseLong(String.valueOf(Math.round(LayDuLieu_MucTieu_Chi(pathPhanLoai))))).replace(".",",").replace(",",","));
        int SoTienHienCo = Integer.parseInt(tvSoTienBanDau.getText().toString().trim().replace(",","")) +Integer.parseInt(tvTongTienChi.getText().toString().trim().replace(",",""));
        tvHienCo.setText(String.format("%,d",Long.parseLong(String.valueOf(SoTienHienCo))).replace(".",",").replace(",",","));;
    }

    private double LayDuLieu_MucTieu_Chi(File duongDanPhanLoai){
        danhsach_MucTieu.clear();
        double tongsotien_chi = 0;
        if (duongDanPhanLoai.isDirectory()){ /**KIỂM TRA CÓ PHẢI LÀ THƯ MỤC HAY KHÔNG*/

            File[] DanhsachMucTieuDaTao = duongDanPhanLoai.listFiles();/**lấy danh sách mục tiêu chi*/
            for (File f:DanhsachMucTieuDaTao)
            {
                if (!f.getName().trim().equals("SoTienTrongVi.txt")){
                    String data = modun.readText(f);
                    String[] mangData = data.split("@");
                    String NgayThangNam = mangData[0];
                    String Ngay = NgayThangNam.split("-")[0];
                    String ThangNam = NgayThangNam.split("-")[1] + " " +NgayThangNam.split("-")[2];
                    double sotien_Chi = Double.valueOf(mangData[2]);
                    String Gio = mangData[3];
                    String MucTieu = mangData[1];
                    String MucTieuSuDung = mangData[4].trim();
                    tongsotien_chi=tongsotien_chi+sotien_Chi;
                    Uri uriImage= Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.icon1);
                    String test = duongDanPhanLoai.getName().trim()+".txt";
                    if (MucTieuSuDung.trim().equals("thu")){
                        File DuongDanIcon = new File(modun.ThuThap_Icon,duongDanPhanLoai.getName().trim()+".txt");
                        uriImage = Uri.parse(modun.readText(DuongDanIcon).trim());
                    }else if (MucTieuSuDung.trim().equals("chi")){
                        File DuongDanIcon = new File(modun.ChiTieu_Icon,duongDanPhanLoai.getName().trim()+".txt");
                        uriImage = Uri.parse(modun.readText(DuongDanIcon).trim());
                    }
                    clsMucTieu doituong_MucTieu = new clsMucTieu(uriImage,MucTieu,Ngay,ThangNam,Gio,String.valueOf(sotien_Chi),MucTieuSuDung,"",f.getName());
                    danhsach_MucTieu.add(doituong_MucTieu);
                    mucTieu_adapter = new MucTieu_Adapter(danhsach_MucTieu, LichSuViActivity.this,R.layout.layout_item_muctieu);
                    listview_MucTieu.setAdapter(mucTieu_adapter);
                }

            }
        }
        else return 0;
        return  tongsotien_chi;
    }
}

