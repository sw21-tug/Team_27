package com.swtug.anticovid.profile

import android.content.Context
import androidx.appcompat.view.menu.ActionMenuItem
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.swtug.anticovid.R
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.utils.TestUtils
import com.swtug.anticovid.view.main.MainFragment
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LogoutNavigationTest {
    private lateinit var navController: TestNavHostController
    private lateinit var mainScenario: FragmentScenario<MainFragment>

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

        mainScenario = launchFragmentInContainer<MainFragment>(themeResId = R.style.Theme_AntiCovid)

        mainScenario.withFragment {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.mainFragment)
            Navigation.setViewNavController(requireView(), navController)
        }


    }

    @After
    fun tearDown(){
        TestUtils.clearSharedPreferences(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun testLogout() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val addMenuItem = ActionMenuItem(context, 0, R.id.logout, 0, 0, null)

        //Call onOptionsItemSelected with the dummy menu item
        mainScenario.onFragment { fragment ->
            fragment.onOptionsItemSelected(addMenuItem)
        }
        TestCase.assertEquals(navController.currentDestination?.id, R.id.loginFragment)
    }
}

