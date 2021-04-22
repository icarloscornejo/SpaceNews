package com.prototype.data.impl

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import com.prototype.data.api.SpaceNewsApi
import com.prototype.data.db.dao.ArticlesDao
import com.prototype.data.util.NetworkBoundResource
import com.prototype.domain.model.ArticleModel
import com.prototype.domain.model.Resource
import com.prototype.domain.repository.ArticlesRepository
import java.net.UnknownHostException

class ArticleRepositoryImpl(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val spaceNewsApi: SpaceNewsApi,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val articlesDao: ArticlesDao
) : ArticlesRepository {

    override fun fetchArticles(): LiveData<Resource<List<ArticleModel>>> = NetworkBoundResource.create(
        query = {
            articlesDao.fetchArticles().map {
                it.toDomainModel()
            }
        },
        fetch = { dbResult ->
            try {
                spaceNewsApi.fetchArticles()
            } catch (ex: UnknownHostException) {
                emit(Resource.error(ex, dbResult))
                null
            }
        },
        saveFetchResult = { response ->
            articlesDao.clearArticles()
            articlesDao.saveArticles(response.map { it.toRoomEntity() })
        }
    )
}