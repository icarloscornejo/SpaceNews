package com.prototype.spacenews

import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import com.prototype.spacenews.test.TestApp
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [Build.VERSION_CODES.P],
    application = TestApp::class
)
abstract class FragmentTest<TestFragment : Fragment> : InjectableTest() {
    private lateinit var fragmentScenario: FragmentScenario<TestFragment>

    @Before
    fun setUp() {
        initCommons()

        runBlocking {
            injectFragment()
        }

        fragmentScenario = FragmentScenario.launchInContainer(
            assignClass(),
            buildArguments(),
            R.style.Theme_SpaceNews,
            null
        )
    }

    abstract fun assignClass(): Class<TestFragment>

    open fun buildArguments(): Bundle {
        return Bundle()
    }

    abstract suspend fun injectFragment()

    open fun transformFragment(fragment: TestFragment): TestFragment {
        return fragment
    }

    private fun fragmentTest(
        isCoroutine: Boolean,
        action: TestFragment.() -> Unit = {},
        blockingAction: suspend TestFragment.() -> Unit = {}
    ) = syncAndThen {
        fragmentScenario.onFragment {
            if (isCoroutine) {
                runBlocking { blockingAction(transformFragment(it)) }
            } else {
                action(transformFragment(it))
            }
        }
    }

    fun safeTest(action: TestFragment.() -> Unit) {
        fragmentTest(false, action = action)
    }

    inline fun <reified TestFragment : Fragment> makeClass(): Class<TestFragment> {
        return TestFragment::class.java
    }

    inline fun syncAndThen(action: () -> Unit) {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        action.invoke()
    }
}