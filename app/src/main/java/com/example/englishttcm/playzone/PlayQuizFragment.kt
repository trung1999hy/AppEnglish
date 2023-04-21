package com.example.englishttcm.playzone

import android.graphics.Color
import android.os.Handler
import android.os.SystemClock
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentPlayMultiChoiceBinding
import com.example.englishttcm.db.EnglishViewModel
import com.example.englishttcm.playzone.model.QuizMode

class PlayQuizFragment : BaseFragment<FragmentPlayMultiChoiceBinding>() {

    private lateinit var englishViewModel: EnglishViewModel
    private var pos = 0
    private var timer = 10
    private var listAns = arrayListOf<String>()


    override fun getLayout(container: ViewGroup?): FragmentPlayMultiChoiceBinding =
        FragmentPlayMultiChoiceBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        englishViewModel = ViewModelProvider(this)[EnglishViewModel::class.java]

//        englishViewModel.readAllQuestionVocab.observe(viewLifecycleOwner, Observer {
//            listAns.add(it[2].incorrectAnswerOne)
//            listAns.add(it[2].incorrectAnswerThree)
//            listAns.add(it[2].incorrectAnswerThree)
//            listAns.add(it[2].correctAnswer)
//            listAns.shuffle()
//            binding.tvAnsA.text = listAns[0]
//            binding.tvAnsB.text = listAns[1]
//            binding.tvAnsC.text = listAns[2]
//            binding.tvAnsD.text = listAns[3]
//            binding.tvWord.text = it[2].question
//        })
//        countDown()
//        listener()
    }

    private fun listener() {
        checkCorrectAnswer(binding.cvAnsA)
    }

    private fun shuffleQuestion(){

    }
    private fun nextQuestion(){

    }

    private fun checkCorrectAnswer(cardView: CardView) {
        onClick(binding.cvAnsA)
        onClick(binding.cvAnsB)
        onClick(binding.cvAnsC)
        onClick(binding.cvAnsD)
    }
    private fun onClick( cardView: CardView){
        cardView.setOnClickListener {
            Handler().postDelayed({
                cardView.setCardBackgroundColor(Color.YELLOW)
            }, 0)
        }
    }
    private fun countDown() {
        binding.viewTimer.isCountDown = true
        binding.viewTimer.base = SystemClock.elapsedRealtime() + timer * 1000
        binding.viewTimer.start()
        binding.viewTimer.setOnChronometerTickListener {
            if (binding.viewTimer.text == "00:00") {
                binding.viewTimer.stop()
                //to do dialog
            }
        }
    }


}