package com.swtug.anticovid.vaccinationInfo

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.models.Vaccination
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.utils.TestUtils
import com.swtug.anticovid.view.main.MainFragment
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.*


@RunWith(AndroidJUnit4::class)
class VaccinatedTest {
    private lateinit var navController: NavController

    @Before
    fun setup() {
        val vaccination = Vaccination("test", Date().toString(), Date().toString(), "test")

        PreferencesRepo.saveVaccination(
            InstrumentationRegistry.getInstrumentation().targetContext,
            vaccination
        )

        navController = mock(NavController::class.java)

        val mainScenario = launchFragmentInContainer<MainFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.pager)).perform(ViewActions.swipeLeft())
        onView(withId(R.id.pager)).perform(ViewActions.swipeLeft())
    }

    @After
    fun tearDown() {
        TestUtils.clearSharedPreferences(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun testVaccinatedScreenDisplaysCorrectInformation() {
        val vaccination = PreferencesRepo.getVaccination(
            InstrumentationRegistry.getInstrumentation().targetContext
        )

        vaccination!!

        onView(withId(R.id.textInput_manufacturer)).check(matches(withText(vaccination.manufacturor)))
        onView(withId(R.id.first_dose_date)).check(matches(withText(vaccination.firstDose)))
        onView(withId(R.id.second_dose_date)).check(matches(withText(vaccination.secondDose)))
        onView(withId(R.id.institution)).check(matches(withText(vaccination.manufacturor)))
    }
}

