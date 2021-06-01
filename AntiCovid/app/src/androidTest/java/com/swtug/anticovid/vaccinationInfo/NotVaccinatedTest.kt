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
        onView(withId(R.id.textInput_vaccine_id)).perform(replaceText("test123"))

        val vaccination = Vaccination("test", Date(), Date(), "test")

        PreferencesRepo.saveVaccination(
            InstrumentationRegistry.getInstrumentation().targetContext,
            vaccination
        )

        Thread.sleep(500)
        onView(withId(R.id.button_add_vaccine)).perform(click())
        onView(withId(R.id.layout_vaccinated)).check(matches(isDisplayed()))
        onView(withId(R.id.layout_not_vaccinated)).check(matches(not(isDisplayed())))
    }
}

