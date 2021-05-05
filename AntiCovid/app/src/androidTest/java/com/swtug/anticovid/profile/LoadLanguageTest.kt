package com.swtug.anticovid.profile

import android.app.Activity
import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.swtug.anticovid.MainActivity
import com.swtug.anticovid.R
import com.swtug.anticovid.TestUtils
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.profile.ProfileFragment
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class LoadLanguageTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {

        PreferencesRepo.saveLocale(ApplicationProvider.getApplicationContext(), Locale.SIMPLIFIED_CHINESE)


    }

    @After
    fun tearDown(){
        TestUtils.clearSharedPreferences(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testLanguageChange() {
        assert((PreferencesRepo.getLocale(ApplicationProvider.getApplicationContext()))
                == Locale.SIMPLIFIED_CHINESE)
    }
}