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

public class ListviewAdapter extends BaseAdapter {

    private List<PhanLoai> myObject = new ArrayList<PhanLoai>();
    Context mycontext;
    int myRecource;
    private ArrayList<String> myList = new ArrayList<String>();
    private ArrayList<PhanLoai> arraylist;

    public ListviewAdapter(List<PhanLoai> myObject, Context mycontext, int myRecource) {
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
            V = inflater.inflate(myRecource, null);
        }
        if (myRecource == R.layout.list_item){
            ImageView imageView = V.findViewById(R.id.img_avatar);
            TextView tvPhanLoai = V.findViewById(R.id.tvPhanLoai);
            TextView tvThuocTinh = V.findViewById(R.id.tvThuocTinh);
            TextView tvTongTienChi = V.findViewById(R.id.tvTongTienChi);
            TextView tvPhanTramChi = V.findViewById(R.id.tvPhanTramChi);
            LinearLayout lnBinhThuong = V.findViewById(R.id.lnBinhThuong);
            imageView.setImageURI(myObject.get(i).getImageID());
            tvPhanLoai.setText(myObject.get(i).getPhanLoai());
            tvPhanTramChi.setText(String.valueOf(myObject.get(i).getPhanTramChi())+"%");
            tvTongTienChi.setText((String.format("%,d",(Long.parseLong(String.valueOf(Math.round(myObject.get(i).getTongTienChi())))))).replace(",",",").replace(".",","));
            if (myObject.get(i).getKieu().trim().equals("ts")){
                File ThuocTinhTS = new File(modun.TaiSan_ThuocTinh,myObject.get(i).getPhanLoai().trim()+".txt");
                if (modun.readText(ThuocTinhTS).trim().equals("tstd")){
                    lnBinhThuong.setBackgroundResource(R.drawable.backgeound_item_muctieu_td);
                    tvThuocTinh.setText("(Ts tiêu dùng)");
                }else if (modun.readText(ThuocTinhTS).trim().equals("tsdt")){
                    lnBinhThuong.setBackgroundResource(R.drawable.backgeound_item_muctieu_dt);
                    tvThuocTinh.setText("(Ts đầu tư)");
                }else if (modun.readText(ThuocTinhTS).trim().equals("tsno")){
                    lnBinhThuong.setBackgroundResource(R.drawable.backgeound_item_muctieu_no);
                    tvThuocTinh.setText("(Ts nợ)");
                }else if (modun.readText(ThuocTinhTS).trim().equals("tstm")){
                    lnBinhThuong.setBackgroundResource(R.drawable.backgeound_item_muctieu_tm);
                    tvThuocTinh.setText("(Ts tiền mặt)");
                }
            }
        }else if (myRecource == R.layout.list_item_baocaotaisan){
            ImageView imageView = V.findViewById(R.id.img_avatar);
            TextView tvPhanLoai = V.findViewById(R.id.tvPhanLoai);
            TextView tvTongTienChi = V.findViewById(R.id.tvTongTienChi);
            TextView tvPhanTramChi = V.findViewById(R.id.tvPhanTramChi);
            tvPhanLoai.setText(myObject.get(i).getPhanLoai());
            tvPhanTramChi.setText(String.valueOf(myObject.get(i).getPhanTramChi())+"%");
            imageView.setImageURI(myObject.get(i).getImageID());
                tvTongTienChi.setText((String.format("%,d",(Long.parseLong(String.valueOf(Math.round(myObject.get(i).getTongTienChi())))))).replace(",",",").replace(".",","));
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
                        .contains(charText) || wp.getTongTienChi().toString().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    myObject.add(wp);
                    myList.add(wp.getPhanLoai());
                }
            }
        }
        notifyDataSetChanged();
        return myList;
    }
}

