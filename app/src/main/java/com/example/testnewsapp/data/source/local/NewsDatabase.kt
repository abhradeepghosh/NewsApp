package com.example.testnewsapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testnewsapp.data.Article

/**
 * @author Abhradeep Ghosh
 */
@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase(){

    abstract fun newsDao(): NewsDao

}