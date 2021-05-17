package com.swtug.anticovid.view.advancedFeatures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.zxing.WriterException
import com.swtug.anticovid.view.BaseFragment
import com.swtug.anticovid.R


class AdvancedFeatureFragment : BaseFragment() {
    private lateinit var btnStat: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_advanced_feature, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)
        initListeners()

    }

    private fun initListeners() {
        btnStat.setOnClickListener {
            findNavController().navigate(R.id.action_advancedFeatureFragment_to_statisticFragment)
        }
    }

    private fun initFields(view: View) {
        btnStat = view.findViewById(R.id.button_show_statistics)
    }
}