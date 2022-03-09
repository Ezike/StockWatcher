package com.ezike.tobenna.lib_stock_price

import android.os.SystemClock

public interface TimeSource {
    public val time: Long

    public companion object : TimeSource {
        override val time: Long
            get() = SystemClock.elapsedRealtime()
    }
}
