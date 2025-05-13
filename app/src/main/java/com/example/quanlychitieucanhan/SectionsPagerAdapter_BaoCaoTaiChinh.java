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
public class SectionsPagerAdapter_BaoCaoTaiChinh extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter_BaoCaoTaiChinh(Context context, FragmentManager fm) {
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
                    tabBaoCaoChiTieu tabBaoCaoChiTieu = new tabBaoCaoChiTieu();
                    return tabBaoCaoChiTieu;
                case 1:
                    tabBaoCaoThuNhap tabBaoCaoThuNhap = new tabBaoCaoThuNhap();
                    return tabBaoCaoThuNhap;
                case 2:
                    tabBaoCaoTaiSan tabBaoCaoTaiSan = new tabBaoCaoTaiSan();
                    return tabBaoCaoTaiSan;
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