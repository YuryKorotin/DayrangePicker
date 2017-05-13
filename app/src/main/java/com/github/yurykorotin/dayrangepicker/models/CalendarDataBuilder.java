package com.github.yurykorotin.dayrangepicker.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yurykorotin on 12/05/17.
 */

public class CalendarDataBuilder {
    private CalendarConfig mCalendarConfig;
    private final CalendarData mCalendarData;

    private DaySelection<CalendarDay> mInvalidDaySelection;
    private List<DaySelection<CalendarDay>> mBusyDaySelections;

    public CalendarDataBuilder() {
        mCalendarData = new CalendarData();
    }

    public CalendarDataBuilder setCalendarConfig(CalendarConfig calendarConfig) {
        mCalendarConfig = calendarConfig;

        return this;
    }

    public CalendarDataBuilder setInvalidDaySelection(DaySelection<CalendarDay> invalidDaySelection) {
        mInvalidDaySelection = invalidDaySelection;
        return this;
    }

    public void setBusyDaySelections(List<DaySelection<CalendarDay>> busyDaySelections) {

        mBusyDaySelections = busyDaySelections;
    }

    public CalendarData build() {

        List<CalendarDay> invalidDays = new ArrayList<>();
        List<CalendarDay> busyDays = new ArrayList<>();

        CalendarDayRangeBuilder dayRangeBuilder = new CalendarDayRangeBuilder();

        invalidDays = dayRangeBuilder
                .setDaySelection(mInvalidDaySelection)
                .build();

        mCalendarData.(invalidDays);


        if (dataModel.monthStart <= 0) {
            dataModel.monthStart = calendar.get(Calendar.MONTH);
        }

        if (dataModel.getLeastDaysNum() <= 0) {
            dataModel.leastDaysNum = 0;
        }

        if (dataModel.mostDaysNum <= 0) {
            dataModel.mostDaysNum = 100;
        }

        if (dataModel.leastDaysNum > dataModel.mostDaysNum) {
            //Log.e("error", "可选择的最小天数不能小于最大天数");
            //throw new IllegalArgumentException("可选择的最小天数不能小于最大天数");
        }

        if(dataModel.monthCount <= 0) {
            dataModel.monthCount = 12;
        }

        if(dataModel.defTag == null) {
            dataModel.defTag = "label";
        }

        mLeastDaysNum = dataModel.leastDaysNum;
        mMostDaysNum = dataModel.mostDaysNum;

        mBusyDays = dataModel.busyDays;
        mInvalidDays = dataModel.invalidDays;
        rangeDays = dataModel.selectedDays;
        mTags = dataModel.tags;
        mDefTag = dataModel.defTag;

        return mCalendarData;
    }

}
