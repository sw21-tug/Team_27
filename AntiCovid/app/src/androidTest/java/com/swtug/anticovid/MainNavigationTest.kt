package com.swtug.anticovid

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.swtug.anticovid.view.main.MainFragment
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@RunWith(AndroidJUnit4::class)
class MainNavigationTest {
    private lateinit var navController: NavController

    @Before
    fun setup() {
        navController = mock(NavController::class.java)

        val mainScenario = launchFragmentInContainer<MainFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun testNavigateFromHomeToProfile() {
        onView(withId(R.id.button_profile)).perform(click())
        verify(navController).navigate(
            R.id.action_mainFragment_to_profileFragment
        )
    }

    @Test
    fun testNavigateFromHomeToTestResults() {
        onView(withId(R.id.button_previous_test_reports)).perform(click())
        verify(navController).navigate(
            R.id.action_mainFragment_to_testResultFragment
        )
    }

    @Test
    fun testNavigateFromHomeToAddTest() {
        onView(withId(R.id.button_add_test_report)).perform(click())
        verify(navController).navigate(
            R.id.action_mainFragment_to_addTestReportFragment
        )
    }

    @Test
    fun testNavigateFromHomeToQRCode() {
        onView(withId(R.id.button_valid_qr_code)).perform(click())
        verify(navController).navigate(
            R.id.action_mainFragment_to_QRCodeFragment
        )
    }

    @Test
    fun testNavigateFromHomeToAdvancedFeatures() {
        onView(withId(R.id.button_advaced_features)).perform(click())
        verify(navController).navigate(
            R.id.action_mainFragment_to_advancedFeatureFragment
        )
    }

}

