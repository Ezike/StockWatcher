package com.ezike.tobenna.stockwatcher.provider

import com.ezike.tobenna.lib_stock_price.domain.model.EventData
import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import com.ezike.tobenna.lib_stock_price.provider.ElapsedTimeProvider
import com.ezike.tobenna.stockwatcher.ParamString
import com.ezike.tobenna.stockwatcher.R
import com.ezike.tobenna.stockwatcher.StringResource
import com.ezike.tobenna.stockwatcher.mapper.StockDataMapper
import com.ezike.tobenna.stockwatcher.model.DataUpdateState
import com.ezike.tobenna.stockwatcher.model.ViewState
import javax.inject.Inject

class ViewStateProvider @Inject constructor(
    private val stockModelMapper: StockDataMapper,
    private val elapsedTimeProvider: ElapsedTimeProvider,
    private val initialStateProvider: InitialStateProvider
) {
    val initialViewState: ViewState
        get() = initialStateProvider.viewState()

    val initialLiveState: DataUpdateState
        get() = initialStateProvider.dataUpdateState()

    fun createState(
        stockData: List<StockData>
    ): ViewState {
        val stockList = stockModelMapper.toStockModel(stockData)
        return ViewState(data = stockList)
    }

    fun dataState() =
        DataUpdateState(
            title = StringResource(R.string.live_updates),
            icon = R.drawable.success,
            showLiveBanner = true,
            showError = false
        )

    fun pausedState(data: EventData): DataUpdateState {
        val icon = if (data.isError) {
            R.drawable.error
        } else R.drawable.offline

        return DataUpdateState(
            title = ParamString(
                R.string.last_updated_at,
                elapsedTimeProvider.elapsedTime(data.time)
            ),
            icon = icon,
            showLiveBanner = true,
            showError = data.isError
        )
    }
}
