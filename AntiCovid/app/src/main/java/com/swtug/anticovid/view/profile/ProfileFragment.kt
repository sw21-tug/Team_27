package com.swtug.anticovid.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.swtug.anticovid.R
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.FirebaseListener
import com.swtug.anticovid.repositories.FirebaseRepo
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.BaseFragment
import java.util.*


class ProfileFragment : BaseFragment() {
    private lateinit var btnChinese: MaterialButton
    private lateinit var btnEnglish: MaterialButton
    private lateinit var toggleGroupLanguage: MaterialButtonToggleGroup
    private lateinit var btnlogout: Button
    private lateinit var btnedit: Button
    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextEMail: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextSocialSecurityID: EditText
    private lateinit var editTextPhoneNumber: EditText

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
        initListeners()
        initView()
        assignValues()
    }

    private fun initView() {
        when (PreferencesRepo.getLocale(requireContext())) {
            Locale.SIMPLIFIED_CHINESE -> toggleGroupLanguage.check(R.id.btn_chinese)
            Locale.ENGLISH -> toggleGroupLanguage.check(R.id.btn_english)
            else -> toggleGroupLanguage.check(R.id.btn_english)
        }
    }

    private fun initFields(view: View) {
        btnChinese = view.findViewById(R.id.btn_chinese)
        btnEnglish = view.findViewById(R.id.btn_english)
        btnlogout = view.findViewById(R.id.logoutbutton)
        toggleGroupLanguage = view.findViewById(R.id.toggle_group_language)

        editTextName = view.findViewById(R.id.editTextProfileName)
        editTextSurname = view.findViewById(R.id.editTextProfileSurname)
        editTextEMail = view.findViewById(R.id.editTextProfileEmail)
        editTextAddress = view.findViewById(R.id.editTextProfileAddress)
        editTextSocialSecurityID = view.findViewById(R.id.editTextProfileSocialSecurityID)
        editTextPhoneNumber = view.findViewById(R.id.editTextProfilePhoneNumber)
    }

    private fun initListeners() {

        btnlogout.setOnClickListener {
            PreferencesRepo.deleteUser(requireContext())
            PreferencesRepo.deleteVaccination(requireContext())
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
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

    }
    private fun assignValues(){
        val user = PreferencesRepo.getUser(requireContext())
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
        btnlogout.setOnClickListener {
            PreferencesRepo.deleteUser(requireContext())
            PreferencesRepo.deleteVaccination(requireContext())
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)

        }

        btnedit.setOnClickListener {
            val oldUser = PreferencesRepo.getUser(requireContext())
            if (oldUser != null) {
                val user = User(
                    oldUser.id,
                    editTextName.text.toString(),
                    editTextSurname.text.toString(),
                    oldUser.email,
                    editTextAddress.text.toString(),
                    editTextSocialSecurityID.text.toString(),
                    editTextPhoneNumber.text.toString(),
                    oldUser.password
                )
                PreferencesRepo.saveUser(requireContext(), user)

                FirebaseRepo.updateUser(user, object : FirebaseListener {
                    override fun onSuccess(user: User?) {
                        btnedit.isEnabled = true
                    }

                    override fun onStart() {
                        btnedit.isEnabled = false
                    }

                    override fun onFailure() {
                        btnedit.isEnabled = true
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_firebase_communication),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }


        }
    }

    private fun initFields(view: View) {
        btnlogout = view.findViewById(R.id.logoutbutton)
        btnedit = view.findViewById(R.id.button2)
        editTextName = view.findViewById(R.id.editTextProfileName)
        editTextSurname = view.findViewById(R.id.editTextProfileSurname)
        editTextEMail = view.findViewById(R.id.editTextProfileEmail)
        editTextAddress = view.findViewById(R.id.editTextProfileAddress)
        editTextSocialSecurityID = view.findViewById(R.id.editTextProfileSocialSecurityID)
        editTextPhoneNumber = view.findViewById(R.id.editTextProfilePhoneNumber)
    }
}