/* NAM NV created on 22:08 12-4-2023 */
package com.example.englishttcm.home

import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.example.englishttcm.*
import com.example.englishttcm.application.MyApplication
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.chatbot.view.ChatBotFragment
import com.example.englishttcm.home.adapter.PlayZoneAdapter
import com.example.englishttcm.home.adapter.StudyZoneAdapter
import com.example.englishttcm.home.model.GamePlayMode
import com.example.englishttcm.home.model.StudyMode
import com.example.englishttcm.learnzone.grammar.LearnGrammarFragment
import com.example.englishttcm.learnzone.listening.view.ListeningFragment
import com.example.englishttcm.learnzone.reading.LearnReadFragment
import com.example.englishttcm.learnzone.vocabulary.view.VocabularyTopicFragment
import com.example.englishttcm.bookmark.view.BookmarkWordFragment
import com.example.englishttcm.playzone.scramble.view.ScrambleFragment
import com.example.englishttcm.playzone.view.fragment.SelectTypeFragment
import com.example.englishttcm.storyzone.view.StoryFragment
import com.example.englishttcm.translate.view.TranslateActivity
import com.example.purchase.InAppPurchaseActivity
import com.tpk.englishttcm.R
import com.tpk.englishttcm.databinding.FragmentHomeBinding

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