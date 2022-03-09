package com.ezike.tobenna.stockwatcher.mapper

import com.ezike.tobenna.lib_stock_price.PricePercentageCalculator
import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import com.ezike.tobenna.stockwatcher.R.color
import com.ezike.tobenna.stockwatcher.R.drawable
import com.ezike.tobenna.stockwatcher.formatter.CurrencyFormatter
import com.ezike.tobenna.stockwatcher.formatter.DecimalFormatter
import com.ezike.tobenna.stockwatcher.model.PercentageState
import com.ezike.tobenna.stockwatcher.model.StockModel
import com.ezike.tobenna.stockwatcher.then
import javax.inject.Inject

class StockDataMapper @Inject constructor(
    private val currencyFormatter: CurrencyFormatter,
    private val decimalFormatter: DecimalFormatter,
    private val percentageCalculator: PricePercentageCalculator
) {

    fun toStockModel(
        stockData: List<StockData>
    ) = stockData.map { data ->
        val price = data.price
        val formattedPrice = if (price != null) {
            currencyFormatter.format(price)
        } else "--"

        val percentage = percentageCalculator.calculatePercentage(
            openPrice = data.openPrice,
            currentPrice = price
        )

        return@map StockModel(
            name = data.name,
            price = formattedPrice,
            percentState = createPercentState(percentage)
        )
    }

    private fun createPercentState(percentage: Double?): PercentageState {
        val state = PercentageState.Initial
        if (percentage == null) return state

        val formattedPercent = decimalFormatter.format(
            value = percentage
        )

        val (icon, background, textColor) = when {
            percentage > 0 -> drawable.up to drawable.bg_high then color.percent_up
            percentage == 0.0 -> drawable.transparent to drawable.bg_none then state.textColor
            else -> drawable.down to drawable.bg_low then color.percent_down
        }

        return state.copy(
            value = formattedPercent.removePrefix("-"),
            icon = icon,
            background = background,
            textColor = textColor
        )
    }
}
