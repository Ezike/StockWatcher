package com.ezike.tobenna.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class UnsubscribeData(
    @Json(name = "unsubscribe") val isin: String
)
