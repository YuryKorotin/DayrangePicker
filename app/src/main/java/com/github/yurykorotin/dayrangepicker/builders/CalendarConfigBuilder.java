package com.github.yurykorotin.dayrangepicker.builders;

import android.content.Context;

import com.github.yurykorotin.dayrangepicker.models.CalendarConfig;
import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.DaySelection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yuri on 15.05.17.
 */

public class CalendarConfigBuilder {
    private final Calendar mCalendar;
    private String mCalendarConfigFileName;
    private CalendarConfig mCalendarConfig;
    private Context mContext;

    private int mYearStart;
    private int mMonthStart;
    private int mMonthCount;
    private int mLeastDaysNum;
    private int mMostDaysNum;
    private String mDefTag;

    private DaySelection<CalendarDay> mDaySelections;
    private List<DaySelection<CalendarDay>> mBusyDays;
    private DaySelection<CalendarDay> mInvalidDays;

    public CalendarConfigBuilder() {
        mCalendarConfig = new CalendarConfig();
        mCalendar = Calendar.getInstance();
    }

    public CalendarConfigBuilder setCalendarConfigFileName(String calendarConfigFileName) {
        mCalendarConfigFileName = calendarConfigFileName;
        return this;
    }

    public CalendarConfigBuilder setYearStart(int yearStart) {
        mYearStart = yearStart;
        return this;
    }

    public CalendarConfigBuilder setMonthStart(int monthStart) {
        mMonthStart = monthStart;
        return this;
    }


    public CalendarConfigBuilder setMonthCount(int monthCount) {
        mMonthCount = monthCount;
        return this;
    }


    public CalendarConfigBuilder setLeastDaysNum(int leastDaysNum) {
        mLeastDaysNum = leastDaysNum;
        return this;
    }


    public CalendarConfigBuilder setMostDaysNum(int mostDaysNum) {
        mMostDaysNum = mostDaysNum;
        return this;
    }


    public CalendarConfigBuilder addBusyRange(Date firstDate, Date lastDate) {
        DaySelection<CalendarDay> calendarDayDaySelection = new DaySelection<>();

        mCalendar.setTime(firstDate);
        CalendarDay firstCalendarDay = new CalendarDay(mCalendar);

        mCalendar.setTime(lastDate);
        CalendarDay lastCalendarDay = new CalendarDay(mCalendar);

        if(mBusyDays == null) {
            mBusyDays = new ArrayList<>();
        }

        mBusyDays.add(new DaySelection<>(
                firstCalendarDay,
                lastCalendarDay));

        return this;
    }

    public CalendarConfigBuilder setInvalidRange(Date firstDate, Date lastDate) {
        DaySelection<CalendarDay> calendarDayDaySelection = new DaySelection<>();

        mCalendar.setTime(firstDate);
        CalendarDay firstCalendarDay = new CalendarDay(mCalendar);

        mCalendar.setTime(lastDate);
        CalendarDay lastCalendarDay = new CalendarDay(mCalendar);

        if(mInvalidDays == null) {
            mInvalidDays = new DaySelection<>(
                    firstCalendarDay,
                    lastCalendarDay
            );
        }


        return this;
    }

    public CalendarConfig build() {
        mCalendarConfig.setYearStart(mYearStart);
        mCalendarConfig.setMostDaysNum(mMostDaysNum);
        mCalendarConfig.setLeastDaysNum(mLeastDaysNum);
        mCalendarConfig.setMonthCount(mMonthCount);
        mCalendarConfig.setMonthStart(mMonthStart);

        mCalendarConfig.setInvalidDays(mInvalidDays);
        mCalendarConfig.setBusyDays(mBusyDays);

        return mCalendarConfig;
    }

    public CalendarConfig buildFromJson() throws JSONException, IOException {
        InputStream calendarConfigStream = null;

        JSONObject calendarConfig = null;

        calendarConfigStream = mContext.getAssets().open(mCalendarConfigFileName);
        int size = calendarConfigStream.available();
        byte[] buffer = new byte[size];
        calendarConfigStream.read(buffer);
        calendarConfigStream.close();
        String json = new String(buffer, "UTF-8");
        calendarConfig = new JSONObject(json);
        parseConfigJson(calendarConfig);

        return mCalendarConfig;
    }


    //TODO: Add parsing of json config object
    private void parseConfigJson(JSONObject calendarConfig) throws JSONException {
        if (calendarConfig == null) {
            return;
        }
        JSONObject invalidDays = calendarConfig.getJSONObject("invalid_days");

        if (invalidDays == null) {
            return;
        }

        invalidDays.getJSONObject("range");

        String firstDateString = invalidDays.getString("first");
        String lastDateString = invalidDays.getString("last");

        Date firstDate = new Date();
    }
}
