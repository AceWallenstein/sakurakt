package com.blankspace.sakura.common.utils

import java.util.*

object CalendarUtil {
    @JvmStatic
    fun getFirstDayOfMonth(showYear: Int, showMonth: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.setTime(Date(showYear, showMonth,1))
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    fun getDays(tempY: Int, tempM: Int): MutableList<Int> {
        val days = mutableListOf<Int>()
        val calendar = Calendar.getInstance()
        calendar.setTime(Date(tempY,tempM,1))
        val firstDayOfMonth = getFirstDayOfMonth(tempY, tempM)
        val dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (index in 1..dayOfMonth) {
            days.add(index)
        }
        return days

    }

    fun getDay(tempY: Int, tempM: Int): MutableList<Int> {
        val days = mutableListOf<Int>()
        var cy = 0;var ny = 0
        var cm = 0;var nm = 0
        if (tempM < 0) {
            cm = 11
            cy = tempY - 1
        }else{
            cm = tempM-1
            cy = tempY
        }
        val lastMouthDay = getDays(cy, cm)

        if (tempM > 11) {
            nm = 0
            ny = tempY + 1
        }else{
            nm = tempM+1
            ny = tempY
        }
        val nextMouthDay = getDays(ny, nm)
        val currentMouthDay = getDays(tempY, tempM)
        val firstDayInMonth = getFirstDayOfMonth(tempY,tempM)
        if (firstDayInMonth == 0) {//星期天
            days.addAll(lastMouthDay.subList(lastMouthDay.size - 7, lastMouthDay.size));
        } else {
            days.addAll(lastMouthDay.subList(lastMouthDay.size - firstDayInMonth, lastMouthDay.size));
        }
        days.addAll(currentMouthDay)
        days.addAll(nextMouthDay.subList(0, 42 - days.size));
        return days
    }


}