package com.swtug.anticovid.utils

import android.content.Context
import java.util.*


object LocaleHelper {

    fun changeLocale(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        val resources = context.resources
        val config = resources.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}