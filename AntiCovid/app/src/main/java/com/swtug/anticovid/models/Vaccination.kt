package com.swtug.anticovid.models

import java.io.Serializable
import java.util.*

data class Vaccination(
    var manufacturor: String,
    var firstDose: Date,
    var secondDose: Date,
    val institution: String
) : Serializable
