package com.github.yurykorotin.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.RangeModel;
import com.github.yurykorotin.dayrangepicker.views.DayRangePickerController;
import com.github.yurykorotin.dayrangepicker.views.DayRangeSelectionView;
import com.github.yurykorotin.dayrangepicker.views.OnDaySelectionListener;
import com.github.yurykorotin.dayrangepicker.views.TabbedDayRangePicker;

import java.util.List;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        RangeModel dataModel = new RangeModel();
        dataModel.leastDaysNum = 2;
        dataModel.mostDaysNum = 100;

        TabbedDayRangePicker dayRangePicker = (TabbedDayRangePicker) findViewById(R.id.calendar);

        dayRangePicker.setDataModel(dataModel, new TabbedDayRangePicker.DayRangeController(dayRangePicker));
    }
}
