package com.ezike.tobenna.lib_stock_price.provider

import java.util.concurrent.TimeUnit
import javax.inject.Inject

public class ElapsedTimeProvider @Inject constructor() {

    public fun elapsedTime(past: Long): String {
        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(past)
        if (seconds < 2) {
            return "1 sec ago"
        } else if (seconds in 2..60) return "$seconds secs ago"

        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(past)
        if (minutes < 2) {
            return "1 minute ago"
        } else if (minutes in 2..60) return "$minutes minutes ago"

        val hours: Long = TimeUnit.MILLISECONDS.toHours(past)
        if (hours < 2) {
            return "1 hour ago"
        } else if (hours in 2..23) return "$hours hours ago"

        val days: Long = TimeUnit.MILLISECONDS.toDays(past)
        if (days < 2) {
            return "1 day ago"
        } else if (days in 2..31) return "$days days ago"

        val months: Long = days / 30
        return when {
            months < 2 -> "$months month ago"
            else -> "$months months ago"
        }
    }
}
