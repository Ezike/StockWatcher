package com.ezike.tobenna.stockwatcher

import com.ezike.tobenna.stockwatcher.formatter.CurrencyFormatter
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.text.Typography.nbsp

class CurrencyFormatterTest {

    @Test
    fun `when currency is formatted then the string is returned`() {
        // Given
        val formatter = NumberFormat.getCurrencyInstance(Locale.GERMAN) as DecimalFormat
        val currencyFormatter = CurrencyFormatter(decimalFormat = formatter)

        // when
        val price = currencyFormatter.format(800.13322)

        // then
        assertEquals("800,13$nbsp€", price)
    }
}
