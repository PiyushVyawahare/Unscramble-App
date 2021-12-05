package com.example.unscrambleapp.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    private val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private var wordList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    private fun getNextWord(){
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while(String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        if(wordList.contains(currentWord)){
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordList.add(currentWord)
        }
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun isUserWordCorrect(playerWord: String): Boolean{
        if(playerWord.equals(currentWord, true)){
            increaseScore()
            return true
        }
        return false
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordList.clear()
        getNextWord()
    }
}