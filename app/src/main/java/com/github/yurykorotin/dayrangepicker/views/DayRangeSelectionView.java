package com.github.yurykorotin.dayrangepicker.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.github.yurykorotin.dayrangepicker.R;
import com.github.yurykorotin.dayrangepicker.models.CalendarDay;
import com.github.yurykorotin.dayrangepicker.models.DaySelection;
import com.github.yurykorotin.dayrangepicker.models.RangeModel;

import java.util.Calendar;

/**
 * Created by yuri on 27.04.17.
 */

public class DayRangeSelectionView extends RecyclerView {
    protected Context mContext;
    protected CalendarAdapter mAdapter;
    private DayRangePickerController mController;
    protected int mCurrentScrollState = 0;
    protected long mPreviousScrollPosition;
    protected int mPreviousScrollState = 0;
    private TypedArray typedArray;
    private OnScrollListener onScrollListener;

    private RangeModel dataModel;

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
            mAdapter = new CalendarAdapter(getContext(), typedArray, mController, dataModel);
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
     * @param dataModel
     * @param mController
     */
    public void setParameter(RangeModel dataModel, DayRangePickerController mController) {
        if (dataModel == null) {
            return;
        }
        this.dataModel = dataModel;
        this.mController = mController;
        setUpAdapter();
        scrollToSelectedPosition(dataModel.selectedDays, dataModel.monthStart);
        scrollToCurrentMonth();
    }

    private void scrollToSelectedPosition(DaySelection<CalendarDay> selectedDays,
                                          int monthStart) {
        if (selectedDays != null && selectedDays.getFirst() != null &&
                selectedDays.getFirst().month > monthStart) {
            int position = selectedDays.getFirst().month - monthStart;
            scrollToPosition(position);
        }
    }

    private void scrollToCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int position = 0;
        int totalMonthCount = 12;
        if (year == dataModel.yearStart) {
            position = month - dataModel.monthStart;
        } else {
            position = totalMonthCount - dataModel.monthStart + month;
        }

        scrollToPosition(position);
    }
}
