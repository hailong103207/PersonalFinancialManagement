package com.example.quanlychitieucanhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TaiSan_Adapter extends BaseAdapter {
    private List<clsTaiSan> myObject;
    Context mycontext;
    int myRecource;
    private ArrayList<String> myList = new ArrayList<String>();
    private ArrayList<clsTaiSan> arraylist;
    TextView tvKhauHaoTrongKy,tvThangNam, tvThangMat, tvMucTieu, tvChi, tvDuKien, tvGiaTriHienTai, tvLoai, tvDaMat, tvMatGia, tvThiTruong, tvLangPhat;

    public TaiSan_Adapter(List<clsTaiSan> myObject, Context mycontext, int myRecource) {
        this.myObject = myObject;
        this.mycontext = mycontext;
        this.myRecource = myRecource;
        this.arraylist = new ArrayList<clsTaiSan>();
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
            V = inflater.inflate(myRecource, null);
        }
//        ImageView imgIcon = V.findViewById(R.id.img_avatar);
        if (myRecource == R.layout.layout_item_khauhaotrongky){
            tvKhauHaoTrongKy = V.findViewById(R.id.tvKhauHaoTrongKy);
            tvKhauHaoTrongKy.setText((String.format("%,d",Long.parseLong(String.valueOf(Math.round(Double.valueOf(myObject.get(i).getKhauHaoTrongKy())))))).replace(".",",").replace(",",","));
        }

        tvThangMat = V.findViewById(R.id.tvThangMat);        tvThangNam = V.findViewById(R.id.tvThangNam);
        tvMucTieu = V.findViewById(R.id.tvMucTieu);
        tvChi = V.findViewById(R.id.tvChi);
        tvDuKien = V.findViewById(R.id.tvDuKien);
        tvGiaTriHienTai = V.findViewById(R.id.tvGiaTriHienTai);
        tvLoai = V.findViewById(R.id.tvLoai);
        tvDaMat = V.findViewById(R.id.tvTongMat);
        tvMatGia = V.findViewById(R.id.tvMatGia);
        tvThiTruong = V.findViewById(R.id.tvThiTruong);
        tvLangPhat = V.findViewById(R.id.tvLangPhat);
        /*double bien_MatTrongThang = Double.valueOf(myObject.get(i).getSoTien())/Double.valueOf(myObject.get(i).getDuKien())/12;
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String Ngay = sdf.format(new Date());
        String ThangNam = Ngay.split("-")[1] + " " +Ngay.split("-")[2];
        double bien_hientai = Double.valueOf(ThangNam.split(" ")[1])*12 + Double.valueOf(ThangNam.split(" ")[0]);
        double bien_thoidiemmua= Double.valueOf(myObject.get(i).getThangNam().split(" ")[1])*12 + Double.valueOf(myObject.get(i).getThangNam().split(" ")[0]);
        double bien_damat = (bien_hientai - bien_thoidiemmua) * bien_MatTrongThang;
        bien_damat = modun.round(bien_damat,0);
        bien_MatTrongThang = modun.round(bien_MatTrongThang,0);*/
        tvLangPhat.setText(myObject.get(i).getLangPhat().trim() + "%");
        tvThiTruong.setText((String.format("%,d",Long.parseLong(String.valueOf(Math.round(Double.valueOf(myObject.get(i).getThiTruong())))))).replace(".",",").replace(",",","));
        tvDaMat.setText((String.format("%,d",Long.parseLong(String.valueOf(Math.round(Double.valueOf(myObject.get(i).getTongMat().trim())))))).replace(".",",").replace(",",","));
        tvThangMat.setText((String.format("%,d",Long.parseLong(String.valueOf(Math.round(Double.valueOf(myObject.get(i).getMoiThangMat().trim())))))).replace(".",",").replace(",",","));
        tvGiaTriHienTai.setText((String.format("%,d",Long.parseLong(String.valueOf(Math.round(Double.valueOf(myObject.get(i).getHienTai())))))).replace(".",",").replace(",",","));
        tvLoai.setText("  " + myObject.get(i).getLoai());
        tvDuKien.setText(myObject.get(i).getDuKien().trim() + " năm");
        tvChi.setText((String.format("%,d",Long.parseLong(myObject.get(i).getSoTien()))).replace(".",",").replace(",",","));
//        imgIcon.setImageURI(myObject.get(i).getIcon());
        tvMatGia.setText((String.format("%,d",Long.parseLong(String.valueOf(Math.round(Double.valueOf(myObject.get(i).getMatGia())))))).replace(".",",").replace(",",","));
        tvThangNam.setText(myObject.get(i).getThangNam());
        tvMucTieu.setText(myObject.get(i).getTenMucTieu());

        SetMauText();
        return V;
    }

    private void SetMauText() {
        if (Double.valueOf(tvGiaTriHienTai.getText().toString().trim().replace(",","").replace(",",""))>=Double.valueOf(tvChi.getText().toString().trim().replace(",","").replace(".",""))){
            tvGiaTriHienTai.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary));
        }else tvGiaTriHienTai.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary2));
        if (Double.valueOf(tvThangMat.getText().toString().trim().replace(",","").replace(",",""))>= 0){
            tvThangMat.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary));
        }else tvThangMat.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary2));
        if (Double.valueOf(tvMatGia.getText().toString().trim().replace(",","").replace(",",""))>= 0){
            tvMatGia.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary));
        }else tvMatGia.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary2));
        if (Double.valueOf(tvThiTruong.getText().toString().trim().replace(",","").replace(",",""))>= 0){
            tvThiTruong.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary));
        }else tvThiTruong.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary2));
        if (Double.valueOf(tvDaMat.getText().toString().trim().replace(",","").replace(",",""))>= 0){
            tvDaMat.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary));
        }else tvDaMat.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary2));
        if (Double.valueOf(tvLangPhat.getText().toString().trim().replace("%","")) > 0){
            tvLangPhat.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary2));
        }else tvLangPhat.setTextColor(mycontext.getResources().getColor(R.color.colorPrimary));
    }
    public ArrayList<String> filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myObject.clear();
        myList.clear();
        if (charText.length() == 0)
        {
            myObject.addAll(arraylist);
        }
        else {
            for (clsTaiSan wp : arraylist) {
                if (wp.getLoai().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getThangNam().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getNgayThangNam().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getTenMucTieu().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getSoTien().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    myObject.add(wp);
                    myList.add(wp.getLoai());
                }
            }
        }
        notifyDataSetChanged();

        return myList;
    }

}
