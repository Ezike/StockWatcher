package com.ezike.tobenna.stockwatcher

import androidx.annotation.StringRes

sealed interface AppString

@JvmInline
value class StringLiteral(
    val value: String
) : AppString

@JvmInline
value class StringResource(
    @StringRes val res: Int
) : AppString

data class ParamString(
    @StringRes val res: Int,
    val params: List<Any>
) : AppString {
    constructor(
        @StringRes res: Int,
        vararg param: Any
    ) : this(
        res = res,
        params = param.toList()
    )
}
