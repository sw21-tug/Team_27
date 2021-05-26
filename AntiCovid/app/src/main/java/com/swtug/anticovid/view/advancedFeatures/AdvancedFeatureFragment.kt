package com.swtug.anticovid.view.advancedFeatures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.swtug.anticovid.R


class AdvancedFeatureFragment : Fragment() {
    private lateinit var btnStat: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_advanced_feature, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)

    }

    private fun initFields(view: View) {
        btnStat = view.findViewById(R.id.button_show_statistics)
    }
}