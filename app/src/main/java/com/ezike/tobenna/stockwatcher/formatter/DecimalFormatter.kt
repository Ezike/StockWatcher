package com.ezike.tobenna.stockwatcher.formatter

import java.text.DecimalFormat
import javax.inject.Inject

class DecimalFormatter @Inject constructor(
    private val decimalFormat: DecimalFormat
) {

    fun format(value: Double): String {
        decimalFormat.maximumFractionDigits = 1
        return String.format("%.1f", value)
    }
}
