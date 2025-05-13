package com.example.quanlychitieucanhan;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter_LichSu extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_3,R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter_LichSu(Context context, FragmentManager fm) {
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
                    tabLichSuTatCa tabTatCa = new tabLichSuTatCa();
                    return tabTatCa;
                case 1:
                    tabLichSuChiTieu tabChiTieu = new tabLichSuChiTieu();
                    return tabChiTieu;
                case 2:
                    tabLichSuThuNhap tabThuNhap = new tabLichSuThuNhap();
                    return tabThuNhap;
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
        // Show 3 total pages.
        return 3;
    }
}