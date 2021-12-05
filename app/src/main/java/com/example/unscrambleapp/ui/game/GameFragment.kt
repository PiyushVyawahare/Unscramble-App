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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }

        viewModel.currentScrambledWord.observe(viewLifecycleOwner,
            {newWord ->
                binding.textViewUnscrambledWord.text = newWord
            })

        viewModel.score.observe(viewLifecycleOwner,
            {newScore ->
                binding.score.text = getString(R.string.score, newScore)
            })

        viewModel.currentWordCount.observe(viewLifecycleOwner,
            {newWordCount ->
                binding.wordCount.text = getString(R.string.word_count, newWordCount, MAX_NO_OF_WORDS)
            })
    }

//    private fun updateNextWordOnScreen() {
//        binding.textViewUnscrambledWord.text = viewModel.currentScrambledWord
//    }

    private fun onSkipWord() {
        if (viewModel.nextWord()) {
            setErrorTextField(false)
        } else {
            showFinalScoreDialog()
        }
    }

    private fun onSubmitWord() {

        val playerWord = binding.textInputEditText.text.toString()

        if(viewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)
            if (!viewModel.nextWord()) {
                showFinalScoreDialog()
            }
        } else {
            setErrorTextField(true)
        }
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
        viewModel.reinitializeData()
        setErrorTextField(false)
    }

    private fun exitGame() {
        activity?.finish()
    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }

}