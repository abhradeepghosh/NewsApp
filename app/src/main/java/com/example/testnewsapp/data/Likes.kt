package com.example.testnewsapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Abhradeep Ghosh
 */

@Entity(tableName = "Likes")
data class Likes @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "articleId")
    val articleId: Int = 0,

    @ColumnInfo(name = "likes")
    val likes: Int = 0

) {

    companion object {

        fun convertRemoteLikesToLocalLikes(numberOfLikes: Int?, articleId: Int?): Likes {

            return Likes(
                likes = numberOfLikes ?: 0,
                articleId = articleId ?: 0
            )
        }
    }
}