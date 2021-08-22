package com.example.testnewsapp.util

/**
 * @author Abhradeep Ghosh
 */

/**
 * To pluralize a text based on the count
 */
fun String.pluralize(count: Int): String {
    return if (count > 1) {
        this + 's'
    } else {
        this
    }
}