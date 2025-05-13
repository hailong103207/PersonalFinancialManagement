package com.example.quanlychitieucanhan;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.quanlychitieucanhan.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.File;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    ImageButton btnGrid, btnChart, btnEdit;
    TextView tvTongSoTien, tvThu, tvChi, tvTaiSan,btnBaoCaoTaiChinh;
    ViewPager viewPager;
    static ImageButton btnSearch;
    SearchView searchBar;
    SearchView searchBarCT;
    SearchView searchBarTN;
    SearchView searchBarTS;
    LinearLayout lnSearchbar;
    LinearLayout lnSearchHide;
    TabTaiSan tabTaiSan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CapQuyenBoNho();
        NhanBienBanDau();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        modun.TaoThuMuc(modun.DataAppThuChi);
        modun.TaoThuMuc(modun.ChiTieu);
        modun.TaoThuMuc(modun.ThuNhap);
        modun.TaoThuMuc(modun.Vi);
        modun.TaoThuMuc(modun.TaiSan);
        modun.TaoThuMuc(modun.ThuocTinh);
        modun.TaoThuMuc(modun.Icon);
        modun.TaoThuMuc(modun.TaiSan_ThuocTinh);
        modun.TaoThuMuc(modun.TaiSan_Icon);
        modun.TaoThuMuc(modun.ThuThap_Icon);
        modun.TaoThuMuc(modun.ChiTieu_Icon);
        modun.TaoThuMuc(modun.Vi_Icon);
        AnhXa();
        SuKien();
//        NhanBien();
    }

    private void NhanBienBanDau() {
        try {
            Intent intent = getIntent();
            String abc = intent.getStringExtra("HanhDong");
            if (abc.equals("truyen")){
                Intent intent1 = new Intent(MainActivity.this, TaiSanActivity.class);
                intent1.putExtra("PhanLoai",intent.getStringExtra("PhanLoai"));
                intent1.putExtra("DuongDan",intent.getStringExtra("DuongDan"));
                startActivity(intent1);
            }
            else if (abc.equals("truyen2")){
                Intent intent1 = new Intent(MainActivity.this, LichSuActivity.class);

                startActivity(intent1);
            }
            else if (abc.equals("truyen3")){
                Intent intent1 = new Intent(MainActivity.this, TaiSanAllActivity.class);

                startActivity(intent1);
            }
            else if (abc.equals("truyen4")){
                Intent intent1 = new Intent(MainActivity.this, MucTieuActivity.class);
                intent1.putExtra("PhanLoai",intent.getStringExtra("PhanLoai"));
                intent1.putExtra("DuongDan",intent.getStringExtra("DuongDan"));
                startActivity(intent1);
            }
            else if (abc.equals("truyen5")){
                Intent intent1 = new Intent(MainActivity.this, LichSuViActivity.class);
                intent1.putExtra("PhanLoai",intent.getStringExtra("PhanLoai"));
                intent1.putExtra("DuongDan",intent.getStringExtra("DuongDan"));
                startActivity(intent1);
            }

        }catch (Exception e){

        }
    }

    public TextView getTvTongSoTien() {
        return tvTongSoTien;
    }

/*
    private void NhanBien() {
        File sotientrongvi = new File(Environment.getExternalStorageDirectory(), "SoTienTrongVi.txt");
        if (sotientrongvi.exists()) {
            if (modun.readText(sotientrongvi).trim().equals("")) {
                modun.saveTextFile("SoTienTrongVi", "0", Environment.getExternalStorageDirectory());
            }
            String tientrongvi = modun.readText(new File(Environment.getExternalStorageDirectory(), "SoTienTrongVi.txt"));
            Double sotien = Double.valueOf(tientrongvi);
            tvTongSoTien.setText(String.valueOf(Math.round(sotien)));
        } else {
            modun.saveTextFile("SoTienTrongVi", "0", Environment.getExternalStorageDirectory());
            tvTongSoTien.setText("0");
        }

    }
*/

    private void SuKien() {
        btnBaoCaoTaiChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SplashScreen.class);
                intent.putExtra("HanhDong","MainActivityBaoCaoTaiChinhActivity");
                startActivity(intent);
            }
        });
        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setQuery("",false);
                TabChiTieu tabChiTieu = (TabChiTieu) viewPager.getAdapter().instantiateItem(viewPager, 0);
                tabChiTieu.updateListView();
                TabThuNhap tabThuNhap = (TabThuNhap) viewPager.getAdapter().instantiateItem(viewPager, 1);
                tabThuNhap.updateListView();
                TabTaiSan tabTaiSan = (TabTaiSan) viewPager.getAdapter().instantiateItem(viewPager, 2);
                tabTaiSan.updateListView();
            }

        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lnSearchbar.getVisibility() == View.GONE){
                    lnSearchbar.setVisibility(View.VISIBLE);
                    lnSearchHide.setVisibility(View.GONE);
                }
                else if (lnSearchbar.getVisibility() == View.VISIBLE){
                    searchBar.setQuery("",false);
                    lnSearchHide.setVisibility(View.VISIBLE);
                    lnSearchbar.setVisibility(View.GONE);
                }

            }
        });
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchBarCT.setQuery(searchBar.getQuery().toString().trim(),false);
                searchBarTN.setQuery(searchBar.getQuery().toString().trim(),false);
                searchBarTS.setQuery(searchBar.getQuery().toString().trim(),false);
                return false;
            }
        });
    }

    public SearchView getSearchBar() {
        return searchBar;
    }

    private void DialogSuaTienTrongVi() {
        final Dialog dialogthongso = new Dialog(MainActivity.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_chinhsua);
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
        final EditText edtChinhSua = dialogthongso.findViewById(R.id.edt_PhanLoai);
        final Button btnChinhSua = dialogthongso.findViewById(R.id.btnLuuThongSo);
        edtChinhSua.setText(tvTongSoTien.getText().toString());
        btnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtChinhSua.getText().equals("")) {
                    modun.saveTextFile("SoTienTrongVi", edtChinhSua.getText().toString(), new File(Environment.getExternalStorageDirectory().getPath()));
                    Double sotien = Double.valueOf(edtChinhSua.getText().toString().trim().replace(",", "."));
                    tvTongSoTien.setText(String.valueOf(sotien));
                    dialogthongso.dismiss();
                }
            }
        });
    }

    public void setTvSoTien( String tvSoTien ){
        tvTongSoTien.setText(tvSoTien);
    }



    public ImageButton truyen_btnGrid() {
        return btnGrid;
    }

    public TextView truyen_tvChi() {
        return tvChi;
    }

    public TextView truyen_tvThu() {
        return tvThu;
    }

    public TextView truyen_tvTaiSan() {
        return tvTaiSan;
    }


    public SearchView getSearchBarCT() {
        return searchBarCT;
    }
    public SearchView getSearchBarTS() {
        return searchBarTS;
    }

    public SearchView getSearchBarTN() {
        return searchBarTN;
    }

    private void AnhXa() {
        tvTaiSan = findViewById(R.id.tvTaiSan);
        btnGrid = findViewById(R.id.btnGird);
        btnSearch = findViewById(R.id.btnSearch);
        btnBaoCaoTaiChinh = findViewById(R.id.btnBaoCaoTaiChinh);
        lnSearchbar = findViewById(R.id.lnSearchbar);
        searchBar = findViewById(R.id.search_bar);
        searchBarCT = findViewById(R.id.search_barCT);
        searchBarTN = findViewById(R.id.search_barTN);
        searchBarTS = findViewById(R.id.search_barTS);
        lnSearchHide = findViewById(R.id.lnSearchHide);
        tvChi = findViewById(R.id.tvChi);
        tvThu = findViewById(R.id.tvThu);
        tabTaiSan = (TabTaiSan) viewPager.getAdapter().instantiateItem(viewPager, 2);
        tvTongSoTien = findViewById(R.id.tvTongSoTien);
    }
    public void LayDuLieuTabTaiSan(){
        tabTaiSan.SetKetQua();
    }
    //region Cấp quyền
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 88;
    public static final int RequestPermissionCode = 1;
    LocationManager locationManager;
    String provider;
    public Boolean CapQuyenBoNho(){
        Boolean Permission = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(MainActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Đang đợi cấp quyền, vui lòng đợi trong giây lát!", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
                Permission= true;
            }
            else
            {
                Permission= false;

            }
        }
        return Permission;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{ACCESS_FINE_LOCATION},RequestPermissionCode);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_STORAGE:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }
    //endregion

}