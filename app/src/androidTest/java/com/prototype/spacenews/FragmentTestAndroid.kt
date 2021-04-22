package com.prototype.spacenews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.prototype.data.util.ResourceManager
import com.prototype.spacenews.test.TestApp
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class FragmentTestAndroid<TestFragment : Fragment> : TestCommonsAndroid(), KoinTest {

    lateinit var context: TestApp
    lateinit var resourceManager: ResourceManager

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        resourceManager = ResourceManager(context)

        if (GlobalContext.getOrNull() != null) {
            stopKoin()
        }

        startKoin {
            androidContext(context)
            modules(listOf())
        }

        runBlocking {
            injectFragment()
        }
    }

    @After
    fun autoClose() {
        unmockkAll()
        stopKoin()
    }

    abstract fun assignClass(): Class<TestFragment>

    inline fun <reified TestFragment : Fragment> makeClass(): Class<TestFragment> {
        return TestFragment::class.java
    }

    open fun buildArguments(): Bundle {
        return Bundle()
    }

    abstract suspend fun injectFragment()

    fun safeTest(action: () -> Unit){
        FragmentScenario.launchInContainer(
            assignClass(),
            buildArguments(),
            R.style.Theme_SpaceNews,
            null
        )

        action()
    }

    fun injectModule(moduleDeclaration: ModuleDeclaration) {
        context.injectModule(module(moduleDeclaration = moduleDeclaration))
    }
}