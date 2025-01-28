package com.example.practicum1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.util.Log
import com.example.practicum1.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Question bank
    private val questionBank = listOf(
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    // Track which question is currently visible
    private var currentIndex = 0

    // Track total and correct answers
    private var totalAnswers = 0
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")  // ← Added from the first code

        enableEdgeToEdge()

        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Display the first question
        updateQuestion()

        // Listeners for True/False buttons
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        // Next/Prev listeners
        binding.nextButton.setOnClickListener {
            goToNextQuestion()
        }
        binding.prevButton.setOnClickListener {
            goToPreviousQuestion()
        }

        // Clicking on the question text also moves to the next question
        binding.questionTextView.setOnClickListener {
            goToNextQuestion()
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }



    private fun updateQuestion() {
        // Update the question text
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

        // Enable or disable the True/False buttons depending on whether this question has been answered
        val question = questionBank[currentIndex]
        binding.trueButton.isEnabled = !question.isAnswered
        binding.falseButton.isEnabled = !question.isAnswered
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val currentQuestion = questionBank[currentIndex]

        // Only increment counters if the question hasn't been answered yet
        if (!currentQuestion.isAnswered) {
            currentQuestion.isAnswered = true
            totalAnswers++

            // If user’s answer matches the question’s correct answer, increment correctAnswers
            if (userAnswer == currentQuestion.answer) {
                correctAnswers++
            }

            // If we've answered all questions, display final score
            if (totalAnswers == questionBank.size) {
                showFinalScore()
            }
        }

        // Show toast telling user if they got it correct or incorrect
        val messageResId = if (userAnswer == currentQuestion.answer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        showFeedback(messageResId)

        // Disable buttons so the user can’t answer again
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }

    private fun showFeedback(messageResId: Int) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun showFinalScore() {
        // Calculate and display percentage
        val scorePercentage = (correctAnswers.toDouble() / questionBank.size) * 100
        val scoreMessage = getString(R.string.score_message, scorePercentage.toInt())
        Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG).show()
    }

    private fun goToNextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun goToPreviousQuestion() {
        currentIndex = if (currentIndex - 1 < 0) {
            questionBank.size - 1
        } else {
            currentIndex - 1
        }
        updateQuestion()
    }
}
