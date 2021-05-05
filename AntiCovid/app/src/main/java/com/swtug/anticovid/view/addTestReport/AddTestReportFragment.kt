package com.swtug.anticovid.view.addTestReport

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
    private lateinit var editTestDate: EditText
    private lateinit var editValidDate: EditText
    
    private lateinit var buttonAddTestDate: ImageButton
    private lateinit var buttonAddValidDate: ImageButton
    
    private lateinit var  radioPositive: RadioButton
    private lateinit var  radioNegative: RadioButton

    private var currentUser: User? = null

    private val VALID_HOURS: Long = 48
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_add_test_report, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUser = PreferencesRepo.getUser(requireContext())
        initFields(view)
        initListeners()
        setCurrentDate()
    }

    private fun setCurrentDate() {
        val currentDate = LocalDateTime.now()
        editTestDate.setText(Utils.getStringFromDate(currentDate))
        autoFillOutValidDate()
    }

    private fun autoFillOutValidDate() {
        val testDateText = editTestDate.text.toString()
        val testDate = LocalDateTime.parse(testDateText, Utils.getLocalDateTimeFormatter())
        editValidDate.setText(Utils.getStringFromDate(testDate.plusHours(VALID_HOURS)))
    }

    private fun initListeners() {

    }

    private fun initFields(view: View) {
        txtUserEmail = view.findViewById(R.id.text_email_info)
        txtUserName = view.findViewById(R.id.text_user_name_info)
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