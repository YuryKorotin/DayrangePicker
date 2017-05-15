package com.github.yurykorotin.dayrangepicker.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yurykorotin on 12/05/17.
 */

public class CalendarDataBuilder {
    private final Calendar mCalendar;
    private CalendarConfig mCalendarConfig;
    private final CalendarData mCalendarData;

    private DaySelection<CalendarDay> mInvalidDaySelection;
    private List<DaySelection<CalendarDay>> mBusyDaySelections = new ArrayList<>();

    public CalendarDataBuilder() {
        mCalendar = Calendar.getInstance();

        mCalendarData = new CalendarData();

        DaySelection selectedRange = new DaySelection();
        selectedRange.setFirst(new CalendarDay());
        selectedRange.setLast(new CalendarDay());

        mCalendarData.setRangeDays(selectedRange);
    }

    public CalendarDataBuilder setCalendarConfig(CalendarConfig calendarConfig) {
        mCalendarConfig = calendarConfig;

        setBusyDaySelections(calendarConfig.getBusyDays());
        setInvalidDaySelection(calendarConfig.getInvalidDays());

        return this;
    }

    public CalendarDataBuilder setInvalidDaySelection(CalendarDay firstInvalidDay,
                                                      CalendarDay lastInvalidDay) {
        DaySelection<CalendarDay> invalidDaySelection = new DaySelection<>();
        invalidDaySelection.setFirst(firstInvalidDay);
        invalidDaySelection.setLast(lastInvalidDay);
        invalidDaySelection.setType(DaySelection.DISABLED_TYPE);

        mInvalidDaySelection = invalidDaySelection;

        return this;
    }

    public CalendarDataBuilder setInvalidDaySelection(DaySelection<CalendarDay> daySelection) {
        if (daySelection == null) {
            return this;
        }

        daySelection.setType(DaySelection.DISABLED_TYPE);

        mInvalidDaySelection = daySelection;

        return this;
    }


    public CalendarDataBuilder setBusyDaySelections(List<DaySelection<CalendarDay>> busyDaySelections) {
        if (busyDaySelections == null) {
            return this;
        }
        for (DaySelection daySelection : busyDaySelections) {
            daySelection.setType(DaySelection.BUSY_TYPE);
        }

        mBusyDaySelections = busyDaySelections;

        return this;
    }

    public CalendarData build() {
        List<CalendarDay> invalidDays = new ArrayList<>();
        List<CalendarDay> busyDays = new ArrayList<>();

        CalendarDayRangeBuilder dayRangeBuilder = new CalendarDayRangeBuilder();

        mCalendarData.setInvalidDays(mInvalidDaySelection);
        mCalendarData.setBusyDays(mBusyDaySelections);

        invalidDays = dayRangeBuilder
                .setDaySelection(mInvalidDaySelection)
                .build();

        mCalendarData.setInvalidDayCollection(invalidDays);

        for (DaySelection daySelection : mBusyDaySelections) {
            busyDays.addAll(dayRangeBuilder
                    .setDaySelection(daySelection)
                    .build());
        }

        mCalendarData.setBusyDayCollection(busyDays);

        mCalendarData.setYearStart(mCalendarConfig.getYearStart());

        if (mCalendarConfig.getMonthStart() <= 0) {
            mCalendarData.setMonthStart(mCalendar.get(Calendar.MONTH));
        } else {
            mCalendarData.setMonthStart(mCalendarConfig.getMonthStart());
        }

        if (mCalendarConfig.getLeastDaysNum() <= 0) {
            mCalendarData.setLeastDaysNum(0);
        } else {
            mCalendarData.setLeastDaysNum(mCalendarConfig.getLeastDaysNum());
        }

        if (mCalendarConfig.getMostDaysNum() <= 0) {
            mCalendarData.setMostDaysNum(100);
        } else {
            mCalendarData.setMostDaysNum(mCalendarConfig.getMostDaysNum());
        }

        if (mCalendarConfig.getLeastDaysNum() > mCalendarConfig.getMostDaysNum()) {
            throw new IllegalArgumentException("Illegal config. Min day cannot be more than max");
        }

        if(mCalendarConfig.getMonthCount() <= 0) {
            mCalendarData.setMonthCount(12);
        } else {
            mCalendarData.setMonthCount(mCalendarConfig.getMonthCount());
        }

        return mCalendarData;
    }

}
