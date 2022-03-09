package com.ezike.tobenna.lib_stock_price

import javax.inject.Inject

public class PricePercentageCalculator @Inject constructor() {

    public fun calculatePercentage(
        openPrice: Double?,
        currentPrice: Double?
    ): Double? {
        if (openPrice == null || openPrice == 0.0 || currentPrice == null) {
            return null
        }
        val fraction = (currentPrice - openPrice) / openPrice
        return fraction * 100
    }
}
