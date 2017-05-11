package com.github.yurykorotin.dayrangepicker.models;

import android.support.annotation.IntDef;

import java.io.Serializable;

/**
 * Created by yuri on 27.04.17.
 */

public class DaySelection<K> implements Serializable {
    private static final long serialVersionUID = 3942549765282708376L;
    private K first;
    private K last;
    private @SelectionType int mType;

    @IntDef({BUSY_TYPE, SELECTED_TYPE, DISABLED_TYPE})

    public @interface SelectionType{}

    public static final int BUSY_TYPE = 0;
    public static final int SELECTED_TYPE = 1;
    public static final int DISABLED_TYPE = 2;

    public DaySelection() {
    }

    public DaySelection(K first, K last) {
        this.first = first;
        this.last = last;
    }

    public K getFirst() {
        return first;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public K getLast() {
        return last;
    }

    public void setLast(K last) {
        this.last = last;
    }

    public @SelectionType int getType() {
        return mType;
    }

    public void setType(@SelectionType int type) {
        mType = type;
    }

}
