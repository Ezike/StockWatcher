package com.ezike.tobenna.stockwatcher

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <R> Flow<R>.delayAfter(emissionCount: Int, delayMillis: Long) = flow {
    var i = 1
    collect { value ->
        if (i == emissionCount) {
            emit(value)
            delay(delayMillis)
        } else {
            i++
            emit(value)
        }
    }
}

fun ViewGroup.inflate(layout: Int): View {
    val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    return layoutInflater.inflate(layout, this, false)
}

fun TextView.drawable(
    left: Int = 0,
    top: Int = 0,
    right: Int = 0,
    bottom: Int = 0
) {
    this.setCompoundDrawablesWithIntrinsicBounds(
        left, top, right, bottom
    )
}

var TextView.string: AppString
    get() = StringLiteral(
        value = this.text.toString()
    )
    set(appString) {
        text = string(context, appString)
    }

private fun string(
    context: Context,
    appString: AppString
): String = when (appString) {
    is ParamString -> context.getString(
        appString.res,
        *appString.params.toTypedArray()
    )
    is StringResource -> context.getString(
        appString.res
    )
    is StringLiteral -> appString.value
}

infix fun <A, B, C> Pair<A, B>.then(that: C): Triple<A, B, C> =
    Triple(this.first, this.second, that)
