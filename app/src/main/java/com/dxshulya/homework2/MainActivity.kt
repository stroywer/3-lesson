package com.dxshulya.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val provider: ViewModelProvider = ViewModelProviders.of(this)
        mainViewModel = provider.get(MainViewModel::class.java)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        mainViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            mainViewModel.moveNext()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            mainViewModel.moveNext()
            updateQuestion()
        }

        prevButton.setOnClickListener {
            mainViewModel.moveBack()
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, mainViewModel.currentIndex)
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
        //val questionTextResId = questionBank[currentIndex].QuestionResId
        //val questionTextResId = mainViewModel.currentQuestionText
        //questionTextView.setText(questionTextResId)
        questionTextView.setText(mainViewModel.currentQuestion.QuestionResId)
        disableAnswerButtons()
    }

    private fun disableAnswerButtons() {
        if (mainViewModel.currentQuestion.answered) {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        } else {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val messageResId = if (mainViewModel.checkAnswer(userAnswer)) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, getString(messageResId, mainViewModel.score()), Toast.LENGTH_LONG).let {
            it.setGravity(Gravity.BOTTOM, 0, 100)
            it.show()
        }

        disableAnswerButtons()
    }

    /*private fun checkAnswer(userAnswer: Boolean) {
        //val correctAnswer = questionBank[currentIndex].correctAnswer
        val correctAnswer = mainViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }*/
}