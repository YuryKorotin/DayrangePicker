package com.github.yurykorotin.dayrangepicker.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.yurykorotin.dayrangepicker.R;
import com.github.yurykorotin.dayrangepicker.Utils;
import com.github.yurykorotin.dayrangepicker.controllers.DayRangePickerController;
import com.github.yurykorotin.dayrangepicker.controllers.DayRangeTabController;
import com.github.yurykorotin.dayrangepicker.models.CalendarConfig;
import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.DaySelection;

import java.util.List;

/**
 * Created by yuri on 03.05.17.
 */

public class TabbedDayRangePicker extends LinearLayout{
    private DayRangeSelectionView mDayRangeSelectionView;
    private TextView mTitleTextView;
    private FrameLayout mHeaderDivider;
    private TabLayout mTabLayout;
    private TabLayout.Tab mFirstDayTab;
    private TabLayout.Tab mLastDayTab;

    private TypedArray mTabbedAttributesArray;
    private TypedArray mCalendarAttributesArray;
    private Context mContext;

    private boolean mIsTabEnabled = true;
    private DayRangeTabController mTabController;

    public void setTabEnabled(boolean tabEnabled) {
        mIsTabEnabled = tabEnabled;

        setupTitle();
    }

    public static class DayRangeController extends DayRangePickerController {
        public void setTabController(DayRangeTabController tabController) {
            mTabController = tabController;
        }

        private DayRangeTabController mTabController;
        private OnSelectionFailedListener mOnSelectionFailedListener;

        public DayRangeController() {

        }

        @Override
        public void onDayOfMonthSelected(CalendarDay calendarDay) {
        }

        @Override
        public void onDaysSelected(List<CalendarDay> selectedDaysList) {

        }

        @Override
        public void alertSelectedFail(DayRangePickerController.FailEven even) {
            if (mOnSelectionFailedListener == null) {
                return;
            }
            if (even.equals(FailEven.NO_REACH_LEAST_DAYS)) {
                mOnSelectionFailedListener.onMinimalPeriodExceed();
            }

            if (even.equals(FailEven.CONTAIN_NO_BUSY)) {
                mOnSelectionFailedListener.onBusyDaysIntersection();
            }
        }

        public void onStartDaySelected(CalendarDay calendarDay) {
            super.onStartDaySelected(calendarDay);
            if (mTabController != null) {
                mTabController.onLastTabSelected();
            }
        }

        public void onEndDaySelected(CalendarDay calendarDay) {
            super.onEndDaySelected(calendarDay);
        }

        public void onDateRangeSelected(DaySelection<CalendarDay> selectedDays) {
            super.onDateRangeSelected(selectedDays);
        }

        public void setOnSelectionFailedListener(OnSelectionFailedListener onSelectionFailedListener) {
            mOnSelectionFailedListener = onSelectionFailedListener;
        }
    }

    public TabbedDayRangePicker(Context context) {
        super(context);

        init(context);
    }

    public void setDataModel(CalendarConfig dataModel, DayRangeController controller) {
        if (mDayRangeSelectionView == null) {
            return;
        }
        mTabController = new DayRangeTabController(mFirstDayTab, mLastDayTab);

        controller.setTabController(mTabController);

        mDayRangeSelectionView.setParameter(dataModel, controller);
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

    public void setFirstDaySelection() {
        if (mDayRangeSelectionView != null) {
            mDayRangeSelectionView.setSelectionMode(DayRangeSelectionView.FIRST_DATE_SELECTION_MODE);
            mTabController.onFirstTabSelected();
        }
    }

    public void setLastDaySelection() {
        if (mDayRangeSelectionView != null) {
            mDayRangeSelectionView.setSelectionMode(DayRangeSelectionView.LAST_DATE_SELECTION_MODE);
            mTabController.onLastTabSelected();
        }
    }

    public void disableDaySelection() {
        if (mDayRangeSelectionView != null) {
            mDayRangeSelectionView.setSelectionMode(DayRangeSelectionView.DISABLED_SELECTION_MODE);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TabbedDayRangePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Context paramContext) {
        mContext = paramContext;

        inflate(mContext, R.layout.tabbed_picker, this);

        CalendarConfig dataModel = new CalendarConfig();
        dataModel.setLeastDaysNum(2);
        dataModel.setMostDaysNum(100);

        setupTabs();

        mTitleTextView = (TextView) findViewById(R.id.title_textview);
        mHeaderDivider = (FrameLayout) findViewById(R.id.header_divider);

        boolean isSelectable = mCalendarAttributesArray.getBoolean(
                R.styleable.DayPickerView_isSelectable,
                true);

        if (!isSelectable) {
            disableDaySelection();
        }

        mIsTabEnabled = mTabbedAttributesArray.getBoolean(
                R.styleable.TabbedDayRangePicker_isTabEnabled,
                true);

        setupTitle();
    }

    private void setupTitle() {
        if (mIsTabEnabled) {
            mTabLayout.setVisibility(VISIBLE);
            mTitleTextView.setVisibility(INVISIBLE);
            mHeaderDivider.setVisibility(GONE);
        } else {
            mTabLayout.setVisibility(INVISIBLE);
            mTitleTextView.setVisibility(VISIBLE);
            mHeaderDivider.setVisibility(VISIBLE);
        }

        String title = mTabbedAttributesArray.getString(R.styleable.TabbedDayRangePicker_title);

        mTitleTextView.setText(title);
    }

    private void setupTabs() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        int tabStripColor = mTabbedAttributesArray.getColor(
                R.styleable.TabbedDayRangePicker_tabStripColor, Color.BLACK);
        mTabLayout.setSelectedTabIndicatorColor(tabStripColor);

        mFirstDayTab = mTabLayout.newTab();
        mLastDayTab = mTabLayout.newTab();

        mTabLayout.addTab(mFirstDayTab);
        mTabLayout.addTab(mLastDayTab);

        String firstTitle = mTabbedAttributesArray.getString(R.styleable.TabbedDayRangePicker_firstDayTitle);
        String lastTitle = mTabbedAttributesArray.getString(R.styleable.TabbedDayRangePicker_lastDayTitle);

        if (firstTitle == null || lastTitle == null) {
            mTabLayout.setVisibility(GONE);
            return;
        }

        mDayRangeSelectionView = (DayRangeSelectionView) findViewById(R.id.day_range_selection_view);
        mDayRangeSelectionView.setAttributes(mCalendarAttributesArray);

        if (mFirstDayTab == null || mLastDayTab == null) {
            return;
        }

        mFirstDayTab.setText(firstTitle);
        mLastDayTab.setText(lastTitle);

        mFirstDayTab.select();

        boolean isTabsEnabled = mTabbedAttributesArray.getBoolean(R.styleable.TabbedDayRangePicker_tabSwitchEnabled, false);
        Utils.enableTabs(mTabLayout, isTabsEnabled);

        if (isTabsEnabled) {
            mTabLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedTab = mTabLayout.getSelectedTabPosition();
                    if (selectedTab == mFirstDayTab.getPosition()) {
                        mDayRangeSelectionView.setSelectionMode(DayRangeSelectionView.FIRST_DATE_SELECTION_MODE);
                    } else if (selectedTab == mLastDayTab.getPosition()) {
                        mDayRangeSelectionView.setSelectionMode(DayRangeSelectionView.LAST_DATE_SELECTION_MODE);
                    }
                }
            });
        }
    }
}
