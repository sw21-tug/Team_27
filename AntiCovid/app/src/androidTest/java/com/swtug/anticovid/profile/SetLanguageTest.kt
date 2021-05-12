package com.swtug.anticovid.profile

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.TestUtils
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.profile.ProfileFragment
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class SetLanguageTest {
    private lateinit var navController: TestNavHostController

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

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        val mainScenario =
            launchFragmentInContainer<ProfileFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.profileFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @After
    fun tearDown(){
        TestUtils.clearSharedPreferences(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun testLanguageChange() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_chinese)).perform(ViewActions.click())
        assert(PreferencesRepo.getLocale(InstrumentationRegistry.getInstrumentation().targetContext)
                == Locale.SIMPLIFIED_CHINESE)
    }






}