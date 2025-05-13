package com.example.quanlychitieucanhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class BaoCaoThuChiTaiChinh_Adapter extends BaseAdapter {
    List<clsMucTieuPhanLoai> myObject;
    Context mycontext;
    int myRecource;
    PhanLoaiAdapter phanLoaiAdapter;


    public BaoCaoThuChiTaiChinh_Adapter(List<clsMucTieuPhanLoai> myObject, Context mycontext, int myRecource) {
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
        View V = view;
        if (null == V) {
            LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = inflater.inflate(R.layout.list_item_baocaochi, null);
        }

        TextView tvPhanLoai = V.findViewById(R.id.tvPhanLoai);
        TextView tvTongSoTien = V.findViewById(R.id.tvTongTienChi);
        TextView tvPhanTram = V.findViewById(R.id.tvPhanTramChi);
        ImageView imageView = V.findViewById(R.id.img_avatar);
        final LinearLayout lnDaiNhat = V.findViewById(R.id.lnDaiNhat);
        final LinearLayout btnShow = V.findViewById(R.id.btnShow);

        ListView listViewMucTieu = V.findViewById(R.id.listview_MucTieu);

        imageView.setImageURI(myObject.get(i).getPhanLoai().getImageID());
        tvPhanLoai.setText(myObject.get(i).getPhanLoai().getPhanLoai());
        tvTongSoTien.setText((String.format("%,d", (Long.parseLong(String.valueOf(Math.round(myObject.get(i).getPhanLoai().getTongTienChi())))))).replace(",", ",").replace(".", ","));
        tvPhanTram.setText(String.valueOf(myObject.get(i).getPhanLoai().getPhanTramChi()) + "%");
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lnDaiNhat.getVisibility() == View.GONE) {
                    lnDaiNhat.setVisibility(View.VISIBLE);

                } else if (lnDaiNhat.getVisibility() == View.VISIBLE) {
                    lnDaiNhat.setVisibility(View.GONE);
                }
            }
        });
        phanLoaiAdapter = new PhanLoaiAdapter(myObject.get(i).getClsMucTieuList(), mycontext, myObject.get(i).getPhanLoai().getPhanLoai(), 0, R.layout.layout_item_phanloaibaocao);
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


}

