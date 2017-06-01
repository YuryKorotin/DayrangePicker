package com.github.yurykorotin.dayrangepicker.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.github.yurykorotin.dayrangepicker.controllers.DayRangePickerController;
import com.github.yurykorotin.dayrangepicker.models.CalendarData;
import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.DaySelection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Adapter for list of months
 * Created by yuri on 27.04.17.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> implements MonthView.OnDayClickListener {
    protected static final int MONTHS_IN_YEAR = 12;

    private final TypedArray mTypedArray;
    private final Context mContext;
    private final DayRangePickerController mController;
    private final Calendar mCalendar;

    private CalendarDay mNearestDay;
    private CalendarData mDataModel;

    public CalendarAdapter(Context context,
                           TypedArray typedArray,
                           DayRangePickerController datePickerController,
                           CalendarData dataModel) {
        mContext = context;
        mCalendar = Calendar.getInstance();
        mTypedArray = typedArray;
        mController = datePickerController;
        mDataModel = dataModel;

//        if (mTypedArray.getBoolean(R.styleable.DayPickerView_currentDaySelected, false))
//            onDayTapped(new CalendarDay(System.currentTimeMillis()));
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final MonthView simpleMonthView = new MonthView(mContext, mTypedArray, mDataModel);
        return new ViewHolder(simpleMonthView, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final MonthView v = viewHolder.monthView;
        final HashMap<String, Object> drawingParams = new HashMap<String, Object>();
        int month;
        int year;

        int monthStart = mDataModel.getMonthStart();
        int yearStart = mDataModel.getYearStart();

        month = (monthStart + (position % MONTHS_IN_YEAR)) % MONTHS_IN_YEAR;
        year = position / MONTHS_IN_YEAR + yearStart + ((monthStart + (position % MONTHS_IN_YEAR)) / MONTHS_IN_YEAR);

//        v.reuse();

        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_BEGIN_DATE, mDataModel.getRangeDays().getFirst());
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_LAST_DATE, mDataModel.getRangeDays().getLast());
        drawingParams.put(MonthView.VIEW_PARAMS_NEAREST_DATE, mNearestDay);
        drawingParams.put(MonthView.VIEW_PARAMS_YEAR, year);
        drawingParams.put(MonthView.VIEW_PARAMS_MONTH, month);
        drawingParams.put(MonthView.VIEW_PARAMS_WEEK_START, mCalendar.getFirstDayOfWeek());
        v.setMonthParams(drawingParams);
        v.invalidate();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataModel.getMonthCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final MonthView monthView;

        public ViewHolder(View itemView, MonthView.OnDayClickListener onDayClickListener) {
            super(itemView);
            monthView = (MonthView) itemView;
            monthView.setLayoutParams(new AbsListView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
            monthView.setClickable(true);
            monthView.setOnDayClickListener(onDayClickListener);
        }
    }

    @Override
    public void onDayClick(MonthView monthView, CalendarDay calendarDay) {
        if (calendarDay != null) {
            onDayTapped(calendarDay);
        }
    }

    /**
     * On day tap callback method
     * @param calendarDay
     */
    protected void onDayTapped(CalendarDay calendarDay) {
        if (mDataModel != null && mDataModel.getSelectionMode() == CalendarData.DISABLED_SELECTION_MODE) {
            return;
        }
        if(mController != null) {
            mController.onDayOfMonthSelected(calendarDay);
        }
        setRangeSelectedDay(calendarDay);
    }

    /**
     *
     * @param calendarDay
     */
    public void setRangeSelectedDay(CalendarDay calendarDay) {
        DaySelection<CalendarDay> rangeDays = mDataModel.getRangeDays();

        CalendarDay firstRangeDay = rangeDays.getFirst();
        CalendarDay lastRangeDay = rangeDays.getLast();

        boolean isFirstDaySelected = firstRangeDay != null && lastRangeDay == null;


        if (isFirstDaySelected ||
                mDataModel.getSelectionMode() == CalendarData.LAST_DATE_SELECTION_MODE) {
            mNearestDay = getNearestDay(firstRangeDay);

            if (!isRangesCorrect(firstRangeDay, calendarDay)) {
                return;
            }

            mDataModel.getRangeDays().setLast(calendarDay);

            if(mController != null) {
                mController.onDateRangeSelected(rangeDays);
                mController.onEndDaySelected(calendarDay);
            }
        } else if (rangeDays.getLast() != null &&
                mDataModel.getSelectionMode() == CalendarData.FIRST_DATE_SELECTION_MODE) {
            if (!isRangesCorrect(calendarDay, rangeDays.getLast())) {
                return;
            }

            mDataModel.getRangeDays().setFirst(calendarDay);
            mNearestDay = getNearestDay(calendarDay);
            if(mController != null) {
                mController.onDateRangeSelected(rangeDays);
            }

        } else {
            mDataModel.getRangeDays().setFirst(calendarDay);
            mController.onStartDaySelected(calendarDay);
            mNearestDay = getNearestDay(calendarDay);
        }

        notifyDataSetChanged();
    }

    private boolean isRangesCorrect(CalendarDay firstRangeDay, CalendarDay calendarDay) {
        if (isContainSpecialDays(firstRangeDay, calendarDay, mDataModel.getBusyDayCollection())) {
            if(mController != null) {
                mController.alertSelectedFail(DayRangePickerController.FailEven.CONTAIN_NO_BUSY);
            }
            return false;
        }
        if (isContainSpecialDays(firstRangeDay, calendarDay, mDataModel.getInvalidDayCollection())) {
            if(mController != null) {
                mController.alertSelectedFail(DayRangePickerController.FailEven.CONTAIN_INVALID);
            }
            return false;
        }
        if (calendarDay.getDate().before(firstRangeDay.getDate()) ||
                calendarDay.getDate().equals(firstRangeDay.getDate())) {
            if(mController != null) {
                mController.alertSelectedFail(DayRangePickerController.FailEven.END_MT_START);
            }
            return false;
        }

        int dayDiff = dateDiff(firstRangeDay, calendarDay);
        if (dayDiff > 1 && mDataModel.getLeastDaysNum() > dayDiff) {
            if(mController != null) {
                mController.alertSelectedFail(DayRangePickerController.FailEven.NO_REACH_LEAST_DAYS);
            }
            return false;
        }
        if (dayDiff > 1 && mDataModel.getMostDaysNum() < dayDiff) {
            if(mController != null) {
                mController.alertSelectedFail(DayRangePickerController.FailEven.NO_REACH_MOST_DAYS);
            }
            return false;
        }
        return true;
    }

    public void setSelectionMode(@CalendarData.SelectionMode int selectionMode) {
        if(mDataModel == null) {
            return;
        }

        mDataModel.setSelectionMode(selectionMode);
    }


    /**
     *
     * @param calendarDay
     * @return
     */
    protected CalendarDay getNearestDay(CalendarDay calendarDay) {
        List<CalendarDay> list = new ArrayList<>();
        list.addAll(mDataModel.getBusyDayCollection());
        list.addAll(mDataModel.getInvalidDayCollection());
        Collections.sort(list);
        for (CalendarDay day : list) {
            if (calendarDay.compareTo(day) < 0) {
                return day;
            }
        }
        return null;
    }

    public DaySelection<CalendarDay> getRangeDays() {
        return mDataModel.getRangeDays();
    }

    /**
     *
     * @param first
     * @param last
     * @param specialDays
     * @return
     */
    protected boolean isContainSpecialDays(CalendarDay first,
                                           CalendarDay last,
                                           List<CalendarDay> specialDays) {
        Date firstDay = first.getDate();
        Date lastDay = last.getDate();
        for (CalendarDay day : specialDays) {
            if (day.getDate().after(firstDay) && day.getDate().before(lastDay)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param first
     * @param last
     * @return
     */
    protected int dateDiff(CalendarDay first, CalendarDay last) {
        long dayDiff = (last.getDate().getTime() - first.getDate().getTime()) / (1000 * 3600 * 24);
        return Integer.valueOf(String.valueOf(dayDiff)) + 1;
    }
    /**
     *
     * @param dataModel
     */
    protected void setDataModel(CalendarData dataModel) {
        this.mDataModel = dataModel;
    }
}

