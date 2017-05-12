package com.github.yurykorotin.dayrangepicker.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuri on 27.04.17.
 */

public class CalendarDay implements Serializable, Comparable<CalendarDay> {
    private static final long serialVersionUID = -5456695978688356202L;
    private static final int HALF_OF_DAY = 12;
    private static final int MAX_MONTH_COUNT = 12;
    private Calendar calendar;

    private int mDay;
    private int mMonth;
    private int mYear;
    private int mHourdOfDay;

    public int getDay() {
        return mDay;
    }

    public void setDay(int day) {
        mDay = day;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public int getHourdOfDay() {
        return mHourdOfDay;
    }

    public void setHourdOfDay(int hourdOfDay) {
        mHourdOfDay = hourdOfDay;
    }

    public String getTag() {
        return mTag;
    }

    private String mTag;

    public CalendarDay(Calendar calendar, String tag) {
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHourdOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        this.mTag = tag;
    }

    public CalendarDay() {
        setTime(System.currentTimeMillis());
    }

    public CalendarDay(int year, int month, int day) {
        setDay(year, month, day, 0);
    }

    public CalendarDay(int year, int month, int day, int hourdOfDay) {
        setDay(year, month, day, hourdOfDay);
    }

    public CalendarDay(long timeInMillis) {
        setTime(timeInMillis);
    }

    public CalendarDay(Calendar calendar) {
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void setTime(long timeInMillis) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.setTimeInMillis(timeInMillis);
        mMonth = this.calendar.get(Calendar.MONTH);
        mYear = this.calendar.get(Calendar.YEAR);
        mDay = this.calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void set(CalendarDay calendarDay) {
        mYear = calendarDay.getYear();
        mMonth = calendarDay.getMonth();
        mDay = calendarDay.getDay();
    }

    public void setDay(int year, int month, int day, int hourdOfDay) {
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        this.mHourdOfDay = hourdOfDay;
    }

    public void setDay(int year, int month, int day) {
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        this.mHourdOfDay = 0;
    }

    public Date getDate() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.clear();
        calendar.set(mYear, mMonth, mDay);
        return calendar.getTime();
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ mYear: ");
        stringBuilder.append(mYear);
        stringBuilder.append(", mMonth: ");
        stringBuilder.append(mMonth);
        stringBuilder.append(", mDay: ");
        stringBuilder.append(mDay);
        stringBuilder.append(", hour: ");
        stringBuilder.append(mHourdOfDay);
        stringBuilder.append(" }");

        return stringBuilder.toString();
    }

    @Override
    public int compareTo(CalendarDay calendarDay) {
//            return getDate().compareTo(calendarDay.getDate());
        if (calendarDay == null) {
            throw new IllegalArgumentException("Calendar mDay is null");
        }

        if (mYear == calendarDay.mYear && mMonth == calendarDay.mMonth && mDay == calendarDay.mDay) {
            return 0;
        }

        if (mYear < calendarDay.mYear ||
                (mYear == calendarDay.mYear && mMonth < calendarDay.mMonth) ||
                (mYear == calendarDay.mYear && mMonth == calendarDay.mMonth && mDay < calendarDay.mDay)) {
            return -1;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CalendarDay) {
            CalendarDay calendarDay = (CalendarDay) o;
            if (compareTo(calendarDay) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean after(Object o) {
        if (o instanceof CalendarDay) {
            CalendarDay calendarDay = (CalendarDay) o;
            if (compareTo(calendarDay) == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean before(Object o) {
        if (o instanceof CalendarDay) {
            CalendarDay calendarDay = (CalendarDay) o;
            if (compareTo(calendarDay) == -1) {
                return true;
            }
        }
        return false;
    }

    public boolean isFirstHalfDay() {
        return mHourdOfDay <= HALF_OF_DAY;
    }


    public boolean isSecondHalfDay() {
        return mHourdOfDay > HALF_OF_DAY;
    }

    public CalendarDay next() {


        CalendarDay nextday = new CalendarDay();
        if (getMonth() != MAX_MONTH_COUNT) {

        }
    }
}
