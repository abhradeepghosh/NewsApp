package com.example.testnewsapp.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testnewsapp.data.Article
import com.example.testnewsapp.data.source.local.NewsDatabase
import com.example.testnewsapp.util.DaoTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Abhradeep Ghosh
 */

@RunWith(AndroidJUnit4::class)
class NewsDaoTest : DaoTest<NewsDatabase>(NewsDatabase::class.java) {

    @Test
    @Throws(InterruptedException::class)
    fun insertArticles() = runBlocking {
        // GIVEN
        val input = listOf(Article(1), Article(2))

        // THEN
        MatcherAssert.assertThat(
            db.newsDao().insertArticles(input),  CoreMatchers.equalTo(listOf(1L, 2L))
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertArticlesAndRead(): Unit = runBlocking {
        // GIVEN
        val input = listOf(
            Article(1, "Abhra", "Android"),
            Article(2, "Deep", "IOS")
        )
        db.newsDao().insertArticles(input)

        // THEN
        MatcherAssert.assertThat(
        db.newsDao().getArticles()[0].author, CoreMatchers.equalTo("Abhra"))
    }
}