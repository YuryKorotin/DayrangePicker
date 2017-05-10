package com.github.yurykorotin.dayrangepicker;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * Created by yuri on 27.04.17.
 */

public class Utils {
    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid month");
        }
    }
    public static void enableTabs(TabLayout tabLayout, Boolean enable){
        ViewGroup viewGroup = getTabViewGroup(tabLayout);
        if (viewGroup != null)
            for (int childIndex = 0; childIndex < viewGroup.getChildCount(); childIndex++)
            {
                View tabView = viewGroup.getChildAt(childIndex);
                if ( tabView != null)
                    tabView.setEnabled(enable);
            }
    }


    public static View getTabView(TabLayout tabLayout, int position){
        View tabView = null;
        ViewGroup viewGroup = getTabViewGroup(tabLayout);
        if (viewGroup != null && viewGroup.getChildCount() > position)
            tabView = viewGroup.getChildAt(position);

        return tabView;
    }

    private static ViewGroup getTabViewGroup(TabLayout tabLayout){
        ViewGroup viewGroup = null;

        if (tabLayout != null && tabLayout.getChildCount() > 0 ) {
            View view = tabLayout.getChildAt(0);
            if (view != null && view instanceof ViewGroup)
                viewGroup = (ViewGroup) view;
        }
        return viewGroup;
    }
}
