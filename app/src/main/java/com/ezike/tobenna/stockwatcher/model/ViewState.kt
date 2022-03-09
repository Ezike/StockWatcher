package com.ezike.tobenna.stockwatcher.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.ezike.tobenna.stockwatcher.AppString
import com.ezike.tobenna.stockwatcher.R

data class StockModel(
    val name: String,
    val price: String,
    val percentState: PercentageState
)

data class PercentageState(
    val value: String,
    @DrawableRes val background: Int?,
    @DrawableRes val icon: Int?,
    @ColorRes val textColor: Int
) {
    companion object {
        val Initial
            get() = PercentageState(
                value = "--",
                icon = null,
                background = null,
                textColor = R.color.black
            )
    }
}

data class DataUpdateState(
    val title: AppString,
    @DrawableRes val icon: Int,
    val showLiveBanner: Boolean,
    val showError: Boolean
)

data class ViewState(
    val data: List<StockModel>
)
