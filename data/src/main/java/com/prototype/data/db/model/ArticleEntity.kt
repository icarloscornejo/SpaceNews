package com.prototype.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prototype.data.mapper.DomainMapper
import com.prototype.domain.model.ArticleModel
import org.jetbrains.annotations.NotNull

@Entity
class ArticleEntity (
    @PrimaryKey
    @NotNull
    val id: String,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String,
    val featured: Boolean
): DomainMapper<ArticleModel>() {

    override fun toDomainModel() = parse<ArticleModel>(this)

}