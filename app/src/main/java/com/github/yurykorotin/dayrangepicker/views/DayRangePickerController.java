package com.github.yurykorotin.dayrangepicker.views;

/**
 * Created by yuri on 27.04.17.
 */

import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.DaySelection;

import java.util.List;

public abstract class DayRangePickerController {
    private OnDaySelectionListener mOnDaySelectionListener;

    protected enum FailEven {
        CONTAIN_NO_SELECTED,
        CONTAIN_INVALID,
        NO_REACH_LEAST_DAYS,
        NO_REACH_MOST_DAYS,
        END_MT_START;

    }

    public DayRangePickerController() {

    }

    public DayRangePickerController(OnDaySelectionListener onDaySelectionListener) {
        mOnDaySelectionListener = onDaySelectionListener;
    }

    protected abstract void onDayOfMonthSelected(CalendarDay calendarDay);

    protected void onStartDaySelected(CalendarDay calendarDay) {
        if (mOnDaySelectionListener != null) {
            mOnDaySelectionListener.onStartDaySelected(calendarDay);
        }
    }

    protected void onEndDaySelected(CalendarDay calendarDay) {
        if (mOnDaySelectionListener != null) {
            mOnDaySelectionListener.onEndDaySelected(calendarDay);
        }
    }

    protected abstract void onDateRangeSelected(DaySelection<CalendarDay> selectedDays);

    protected abstract void onDaysSelected(List<CalendarDay> seleDaysList);

    protected abstract void alertSelectedFail(FailEven even);
}
