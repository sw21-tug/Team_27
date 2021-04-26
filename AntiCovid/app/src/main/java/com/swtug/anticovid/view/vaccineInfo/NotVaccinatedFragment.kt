package com.swtug.anticovid.view.vaccineInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.swtug.anticovid.R
import com.swtug.anticovid.models.Vaccination
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.BaseFragment
import java.util.*

class NotVaccinatedFragment : BaseFragment() {
    private var vaccineID = ""

    private lateinit var inputVaccineID: TextInputEditText
    private lateinit var btnAddVaccine: MaterialButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_not_vaccinated, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMember(view)
        initListeners()
    }

    private fun initListeners() {
        inputVaccineID.addTextChangedListener {
            vaccineID = it.toString()
        }

        btnAddVaccine.setOnClickListener {
            val vaccination = getVaccinationFromVaccineID()
            PreferencesRepo.saveVaccination(requireContext(), vaccination)
            val direction = NotVaccinatedFragmentDirections.actionNotVaccinatedFragmentToVaccinatedFragment(vaccination)
            findNavController().navigate(direction)
        }
    }

    private fun initMember(view: View) {
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