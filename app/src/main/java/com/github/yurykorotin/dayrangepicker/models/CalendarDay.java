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
    private Calendar calendar;

    public int day;
    public int month;
    public int year;
    public int hourdOfDay;
    public String tag;

    public CalendarDay(Calendar calendar, String tag) {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hourdOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        this.tag = tag;
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
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void setTime(long timeInMillis) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.setTimeInMillis(timeInMillis);
        month = this.calendar.get(Calendar.MONTH);
        year = this.calendar.get(Calendar.YEAR);
        day = this.calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void set(CalendarDay calendarDay) {
        year = calendarDay.year;
        month = calendarDay.month;
        day = calendarDay.day;
    }

    public void setDay(int year, int month, int day, int hourdOfDay) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hourdOfDay = hourdOfDay;
    }

    public void setDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hourdOfDay = 0;
    }

    public Date getDate() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.clear();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ year: ");
        stringBuilder.append(year);
        stringBuilder.append(", month: ");
        stringBuilder.append(month);
        stringBuilder.append(", day: ");
        stringBuilder.append(day);
        stringBuilder.append(", hour: ");
        stringBuilder.append(hourdOfDay);
        stringBuilder.append(" }");

        return stringBuilder.toString();
    }

    @Override
    public int compareTo(CalendarDay calendarDay) {
//            return getDate().compareTo(calendarDay.getDate());
        if (calendarDay == null) {
            throw new IllegalArgumentException("Calendar day is null");
        }

        if (year == calendarDay.year && month == calendarDay.month && day == calendarDay.day) {
            return 0;
        }

        if (year < calendarDay.year ||
                (year == calendarDay.year && month < calendarDay.month) ||
                (year == calendarDay.year && month == calendarDay.month && day < calendarDay.day)) {
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
        return hourdOfDay <= HALF_OF_DAY;
    }


    public boolean isSecondHalfDay() {
        return hourdOfDay > HALF_OF_DAY;
    }
}
