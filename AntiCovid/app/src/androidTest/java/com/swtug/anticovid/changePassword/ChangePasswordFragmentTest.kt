package com.swtug.anticovid.profile

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.utils.TestUtils
import com.swtug.anticovid.view.changePassword.ChangePassword
import com.swtug.anticovid.view.profile.ProfileFragment
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
class ChangePasswordFragmentTest {
    private lateinit var navController: NavController
    private val testUser: User = User(
        -1,
        "Max",
        "Mustermann",
        "test@test.com",
        "Muster 15",
        "1234567890",
        "+436605566777",
        "testtest"
    )

    @Before
    fun setup() {
        PreferencesRepo.saveUser(
            InstrumentationRegistry.getInstrumentation().targetContext,
            testUser
        )


        navController = Mockito.mock(NavController::class.java)

        val mainScenario =
            launchFragmentInContainer<ChangePassword>(
                themeResId = R.style.Theme_AntiCovid,

                )

        mainScenario.withFragment {
            Navigation.setViewNavController(requireView(), navController)
        }
    }



    @Test
    fun testOldPasswordIncorrect() {
        onView(ViewMatchers.withId(R.id.editTextOldPassword)).perform(ViewActions.replaceText("WrongPassword"))
        onView(ViewMatchers.withId(R.id.editTextOldPassword)).check(matches(hasErrorText("Wrong Password")));

    }

    @Test
    fun testNewPasswordToShort() {
        onView(ViewMatchers.withId(R.id.editTextNewPassword)).perform(ViewActions.replaceText("abc"))
        onView(ViewMatchers.withId(R.id.editTextNewPassword)).check(matches(hasErrorText("Password does not meet the criteria")));

    }
    @Test
    fun testNewPasswordSameAsOldOne() {
        onView(ViewMatchers.withId(R.id.editTextNewPassword)).perform(ViewActions.replaceText(testUser.password))
        onView(ViewMatchers.withId(R.id.editTextNewPassword)).check(matches(hasErrorText("Password does not meet the criteria")));

    }
    @Test
    fun testPasswordsDoNotMatch() {
        onView(ViewMatchers.withId(R.id.editTextNewPassword)).perform(ViewActions.replaceText("testtest1"))
        onView(ViewMatchers.withId(R.id.editTextRepeatPassword)).perform(ViewActions.replaceText("testtest2"))
        onView(ViewMatchers.withId(R.id.editTextRepeatPassword)).check(matches(hasErrorText("Passwords do not match")));

    }


    @After
    fun tearDown() {
        TestUtils.clearSharedPreferences(InstrumentationRegistry.getInstrumentation().targetContext)
    }
}
