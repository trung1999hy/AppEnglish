package com.example.englishttcm.translate.view

import android.content.Context
import com.example.englishttcm.R
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentTranslateBinding
import com.example.englishttcm.storyzone.viewmodel.StoryViewModel
import com.example.englishttcm.translate.viewmodel.TranslateViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject


class TranslateFragment : BaseFragment<FragmentTranslateBinding>(),OnItemSelectedListener {
    private lateinit var translateViewModel: TranslateViewModel


    override fun getLayout(container: ViewGroup?): FragmentTranslateBinding =
        FragmentTranslateBinding.inflate(layoutInflater, container, false)

    private var srcLangId = 0
    private var tarLangId = 1
    override fun initViews() {
        translateViewModel = ViewModelProvider(this)[TranslateViewModel::class.java]
        val sourceSpnAdpt = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("Tiếng Anh", "Tiếng Việt")
        )
        sourceSpnAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnSource.adapter = sourceSpnAdpt
        binding.spnSource.onItemSelectedListener = this
        val targetSpnAdpt = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("Tiếng Anh", "Tiếng Việt")
        )
        targetSpnAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnTarget.adapter = targetSpnAdpt
        binding.spnTarget.onItemSelectedListener = this
        binding.spnSource.setSelection(0)
        binding.spnTarget.setSelection(1)
        binding.btnTranslate.setOnClickListener {
            val sourceText = binding.edSourcetext.text.toString()
            translateViewModel.getTranslateText(srcLangId,tarLangId,sourceText).observe(viewLifecycleOwner){
                binding.edTargettext.setText(it)
            }
        }



    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p0?.id) {
            R.id.spn_source -> {
                if (p2 == tarLangId) {
                    tarLangId = srcLangId
                }
                srcLangId = p2
            }
            R.id.spn_target -> {
                if (p2 == srcLangId) {
                    srcLangId = tarLangId
                }
                tarLangId = p2
            }
        }
        binding.spnSource.setSelection(srcLangId)
        binding.spnTarget.setSelection(tarLangId)
        binding.edSourcetext.setText("")
        binding.edTargettext.setText("")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onResume() {
        super.onResume()
        var fab = activity?.findViewById<FloatingActionButton>(R.id.fabTranslate)
        fab!!.visibility = View.INVISIBLE
    }

}



