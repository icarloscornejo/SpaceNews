package com.prototype.data.api.response


import com.google.gson.annotations.SerializedName
import com.prototype.data.db.model.ArticleEntity
import com.prototype.data.mapper.RoomMapper

data class ArticleResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("newsSite")
    val newsSite: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("featured")
    val featured: Boolean
) : RoomMapper<ArticleEntity>() {

    override fun toRoomEntity() = parse<ArticleEntity>(this)

}