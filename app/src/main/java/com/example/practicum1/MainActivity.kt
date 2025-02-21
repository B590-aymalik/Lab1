package com.example.practicum1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.practicum1.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // ViewModel initialization
    private val quizViewModel: QuizViewModel by viewModels()

    // Registering result callback for CheatActivity
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater = result.data?.getBooleanExtra(
                CheatActivity.EXTRA_ANSWER_SHOWN, false
            ) ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        enableEdgeToEdge()

        // Inflate the layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        // Display the current question
        updateQuestion()

        // True/False Buttons
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        // Next/Previous Buttons
        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNextQuestion()
            updateQuestion()
        }
        binding.prevButton.setOnClickListener {
            quizViewModel.moveToPrevQuestion()
            updateQuestion()
        }

        // Clicking on the question text also moves to the next question
        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNextQuestion()
            updateQuestion()
        }

        // Cheat Button
        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this, answerIsTrue)
            cheatLauncher.launch(intent)  // Using the launcher instead of startActivity
        }
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionTextResId
        binding.questionTextView.setText(questionTextResId)

        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        // Disable answer buttons after selection
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }
}
