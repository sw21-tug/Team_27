package com.swtug.anticovid.models

import java.io.Serializable
import java.time.LocalDateTime

data class TestReport(
    var email: String,
    var testdate: String,
    var testresult: Boolean,
    val validdate: String
) : Serializable
