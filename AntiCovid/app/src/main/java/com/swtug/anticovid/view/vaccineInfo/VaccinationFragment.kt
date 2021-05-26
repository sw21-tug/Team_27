package com.swtug.anticovid.view.vaccineInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.swtug.anticovid.R
import com.swtug.anticovid.models.Vaccination
import com.swtug.anticovid.repositories.PreferencesRepo
import java.util.*

class VaccinationFragment : Fragment() {
    private var vaccineID = ""

    private lateinit var layoutNotVaccinated: LinearLayout
    private lateinit var inputVaccineID: TextInputEditText
    private lateinit var btnAddVaccine: MaterialButton

    private lateinit var layoutVaccinated: LinearLayout
    private lateinit var txtManufacturer: TextInputEditText
    private lateinit var txtFirstDose: TextInputEditText
    private lateinit var txtSecondDose: TextInputEditText
    private lateinit var txtInstitution: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_vaccination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)
        initListeners()
        setupLayout()
    }

    private fun initListeners() {
        inputVaccineID.addTextChangedListener {
            vaccineID = it.toString()
        }

        btnAddVaccine.setOnClickListener {
            val vaccination = getVaccinationFromVaccineID()
            PreferencesRepo.saveVaccination(requireContext(), vaccination)
            setupLayout()
        }
    }

    private fun setupLayout() {
        PreferencesRepo.getVaccination(requireContext())?.run {
            layoutVaccinated.visibility = View.VISIBLE
            layoutNotVaccinated.visibility = View.GONE

            txtManufacturer.setText(manufacturor)
            txtFirstDose.setText(firstDose.toString())
            txtSecondDose.setText(secondDose.toString())
            txtInstitution.setText(institution)
        } ?: kotlin.run {
            layoutVaccinated.visibility = View.GONE
            layoutNotVaccinated.visibility = View.VISIBLE
        }
    }

    private fun initFields(view: View) {
        layoutVaccinated = view.findViewById(R.id.layout_vaccinated)
        txtManufacturer = view.findViewById(R.id.textInput_manufacturer)
        txtFirstDose = view.findViewById(R.id.first_dose_date)
        txtSecondDose = view.findViewById(R.id.second_dose_date)
        txtInstitution = view.findViewById(R.id.institution)

        layoutNotVaccinated = view.findViewById(R.id.layout_not_vaccinated)
        inputVaccineID = view.findViewById(R.id.textInput_vaccine_id)
        btnAddVaccine = view.findViewById(R.id.button_add_vaccine)
    }

    private fun getVaccinationFromVaccineID(): Vaccination {
        //TODO check if vaccineID is valid
        return Vaccination(
            "Default Manufacturer",
            Date(),
            Date(),
            "Default Institution"
        )
    }
}