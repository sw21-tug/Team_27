package com.swtug.anticovid.termsOfUse

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.models.Vaccination
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.splashScreen.SplashScreenFragment
import com.swtug.anticovid.view.vaccineInfo.NotVaccinatedFragment
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.*


@RunWith(AndroidJUnit4::class)
class TermsOfUseNavigationTest {
    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        clearSharedPreferences()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        val mainScenario =
            launchFragmentInContainer<SplashScreenFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun testTermsOfUseNotAccepted() {
        PreferencesRepo.saveAcceptTermsOfUse(InstrumentationRegistry.getInstrumentation().targetContext, false)
        Thread.sleep(3500);
        TestCase.assertEquals(navController.currentDestination?.id, R.id.termsOfUseFragment)
    }

    @Test
    fun testTermsOfUseAccepted() {
        PreferencesRepo.saveAcceptTermsOfUse(InstrumentationRegistry.getInstrumentation().targetContext, true)
        Thread.sleep(3500);
        TestCase.assertEquals(navController.currentDestination?.id, R.id.mainFragment)
    }

    private fun clearSharedPreferences() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val preferences = context.getSharedPreferences("ANTI_COVID_APP", Context.MODE_PRIVATE)
        preferences.edit()
            .clear()
            .commit()
    }
}

