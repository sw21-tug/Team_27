package com.swtug.anticovid.startUp

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.utils.TestUtils
import com.swtug.anticovid.view.splashScreen.SplashScreenFragment
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TermsOfUseNavigationTest {
    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        TestUtils.clearSharedPreferences(InstrumentationRegistry.getInstrumentation().targetContext)

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
        TestCase.assertEquals(navController.currentDestination?.id, R.id.loginFragment)
    }
}

