package com.example.quanlychitieucanhan.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.quanlychitieucanhan.R;
import com.example.quanlychitieucanhan.TabChiTieu;
import com.example.quanlychitieucanhan.TabTaiSan;
import com.example.quanlychitieucanhan.TabThuNhap;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position)
            {
                case 0:
                    TabChiTieu tabChiTieu = new TabChiTieu();
                    return tabChiTieu;
                case 1:
                    TabThuNhap tabThuNhap = new TabThuNhap();
                    return tabThuNhap;
                case 2:
                    TabTaiSan tabTaiSan = new TabTaiSan();
                    return tabTaiSan;
                default:
                    return null;
            }
        //return PlaceholderFragment.newInstance(position + 1);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}