package com.swtug.anticovid

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.utils.LocaleHelper
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun attachBaseContext(newBase: Context) {
        val locale = PreferencesRepo.getLocale(newBase)
        super.attachBaseContext(LocaleHelper.changeLocale(newBase, locale))
    }
}