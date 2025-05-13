package com.example.quanlychitieucanhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class LichSuActivity extends AppCompatActivity {

    ImageButton btnBoLoc,btnTrangChu;
    SearchView search_bar;
    TextView tvTu, tvDen;
    tabLichSuTatCa TabLichSuTatCa;
    int mYear, mMonth, mDay;
    tabLichSuTatCa tabLichSuTatCa;
    ViewPager viewPager;
    List<clsMucTieu> danhsachMucTieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);
        SectionsPagerAdapter_LichSu sectionsPagerAdapter = new SectionsPagerAdapter_LichSu(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        AnhXa();
        SuKien();
    }

    public TextView getTvTu() {
        return tvTu;
    }



    public TextView getTvDen() {
        return tvDen;
    }



    private void AnhXa() {
        btnBoLoc = findViewById(R.id.btnBoLoc);
        search_bar = findViewById(R.id.search_bar);
        tvTu = findViewById(R.id.tvTu);
        tvDen = findViewById(R.id.tvDen);
        tabLichSuTatCa = (tabLichSuTatCa) viewPager.getAdapter().instantiateItem(viewPager, 0);
        btnTrangChu = findViewById(R.id.btnTrangChu);
    }

    private void SuKien() {
        btnTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LichSuActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnBoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabLichSuTatCa.LayDuongDanFile("10-10-2022", "10-10-2024");
                Toast.makeText(LichSuActivity.this,"Chức năng đang phát triển",Toast.LENGTH_SHORT).show();
            }
        });
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(LichSuActivity.this, "chức năng đang phát triển", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }



}