package com.swtug.anticovid.testReports

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.models.User
import com.swtug.anticovid.R
import com.swtug.anticovid.utils.DateTimeUtils
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.utils.TestUtils
import com.swtug.anticovid.view.addTestReport.AddTestReportFragment
import com.swtug.anticovid.view.addTestReport.AddTestReportFragment.Companion.VALID_DAYS_POSITIVE
import com.swtug.anticovid.view.addTestReport.AddTestReportFragment.Companion.VALID_HOURS_NEGATIVE
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime


@RunWith(AndroidJUnit4::class)
class AddTestReportFirebaseTest {
    private lateinit var navController: TestNavHostController
    private val testUser: User = User(-1,
        "Max",
        "Mustermann",
        "test@test.com",
        "Muster 15",
        "None",
        "+436605566777",
        "testtest")

    private val testTimeStamp: LocalDateTime = LocalDateTime.of(2021, 5, 10, 12, 13)
    private val correctTestDate: String = DateTimeUtils.getStringFromDate(testTimeStamp)

    @Before
    fun setup() {
        PreferencesRepo.saveUser(getContext(), testUser)

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        val mainScenario =
            launchFragmentInContainer<AddTestReportFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            navController.setGraph(R.navigation.nav_graph)
         //   navController.setCurrentDestination(R.id.addTestReportFragment)
            Navigation.setViewNavController(requireView(), navController)

            setTestAndValidDate(testTimeStamp)
        }
    }

    @After
    fun tearDown() {
        TestUtils.clearSharedPreferences(getContext())
    }

    private fun getContext() : Context {
        return InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testCorrectUserInfoShown() {
        val fullUserName = "${testUser.name} ${testUser.surname}"
        onView(withId(R.id.text_user_name_info)).check(matches(isEditTextCorrectSetTo(fullUserName)))
    }


    @Test
    fun testNegativeValidationDate() {
        onView(withId(R.id.radio_test_negative)).perform(click())

        val correctValidDate = DateTimeUtils.getStringFromDate(testTimeStamp.plusHours(VALID_HOURS_NEGATIVE))
        onView(withId(R.id.text_test_date)).check(matches(isEditTextCorrectSetTo(correctTestDate)))
        onView(withId(R.id.text_valid_date)).check(matches(isEditTextCorrectSetTo(correctValidDate)))
    }

    @Test
    fun testNegativeValidationText() {
        onView(withId(R.id.radio_test_negative)).perform(click())

        val correctInfo = TestUtils.getResoureceString(getContext(), R.string.negative_test_valid_info)
        onView(withId(R.id.validity_info)).check(matches(isEditTextCorrectSetTo(correctInfo)))
    }

    @Test
    fun testPositiveValidationDate() {
        onView(withId(R.id.radio_test_positive)).perform(click())

        val correctValidDate = DateTimeUtils.getStringFromDate(testTimeStamp.plusDays(VALID_DAYS_POSITIVE))
        onView(withId(R.id.text_test_date)).check(matches(isEditTextCorrectSetTo(correctTestDate)))
        onView(withId(R.id.text_valid_date)).check(matches(isEditTextCorrectSetTo(correctValidDate)))
    }

    @Test
    fun testPositiveValidationText() {
        onView(withId(R.id.radio_test_positive)).perform(click())

        val correctInfo = TestUtils.getResoureceString(getContext(), R.string.positive_test_valid_info)
        onView(withId(R.id.validity_info)).check(matches(isEditTextCorrectSetTo(correctInfo)))
    }

    private fun isEditTextCorrectSetTo(content: String):
            Matcher<View> = object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description?) {
            description?.appendText("Has Edit Text set the correct date to <$content>")
        }

        override fun matchesSafely(view: View?): Boolean =
            if(view == null) {
                false
            } else {
                if(view is EditText) {
                    view.text.toString() == content
                } else if(view is TextView) {
                    view.text.toString() == content
                } else {
                    false
                }
            }
    }

}

