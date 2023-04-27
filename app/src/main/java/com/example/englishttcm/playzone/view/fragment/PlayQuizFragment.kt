package com.example.englishttcm.playzone.view.fragment

import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.R
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentPlayMultiChoiceBinding
import com.example.englishttcm.playzone.model.Quiz
import com.example.englishttcm.playzone.view.dialog.LoseQuizDialog
import com.example.englishttcm.playzone.view.dialog.OnBackListener
import com.example.englishttcm.playzone.view.dialog.WinQuizDialog
import com.example.englishttcm.playzone.viewmodel.QuizViewModel
import com.google.android.material.card.MaterialCardView

class PlayQuizFragment : BaseFragment<FragmentPlayMultiChoiceBinding>() {

    private lateinit var quizViewModel: QuizViewModel
    private var listAnswer = arrayListOf<String>()
    private var handlerAnswered: Handler? = null
    private var handlerNextQuiz: Handler? = null
    private var handlerWin: Handler? = null
    private var handlerLose: Handler? = null
    private lateinit var countDownTimer: CountDownTimer
    private var isDown: Boolean = true

    companion object{
        var mediaBg = MediaPlayer()
    }
    private var mediaWrong = MediaPlayer()
    private var mediaCorrect = MediaPlayer()
    private var mediaTouch = MediaPlayer()
    override fun getLayout(container: ViewGroup?): FragmentPlayMultiChoiceBinding =
        FragmentPlayMultiChoiceBinding.inflate(layoutInflater, container, false)

    override fun initViews() {

        mediaBg = MediaPlayer.create(mContext, R.raw.main)
        mediaBg.start()
        mediaBg.isLooping = true

        handlerAnswered = Handler(Looper.getMainLooper())
        handlerNextQuiz = Handler(Looper.getMainLooper())
        handlerWin = Handler(Looper.getMainLooper())
        handlerLose = Handler(Looper.getMainLooper())

        quizViewModel = ViewModelProvider(this)[QuizViewModel::class.java]
        quizViewModel.currentQuiz.observe(viewLifecycleOwner) { quiz ->
            metaData(quiz)
            countdown(15000)

            ansClicked(
                binding.cvAnsA,
                binding.cvAnsB,
                binding.cvAnsC,
                binding.cvAnsD,
                binding.tvAnsA,
                quiz.trueAnswer
            )
            ansClicked(
                binding.cvAnsB,
                binding.cvAnsA,
                binding.cvAnsC,
                binding.cvAnsD,
                binding.tvAnsB,
                quiz.trueAnswer
            )
            ansClicked(
                binding.cvAnsC,
                binding.cvAnsA,
                binding.cvAnsB,
                binding.cvAnsD,
                binding.tvAnsC,
                quiz.trueAnswer
            )
            ansClicked(
                binding.cvAnsD,
                binding.cvAnsB,
                binding.cvAnsC,
                binding.cvAnsA,
                binding.tvAnsD,
                quiz.trueAnswer
            )

        }

        binding.ivFiftyfifty.setOnClickListener() {
            binding.ivFiftyfifty.isVisible = false
            supportFiftyFiftyClicked()
        }

        binding.ivBack.setOnClickListener {
            backToBackStack()
        }

    }

    private fun resetColor(bgColor: LinearLayout) {
        bgColor.background = ContextCompat.getDrawable(mContext, R.color.white)
    }

    private fun setColorAnswered(bgColor: LinearLayout) {
        bgColor.background = ContextCompat.getDrawable(mContext, R.color.yellow)
    }

    private fun setTrueColor(bgColor: LinearLayout) {
        bgColor.background = ContextCompat.getDrawable(mContext, R.color.green)
    }

    private fun setFalseColor(bgColor: LinearLayout) {
        bgColor.background = ContextCompat.getDrawable(mContext, R.color.red)
    }

    private fun setMediaTrue() {
        mediaCorrect = MediaPlayer.create(mContext, R.raw.correct)
        mediaCorrect.start()
    }

    private fun setMediaFalse() {
        mediaWrong = MediaPlayer.create(mContext, R.raw.wrong)
        mediaWrong.start()
    }

    private fun fiftyFiftyClicked(
    ) {
        binding.ivFiftyfifty.setOnClickListener() {
            backToBackStack()
        }
    }

    private fun ansClicked(
        cvClicked: MaterialCardView,
        cv1: MaterialCardView,
        cv2: MaterialCardView,
        cv3: MaterialCardView,
        tvAnswered: TextView,
        trueAns: String
    ) {
        cvClicked.setOnClickListener {
            mediaTouch = MediaPlayer.create(mContext, R.raw.touch)
            mediaTouch.start()
            when (cvClicked) {
                binding.cvAnsA -> {
                    setColorAnswered(binding.lnA)
                    stopTime()
                }
                binding.cvAnsB -> {
                    setColorAnswered(binding.lnB)
                    stopTime()
                }
                binding.cvAnsC -> {
                    setColorAnswered(binding.lnC)
                    stopTime()
                }
                binding.cvAnsD -> {
                    setColorAnswered(binding.lnD)
                    stopTime()
                }
            }
            cv1.isEnabled = false
            cv2.isEnabled = false
            cv3.isEnabled = false
            cvClicked.isEnabled = false
            handlerAnswered!!.postDelayed({
                if (quizViewModel.checkTrue(tvAnswered.text.toString(), trueAns)) {
                    when (cvClicked) {
                        binding.cvAnsA -> {
                            setTrueColor(binding.lnA)
                            setMediaTrue()
                        }
                        binding.cvAnsB -> {
                            setTrueColor(binding.lnB)
                            setMediaTrue()
                        }
                        binding.cvAnsC -> {
                            setTrueColor(binding.lnC)
                            setMediaTrue()
                        }
                        binding.cvAnsD -> {
                            setTrueColor(binding.lnD)
                            setMediaTrue()
                        }
                    }
                    if(!quizViewModel.checkWin()){
                        moveToNext()
                    } else {
                        win()
                    }
                } else {
                    when (cvClicked) {
                        binding.cvAnsA -> {
                            setFalseColor(binding.lnA)
                            setMediaFalse()
                        }
                        binding.cvAnsB -> {
                            setFalseColor(binding.lnB)
                            setMediaFalse()
                        }
                        binding.cvAnsC -> {
                            setFalseColor(binding.lnC)
                            setMediaFalse()
                        }
                        binding.cvAnsD -> {
                            setFalseColor(binding.lnD)
                            setMediaFalse()
                        }
                    }
                    lose()
                }
            }, 2500)
        }
    }

    private fun lose() {
        val dialog = LoseQuizDialog(object: OnBackListener{
            override fun onBack() {
                callback.backToPrevious()
            }
        })
        handlerLose!!.postDelayed({
            dialog.show(requireActivity().supportFragmentManager, "lose_dialog")
        }, 1500)
    }

    private fun win() {
        val dialog = WinQuizDialog(object: OnBackListener{
            override fun onBack() {
                callback.backToPrevious()
            }
        })
        handlerWin!!.postDelayed({
            dialog.show(requireActivity().supportFragmentManager, "win_dialog")
        },2000)
    }

    private fun moveToNext() {
        handlerNextQuiz!!.postDelayed({
            quizViewModel.next()
            stopTime()
            resetView()
            countdown(15000)
        }, 3000)
    }

    private fun resetView() {
        enableView(binding.cvAnsA)
        enableView(binding.cvAnsB)
        enableView(binding.cvAnsC)
        enableView(binding.cvAnsD)
        resetColorCardView()
    }

    private fun enableView(view: MaterialCardView) {
        if (!view.isEnabled) {
            view.isEnabled = true
        }
    }

    private fun resetColorCardView() {
        resetColor(binding.lnA)
        resetColor(binding.lnB)
        resetColor(binding.lnC)
        resetColor(binding.lnD)
    }

    private fun metaData(quiz: Quiz) {
        listAnswer = quizViewModel.shuffle(
            quiz.trueAnswer,
            quiz.incorrectAnsOne,
            quiz.incorrectAnsTwo,
            quiz.incorrectAnsThree
        )
        binding.tvWord.text = quiz.word
        binding.tvAnsA.text = listAnswer[0]
        binding.tvAnsB.text = listAnswer[1]
        binding.tvAnsC.text = listAnswer[2]
        binding.tvAnsD.text = listAnswer[3]
    }

    private fun backToBackStack() {
        callback.backToPrevious()
    }

    private fun stopTime(){
        countDownTimer.cancel()
    }

    private fun countdown(time: Long) {
        if (isDown) {
            countDownTimer = object : CountDownTimer(time, 1000) {
                override fun onTick(p0: Long) {
                    isDown = true
                    binding.timer.text = (p0 / 1000).toString()
                }

                override fun onFinish() {
                    lose()
                }
            }.start()
        } else {
            isDown = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (mediaBg.isPlaying) {
            mediaBg.stop()
        }
        stopTime()
        handlerAnswered!!.removeCallbacksAndMessages(null)
        handlerNextQuiz!!.removeCallbacksAndMessages(null)
        handlerLose!!.removeCallbacksAndMessages(null)
        handlerWin!!.removeCallbacksAndMessages(null)
    }

    private fun supportFiftyFiftyClicked() {
        quizViewModel.fiftyFiftySupport(
            binding.tvAnsA,
            binding.tvAnsB,
            binding.tvAnsC,
            binding.tvAnsD
        )
        quizViewModel.fiftyFiftySupport(
            binding.tvAnsB,
            binding.tvAnsA,
            binding.tvAnsC,
            binding.tvAnsD
        )
        quizViewModel.fiftyFiftySupport(
            binding.tvAnsC,
            binding.tvAnsA,
            binding.tvAnsB,
            binding.tvAnsD
        )
        quizViewModel.fiftyFiftySupport(
            binding.tvAnsD,
            binding.tvAnsA,
            binding.tvAnsB,
            binding.tvAnsC
        )
    }

}