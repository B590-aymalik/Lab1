package com.example.practicum1

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test

class QuizViewModelTest {

    @Test
    fun providesExpectedQuestionText() {
        // 1. Create a SavedStateHandle
        val savedStateHandle = SavedStateHandle()

        // 2. Initialize the ViewModel
        val quizViewModel = QuizViewModel(savedStateHandle)

        // 3. Verify the first question is question_oceans
        assertEquals(
            "Initial question should be question_oceans",
            R.string.question_oceans,
            quizViewModel.currentQuestionTextResId
        )
    }

    @Test
    fun wrapsAroundQuestionBank() {
        // Suppose 'question_asia' is your *last* question in the bank (index 4)
        val savedStateHandle = SavedStateHandle(mapOf("CURRENT_INDEX_KEY" to 4))
        val quizViewModel = QuizViewModel(savedStateHandle)

        // Verify we start on the last question
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionTextResId)

        // Move to next => should wrap to index 0
        quizViewModel.moveToNextQuestion()
        assertEquals(R.string.question_oceans, quizViewModel.currentQuestionTextResId)
    }

    @Test
    fun verifiesCorrectAnswerChecking() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)

        // First question answer should be true
        assertTrue(quizViewModel.currentQuestionAnswer)

        // Move to next question (question_mideast)
        quizViewModel.moveToNextQuestion()
        assertFalse(quizViewModel.currentQuestionAnswer)
    }
}
