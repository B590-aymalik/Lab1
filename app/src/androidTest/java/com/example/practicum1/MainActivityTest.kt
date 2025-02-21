package com.example.practicum1

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun showsFirstQuestionOnLaunch() {
        // Check if the first question is displayed
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.question_oceans))) // Ensure this matches the first question
    }

    @Test
    fun showsSecondQuestionAfterNextPress() {
        // Click the NEXT button
        onView(withId(R.id.next_button)).perform(click())

        // Check if the second question is displayed
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.question_mideast))) // Ensure this matches the second question
    }

    @Test
    fun handlesActivityRecreation() {
        // Click the NEXT button to move to the second question
        onView(withId(R.id.next_button)).perform(click())

        // Simulate a configuration change (e.g., screen rotation)
        scenario.recreate()

        // Verify that the second question is still displayed after recreation
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.question_mideast))) // Ensure this matches the second question after recreation
    }
}
