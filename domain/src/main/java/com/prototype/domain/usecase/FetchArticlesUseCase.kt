package com.prototype.domain.usecase

import androidx.annotation.VisibleForTesting
import com.prototype.domain.repository.ArticlesRepository

class FetchArticlesUseCase(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val articlesRepository: ArticlesRepository
) {
    operator fun invoke() = articlesRepository.fetchArticles()
}