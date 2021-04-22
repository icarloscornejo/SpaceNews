package com.prototype.spacenews.presentation.home

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prototype.domain.model.ArticleModel
import com.prototype.spacenews.presentation.util.SingleLiveEvent

class ArticleViewModel(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val article: ArticleModel,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val openArticleDetailEvent: SingleLiveEvent<ArticleModel>
): ViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val imageField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val titleField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val descriptionField = ObservableField("")

    init {
        with(article) {
            imageField.set(imageUrl)
            titleField.set(title)
            descriptionField.set(summary)
        }
    }

    fun openArticleDetail() = openArticleDetailEvent.postValue(article)
}