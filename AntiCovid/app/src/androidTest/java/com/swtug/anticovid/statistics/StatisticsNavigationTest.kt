package com.swtug.anticovid.statistics

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.TestUtils
import com.swtug.anticovid.view.advancedFeatures.AdvancedFeatureFragment
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith




@RunWith(AndroidJUnit4::class)
class StatisticsNavigationTest {
    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        val mainScenario =
            launchFragmentInContainer<AdvancedFeatureFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.advancedFeatureFragment)
            Navigation.setViewNavController(requireView(), navController)
        }

        TestUtils.clearSharedPreferences(InstrumentationRegistry.getInstrumentation().targetContext)
    }


    @Test
    fun testNavigateFromHomeToStatistic() {

        TestCase.assertEquals(navController.currentDestination?.id, R.id.advancedFeatureFragment)
        onView(withId(R.id.button_show_statistics)).perform(click())
        TestCase.assertEquals(navController.currentDestination?.id, R.id.statisticFragment)
        //onView(withId(R.id.button_show_statistics)).perform(click())
        //TestCase.assertEquals(navController.currentDestination?.id, R.id.statisticFragment)

    }
}