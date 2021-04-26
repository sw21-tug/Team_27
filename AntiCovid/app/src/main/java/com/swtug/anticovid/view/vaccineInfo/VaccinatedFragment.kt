package com.swtug.anticovid.view.vaccineInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.swtug.anticovid.R
import com.swtug.anticovid.view.BaseFragment

class VaccinatedFragment : BaseFragment() {
    private val args: VaccinatedFragmentArgs by navArgs()

    private lateinit var txtManufacturer: TextInputEditText
    private lateinit var txtFirstDose: TextInputEditText
    private lateinit var txtSecondDose: TextInputEditText
    private lateinit var txtInstitution: TextInputEditText
    private lateinit var btnQRCode: MaterialButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_vaccinated, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)
        setupLayout()

        btnQRCode.setOnClickListener {
            findNavController().navigate(R.id.action_vaccinetedFragment_to_QRCodeFragment)
        }
    }

    private fun setupLayout() {
        val vaccination = args.vaccination

        txtManufacturer.setText(vaccination.manufacturor)
        txtFirstDose.setText(vaccination.firstDose.toString())
        txtSecondDose.setText(vaccination.secondDose.toString())
        txtInstitution.setText(vaccination.institution)
    }

    private fun initFields(view: View) {
        txtManufacturer = view.findViewById(R.id.textInput_manufacturer)
        txtFirstDose = view.findViewById(R.id.first_dose_date)
        txtSecondDose = view.findViewById(R.id.second_dose_date)
        txtInstitution = view.findViewById(R.id.institution)
        btnQRCode = view.findViewById(R.id.btn_show_qr_code)
    }
}