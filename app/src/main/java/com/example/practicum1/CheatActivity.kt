package com.example.practicum1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practicum1.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false

    companion object {
        const val EXTRA_ANSWER_IS_TRUE = "com.example.practicum1.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.example.practicum1.answer_shown"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the answer from the intent
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        // Handle "Show Answer" button click
        binding.showAnswerButton.setOnClickListener {
            val answerText = if (answerIsTrue) {
                getString(R.string.true_button)
            } else {
                getString(R.string.false_button)
            }
            binding.answerTextView.text = answerText

            // Send result back to MainActivity
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
}
