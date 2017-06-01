package com.github.yurykorotin.dayrangepicker.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.github.yurykorotin.dayrangepicker.R;
import com.github.yurykorotin.dayrangepicker.builders.CalendarDataBuilder;
import com.github.yurykorotin.dayrangepicker.controllers.DayRangePickerController;
import com.github.yurykorotin.dayrangepicker.models.CalendarConfig;
import com.github.yurykorotin.dayrangepicker.models.CalendarData;
import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.DaySelection;

import java.util.Calendar;

/**
 * Created by yuri on 27.04.17.
 */

public class DayRangeSelectionView extends RecyclerView {
    protected Context mContext;
    protected CalendarAdapter mAdapter;
    private DayRangePickerController mController;
    private @SelectionMode int mSelectionMode = COMMON_SELECTION_MODE;

    public @SelectionMode int getSelectionMode() {
        return mSelectionMode;
    }

    public void setSelectionMode(@SelectionMode int selectionMode) {
        mSelectionMode = selectionMode;

        notifyModeChange();
    }

    private void notifyModeChange() {

        if (mAdapter == null) {
            return;
        }

        switch (mSelectionMode) {
            case FIRST_DATE_SELECTION_MODE:
                mAdapter.setSelectionMode(CalendarData.FIRST_DATE_SELECTION_MODE);
                break;
            case LAST_DATE_SELECTION_MODE:
                mAdapter.setSelectionMode(CalendarData.LAST_DATE_SELECTION_MODE);
                break;
            case DISABLED_SELECTION_MODE:
                mAdapter.setSelectionMode(CalendarData.DISABLED_SELECTION_MODE);
                break;
            default:
                mAdapter.setSelectionMode(CalendarData.COMMON_SELECTION_MODE);
        }

        mAdapter.notifyDataSetChanged();
    }

    @IntDef({FIRST_DATE_SELECTION_MODE,
            LAST_DATE_SELECTION_MODE,
            COMMON_SELECTION_MODE,
            DISABLED_SELECTION_MODE})

    public @interface SelectionMode{}

    public static final int FIRST_DATE_SELECTION_MODE = 0;
    public static final int LAST_DATE_SELECTION_MODE = 1;
    public static final int COMMON_SELECTION_MODE = 2;
    public static final int DISABLED_SELECTION_MODE = 3;

    protected int mCurrentScrollState = 0;
    protected long mPreviousScrollPosition;
    protected int mPreviousScrollState = 0;

    private TypedArray typedArray;
    private OnScrollListener onScrollListener;
    private CalendarConfig mDataModel;

    public DayRangeSelectionView(Context context) {
        this(context, null);
    }

    public DayRangeSelectionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayRangeSelectionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayPickerView);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        init(context);
    }

    public void init(Context paramContext) {
        setLayoutManager(new LinearLayoutManager(paramContext));
        mContext = paramContext;
        onScrollListener = new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final MonthView child = (MonthView) recyclerView.getChildAt(0);
                if (child == null) {
                    return;
                }

                mPreviousScrollPosition = dy;
                mPreviousScrollState = mCurrentScrollState;
            }
        };
        setUpListView();

    }

    protected void setUpAdapter() {
        if (mAdapter == null) {
            CalendarDataBuilder builder = new CalendarDataBuilder();
            builder.setCalendarConfig(mDataModel);
            mAdapter = new CalendarAdapter(
                    getContext(),
                    typedArray,
                    mController,
                    builder.build());
            setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
        scrollToCurrentMonth();
    }

    protected void setUpListView() {
        setVerticalScrollBarEnabled(false);
        addOnScrollListener(onScrollListener);
        setFadingEdgeLength(0);
    }

    /**
     * @param dataModel contains info of day ranges
     * @param controller callbacks the selection actions
     */
    public void setParameter(CalendarConfig dataModel, DayRangePickerController controller) {
        if (dataModel == null) {
            return;
        }
        mDataModel = dataModel;
        mController = controller;
        setUpAdapter();
        scrollToSelectedPosition(mDataModel.getSelectedDays(), mDataModel.getMonthStart());
        scrollToCurrentMonth();
    }

    private void scrollToSelectedPosition(DaySelection<CalendarDay> selectedDays,
                                          int monthStart) {
        if (selectedDays != null &&
                selectedDays.getFirst() != null &&
                selectedDays.getFirst().getMonth() > monthStart) {
            int position = selectedDays.getFirst().getMonth() - monthStart;
            scrollToPosition(position);
        }
    }

    private void scrollToCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int position = 0;
        int totalMonthCount = mDataModel.getMonthCount();

        if (year == mDataModel.getYearStart()) {
            position = month - mDataModel.getMonthStart();
        } else {
            position = totalMonthCount -
                    mDataModel.getMonthStart()
                    + month;
        }
        scrollToPosition(position);
    }

    public void setAttributes(TypedArray attributes) {
        typedArray = attributes;
        init(getContext());
    }
}
