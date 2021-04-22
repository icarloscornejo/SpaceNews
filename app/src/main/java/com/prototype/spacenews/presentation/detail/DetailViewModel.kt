package com.prototype.spacenews.presentation.detail

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.prototype.domain.model.ArticleModel
import com.prototype.spacenews.presentation.util.SingleLiveEvent

class DetailViewModel: ViewModel() {

    lateinit var article: ArticleModel

    val backEvent = SingleLiveEvent<Boolean>()
    val openWebDetailEvent = SingleLiveEvent<String>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val imageField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val titleField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val descriptionField = ObservableField("")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val originField = ObservableField("")

    fun populateDetails(article: ArticleModel) = with(article){
        imageField.set(imageUrl)
        titleField.set(title)
        descriptionField.set(summary)
        originField.set(newsSite)
        this@DetailViewModel.article = this
    }

    fun goBack() = backEvent.postValue(true)

    fun openWebDetail() = openWebDetailEvent.postValue(article.url)
}