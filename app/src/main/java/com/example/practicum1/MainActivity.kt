package com.example.practicum1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.practicum1.databinding.ActivityMainBinding
import android.widget.Toast
import android.util.Log

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // List of questions
    private val questionBank = listOf(
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    // Index to track the current question
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        enableEdgeToEdge()

        // Enable view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Display the first question
        updateQuestion()

        // Set listeners for True and False buttons
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        // Set listener for the Next button
        binding.nextButton.setOnClickListener {
            goToNextQuestion()
        }

        // Set listener for the Previous button
        binding.prevButton.setOnClickListener {
            goToPreviousQuestion()
        }

        // Set listener for the TextView to go to the next question
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

    // Function to update the question text
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)


        // Re-enable the buttons for the next question
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }

    // Function to check the user's answer
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        showFeedback(messageResId)
        // Disable the buttons to prevent multiple answers
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false


    }

    // Function to show feedback using Toast
    private fun showFeedback(messageResId: Int) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    // Function to move to the next question
    private fun goToNextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    // Function to move to the previous question
    private fun goToPreviousQuestion() {
        currentIndex = if (currentIndex - 1 < 0) {
            questionBank.size - 1
        } else {
            currentIndex - 1
        }
        updateQuestion()
    }
}
