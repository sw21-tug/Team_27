package com.swtug.anticovid

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.swtug.anticovid.login.LoginFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class NavigationLoginTest {
    private lateinit var navController: NavController

    @Before
    fun setup() {
        navController = mock(NavController::class.java)

        val mainScenario =
            launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun testNavigateFromHomeToProfile() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(ViewActions.typeText("test@test.com"))
        onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText("0123456789"))
        onView(isRoot()).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.buttonLogin)).perform(click())
        Thread.sleep(3000)
        verify(navController).navigate(
            R.id.action_loginFragment_to_mainFragment
        )
    }
}

