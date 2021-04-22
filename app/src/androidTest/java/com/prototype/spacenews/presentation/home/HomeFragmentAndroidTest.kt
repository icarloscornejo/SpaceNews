package com.prototype.spacenews.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.prototype.domain.model.ArticleModel
import com.prototype.domain.model.Resource
import com.prototype.spacenews.FragmentTestAndroid
import com.prototype.spacenews.R
import io.mockk.every
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel

class HomeFragmentAndroidTest : FragmentTestAndroid<HomeFragment>() {

    private val articleListMock = listOf(
        ArticleModel(
            "123",
            "Custom title 1",
            "url",
            "image",
            "site",
            "summary",
            "0",
            "0",
            false
        ),
        ArticleModel(
            "456",
            "Title 2 custom",
            "url",
            "image",
            "site",
            "summary",
            "0",
            "0",
            true
        )
    )
    private val secondList = listOf(
        ArticleModel(
            "123",
            "Lorem ipsum",
            "url",
            "image",
            "site",
            "summary",
            "0",
            "0",
            false
        ),
        ArticleModel(
            "456",
            "Dolor sit amet",
            "url",
            "image",
            "site",
            "summary",
            "0",
            "0",
            true
        )
    )

    override fun assignClass() = makeClass<HomeFragment>()

    override suspend fun injectFragment() {
        val viewModel = HomeViewModel(mock(), resourceManager)

        var first = false
        every {
            viewModel.fetchArticlesUseCase.invoke()
        } answers {
            first = !first
            MutableLiveData(Resource.success(if(first) articleListMock else secondList))
        }

        injectModule {
            viewModel { viewModel }
        }
    }

    @Test
    fun articleListPopulatedSuccessfully() = safeTest {
        Thread.sleep(500)

        onView(withText("Title 2 custom"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun articleListIsRefreshedWithNewContent() = safeTest {
        onView(withId(R.id.rv_articles))
            .perform(swipeDown())

        Thread.sleep(500)

        onView(withText("Dolor sit amet"))
            .check(matches(isDisplayed()))
    }
}