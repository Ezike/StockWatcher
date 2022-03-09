package com.ezike.tobenna.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class StockRemoteModel(
    @Json(name = "isin") val isin: String,
    @Json(name = "price") val price: Double,
    @Json(name = "open") val openPrice: Double
)
