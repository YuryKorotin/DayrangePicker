package com.github.yurykorotin.dayrangepicker.models;

import java.io.Serializable;

/**
 * Created by yuri on 27.04.17.
 */

public class DaySelection<K> implements Serializable {
    private static final long serialVersionUID = 3942549765282708376L;
    private K first;
    private K last;

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

}
