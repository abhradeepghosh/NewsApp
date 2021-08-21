package com.example.testnewsapp.data.source.remote

/**
 * @author Abhradeep Ghosh
 */

/**
 * Data model mapped with API response
 */

data class HeadlineResponse(
    val status: String? = "",
    val totalResults: Int? = 0,
    val articles: List<NewsArticle?> = emptyList()
)

data class NewsArticle(
    val source: Source? = Source(),
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String? = "",
    val content: String? = ""
)

data class Source(
    val id: String? = "",
    val name: String? = ""
)

data class ArticleLikes(
    val likes: Int? = 0
)

data class ArticleComments(
    val comments: Int? = 0
)
