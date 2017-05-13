package com.github.yurykorotin.dayrangepicker.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.github.yurykorotin.dayrangepicker.models.CalendarData;
import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.DaySelection;
import com.github.yurykorotin.dayrangepicker.models.CalendarConfig;

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

    private final TypedArray typedArray;
    private final Context mContext;
    private final DayRangePickerController mController;

    private CalendarDay mNearestDay;
    private CalendarData dataModel;

    public CalendarAdapter(Context context,
                              TypedArray typedArray,
                              DayRangePickerController datePickerController,
                              CalendarConfig dataModel) {
        mContext = context;
        this.typedArray = typedArray;
        mController = datePickerController;
        this.dataModel = dataModel;

//        if (typedArray.getBoolean(R.styleable.DayPickerView_currentDaySelected, false))
//            onDayTapped(new CalendarDay(System.currentTimeMillis()));
        initData();
    }

    private void initData() {
        if (dataModel.getInvalidDays() == null) {
            dataModel.setInvalidDays(new DaySelection<CalendarDay>());
        }

        if (dataModel.busyDays == null) {
            dataModel.busyDays = new ArrayList<>();
        }

        if (dataModel.tags == null) {
            dataModel.tags = new ArrayList<>();
        }

        if (dataModel.selectedDays == null) {
            dataModel.selectedDays = new DaySelection<>();
        }

        if (dataModel.yearStart <= 0) {
            dataModel.yearStart = calendar.get(Calendar.YEAR);
        }
        if (dataModel.monthStart <= 0) {
            dataModel.monthStart = calendar.get(Calendar.MONTH);
        }

        if (dataModel.getLeastDaysNum() <= 0) {
            dataModel.leastDaysNum = 0;
        }

        if (dataModel.mostDaysNum <= 0) {
            dataModel.mostDaysNum = 100;
        }

        if (dataModel.leastDaysNum > dataModel.mostDaysNum) {
            //Log.e("error", "可选择的最小天数不能小于最大天数");
            //throw new IllegalArgumentException("可选择的最小天数不能小于最大天数");
        }

        if(dataModel.monthCount <= 0) {
            dataModel.monthCount = 12;
        }

        if(dataModel.defTag == null) {
            dataModel.defTag = "label";
        }

        mLeastDaysNum = dataModel.leastDaysNum;
        mMostDaysNum = dataModel.mostDaysNum;

        mBusyDays = dataModel.busyDays;
        mInvalidDays = dataModel.invalidDays;
        rangeDays = dataModel.selectedDays;
        mTags = dataModel.tags;
        mDefTag = dataModel.defTag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final MonthView simpleMonthView = new MonthView(mContext, typedArray, dataModel);
        return new ViewHolder(simpleMonthView, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final MonthView v = viewHolder.monthView;
        final HashMap<String, Object> drawingParams = new HashMap<String, Object>();
        int month;
        int year;

        int monthStart = dataModel.getMonthStart();
        int yearStart = dataModel.getYearStart();

        month = (monthStart + (position % MONTHS_IN_YEAR)) % MONTHS_IN_YEAR;
        year = position / MONTHS_IN_YEAR + yearStart + ((monthStart + (position % MONTHS_IN_YEAR)) / MONTHS_IN_YEAR);

//        v.reuse();

        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_BEGIN_DATE, rangeDays.getFirst());
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_LAST_DATE, rangeDays.getLast());
        drawingParams.put(MonthView.VIEW_PARAMS_NEAREST_DATE, mNearestDay);
        drawingParams.put(MonthView.VIEW_PARAMS_YEAR, year);
        drawingParams.put(MonthView.VIEW_PARAMS_MONTH, month);
        drawingParams.put(MonthView.VIEW_PARAMS_WEEK_START, calendar.getFirstDayOfWeek());
        v.setMonthParams(drawingParams);
        v.invalidate();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataModel.getMonthCount();
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
        if (rangeDays.getFirst() != null && rangeDays.getLast() == null) {
            mNearestDay = getNearestDay(rangeDays.getFirst());
            if (isContainSpecialDays(rangeDays.getFirst(), calendarDay, mBusyDays)) {
                if(mController != null) {
                    mController.alertSelectedFail(DayRangePickerController.FailEven.CONTAIN_NO_SELECTED);
                }
                return;
            }
            if (isContainSpecialDays(rangeDays.getFirst(), calendarDay, mInvalidDays)) {
                if(mController != null) {
                    mController.alertSelectedFail(DayRangePickerController.FailEven.CONTAIN_INVALID);
                }
                return;
            }
            if (calendarDay.getDate().before(rangeDays.getFirst().getDate())) {
                if(mController != null) {
                    mController.alertSelectedFail(DayRangePickerController.FailEven.END_MT_START);
                }
                return;
            }

            int dayDiff = dateDiff(rangeDays.getFirst(), calendarDay);
            if (dayDiff > 1 && mLeastDaysNum > dayDiff) {
                if(mController != null) {
                    mController.alertSelectedFail(DayRangePickerController.FailEven.NO_REACH_LEAST_DAYS);
                }
                return;
            }
            if (dayDiff > 1 && mMostDaysNum < dayDiff) {
                if(mController != null) {
                    mController.alertSelectedFail(DayRangePickerController.FailEven.NO_REACH_MOST_DAYS);
                }
                return;
            }

            rangeDays.setLast(calendarDay);

            if(mController != null) {
                mController.onDateRangeSelected(addSelectedDays());
                mController.onEndDaySelected(calendarDay);
            }
        } else if (rangeDays.getLast() != null) {
            rangeDays.setFirst(calendarDay);
            rangeDays.setLast(null);
            mNearestDay = getNearestDay(calendarDay);
            mController.onStartDaySelected(calendarDay);
        } else {
            rangeDays.setFirst(calendarDay);
            mController.onStartDaySelected(calendarDay);
            mNearestDay = getNearestDay(calendarDay);
        }

        notifyDataSetChanged();
    }

    /**
     *
     * @param calendarDay
     * @return
     */
    protected CalendarDay getNearestDay(CalendarDay calendarDay) {
        List<CalendarDay> list = new ArrayList<>();
        list.addAll(mBusyDays);
        list.addAll(mInvalidDays);
        Collections.sort(list);
        for (CalendarDay day : list) {
            if (calendarDay.compareTo(day) < 0) {
                return day;
            }
        }
        return null;
    }

    public DaySelection<CalendarDay> getRangeDays() {
        return rangeDays;
    }

    /**
     *
     * @param first
     * @param last
     * @param specialDays
     * @return
     */
    protected boolean isContainSpecialDays(CalendarDay first, CalendarDay last, List<CalendarDay> specialDays) {
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
     * @return
     */
    protected List<CalendarDay> addSelectedDays() {
        List<CalendarDay> rangeDays = new ArrayList<>();
        CalendarDay firstDay = this.rangeDays.getFirst();
        CalendarDay lastDay = this.rangeDays.getLast();
        rangeDays.add(firstDay);
        int diffDays = dateDiff(firstDay, lastDay);
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(firstDay.getDate());
        for (int i = 1; i < diffDays; i++) {
            tempCalendar.set(Calendar.DATE, tempCalendar.get(Calendar.DATE) + 1);
            CalendarDay calendarDay = new CalendarDay(tempCalendar);
            boolean isTag = false;
            for (CalendarDay calendarTag : mTags) {
                if (calendarDay.compareTo(calendarTag) == 0) {
                    isTag = true;
                    rangeDays.add(calendarTag);
                    break;
                }
            }
            if (!isTag) {
                calendarDay.setTag(mDefTag);
                rangeDays.add(calendarDay);
            }
        }
        return rangeDays;
    }

    /**
     *
     * @param dataModel
     */
    protected void setDataModel(CalendarConfig dataModel) {
        this.dataModel = dataModel;
    }
}

