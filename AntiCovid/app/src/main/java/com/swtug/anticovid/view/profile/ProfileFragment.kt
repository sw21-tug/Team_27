package com.swtug.anticovid.view.profile

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.swtug.anticovid.R
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.FirebaseUserListener
import com.swtug.anticovid.repositories.FirebaseRepo
import com.swtug.anticovid.repositories.PreferencesRepo
import java.util.*


class ProfileFragment : Fragment() {
    private lateinit var btnChinese: MaterialButton
    private lateinit var btnEnglish: MaterialButton
    private lateinit var toggleGroupLanguage: MaterialButtonToggleGroup
    private lateinit var btnedit: Button
    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextEMail: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextSocialSecurityID: EditText
    private lateinit var editTextPhoneNumber: EditText

    private lateinit var currentUser: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)
        initView()
        initListeners()
        currentUser = PreferencesRepo.getUser(requireContext())!!
        assignValues(currentUser)

    }

    private fun initView() {
        when (PreferencesRepo.getLocale(requireActivity())) {
            Locale.SIMPLIFIED_CHINESE -> toggleGroupLanguage.check(R.id.btn_chinese)
            Locale.ENGLISH -> toggleGroupLanguage.check(R.id.btn_english)
            else -> toggleGroupLanguage.check(R.id.btn_english)
        }
    }

    private fun initFields(view: View) {
        btnedit = view.findViewById(R.id.button2)

        btnChinese = view.findViewById(R.id.btn_chinese)
        btnEnglish = view.findViewById(R.id.btn_english)
        toggleGroupLanguage = view.findViewById(R.id.toggle_group_language)

        editTextName = view.findViewById(R.id.editTextProfileName)
        editTextSurname = view.findViewById(R.id.editTextProfileSurname)
        editTextEMail = view.findViewById(R.id.editTextProfileEmail)
        editTextAddress = view.findViewById(R.id.editTextProfileAddress)
        editTextSocialSecurityID = view.findViewById(R.id.editTextProfileSocialSecurityID)
        editTextPhoneNumber = view.findViewById(R.id.editTextProfilePhoneNumber)

    }


    private fun assignValues(user: User?) {
        if (user != null) {
            editTextName.setText(user.name)
            editTextSurname.setText(user.surname)
            editTextEMail.setText(user.email)
            editTextAddress.setText(user.address)
            editTextSocialSecurityID.setText(user.secid)
            editTextPhoneNumber.setText(user.phonenumber)
        }

    }

    private fun initListeners() {
        toggleGroupLanguage.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when (checkedId) {
                R.id.btn_chinese -> {
                    PreferencesRepo.saveLocale(requireContext(), Locale.SIMPLIFIED_CHINESE)
                    requireActivity().recreate()
                }
                R.id.btn_english -> {
                    PreferencesRepo.saveLocale(requireContext(), Locale.ENGLISH)
                    requireActivity().recreate()
                }
            }
        }

        btnedit.setOnClickListener {
            storeNewUserData()

        }

    }

    private fun getUserFromEditText(): User {
        currentUser.let {
            return User(
                it.id,
                editTextName.text.toString(),
                editTextSurname.text.toString(),
                it.email,
                editTextAddress.text.toString(),
                editTextSocialSecurityID.text.toString(),
                editTextPhoneNumber.text.toString(),
                it.password
            )
        }

    }

    private fun checkEditTextInputs(): String? {

        if (editTextName.text.isEmpty() ||
            editTextSurname.text.isEmpty() ||
            editTextPhoneNumber.text.isEmpty() ||
            editTextAddress.text.isEmpty() ||
            editTextSocialSecurityID.text.isEmpty()
        ) {
            return requireActivity().getString(R.string.error_empty_fields)
        }

        if (editTextSocialSecurityID.text.length != 10) {
            return requireActivity().getString(R.string.error_secID_lenth)
        }

        return null;
    }

    private fun storeNewUserData() {
        val oldUser = PreferencesRepo.getUser(requireContext())
        val validValues = checkEditTextInputs()
        if (validValues != null) {

            Toast.makeText(
                requireActivity(),
                validValues,
                Toast.LENGTH_LONG
            ).show()

            return
        }

        if (oldUser == null) {
            return
        }

        val newUser = getUserFromEditText() ?: return

        FirebaseRepo.updateUser(newUser, object : FirebaseUserListener {
            override fun onSuccess(user: User?) {
                btnedit.isEnabled = true

                PreferencesRepo.saveUser(requireContext(), newUser)


                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.changes_saved),
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onStart() {
                btnedit.isEnabled = false
            }

            override fun onFailure() {
                btnedit.isEnabled = true

                assignValues(currentUser)

                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.error_firebase_communication),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}