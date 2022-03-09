package com.ezike.tobenna.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class SubscribeData(
    @Json(name = "subscribe") val isin: String
)
