package com.androidevlinux.percy.UTXO.formatters

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class MonthSlashDayDateFormatter : ValueFormatter() {

    override fun getAxisLabel(unixSeconds: Float, axis: AxisBase): String {
        val date = Date(unixSeconds.toLong())
        val sdf = SimpleDateFormat("MM/dd", Locale.ENGLISH)
        return sdf.format(date)
    }
}
