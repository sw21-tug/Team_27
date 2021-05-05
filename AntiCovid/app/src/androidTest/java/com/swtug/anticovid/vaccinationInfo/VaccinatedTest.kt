package com.swtug.anticovid.vaccinationInfo

import android.content.Context
import androidx.core.os.bundleOf
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
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.TestUtils
import com.swtug.anticovid.models.Vaccination
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.vaccineInfo.VaccinatedFragment
import junit.framework.TestCase.assertEquals
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
        val vaccination = Vaccination("test", Date(), Date(), "test")

        PreferencesRepo.saveVaccination(
            InstrumentationRegistry.getInstrumentation().targetContext,
            vaccination
        )

        navController = mock(NavController::class.java)

        val mainScenario =
            launchFragmentInContainer<VaccinatedFragment>(themeResId = R.style.Theme_AntiCovid,
                fragmentArgs = bundleOf("vaccination" to vaccination))

        mainScenario.withFragment {
            Navigation.setViewNavController(requireView(), navController)
        }
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
        onView(withId(R.id.first_dose_date)).check(matches(withText(vaccination.firstDose.toString())))
        onView(withId(R.id.second_dose_date)).check(matches(withText(vaccination.secondDose.toString())))
        onView(withId(R.id.institution)).check(matches(withText(vaccination.manufacturor)))
    }

    @Test
    fun testClickAddVaccine() {
        onView(withId(R.id.btn_show_qr_code)).perform(click())
        verify(navController).navigate(R.id.action_vaccinatedFragment_to_QRCodeFragment)
    }
}

