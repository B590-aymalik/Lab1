package com.example.practicum1

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
private const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

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

    var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) {
            savedStateHandle[CURRENT_INDEX_KEY] = value
        }

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionTextResId: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        isCheater = false  // Reset cheating status when moving to the next question
    }

    fun moveToPrevQuestion() {
        currentIndex = if (currentIndex - 1 < 0) {
            questionBank.size - 1
        } else {
            currentIndex - 1
        }
    }
}
