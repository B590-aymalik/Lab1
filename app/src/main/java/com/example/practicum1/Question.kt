package com.example.practicum1



import androidx.annotation.StringRes

data class Question(
    @StringRes val textResId: Int,  // Resource ID for question text
    val answer: Boolean            // True/False answer
)
