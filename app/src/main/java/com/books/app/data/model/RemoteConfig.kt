package com.books.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteConfig(
    @SerialName("books")
    val books: List<Book> = emptyList(),
    @SerialName("top_banner_slides")
    val topBannerSlides: List<TopBannerSlide> = emptyList(),
    @SerialName("you_will_like_section")
    val youWillLikeSection: List<Int> = emptyList()
)

@Serializable
data class Book(
    @SerialName("author")
    val author: String = "",
    @SerialName("cover_url")
    val coverUrl: String = "",
    @SerialName("genre")
    val genre: String = "",
    @SerialName("id")
    val id: Int = -1,
    @SerialName("likes")
    val likes: String  = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("quotes")
    val quotes: String = "",
    @SerialName("summary")
    val summary: String = "",
    @SerialName("views")
    val views: String = ""
)

@Serializable
data class TopBannerSlide(
    @SerialName("book_id")
    val bookId: Int,
    @SerialName("cover")
    val cover: String,
    @SerialName("id")
    val id: Int
)
