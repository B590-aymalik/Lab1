package com.example.practicum1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.util.Log
import com.example.practicum1.databinding.ActivityMainBinding
import androidx.activity.viewModels

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // The ViewModel is obtained via the delegate.
    // With the correct dependencies, this automatically uses SavedStateViewModelFactory.
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        enableEdgeToEdge()

        // Inflate the layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        // Show the current question (restored via SavedStateHandle)
        updateQuestion()

        // True/False buttons
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        // Next/Prev
        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNextQuestion()
            updateQuestion()
        }
        binding.prevButton.setOnClickListener {
            quizViewModel.moveToPrevQuestion()
            updateQuestion()
        }

        // Clicking on the question text also goes to next question
        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNextQuestion()
            updateQuestion()
        }
    }

    private fun updateQuestion() {
        // Ask the ViewModel for the text resource for the current question
        val questionTextResId = quizViewModel.currentQuestionTextResId
        binding.questionTextView.setText(questionTextResId)

        // If you track "isAnswered" in the ViewModel, update button states accordingly.
        // For simplicity here, assume it's not answered => enabled
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val isCorrect = (userAnswer == quizViewModel.currentQuestionAnswer)

        // If you're tracking scores in the ViewModel, update them here:
        // quizViewModel.totalAnswers++
        // if (isCorrect) quizViewModel.correctAnswers++
        // if (quizViewModel.totalAnswers == quizViewModel.questionBank.size) {
        //     showFinalScore()
        // }

        // Show a toast
        val messageResId = if (isCorrect) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        // Disable the buttons if a question is answered
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }

    private fun showFinalScore() {
        val score = quizViewModel.correctAnswers.toDouble() / quizViewModel.questionBank.size
        val scorePercentage = (score * 100).toInt()
        val scoreMessage = getString(R.string.score_message, scorePercentage)
        Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG).show()
    }
}
