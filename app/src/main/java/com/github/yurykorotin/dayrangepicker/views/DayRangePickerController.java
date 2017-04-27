package com.github.yurykorotin.dayrangepicker.views;

/**
 * Created by yuri on 27.04.17.
 */

import com.github.yurykorotin.dayrangepicker.models.CalendarDay;

import java.util.List;

public interface DayRangePickerController {
    enum FailEven {
        CONTAIN_NO_SELECTED,
        CONTAIN_INVALID,
        NO_REACH_LEAST_DAYS,
        NO_REACH_MOST_DAYS,
        END_MT_START;

    }
    void onDayOfMonthSelected(CalendarDay calendarDay);

    void onDateRangeSelected(List<CalendarDay> selectedDays);

    void onDaysSelected(List<CalendarDay> seleDaysList);

    void alertSelectedFail(FailEven even);
}
