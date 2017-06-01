package com.github.yurykorotin.dayrangepicker.controllers;

import android.support.design.widget.TabLayout;

/**
 * Created by yuri on 20.05.17.
 */

public class DayRangeTabController {
    private TabLayout.Tab mLastDayTab;
    private TabLayout.Tab mFirstDayTab;

    public DayRangeTabController() {

    }

    public DayRangeTabController(TabLayout.Tab firstTab, TabLayout.Tab lastTab) {
        mFirstDayTab = firstTab;
        mLastDayTab = lastTab;
    }

    public void onFirstTabSelected() {
        if (mFirstDayTab != null) {
            mFirstDayTab.select();
        }
    }

    public void onLastTabSelected() {
        if (mLastDayTab != null) {
            mLastDayTab.select();
        }
    }

}
