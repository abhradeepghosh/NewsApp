package com.example.testnewsapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testnewsapp.data.source.remote.NewsArticle

/**
 * @author Abhradeep Ghosh
 */

@Entity(tableName = "Article")
data class Article @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "author")
    val author: String = "",

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "description")
    val description: String = "",

    @ColumnInfo(name = "url")
    val url: String = "",

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String = "",

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String = "",

    @ColumnInfo(name = "source")
    val source: String = "",
) {

    companion object {

        fun convertRemoteArticleToLocalArticle(newsArticle: NewsArticle?): Article {

            return Article(
                author = newsArticle?.author ?: "",
                title = newsArticle?.title ?: "",
                description = newsArticle?.description ?: "",
                url = newsArticle?.url ?: "",
                urlToImage = newsArticle?.urlToImage ?: "",
                publishedAt = newsArticle?.publishedAt ?: "",
                source = newsArticle?.source?.name ?: ""
            )
        }
    }
}