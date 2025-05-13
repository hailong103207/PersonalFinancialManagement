package com.example.quanlychitieucanhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChonViAdapter extends BaseAdapter {
    List<PhanLoai> myObject;

    Context mycontext;
    int myRecource;


    public ChonViAdapter(List<PhanLoai> myObject, Context mycontext, int myRecource) {
        this.myObject = myObject;
        this.mycontext = mycontext;
        this.myRecource = myRecource;
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
            V = inflater.inflate(R.layout.chonvi_item, null);
        }
        ImageView imageView = V.findViewById(R.id.imageView2);
        TextView Vi = V.findViewById(R.id.txtTenPhanLoai);
        imageView.setImageURI(myObject.get(i).getImageID());
        Vi.setText(myObject.get(i).getPhanLoai());




        return V;
    }

}