package com.example.practicum1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practicum1.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    // The actual answer for the current question
    private var answerIsTrue = false

    // Keep track of whether the user has already revealed the answer
    private var isAnswerShown = false

    companion object {
        const val EXTRA_ANSWER_IS_TRUE = "com.example.practicum1.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.example.practicum1.answer_shown"

        // Key used for saving/restoring 'isAnswerShown' across rotation or process death
        private const val KEY_IS_ANSWER_SHOWN = "isAnswerShown"

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

        // Retrieve the answer from the Intent
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        // Restore our cheat state if we have a savedInstanceState
        isAnswerShown = savedInstanceState?.getBoolean(KEY_IS_ANSWER_SHOWN, false) ?: false

        // If the user had already revealed the answer before rotation, show it again
        if (isAnswerShown) {
            showAnswer()
        }

        // Handle "Show Answer" button click
        binding.showAnswerButton.setOnClickListener {
            showAnswer()
        }
    }

    /**
     * Actually reveal the answer text and set the result indicating cheating.
     */
    private fun showAnswer() {
        val answerText = if (answerIsTrue) {
            getString(R.string.true_button)
        } else {
            getString(R.string.false_button)
        }
        binding.answerTextView.text = answerText

        // Mark that the user has now seen the answer
        isAnswerShown = true

        // Send result back to MainActivity that the user has cheated
        setAnswerShownResult(true)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    /**
     * Save the current 'cheated' state so we can restore it on rotation or process death.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_IS_ANSWER_SHOWN, isAnswerShown)
    }
}
