package com.example.quanlychitieucanhan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SplashScreen extends AppCompatActivity {
    private int SPLASH_TIME_OUT = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        NhanBien();

    }

    private void NhanBien() {
        try {
            final Intent intent = getIntent();
            String HanhDong = intent.getStringExtra("HanhDong");
            if (HanhDong.equals("MainActivityBaoCaoTaiChinhActivity")){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Time today = new Time(Time.getCurrentTimezone());
                        today.setToNow();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        String Ngay = sdf.format(new Date());
                        String DauKy = "1-1-"+Ngay.trim().split("-")[2];
                        Intent intent2 = new Intent(SplashScreen.this,BaoCaoTaiChinhActivity.class);
                        intent2.putExtra("Tu",DauKy);
                        intent2.putExtra("Den",Ngay);
                        startActivity(intent2);
                        finish();
                    }
                },SPLASH_TIME_OUT);
            }
            else if (HanhDong.equals("MainLichSuActivity")){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1 = new Intent(SplashScreen.this,LichSuActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                },SPLASH_TIME_OUT);
            }
            else if (HanhDong.equals("MainTaiSanAll")){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1 = new Intent(SplashScreen.this,TaiSanAllActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                },SPLASH_TIME_OUT);
            }

        }catch (Exception e){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }
    }
}
