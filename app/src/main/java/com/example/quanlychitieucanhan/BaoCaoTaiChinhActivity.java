package com.example.quanlychitieucanhan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class BaoCaoTaiChinhActivity extends AppCompatActivity {
    TextView tvTu, tvDen, tvChi, tvThu, tvTaiSan;
    int mYear, mMonth, mDay;
    ViewPager viewPager;
    Button btnChinhSua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocaotaichinh);
        SectionsPagerAdapter_BaoCaoTaiChinh sectionsPagerAdapter = new SectionsPagerAdapter_BaoCaoTaiChinh(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        AnhXa();
        NhanBien();
        SuKien();
    }

    public TextView getTvChi() {
        return tvChi;
    }

    public TextView getTvThu() {
        return tvThu;
    }

    public TextView getTvTaiSan() {
        return tvTaiSan;
    }

    private void AnhXa() {
        tvTu = findViewById(R.id.tvTu);
        tvDen = findViewById(R.id.tvDen);
        tvTaiSan = findViewById(R.id.tvTaiSan);
        btnChinhSua = findViewById(R.id.btnBoLoc);
        tvThu = findViewById(R.id.tvThu);
        tvChi = findViewById(R.id.tvChi);
    }

    private void NhanBien() {
        Intent intent = getIntent();
        tvTu.setText(intent.getStringExtra("Tu"));
        tvDen.setText(intent.getStringExtra("Den"));
        Toast.makeText(BaoCaoTaiChinhActivity.this, "`Báo cáo tài chính từ " + intent.getStringExtra("Tu") + " đến " + intent.getStringExtra("Den"), Toast.LENGTH_SHORT).show();

    }

    private void SuKien() {
        btnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBoLoc();
            }
        });
    }

    public void DialogBoLoc() {
        final Dialog dialogthongso = new Dialog(BaoCaoTaiChinhActivity.this, R.style.PauseDialog);
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

        btnTu.setText(tvTu.getText().toString().trim());
        btnDen.setText(tvDen.getText().toString().trim());

        btnTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(BaoCaoTaiChinhActivity.this, R.style.MyDatePickerDialogTheme,
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(BaoCaoTaiChinhActivity.this, R.style.MyDatePickerDialogTheme,
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(BaoCaoTaiChinhActivity.this, R.style.MyDatePickerCalendarDialogTheme,
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(BaoCaoTaiChinhActivity.this, R.style.MyDatePickerCalendarDialogTheme,
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

                //Toast.makeText(BaoCaoTaiChinhActivity.this, "nandjasndjsandas", Toast.LENGTH_SHORT).show();
                // mucTieu_adapter.sort(danhsach_MucTieu, lichSuActivity.getTvTu().getText().toString().trim(), lichSuActivity.getTvDen().getText().toString().trim());
                //tabLichSuTatCa.mucTieu_adapter = new MucTieu_Adapter( tabLichSuTatCa.mucTieu_adapter.sort(tabLichSuTatCa.danhsach_MucTieu, tabLichSuTatCa.lichSuActivity.tvTu.getText().toString().trim(), tabLichSuTatCa.lichSuActivity.tvDen.getText().toString().trim()),tabLichSuTatCa.BaoCaoTaiChinhActivity.this, R.layout.layout_item_muctieu);
                //listview_MucTieu.setAdapter(null)
                int NgayMin = Integer.parseInt(btnTu.getText().toString().trim().split("-")[0]) + Integer.parseInt(btnTu.getText().toString().trim().split("-")[1]) * 30 + Integer.parseInt(btnTu.getText().toString().trim().split("-")[2]) * 365;
                int NgayMax = Integer.parseInt(btnDen.getText().toString().trim().split("-")[0]) + Integer.parseInt(btnDen.getText().toString().trim().split("-")[1]) * 30 + Integer.parseInt(btnDen.getText().toString().trim().split("-")[2]) * 365;
                if (NgayMin < NgayMax) {
                    Intent intent = new Intent(BaoCaoTaiChinhActivity.this, BaoCaoTaiChinhActivity.class);
                    intent.putExtra("Tu", btnTu.getText().toString().trim());
                    intent.putExtra("Den", btnDen.getText().toString().trim());
                    startActivity(intent);
                    dialogthongso.dismiss();
                } else {
                    Toast.makeText(BaoCaoTaiChinhActivity.this, "Vui lòng không nhập sai ngày", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public TextView getTvTu() {
        return tvTu;
    }

    public TextView getTvDen() {
        return tvDen;
    }

}