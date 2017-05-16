package com.github.yurykorotin.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.yurykorotin.dayrangepicker.builders.CalendarConfigBuilder;
import com.github.yurykorotin.dayrangepicker.models.CalendarConfig;
import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.views.TabbedDayRangePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StartActivity extends AppCompatActivity {
    private final static String LOG_TAG = "DAY_RANGE_PICKER";

    TabbedDayRangePicker mDayRangePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mDayRangePicker = (TabbedDayRangePicker) findViewById(R.id.calendar);
        loadConfig();
    }

    private void loadConfig() {
        /*String calendarConfigFileName = "day_range_picker_config.json";

        try {
            CalendarConfigBuilder builder = new CalendarConfigBuilder();

            CalendarConfig calendarConfig = builder
                    .setCalendarConfigFileName(calendarConfigFileName)
                    .setContext(getApplicationContext())
                    .build();

            mDayRangePicker.setDataModel(calendarConfig,
                    new TabbedDayRangePicker.DayRangeController(mDayRangePicker));

        } catch (IOException e) {
            Log.e(LOG_TAG,"failed to load calendar config");
        } catch (JSONException e) {
            Log.e(LOG_TAG,"failed to parsejson");
        }*/

        String startDateString = "05-21-2012 20:00";
        String endDateString = "05-25-2012 20:00";

        CalendarConfigBuilder builder = new CalendarConfigBuilder();
        builder
                .setMonthCount(12)
                .setLeastDaysNum(2)
                .setYearStart(Calendar.getInstance().get(Calendar.YEAR))
                .setMostDaysNum(20)
                .addBusyRange(parseDate(startDateString), parseDate(endDateString));

        CalendarConfig config = builder.build();

        mDayRangePicker.setDataModel(config, new TabbedDayRangePicker.DayRangeController(mDayRangePicker));
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }
}
