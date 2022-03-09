package com.ezike.tobenna.lib_stock_price.domain.model

public sealed interface StockResponse {
    public val stockList: List<StockData>

    public data class Data(
        override val stockList: List<StockData>
    ) : StockResponse

    public sealed interface Inactive : StockResponse {
        public val message: String?
    }

    public data class Closed(
        override val stockList: List<StockData>,
        override val message: String
    ) : Inactive

    public data class Error(
        override val stockList: List<StockData>,
        override val message: String?
    ) : Inactive
}
