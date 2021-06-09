package com.swtug.anticovid.vaccinationInfo

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
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
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.*


@RunWith(AndroidJUnit4::class)
class VaccinatedTest {
    private lateinit var navController: NavController
    private val vaccination = Vaccination("test@test.com","test", Date().toString(), Date().toString(), "test")

    @Before
    fun setup() {
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
    @Ignore
    fun testVaccinatedScreenDisplaysCorrectInformation() {
        onView(withId(R.id.textInput_manufacturer)).perform(ViewActions.replaceText(vaccination.manufacturor))
        onView(withId(R.id.first_dose_date)).perform(ViewActions.replaceText(vaccination.firstDose))
        onView(withId(R.id.second_dose_date)).perform(ViewActions.replaceText(vaccination.secondDose))
        onView(withId(R.id.institution)).perform(ViewActions.replaceText(vaccination.institution))
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())


        onView(withId(R.id.textInput_manufacturer)).check(matches(withText(vaccination.manufacturor)))
        onView(withId(R.id.first_dose_date)).check(matches(withText(vaccination.firstDose)))
        onView(withId(R.id.second_dose_date)).check(matches(withText(vaccination.secondDose)))
        onView(withId(R.id.institution)).check(matches(withText(vaccination.institution)))
    }
}

