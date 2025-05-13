package com.example.quanlychitieucanhan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tabBaoCaoTaiSan extends Fragment {

    LinearLayout btnShowDT, btnShowNO, btnShowTM, btnShowTD;
    LinearLayout btnShowDauKyDT, btnShowDauKyNO, btnShowDauKyTM, btnShowDauKyTD, btnShowTMDT, btnShowTMTD, btnShowKHTD, btnShowKHDT;
    TextView tvTMDT, tvKhauHaoTC, tvThuChiTC, tvTMTD, tvCuoiKyTTTC;
    ListView lvPhanLoaiNO, lvPhanLoaiTM, lvPhanLoaiDT, lvPhanLoaiTD, lvTMDT, lvTMTD, lvKHDT, lvKHTD;
    ListView lvDauKyTD, lvDauKyNO, lvDauKyTM, lvDauKyDT;
    BaoCaoTaiChinhActivity baoCaoTaiChinhActivity;


    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


    TextView tvDauKyTC, tvCuoiKyTC;
    TextView tvDauKyNO, tvKHNO, tvCuoiKyNO;
    TextView tvDauKyDT, tvKHDT, tvCuoiKyDT;
    TextView tvDauKyTD, tvKHTD, tvCuoiKyTD;
    TextView tvDauKyTM, tvKHTM, tvCuoiKyTM;

    int intMuaThemDT, intKhauHaoTC = 0, intKhauHaoTD = 0, intKhauHaoTM = 0, intMuaThemTC = 0, intKhauHaoNO = 0, intKhauHaoDT = 0, intBienDongThiTruongTC = 0;
    int intMuaThemTD, intThuChi;
    TextView tvKHTTD;
    TextView tvKHTDT;
    TextView tvKHTTM;
    TextView tvKHTNO;

    List<clsTaiSan> DanhSachTSTD = new ArrayList<>();
    List<clsTaiSan> DanhSachTSTM = new ArrayList<>();
    List<clsTaiSan> DanhSachTSDT = new ArrayList<>();
    List<clsTaiSan> DanhSachTSNO = new ArrayList<>();

    List<clsTaiSan> DanhSachDauKyTD = new ArrayList<>();
    List<clsTaiSan> DanhSachDauKyTM = new ArrayList<>();
    List<clsTaiSan> DanhSachDauKyDT = new ArrayList<>();
    List<clsTaiSan> DanhSachDauKyNO = new ArrayList<>();

    List<clsTaiSan> DanhSachKHTrongKyTD = new ArrayList<>();
    List<clsTaiSan> DanhSachKHTrongKyDT = new ArrayList<>();
    List<clsTaiSan> DanhSachTrongKyTD = new ArrayList<>();
    List<clsTaiSan> DanhSachTrongKyTM = new ArrayList<>();
    List<clsTaiSan> DanhSachTrongKyDT = new ArrayList<>();
    List<clsTaiSan> DanhSachTrongKyNO = new ArrayList<>();

    List<Integer> lstBienDongTD, lstBienDongNO, lstBienDongTM, lstBienDongDT;

    double tongTienChiTD = 0, phanTramChiTD = 0;
    double tongTienChiDT = 0, phanTramChiDT = 0;
    double tongTienChiNO = 0, phanTramChiNO = 0;
    double tongTienChiTM = 0, phanTramChiTM = 0;

    double taisandaukyTC = 0;
    double taisandaukyTD = 0;
    double taisandaukyDT = 0;
    double taisandaukyTM = 0;
    double taisandaukyNO = 0;

    double taisancuoikyTC = 0;
    double taisancuoikyTD = 0;
    double taisancuoikyDT = 0;
    double taisancuoikyTM = 0;
    double taisancuoikyNO = 0;

    List<PhanLoai> DanhSachPhanLoaiTD = new ArrayList<>(), DanhSachPhanLoaiDT = new ArrayList<>(), DanhSachPhanLoaiTM = new ArrayList<>(), DanhSachPhanLoaiNO = new ArrayList<>();
    List<PhanLoai> DanhSachPhanLoaiDauKyTD = new ArrayList<>(), DanhSachPhanLoaiDauKyDT = new ArrayList<>(), DanhSachPhanLoaiDauKyTM = new ArrayList<>(), DanhSachPhanLoaiDauKyNO = new ArrayList<>();
    private ListviewAdapter listViewAdapterTD, listViewAdapterDT, listViewAdapterTM, listViewAdapterNO;

    public tabBaoCaoTaiSan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_baocaotaisan, container, false);
        AnhXa(v);
        SuKien();
        try {
            LayDuongDanFile();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        settvThuChiTC();
        return v;
    }

    private void settvThuChiTC() {
        int intChi = Integer.parseInt(baoCaoTaiChinhActivity.getTvChi().getText().toString().trim().replace(",", ""));
        int intThu = Integer.parseInt(baoCaoTaiChinhActivity.getTvThu().getText().toString().trim().replace(",", ""));
        int ThuChi = intThu + intChi;
        intThuChi = ThuChi;
        tvThuChiTC.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(ThuChi))))).replace(",", ",").replace(".", ","));

    }

    private void SuKien() {
        //region KEO DAI VA THU GON
        //region CUOI KY
        btnShowDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvPhanLoaiDT.getVisibility() == View.GONE) {
                    lvPhanLoaiDT.setVisibility(View.VISIBLE);

                } else if (lvPhanLoaiDT.getVisibility() == View.VISIBLE) {
                    lvPhanLoaiDT.setVisibility(View.GONE);

                }
            }
        });

        btnShowTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvPhanLoaiTD.getVisibility() == View.GONE) {
                    lvPhanLoaiTD.setVisibility(View.VISIBLE);

                } else if (lvPhanLoaiTD.getVisibility() == View.VISIBLE) {
                    lvPhanLoaiTD.setVisibility(View.GONE);

                }
            }
        });

        btnShowTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvPhanLoaiTM.getVisibility() == View.GONE) {
                    lvPhanLoaiTM.setVisibility(View.VISIBLE);

                } else if (lvPhanLoaiTM.getVisibility() == View.VISIBLE) {
                    lvPhanLoaiTM.setVisibility(View.GONE);

                }
            }
        });

        btnShowNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvPhanLoaiNO.getVisibility() == View.GONE) {
                    lvPhanLoaiNO.setVisibility(View.VISIBLE);

                } else if (lvPhanLoaiNO.getVisibility() == View.VISIBLE) {
                    lvPhanLoaiNO.setVisibility(View.GONE);

                }
            }
        });
        //endregion
        //region DAU KY

        btnShowDauKyDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvDauKyDT.getVisibility() == View.GONE) {
                    lvDauKyDT.setVisibility(View.VISIBLE);
                } else if (lvDauKyDT.getVisibility() == View.VISIBLE) {
                    lvDauKyDT.setVisibility(View.GONE);
                }
            }
        });

        btnShowDauKyTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvDauKyTD.getVisibility() == View.GONE) {
                    lvDauKyTD.setVisibility(View.VISIBLE);

                } else if (lvDauKyTD.getVisibility() == View.VISIBLE) {
                    lvDauKyTD.setVisibility(View.GONE);

                }
            }
        });

        btnShowDauKyTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvDauKyTM.getVisibility() == View.GONE) {
                    lvDauKyTM.setVisibility(View.VISIBLE);

                } else if (lvDauKyTM.getVisibility() == View.VISIBLE) {
                    lvDauKyTM.setVisibility(View.GONE);

                }
            }
        });

        btnShowDauKyNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvDauKyNO.getVisibility() == View.GONE) {
                    lvDauKyNO.setVisibility(View.VISIBLE);

                } else if (lvDauKyNO.getVisibility() == View.VISIBLE) {
                    lvDauKyNO.setVisibility(View.GONE);

                }
            }
        });


        //endregion

        //region TRONG KY
        btnShowTMDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvTMDT.getVisibility() == View.GONE) {
                    lvTMDT.setVisibility(View.VISIBLE);

                } else if (lvTMDT.getVisibility() == View.VISIBLE) {
                    lvTMDT.setVisibility(View.GONE);

                }
            }
        });
        btnShowKHDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvKHDT.getVisibility() == View.GONE) {
                    lvKHDT.setVisibility(View.VISIBLE);

                } else if (lvKHDT.getVisibility() == View.VISIBLE) {
                    lvKHDT.setVisibility(View.GONE);

                }
            }
        });
        btnShowTMTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvTMTD.getVisibility() == View.GONE) {
                    lvTMTD.setVisibility(View.VISIBLE);

                } else if (lvTMTD.getVisibility() == View.VISIBLE) {
                    lvTMTD.setVisibility(View.GONE);

                }
            }
        });
        btnShowKHTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvKHTD.getVisibility() == View.GONE) {
                    lvKHTD.setVisibility(View.VISIBLE);

                } else if (lvKHTD.getVisibility() == View.VISIBLE) {
                    lvKHTD.setVisibility(View.GONE);

                }
            }
        });
        //endregion

        //endregion

        //region AnPhanLoai


        lvPhanLoaiTD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BaoCaoTaiSanActivity.class);
                intent.putExtra("Tu", baoCaoTaiChinhActivity.getTvTu().getText().toString().trim());
                intent.putExtra("Den", baoCaoTaiChinhActivity.getTvDen().getText().toString().trim());
                intent.putExtra("DuongDan", modun.TaiSan.getPath());
                intent.putExtra("PhanLoai", DanhSachPhanLoaiTD.get(position).getPhanLoai().trim());
                startActivity(intent);
            }
        });
        lvPhanLoaiDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BaoCaoTaiSanActivity.class);
                intent.putExtra("Tu", baoCaoTaiChinhActivity.getTvTu().getText().toString().trim());
                intent.putExtra("Den", baoCaoTaiChinhActivity.getTvDen().getText().toString().trim());
                intent.putExtra("DuongDan", modun.TaiSan.getPath());
                intent.putExtra("PhanLoai", DanhSachPhanLoaiDT.get(position).getPhanLoai().trim());
                startActivity(intent);
            }
        });
        lvPhanLoaiTM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BaoCaoTaiSanActivity.class);
                intent.putExtra("Tu", baoCaoTaiChinhActivity.getTvTu().getText().toString().trim());
                intent.putExtra("Den", baoCaoTaiChinhActivity.getTvDen().getText().toString().trim());
                intent.putExtra("DuongDan", modun.TaiSan.getPath());
                intent.putExtra("PhanLoai", DanhSachPhanLoaiTM.get(position).getPhanLoai().trim());
                startActivity(intent);
            }
        });
        lvPhanLoaiNO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BaoCaoTaiSanActivity.class);
                intent.putExtra("Tu", baoCaoTaiChinhActivity.getTvTu().getText().toString().trim());
                intent.putExtra("Den", baoCaoTaiChinhActivity.getTvDen().getText().toString().trim());
                intent.putExtra("DuongDan", modun.TaiSan.getPath());
                intent.putExtra("PhanLoai", DanhSachPhanLoaiNO.get(position).getPhanLoai().trim());
                startActivity(intent);
            }
        });


        lvDauKyTD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BaoCaoTaiSanActivity.class);
                intent.putExtra("Tu", "---");
                intent.putExtra("Den", baoCaoTaiChinhActivity.getTvTu().getText().toString().trim());
                intent.putExtra("DuongDan", modun.TaiSan.getPath());
                intent.putExtra("PhanLoai", DanhSachPhanLoaiDauKyTD.get(position).getPhanLoai().trim());
                startActivity(intent);
            }
        });
        lvDauKyDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BaoCaoTaiSanActivity.class);
                intent.putExtra("Tu", "---");
                intent.putExtra("Den", baoCaoTaiChinhActivity.getTvTu().getText().toString().trim());
                intent.putExtra("DuongDan", modun.TaiSan.getPath());
                intent.putExtra("PhanLoai", DanhSachPhanLoaiDauKyDT.get(position).getPhanLoai().trim());
                startActivity(intent);
            }
        });
        lvDauKyTM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BaoCaoTaiSanActivity.class);
                intent.putExtra("Tu", "---");
                intent.putExtra("Den", baoCaoTaiChinhActivity.getTvTu().getText().toString().trim());
                intent.putExtra("DuongDan", modun.TaiSan.getPath());
                intent.putExtra("PhanLoai", DanhSachPhanLoaiDauKyTM.get(position).getPhanLoai().trim());
                startActivity(intent);
            }
        });
        lvDauKyNO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BaoCaoTaiSanActivity.class);
                intent.putExtra("Tu", "---");
                intent.putExtra("Den", baoCaoTaiChinhActivity.getTvTu().getText().toString().trim());
                intent.putExtra("DuongDan", modun.TaiSan.getPath());
                intent.putExtra("PhanLoai", DanhSachPhanLoaiDauKyNO.get(position).getPhanLoai().trim());
                startActivity(intent);
            }
        });


        //endregion
    }

    public void LayDuongDanFile() throws ParseException {
        ListAdapter listadp;

        String[] Thuoctinh = {"tstd", "tsdt", "tsno", "tstm"};
        String ngaymin = baoCaoTaiChinhActivity.getTvTu().getText().toString().trim();
        String ngaymax = baoCaoTaiChinhActivity.getTvDen().getText().toString().trim();
        int NgayMin = Integer.parseInt(ngaymin.split("-")[0]) + Integer.parseInt(ngaymin.split("-")[1]) * 30 + Integer.parseInt(ngaymin.split("-")[2]) * 365;
        int NgayMax = Integer.parseInt(ngaymax.split("-")[0]) + Integer.parseInt(ngaymax.split("-")[1]) * 30 + Integer.parseInt(ngaymax.split("-")[2]) * 365;
        int SongayDaQuaTuDauKyDenCuoiKy = NgayMax - NgayMin;
        for (String thuocTinh : Thuoctinh) {
            LayFilePhanLoai(thuocTinh, ngaymin, ngaymax);
        }
        for (File filePhanLoai : modun.TaiSan.listFiles()) {
            File ThuocTinh = new File(modun.TaiSan_ThuocTinh, filePhanLoai.getName().trim() + ".txt");
            String strThuocTinh = modun.readText(ThuocTinh).trim();
            LayDuLieu_PhanLoai(strThuocTinh, filePhanLoai, ngaymin, ngaymax);
        }
        for (String thuocTinh : Thuoctinh) {
            List<clsTaiSan> DanhSachDauKy = new ArrayList<>();
            switch (thuocTinh) {
                case "tstd":
                    DanhSachDauKy = DanhSachDauKyTD;
                    SetKhauHao(DanhSachDauKy, DanhSachTrongKyTD, SongayDaQuaTuDauKyDenCuoiKy, NgayMax, thuocTinh);
                    break;
                case "tsdt":
                    DanhSachDauKy = DanhSachDauKyDT;
                    SetKhauHao(DanhSachDauKy, DanhSachTrongKyDT, SongayDaQuaTuDauKyDenCuoiKy, NgayMax, thuocTinh);
                    break;
                case "tstm":
                    DanhSachDauKy = DanhSachDauKyTM;
                    SetKhauHao(DanhSachDauKy, DanhSachTrongKyTM, SongayDaQuaTuDauKyDenCuoiKy, NgayMax, thuocTinh);
                    break;
                case "tsno":
                    DanhSachDauKy = DanhSachDauKyNO;
                    SetKhauHao(DanhSachDauKy, DanhSachTrongKyNO, SongayDaQuaTuDauKyDenCuoiKy, NgayMax, thuocTinh);
                    break;
            }
        }
        for (String thuocTinh : Thuoctinh) {
            SetKetQua(thuocTinh, ngaymin, ngaymax);
        }

        lvTMTD.setAdapter(new TaiSan_Adapter(DanhSachTrongKyTD, getContext(), R.layout.layout_item_taisantrongky));
        lvTMDT.setAdapter(new TaiSan_Adapter(DanhSachTrongKyDT, getContext(), R.layout.layout_item_taisantrongky));

        lvKHTD.setAdapter(new TaiSan_Adapter(DanhSachKHTrongKyTD, getContext(), R.layout.layout_item_khauhaotrongky));
        lvKHDT.setAdapter(new TaiSan_Adapter(DanhSachKHTrongKyDT, getContext(), R.layout.layout_item_khauhaotrongky));


        listadp = lvTMDT.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvTMDT);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvTMDT.getLayoutParams();
            params.height = totalHeight + (lvTMDT.getDividerHeight() * (listadp.getCount() - 1));
            lvTMDT.setLayoutParams(params);
            lvTMDT.requestLayout();

        }

        listadp = lvTMTD.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvTMTD);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvTMTD.getLayoutParams();
            params.height = totalHeight + (lvTMTD.getDividerHeight() * (listadp.getCount() - 1));
            lvTMTD.setLayoutParams(params);
            lvTMTD.requestLayout();

        }

        listadp = lvKHDT.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvKHDT);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvKHDT.getLayoutParams();
            params.height = totalHeight + (lvKHDT.getDividerHeight() * (listadp.getCount() - 1));
            lvKHDT.setLayoutParams(params);
            lvKHDT.requestLayout();

        }

        listadp = lvKHTD.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvKHTD);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvKHTD.getLayoutParams();
            params.height = totalHeight + (lvKHTD.getDividerHeight() * (listadp.getCount() - 1));
            lvKHTD.setLayoutParams(params);
            lvKHTD.requestLayout();

        }

        lvDauKyDT.setAdapter(new ListviewAdapter(DanhSachPhanLoaiDauKyDT, getContext(), R.layout.list_item_baocaotaisan));
        lvDauKyTD.setAdapter(new ListviewAdapter(DanhSachPhanLoaiDauKyTD, getContext(), R.layout.list_item_baocaotaisan));
        lvDauKyTM.setAdapter(new ListviewAdapter(DanhSachPhanLoaiDauKyTM, getContext(), R.layout.list_item_baocaotaisan));
        lvDauKyNO.setAdapter(new ListviewAdapter(DanhSachPhanLoaiDauKyNO, getContext(), R.layout.list_item_baocaotaisan));


        listadp = lvDauKyTD.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvDauKyTD);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvDauKyTD.getLayoutParams();
            params.height = totalHeight + (lvDauKyTD.getDividerHeight() * (listadp.getCount() - 1));
            lvDauKyTD.setLayoutParams(params);
            lvDauKyTD.requestLayout();

        }
        listadp = lvDauKyDT.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvDauKyDT);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvDauKyDT.getLayoutParams();
            params.height = totalHeight + (lvDauKyDT.getDividerHeight() * (listadp.getCount() - 1));
            lvDauKyDT.setLayoutParams(params);
            lvDauKyDT.requestLayout();

        }
        listadp = lvDauKyTM.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvDauKyTM);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvDauKyTM.getLayoutParams();
            params.height = totalHeight + (lvDauKyTM.getDividerHeight() * (listadp.getCount() - 1));
            lvDauKyTM.setLayoutParams(params);
            lvDauKyTM.requestLayout();

        }
        listadp = lvDauKyNO.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvDauKyNO);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvDauKyNO.getLayoutParams();
            params.height = totalHeight + (lvDauKyNO.getDividerHeight() * (listadp.getCount() - 1));
            lvDauKyNO.setLayoutParams(params);
            lvDauKyNO.requestLayout();

        }


        listViewAdapterTD = new ListviewAdapter(DanhSachPhanLoaiTD, getContext(), R.layout.list_item_baocaotaisan);
        lvPhanLoaiTD.setAdapter(listViewAdapterTD);
        listViewAdapterDT = new ListviewAdapter(DanhSachPhanLoaiDT, getContext(), R.layout.list_item_baocaotaisan);
        lvPhanLoaiDT.setAdapter(listViewAdapterDT);
        listViewAdapterTM = new ListviewAdapter(DanhSachPhanLoaiTM, getContext(), R.layout.list_item_baocaotaisan);
        lvPhanLoaiTM.setAdapter(listViewAdapterTM);
        listViewAdapterNO = new ListviewAdapter(DanhSachPhanLoaiNO, getContext(), R.layout.list_item_baocaotaisan);
        lvPhanLoaiNO.setAdapter(listViewAdapterNO);


        listadp = lvPhanLoaiTD.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvPhanLoaiTD);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvPhanLoaiTD.getLayoutParams();
            params.height = totalHeight + (lvPhanLoaiTD.getDividerHeight() * (listadp.getCount() - 1));
            lvPhanLoaiTD.setLayoutParams(params);
            lvPhanLoaiTD.requestLayout();

        }
        listadp = lvPhanLoaiDT.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvPhanLoaiDT);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvPhanLoaiDT.getLayoutParams();
            params.height = totalHeight + (lvPhanLoaiDT.getDividerHeight() * (listadp.getCount() - 1));
            lvPhanLoaiDT.setLayoutParams(params);
            lvPhanLoaiDT.requestLayout();

        }
        listadp = lvPhanLoaiTM.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvPhanLoaiTM);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvPhanLoaiTM.getLayoutParams();
            params.height = totalHeight + (lvPhanLoaiTM.getDividerHeight() * (listadp.getCount() - 1));
            lvPhanLoaiTM.setLayoutParams(params);
            lvPhanLoaiTM.requestLayout();

        }
        listadp = lvPhanLoaiNO.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, lvPhanLoaiNO);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = lvPhanLoaiNO.getLayoutParams();
            params.height = totalHeight + (lvPhanLoaiNO.getDividerHeight() * (listadp.getCount() - 1));
            lvPhanLoaiNO.setLayoutParams(params);
            lvPhanLoaiNO.requestLayout();

        }
    }

    private void SetKhauHao(List<clsTaiSan> danhsachDauKy, List<clsTaiSan> danhsachTrongKy, int SongayDaQuaTuDauKyDenCuoiKy, int NgayMax, String thuocTinh) {
        int TongKhauHaoTuDauKyDenCuoiKy = 0;

        for (clsTaiSan doituongMucTieu : danhsachDauKy) {
            int bien_HienTai;
            int SongayDaQuaTuDauDenDauKy = NgayMax - (Integer.parseInt(doituongMucTieu.getNgayThangNam().trim().split("-")[0]) + Integer.parseInt(doituongMucTieu.getNgayThangNam().trim().split("-")[1]) * 30 + Integer.parseInt(doituongMucTieu.getNgayThangNam().trim().split("-")[2]) * 365);
            if (doituongMucTieu.getThiTruong2().trim().equals("auto")) {
                bien_HienTai = Integer.parseInt(doituongMucTieu.getSoTien()) - Integer.parseInt(doituongMucTieu.getDaMat(SongayDaQuaTuDauDenDauKy));
                if (bien_HienTai <= 0) bien_HienTai = 0;
            } else {
                bien_HienTai = Integer.parseInt(doituongMucTieu.getThiTruong2().trim());
            }
            int GiaTriDauKy = bien_HienTai;
            int KhauHaoTuDauKyDenCuoiKy = Integer.parseInt(String.valueOf(Math.round(GiaTriDauKy * Double.valueOf(doituongMucTieu.getLangPhat().trim()) / 100 / 12 * SongayDaQuaTuDauKyDenCuoiKy / 30))) * -1;
            doituongMucTieu.setKhauHaoTrongKy(String.valueOf(KhauHaoTuDauKyDenCuoiKy));
            if (KhauHaoTuDauKyDenCuoiKy != 0) {

                switch (thuocTinh) {
                    case "tstd":

                        DanhSachKHTrongKyTD.add(doituongMucTieu);
                        break;
                    case "tsdt":
                        DanhSachKHTrongKyDT.add(doituongMucTieu);
                        break;
                }
                switch (thuocTinh) {
                    case "tstd":
                        intKhauHaoTD += KhauHaoTuDauKyDenCuoiKy;
                        break;
                    case "tsdt":
                        intKhauHaoDT += KhauHaoTuDauKyDenCuoiKy;
                        break;
                    case "tstm":
                        intKhauHaoTM += KhauHaoTuDauKyDenCuoiKy;
                        break;
                    case "tsno":
                        intKhauHaoNO += KhauHaoTuDauKyDenCuoiKy;
                        break;
                }
                intKhauHaoTC += KhauHaoTuDauKyDenCuoiKy;
            }
            TongKhauHaoTuDauKyDenCuoiKy += KhauHaoTuDauKyDenCuoiKy;
        }
        for (clsTaiSan doituongMucTieu : danhsachTrongKy) {
            int KhauHaoTrongKy = Integer.parseInt(doituongMucTieu.getTongMat());
            String sotien = doituongMucTieu.getSoTien();
            doituongMucTieu.setKhauHaoTrongKy(String.valueOf(KhauHaoTrongKy));

            switch (thuocTinh) {
                case "tstd":

                    DanhSachKHTrongKyTD.add(doituongMucTieu);
                    break;
                case "tsdt":
                    DanhSachKHTrongKyDT.add(doituongMucTieu);
                    break;
            }
            switch (thuocTinh) {
                case "tstd":
                    intKhauHaoTD += KhauHaoTrongKy;
                    break;
                case "tsdt":
                    intKhauHaoDT += KhauHaoTrongKy;
                    break;
                case "tstm":
                    intKhauHaoTM += KhauHaoTrongKy;
                    break;
                case "tsno":
                    intKhauHaoNO += KhauHaoTrongKy;
                    break;
            }
            intKhauHaoTC += KhauHaoTrongKy;

        }
    }

    private void LayFilePhanLoai(String thuocTinh, String ngaymin, String ngaymax) throws ParseException {
        for (File ThuocTinh : modun.TaiSan_ThuocTinh.listFiles()) {
            String strThuocTinh = modun.readText(ThuocTinh).trim();
            if (strThuocTinh.equals(thuocTinh)) {
                File duongDanToiMucTieu = new File(modun.TaiSan, ThuocTinh.getName().trim().replace(".txt", ""));
                LayDuLieu_MucTieu_Chi(duongDanToiMucTieu, thuocTinh, ngaymin, ngaymax);
            }
        }
    }

    private void AnhXa(View v) {
        /** KEO DAI VA THU GON */
        //region KEO DAI VA THU GON
        btnShowDT = v.findViewById(R.id.btnShowDT);
        btnShowTD = v.findViewById(R.id.btnShowTD);
        btnShowNO = v.findViewById(R.id.btnShowNO);
        btnShowTM = v.findViewById(R.id.btnShowTM);

        btnShowDauKyDT = v.findViewById(R.id.btnShowDauKyDT);
        btnShowDauKyTD = v.findViewById(R.id.btnShowDauKyTD);
        btnShowDauKyNO = v.findViewById(R.id.btnShowDauKyNO);
        btnShowDauKyTM = v.findViewById(R.id.btnShowDauKyTM);

        btnShowTMDT = v.findViewById(R.id.btnShowTMDT);
        btnShowTMTD = v.findViewById(R.id.btnShowTMTD);
        btnShowTMDT = v.findViewById(R.id.btnShowTMDT);
        btnShowTMTD = v.findViewById(R.id.btnShowTMTD);
        btnShowKHDT = v.findViewById(R.id.btnShowKHDT);
        btnShowKHTD = v.findViewById(R.id.btnShowKHTD);

        //endregion
        //region LISTVIEW
        lvPhanLoaiDT = v.findViewById(R.id.lvPhanLoaiDT);
        lvPhanLoaiTD = v.findViewById(R.id.lvPhanLoaiTD);
        lvPhanLoaiTM = v.findViewById(R.id.lvPhanLoaiTM);
        lvPhanLoaiNO = v.findViewById(R.id.lvPhanLoaiNO);

        lvDauKyDT = v.findViewById(R.id.lvDauKyDT);
        lvDauKyTD = v.findViewById(R.id.lvDauKyTD);
        lvDauKyNO = v.findViewById(R.id.lvDauKyNO);
        lvDauKyTM = v.findViewById(R.id.lvDauKyTM);

        lvTMDT = v.findViewById(R.id.lvTMDT);
        lvTMTD = v.findViewById(R.id.lvTMTD);
        lvKHDT = v.findViewById(R.id.lvKHDT);
        lvKHTD = v.findViewById(R.id.lvKHTD);
        //endregion
        //region TEXTVIEW
        tvDauKyTC = v.findViewById(R.id.tvDauKyTC);
        tvTMDT = v.findViewById(R.id.tvTMDT);
        tvCuoiKyTC = v.findViewById(R.id.tvCuoiKyTC);
        tvCuoiKyTTTC = v.findViewById(R.id.tvCuoiKyTTTC);
        tvTMTD = v.findViewById(R.id.tvTMTD);
        tvThuChiTC = v.findViewById(R.id.tvThuChi);

        tvDauKyTD = v.findViewById(R.id.tvDauKyTD);
        tvCuoiKyTD = v.findViewById(R.id.tvCuoiKyTD);
        tvKHTD = v.findViewById(R.id.tvKHTD);
        tvKHDT = v.findViewById(R.id.tvKHDT);

        tvDauKyDT = v.findViewById(R.id.tvDauKyDT);
        tvCuoiKyDT = v.findViewById(R.id.tvCuoiKyDT);

        tvDauKyNO = v.findViewById(R.id.tvDauKyNO);
        tvCuoiKyNO = v.findViewById(R.id.tvCuoiKyNO);

        tvDauKyTM = v.findViewById(R.id.tvDauKyTM);
        tvCuoiKyTM = v.findViewById(R.id.tvCuoiKyTM);

        //endregion
        baoCaoTaiChinhActivity = (BaoCaoTaiChinhActivity) getActivity();


    }

    private void LayDuLieu_PhanLoai(String thuocTinh, File filePhanLoai, String ngaymin, String ngaymax) throws ParseException {
        Date NgayMin = format.parse(ngaymin);
        Date NgayMax = format.parse(ngaymax);
        double phantramchi;
        double phantramchidauky = 0;
        double tongtienchi = 0;
        double tongtiendauky = 0;
        PhanLoai doituongPhanLoai;
        PhanLoai doituongPhanLoaiDauKy;
        clsTaiSan doituong_MucTieu;
        File DataIcon = new File(modun.TaiSan_Icon, filePhanLoai.getName().trim() + ".txt");
        Uri ImageData = Uri.parse(modun.readText(DataIcon).trim());

        for (File fileMucTieu : filePhanLoai.listFiles()) {
            String data = modun.readText(fileMucTieu).trim();
            String[] mangData = data.split("@");


            String NgayThangNam = mangData[0];
            String ThangNam = NgayThangNam.split("-")[1] + " " + NgayThangNam.split("-")[2];
            int sotien_Chi = Integer.parseInt(mangData[2]);
            String MucTieu = mangData[1];
            String DuKien = mangData[3];
            String Loai = mangData[4];
            String ThiTruong = mangData[5];
            String LangPhat = mangData[6];
            Uri uriImage = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.icon1);
            File DataIcon2 = new File(modun.TaiSan_Icon, fileMucTieu.getName().trim() + ".txt");
            uriImage = Uri.parse(modun.readText(DataIcon2).trim());

            Date NgayMucTieu = format.parse(NgayThangNam);
            if (!NgayMucTieu.after(NgayMax)) {
                doituong_MucTieu = new clsTaiSan(uriImage, MucTieu, ThangNam, String.valueOf(sotien_Chi), DuKien, Loai, fileMucTieu.getName(), NgayThangNam, ThiTruong, LangPhat);
                tongtienchi += Double.parseDouble(doituong_MucTieu.getHienTai());
            }
            if (!NgayMucTieu.after(NgayMin)) {
                doituong_MucTieu = new clsTaiSan(uriImage, MucTieu, ThangNam, String.valueOf(sotien_Chi), DuKien, Loai, fileMucTieu.getName(), NgayThangNam, ThiTruong, LangPhat);
                tongtiendauky += Double.parseDouble(doituong_MucTieu.getHienTai());
            }
        }

        switch (thuocTinh.trim()) {
            case "tstd":
                phantramchi = modun.round(tongtienchi / tongTienChiTD * 100, 1);
                doituongPhanLoai = new PhanLoai(ImageData, filePhanLoai.getName().trim(), tongtienchi, phantramchi, "tstd");
                phantramchidauky = modun.round(tongtiendauky / taisandaukyTD * 100, 1);
                doituongPhanLoaiDauKy = new PhanLoai(ImageData, filePhanLoai.getName().trim(), tongtiendauky, phantramchidauky, "tstd");
                DanhSachPhanLoaiDauKyTD.add(doituongPhanLoaiDauKy);
                DanhSachPhanLoaiTD.add(doituongPhanLoai);
                break;
            case "tsdt":
                phantramchi = modun.round(tongtienchi / tongTienChiDT * 100, 1);
                doituongPhanLoai = new PhanLoai(ImageData, filePhanLoai.getName().trim(), tongtienchi, phantramchi, "tsdt");
                phantramchidauky = modun.round(tongtiendauky / taisandaukyDT * 100, 1);
                doituongPhanLoaiDauKy = new PhanLoai(ImageData, filePhanLoai.getName().trim(), tongtiendauky, phantramchidauky, "tstd");
                DanhSachPhanLoaiDauKyDT.add(doituongPhanLoaiDauKy);
                DanhSachPhanLoaiDT.add(doituongPhanLoai);
                break;
            case "tsno":
                phantramchi = modun.round(tongtienchi / tongTienChiNO * 100, 1);
                doituongPhanLoai = new PhanLoai(ImageData, filePhanLoai.getName().trim(), tongtienchi, phantramchi, "tsno");
                phantramchidauky = modun.round(tongtiendauky / taisandaukyNO * 100, 1);
                doituongPhanLoaiDauKy = new PhanLoai(ImageData, filePhanLoai.getName().trim(), tongtiendauky, phantramchidauky, "tstd");
                DanhSachPhanLoaiDauKyNO.add(doituongPhanLoaiDauKy);
                DanhSachPhanLoaiNO.add(doituongPhanLoai);
                break;
            case "tstm":
                phantramchi = modun.round(tongtienchi / tongTienChiTM * 100, 1);
                doituongPhanLoai = new PhanLoai(ImageData, filePhanLoai.getName().trim(), tongtienchi, phantramchi, "tstm");
                phantramchidauky = modun.round(tongtiendauky / taisandaukyTM * 100, 1);
                doituongPhanLoaiDauKy = new PhanLoai(ImageData, filePhanLoai.getName().trim(), tongtiendauky, phantramchidauky, "tstd");
                DanhSachPhanLoaiDauKyTM.add(doituongPhanLoaiDauKy);
                DanhSachPhanLoaiTM.add(doituongPhanLoai);
                break;

        }


    }

    public void SetKetQua(String thuocTinh, String ngaymin, String ngaymax) {
        double bien_damat = 0;
        double bien_trongthang = 0;
        double bien_tongtaisan = 0;
        double bien_gthientai = 0;

        int NgayMin = Integer.parseInt(ngaymin.split("-")[0]) + Integer.parseInt(ngaymin.split("-")[1]) * 30 + Integer.parseInt(ngaymin.split("-")[2]) * 365;
        int NgayMax = Integer.parseInt(ngaymax.split("-")[0]) + Integer.parseInt(ngaymax.split("-")[1]) * 30 + Integer.parseInt(ngaymax.split("-")[2]) * 365;

        int SoNgayDaQua = NgayMax - NgayMin;

        tvDauKyTC.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(taisandaukyTC))))).replace(",", ",").replace(".", ","));
        tvDauKyTM.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(taisandaukyTM))))).replace(",", ",").replace(".", ","));
        tvDauKyTD.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(taisandaukyTD))))).replace(",", ",").replace(".", ","));
        tvDauKyDT.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(taisandaukyDT))))).replace(",", ",").replace(".", ","));
        tvTMDT.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(intMuaThemDT))))).replace(",", ",").replace(".", ","));
        tvTMTD.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(intMuaThemTD))))).replace(",", ",").replace(".", ","));
        tvDauKyNO.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(taisandaukyNO))))).replace(",", ",").replace(".", ","));

        tvCuoiKyTC.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(taisancuoikyTC))))).replace(",", ",").replace(".", ","));
        tvCuoiKyTM.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(taisancuoikyTM))))).replace(",", ",").replace(".", ","));
        tvCuoiKyDT.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(taisancuoikyDT))))).replace(",", ",").replace(".", ","));
        tvCuoiKyTD.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(taisancuoikyTD))))).replace(",", ",").replace(".", ","));
        tvCuoiKyNO.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(taisancuoikyNO))))).replace(",", ",").replace(".", ","));


        tvKHDT.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(intKhauHaoDT))))).replace(",", ",").replace(".", ","));
        tvCuoiKyTTTC.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(intKhauHaoTD + intKhauHaoDT + intThuChi + taisandaukyTC))))).replace(",", ",").replace(".", ","));
        tvKHTD.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(intKhauHaoTD))))).replace(",", ",").replace(".", ","));
        baoCaoTaiChinhActivity.getTvTaiSan().setText(tvCuoiKyTC.getText().toString().trim());
/*        switch (thuocTinh) {
            case "tstd":
                for (clsTaiSan x : DanhSachTSTD) {
                    bien_gthientai += Double.parseDouble(x.getHienTai());

                }
                tvCuoiKyTD.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(bien_gthientai))))).replace(",", ",").replace(".", ","));
                tvBDTD.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(bien_gthientai - taisandaukyTD))))).replace(",", ",").replace(".", ","));

            case "tsdt":
                for (clsTaiSan x : DanhSachTSTD) {
                    bien_gthientai += Double.parseDouble(x.getHienTai());

                }
                tvCuoiKyDT.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(bien_gthientai))))).replace(",", ",").replace(".", ","));
                tvBDDT.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(bien_gthientai - taisandaukyDT))))).replace(",", ",").replace(".", ","));
            case "tstm":
                for (clsTaiSan x : DanhSachTSTD) {
                    bien_gthientai += Double.parseDouble(x.getHienTai());

                }
                tvCuoiKyTM.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(bien_gthientai))))).replace(",", ",").replace(".", ","));
                tvBDTM.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(bien_gthientai - taisandaukyTM))))).replace(",", ",").replace(".", ","));
            case "tsno":
                for (clsTaiSan x : DanhSachTSTD) {
                    bien_gthientai += Double.parseDouble(x.getHienTai());

                }
                tvCuoiKyNO.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(bien_gthientai))))).replace(",", ",").replace(".", ","));
                tvBDNO.setText(String.format("%,d", (Long.parseLong(String.valueOf(Math.round(bien_gthientai - taisandaukyNO))))).replace(",", ",").replace(".", ","));

        }*/


    }

    private void LayDuLieu_MucTieu_Chi(File duongDanPhanLoai, String ThuocTinh, String ngaymin, String ngaymax) throws ParseException {
        File[] listMucTieu = duongDanPhanLoai.listFiles();
        Date NgayMin = format.parse(ngaymin);
        Date NgayMax = format.parse(ngaymax);
        if (listMucTieu != null) {
            for (File fileMucTieu : listMucTieu) {
                String data = modun.readText(fileMucTieu).trim();
                String[] mangData = data.split("@");
                String NgayThangNam = mangData[0];
                String ThangNam = NgayThangNam.split("-")[1] + " " + NgayThangNam.split("-")[2];
                int sotien_Chi = Integer.parseInt(mangData[2]);
                String MucTieu = mangData[1];
                String DuKien = mangData[3];
                String Loai = mangData[4];
                String ThiTruong = mangData[5];
                String LangPhat = mangData[6];
                Date NgayMucTieu = format.parse(NgayThangNam);
                Uri uriImage = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.icon1);
                File DataIcon = new File(modun.TaiSan_Icon, duongDanPhanLoai.getName().trim() + ".txt");
                uriImage = Uri.parse(modun.readText(DataIcon).trim());
                clsTaiSan doituong_MucTieu = new clsTaiSan(uriImage, MucTieu, ThangNam, String.valueOf(sotien_Chi), DuKien, Loai, fileMucTieu.getName(), NgayThangNam, ThiTruong, LangPhat);
                if (!NgayMucTieu.after(NgayMax) && !NgayMucTieu.before(NgayMin)) {
                    intMuaThemTC += Integer.parseInt(doituong_MucTieu.getSoTien().trim());
                    if (Integer.parseInt(doituong_MucTieu.getSoTien().trim()) == 0) {
                        intBienDongThiTruongTC += Integer.parseInt(doituong_MucTieu.getHienTai().trim());
                    }
                    switch (ThuocTinh) {
                        case "tstd":
                            if (Integer.parseInt(doituong_MucTieu.getSoTien().trim()) != 0) {
                                intMuaThemTD += Integer.parseInt(doituong_MucTieu.getSoTien().trim());
                            }
                            DanhSachTrongKyTD.add(doituong_MucTieu);
                            break;
                        case "tsdt":
                            if (Integer.parseInt(doituong_MucTieu.getSoTien().trim()) != 0) {
                                intMuaThemDT += Integer.parseInt(doituong_MucTieu.getSoTien().trim());
                            }
                            DanhSachTrongKyDT.add(doituong_MucTieu);
                            break;
                        case "tsno":
                            DanhSachTrongKyNO.add(doituong_MucTieu);
                            break;
                        case "tstm":
                            DanhSachTrongKyTM.add(doituong_MucTieu);
                            break;
                    }
                }
                if (!NgayMucTieu.after(NgayMin)) {
                    switch (ThuocTinh) {
                        case "tstd":
                            DanhSachDauKyTD.add(doituong_MucTieu);
                            taisandaukyTD += Double.parseDouble(doituong_MucTieu.getHienTai());
                            taisandaukyTC += Double.parseDouble(doituong_MucTieu.getHienTai());
                            break;
                        case "tsdt":
                            DanhSachDauKyDT.add(doituong_MucTieu);
                            taisandaukyDT += Double.parseDouble(doituong_MucTieu.getHienTai());
                            taisandaukyTC += Double.parseDouble(doituong_MucTieu.getHienTai());
                            break;
                        case "tsno":
                            DanhSachDauKyNO.add(doituong_MucTieu);
                            taisandaukyNO += Double.parseDouble(doituong_MucTieu.getHienTai());
                            taisandaukyTC += Double.parseDouble(doituong_MucTieu.getHienTai());
                            break;
                        case "tstm":
                            DanhSachDauKyTM.add(doituong_MucTieu);
                            taisandaukyTM += Double.parseDouble(doituong_MucTieu.getHienTai());
                            taisandaukyTC += Double.parseDouble(doituong_MucTieu.getHienTai());
                            break;
                    }
                }
                if (!NgayMucTieu.after(NgayMax)) {
                    switch (ThuocTinh) {
                        case "tstd":
                            DanhSachTSTD.add(doituong_MucTieu);
                            taisancuoikyTD += Double.parseDouble(doituong_MucTieu.getHienTai());
                            tongTienChiTD += Double.parseDouble(doituong_MucTieu.getHienTai());
                            break;
                        case "tsdt":
                            DanhSachTSDT.add(doituong_MucTieu);
                            taisancuoikyDT += Double.parseDouble(doituong_MucTieu.getHienTai());
                            tongTienChiDT += Double.parseDouble(doituong_MucTieu.getHienTai());
                            break;
                        case "tsno":
                            DanhSachTSNO.add(doituong_MucTieu);
                            tongTienChiNO += Double.parseDouble(doituong_MucTieu.getHienTai());
                            taisancuoikyNO += Double.parseDouble(doituong_MucTieu.getHienTai());
                            break;
                        case "tstm":
                            DanhSachTSTM.add(doituong_MucTieu);
                            tongTienChiTM += Double.parseDouble(doituong_MucTieu.getHienTai());
                            taisancuoikyTM += Double.parseDouble(doituong_MucTieu.getHienTai());
                            break;
                    }
                    taisancuoikyTC += Double.parseDouble(doituong_MucTieu.getHienTai());


                }

            }
        }

    }

}
