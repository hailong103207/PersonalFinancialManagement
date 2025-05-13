package com.example.quanlychitieucanhan;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

final class modun {
    private modun() {
    }

    static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
            long tmp = Math.round(value);
            return (double) tmp / factor;
        }

//        static String readText(File file) {
//            String text = "";
//            BufferedReader input = null;
//            try {
//                input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//                String line;
//                StringBuffer buffer = new StringBuffer();
//                while ((line = input.readLine()) != null) {
//                buffer.append(line).append("\n");
//            }
//            text = buffer.toString();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return text;
//    }
    static String readText(File file) {
        String text = "";
        try {
            FileInputStream fIn = new FileInputStream(file);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(
                    fIn));
            String aDataRow = "";
            StringBuilder buffer = new StringBuilder();

            while ((aDataRow = myReader.readLine()) != null) {
                buffer.append(aDataRow);
                buffer.append("\n");
            }
            text = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    static void TaoThuMuc(File file) {
        /**THÊM FOLDER GIÁM SÁT*/
        //File Template = new File(Environment.getExternalStorageDirectory(),"DataAppThuChi");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.d("App", "failed to create directory");
            } else {
                //Toast.makeText(getApplicationContext(), "Đã thêm folder Template vào bộ nhớ" , Toast.LENGTH_SHORT).show();
            }
        }
    }

    static void saveTextFile(String name, String text, File file) {
        String content = text;
        FileOutputStream outputStream;
        try {
            if (!file.exists())
                if (!file.mkdirs()) {
                    Log.d("App", "failed to create directory");
                }
            file = new File(file, name + ".txt");
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
            //Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static final File DataAppThuChi = new File(Environment.getExternalStorageDirectory(), "DataAppThuChi");
    static final File ChiTieu = new File(DataAppThuChi, "ChiTieu"); //DatapThuChi\Chitieu
    static final File ThuNhap = new File(DataAppThuChi, "ThuNhap"); //DatapThuChi\Chitieu
    static final File TaiSan = new File(DataAppThuChi, "TaiSan"); //DatapThuChi\Chitieu
    static final File Vi = new File(DataAppThuChi, "Vi"); //DatapThuChi\Chitieu
    static final File ThuocTinh = new File(DataAppThuChi, "ThuocTinh"); //DatapThuChi\Chitieu
    static final File TaiSan_ThuocTinh = new File(ThuocTinh, "TaiSan"); //DatapThuChi\Chitieu
    static final File Icon = new File(DataAppThuChi, "Icon"); //DatapThuChi\Chitieu
    static final File TaiSan_Icon = new File(Icon, "TaiSan"); //DatapThuChi\Chitieu
    static final File ThuThap_Icon = new File(Icon, "ThuThap"); //DatapThuChi\Chitieu
    static final File ChiTieu_Icon = new File(Icon, "ChiTieu"); //DatapThuChi\Chitieu
    static final File Vi_Icon = new File(Icon, "Vi"); //DatapThuChi\Chitieu
    static final ArrayList<String> listSoTien = new ArrayList<String>(Arrays.asList("50", "500", "5000", "50000","500000"));

    static void setPopUp(Context context,  AutoCompleteTextView edt,  ArrayList<String> arrayList){
        ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(context, R.layout.custom_list_item, R.id.text_view_list_item, arrayList);
        edt.setAdapter(adapterHT);
        edt.setThreshold(1);
        edt.setDropDownHeight(400);

    }

}
