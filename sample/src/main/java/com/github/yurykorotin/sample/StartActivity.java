package com.github.yurykorotin.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.yurykorotin.dayrangepicker.models.CalendarConfig;
import com.github.yurykorotin.dayrangepicker.views.TabbedDayRangePicker;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        CalendarConfig dataModel = new CalendarConfig();
        dataModel.leastDaysNum = 2;
        dataModel.mostDaysNum = 100;

        TabbedDayRangePicker dayRangePicker = (TabbedDayRangePicker) findViewById(R.id.calendar);

        dayRangePicker.setDataModel(dataModel, new TabbedDayRangePicker.DayRangeController(dayRangePicker));
    }
}
