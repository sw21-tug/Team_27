package com.swtug.anticovid.vaccinationInfo

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.models.Vaccination
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.utils.TestUtils
import com.swtug.anticovid.view.main.MainFragment
import junit.framework.Assert.assertEquals
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.util.*


@RunWith(AndroidJUnit4::class)
class NotVaccinatedTest {
    private lateinit var navController: NavController

    @Before
    fun setup() {
        navController = Mockito.mock(NavController::class.java)

        val mainScenario = launchFragmentInContainer<MainFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.pager)).perform(ViewActions.swipeLeft())
        onView(withId(R.id.pager)).perform(ViewActions.swipeLeft())
        onView(withId(R.id.pager)).perform(ViewActions.swipeLeft())
    }

    @After
    fun tearDown() {
        TestUtils.clearSharedPreferences(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testAddVaccineWithID() {
        val expectedVaccination = Vaccination("test", Date().toString(), Date().toString(), "test")

        onView(withId(R.id.textInput_manufacturer)).perform(replaceText(expectedVaccination.manufacturor))
        onView(withId(R.id.first_dose_date)).perform(replaceText(expectedVaccination.firstDose))
        onView(withId(R.id.second_dose_date)).perform(replaceText(expectedVaccination.secondDose))
        onView(withId(R.id.institution)).perform(replaceText(expectedVaccination.institution))

        onView(withId(R.id.button_add_vaccine)).perform(click())

        val vaccination = PreferencesRepo.getVaccination(
            InstrumentationRegistry.getInstrumentation().targetContext
        )

        vaccination!!

        assertEquals("Manufacturer not the same!", vaccination.manufacturor,expectedVaccination.manufacturor)
        assertEquals("First dose date not the same!", vaccination.firstDose,expectedVaccination.firstDose)
        assertEquals("Second dose date not the same!", vaccination.secondDose,expectedVaccination.secondDose)
        assertEquals("Institution not the same!", vaccination.institution,expectedVaccination.institution)

    }
}

