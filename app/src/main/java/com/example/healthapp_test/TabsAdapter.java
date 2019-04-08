package com.example.healthapp_test;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ScheduleTab();
        } else if (position == 1){
            return new GoalsTab();
        } else{
            return new TrendsTab();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 3;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        if (position == 0) {
            return mContext.getString(R.string.schedule_tab);
        } else if (position == 1){
            return mContext.getString(R.string.goals_tab);
        } else{
            return mContext.getString(R.string.trends_tab);
        }
    }

}