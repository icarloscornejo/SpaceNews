package com.prototype.domain.repository

import androidx.lifecycle.LiveData
import com.prototype.domain.model.ArticleModel
import com.prototype.domain.model.Resource

interface ArticlesRepository {

    fun fetchArticles(): LiveData<Resource<List<ArticleModel>>>

}