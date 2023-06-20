package com.example.englishttcm.playzone.scramble.view

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.R
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentScrambleGameBinding
import com.example.englishttcm.playzone.scramble.adapter.ScrambleAdapter
import com.example.englishttcm.playzone.scramble.viewmodel.ScrambleViewModel
import com.example.englishttcm.playzone.view.dialog.LoseQuizDialog
import com.example.englishttcm.playzone.view.dialog.OnBackListener
import com.example.englishttcm.playzone.view.dialog.WinQuizDialog


class ScrambleFragment : BaseFragment<FragmentScrambleGameBinding>() {

    private lateinit var scrambleViewModel: ScrambleViewModel
    private var handlerWin: Handler? = null
    private var handlerLose: Handler? = null
    override fun getLayout(container: ViewGroup?): FragmentScrambleGameBinding =
        FragmentScrambleGameBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        handlerWin = Handler(Looper.getMainLooper())
        handlerLose = Handler(Looper.getMainLooper())

        scrambleViewModel = ViewModelProvider(this)[ScrambleViewModel::class.java]
        scrambleViewModel.getWords().observe(viewLifecycleOwner) {
            binding.vp2Scramble.adapter = ScrambleAdapter(it)
        }
        binding.btnSubmit.setOnClickListener { onSubmit() }
        binding.btnSkip.setOnClickListener { onSkip() }
        binding.vp2Scramble.isUserInputEnabled = false
        scrambleViewModel.score.observe(viewLifecycleOwner) {
            "Score: $it".also { binding.tvScore.text = it }

        }
        scrambleViewModel.currentWordCount.observe(viewLifecycleOwner) {
            "You scramble $it words!".also { binding.tvAnswered.text = it }
        }
        scrambleViewModel.isOriginal.observe(viewLifecycleOwner) {
            if (it) {
                val currentItem = binding.vp2Scramble.currentItem
                val itemCount = binding.vp2Scramble.adapter?.itemCount ?: 0
                if (currentItem == itemCount - 1) {
                    scrambleViewModel.checkWin()
                } else {
                    binding.vp2Scramble.currentItem += 1
                    binding.edtYourWord.setText("")
                }
                setErrorTextField(false)
            } else {
                setErrorTextField(true)
            }
        }
        scrambleViewModel.isWin.observe(viewLifecycleOwner) {
            if (it) {
                win()
            } else {
                lose()
            }
        }
    }

    private fun onSkip() {
        scrambleViewModel.isSkip()
        setErrorTextField(false)
    }

    private fun onSubmit() {
        val answered = binding.edtYourWord.text.toString()
        val currentIndex = binding.vp2Scramble.currentItem
        val adapter = binding.vp2Scramble.adapter as ScrambleAdapter
        val currentItem = adapter.getItem(currentIndex)
        scrambleViewModel.checkTrue(answered, currentItem.original.toString())
        notify(currentIndex.toString() + currentItem.original)
    }

    private fun lose() {
        val dialog = LoseQuizDialog(object : OnBackListener {
            override fun onBack() {
                callback.backToPrevious()
            }
        })
        handlerLose!!.postDelayed({
            dialog.show(requireActivity().supportFragmentManager, "lose_dialog")
        }, 100)
    }

    private fun win() {
        val dialog = WinQuizDialog(object : OnBackListener {
            override fun onBack() {
                callback.backToPrevious()
            }
        })
        handlerWin!!.postDelayed({
            dialog.show(requireActivity().supportFragmentManager, "win_dialog")
        }, 100)
    }

    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.layoutInput.isErrorEnabled = true
            binding.layoutInput.error = getString(R.string.try_again)
        } else {
            binding.layoutInput.isErrorEnabled = false
            binding.edtYourWord.text = null
        }
    }

}