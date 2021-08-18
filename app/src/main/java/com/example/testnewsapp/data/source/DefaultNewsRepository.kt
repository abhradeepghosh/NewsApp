package com.example.testnewsapp.data.source

import androidx.lifecycle.LiveData
import com.example.testnewsapp.data.*
import com.example.testnewsapp.data.source.local.NewsLocalDataSource
import com.example.testnewsapp.data.source.remote.HeadlineResponse
import com.example.testnewsapp.data.source.remote.NewsRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Abhradeep Ghosh
 */
class DefaultNewsRepository @Inject constructor(
    private val localDataSource: NewsLocalDataSource,
    private val remoteDataSource: NewsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : NewsRepository {


    override suspend fun refreshArticles() {
        try {
            updateArticlesFromRemoteDataSource()
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    override suspend fun getComments(article: Result<Article>) {
        try {
            updateArticlesCommentsFromRemoteDataSource(article)
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    override suspend fun getLikes(article: Result<Article>) {
        try {
            updateArticlesLikesFromRemoteDataSource(article)
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    override suspend fun getArticles(forceUpdate: Boolean): Result<List<Article>> {
        if (forceUpdate) {
            try {
                updateArticlesFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex.localizedMessage)
            }
        }
        return localDataSource.getArticles()
    }

    private suspend fun updateArticlesFromRemoteDataSource()  = withContext(ioDispatcher){
        try {
            val apiResponse = remoteDataSource.getTopHeadlines()

            when {
                apiResponse.isSuccessful && apiResponse.body() != null -> {
                    apiResponse.body()?.let {
                        val articles: List<Article> =
                            (it as HeadlineResponse).articles.map { article ->
                                Article.convertRemoteArticleToLocalArticle(article)
                            }
                        localDataSource.clearAndCacheArticles(articles)
                    }
                }
                else -> {
                    throw Exception("no response")
                }
            }
        }catch (e: HttpException) {
            throw e
        }catch (e: IOException) {
            throw e
        }

    }


    private suspend fun updateArticlesCommentsFromRemoteDataSource(taskResult: Result<Article>) = withContext(ioDispatcher){
        if(taskResult is Result.Success) {
            taskResult.data?.let {
                val numberOfComments = remoteDataSource.getNumberOfComments(
                    taskResult.data.url.substring (8).replace("/", "-")
                )

                when {
                    numberOfComments.body() != null -> {
                        numberOfComments.body()?.let{ numberOfComments ->
                            val comments : Comments = Comments.convertRemoteCommentsToLocalComments(numberOfComments.comments, taskResult.data.id)
                            localDataSource.clearAndCacheComments(comments)
                        }
                    }
                    else -> {
                        throw Exception("no response")
                    }
                }
            }
        }
    }

    private suspend fun updateArticlesLikesFromRemoteDataSource(taskResult: Result<Article>) = withContext(ioDispatcher){
        if(taskResult is Result.Success) {

            taskResult.data?.let {

                val numberOfLikes = remoteDataSource.getNumberOfLikes(
                    taskResult.data.url.substring(8).replace("/", "-")
                )

                when {
                    numberOfLikes.body() != null -> {
                        numberOfLikes.body()?.let { numberOfLikes ->
                            var likes: Likes = Likes.convertRemoteLikesToLocalLikes(numberOfLikes.likes, taskResult.data.id)
                            localDataSource.clearAndCacheLikes(likes)
                        }
                    }
                    else -> {
                        throw Exception("no response")
                    }
                }
            }
        }
    }

    override suspend fun getArticle(articleId: Int): Result<Article> {
        return localDataSource.getArticle(articleId)
    }

    override fun observeArticles(): LiveData<Result<List<Article>>> {
        return localDataSource.observeArticles()
    }

    override fun observeArticle(articleId: Int): LiveData<Result<Article>> {
        return localDataSource.observeArticle(articleId)
    }

    override fun observeArticleNumberOfComments(articleId: Int?): LiveData<Result<Comments>> {
        return localDataSource.observeArticleNumberOfComments(articleId)
    }

    override fun observeArticleNumberOfLikes(articleId: Int?): LiveData<Result<Likes>>{
        return localDataSource.observeArticleNumberOfLikes(articleId)
    }

    override suspend fun insertArticles(articles: List<Article>): Result<List<Long>> {
        return localDataSource.insertArticles(articles)
    }

    override suspend fun clearAndCacheArticles(articles: List<Article>) {
        return localDataSource.clearAndCacheArticles(articles)
    }
}