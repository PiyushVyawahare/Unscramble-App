package com.example.unscrambleapp.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.unscrambleapp.R
import com.example.unscrambleapp.databinding.FragmentGameBinding
import kotlin.properties.ReadOnlyProperty


class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()


    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        Log.d("GameFragment", "GameFragment created/re-created!")
        return binding.root
    }


    override fun onDetach() {
        super.onDetach()
        Log.d("GameFragment", "GameFragment destroyed!")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }

        updateNextWordOnScreen()

        binding.score.text = getString(R.string.score, 0)
        binding.wordCount.text = getString(R.string.word_count, 0, MAX_NO_OF_WORDS)
    }

    private fun updateNextWordOnScreen() {
        binding.textViewUnscrambledWord.text = viewModel.currentScrambledWord
    }

    private fun onSkipWord() {
//        currentScrambledWord = getNextScrambledWord()
//        currentWordCount++
//        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
//        setErrorTextField(false)
//        updateNextWordOnScreen()
    }

    private fun onSubmitWord() {
//        currentScrambledWord = getNextScrambledWord()
//        currentWordCount++
//        score += SCORE_INCREASE
//        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
//        binding.score.text = getString(R.string.score, score)
//        setErrorTextField(false)
//        updateNextWordOnScreen()
    }

    private fun setErrorTextField(error: Boolean) {
        if(error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        }
        else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    private fun getNextScrambledWord(): String {
        val tempWord = allWordsList.random().toCharArray()
        tempWord.shuffle()
        return String(tempWord)

    }

    private fun restartGame() {
        setErrorTextField(false)
        updateNextWordOnScreen()
    }

    private fun exitGame() {
        activity?.finish()
    }

}