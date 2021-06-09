package com.swtug.anticovid.models

import java.io.Serializable
import java.time.LocalDateTime

data class TestReport(
    var email: String,
    var testdate: LocalDateTime,
    var testresult: Boolean,
    val validdate: LocalDateTime
) : Serializable
