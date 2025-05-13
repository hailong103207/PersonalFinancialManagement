package com.example.quanlychitieucanhan;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BaoCao_Adapter extends BaseAdapter {
    List<clsMucTieu> myObject;
    Context mycontext;
    int myRecource;
    private ArrayList<String> myList = new ArrayList<String>();
    private List<String> arraylist = new ArrayList<>();
    private List<Uri> iconlist = new ArrayList<>();
    double bien_tinhtongc;
    PhanLoaiAdapter phanLoaiAdapter;
    public BaoCao_Adapter(List<clsMucTieu> myObject, Context mycontext, int myRecource,double bien_tinhtong) {
        this.myObject = myObject;
        this.bien_tinhtongc = bien_tinhtong;
        this.mycontext = mycontext;
        this.myRecource = myRecource;
        for (clsMucTieu doituong : myObject){
            if (!arraylist.contains(doituong.getLoai().trim()) ){
                arraylist.add(doituong.getLoai().trim());
                iconlist.add(doituong.getIcon());
            }
        }
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylist.get(i);
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
            V = inflater.inflate(R.layout.layout_item_baocaophanloaichi, null);
        }
        ImageView imgIcon = V.findViewById(R.id.imgIcon);
        TextView tvPhanLoai = V.findViewById(R.id.tvPhanLoai);
        TextView tvSoTien = V.findViewById(R.id.tvSoTien);
        ListView listViewMucTieu = V.findViewById(R.id.listview_MucTieu);
        TextView tvTyLe = V.findViewById(R.id.tvTyLe);
        double bien_tinhtong = 0;
        tvPhanLoai.setText(arraylist.get(i));
        imgIcon.setImageURI(iconlist.get(i));
        List<clsMucTieu> list = new ArrayList<>();
        for (clsMucTieu doituong: myObject){
            if (doituong.getLoai().trim().equals(arraylist.get(i))){
                list.add(doituong);
                bien_tinhtong+= Integer.parseInt(doituong.getSoTien());
            }
        }
        double phantram = modun.round((bien_tinhtong / bien_tinhtongc * 100),1);
        tvTyLe.setText(String.valueOf(modun.round((phantram),1))+"%");
        tvSoTien.setText((String.format("%,d", Long.parseLong(String.valueOf(Math.round(bien_tinhtong))))).replace(",", ",").replace(".", ","));
        phanLoaiAdapter = new PhanLoaiAdapter(list, mycontext, arraylist.get(i),Integer.parseInt(String.valueOf(Math.round(bien_tinhtong))),R.layout.layout_item_baocaochi);
        listViewMucTieu.setAdapter(phanLoaiAdapter);
        ListAdapter listadp = listViewMucTieu.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                View listItem = listadp.getView(p, null, listViewMucTieu);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listViewMucTieu.getLayoutParams();
            params.height = totalHeight + (listViewMucTieu.getDividerHeight() * (listadp.getCount() - 1));
            listViewMucTieu.setLayoutParams(params);
            listViewMucTieu.requestLayout();

        }
        return V;
    }
/*    public ArrayList<String> filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myObject.clear();
        myList.clear();
        if (charText.length() == 0)
        {
            myObject.addAll(arraylist);
        }
        else {
            for (PhanLoai wp : arraylist) {
                if (wp.getPhanLoai().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getPhanTramChi().toString().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getTongTienChi().toString().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    myObject.add(wp);
                    myList.add(wp.getPhanLoai());
                }
            }
        }

        return myList;
    }*/

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

