package com.example.testnewsapp.source.local

import androidx.lifecycle.LiveData
import com.example.testnewsapp.data.source.local.NewsLocalDataSource
import com.example.testnewsapp.data.Article
import com.example.testnewsapp.data.Comments
import com.example.testnewsapp.data.Likes
import com.example.testnewsapp.data.Result

/**
 * @author Abhradeep Ghosh
 */

class FakeNewsLocalDataSource(private val articles: MutableList<Article>? = mutableListOf()) : NewsLocalDataSource {

    override suspend fun getArticles(): Result<List<Article>> {
        articles?.let { return Result.Success(ArrayList(it)) }
        return Result.Error("No article found")
    }

    override suspend fun getArticle(articleId: Int): Result<Article> {
        articles?.let{
            val article = articles.toList().find { it.id == articleId }
            article?.let { return Result.Success<Article>(article) }
        }
        return Result.Error("Pupil not found")
    }

    override fun observeArticles(): LiveData<Result<List<Article>>> {
        TODO("Not yet implemented")
    }

    override fun observeArticle(articleId: Int): LiveData<Result<Article>> {
        TODO("Not yet implemented")
    }

    override fun observeArticleNumberOfComments(articleId: Int?): LiveData<Result<Comments>> {
        TODO("Not yet implemented")
    }

    override fun observeArticleNumberOfLikes(articleId: Int?): LiveData<Result<Likes>> {
        TODO("Not yet implemented")
    }

    override suspend fun clearAndCacheArticles(articles: List<Article>) {
        this.articles?.let{
            it.clear()
            it.addAll(articles)
        }
    }

    override suspend fun insertLikes(likes: Likes) {
        TODO("Not yet implemented")
    }

    override suspend fun insertComments(comments: Comments) {
        TODO("Not yet implemented")
    }


}