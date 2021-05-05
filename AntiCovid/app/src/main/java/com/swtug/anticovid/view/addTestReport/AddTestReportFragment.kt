package com.swtug.anticovid.view.addTestReport

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.swtug.anticovid.models.User
import com.swtug.anticovid.view.BaseFragment
import com.swtug.anticovid.R
import com.swtug.anticovid.Utils
import com.swtug.anticovid.repositories.PreferencesRepo
import java.time.LocalDateTime
import java.util.*

class AddTestReportFragment: BaseFragment() {

    private lateinit var txtUserEmail: TextView
    private lateinit var txtUserName: TextView
    private lateinit var txtValidityInfo: TextView

    private lateinit var editTestDate: EditText
    private lateinit var editValidDate: EditText
    
    private lateinit var buttonAddTestDate: ImageButton
    private lateinit var buttonAddValidDate: ImageButton
    
    private lateinit var  radioPositive: RadioButton
    private lateinit var  radioNegative: RadioButton

    private lateinit var selectedDate: LocalDateTime

    private var isPositiveTest: Boolean = false

    private var currentUser: User? = null

    private val VALID_HOURS_NEGATIVE: Long = 48
    private val VALID_DAYS_POSITIVE: Long = 14

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_add_test_report, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUser = PreferencesRepo.getUser(requireContext())
        initFields(view)
        initListeners()
        setTestAndValidDate(LocalDateTime.now())
    }

    private fun setTestAndValidDate(testDate: LocalDateTime) {
        selectedDate = testDate
        editTestDate.setText(Utils.getStringFromDate(testDate))
        if(isPositiveTest) {
            editValidDate.setText(Utils.getStringFromDate(testDate.plusDays(VALID_DAYS_POSITIVE)))
            txtValidityInfo.text = getString(R.string.positive_test_valid_info)
        } else {
            editValidDate.setText(Utils.getStringFromDate(testDate.plusHours(VALID_HOURS_NEGATIVE)))
            txtValidityInfo.text = getString(R.string.negative_test_valid_info)
        }
    }

    private fun initListeners() {

        buttonAddTestDate.setOnClickListener {
            pickDateAndTime()
        }

        radioNegative.setOnClickListener {
            isPositiveTest = false
            editValidDate.setText(Utils.getStringFromDate(selectedDate.plusHours(VALID_HOURS_NEGATIVE)))
            txtValidityInfo.text = getString(R.string.negative_test_valid_info)
        }

        radioPositive.setOnClickListener {
            isPositiveTest = true
            editValidDate.setText(Utils.getStringFromDate(selectedDate.plusDays(VALID_DAYS_POSITIVE)))
            txtValidityInfo.text = getString(R.string.positive_test_valid_info)
        }
    }

    private fun pickDateAndTime() {
        selectedDate = LocalDateTime.now()
        DatePickerDialog(requireContext(),
            { _, year, month, dayOfMonth ->
                selectedDate = LocalDateTime.of(year, month, dayOfMonth, 0, 0)

                TimePickerDialog(requireContext(),
                    { _, hourOfDay, minute ->
                        selectedDate = selectedDate.plusHours(hourOfDay.toLong())
                        selectedDate = selectedDate.plusMinutes(minute.toLong())
                        setTestAndValidDate(selectedDate)
                }, selectedDate.hour, selectedDate.minute, true).show()

        }, selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth).show()
    }

    private fun initFields(view: View) {
        txtUserEmail = view.findViewById(R.id.text_email_info)
        txtUserName = view.findViewById(R.id.text_user_name_info)
        txtValidityInfo = view.findViewById(R.id.validity_info)
        editTestDate = view.findViewById(R.id.text_test_date)
        editValidDate = view.findViewById(R.id.text_valid_date)

        buttonAddTestDate = view.findViewById(R.id.button_enter_test_date)
        buttonAddValidDate = view.findViewById(R.id.button_enter_valid_date)

        radioPositive = view.findViewById(R.id.radio_test_positive)
        radioNegative = view.findViewById(R.id.radio_test_negative)

        txtUserEmail.text = currentUser?.email
        txtUserName.text = currentUser?.name + " " +  currentUser?.surname
    }
}