package com.material.travel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.material.travel.fragment.TravelFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT =9;
    private String mTitles[] ;

    public ViewPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return TravelFragment.newInstance(mTitles[0]);
            case 1:
                return TravelFragment.newInstance(mTitles[1]);
            case 2:
                return TravelFragment.newInstance(mTitles[2]);
            case 3:
                return TravelFragment.newInstance(mTitles[3]);
            case 4:
                return TravelFragment.newInstance(mTitles[4]);
            case 5:
                return TravelFragment.newInstance(mTitles[5]);
            case 6:
                return TravelFragment.newInstance(mTitles[6]);
            case 7:
                return TravelFragment.newInstance(mTitles[7]);

            case 8:
                return TravelFragment.newInstance(mTitles[8]);
        }
        return null;
    }

    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}