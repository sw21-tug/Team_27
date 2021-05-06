package com.swtug.anticovid

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {

    fun getLocalDateTimeFormatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    }

    fun getStringFromDate(date: LocalDateTime): String {
        return date.format(getLocalDateTimeFormatter())
    }
}