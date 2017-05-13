package com.github.yurykorotin.dayrangepicker.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yurykorotin on 12/05/17.
 */

public class CalendarData implements Serializable{
    private Calendar mCalendar;
    private DaySelection<CalendarDay> rangeDays;

    private List<CalendarDay> mBusyDays;
    private List<CalendarDay> mInvalidDays;
    private List<CalendarDay> mTags;
    private int mLeastDaysNum;
    private int mMostDaysNum;

    public CalendarData() {
        mCalendar = Calendar.getInstance();
    }

}
