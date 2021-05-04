package com.swtug.anticovid.vaccinationInfo

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.TestUtils
import com.swtug.anticovid.models.Vaccination
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.main.MainFragment
import com.swtug.anticovid.view.main.MainFragmentDirections
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.*


@RunWith(AndroidJUnit4::class)
class VaccinationInfoNavigationTest {
    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        val mainScenario =
            launchFragmentInContainer<MainFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.mainFragment)
            Navigation.setViewNavController(requireView(), navController)
        }

        TestUtils.clearSharedPreferences(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @After
    fun tearDown() {
        TestUtils.clearSharedPreferences(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun testClickVaccineInfoUserVaccinated() {
        val vaccination = Vaccination("test", Date(), Date(), "test")

        PreferencesRepo.saveVaccination(
            InstrumentationRegistry.getInstrumentation().targetContext,
            vaccination
        )
        onView(withId(R.id.button_vaccine_info)).perform(click())
        assertEquals(navController.currentDestination?.id, R.id.vaccinatedFragment)

    }

    @Test
    fun testClickVaccineInfoUserNotVaccinated() {
        onView(withId(R.id.button_vaccine_info)).perform(click())
        assertEquals(navController.currentDestination?.id, R.id.notVaccinatedFragment)

    }
}

