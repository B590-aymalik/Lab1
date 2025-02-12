package com.example.practicum1

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Test

class QuizViewModelTest {

    @Test
    fun providesExpectedQuestionText() {
        // 1. Create a SavedStateHandle
        val savedStateHandle = SavedStateHandle()

        // 2. Initialize the ViewModel
        val quizViewModel = QuizViewModel(savedStateHandle)

        // 3. Verify the first question is question_oceans
        //    (i.e. "The Pacific Ocean is larger than the Atlantic Ocean.")
        assertEquals(
            "Initial question should be question_oceans",
            R.string.question_oceans,
            quizViewModel.currentQuestionTextResId
        )
    }

    @Test
    fun wrapsAroundQuestionBank() {
        // Suppose 'question_asia' is your *last* question in the bank
        // and it lives at index 4 in an array of size 5.
        // So we set currentIndex to 4 via the SavedStateHandle.
        val savedStateHandle = SavedStateHandle(mapOf("CURRENT_INDEX_KEY" to 4))
        val quizViewModel = QuizViewModel(savedStateHandle)

        // Verify we start on the last question
        assertEquals(
            R.string.question_asia,
            quizViewModel.currentQuestionTextResId
        )

        // Move to next => wraps to index 0
        quizViewModel.moveToNextQuestion()

        // Now the question should be question_oceans (index 0)
        assertEquals(
            R.string.question_oceans,
            quizViewModel.currentQuestionTextResId
        )
    }
}
