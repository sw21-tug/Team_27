package com.swtug.anticovid

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.utils.TestUtils
import com.swtug.anticovid.utils.selectTabAtPosition
import com.swtug.anticovid.view.main.MainFragment
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock


@RunWith(AndroidJUnit4::class)
class MainNavigationTest {
    private var navController: NavController = mock(NavController::class.java)

    private val testUser: User = User(-1,
        "Max",
        "Mustermann",
        "test@test.com",
        "Muster 15",
        "None",
        "+436605566777",
        "testtest")


    @Before
    fun setup() {
        PreferencesRepo.saveUser(InstrumentationRegistry.getInstrumentation().targetContext, testUser)

        val mainScenario = launchFragmentInContainer<MainFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @After
    fun tearDown() {
        TestUtils.clearSharedPreferences(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    @Ignore
    fun testNavigateFromHomeToProfile() {
        onView(withId(R.id.pager)).perform(swipeLeft())
        onView(withId(R.id.pager)).perform(swipeLeft())
        onView(withId(R.id.pager)).perform(swipeLeft())
        onView(withId(R.id.toggle_group_language)).check(matches(isDisplayed()))
    }

    @Test
    @Ignore
    fun testNavigateFromHomeToVaccinationInformation() {
        onView(withId(R.id.pager)).perform(swipeLeft())
        onView(withId(R.id.pager)).perform(swipeLeft())
        onView(withId(R.id.textInput_manufacturer)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigateFromHomeToTestResults() {
        onView(withId(R.id.pager)).perform(swipeLeft())
        onView(withId(R.id.fab_add_test_report)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigateFromHomeToAdvancedFeatures() {
        onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(0))
        onView(withId(R.id.active_cases)).check(matches(isDisplayed()))
    }

}

