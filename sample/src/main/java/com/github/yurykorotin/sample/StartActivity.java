package com.github.yurykorotin.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.yurykorotin.dayrangepicker.builders.CalendarConfigBuilder;
import com.github.yurykorotin.dayrangepicker.models.CalendarConfig;
import com.github.yurykorotin.dayrangepicker.views.TabbedDayRangePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

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
        String calendarConfigFileName = "day_range_picker_config.json";

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
        }
    }
}
