/* NAM NV created on 22:08 12-4-2023 */
package com.example.englishttcm.home

import android.util.Log
import android.view.ViewGroup
import com.example.englishttcm.*
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentHomeBinding
import com.example.englishttcm.home.adapter.PlayZoneAdapter
import com.example.englishttcm.home.adapter.StudyZoneAdapter
import com.example.englishttcm.home.model.GamePlayMode
import com.example.englishttcm.home.model.StudyMode
import com.example.englishttcm.learnzone.grammar.LearnGrammarFragment
import com.example.englishttcm.learnzone.learning.LearnListenFragment
import com.example.englishttcm.learnzone.reading.LearnReadFragment
import com.example.englishttcm.learnzone.vocabulary.LearnVocabularyFragment
import com.example.englishttcm.playzone.SelectTypeFragment
import com.google.firebase.auth.FirebaseUser

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var firebaseUser: FirebaseUser

    private lateinit var listStudyTitle: ArrayList<StudyMode>
    private lateinit var listPlayMode: ArrayList<GamePlayMode>

    override fun getLayout(container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        firebaseUser = data as FirebaseUser
        Log.i("id", firebaseUser.uid)
        setLearnData()
        setPlayMode()
        Log.i("list", listStudyTitle.size.toString())
        binding.rcvStudyZone.adapter =
            StudyZoneAdapter(requireContext(), listStudyTitle, object : OnItemClickListener {
                override fun onItemClick(data: Any?) {
                    val studyMode = data as StudyMode
                    if (studyMode.title == VOCABULARY) {
                        callback.showFragment(
                            HomeFragment::class.java,
                            LearnVocabularyFragment::class.java,
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
                            LearnListenFragment::class.java,
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
                    if(gameMode.mode == MULTIPLE_CHOICE){
                        callback.showFragment(HomeFragment::class.java, SelectTypeFragment::class.java,0,0)
                    }
                }
            })
    }



    private fun setPlayMode() {
        listPlayMode = arrayListOf()
        listPlayMode.add(GamePlayMode(R.drawable.bg_study_zone_green, "Multiple Choice", R.drawable.multiple_choice))
        listPlayMode.add(GamePlayMode(R.drawable.header_home_background, "Scramble", R.drawable.fill_blank))
    }

    private fun setLearnData() {
        listStudyTitle = arrayListOf()
        listStudyTitle.add(StudyMode(R.drawable.bg_study_zone_green, "Vocabulary", R.drawable.img_vocabulary))
        listStudyTitle.add(StudyMode(R.drawable.bg_study_zone_pink, "Grammar", R.drawable.img_vocabulary))
        listStudyTitle.add(StudyMode(R.drawable.bg_study_zone_yellow, "Listening", R.drawable.img_vocabulary))
        listStudyTitle.add(StudyMode(R.drawable.header_home_background, "Reading", R.drawable.img_vocabulary))
    }
}