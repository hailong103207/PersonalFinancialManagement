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

public class BaoCaoTaiSan_Adapter extends BaseAdapter {
    List<clsTaiSan> myObject;
    Context mycontext;
    int myRecource;
    private ArrayList<String> myList = new ArrayList<String>();
    private List<String> arraylist = new ArrayList<>();
    private List<Uri> iconlist = new ArrayList<>();
    double bien_tinhtongc;
    double bien_tongmatc;
    TaiSan_Adapter phanLoaiAdapter;

    public double getBien_tinhtongc() {
        return bien_tinhtongc;
    }

    public void setBien_tinhtongc(double bien_tinhtongc) {
        this.bien_tinhtongc = bien_tinhtongc;
    }

    public double getBien_tongmatc() {
        return bien_tongmatc;
    }

    public void setBien_tongmatc(double bien_tongmatc) {
        this.bien_tongmatc = bien_tongmatc;
    }

    public BaoCaoTaiSan_Adapter(List<clsTaiSan> myObject, Context mycontext, int myRecource, double bien_tinhtong, double bien_tongmat) {
        this.myObject = myObject;
        this.bien_tinhtongc = bien_tinhtong;
        this.mycontext = mycontext;
        this.myRecource = myRecource;
        this.bien_tongmatc = bien_tongmat;
        for (clsTaiSan doituong : myObject){
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
            V = inflater.inflate(R.layout.layout_item_baocaophanloaitaisan, null);
        }
        ImageView imgIcon = V.findViewById(R.id.imgIcon);
        TextView tvPhanLoai = V.findViewById(R.id.tvPhanLoai);
        TextView tvSoTien = V.findViewById(R.id.tvSoTien);
        ListView listViewMucTieu = V.findViewById(R.id.listview_MucTieu);
        TextView tvTyLe = V.findViewById(R.id.tvTyLe);
        double bien_tinhtong = 0;
        double bien_tinhtongmat = 0;
        tvPhanLoai.setText(arraylist.get(i));
        imgIcon.setImageURI(iconlist.get(i));
        List<clsTaiSan> list = new ArrayList<>();
        for (clsTaiSan doituong: myObject){
            if (doituong.getLoai().trim().equals(arraylist.get(i))){
                list.add(doituong);
                bien_tinhtong+= Integer.parseInt(doituong.getSoTien());
                bien_tinhtongmat+= Integer.parseInt(doituong.getTongMat());
            }
        }
        double phantram = modun.round((bien_tinhtong / bien_tinhtongc * 100),1);
        tvTyLe.setText((String.format("%,d", Long.parseLong(String.valueOf(Math.round(bien_tinhtongmat))))).replace(",", ",").replace(".", ","));
        tvSoTien.setText((String.format("%,d", Long.parseLong(String.valueOf(Math.round(bien_tinhtong))))).replace(",", ",").replace(".", ","));
        phanLoaiAdapter = new TaiSan_Adapter(list, mycontext,R.layout.layout_item_taisan);
        listViewMucTieu.setAdapter(phanLoaiAdapter);
        ListAdapter listadp = listViewMucTieu.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int p = 0; p < listadp.getCount(); p++) {
                int a = listadp.getCount();
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

}

