package com.example.quanlychitieucanhan;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class IconAdapter extends BaseAdapter {
    List<Uri> myObject = new ArrayList<>();

    Context mycontext;
    int myRecource;
    private ArrayList<String> myList = new ArrayList<String>();
    private ArrayList<Uri> arraylist = new ArrayList<Uri>();

    public IconAdapter( Context mycontext, int myRecource) {

        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName()+ "/" + R.drawable.icon1));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon2));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon3));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon4));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon5));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon6));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon7));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon8));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon9));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon10));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon11));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon12));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon13));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon14));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon15));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon16));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon17));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon18));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon19));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon20));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon21));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon22));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon23));
        myObject.add(Uri.parse("android.resource://" + mycontext.getPackageName() + "/" + R.drawable.icon24));
        this.mycontext = mycontext;
        this.myRecource = myRecource;
        this.arraylist = new ArrayList<Uri>();
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
            V = inflater.inflate(R.layout.grid_item_icon, null);
        }
        ImageView imageView = V.findViewById(R.id.imgIcon);


        imageView.setImageURI(myObject.get(i));

        return V;
    }

}