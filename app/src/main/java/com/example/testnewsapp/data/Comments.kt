package com.example.testnewsapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Abhradeep Ghosh
 */

@Entity(tableName = "Comments")
data class Comments @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "articleId")
    val articleId: Int = 0,

    @ColumnInfo(name = "comments")
    val comments: Int = 0

) {

    companion object {

        fun convertRemoteCommentsToLocalComments(
            numberOfComments: Int?,
            articleId: Int?
        ): Comments {

            return Comments(
                comments = numberOfComments ?: 0,
                articleId = articleId ?: 0
            )
        }
    }
}