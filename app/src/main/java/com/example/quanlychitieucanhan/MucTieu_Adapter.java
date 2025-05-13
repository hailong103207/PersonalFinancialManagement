package com.example.quanlychitieucanhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MucTieu_Adapter extends BaseAdapter {
    List<clsMucTieu> myObject;
    Context mycontext;
    int myRecource;
    private ArrayList<String> myList = new ArrayList<String>();
    private ArrayList<clsMucTieu> arraylist;

    public MucTieu_Adapter(List<clsMucTieu> myObject, Context mycontext, int myRecource) {
        this.myObject = myObject;
        this.mycontext = mycontext;
        this.myRecource = myRecource;
        this.arraylist = new ArrayList<clsMucTieu>();
        this.arraylist.addAll(myObject);
    }

    @Override
    public int getCount() {
        return myObject.size();
    }

    @Override
    public Object getItem(int i) {
        return myObject.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View V = view;
        if (null == V) {
            LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = inflater.inflate(R.layout.layout_item_muctieu, null);
        }
        TextView tvNgayThangNam = V.findViewById(R.id.tvNgayThangNam);
        ImageView imgIcon = V.findViewById(R.id.imgIcon);
        ImageView imgIcon2 = V.findViewById(R.id.imgIcon2);
        TextView tvNgay = V.findViewById(R.id.tvNgay);
        TextView tvGioPhut = V.findViewById(R.id.tvGioPhut);
        LinearLayout lnAll = V.findViewById(R.id.lnAll);
        final LinearLayout lnLong = V.findViewById(R.id.lnLong);
        final LinearLayout lnShort = V.findViewById(R.id.lnShort);
        TextView tvThangNam = V.findViewById(R.id.tvThangNam);
        TextView tvMucTieu = V.findViewById(R.id.tvMucTieu);
        TextView tvMucTieu2 = V.findViewById(R.id.tvMucTieu2);
        TextView tvChi = V.findViewById(R.id.tvChi);
        TextView tvChi2 = V.findViewById(R.id.tvChi2);
        if (myObject.get(i).getPhanLoai().equals("chi")) {
            String donvi = ".000 vnđ";
            tvChi.setText("-" + myObject.get(i).getSoTien() + donvi);
            tvChi.setTextColor(V.getResources().getColor(R.color.colorPrimary2));


        } else if (myObject.get(i).getPhanLoai().equals("thu")) {
            String donvi = ".000 vnđ";
            tvChi.setText("+" + myObject.get(i).getSoTien() + donvi);
            tvChi.setTextColor(V.getResources().getColor(R.color.colorPrimary));
        }

        imgIcon.setImageURI(myObject.get(i).getIcon());
        imgIcon2.setImageURI(myObject.get(i).getIcon());
        tvNgay.setText(myObject.get(i).getNgay());
        tvGioPhut.setText(myObject.get(i).getGio());
        tvThangNam.setText(myObject.get(i).getThangNam());
        tvMucTieu.setText(myObject.get(i).getTenMucTieu());
        tvNgayThangNam.setText(("(" + tvNgay.getText().toString().trim() + " " + tvThangNam.getText().toString().trim() + ")").replace(" ", "/"));
        tvMucTieu2.setText(myObject.get(i).getTenMucTieu());
        tvChi.setText((String.format("%,d", Long.parseLong(myObject.get(i).getSoTien()))).replace(",", ",").replace(".", ","));
        tvChi2.setText((String.format("%,d", Long.parseLong(myObject.get(i).getSoTien()))).replace(",", ",").replace(".", ","));


        if (Double.valueOf(tvChi.getText().toString().trim().replace(",", "")) > 0) {
            tvChi.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary));
            tvChi2.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary));

        } else {
            tvChi.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary2));
            tvChi2.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary2));
        }

        return V;
    }

    public ArrayList<String> filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myObject.clear();
        myList.clear();
        if (charText.length() == 0) {
            myObject.addAll(arraylist);
        } else {
            for (clsMucTieu wp : arraylist) {
                if (wp.getPhanLoai().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getPhanLoai().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getSoTien().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getGio().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getNgay().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getThangNam().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getTenMucTieu().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    myObject.add(wp);
                    myList.add(wp.getPhanLoai());
                }
            }
        }
        notifyDataSetChanged();

        return myList;
    }

    public List<clsMucTieu> sort(List<clsMucTieu> clsMucTieuList) {
        myObject.clear();
        myList.clear();

        List<clsMucTieu> clsMucTieudasapxep = new ArrayList<>();
        clsMucTieudasapxep.clear();
        int ngaymax = 0;
        for (clsMucTieu doituong : clsMucTieuList) {
            int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
            if (songay > ngaymax) {
                ngaymax = songay;
            }
        }
        int ngaymin = ngaymax;
        for (clsMucTieu doituong : clsMucTieuList) {
            int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
            if (songay < ngaymin) {
                ngaymin = songay;
            }
        }
        for (int i = ngaymax; i >= ngaymin; i--) {
            for (clsMucTieu doituong : clsMucTieuList) {
                int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                if (songay == i) {
                    clsMucTieudasapxep.add(doituong);
                }
            }
        }
        myObject.clear();
        myObject.addAll(clsMucTieudasapxep);

        return clsMucTieudasapxep;
    }

    public List<clsMucTieu> sort(List<clsMucTieu> clsMucTieuList, String ngaymins, String ngaymaxs) {
        List<clsMucTieu> clsMucTieudasapxep = new ArrayList<>();
        int ngaymin = 10000000;
        int ngaymax = 0;
        if (ngaymins.trim().equals("---")) {
            if (ngaymaxs.trim().equals("---")) { //CA 2 DEU KHONG
                return sort(clsMucTieuList);
            } else { //1 KHONG 1 CO
                ngaymax = Integer.parseInt(ngaymaxs.split("-")[2]) * 372 + Integer.parseInt(ngaymaxs.split("-")[1]) * 31 + Integer.parseInt(ngaymaxs.split("-")[0]);
                for (clsMucTieu doituong : clsMucTieuList) {
                    int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                    if (songay < ngaymin) {
                        ngaymin = songay;
                    }
                }
                for (int i = ngaymax; i >= ngaymin; i--) {
                    for (clsMucTieu doituong : clsMucTieuList) {
                        int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                        if (songay == i) {
                            clsMucTieudasapxep.add(doituong);
                        }
                    }
                }
                myObject.clear();
                myObject.addAll(clsMucTieudasapxep);

                return clsMucTieudasapxep;
            }
        } else {
            ngaymin = Integer.parseInt(ngaymins.split("-")[2]) * 372 + Integer.parseInt(ngaymins.split("-")[1]) * 31 + Integer.parseInt(ngaymins.split("-")[0]);
            if (ngaymaxs.trim().equals("---")) { //1 CO 1 KHONG
                for (clsMucTieu doituong : clsMucTieuList) {
                    int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                    if (songay > ngaymax) {
                        ngaymax = songay;
                    }
                }
                for (int i = ngaymax; i >= ngaymin; i--) {
                    for (clsMucTieu doituong : clsMucTieuList) {
                        int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                        if (songay == i) {
                            clsMucTieudasapxep.add(doituong);
                        }
                    }
                }
                return sort(clsMucTieudasapxep);
            } else {//CA HAI CUNG CO
                ngaymax = Integer.parseInt(ngaymaxs.split("-")[2]) * 372 + Integer.parseInt(ngaymaxs.split("-")[1]) * 31 + Integer.parseInt(ngaymaxs.split("-")[0]);
                ngaymin = Integer.parseInt(ngaymins.split("-")[2]) * 372 + Integer.parseInt(ngaymins.split("-")[1]) * 31 + Integer.parseInt(ngaymins.split("-")[0]);
                if (ngaymax > ngaymin) {
                    int abc = Integer.parseInt(clsMucTieuList.get(0).getThangNam().split(" ")[1]) * 372 + Integer.parseInt(clsMucTieuList.get(0).getThangNam().split(" ")[0]) * 31 + Integer.parseInt(clsMucTieuList.get(0).getNgay());
                    for (int i = ngaymax; i >= ngaymin; i--) {
                        for (clsMucTieu doituong : clsMucTieuList) {
                            String ngay = doituong.getThangNam();
                            String thang = doituong.getNgay();
                            int songay = Integer.parseInt(doituong.getThangNam().split(" ")[1]) * 372 + Integer.parseInt(doituong.getThangNam().split(" ")[0]) * 31 + Integer.parseInt(doituong.getNgay());
                            if (songay == i) {
                                clsMucTieudasapxep.add(doituong);
                            }
                        }
                    }
                    myObject.clear();
                    myObject.addAll(clsMucTieudasapxep);
                    return clsMucTieudasapxep;
                } else {
                    clsMucTieudasapxep = new ArrayList<>();
                    myObject.clear();
                    myObject.addAll(clsMucTieudasapxep);
                    return clsMucTieudasapxep;
                }
            }

        }

    }
}

