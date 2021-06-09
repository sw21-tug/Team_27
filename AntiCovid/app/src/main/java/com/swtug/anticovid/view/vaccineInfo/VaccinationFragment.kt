package com.swtug.anticovid.view.vaccineInfo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.swtug.anticovid.R
import com.swtug.anticovid.models.Vaccination
import com.swtug.anticovid.repositories.FirebaseRepo
import com.swtug.anticovid.repositories.FirebaseVaccinationListener
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.utils.DateTimeUtils
import java.time.LocalDateTime

class VaccinationFragment : Fragment() {
    private var loggedInUserEmail = ""

    private lateinit var btnAddVaccine: FloatingActionButton

    private lateinit var layoutVaccinated: LinearLayout
    private lateinit var txtManufacturer: TextInputEditText
    private lateinit var txtFirstDose: TextInputEditText
    private lateinit var txtSecondDose: TextInputEditText
    private lateinit var txtInstitution: TextInputEditText

    private lateinit var inputLayoutFirstDose : TextInputLayout
    private lateinit var inputLayoutSecondDose : TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_vaccination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loggedInUser = PreferencesRepo.getUser(requireContext())
        loggedInUser?.let {
            loggedInUserEmail = loggedInUser.email
        }

        initFields(view)
        initListeners()
        FirebaseRepo.getVaccination(loggedInUserEmail, firebaseVaccinationLoadListener)
    }

    private val firebaseVaccinationUpdateListener = object : FirebaseVaccinationListener {
        override fun onSuccess(vaccination: Vaccination?) {
            btnAddVaccine.isEnabled = true
            Toast.makeText(
                requireContext(),
                getString(R.string.updated_vaccination),
                Toast.LENGTH_LONG
            ).show()
            PreferencesRepo.saveVaccinated(requireContext(), vaccination != null)
            setupLayout(vaccination)
        }

        override fun onStart() {
            btnAddVaccine.isEnabled = false
        }

        override fun onFailure() {
            btnAddVaccine.isEnabled = true
            Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.error_firebase_communication),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private val firebaseVaccinationLoadListener = object : FirebaseVaccinationListener {
        override fun onSuccess(vaccination: Vaccination?) {
            btnAddVaccine.isEnabled = true
            PreferencesRepo.saveVaccinated(requireContext(), vaccination != null)
            setupLayout(vaccination)
        }

        override fun onStart() {
            btnAddVaccine.isEnabled = false
        }

        override fun onFailure() {
            btnAddVaccine.isEnabled = true
            Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.error_firebase_communication),
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun initListeners() {

        btnAddVaccine.setOnClickListener {
            val vaccination = getVaccination()
            if(vaccination != null) {
                FirebaseRepo.saveOrUpdateVaccination(loggedInUserEmail, vaccination, firebaseVaccinationUpdateListener)
            } else {

            }
        }

        inputLayoutFirstDose.setEndIconOnClickListener {
            txtFirstDose.text?.clear()
        }

        inputLayoutFirstDose.setStartIconOnClickListener {
            pickDateAndTimeFor(txtFirstDose)
        }

        inputLayoutSecondDose.setEndIconOnClickListener {
            txtSecondDose.text?.clear()
        }

        inputLayoutSecondDose.setStartIconOnClickListener {
            pickDateAndTimeFor(txtSecondDose)
        }
    }

    private fun setupLayout(vaccination: Vaccination?) {
        if(vaccination != null) {
            txtManufacturer.setText(vaccination.manufacturor)
            txtFirstDose.setText(vaccination.firstDose)
            txtSecondDose.setText(vaccination.secondDose)
            txtInstitution.setText(vaccination.institution)
        } else {
            txtManufacturer.text?.clear()
            txtFirstDose.text?.clear()
            txtSecondDose.text?.clear()
            txtInstitution.text?.clear()
        }
    }

    private fun initFields(view: View) {
        layoutVaccinated = view.findViewById(R.id.layout_vaccinated)
        txtManufacturer = view.findViewById(R.id.textInput_manufacturer)
        txtFirstDose = view.findViewById(R.id.first_dose_date)
        txtSecondDose = view.findViewById(R.id.second_dose_date)
        txtInstitution = view.findViewById(R.id.institution)

        inputLayoutFirstDose = view.findViewById(R.id.inputLayoutFirstDose)
        inputLayoutSecondDose = view.findViewById(R.id.inputLayoutSecondDose)

        btnAddVaccine = view.findViewById(R.id.button_add_vaccine)
    }

    private fun getVaccination(): Vaccination? {

        if (txtManufacturer.text.isNullOrBlank() ||
            txtFirstDose.text.isNullOrBlank() ||
            txtInstitution.text.isNullOrBlank()) {
            Toast.makeText(requireContext(), getString(R.string.error_empty_fields), Toast.LENGTH_LONG).show()
            return null
        }

        if(!txtFirstDose.text.isNullOrBlank() &&
                !txtSecondDose.text.isNullOrBlank()) {
            val firstDoseDate = DateTimeUtils.getDateFromString(txtFirstDose.text.toString())
            val secondDoseDate = DateTimeUtils.getDateFromString(txtSecondDose.text.toString())
            if(firstDoseDate.isAfter(secondDoseDate)) {
                Toast.makeText(requireContext(), getString(R.string.error_second_date_before), Toast.LENGTH_LONG).show()
                return null
            }
        }

        return Vaccination(
            loggedInUserEmail,
            txtManufacturer.text.toString(),
            txtFirstDose.text.toString(),
            txtSecondDose.text.toString(),
            txtInstitution.text.toString(),
            )
    }

    private fun pickDateAndTimeFor(editText: TextInputEditText) {
        var currentDate = LocalDateTime.now()
        DatePickerDialog(requireContext(),
            { _, year, month, dayOfMonth ->
                currentDate = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)

                TimePickerDialog(requireContext(),
                    { _, hourOfDay, minute ->
                        currentDate = currentDate.plusHours(hourOfDay.toLong())
                        currentDate = currentDate.plusMinutes(minute.toLong())
                        editText.setText(DateTimeUtils.getStringFromDate(currentDate))
                    }, currentDate.hour, currentDate.minute, true).show()

            }, currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth).show()
    }
}