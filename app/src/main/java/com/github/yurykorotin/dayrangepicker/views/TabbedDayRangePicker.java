package com.github.yurykorotin.dayrangepicker.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.yurykorotin.dayrangepicker.R;
import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.RangeModel;

import java.util.List;

/**
 * Created by yuri on 03.05.17.
 */

public class TabbedDayRangePicker extends LinearLayout{
    private DayRangeSelectionView mDayRangeSelectionView;
    private TabLayout mTabLayout;

    class DayRangeController extends DayRangePickerController {
        public DayRangeController() {

        }

        public DayRangeController(OnDaySelectionListener onDaySelectionListener) {
            super(onDaySelectionListener);
        }

        @Override
        protected void onDayOfMonthSelected(CalendarDay calendarDay) {
        }

        @Override
        protected void onStartDaySelected(CalendarDay calendarDay) {
        }

        @Override
        protected void onEndDaySelected(CalendarDay calendarDay) {
        }

        @Override
        protected void onDateRangeSelected(List<CalendarDay> selectedDays) {
        }

        @Override
        protected void onDaysSelected(List<CalendarDay> seleDaysList) {

        }

        @Override
        protected void alertSelectedFail(DayRangePickerController.FailEven even) {
        }
    }


    public TabbedDayRangePicker(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tabbed_picker, this, true);

        RangeModel dataModel = new RangeModel();
        dataModel.yearStart = 2016;
        dataModel.monthStart = 6;
        dataModel.monthCount = 16;
        dataModel.leastDaysNum = 2;
        dataModel.mostDaysNum = 100;

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mDayRangeSelectionView = (DayRangeSelectionView) findViewById(R.id.calendar);
        mDayRangeSelectionView.setParameter(dataModel, new DayRangeController());
    }

    public TabbedDayRangePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabbedDayRangePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TabbedDayRangePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
