package com.example.practicum1

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    // The question bank is now inside the ViewModel
    val questionBank = listOf(
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    // Keep track of which question index is active
    var currentIndex = 0

    // If you want the ViewModel to also track total/correct answers:
    var totalAnswers = 0
    var correctAnswers = 0

    // Convenience properties for current question data
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionTextResId: Int
        get() = questionBank[currentIndex].textResId


    fun moveToNextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }


    fun moveToPrevQuestion() {
        currentIndex =
            if (currentIndex - 1 < 0) {
                questionBank.size - 1
                questionBank.size - 1
            } else {
                currentIndex - 1
            }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}
