package com.github.yurykorotin.dayrangepicker.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuri on 27.04.17.
 */

public class RangeModel implements Serializable {

    public enum TYPE {TYPE_MULTI, TYPE_RANGE, TYPE_ONLY_READ}

    TYPE type;
    public int yearStart;
    public int monthStart;
    public int monthCount;
    public List<CalendarDay> invalidDays;
    public List<CalendarDay> busyDays;
    public DaySelection<CalendarDay> selectedDays;
    public int leastDaysNum;
    public int mostDaysNum;
    public List<CalendarDay> tags;
    public String defTag;
    public boolean displayTag;

}
