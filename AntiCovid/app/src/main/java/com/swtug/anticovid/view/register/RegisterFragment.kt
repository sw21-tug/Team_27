package com.swtug.anticovid.view.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swtug.anticovid.R
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.FirebaseUserListener
import com.swtug.anticovid.repositories.FirebaseRepo
import com.swtug.anticovid.repositories.PreferencesRepo

class RegisterFragment : Fragment() {
    private lateinit var btnSignUp: Button
    private lateinit var btnCancelSignUp: Button
    private lateinit var editTxtEmail: EditText
    private lateinit var editTxtPassword: EditText
    private lateinit var editTxtName: EditText
    private lateinit var editTxtSureName: EditText
    private lateinit var editTxtAddress: EditText
    private lateinit var editTxtSocialSecurityId: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var txtErrorLabel: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_register, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields(view)
        initListeners(object : FirebaseUserListener {
            override fun onSuccess(user: User?) {
                setButtonsEnabled(true)

                if (user != null) {
                    PreferencesRepo.saveUser(requireContext(), user)
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                } else {
                    txtErrorLabel.text = getString(R.string.error_user_exists)
                }
            }

            override fun onStart() {
                setButtonsEnabled(false)
            }

            override fun onFailure() {
                setButtonsEnabled(true)
                txtErrorLabel.text = getString(R.string.error_firebase_communication)
            }
        })
    }

    private fun trySignUpNewUser(user: User, firebaseUserListener: FirebaseUserListener) {
        FirebaseRepo.saveUser(user, firebaseUserListener)
    }

    private fun initListeners(firebaseUserListener: FirebaseUserListener) {
        btnSignUp.setOnClickListener {
            if (checkEditTextInputs()) {
                trySignUpNewUser(getUserObjectFromInputs(), firebaseUserListener)
            }
        }

        btnCancelSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun initFields(view: View) {
        btnSignUp = view.findViewById(R.id.buttonSignUp)
        btnCancelSignUp = view.findViewById(R.id.buttonCancelSignUp)

        editTxtEmail = view.findViewById(R.id.editTextEmail)
        editTxtPassword = view.findViewById(R.id.editTextPassword)
        editTxtName = view.findViewById(R.id.editTextName)
        editTxtSureName = view.findViewById(R.id.editTextSureName)
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber)
        editTxtAddress = view.findViewById(R.id.editTextAddress)
        editTxtSocialSecurityId = view.findViewById(R.id.editTextSecurityID)

        txtErrorLabel = view.findViewById(R.id.editTextInputError)
    }

    private fun checkEditTextInputs(): Boolean {
        txtErrorLabel.text = "";

        if (editTxtEmail.text.isEmpty() ||
            editTxtPassword.text.isEmpty() ||
            editTxtName.text.isEmpty() ||
            editTxtSureName.text.isEmpty() ||
            editTextPhoneNumber.text.isEmpty() ||
            editTxtAddress.text.isEmpty() ||
            editTxtSocialSecurityId.text.isEmpty()
        ) {
            txtErrorLabel.text = getString(R.string.error_empty_fields)
            return false;
        }

        if (editTxtPassword.text.length < 8) {
            txtErrorLabel.text = getString(R.string.error_pwd_too_short)
            return false;
        }

        if (editTxtSocialSecurityId.text.length != 10) {
            txtErrorLabel.text = getString(R.string.error_secID_lenth)
            return false;
        }

        return true;
    }

    private fun getUserObjectFromInputs(): User {
        return User(
            -1,
            editTxtName.text.toString(),
            editTxtSureName.text.toString(),
            editTxtEmail.text.toString(),
            editTxtAddress.text.toString(),
            editTxtSocialSecurityId.text.toString(),
            editTextPhoneNumber.text.toString(),
            editTxtPassword.text.toString()
        )
    }

    private fun setButtonsEnabled(enabled: Boolean) {
        btnSignUp.isEnabled = enabled
        btnCancelSignUp.isEnabled = enabled
    }
}
