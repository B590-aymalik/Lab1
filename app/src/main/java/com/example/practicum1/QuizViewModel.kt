package com.example.practicum1

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

// Keys for saving state
private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
private const val CHEATED_QUESTIONS_KEY = "CHEATED_QUESTIONS_KEY"

class QuizViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val questionBank = listOf(
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    // Current question index
    var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) {
            savedStateHandle[CURRENT_INDEX_KEY] = value
        }

    // Track cheating on a per-question basis
    // We store a BooleanArray in SavedStateHandle so it persists across rotation/process death
    private var cheatedQuestions: BooleanArray =
        savedStateHandle.get<BooleanArray>(CHEATED_QUESTIONS_KEY)
            ?.copyOf() // copyOf() to avoid direct references
            ?: BooleanArray(questionBank.size) { false }

    // Save the array back into the SavedStateHandle any time we modify it
    private fun saveCheatedArray() {
        savedStateHandle[CHEATED_QUESTIONS_KEY] = cheatedQuestions
    }

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionTextResId: Int
        get() = questionBank[currentIndex].textResId

    // Mark that the user has cheated on the current question
    fun setQuestionCheated(index: Int) {
        cheatedQuestions[index] = true
        saveCheatedArray()
    }

    // Check if the user cheated on the given question
    fun isQuestionCheated(index: Int): Boolean {
        return cheatedQuestions[index]
    }

    // Move to the next question, but do NOT reset cheating states
    fun moveToNextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    // Move to the previous question
    fun moveToPrevQuestion() {
        currentIndex = if (currentIndex - 1 < 0) {
            questionBank.size - 1
        } else {
            currentIndex - 1
        }
    }
}
