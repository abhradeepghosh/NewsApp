package com.example.testnewsapp.util

/**
 * @author Abhradeep Ghosh
 */

class Format {
    companion object {
        fun dateFormatFromDateTime(dateTime: String?): String {
            dateTime?.let {
                return dateTime.substringBefore('T')
            }
            return ""
        }
    }

}