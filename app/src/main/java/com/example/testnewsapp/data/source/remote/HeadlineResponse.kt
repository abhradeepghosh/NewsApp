package com.example.testnewsapp.data.source.remote

/**
 * @author Abhradeep Ghosh
 */

data class HeadlineResponse(
    val status: String = "",
    val totalResults: Int = 0,
    val articles: List<NewsArticle> = emptyList()
)


data class NewsArticle(
    val source: Source = Source(),
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "",
    val content: String = ""
)

data class Source(
    val id: String = "",
    val name: String = ""
)