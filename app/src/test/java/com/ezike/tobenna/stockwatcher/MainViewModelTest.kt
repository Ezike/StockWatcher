package com.ezike.tobenna.stockwatcher

import app.cash.turbine.test
import com.ezike.tobenna.lib_stock_price.domain.model.EventData
import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import com.ezike.tobenna.lib_stock_price.domain.usecase.ObserveStockData
import com.ezike.tobenna.lib_stock_price.domain.usecase.SubscribeStock
import com.ezike.tobenna.lib_stock_price.domain.usecase.UnsubscribeStock
import com.ezike.tobenna.stockwatcher.model.ConnectionState
import com.ezike.tobenna.stockwatcher.model.PercentageState
import com.ezike.tobenna.stockwatcher.model.StockModel
import com.ezike.tobenna.stockwatcher.model.ViewState
import com.ezike.tobenna.stockwatcher.provider.ViewStateProvider
import com.ezike.tobenna.stockwatcher.ui.MainViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

internal class MainViewModelTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Test
    fun `when data is loaded then the correct view state is returned`() = runTest {
        // Given
        val stockObserver = mock<ObserveStockData> {
            on { stockData } doReturn flowOf(
                listOf(
                    StockData(
                        name = "NETFLIX",
                        id = "US213444",
                        price = 2333.55,
                        openPrice = null
                    )
                )
            )
        }

        val stateProvider = mock<ViewStateProvider> {
            on { initialStockModel } doReturn listOf(
                    StockModel(
                        name = "TRIVAGO",
                        price = "--",
                        percentState = PercentageState(
                            value = "",
                            background = 0,
                            icon = 9,
                            textColor = 89
                        )
                )
            )
            on { initialConnectionState } doReturn ConnectionState(
                title = StringLiteral("Connecting ..."),
                icon = 2,
                isError = false
            )
            on {
                createStockModel(
                    listOf(
                        StockData(
                            name = "NETFLIX",
                            "US213444",
                            price = 2333.55,
                            openPrice = null
                        )
                    )
                )
            } doReturn  listOf(
                    StockModel(
                        name = "NETFLIX", price = "2333.55 $",
                        percentState = PercentageState(
                            value = "8",
                            background = 2,
                            icon = 1,
                            textColor = 34
                        )
                    )
                )
            on { connectedState() } doReturn ConnectionState(
                title = StringLiteral("Live"),
                icon = 2,
                isError = false
            )
            on {
                disconnectedState(EventData(time = 3L, isError = true))
            } doReturn ConnectionState(
                title = StringLiteral("Last updated: 2 mins ago"),
                icon = 2,
                isError = true
            )
        }

        val mainViewModel = createViewModel(
            observeStockData = stockObserver,
            stateProvider = stateProvider
        )

        // when
        val state = mainViewModel.state

        // then
        state.test {
            assertEquals(
                ViewState(
                    data = listOf(
                        StockModel(
                            name = "TRIVAGO", price = "--",
                            percentState = PercentageState(
                                value = "8",
                                background = 2,
                                icon = 1,
                                textColor = 34
                            )
                        )
                    ),
                    ConnectionState(
                        title = StringLiteral("Connecting ..."),
                        icon = 2,
                        isError = false
                    )
                ),
                awaitItem()
            )
        }
    }

    private fun createViewModel(
        observeStockData: ObserveStockData = mock(),
        unsubscribeAll: UnsubscribeStock = mock(),
        subscribeAll: SubscribeStock = mock(),
        stateProvider: ViewStateProvider = mock()
    ) = MainViewModel(
        observeStockData = observeStockData,
        unsubscribeAll = unsubscribeAll,
        subscribeAll = subscribeAll,
        stateProvider = stateProvider
    )
}
