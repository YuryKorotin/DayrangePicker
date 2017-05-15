package com.github.yurykorotin.dayrangepicker.builders;

import android.content.Context;

import com.github.yurykorotin.dayrangepicker.models.CalendarConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuri on 15.05.17.
 */

public class CalendarConfigBuilder {
    private String mCalendarConfigFileName;
    private CalendarConfig mCalendarConfig;
    private Context mContext;

    public CalendarConfigBuilder() {
        mCalendarConfig = new CalendarConfig();
    }

    public CalendarConfigBuilder setCalendarConfigFileName(String calendarConfigFileName) {
        mCalendarConfigFileName = calendarConfigFileName;
        return this;
    }

    public CalendarConfigBuilder setContext(Context context) {
        mContext = context;
        return this;
    }

    public CalendarConfig build() throws JSONException, IOException{
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

    private void parseConfigJson(JSONObject calendarConfig) throws JSONException {
        if (calendarConfig == null) {
            return;
        }
        JSONObject invalidDays = calendarConfig.getJSONObject("invalid_days");

        if (invalidDays == null) {
            return;
        }

        invalidDays.getJSONObject("range");
    }
}
