package com.example.testnewsapp.util

fun String.pluralize(count: Int): String? {
    return if (count > 1) {
        this + 's'
    } else {
        this
    }
}