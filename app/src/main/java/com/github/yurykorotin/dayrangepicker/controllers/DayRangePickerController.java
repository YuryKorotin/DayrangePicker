package com.github.yurykorotin.dayrangepicker.controllers;

/**
 * Created by yuri on 27.04.17.
 */

import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.DaySelection;
import com.github.yurykorotin.dayrangepicker.views.OnDaySelectionListener;

import java.util.List;

public abstract class DayRangePickerController {
    public void setOnDaySelectionListener(OnDaySelectionListener onDaySelectionListener) {
        mOnDaySelectionListener = onDaySelectionListener;
    }

    private OnDaySelectionListener mOnDaySelectionListener;

    public enum FailEven {
        CONTAIN_NO_SELECTED,
        CONTAIN_NO_BUSY,
        CONTAIN_INVALID,
        NO_REACH_LEAST_DAYS,
        NO_REACH_MOST_DAYS,
        END_MT_START;
    }

    public DayRangePickerController() {
    }

    public abstract void onDayOfMonthSelected(CalendarDay calendarDay);

    public void onStartDaySelected(CalendarDay calendarDay) {
        if (mOnDaySelectionListener != null) {
            mOnDaySelectionListener.onStartDaySelected(calendarDay);
        }
    }

    public void onEndDaySelected(CalendarDay calendarDay) {
        if (mOnDaySelectionListener != null) {
            mOnDaySelectionListener.onEndDaySelected(calendarDay);
        }
    }

    public void onDateRangeSelected(DaySelection<CalendarDay> selectedDays) {
        if (mOnDaySelectionListener != null) {
            mOnDaySelectionListener.onPeriodSelected(selectedDays);
        }
    }

    public abstract void onDaysSelected(List<CalendarDay> seleDaysList);

    public abstract void alertSelectedFail(FailEven even);
}
