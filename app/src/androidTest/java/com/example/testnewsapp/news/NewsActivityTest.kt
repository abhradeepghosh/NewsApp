package com.example.testnewsapp.news

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.testnewsapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Abhradeep Ghosh
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class NewsActivityTest{

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(NewsActivity::class.java)

    @Test
    fun testForRecyclerItemClick(){

        onView(ViewMatchers.withId(R.id.recyclerView_headlines))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<HeadlinesAdapter.ViewHolder>(
                    0,
                    click()
                )
            )
    }

    @Test fun scrollToItemBelowFold_click() {
        // First, scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recyclerView_headlines))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<HeadlinesAdapter.ViewHolder>(
                    18,
                    click()
                )
            )

        onView(ViewMatchers.withId(R.id.imageButton_back))
            .perform(click())

    }

}