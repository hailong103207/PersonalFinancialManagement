package com.example.quanlychitieucanhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GridviewAdapter extends BaseAdapter {
    private List<PhanLoai> myObject;
    Context mycontext;
    int myRecource;
    private ArrayList<String> myList = new ArrayList<String>();
    private ArrayList<PhanLoai> arraylist;

    public GridviewAdapter(List<PhanLoai> myObject, Context mycontext, int myRecource) {
        this.myObject = myObject;
        this.mycontext = mycontext;
        this.myRecource = myRecource;
        this.arraylist = new ArrayList<PhanLoai>();
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
        View V =view;
        if (null == V)
        {
            LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = inflater.inflate(R.layout.grid_item, null);
        }
        ImageView imageView = V.findViewById(R.id.imageView2);
        TextView tvPhanLoai = V.findViewById(R.id.txtTenPhanLoai);
        LinearLayout lnBinhThuong = V.findViewById(R.id.lnBinhThuong);
        TextView tvPhanTram = V.findViewById(R.id.txtPhanTram);

        imageView.setImageURI(myObject.get(i).getImageID());
        tvPhanLoai.setText(myObject.get(i).getPhanLoai());
        tvPhanTram.setText(String.valueOf(myObject.get(i).getPhanTramChi())+"%");
        if (myObject.get(i).getKieu().trim().equals("ts")){
            File ThuocTinhTS = new File(modun.TaiSan_ThuocTinh,myObject.get(i).getPhanLoai().trim()+".txt");
            if (modun.readText(ThuocTinhTS).trim().equals("tstd")){
                lnBinhThuong.setBackgroundResource(R.drawable.layout_gridview_td);
            }else if (modun.readText(ThuocTinhTS).trim().equals("tsdt")){
                lnBinhThuong.setBackgroundResource(R.drawable.layout_gridview_dt);
            }else if (modun.readText(ThuocTinhTS).trim().equals("tsno")){
                lnBinhThuong.setBackgroundResource(R.drawable.layout_gridview_no);
            }else if (modun.readText(ThuocTinhTS).trim().equals("tstm")){
                lnBinhThuong.setBackgroundResource(R.drawable.layout_gridview_tm);
            }
        }
        return V;
    }
    // Filter Class
    public ArrayList<String> filter(String charText) {
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
                        .contains(charText) ) {
                    myObject.add(wp);
                    myList.add(wp.getPhanLoai());
                }
            }
        }
        notifyDataSetChanged();
        return myList;
    }
}