package com.github.yurykorotin.dayrangepicker.models;

import android.support.annotation.IntDef;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yurykorotin on 12/05/17.
 */

public class CalendarData implements Serializable{
    private @SelectionMode
    int mSelectionMode = COMMON_SELECTION_MODE;

    public @SelectionMode
    int getSelectionMode() {
        return mSelectionMode;
    }

    public void setSelectionMode(@SelectionMode int selectionMode) {
        mSelectionMode = selectionMode;
    }

    @IntDef({FIRST_DATE_SELECTION_MODE,
            LAST_DATE_SELECTION_MODE,
            COMMON_SELECTION_MODE,
            DISABLED_SELECTION_MODE})

    public @interface SelectionMode{}

    public static final int FIRST_DATE_SELECTION_MODE = 0;
    public static final int LAST_DATE_SELECTION_MODE = 1;
    public static final int COMMON_SELECTION_MODE = 2;
    public static final int DISABLED_SELECTION_MODE = 3;

    private DaySelection<CalendarDay> mRangeDays;
    private List<CalendarDay> mBusyDayCollection;
    private List<CalendarDay>  mFirstLastDayCollection;
    private List<CalendarDay> mInvalidDayCollection;
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

    public List<CalendarDay> getInvalidDayCollection() {
        return mInvalidDayCollection;
    }

    public void setInvalidDayCollection(List<CalendarDay> invalidDayCollection) {
        mInvalidDayCollection = invalidDayCollection;
    }

    public List<CalendarDay> getFirstLastDayCollection() {
        return mFirstLastDayCollection;
    }

    public void setFirstLastDayCollection(List<CalendarDay> firstLastDayCollection) {
        mFirstLastDayCollection = firstLastDayCollection;
    }
}
