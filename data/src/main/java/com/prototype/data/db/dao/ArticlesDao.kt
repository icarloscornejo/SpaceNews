package com.prototype.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prototype.data.db.model.ArticleEntity

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticles(articles: List<ArticleEntity>): LongArray

    @Query("DELETE FROM ArticleEntity")
    suspend fun clearArticles(): Int

    @Query("SELECT * FROM ArticleEntity")
    suspend fun fetchArticles(): List<ArticleEntity>

}