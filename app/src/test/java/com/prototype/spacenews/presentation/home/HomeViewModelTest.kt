package com.prototype.spacenews.presentation.home

import androidx.lifecycle.MutableLiveData
import com.prototype.domain.model.ArticleModel
import com.prototype.domain.model.Resource
import com.prototype.spacenews.ViewModelTest
import io.mockk.every
import org.junit.Test

class HomeViewModelTest : ViewModelTest<HomeViewModel>() {

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

    override suspend fun buildViewModel(): HomeViewModel {
        val viewModel = HomeViewModel(mock(), resourceManager)

        every {
            viewModel.fetchArticlesUseCase.invoke()
        } answers {
            MutableLiveData(Resource.success(articleListMock))
        }

        return viewModel
    }

    // We are gonna check that certain variables are not null
    @Test
    fun varsAreNotNull() = safeTest {
        assertNotNull(articleList)
        assertNotNull(isLoading)
        assertNotNull(showSnackbarMessageEvent)
        assertNotNull(notifyDataChangedEvent)
        assertNotNull(openArticleDetailEvent)
        assertNotNull(articlesSource)
        assertNotNull(articlesData)
    }

    @Test
    fun `Articles are fetched and the final status is success`() = safeTest {
        articlesData.observeForever {
            assertTrue(it.status == Resource.Status.SUCCESS)
        }

        fetchArticles()
    }

    @Test
    fun `Articles are being fetched and the request is loading`() = safeTest {
        onArticlesFetched(Resource.loading(articleListMock))

        assertTrue(isLoading.get())
    }

    @Test
    fun `Articles are not fetched from API but we have cached results`() = safeTest {
        onArticlesFetched(Resource.error(Throwable("Error"), articleListMock))

        assertFalse(isLoading.get())
        assertEquals(articleList.size, articleListMock.size)
    }

    @Test
    fun `Articles are not fetched from API and we DONT have cached results`() = safeTest {
        onArticlesFetched(Resource.error(Throwable("Error"), null))

        assertFalse(isLoading.get())
        assertEquals(articleList.size, 0)
    }

    @Test
    fun `Articles are fetched from API but there are zero results`() = safeTest {
        onArticlesFetched(Resource.success(listOf()))

        assertFalse(isLoading.get())
        assertEquals(articleList.size, 0)
    }

    @Test
    fun `Articles are fetched from API with valid results`() = safeTest {
        onArticlesFetched(Resource.success(articleListMock))

        assertFalse(isLoading.get())
        assertEquals(articleList.size, articleListMock.size)
    }

    @Test
    fun `The featured article is not on the top of the list, so we send it to the top`() =
        safeTest {
            processArticles(articleListMock)

            assertEquals("456", articleList[0].id)
        }

    @Test
    fun `We dont have any featured article, so we put the first one as featured`() = safeTest {
        processArticles(articleListMock.apply {
            get(1).featured = false
        })

        assertEquals("123", articleList[0].id)
    }

}