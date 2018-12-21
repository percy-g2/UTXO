package com.androidevlinux.percy.UTXO.formatters

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class MonthSlashDayDateFormatter : IAxisValueFormatter {

    override fun getFormattedValue(unixSeconds: Float, axis: AxisBase): String {
        val date = Date(unixSeconds.toLong())
        val sdf = SimpleDateFormat("MM/dd", Locale.ENGLISH)
        return sdf.format(date)
    }
}
