package com.github.yurykorotin.dayrangepicker.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yurykorotin on 12/05/17.
 */

public class CalendarData implements Serializable{
    private DaySelection<CalendarDay> mRangeDays;
    private List<CalendarDay> mBusyDayCollection;
    private List<DaySelection<CalendarDay>> mBusyDays;
    private DaySelection<CalendarDay> mInvalidDays;
    private DaySelection<CalendarDay> mTags;
    private int mLeastDaysNum;
    private int mMostDaysNum;
    private int mYearStart;
    private int mMonthStart;
    private int mMonthCount;

    public DaySelection<CalendarDay> getRangeDays() {
        return mRangeDays;
    }

    public void setRangeDays(DaySelection<CalendarDay> rangeDays) {
        mRangeDays = rangeDays;
    }

    public List<DaySelection<CalendarDay>> getBusyDays() {
        return mBusyDays;
    }

    public void setBusyDays(List<DaySelection<CalendarDay>> busyDays) {
        mBusyDays = busyDays;
    }

    public DaySelection<CalendarDay> getInvalidDays() {
        return mInvalidDays;
    }

    public void setInvalidDays(DaySelection<CalendarDay> invalidDays) {
        mInvalidDays = invalidDays;
    }

    public DaySelection<CalendarDay> getTags() {
        return mTags;
    }

    public void setTags(DaySelection<CalendarDay> tags) {
        mTags = tags;
    }

    public int getLeastDaysNum() {
        return mLeastDaysNum;
    }

    public void setLeastDaysNum(int leastDaysNum) {
        mLeastDaysNum = leastDaysNum;
    }

    public int getMostDaysNum() {
        return mMostDaysNum;
    }

    public void setMostDaysNum(int mostDaysNum) {
        mMostDaysNum = mostDaysNum;
    }

    public int getYearStart() {
        return mYearStart;
    }

    public void setYearStart(int yearStart) {
        mYearStart = yearStart;
    }

    public int getMonthStart() {
        return mMonthStart;
    }

    public void setMonthStart(int monthStart) {
        mMonthStart = monthStart;
    }

    public int getMonthCount() {
        return mMonthCount;
    }

    public void setMonthCount(int monthCount) {
        mMonthCount = monthCount;
    }

    public CalendarData() {
    }

    public List<CalendarDay> getBusyDayCollection() {
        return mBusyDayCollection;
    }

    public void setBusyDayCollection(List<CalendarDay> busyDayCollection) {
        mBusyDayCollection = busyDayCollection;
    }
}
