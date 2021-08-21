package com.example.testnewsapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testnewsapp.data.Article
import com.example.testnewsapp.data.Comments
import com.example.testnewsapp.data.Likes

/**
 * @author Abhradeep Ghosh
 */
@Database(
    entities = [Article::class, Comments::class, Likes::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

}