package com.swtug.anticovid.profile

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.swtug.anticovid.MainActivity
import com.swtug.anticovid.TestUtils
import com.swtug.anticovid.repositories.PreferencesRepo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.System.exit
import java.util.*


@RunWith(AndroidJUnit4::class)
class LoadLanguageTest {
    @get:Rule
    var launchActivityRule = ActivityTestRule(
        MainActivity::class.java, false, false
    )

    @Before
    fun setup() {

        PreferencesRepo.saveLocale(ApplicationProvider.getApplicationContext(), Locale.SIMPLIFIED_CHINESE)

        launchActivityRule.launchActivity(Intent(InstrumentationRegistry.getInstrumentation().targetContext, MainActivity::class.java))

    }

    @After
    fun tearDown(){
        TestUtils.clearSharedPreferences(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testLanguageChange() {

        assert((PreferencesRepo.getLocale(ApplicationProvider.getApplicationContext()))
                == Locale.SIMPLIFIED_CHINESE)
        Thread.sleep(3500)
        Espresso.pressBackUnconditionally()

    }


}
