package com.example.testnewsapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.testnewsapp.data.Article
import com.example.testnewsapp.data.Comments
import com.example.testnewsapp.data.Likes

/**
 * @author Abhradeep Ghosh
 */

@Dao
interface NewsDao {

    /**
     * Select all articles from the article table.
     *
     * @return all article.
     */
    @Query("SELECT * FROM Article")
    suspend fun getArticles(): List<Article>


    /**
     * Select a article by id.
     *
     * @param articleId the article id.
     * @return the article with articleId.
     */
    @Query("SELECT * FROM Article WHERE id = :articleId")
    suspend fun getArticleById(articleId: Int): Article?

    /**
     * Select comments from the article table.
     *
     * @return number of comments.
     */
    @Query("SELECT * FROM Comments")
    fun getArticleNumberOfComments(): LiveData<Comments>

    /**
     * Select likes from the article table.
     *
     * @return number of likes.
     */
    @Query("SELECT * FROM Likes")
    fun getArticleNumberOfLikes(): LiveData<List<Likes>>


    /**
     * Observes a single article.
     *
     * @param articleId the article id.
     * @return the article with id.
     */
    @Query("SELECT * FROM Article WHERE id = :articleId")
    fun observeArticleById(articleId: Int): LiveData<Article>

    /**
     * Observes number of comments.
     *
     * @param articleId the article id.
     * @return the number of comments based on articleId.
     */
    @Query("SELECT * FROM Comments WHERE articleId = :articleId")
    fun observeArticleNumberOfComments(articleId: Int?): LiveData<Comments>

    /**
     * Observes number of likes.
     *
     * @param articleId the article id.
     * @return the number of likes based on articleId.
     */
    @Query("SELECT * FROM Likes WHERE articleId = :articleId")
    fun observeArticleNumberOfLikes(articleId: Int?): LiveData<Likes>


    /**
     * Observes list of Articles.
     *
     * @return all articles.
     */
    @Query("SELECT * FROM Article")
    fun observeArticles(): LiveData<List<Article>>


    /**
     * Insert articles into the article table
     */
    @Insert
    suspend fun insertArticles(articles: List<Article>): List<Long>

    /**
     * Insert likes into the likes table
     */
    @Insert
    suspend fun insertLikes(likes: Likes)

    /**
     * Insert comments into the comments table
     */
    @Insert
    suspend fun insertComments(comments: Comments)

    /**
     * Delete all articles.
     */
    @Query("DELETE FROM Article")
    suspend fun deleteArticles()

    /**
     * Delete all Likes.
     */
    @Query("DELETE FROM Likes")
    suspend fun deleteLikes()

    /**
     * Delete all Comments.
     */
    @Query("DELETE FROM Comments")
    suspend fun deleteComments()

    /**
     * Delete all articles and insert new articles.
     */
    @Transaction
    suspend fun clearAndCacheArticles(articles: List<Article>) {
        deleteArticles()
        insertArticles(articles)
    }

    /**
     * Delete all likes and insert new likes.
     */
    @Transaction
    suspend fun clearAndCacheLikes(likes: Likes) {
        insertLikes(likes)
    }

    /**
     * Delete all comments and insert new comments.
     */
    @Transaction
    suspend fun clearAndCacheComments(comments: Comments) {
        insertComments(comments)
    }

}