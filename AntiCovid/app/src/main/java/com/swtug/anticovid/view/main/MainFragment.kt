package com.swtug.anticovid.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.swtug.anticovid.R
import com.swtug.anticovid.repositories.PreferencesRepo

class MainFragment : Fragment() {
    private lateinit var btnProfile: Button
    private lateinit var btnAddTest: Button
    private lateinit var btnVaccineInfo: Button
    private lateinit var btnPreviousTestReports: Button
    private lateinit var btnQRCode: Button
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_main, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)
        initListeners()

    }

    private fun initListeners() {
        btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_profileFragment)
        }

        btnAddTest.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addTestReportFragment)
        }

        btnPreviousTestReports.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_testResultFragment)
        }

        btnVaccineInfo.setOnClickListener {
            val vaccination = PreferencesRepo.getVaccination(requireContext())
            if(vaccination == null) {
                findNavController().navigate(R.id.action_mainFragment_to_notVaccinatedFragment)
            } else {
                val direction = MainFragmentDirections.actionMainFragmentToVaccinatedFragment(vaccination)
                findNavController().navigate(direction)
            }
        }

        btnQRCode.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_QRCodeFragment)
        }
    }

    private fun initFields(view: View) {
        btnProfile = view.findViewById(R.id.button_profile)
        btnAddTest = view.findViewById(R.id.button_add_test_report)
        btnVaccineInfo = view.findViewById(R.id.button_vaccine_info)
        btnPreviousTestReports = view.findViewById(R.id.button_previous_test_reports)
        btnQRCode = view.findViewById(R.id.button_valid_qr_code)
    }
}