package com.ezike.tobenna.stockwatcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezike.tobenna.lib_stock_price.domain.usecase.ObserveStockData
import com.ezike.tobenna.lib_stock_price.domain.usecase.SubscribeStock
import com.ezike.tobenna.lib_stock_price.domain.usecase.UnsubscribeStock
import com.ezike.tobenna.stockwatcher.delayAfter
import com.ezike.tobenna.stockwatcher.model.ViewState
import com.ezike.tobenna.stockwatcher.provider.ViewStateProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeStockData: ObserveStockData,
    private val unsubscribeAll: UnsubscribeStock,
    private val subscribeAll: SubscribeStock,
    private val stateProvider: ViewStateProvider
) : ViewModel() {

    private val stockData = MutableStateFlow(stateProvider.initialStockModel)
    private val connectionState = MutableStateFlow(stateProvider.initialConnectionState)

    val state = combine(
        stockData,
        connectionState,
        ::ViewState
    ).distinctUntilChanged()

    init {
        subscribeToStocks()
        launchObservers()
    }

    private fun subscribeToStocks() {
        viewModelScope.launch {
            subscribeAll.execute()
        }
    }

    private fun launchObservers() {
        viewModelScope.launch {
            observeStockData.stockData
                .delayAfter(emissionCount = EMISSION_COUNT, delayMillis = DELAY_MILLIS)
                .map(stateProvider::createStockModel)
                .collect { state ->
                    stockData.emit(state)
                    connectionState.emit(stateProvider.connectedState())
                }
        }
        viewModelScope.launch {
            observeStockData.connectionState
                .map(stateProvider::disconnectedState)
                .collect(connectionState::emit)
        }
    }

    override fun onCleared() {
        unsubscribeAll.execute()
        super.onCleared()
    }

    private companion object {
        const val EMISSION_COUNT = 10
        const val DELAY_MILLIS = 3000L
    }
}
