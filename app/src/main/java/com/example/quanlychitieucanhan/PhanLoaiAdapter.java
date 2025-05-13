package com.example.quanlychitieucanhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PhanLoaiAdapter extends BaseAdapter {
    List<clsMucTieu> myObject;
    Context mycontext;
    private ArrayList<String> myList = new ArrayList<String>();
    private List<String> arraylist = new ArrayList<>();
    BaoCao_Adapter baoCao_adapter;
    String PhanLoai;
    int bien_tinhtong;
    List<clsMucTieu> myObj = new ArrayList<>();
    int myRecource;

    public PhanLoaiAdapter(List<clsMucTieu> myObject, Context mycontext,String phanLoai,int bien_tinhtong,int myRecource) {
        this.myObject = myObject;
        this.mycontext = mycontext;
        myObj.addAll(myObject);
        this.myRecource = myRecource;
        this.PhanLoai = phanLoai;
        this.bien_tinhtong = bien_tinhtong;
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
        View V =view;
        if (null == V)
        {
            LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = inflater.inflate(myRecource, null);
        }
        if (myRecource == R.layout.layout_item_baocaochi){
            Boolean abc = false;
            TextView tvPhanLoai = V.findViewById(R.id.tvPhanLoai);
            TextView tvSoTien = V.findViewById(R.id.tvSoTien);
            ListView listViewMucTieu = V.findViewById(R.id.listview_MucTieu);
            TextView tvTyLe = V.findViewById(R.id.tvTyLe);
            if (!myObject.get(i).getLoai().trim().equals(PhanLoai)){
                myObject.remove(i);
            }else {
                double bien_phantram = Double.valueOf(myObject.get(i).getSoTien()) / bien_tinhtong *100;
                tvTyLe.setText(String.valueOf(modun.round(bien_phantram,1)) + "%");
                tvPhanLoai.setText(myObject.get(i).getTenMucTieu().trim());
                tvSoTien.setText((String.format("%,d",Long.parseLong(myObject.get(i).getSoTien().trim()))).replace(",",",").replace(".",","));
            }
        }else if (myRecource == R.layout.layout_item_phanloaibaocao){
            TextView tvMucTieu = V.findViewById(R.id.tvMucTieu);
            TextView tvSoTien = V.findViewById(R.id.tvChi);
            tvMucTieu.setText(myObject.get(i).getTenMucTieu());
            tvSoTien.setText((String.format("%,d",Long.parseLong(myObject.get(i).getSoTien().trim()))).replace(",",",").replace(".",","));
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

