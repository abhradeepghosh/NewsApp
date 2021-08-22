package com.example.testnewsapp.data.source

import androidx.lifecycle.LiveData
import com.example.testnewsapp.data.*
import com.example.testnewsapp.data.source.local.NewsLocalDataSource
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
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsRepository {

    /**
     * fetch top headlines ( articles ) from remote source - api
     */
    override suspend fun refreshArticles() {
        try {
            updateArticlesFromRemoteDataSource()
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    /**
     * fetch comments from remote source - api
     */
    override suspend fun getComments(article: Result<Article>) {
        try {
            updateArticlesCommentsFromRemoteDataSource(article)
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    /**
     * fetch likes from remote source - api
     */
    override suspend fun getLikes(article: Result<Article>) {
        try {
            updateArticlesLikesFromRemoteDataSource(article)
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    /**
     * fetch top headlines ( articles ) from remote data source and store in local database
     *
     * @return top headlines ( articles ) from local data source - db
     */
    override suspend fun getArticles(forceUpdate: Boolean): Result<List<Article>> {
        if (forceUpdate) {
            try {
                updateArticlesFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex.localizedMessage!!)
            }
        }
        return localDataSource.getArticles()
    }

    /**
     * Fetch the article from local data source.
     *
     * @return one article
     */
    override suspend fun getArticle(articleId: Int): Result<Article> {
        return localDataSource.getArticle(articleId)
    }

    /**
     * Actual implementation of fetching top headlines ( articles ) from api and storing in local database
     */
    private suspend fun updateArticlesFromRemoteDataSource() = withContext(ioDispatcher) {
        try {
            val apiResponse = remoteDataSource.getTopHeadlines()

            when {
                apiResponse.isSuccessful && apiResponse.body() != null -> {
                    apiResponse.body()?.let {
                        val articles: List<Article> =
                            it.articles.map { article ->
                                Article.convertRemoteArticleToLocalArticle(article)
                            }
                        localDataSource.clearAndCacheArticles(articles)
                    }
                }
                else -> {
                    throw Exception("no response")
                }
            }
        } catch (e: HttpException) {
            throw e
        } catch (e: IOException) {
            throw e
        }

    }

    /**
     * Actual implementation of fetching top comments from api and storing in local database
     */
    private suspend fun updateArticlesCommentsFromRemoteDataSource(taskResult: Result<Article>) =
        withContext(ioDispatcher) {
            if (taskResult is Result.Success) {
                val numberOfComments = remoteDataSource.getNumberOfComments(
                    taskResult.data.url.substring(getStartingIndexForUrl(taskResult.data.url)).replace("/", "-")
                )
                when {
                    numberOfComments.body() != null -> {
                        numberOfComments.body()?.let { commentsNumber ->
                            val comments: Comments = Comments.convertRemoteCommentsToLocalComments(
                                commentsNumber.comments,
                                taskResult.data.id
                            )
                            localDataSource.clearAndCacheComments(comments)
                        }
                    }
                    else -> {
                        throw Exception("no response")
                    }
                }
            }
        }

    /**
     * Actual implementation of fetching likes from api and storing in local database
     */
    private suspend fun updateArticlesLikesFromRemoteDataSource(taskResult: Result<Article>) =
        withContext(ioDispatcher) {
            if (taskResult is Result.Success) {
                val numberOfLikes = remoteDataSource.getNumberOfLikes(
                    taskResult.data.url.substring(getStartingIndexForUrl(taskResult.data.url)).replace("/", "-")
                )
                when {
                    numberOfLikes.body() != null -> {
                        numberOfLikes.body()?.let { likesNumber ->
                            val likes: Likes = Likes.convertRemoteLikesToLocalLikes(
                                likesNumber.likes,
                                taskResult.data.id
                            )
                            localDataSource.clearAndCacheLikes(likes)
                        }
                    }
                    else -> {
                        throw Exception("no response")
                    }
                }
            }
        }

    /**
     * Get the starting index with which the url would be constructed to fetch number of comments/likes.
     *
     * @return starting index of the url
     */
    private fun getStartingIndexForUrl(source : String) : Int{
        return source.indexOf("/", source.indexOf("/") + 2)
    }

    /**
     * observe the data changes for the list of top headlines ( article ) from local data source.
     *
     * @return list of top headlines ( article )
     */
    override fun observeArticles(): LiveData<Result<List<Article>>> {
        return localDataSource.observeArticles()
    }

    /**
     * observe the data changes for the article from local data source.
     *
     * @return one article
     */
    override fun observeArticle(articleId: Int): LiveData<Result<Article>> {
        return localDataSource.observeArticle(articleId)
    }

    /**
     * observe the data changes for the number of comments for a particular article from local data source.
     *
     * @return result consisting of number of comments
     */
    override fun observeArticleNumberOfComments(articleId: Int?): LiveData<Result<Comments>> {
        return localDataSource.observeArticleNumberOfComments(articleId)
    }

    /**
     * observe the data changes for the number of likes for a particular article from local data source.
     *
     * @return result consisting of number of likes
     */
    override fun observeArticleNumberOfLikes(articleId: Int?): LiveData<Result<Likes>> {
        return localDataSource.observeArticleNumberOfLikes(articleId)
    }
}