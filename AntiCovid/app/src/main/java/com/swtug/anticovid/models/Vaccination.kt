package com.swtug.anticovid.models

import java.io.Serializable
import java.util.*

data class Vaccination(
    var manufacturor: String,
    var firstDose: String,
    var secondDose: String,
    val institution: String
) : Serializable
