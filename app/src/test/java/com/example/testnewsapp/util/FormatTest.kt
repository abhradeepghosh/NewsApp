package com.example.testnewsapp.util

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

/**
 * @author Abhradeep Ghosh
 */
class FormatTest{

    @Test
    fun dateFormatFromDateTime(){
        var dateTime: String? = "2020-06-07T10:33:58Z"
        var date = Format.dateFormatFromDateTime(dateTime)
        assertThat("2020-06-07", `is`(date))

        dateTime = "2020/06/07T10:30:58Z"
        date = Format.dateFormatFromDateTime(dateTime)
        assertThat("2020/06/07", `is`(date))

        dateTime = "2020-06-07"
        date = Format.dateFormatFromDateTime(dateTime)
        assertThat("2020-06-07", `is`(date))

        dateTime = ""
        date = Format.dateFormatFromDateTime(dateTime)
        assertThat("", `is`(date))

        dateTime = null
        date = Format.dateFormatFromDateTime(dateTime)
        assertThat("", `is`(date))
    }

}