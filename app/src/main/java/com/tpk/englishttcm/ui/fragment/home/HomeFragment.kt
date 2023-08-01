/* NAM NV created on 22:08 12-4-2023 */
package com.tpk.englishttcm.ui.fragment.home

import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.tpk.englishttcm.application.MyApplication
import com.tpk.englishttcm.base.BaseFragment
import com.tpk.englishttcm.ui.fragment.chatbot.ChatBotFragment
import com.tpk.englishttcm.ui.adapter.PlayZoneAdapter
import com.tpk.englishttcm.ui.adapter.StudyZoneAdapter
import com.tpk.englishttcm.model.GamePlayMode
import com.tpk.englishttcm.model.StudyMode
import com.tpk.englishttcm.ui.fragment.grammar.LearnGrammarFragment
import com.tpk.englishttcm.ui.fragment.listening.ListeningFragment
import com.tpk.englishttcm.ui.fragment.reading.LearnReadFragment
import com.tpk.englishttcm.ui.fragment.vocabulary.VocabularyTopicFragment
import com.tpk.englishttcm.ui.fragment.bookmark.BookmarkWordFragment
import com.tpk.englishttcm.ui.fragment.game.scramble.ScrambleFragment
import com.tpk.englishttcm.ui.fragment.game.quiz.SelectTypeFragment
import com.tpk.englishttcm.ui.fragment.book.StoryFragment
import com.tpk.englishttcm.ui.activity.TranslateActivity
import com.tpk.purchase.InAppPurchaseActivity
import com.tpk.englishttcm.R
import com.tpk.englishttcm.callback.OnItemClickListener
import com.tpk.englishttcm.databinding.FragmentHomeBinding
import com.tpk.englishttcm.utils.Constant.GRAMMAR
import com.tpk.englishttcm.utils.Constant.LISTENING
import com.tpk.englishttcm.utils.Constant.MULTIPLE_CHOICE
import com.tpk.englishttcm.utils.Constant.READING
import com.tpk.englishttcm.utils.Constant.SCRAMBLE
import com.tpk.englishttcm.utils.Constant.VOCABULARY

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var listStudyTitle: ArrayList<StudyMode>
    private lateinit var listPlayMode: ArrayList<GamePlayMode>
    private val currentCoin = MyApplication.getInstance().getPreference().getValueCoin()



    override fun getLayout(container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        binding.tvCoinCount.text = currentCoin.toString()
        binding.btnBuyCoin.setOnClickListener {
            startActivity(Intent(context, InAppPurchaseActivity::class.java))
        }
        binding.fabTranslate.setOnClickListener {
            startActivity(Intent(context, TranslateActivity::class.java))
        }

        setLearnData()
        setPlayMode()
        Log.i("list", listStudyTitle.size.toString())
        binding.rcvStudyZone.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        binding.rcvStudyZone.adapter =
            StudyZoneAdapter(requireContext(), listStudyTitle, object : OnItemClickListener {
                override fun onItemClick(data: Any?) {
                    val studyMode = data as StudyMode
                    if (studyMode.title == VOCABULARY) {
                        callback.showFragment(
                            HomeFragment::class.java,
                            VocabularyTopicFragment::class.java,
                            0,
                            0,
                            studyMode,
                            true
                        )
                    }
                    if (studyMode.title == GRAMMAR) {
                        callback.showFragment(
                            HomeFragment::class.java,
                            LearnGrammarFragment::class.java,
                            0,
                            0,
                            studyMode,
                            true
                        )
                    }
                    if (studyMode.title == LISTENING) {
                        callback.showFragment(
                            HomeFragment::class.java,
                            ListeningFragment::class.java,
                            0,
                            0,
                            studyMode,
                            true
                        )
                    }
                    if (studyMode.title == READING) {
                        callback.showFragment(
                            HomeFragment::class.java,
                            LearnReadFragment::class.java,
                            0,
                            0,
                            studyMode,
                            true
                        )
                    }
                }
            })

        binding.rcvPlayZone.adapter =
            PlayZoneAdapter(requireContext(), listPlayMode, object : OnItemClickListener {
                override fun onItemClick(data: Any?) {
                    val gameMode = data as GamePlayMode
                    if (gameMode.mode == MULTIPLE_CHOICE) {
                        callback.showFragment(
                            HomeFragment::class.java,
                            SelectTypeFragment::class.java,
                            0,
                            0
                        ,null, true
                        )
                    }
                    if(gameMode.mode == SCRAMBLE){
                        callback.showFragment(
                            HomeFragment::class.java,
                            ScrambleFragment::class.java,
                            0,
                            0,
                            gameMode,
                            true
                        )
                    }
                }
            })

        binding.btnBook.setOnClickListener {
            callback.showFragment(
                HomeFragment::class.java,
                StoryFragment::class.java,
                0,
                0,
                null,
                true
            )
        }
        binding.btnChatBot.setOnClickListener {
            callback.showFragment(
                HomeFragment::class.java,
                ChatBotFragment::class.java,
                0,
                0,
                null,
                true
            )
        }
        binding.btnBookmark.setOnClickListener {
            callback.showFragment(
                HomeFragment::class.java,
                BookmarkWordFragment::class.java,
                0,
                0,
                null,
                true
            )
        }
    }

    private fun setPlayMode() {
        listPlayMode = arrayListOf()
        listPlayMode.add(GamePlayMode(R.drawable.bg_study_zone_green, "Multiple Choice", R.drawable.multiple_choice))
        listPlayMode.add(GamePlayMode(R.drawable.header_home_background, "Scramble", R.drawable.fill_blank))
    }

    private fun setLearnData() {
        listStudyTitle = arrayListOf()
        listStudyTitle.add(StudyMode(R.drawable.bg_study_zone_green, "Vocabulary", R.drawable.img_vocabulary))
        listStudyTitle.add(StudyMode(R.drawable.bg_study_zone_yellow, "Listening", R.drawable.img_vocabulary))
    }


    override fun onResume() {
        super.onResume()
        binding.tvCoinCount.text = MyApplication.getInstance().getPreference().getValueCoin().toString()
    }



}