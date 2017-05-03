package com.github.yurykorotin.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.RangeModel;
import com.github.yurykorotin.dayrangepicker.views.DayRangePickerController;
import com.github.yurykorotin.dayrangepicker.views.DayRangePickerView;

import java.util.List;

public class StartActivity extends AppCompatActivity {

    class DayRangeController implements DayRangePickerController {
        @Override
        public void onDayOfMonthSelected(CalendarDay calendarDay) {
            Toast.makeText(StartActivity.this, "onDayOfMonthSelected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStartDaySelected(CalendarDay calendarDay) {
            Toast.makeText(StartActivity.this, "start", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEndDaySelected(CalendarDay calendarDay) {
            Toast.makeText(StartActivity.this, "end", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDateRangeSelected(List<CalendarDay> selectedDays) {
            Toast.makeText(StartActivity.this, "onDateRangeSelected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDaysSelected(List<CalendarDay> seleDaysList) {

        }

        @Override
        public void alertSelectedFail(DayRangePickerController.FailEven even) {
            Toast.makeText(StartActivity.this, "alertSelectedFail", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        RangeModel dataModel = new RangeModel();
        dataModel.yearStart = 2016;
        dataModel.monthStart = 6;
        dataModel.monthCount = 16;
        dataModel.leastDaysNum = 2;
        dataModel.mostDaysNum = 100;

        DayRangePickerView dayRangePicker = (DayRangePickerView) findViewById(R.id.calendar);

        dayRangePicker.setParameter(dataModel, new DayRangeController());


    }
}
