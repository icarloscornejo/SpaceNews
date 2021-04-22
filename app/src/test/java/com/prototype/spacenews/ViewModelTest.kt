package com.prototype.spacenews

import android.os.Build
import androidx.lifecycle.ViewModel
import com.prototype.spacenews.test.TestApp
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [Build.VERSION_CODES.P],
    application = TestApp::class
)
abstract class ViewModelTest<TestViewModel : ViewModel> : InjectableTest() {

    private lateinit var viewModel: TestViewModel

    @Before
    fun setUp() {
        initCommons()

        runBlocking {
            viewModel = buildViewModel()
        }
    }

    abstract suspend fun buildViewModel(): TestViewModel

    open fun transformViewModel(viewModel: TestViewModel): TestViewModel {
        return viewModel
    }

    fun safeTest(action: TestViewModel.() -> Unit) {
        action(transformViewModel(viewModel))
    }
}