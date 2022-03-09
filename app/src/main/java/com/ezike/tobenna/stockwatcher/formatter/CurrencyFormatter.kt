package com.ezike.tobenna.stockwatcher.formatter

import java.text.DecimalFormat
import javax.inject.Inject

class CurrencyFormatter @Inject constructor(
    private val decimalFormat: DecimalFormat
) {

    fun format(value: Double): String {
        val symbols = decimalFormat.decimalFormatSymbols
        symbols.currencySymbol = "€"
        decimalFormat.decimalFormatSymbols = symbols
        decimalFormat.maximumFractionDigits = 2
        return decimalFormat.format(value)
    }
}
