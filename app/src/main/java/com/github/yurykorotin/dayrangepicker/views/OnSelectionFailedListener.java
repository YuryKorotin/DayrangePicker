package com.github.yurykorotin.dayrangepicker.views;

/**
 * Created by yuri on 23.05.17.
 */

public interface OnSelectionFailedListener {
    public void onMinimalPeriodExceed();
    public void onBusyDaysIntersection();
}
