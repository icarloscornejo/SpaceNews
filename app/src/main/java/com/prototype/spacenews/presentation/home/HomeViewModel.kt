package com.prototype.spacenews.presentation.home

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.prototype.data.util.ResourceManager
import com.prototype.domain.model.ArticleModel
import com.prototype.domain.model.Resource
import com.prototype.domain.usecase.FetchArticlesUseCase
import com.prototype.spacenews.R
import com.prototype.spacenews.presentation.util.SingleLiveEvent
import java.util.*

class HomeViewModel(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fetchArticlesUseCase: FetchArticlesUseCase,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val resourceManager: ResourceManager
) : ViewModel() {

    val articleList = mutableListOf<ArticleModel>()

    val isLoading = ObservableBoolean(false)
    val showSnackbarMessageEvent = SingleLiveEvent<Pair<String, Int>>()
    val notifyDataChangedEvent = SingleLiveEvent<Boolean>()
    val openArticleDetailEvent = SingleLiveEvent<ArticleModel>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var articlesSource: LiveData<Resource<List<ArticleModel>>> = MutableLiveData()
    val articlesData = MediatorLiveData<Resource<List<ArticleModel>>>()

    fun fetchArticles() {
        isLoading.set(true)

        articlesData.removeSource(articlesSource)
        articlesSource = fetchArticlesUseCase.invoke()
        articlesData.addSource(articlesSource) { articlesData.postValue(it) }
    }

    fun onArticlesFetched(resource: Resource<List<ArticleModel>>) {
        // We are gonna process the current status on the resource that are being served on the LiveData
        when (resource.status) {
            // Just loading, we set the screen state to loading
            Resource.Status.LOADING -> isLoading.set(true)
            // An error has been emitted on the LiveData
            Resource.Status.ERROR -> {
                val data = resource.data
                // We check if some data is available to show it to the user, (we can have it cached)
                if (!data.isNullOrEmpty()) {
                    processArticles(data)
                } else {
                    isLoading.set(false)
                }

                // Data or no data available, this error will be showed to the user letting them know
                // that an error has ocurred
                showSnackbarMessageEvent.postValue(
                    Pair(
                        resourceManager.getString(R.string.request_failed),
                        Snackbar.LENGTH_LONG
                    )
                )

            }
            // The request was a success
            Resource.Status.SUCCESS -> {
                val data = resource.data
                // We check if the result has any valid results
                if (data.isNullOrEmpty()) {
                    // We send a message to the user
                    showSnackbarMessageEvent.postValue(
                        Pair(
                            resourceManager.getString(R.string.request_no_data),
                            Snackbar.LENGTH_LONG
                        )
                    )
                    isLoading.set(false)
                } else {
                    processArticles(data)
                }
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun processArticles(articles: List<ArticleModel>) {
        articleList.apply {
            clear()
            addAll(articles)
        }

        if (articleList.none { it.featured }) {
            articleList[0].featured = true
        } else {
            val featuredIndex = articleList.indexOfFirst { it.featured }
            if (featuredIndex > 0) {
                Collections.swap(articleList, featuredIndex, 0)
            }
        }

        isLoading.set(false)
        notifyDataChangedEvent.postValue(true)
    }
}