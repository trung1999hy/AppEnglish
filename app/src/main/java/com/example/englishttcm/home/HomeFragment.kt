/* NAM NV created on 22:08 12-4-2023 */
package com.example.englishttcm.home

import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
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
import com.example.englishttcm.learnzone.vocabulary.view.VocabularyTopicFragment
import com.example.englishttcm.log.view.LogInFragment
import com.example.englishttcm.log.viewmodel.AuthenticationViewModel
import com.example.englishttcm.playzone.SelectTypeFragment
import com.example.englishttcm.storyzone.view.StoryFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var firebaseUser: FirebaseUser

    private lateinit var listStudyTitle: ArrayList<StudyMode>
    private lateinit var listPlayMode: ArrayList<GamePlayMode>
    private lateinit var authenticationViewModel: AuthenticationViewModel
    lateinit var toggle: ActionBarDrawerToggle



    override fun getLayout(container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]

        binding.ivApp.setOnClickListener {
            authenticationViewModel.signOut()
            callback.showFragment(
                HomeFragment::class.java,
                LogInFragment::class.java,
                0,
                0,
                null,
                false
            )
        }
        firebaseUser = data as FirebaseUser
        authenticationViewModel.getUserDetail(firebaseUser.uid)
        authenticationViewModel.getUserDetail.observe(viewLifecycleOwner){
            binding.tvNameUser.setText(it.name)
            binding.tvWinCount.setText(it.win!!.toString())
            binding.tvCoinCount.setText(it.coin!!.toString())
            binding.tvTrophyCount.setText(it.trophy!!.toString())
        }

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
                    if (gameMode.mode == MULTIPLE_CHOICE) {
                        callback.showFragment(
                            HomeFragment::class.java,
                            SelectTypeFragment::class.java,
                            0,
                            0
                        )
                    }
                }
            })

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navigationView
        toggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            R.string.open_navigation,
            R.string.close_navigation
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_account -> callback.showFragment(
                    HomeFragment::class.java,
                    AccountFragment::class.java,
                    0,0,
                    firebaseUser,
                    true
                )
                R.id.nav_signout -> {
                    authenticationViewModel.signOut()
                    callback.showFragment(HomeFragment::class.java, LogInFragment::class.java,0,0,null,false)
                }
            }
            true

        }
        binding.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.ivTopicStory.setOnClickListener {
            callback.showFragment(
                HomeFragment::class.java,
                StoryFragment::class.java,
                0,
                0,
                null,
                true
            )
        }
        addBannerAds()
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

    private fun addBannerAds(){
        MobileAds.initialize(requireActivity())
        val adRequest =AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }
}