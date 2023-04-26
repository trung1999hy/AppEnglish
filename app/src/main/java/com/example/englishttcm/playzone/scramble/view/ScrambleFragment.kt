package com.example.englishttcm.playzone.scramble.view


import android.media.MediaPlayer
import android.os.Handler
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentScrambleGameBinding
import com.example.englishttcm.playzone.scramble.adapter.ScrambleAdapter
import com.example.englishttcm.playzone.scramble.viewmodel.ScrambleViewModel


class ScrambleFragment : BaseFragment<FragmentScrambleGameBinding>() {

    private lateinit var scrambleViewModel: ScrambleViewModel
    override fun getLayout(container: ViewGroup?): FragmentScrambleGameBinding
    = FragmentScrambleGameBinding.inflate(layoutInflater,container,false)

    override fun initViews() {
        scrambleViewModel = ViewModelProvider(this)[ScrambleViewModel::class.java]
        scrambleViewModel.getWords().observe(viewLifecycleOwner) {
            binding.vp2Scramble.adapter = ScrambleAdapter(it)
        }
        binding.btnSubmit.setOnClickListener { onSubmit() }
        binding.btnSkip.setOnClickListener { onSkip() }
   }

    private fun onSkip() {

    }

    private fun onSubmit() {
        val answered = binding.tvAnswered.text.toString()

    }
//
//    private fun onSubmit() {
//        val playWord = binding.edtYourWord.text.toString()
//        if (scrambleViewModel.isUserWordCorrect(playWord)) {
//            setErrorTextField(false)
//            if (!scrambleViewModel.nextWord()) {
//                //showFinalScoreDialog()
//            }
//        } else {
//            setErrorTextField(true)
//        }
//    }
//    private fun onSkip() {
//        if(scrambleViewModel.nextWord()) {
//            setErrorTextField(false)
//        } else {
//          //  showFinalScoreDialog()
//        }
//    }

//    private fun showFinalScoreDialog() {
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle(getString(R.string.congratulations))
//            .setMessage(getString(R.string.you_scored, scrambleViewModel.score.value))
//            .setCancelable(false)
//            .setNegativeButton(getString(R.string.exit)) { _, _ ->
//                exitGame()
//            }
//            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
//                restartGame()
//            }
//            .show()
//    }

//    private fun restartGame() {
//        scrambleViewModel.reinitializeData()
//        setErrorTextField(false)
//    }
//
//    private fun exitGame() {
//
//    }
//    private fun setErrorTextField(error: Boolean) {
//        if (error) {
//            binding.layoutInput.isErrorEnabled = true
//            binding.layoutInput.error = getString(R.string.try_again)
//        } else {
//            binding.layoutInput.isErrorEnabled = false
//            binding.edtYourWord.text = null
//        }
//    }

}