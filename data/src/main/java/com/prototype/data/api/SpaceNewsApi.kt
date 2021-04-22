package com.prototype.data.api

import com.prototype.data.api.response.ArticleResponse
import retrofit2.http.GET

interface SpaceNewsApi {

    @GET("articles")
    suspend fun fetchArticles(): List<ArticleResponse>

}