package com.swtug.anticovid.profile

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.repositories.IPreferencesRepo
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.profile.ProfileFragment
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.*

@RunWith(AndroidJUnit4::class)
class LanguageTest {
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


    fun tearDown(){

    }

    @Test
    fun testLanguageChange() {

        Espresso.onView(ViewMatchers.withId(R.id.btn_chinese)).perform(ViewActions.click())
        assert(PreferencesRepo.getLocale(ApplicationProvider.getApplicationContext())
                == Locale.SIMPLIFIED_CHINESE)
    }


}