package com.ezike.tobenna.stockwatcher.mapper

import com.ezike.tobenna.lib_stock_price.PricePercentageCalculator
import com.ezike.tobenna.stockwatcher.R
import com.ezike.tobenna.stockwatcher.formatter.DecimalFormatter
import com.ezike.tobenna.stockwatcher.model.PercentageState
import com.ezike.tobenna.stockwatcher.then
import javax.inject.Inject

class PricePercentProvider @Inject constructor(
    private val decimalFormatter: DecimalFormatter,
    private val percentageCalculator: PricePercentageCalculator
) {

    data class Param(
        val currentValue: Double?,
        val openPrice: Double?
    )

    fun createPercentState(data: Param): PercentageState {
        val percentage = percentageCalculator.calculatePercentage(
            openPrice = data.openPrice,
            currentPrice = data.currentValue
        )

        val state = PercentageState.Initial

        if (percentage == null) return state

        val formattedPercent = decimalFormatter.format(
            value = percentage
        )

        val (icon, background, textColor) = when {
            percentage > 0 -> R.drawable.up to R.drawable.bg_high then R.color.percent_up
            percentage == 0.0 -> R.drawable.transparent to R.drawable.bg_none then state.textColor
            else -> R.drawable.down to R.drawable.bg_low then R.color.percent_down
        }

        return state.copy(
            value = formattedPercent.removePrefix("-"),
            icon = icon,
            background = background,
            textColor = textColor
        )
    }
}