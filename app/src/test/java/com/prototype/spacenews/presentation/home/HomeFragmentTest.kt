package com.prototype.spacenews.presentation.home

import androidx.lifecycle.MutableLiveData
import com.prototype.domain.model.ArticleModel
import com.prototype.domain.model.Resource
import com.prototype.spacenews.FragmentTest
import io.mockk.every
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.robolectric.annotation.LooperMode

@LooperMode(LooperMode.Mode.PAUSED)
class HomeFragmentTest : FragmentTest<HomeFragment>() {

    private val articleListMock = listOf(
        ArticleModel(
            "123",
            "title",
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
            "title",
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

        every {
            viewModel.fetchArticlesUseCase.invoke()
        } answers {
            MutableLiveData(Resource.success(articleListMock))
        }

        injectModule {
            viewModel { viewModel }
        }
    }

    // We are gonna check that certain variables are not null
    @Test
    fun varsAreNotNull() = safeTest {
        assertNotNull(viewModel)
    }

    @Test
    fun `Article list is fetched from the API and loaded correctly`() = safeTest {
        onArticlesFetched(
            Resource.success(articleListMock)
        )

        assertFalse(viewModel.isLoading.get())
        assertEquals("456", viewModel.articleList[0].id)
    }
}