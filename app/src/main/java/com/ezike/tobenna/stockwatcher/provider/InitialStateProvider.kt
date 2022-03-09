package com.ezike.tobenna.stockwatcher.provider

import com.ezike.tobenna.lib_stock_price.domain.model.Stock
import com.ezike.tobenna.stockwatcher.R
import com.ezike.tobenna.stockwatcher.StringResource
import com.ezike.tobenna.stockwatcher.model.DataUpdateState
import com.ezike.tobenna.stockwatcher.model.PercentageState
import com.ezike.tobenna.stockwatcher.model.StockModel
import com.ezike.tobenna.stockwatcher.model.ViewState
import javax.inject.Inject

class InitialStateProvider @Inject constructor() {

    fun viewState(): ViewState {
        val stockData = Stock.values().map { security ->
            StockModel(
                name = security.name,
                price = "--",
                percentState = PercentageState.Initial
            )
        }
        return ViewState(data = stockData)
    }

    fun dataUpdateState() = DataUpdateState(
        title = StringResource(R.string.connecting),
        icon = R.drawable.offline,
        showLiveBanner = true,
        showError = false
    )
}
