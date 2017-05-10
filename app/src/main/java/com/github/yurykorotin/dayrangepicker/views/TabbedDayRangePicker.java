package com.github.yurykorotin.dayrangepicker.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.yurykorotin.dayrangepicker.R;
import com.github.yurykorotin.dayrangepicker.Utils;
import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.RangeModel;

import java.util.List;

/**
 * Created by yuri on 03.05.17.
 */

public class TabbedDayRangePicker extends LinearLayout implements OnDaySelectionListener{
    private DayRangeSelectionView mDayRangeSelectionView;
    private TabLayout mTabLayout;
    private TabLayout.Tab mFirstDayTab;
    private TabLayout.Tab mLastDayTab;
    private TypedArray mTabbedAttributesArray;
    private TypedArray mCalendarAttributesArray;
    private Context mContext;

    @Override
    public void onStartDaySelected(CalendarDay calendarDay) {
        if (mLastDayTab != null) {
            mLastDayTab.select();
        }
    }

    @Override
    public void onEndDaySelected(CalendarDay calendarDay) {
    }

    public static class DayRangeController extends DayRangePickerController {
        public DayRangeController() {

        }

        public DayRangeController(OnDaySelectionListener onDaySelectionListener) {
            super(onDaySelectionListener);
        }

        @Override
        protected void onDayOfMonthSelected(CalendarDay calendarDay) {
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

        init(context);
    }

    public void setDataModel(RangeModel dataModel, DayRangePickerController controller) {
        if (mDayRangeSelectionView != null) {
            mDayRangeSelectionView.setParameter(dataModel, controller);
        }
    }

    public TabbedDayRangePicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabbedDayRangePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTabbedAttributesArray = context.obtainStyledAttributes(attrs, R.styleable.TabbedDayRangePicker);
        mCalendarAttributesArray = context.obtainStyledAttributes(attrs, R.styleable.DayPickerView);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TabbedDayRangePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Context paramContext) {
        mContext = paramContext;

        inflate(mContext, R.layout.tabbed_picker, this);

        RangeModel dataModel = new RangeModel();
        dataModel.leastDaysNum = 2;
        dataModel.mostDaysNum = 100;

        setupTabs();
    }

    private void setupTabs() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mFirstDayTab = mTabLayout.newTab();
        mLastDayTab = mTabLayout.newTab();

        mTabLayout.addTab(mFirstDayTab);
        mTabLayout.addTab(mLastDayTab);

        mDayRangeSelectionView = (DayRangeSelectionView) findViewById(R.id.day_range_selection_view);
        mDayRangeSelectionView.setAttributes(mCalendarAttributesArray);

        if (mFirstDayTab == null || mLastDayTab == null) {
            return;
        }

        String firstTitle = mTabbedAttributesArray.getString(R.styleable.TabbedDayRangePicker_firstDayTitle);
        String lastTitle = mTabbedAttributesArray.getString(R.styleable.TabbedDayRangePicker_lastDayTitle);

        if (firstTitle == null) {
            firstTitle = getResources().getString(R.string.first_title);
        }

        if (lastTitle == null) {
            lastTitle = getResources().getString(R.string.last_title);
        }

        mFirstDayTab.setText(firstTitle);
        mLastDayTab.setText(lastTitle);

        mFirstDayTab.select();

        boolean isTabsEnabled = mTabbedAttributesArray.getBoolean(R.styleable.TabbedDayRangePicker_tabSwitchEnabled, false);

        Utils.enableTabs(mTabLayout, isTabsEnabled);
    }
}
