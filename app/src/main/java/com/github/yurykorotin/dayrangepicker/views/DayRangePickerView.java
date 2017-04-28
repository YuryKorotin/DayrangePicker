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

import java.io.Serializable;

/**
 * Created by yuri on 27.04.17.
 */

public class DayRangePickerView extends RecyclerView {
    protected Context mContext;
    protected CalendarAdapter mAdapter;
    private DayRangePickerController mController;
    protected int mCurrentScrollState = 0;
    protected long mPreviousScrollPosition;
    protected int mPreviousScrollState = 0;
    private TypedArray typedArray;
    private OnScrollListener onScrollListener;

    private RangeModel dataModel;

    public DayRangePickerView(Context context) {
        this(context, null);
    }

    public DayRangePickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayRangePickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayPickerView);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        init(context);
    }

    public void init(Context paramContext) {
        setLayoutManager(new LinearLayoutManager(paramContext));
        mContext = paramContext;
        setUpListView();

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
    }

    protected void setUpAdapter() {
        if (mAdapter == null) {
            mAdapter = new CalendarAdapter(getContext(), typedArray, mController, dataModel);
            setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    protected void setUpListView() {
        setVerticalScrollBarEnabled(false);
        setOnScrollListener(onScrollListener);
        setFadingEdgeLength(0);
    }

    /**
     *
     * @param dataModel   数据
     * @param mController 回调监听
     */
    public void setParameter(RangeModel dataModel, DayRangePickerController mController) {
        if (dataModel == null) {
            return;
        }
        this.dataModel = dataModel;
        this.mController = mController;
        setUpAdapter();
        scrollToSelectedPosition(dataModel.selectedDays, dataModel.monthStart);
    }

    private void scrollToSelectedPosition(DaySelection<CalendarDay> selectedDays,
                                          int monthStart) {
        if (selectedDays != null && selectedDays.getFirst() != null &&
                selectedDays.getFirst().month > monthStart) {
            int position = selectedDays.getFirst().month - monthStart;
            scrollToPosition(position);
        }
    }
}
