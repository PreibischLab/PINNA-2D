package com.preibisch.pinna2d.util

import org.joda.time.DateTime
import java.time.LocalDate

fun DateTime.toJavaLocalDate(): LocalDate {
    return LocalDate.of(this.year, this.monthOfYear, this.dayOfMonth)
}

fun LocalDate.toDate(default: DateTime = org.joda.time.DateTime(1900, 1, 1, 0, 0, 0)): DateTime {
    return DateTime(this.year, this.monthValue, this.dayOfMonth, 0, 0, 0)
}


fun <T> randomValue(list: List<T>): T{
    val listSize: Int = list.size
    val randomNum: Int = (0 until listSize).shuffled().last()
    return list[randomNum]
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)

val Int.plural : String get() = if(this ==1) "" else "s"