package com.ezike.tobenna.stockwatcher.provider

import com.ezike.tobenna.lib_stock_price.domain.model.EventData
import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import com.ezike.tobenna.lib_stock_price.provider.ElapsedTimeProvider
import com.ezike.tobenna.stockwatcher.ParamString
import com.ezike.tobenna.stockwatcher.R
import com.ezike.tobenna.stockwatcher.StringResource
import com.ezike.tobenna.stockwatcher.mapper.StockDataMapper
import com.ezike.tobenna.stockwatcher.model.ConnectionState
import com.ezike.tobenna.stockwatcher.model.StockModel
import javax.inject.Inject

class ViewStateProvider @Inject constructor(
    private val stockModelMapper: StockDataMapper,
    private val elapsedTimeProvider: ElapsedTimeProvider,
    private val initialStateProvider: InitialStateProvider
) {
    val initialStockModel: List<StockModel>
        get() = initialStateProvider.viewState()

    val initialConnectionState: ConnectionState
        get() = initialStateProvider.connectionState()

    fun createStockModel(
        stockData: List<StockData>
    ): List<StockModel> = stockModelMapper.toStockModel(stockData)

    fun connectedState() = ConnectionState(
        title = StringResource(R.string.live_updates),
        icon = R.drawable.success,
        isError = false
    )

    fun disconnectedState(data: EventData): ConnectionState {
        val icon = if (data.isError) {
            R.drawable.error
        } else R.drawable.offline

        return ConnectionState(
            title = ParamString(
                R.string.last_updated_at,
                elapsedTimeProvider.elapsedTime(data.time)
            ),
            icon = icon,
            isError = data.isError
        )
    }
}
