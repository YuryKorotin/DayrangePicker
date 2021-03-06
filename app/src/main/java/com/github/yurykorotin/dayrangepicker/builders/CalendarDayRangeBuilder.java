package com.github.yurykorotin.dayrangepicker.builders;

import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.DaySelection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarDayRangeBuilder {

    private DaySelection<CalendarDay> mDaySelection;
    public CalendarDayRangeBuilder() {

    }

    public CalendarDayRangeBuilder setDaySelection(DaySelection daySelection) {
        mDaySelection = daySelection;
        return this;
    }

    protected int dateDiff(CalendarDay first, CalendarDay last) {
        long dayDiff = (last.getDate().getTime() - first.getDate().getTime()) / (1000 * 3600 * 24);
        return Integer.valueOf(String.valueOf(dayDiff)) + 1;
    }

    public List<CalendarDay> build() {
        List<CalendarDay> rangeDays = new ArrayList<>();

        if (mDaySelection == null) {
            return rangeDays;
        }
        @CalendarDay.DayType int dayType = getDayType();

        CalendarDay firstDay = mDaySelection.getFirst();
        CalendarDay lastDay = mDaySelection.getLast();
        rangeDays.add(firstDay);
        int diffDays = dateDiff(firstDay, lastDay);
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(firstDay.getDate());
        for (int i = 1; i < diffDays; i++) {
            tempCalendar.set(Calendar.DATE, tempCalendar.get(Calendar.DATE) + 1);
            CalendarDay calendarDay = new CalendarDay(tempCalendar);
            calendarDay.setType(dayType);
            rangeDays.add(calendarDay);
        }
        return rangeDays;
    }

    private int getDayType() {
        switch (mDaySelection.getType()) {
            case DaySelection.BUSY_TYPE:
                return CalendarDay.BUSY_TYPE;
            case DaySelection.DISABLED_TYPE:
                return CalendarDay.DISABLED_TYPE;
            case DaySelection.SELECTED_TYPE:
                return CalendarDay.SELECTED_TYPE;

            default:
                return CalendarDay.BUSY_TYPE;
        }
    }
}
