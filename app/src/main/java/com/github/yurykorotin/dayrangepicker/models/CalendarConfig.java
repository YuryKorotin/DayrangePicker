package com.github.yurykorotin.dayrangepicker.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yuri on 27.04.17.
 */

public class CalendarConfig implements Serializable {
    
    public static int DEFAULT_MONTH_COUNT = 24;
    //public enum TYPE {TYPE_MULTI, TYPE_RANGE, TYPE_ONLY_READ}

    //TYPE type;
    private int mYearStart = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonthStart = -1;
    private int mMonthCount = DEFAULT_MONTH_COUNT;
    private int mLeastDaysNum;
    private int mMostDaysNum;
    private String mDefTag;
    private DaySelection<CalendarDay> mSelectedDays;
    private DaySelection<CalendarDay> mInvalidDays;
    private List<DaySelection<CalendarDay>> mBusyDays;
    private List<CalendarDay> mTags;


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

    public String getDefTag() {
        return mDefTag;
    }

    public void setDefTag(String defTag) {
        mDefTag = defTag;
    }

    public DaySelection<CalendarDay> getSelectedDays() {
        if (mSelectedDays == null) {
            mSelectedDays = new DaySelection<>();
        }
        return mSelectedDays;
    }

    public void setSelectedDays(DaySelection<CalendarDay> selectedDays) {
        mSelectedDays = selectedDays;
    }

    public DaySelection<CalendarDay> getInvalidDays() {
        return mInvalidDays;
    }

    public void setInvalidDays(DaySelection<CalendarDay> invalidDays) {
        mInvalidDays = invalidDays;
    }

    public List<DaySelection<CalendarDay>> getBusyDays() {
        return mBusyDays;
    }

    public void setBusyDays(List<DaySelection<CalendarDay>> busyDays) {
        mBusyDays = busyDays;
    }

    public List<CalendarDay> getTags() {
        return mTags;
    }

    public void setTags(List<CalendarDay> tags) {
        mTags = tags;
    }
}
