package com.github.yurykorotin.dayrangepicker.views;

import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.DaySelection;

/**
 * Created by yuri on 03.05.17.
 */

public interface OnDaySelectionListener {
    void onStartDaySelected(CalendarDay calendarDay);

    void onEndDaySelected(CalendarDay calendarDay);

    void onPeriodSelected(DaySelection<CalendarDay> selection);
}
