package com.swtug.anticovid.statistics

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.Navigation

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.utils.TestUtils
import com.swtug.anticovid.view.statistics.StatisticFragment

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavigateToInformationAndRules{
    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        TestUtils.clearSharedPreferences(getContext())

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        val mainScenario =
            launchFragmentInContainer<StatisticFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    private fun getContext(): Context {
        return InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testNavigateToInformationAndRules() {
        onView(withId(R.id.fab_show_information_and_rules)).check(matches(isDisplayed()))
    }


}

