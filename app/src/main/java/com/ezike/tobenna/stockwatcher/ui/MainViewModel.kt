package com.ezike.tobenna.stockwatcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezike.tobenna.lib_stock_price.domain.usecase.ObserveStockData
import com.ezike.tobenna.lib_stock_price.domain.usecase.SubscribeStock
import com.ezike.tobenna.lib_stock_price.domain.usecase.UnsubscribeStock
import com.ezike.tobenna.stockwatcher.delayAfter
import com.ezike.tobenna.stockwatcher.provider.ViewStateProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val dataUpdateFlow = MutableStateFlow(stateProvider.initialLiveState)
    private val stockData = MutableStateFlow(stateProvider.initialViewState)

    val stocks get() = stockData.asStateFlow()
    val liveUpdate get() = dataUpdateFlow.asStateFlow()

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
                .map(stateProvider::createState)
                .collect { state ->
                    stockData.emit(state)
                    dataUpdateFlow.emit(stateProvider.dataState())
                }
        }
        viewModelScope.launch {
            observeStockData.dataState
                .map(stateProvider::pausedState)
                .collect(dataUpdateFlow::emit)
        }
    }

    override fun onCleared() {
        unsubscribeAll.execute()
        super.onCleared()
    }

    private companion object {
        const val EMISSION_COUNT = 5
        const val DELAY_MILLIS = 3000L
    }
}
