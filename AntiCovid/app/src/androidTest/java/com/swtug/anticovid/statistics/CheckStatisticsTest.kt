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

import androidx.test.espresso.assertion.ViewAssertions.matches
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
class CheckStatisticsTest{
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
    fun testCheckReceivedData() {
        Thread.sleep(2000)
        val compareString = TestUtils.getResoureceString(getContext(), R.string.statistic_error_message)
        onView(withId(R.id.activevalue)).check(matches(isEditTextCorrectSetTo(compareString)))
        onView(withId(R.id.casestodayvalue)).check(matches(isEditTextCorrectSetTo(compareString)))
        onView(withId(R.id.deathtodayvalue)).check(matches(isEditTextCorrectSetTo(compareString)))
        onView(withId(R.id.recoveredtodayvalue)).check(matches(isEditTextCorrectSetTo(compareString)))
        onView(withId(R.id.totalcasesvalue)).check(matches(isEditTextCorrectSetTo(compareString)))
        onView(withId(R.id.totaldeathvalue)).check(matches(isEditTextCorrectSetTo(compareString)))
        onView(withId(R.id.totalrecoveredvalue)).check(matches(isEditTextCorrectSetTo(compareString)))
        onView(withId(R.id.totaltestedvalue)).check(matches(isEditTextCorrectSetTo(compareString)))
        onView(withId(R.id.populationvalue)).check(matches(isEditTextCorrectSetTo(compareString)))
    }

    private fun isEditTextCorrectSetTo(content: String):
            Matcher<View> = object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description?) {
            description?.appendText("Checks if responded Data is not <$content>")
        }

        override fun matchesSafely(view: View?): Boolean =
            if(view == null) {
                false
            } else {
                if(view is EditText) {
                    view.text.toString() != content
                } else if(view is TextView) {
                    view.text.toString() != content
                } else {
                    false
                }
            }
    }
}

