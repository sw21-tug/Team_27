
package com.swtug.anticovid.view.addTestReport

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.swtug.anticovid.MainActivity
import com.swtug.anticovid.models.User
import com.swtug.anticovid.R
import com.swtug.anticovid.utils.DateTimeUtils
import com.swtug.anticovid.models.FirebaseTestReport
import com.swtug.anticovid.repositories.FirebaseUserListener
import com.swtug.anticovid.repositories.FirebaseRepo
import com.swtug.anticovid.repositories.PreferencesRepo
import java.time.LocalDateTime

class AddTestReportFragment: Fragment(R.layout.fragment_add_test_report) {

    private lateinit var txtUserEmail: TextView
    private lateinit var txtUserName: TextView
    private lateinit var txtValidityInfo: TextView

    private lateinit var editTestLayout: TextInputLayout
    private lateinit var editTestDate: TextInputEditText
    private lateinit var editValidDate: EditText

    private lateinit var  radioPositive: RadioButton
    private lateinit var  radioNegative: RadioButton

    private lateinit var selectedDate: LocalDateTime

    private var isPositiveTest: Boolean = false

    private var currentUser: User? = null

    companion object {
        const val VALID_HOURS_NEGATIVE: Long = 48
        const val VALID_DAYS_POSITIVE: Long = 14
    }

    init {
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        currentUser = PreferencesRepo.getUser(requireContext())
        initFields(view)
        setTestAndValidDate(LocalDateTime.now())

        (activity as? MainActivity?)?.run {
            setSupportActionBar(view.findViewById(R.id.toolbar))
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }

        initListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_test, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
            }
            R.id.save -> {
                addTestReportToFirebase(firebaseUserListener)
            }
        }
        return true
    }

    private fun addTestReportToFirebase(firebaseUserListener: FirebaseUserListener) {
        if(currentUser != null) {
            FirebaseRepo.addTestReport(getTestReportOfForm(), firebaseUserListener)
        } else {
            Toast.makeText(requireContext(), getString(R.string.error_no_user_logged_in), Toast.LENGTH_LONG).show()
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setTestAndValidDate(testDate: LocalDateTime) {
        selectedDate = testDate
        editTestDate.setText(DateTimeUtils.getStringFromDate(testDate))
        if(isPositiveTest) {
            editValidDate.setText(DateTimeUtils.getStringFromDate(testDate.plusDays(VALID_DAYS_POSITIVE)))
            txtValidityInfo.text = getString(R.string.positive_test_valid_info)
        } else {
            editValidDate.setText(DateTimeUtils.getStringFromDate(testDate.plusHours(VALID_HOURS_NEGATIVE)))
            txtValidityInfo.text = getString(R.string.negative_test_valid_info)
        }
    }

    private fun initListeners() {
        radioNegative.setOnClickListener {
            isPositiveTest = false
            editValidDate.setText(DateTimeUtils.getStringFromDate(selectedDate.plusHours(VALID_HOURS_NEGATIVE)))
            txtValidityInfo.text = getString(R.string.negative_test_valid_info)
        }

        radioPositive.setOnClickListener {
            isPositiveTest = true
            editValidDate.setText(DateTimeUtils.getStringFromDate(selectedDate.plusDays(VALID_DAYS_POSITIVE)))
            txtValidityInfo.text = getString(R.string.positive_test_valid_info)
        }

        editTestLayout.setEndIconOnClickListener {
            pickDateAndTime()
        }
    }

    private fun pickDateAndTime() {
        selectedDate = LocalDateTime.now()
        DatePickerDialog(requireContext(),
            { _, year, month, dayOfMonth ->
                selectedDate = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)

                TimePickerDialog(requireContext(),
                    { _, hourOfDay, minute ->
                        selectedDate = selectedDate.plusHours(hourOfDay.toLong())
                        selectedDate = selectedDate.plusMinutes(minute.toLong())
                        setTestAndValidDate(selectedDate)
                }, selectedDate.hour, selectedDate.minute, true).show()

        }, selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth).show()
    }

    private fun getTestReportOfForm(): FirebaseTestReport {
        return FirebaseTestReport(currentUser?.email ?: "",
                                editTestDate.text.toString(),
                                isPositiveTest,
                                editValidDate.text.toString())
    }

    private fun initFields(view: View) {
        txtUserEmail = view.findViewById(R.id.text_email_info)
        txtUserName = view.findViewById(R.id.text_user_name_info)
        txtValidityInfo = view.findViewById(R.id.validity_info)
        editTestDate = view.findViewById(R.id.text_test_date)
        editTestLayout = view.findViewById(R.id.test_date_layout)
        editValidDate = view.findViewById(R.id.text_valid_date)

        radioPositive = view.findViewById(R.id.radio_test_positive)
        radioNegative = view.findViewById(R.id.radio_test_negative)

        txtUserEmail.text = currentUser?.email
        txtUserName.text = currentUser?.name + " " +  currentUser?.surname
    }

    private fun setButtonsEnabled(enabled: Boolean) {
        radioPositive.isEnabled = enabled
        radioNegative.isEnabled = enabled
    }

    private val firebaseUserListener = object : FirebaseUserListener {
        override fun onSuccess(user: User?) {
            setButtonsEnabled(true)
            Toast.makeText(requireContext(), getString(R.string.report_added_success),
                Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }

        override fun onStart() {
            setButtonsEnabled(false)
        }

        override fun onFailure() {
            setButtonsEnabled(true)
            Toast.makeText(requireContext(), getString(R.string.error_firebase_communication),
                Toast.LENGTH_LONG).show()
        }
    }
}