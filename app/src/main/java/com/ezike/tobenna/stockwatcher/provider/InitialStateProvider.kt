package com.ezike.tobenna.stockwatcher.provider

import com.ezike.tobenna.lib_stock_price.domain.model.Stock
import com.ezike.tobenna.stockwatcher.R
import com.ezike.tobenna.stockwatcher.StringResource
import com.ezike.tobenna.stockwatcher.model.ConnectionState
import com.ezike.tobenna.stockwatcher.model.PercentageState
import com.ezike.tobenna.stockwatcher.model.StockModel
import javax.inject.Inject

class InitialStateProvider @Inject constructor() {

    fun viewState(): List<StockModel> =
        Stock.values().map { security ->
            StockModel(
                name = security.name,
                price = "--",
                percentState = PercentageState.Initial
            )
        }

    fun connectionState() = ConnectionState(
        title = StringResource(R.string.connecting),
        icon = R.drawable.offline,
        isError = false
    )
}
