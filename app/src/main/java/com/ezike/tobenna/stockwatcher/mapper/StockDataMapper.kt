package com.ezike.tobenna.stockwatcher.mapper

import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import com.ezike.tobenna.stockwatcher.formatter.CurrencyFormatter
import com.ezike.tobenna.stockwatcher.model.StockModel
import javax.inject.Inject

class StockDataMapper @Inject constructor(
    private val currencyFormatter: CurrencyFormatter,
    private val pricePercentProvider: PricePercentProvider
) {

    fun toStockModel(
        stockData: List<StockData>
    ) = stockData.map { data ->
        val price = data.price
        val formattedPrice = if (price != null) {
            currencyFormatter.format(price)
        } else "--"

        val percentState = pricePercentProvider.createPercentState(
            data = PricePercentProvider.Param(
                openPrice = data.openPrice,
                currentValue = price
            )
        )

        return@map StockModel(
            name = data.name,
            price = formattedPrice,
            percentState = percentState
        )
    }
}
