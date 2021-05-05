package com.swtug.anticovid.profile

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.login.LoginFragment
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.profile.ProfileFragment
import com.swtug.anticovid.view.splashScreen.SplashScreenFragment
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify


@RunWith(AndroidJUnit4::class)
class LogoutNavigationTest {
    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        val mainScenario =
            launchFragmentInContainer<ProfileFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.profileFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
    }


    @Test
    fun testLogout() {

        onView(withId(R.id.logoutbutton)).perform(click())
        TestCase.assertEquals(navController.currentDestination?.id, R.id.loginFragment)
    }


}

