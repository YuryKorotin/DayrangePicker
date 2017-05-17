package com.github.yurykorotin.dayrangepicker.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;

import com.github.yurykorotin.dayrangepicker.R;
import com.github.yurykorotin.dayrangepicker.Utils;
import com.github.yurykorotin.dayrangepicker.models.CalendarData;
import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.DaySelection;

import java.security.InvalidParameterException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by yuri on 27.04.17.
 * Class for month item in recyclerview of calendar
 */

public class MonthView extends View {
    public static final String VIEW_PARAMS_SELECTED_BEGIN_DATE = "selected_begin_date";
    public static final String VIEW_PARAMS_SELECTED_LAST_DATE = "selected_last_date";
    public static final String VIEW_PARAMS_NEAREST_DATE = "mNearestDay";

    public static final String VIEW_PARAMS_HEIGHT = "height";
    public static final String VIEW_PARAMS_MONTH = "month";
    public static final String VIEW_PARAMS_YEAR = "year";
    public static final String VIEW_PARAMS_WEEK_START = "week_start";

    private static final int SELECTED_CIRCLE_ALPHA = 128;
    protected static int DEFAULT_HEIGHT = 32;
    protected static final int DEFAULT_NUM_ROWS = 6;
    protected static int DAY_SELECTED_RECT_HALF_WIDTH;
    protected static int DAY_SELECTED_RECT_HALF_HEIGHT;
    protected static int ROW_SEPARATOR = 12;
    protected static int MINI_DAY_NUMBER_TEXT_SIZE;
    private static int TAG_TEXT_SIZE;
    protected static int MIN_HEIGHT = 10;
    protected static int MONTH_HEADER_SIZE;
    protected static int YEAR_MONTH_TEXT_SIZE;
    protected static int WEEK_TEXT_SIZE;

    private final CalendarData mDataModel;
    private CalendarDay mNearestDay;

    protected int mPadding = 0;
    protected int mTopDayPadding = 0;

    private String mDayOfWeekTypeface;
    private String mMonthTitleTypeface;

    protected Paint mWeekTextPaint;
    protected Paint mDayTextPaint;
    protected Paint mTodayFramePaint;

    protected Paint mTagTextPaint;
    protected Paint mTitleBGPaint;
    protected Paint mYearMonthPaint;
    protected Paint mSelectedDayBgPaint;
    protected Paint mBusyDayBgPaint;
    protected Paint mFirstLastSelectedDayBgPaint;
    protected Paint mInValidDayBgPaint;
    protected Paint mSelectedDayTextPaint;

    ///Colors for calendar
    protected int mCurrentDayTextColor;
    protected int mYearMonthTextColor;
    protected int mWeekTextColor;
    protected int mDayTextColor;
    protected int mSelectedDayTextColor;
    protected int mPreviousDayTextColor;
    protected int mSelectedDaysBgColor;
    protected int mBusyDaysBgColor;
    protected int mInValidDaysBgColor;
    protected int mBusyDaysTextColor;
    protected int mInValidDaysTextColor;
    protected int mTodayBorderColor;
    protected int mFirstLastSelectedDayColor;

    private final StringBuilder mStringBuilder;

    protected boolean mHasToday = true;
    protected int mToday = -1;
    protected int mWeekStart = 1;
    protected int mNumDays = 7;
    protected int mNumCells;
    private int mDayOfWeekStart = 0;
    protected Boolean mDrawRect;
    protected int mRowHeight = DEFAULT_HEIGHT;
    protected int mWidth;

    protected int mYear;
    protected int mMonth;
    final Date today;

    private final Calendar mCalendar;
    private final Calendar mDayLabelCalendar;
    private final Boolean isPrevDayEnabled;

    private int mNumRows;

    private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();

    private OnDayClickListener mOnDayClickListener;

    CalendarDay mStartDate;
    CalendarDay mEndDate;

    CalendarDay cellCalendar;

/*
    public MonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    public MonthView(Context context, TypedArray typedArray, CalendarData dataModel) {
        super(context);

        Resources resources = context.getResources();
        mDayLabelCalendar = Calendar.getInstance();
        mCalendar = Calendar.getInstance();
        today = Calendar.getInstance().getTime();
        mDayOfWeekTypeface = resources.getString(R.string.sans_serif);
        mMonthTitleTypeface = resources.getString(R.string.sans_serif);
        mCurrentDayTextColor = typedArray.getColor(
                R.styleable.DayPickerView_currentDayTextColor,
                ContextCompat.getColor(context, R.color.normal_day));
        mYearMonthTextColor = typedArray.getColor(
                R.styleable.DayPickerView_yearMonthTextColor,
                ContextCompat.getColor(context, R.color.normal_day));
        mWeekTextColor = typedArray.getColor(
                R.styleable.DayPickerView_weekTextColor,
                ContextCompat.getColor(context, R.color.normal_day));
//        mDayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorDayName, resources.getColor(R.color.normal_day));
        mDayTextColor = typedArray.getColor(
                R.styleable.DayPickerView_normalDayTextColor,
                ContextCompat.getColor(context, R.color.normal_day));
        mPreviousDayTextColor = typedArray.getColor(
                R.styleable.DayPickerView_previousDayTextColor,
                ContextCompat.getColor(context, R.color.normal_day));
        mSelectedDaysBgColor = typedArray.getColor(
                R.styleable.DayPickerView_selectedDayBackgroundColor,
                ContextCompat.getColor(context, R.color.selected_day_background));
        mSelectedDayTextColor = typedArray.getColor(
                R.styleable.DayPickerView_selectedDayTextColor,
                ContextCompat.getColor(context, R.color.selected_day_text));
        mBusyDaysBgColor = typedArray.getColor(
                R.styleable.DayPickerView_busyDaysBackgroundColor,
                Color.RED);
        mInValidDaysBgColor = typedArray.getColor(
                R.styleable.DayPickerView_inValidDaysBackgroundColor,
                Color.GRAY);
        mBusyDaysTextColor = typedArray.getColor(
                R.styleable.DayPickerView_busyDaysTextColor,
                ContextCompat.getColor(context, R.color.selected_day_text));
        mInValidDaysTextColor = typedArray.getColor(
                R.styleable.DayPickerView_inValidDaysTextColor,
                ContextCompat.getColor(context, R.color.normal_day));

        mFirstLastSelectedDayColor = typedArray.getColor(
                R.styleable.DayPickerView_selectedFirstLastDayColor,
                ContextCompat.getColor(context, R.color.selected_day_background));
//        mDrawRect = typedArray.getBoolean(R.styleable.DayPickerView_drawRoundRect, true);

        mTodayBorderColor = typedArray.getColor(
                R.styleable.DayPickerView_currentDayFrameColor,
                Color.RED);

        mStringBuilder = new StringBuilder(50);

        MINI_DAY_NUMBER_TEXT_SIZE = typedArray.getDimensionPixelSize(
                R.styleable.DayPickerView_dayTextSize,
                resources.getDimensionPixelSize(R.dimen.text_size_day));
        TAG_TEXT_SIZE = typedArray.getDimensionPixelSize(
                R.styleable.DayPickerView_tagTextSize,
                resources.getDimensionPixelSize(R.dimen.text_size_tag));
        YEAR_MONTH_TEXT_SIZE = typedArray.getDimensionPixelSize(
                R.styleable.DayPickerView_yearMonthTextSize,
                resources.getDimensionPixelSize(R.dimen.text_size_month));
        WEEK_TEXT_SIZE = typedArray.getDimensionPixelSize(
                R.styleable.DayPickerView_weekTextSize,
                resources.getDimensionPixelSize(R.dimen.text_size_day_name));
        MONTH_HEADER_SIZE = typedArray.getDimensionPixelOffset(
                R.styleable.DayPickerView_headerMonthHeight,
                resources.getDimensionPixelOffset(R.dimen.header_month_height));
        DAY_SELECTED_RECT_HALF_WIDTH = typedArray.getDimensionPixelSize(
                R.styleable.DayPickerView_selectedDaySize,
                resources.getDimensionPixelOffset(R.dimen.day_x_radius));

        DAY_SELECTED_RECT_HALF_HEIGHT = typedArray.getDimensionPixelSize(
                R.styleable.DayPickerView_selectedDaySize,
                resources.getDimensionPixelOffset(R.dimen.day_y_radius));


        mRowHeight = ((typedArray.getDimensionPixelSize(
                R.styleable.DayPickerView_calendarViewHeight,
                resources.getDimensionPixelOffset(R.dimen.calendar_height)) - MONTH_HEADER_SIZE - ROW_SEPARATOR) / 6);

        isPrevDayEnabled = typedArray.getBoolean(R.styleable.DayPickerView_enablePreviousDay, false);
        cellCalendar = new CalendarDay();

        mTopDayPadding = MINI_DAY_NUMBER_TEXT_SIZE / 2;

        mDataModel = dataModel;

        initView();
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumCells) / mNumDays;
        int remainder = (offset + mNumCells) % mNumDays;
        return (dividend + (remainder > 0 ? 1 : 0));
    }


    private void drawMonthDayLabels(Canvas canvas) {
        int y = MONTH_HEADER_SIZE - (WEEK_TEXT_SIZE / 2);
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);

        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            int x = (2 * i + 1) * dayWidthHalf + mPadding;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            canvas.drawText(mDateFormatSymbols.getShortWeekdays()[mDayLabelCalendar.get(Calendar.DAY_OF_WEEK)].toUpperCase(Locale.getDefault()),
                    x, y, mWeekTextPaint);
        }
    }

    private void drawMonthTitle(Canvas canvas) {
        int x = (mWidth + 2 * mPadding) / 2;
        int y = (MONTH_HEADER_SIZE - WEEK_TEXT_SIZE) / 2 + (YEAR_MONTH_TEXT_SIZE / 3);
        StringBuilder stringBuilder = new StringBuilder(getMonthAndYearString().toLowerCase());
        //stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
        canvas.drawText(stringBuilder.toString(), x, y, mYearMonthPaint);
    }

    private int findDayOffset() {
        return (mDayOfWeekStart < mWeekStart ? (mDayOfWeekStart + mNumDays) : mDayOfWeekStart)
                - mWeekStart;
    }

    private String getMonthAndYearString() {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NO_MONTH_DAY;
        mStringBuilder.setLength(0);
        long millis = mCalendar.getTimeInMillis();
        return DateUtils.formatDateRange(getContext(), millis, millis, flags);
    }

    private void onDayClick(CalendarDay calendarDay) {
        if (mOnDayClickListener != null && (isPrevDayEnabled || !prevDay(calendarDay.getDay(), today))) {
            mOnDayClickListener.onDayClick(this, calendarDay);
        }
    }

    private boolean sameDay(int monthDay, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return (mYear == year) && (mMonth == month) && (monthDay == day);
    }

    private boolean prevDay(int monthDay, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return ((mYear < year)) || (mYear == year && mMonth < month) ||
                (mYear == year && mMonth == month && monthDay < day);
    }

    protected void drawMonthCell(Canvas canvas) {
        //int y = MONTH_HEADER_SIZE + ROW_SEPARATOR + mRowHeight / 2;
        int y = MONTH_HEADER_SIZE + ROW_SEPARATOR + mRowHeight / 2;
        float paddingDay = (mWidth - 2 * mPadding) / (2 * mNumDays);
        int dayOffset = findDayOffset();
        int day = 1;

        while (day <= mNumCells) {
            int x = (int) (paddingDay * (1 + dayOffset * 2) + mPadding);

            mDayTextPaint.setColor(mDayTextColor);
            mDayTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            mTagTextPaint.setColor(mDayTextColor);

            cellCalendar.setDay(mYear, mMonth, day);

            boolean isToday = false;

            drawToday(isToday, day, canvas, x, y);

            boolean isPrevDay = false;
            if (!isPrevDayEnabled && prevDay(day, today)) {
                isPrevDay = true;
                mDayTextPaint.setColor(mPreviousDayTextColor);
                mDayTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                canvas.drawText(String.format("%d", day), x, y, mDayTextPaint);
            }

            boolean isBeginDay = false;
            if (mStartDate != null && cellCalendar.equals(mStartDate) && !mStartDate.equals(mEndDate)) {
                isBeginDay = true;
                //drawDayBg(canvas, x, y, mSelectedDayBgPaint);
                drawDayBg(canvas, x, y, mFirstLastSelectedDayBgPaint);

                mDayTextPaint.setColor(mSelectedDayTextColor);
                //canvas.drawText("start day", x, getTextYCenter(mDayTextPaint, y + DAY_SELECTED_RECT_HALF_WIDTH / 2), mDayTextPaint);
                if (isToday) {
                    //canvas.drawText(getResources().getString(R.string.today), x, getTextYCenter(mDayTextPaint, y - DAY_SELECTED_RECT_HALF_WIDTH / 2), mDayTextPaint);
                }
            }

            boolean isLastDay = false;
            if (mEndDate != null && cellCalendar.equals(mEndDate) && !mStartDate.equals(mEndDate)) {
                isLastDay = true;
                //drawDayBg(canvas, x, y, mSelectedDayBgPaint);
                drawDayBg(canvas, x, y, mFirstLastSelectedDayBgPaint);

                mDayTextPaint.setColor(mSelectedDayTextColor);
                //canvas.drawText("end day", x, getTextYCenter(mDayTextPaint, y + DAY_SELECTED_RECT_HALF_WIDTH / 2), mDayTextPaint);
            }

            if (cellCalendar.after(mStartDate) && cellCalendar.before(mEndDate)) {
                mDayTextPaint.setColor(mSelectedDayTextColor);
                drawDayBg(canvas, x, y, mSelectedDayBgPaint);
                mTagTextPaint.setColor(Color.WHITE);
                //canvas.drawText(String.format("%d", day), x, y - DAY_SELECTED_RECT_HALF_WIDTH / 2, mDayTextPaint);
            }

            boolean isSelected = false;

            isSelected = drawSelectedDay(x, y, canvas, day, isSelected, isPrevDay);

            isSelected = drawBusyDay(x, y, canvas, day, isSelected, isPrevDay);

            boolean isInvalidDays = false;
            if (isInInvalidRange(cellCalendar) && !isPrevDay) {
                isSelected = true;
//                    RectF rectF = new RectF(x - DAY_SELECTED_RECT_HALF_WIDTH,
//                            (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) - DAY_SELECTED_RECT_HALF_WIDTH,
//                            x + DAY_SELECTED_RECT_HALF_WIDTH, (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) + DAY_SELECTED_RECT_HALF_WIDTH);

                if (mStartDate != null &&
                        mEndDate != null &&
                        mNearestDay != null &&
                        mEndDate.equals(mNearestDay) &&
                        mEndDate.equals(cellCalendar)) {
                } else {
                    if (mStartDate != null && mEndDate == null && mNearestDay != null && cellCalendar.equals(mNearestDay)) {
                        mDayTextPaint.setColor(mDayTextColor);
                    } else {
                        drawDayBg(canvas, x, y, mInValidDayBgPaint);
//                            canvas.drawRoundRect(rectF, 10.0f, 10.0f, mBusyDayBgPaint);
                        mDayTextPaint.setColor(mInValidDaysTextColor);
                    }
                    canvas.drawText("Temp", x, getTextYCenter(mInValidDayBgPaint, y + DAY_SELECTED_RECT_HALF_WIDTH / 2), mDayTextPaint);
                }
                canvas.drawText(String.format("%d", day), x, getTextYCenter(mTagTextPaint, y - DAY_SELECTED_RECT_HALF_HEIGHT / 2), mDayTextPaint);
            }


            if (mStartDate != null && mEndDate == null && !mStartDate.equals(mEndDate) && !isInvalidDays && !isSelected) {
                if (cellCalendar.before(mStartDate) || (mNearestDay != null && cellCalendar.after(mNearestDay))) {
//                    RectF rectF = new RectF(x - DAY_SELECTED_RECT_HALF_WIDTH, (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) - DAY_SELECTED_RECT_HALF_WIDTH,
//                            x + DAY_SELECTED_RECT_HALF_WIDTH, (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) + DAY_SELECTED_RECT_HALF_WIDTH);
//                    canvas.drawRoundRect(rectF, 10.0f, 10.0f, mInValidDayBgPaint);
                    drawDayBg(canvas, x, y, mInValidDayBgPaint);
                }
            }

            if (!isToday && !isPrevDay && !isInvalidDays && !isSelected) {
                canvas.drawText(String.format("%d", day), x, getTextYCenter(mTagTextPaint, y - DAY_SELECTED_RECT_HALF_HEIGHT / 2), mDayTextPaint);
            }

            dayOffset++;
            if (dayOffset == mNumDays) {
                dayOffset = 0;
                y += mRowHeight;
            }
            day++;
        }
    }

    private boolean drawSelectedDay(int x, int y, Canvas canvas, int day, boolean isDaySelected, boolean isPrevDay) {
        boolean isSelected = isDaySelected;

        if (isSelectedDay(cellCalendar) && !isPrevDay) {
            isSelected = true;
            RectF rectF = new RectF(x - DAY_SELECTED_RECT_HALF_WIDTH,
                    (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) - DAY_SELECTED_RECT_HALF_WIDTH,
                    x + DAY_SELECTED_RECT_HALF_WIDTH, (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) + DAY_SELECTED_RECT_HALF_HEIGHT);

            if (mStartDate != null && mEndDate != null && mNearestDay != null &&
                    mEndDate.equals(mNearestDay) && mEndDate.equals(cellCalendar)) {

            } else {
                if (mStartDate != null && mEndDate == null && mNearestDay != null && cellCalendar.equals(mNearestDay)) {
                    mDayTextPaint.setColor(mDayTextColor);
                } else {
                    //canvas.drawRoundRect(rectF, 10.0f, 10.0f, mSelectedDayBgPaint);
                    mDayTextPaint.setColor(mSelectedDayTextColor);
                }

                if (cellCalendar.equals(mDataModel.getRangeDays().getLast()) ||
                        cellCalendar.equals(mDataModel.getRangeDays().getFirst())) {
                    drawDayBg(canvas, x, y, mFirstLastSelectedDayBgPaint);
                    //canvas.drawRoundRect(rectF, 10.0f, 10.0f, mSelectedDayBgPaint);
                } else {
                    drawDayBg(canvas, x, y, mSelectedDayBgPaint);
                }
                    /*canvas.drawText(
                            getResources().getString(R.string.busy),
                            x,
                            getTextYCenter(mSelectedDayBgPaint, y + DAY_SELECTED_RECT_HALF_WIDTH / 2),
                            mDayTextPaint);*/
            }
            canvas.drawText(String.format("%d", day), x, getTextYCenter(mTagTextPaint, y - DAY_SELECTED_RECT_HALF_HEIGHT / 2), mDayTextPaint);
        }

        return isSelected;
    }

    private boolean drawBusyDay(int x,
                                int y,
                                Canvas canvas,
                                int day,
                                boolean isDaySelected,
                                boolean isPrevDay) {
        boolean isSelected = isDaySelected;
        if (isBusyDay(cellCalendar) && !isPrevDay) {
            isSelected = true;
            /*RectF rectF = new RectF(x - DAY_SELECTED_RECT_HALF_WIDTH,
                    (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) - DAY_SELECTED_RECT_HALF_WIDTH,
                    x + DAY_SELECTED_RECT_HALF_WIDTH, (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) + DAY_SELECTED_RECT_HALF_WIDTH);*/

            if (mStartDate != null
                    && mEndDate != null
                    && mNearestDay != null &&
                    mEndDate.equals(mNearestDay) && mEndDate.equals(cellCalendar)) {

            } else {
                if (mStartDate != null && mEndDate == null && mNearestDay != null && cellCalendar.equals(mNearestDay)) {
                    mDayTextPaint.setColor(mDayTextColor);
                } else if (mDataModel.getFirstLastDayCollection().contains(cellCalendar)) {
                    drawLastAndFirstBusyDays(x, y, canvas, cellCalendar);
                } else {
                    drawDayBg(canvas, x, y, mBusyDayBgPaint);
                    //canvas.drawRect(rectF, mBusyDayBgPaint);
                    mDayTextPaint.setColor(mBusyDaysTextColor);
                }

                    /*canvas.drawText(
                            getResources().getString(R.string.busy),
                            x,
                            getTextYCenter(mBusyDayBgPaint, y + DAY_SELECTED_RECT_HALF_WIDTH / 2),
                            mDayTextPaint);*/
            }
            canvas.drawText(String.format("%d", day), x, getTextYCenter(mTagTextPaint, y - DAY_SELECTED_RECT_HALF_HEIGHT / 2), mDayTextPaint);
        }
        return isSelected;
    }

    private void drawLastAndFirstBusyDays(int x,
                                          int y,
                                          Canvas canvas,
                                          CalendarDay cellCalendar) {

        RectF rectF = new RectF(x - DAY_SELECTED_RECT_HALF_WIDTH, y - DAY_SELECTED_RECT_HALF_HEIGHT,
                x + DAY_SELECTED_RECT_HALF_WIDTH, y + DAY_SELECTED_RECT_HALF_HEIGHT);

        List<CalendarDay> daysCollection = mDataModel.getFirstLastDayCollection();
        int position = daysCollection.indexOf(cellCalendar);
        CalendarDay extemeDay =  daysCollection.get(position);

        if (extemeDay.getType() == CalendarDay.FIRST_BUSY_TYPE &&
                extemeDay.isSecondHalfDay()) {
            rectF.left = rectF.left + (rectF.right - rectF.left) / 2;
        }

        if (extemeDay.getType() == CalendarDay.LAST_BUSY_TYPE &&
                extemeDay.isFirstHalfDay()) {
            rectF.right = rectF.right - (rectF.right - rectF.left) / 2;
        }

        canvas.drawRect(rectF, mBusyDayBgPaint);
        mDayTextPaint.setColor(mBusyDaysTextColor);
    }

    private void drawToday(boolean isToday, int day, Canvas canvas, int x, int y) {
        if (mHasToday && (mToday == day)) {
            isToday = true;
            /*canvas.drawText(
                    getResources().getString(R.string.today),
                    x,
                    getTextYCenter(mDayTextPaint, y - DAY_SELECTED_RECT_HALF_WIDTH / 2),
                    mDayTextPaint);*/
            RectF todayFrameRect = new RectF(x - DAY_SELECTED_RECT_HALF_WIDTH, y - DAY_SELECTED_RECT_HALF_HEIGHT,
                    x + DAY_SELECTED_RECT_HALF_WIDTH, y + DAY_SELECTED_RECT_HALF_HEIGHT);
            canvas.drawRect(todayFrameRect, mTodayFramePaint);
        }
    }

    public CalendarDay getDayFromLocation(float x, float y) {
        int padding = mPadding;
        if ((x < padding) || (x > mWidth - mPadding)) {
            return null;
        }

        int yDay = (int) (y - MONTH_HEADER_SIZE) / mRowHeight;
        int day = 1 + ((int) ((x - padding) * mNumDays / (mWidth - padding - mPadding)) - findDayOffset()) + yDay * mNumDays;

        if (mMonth > 11 || mMonth < 0 || Utils.getDaysInMonth(mMonth, mYear) < day || day < 1)
            return null;

        CalendarDay calendar = new CalendarDay(mYear, mMonth, day);
        return calendar;
    }

    protected void initView() {
        mYearMonthPaint = new Paint();
        mYearMonthPaint.setFakeBoldText(true);
        mYearMonthPaint.setAntiAlias(true);
        mYearMonthPaint.setTextSize(YEAR_MONTH_TEXT_SIZE);
        mYearMonthPaint.setTypeface(Typeface.create(mMonthTitleTypeface, Typeface.BOLD));
        mYearMonthPaint.setColor(mYearMonthTextColor);
        mYearMonthPaint.setTextAlign(Paint.Align.CENTER);
        mYearMonthPaint.setStyle(Paint.Style.FILL);

        mWeekTextPaint = new Paint();
        mWeekTextPaint.setAntiAlias(true);
        mWeekTextPaint.setTextSize(WEEK_TEXT_SIZE);
        mWeekTextPaint.setColor(mWeekTextColor);
        mWeekTextPaint.setTypeface(Typeface.create(mDayOfWeekTypeface, Typeface.NORMAL));
        mWeekTextPaint.setStyle(Paint.Style.FILL);
        mWeekTextPaint.setTextAlign(Paint.Align.CENTER);
        mWeekTextPaint.setFakeBoldText(true);

//        mTitleBGPaint = new Paint();
//        mTitleBGPaint.setFakeBoldText(true);
//        mTitleBGPaint.setAntiAlias(true);
//        mTitleBGPaint.setColor(mSelectedDayTextColor);
//        mTitleBGPaint.setTextAlign(Align.CENTER);
//        mTitleBGPaint.setStyle(Style.FILL);

        mSelectedDayBgPaint = new Paint();
        mSelectedDayBgPaint.setFakeBoldText(true);
        mSelectedDayBgPaint.setAntiAlias(true);
        mSelectedDayBgPaint.setColor(mSelectedDaysBgColor);
        mSelectedDayBgPaint.setTextAlign(Paint.Align.CENTER);
        mSelectedDayBgPaint.setStyle(Paint.Style.FILL);
        mSelectedDayBgPaint.setAlpha(SELECTED_CIRCLE_ALPHA);

        mFirstLastSelectedDayBgPaint = new Paint();
        mFirstLastSelectedDayBgPaint.setFakeBoldText(true);
        mFirstLastSelectedDayBgPaint.setAntiAlias(true);
        mFirstLastSelectedDayBgPaint.setColor(mFirstLastSelectedDayColor);
        mFirstLastSelectedDayBgPaint.setTextAlign(Paint.Align.CENTER);
        mFirstLastSelectedDayBgPaint.setStyle(Paint.Style.FILL);
        //mFirstLastSelectedDayBgPaint.setAlpha(SELECTED_CIRCLE_ALPHA);

        mBusyDayBgPaint = new Paint();
        mBusyDayBgPaint.setFakeBoldText(true);
        mBusyDayBgPaint.setAntiAlias(true);
        mBusyDayBgPaint.setColor(mBusyDaysBgColor);
        mBusyDayBgPaint.setTextSize(TAG_TEXT_SIZE);
        mBusyDayBgPaint.setTextAlign(Paint.Align.CENTER);
        mBusyDayBgPaint.setStyle(Paint.Style.FILL);
        //mBusyDayBgPaint.setAlpha(SELECTED_CIRCLE_ALPHA);

        mInValidDayBgPaint = new Paint();
        mInValidDayBgPaint.setFakeBoldText(true);
        mInValidDayBgPaint.setAntiAlias(true);
        mInValidDayBgPaint.setColor(mInValidDaysBgColor);
        mInValidDayBgPaint.setTextSize(TAG_TEXT_SIZE);
        mInValidDayBgPaint.setTextAlign(Paint.Align.CENTER);
        mInValidDayBgPaint.setStyle(Paint.Style.FILL);
        mInValidDayBgPaint.setAlpha(SELECTED_CIRCLE_ALPHA);

//        mSelectedDayTextPaint = new Paint();
//        mSelectedDayTextPaint.setAntiAlias(true);
//        mSelectedDayTextPaint.setTextSize(MONTH_DAY_LABEL_TEXT_SIZE);
//        mSelectedDayTextPaint.setColor(Color.WHITE);
//        mSelectedDayTextPaint.setTypeface(Typeface.create(mDayOfWeekTypeface, Typeface.NORMAL));
//        mSelectedDayTextPaint.setStyle(Style.FILL);
//        mSelectedDayTextPaint.setTextAlign(Align.CENTER);
//        mSelectedDayTextPaint.setFakeBoldText(true);

        mDayTextPaint = new Paint();
        mDayTextPaint.setAntiAlias(true);
        mDayTextPaint.setColor(mDayTextColor);
        mDayTextPaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mDayTextPaint.setStyle(Paint.Style.FILL);
        mDayTextPaint.setTextAlign(Paint.Align.CENTER);
        mDayTextPaint.setFakeBoldText(false);

        mTagTextPaint = new Paint();
        mTagTextPaint.setAntiAlias(true);
        mTagTextPaint.setColor(mDayTextColor);
        mTagTextPaint.setTextSize(TAG_TEXT_SIZE);
        mTagTextPaint.setStyle(Paint.Style.FILL);
        mTagTextPaint.setTextAlign(Paint.Align.CENTER);
        mTagTextPaint.setFakeBoldText(false);


        //TODO: Add thickness of border parameter to styleable
        mTodayFramePaint = new Paint();
        mTodayFramePaint.setAntiAlias(true);
        mTodayFramePaint.setColor(mTodayBorderColor);
        mTodayFramePaint.setStrokeWidth(getResources().getDimension(R.dimen.today_border_width));
        mTodayFramePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawMonthTitle(canvas);
        drawMonthDayLabels(canvas);
        drawMonthCell(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mRowHeight * mNumRows + MONTH_HEADER_SIZE + ROW_SEPARATOR);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            CalendarDay calendarDay = getDayFromLocation(event.getX(), event.getY());
            boolean isValidDay = false;
            if (calendarDay == null) {
                return true;
            }

            if (isInInvalidRange(calendarDay)) {
                return true;
            }

            if (isBusyDay(calendarDay)) {
                return true;
            }

            if (isSelectedDay(calendarDay)) {
                return true;
            }

            onDayClick(calendarDay);
        }
        return true;
    }

    private boolean isInInvalidRange(CalendarDay calendarDay) {
        if(mDataModel.getInvalidDays() == null) {
            return false;
        }

        boolean isInInvalidRange =
                calendarDay.after(mDataModel.getInvalidDays().getFirst()) &&
                        calendarDay.before(mDataModel.getInvalidDays().getLast()) ||
                        calendarDay.equals(mDataModel.getInvalidDays().getFirst()) ||
                        calendarDay.equals(mDataModel.getInvalidDays().getLast());


        if (isInInvalidRange && isNotNearestDay(calendarDay)) {
            return true;
        }

        return false;
    }

    private boolean isNotNearestDay(CalendarDay calendarDay) {
        boolean isNearest =
                mEndDate == null &&
                        mNearestDay != null &&
                        calendarDay.equals(mNearestDay);

        return !isNearest;
    }

    private boolean isSelectedDay(CalendarDay calendarDay) {

        boolean isInSelectedRange =
                calendarDay.after(mDataModel.getRangeDays().getFirst()) &&
                        calendarDay.before(mDataModel.getRangeDays().getLast()) ||
                        calendarDay.equals(mDataModel.getRangeDays().getFirst()) ||
                        calendarDay.equals(mDataModel.getRangeDays().getLast());

        if (isInSelectedRange && !isNotNearestDay(calendarDay)) {
            return true;
        }

        return false;
    }

    private boolean isBusyDay(CalendarDay calendarDay) {
        List<DaySelection<CalendarDay>> busyRanges = mDataModel.getBusyDays();
        boolean isInBusyRange =
                false;

        for (DaySelection daySelection : busyRanges) {
            isInBusyRange =
                    calendarDay.after(daySelection.getFirst()) &&
                            calendarDay.before(daySelection.getLast()) ||
                    calendarDay.equals(daySelection.getFirst()) ||
                    calendarDay.equals(daySelection.getLast());

            if (isInBusyRange) {
                return true;
            }
        }

        return false;
    }

    public void reuse() {
        mNumRows = DEFAULT_NUM_ROWS;
        requestLayout();
    }


    public void setMonthParams(HashMap<String, Object> params) {
        if (!params.containsKey(VIEW_PARAMS_MONTH) && !params.containsKey(VIEW_PARAMS_YEAR)) {
            throw new InvalidParameterException("You must specify month and year for this view");
        }
        setTag(params);

        if (params.containsKey(VIEW_PARAMS_HEIGHT)) {
            mRowHeight = (int) params.get(VIEW_PARAMS_HEIGHT);

            if (mRowHeight < MIN_HEIGHT) {
                mRowHeight = MIN_HEIGHT;
            }
        }

        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_DATE)) {
            mStartDate = (CalendarDay) params.get(VIEW_PARAMS_SELECTED_BEGIN_DATE);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_DATE)) {
            mEndDate = (CalendarDay) params.get(VIEW_PARAMS_SELECTED_LAST_DATE);
        }

        if (params.containsKey(VIEW_PARAMS_NEAREST_DATE)) {
            mNearestDay = (CalendarDay) params.get(VIEW_PARAMS_NEAREST_DATE);
        }

        mMonth = (int) params.get(VIEW_PARAMS_MONTH);
        mYear = (int) params.get(VIEW_PARAMS_YEAR);

        mHasToday = false;
        mToday = -1;

        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mDayOfWeekStart = mCalendar.get(Calendar.DAY_OF_WEEK);

        if (params.containsKey(VIEW_PARAMS_WEEK_START)) {
            mWeekStart = (int) params.get(VIEW_PARAMS_WEEK_START);
        } else {
            mWeekStart = mCalendar.getFirstDayOfWeek();
        }

        mNumCells = Utils.getDaysInMonth(mMonth, mYear);
        for (int i = 0; i < mNumCells; i++) {
            final int day = i + 1;
            if (sameDay(day, today)) {
                mHasToday = true;
                mToday = day;
            }
        }

        mNumRows = calculateNumRows();
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public interface OnDayClickListener {
        void onDayClick(MonthView monthView, CalendarDay calendarDay);
    }

    private void drawDayBg(Canvas canvas, int x, int y, Paint paint) {
        RectF rectF = new RectF(x - DAY_SELECTED_RECT_HALF_WIDTH, y - DAY_SELECTED_RECT_HALF_HEIGHT,
                x + DAY_SELECTED_RECT_HALF_WIDTH, y + DAY_SELECTED_RECT_HALF_HEIGHT);
        canvas.drawRect(rectF, paint);
    }


    private float getTextYCenter(Paint paint, int y) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        float offY = fontTotalHeight / 2 - fontMetrics.bottom;
        return y + offY + mTopDayPadding;
    }
}
