package com.swtug.anticovid.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swtug.anticovid.R
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.FirebaseListener
import com.swtug.anticovid.repositories.FirebaseRepo
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.BaseFragment
import java.util.*


class LoginFragment : Fragment() {

    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText

    private lateinit var txtEmailError: TextView
    private lateinit var txtLoginError: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)
        initListeners(object : FirebaseListener {
            override fun onSuccess(user: User?) {
                setButtonsEnabled(true)

                if(user != null && editTextPassword.text.toString() == user.password) {
                    PreferencesRepo.saveUser(requireContext(), user)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                } else {
                    txtLoginError.text = getString(R.string.error_wrong_password)
                }
            }

            override fun onStart() {
                setButtonsEnabled(false)
            }

            override fun onFailure() {
                setButtonsEnabled(true)
                txtLoginError.text = getString(R.string.error_firebase_communication)
            }
        })

    }


    private fun initListeners(firebaseListener: FirebaseListener) {

        btnLogin.setOnClickListener {
            checkUserCredentials(firebaseListener)
        }

        btnRegister.setOnClickListener {
            clearErrorLabels()
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun initFields(view: View) {
        btnLogin = view.findViewById(R.id.buttonLogin)
        btnRegister = view.findViewById(R.id.buttonRegister)
        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress)
        editTextPassword = view.findViewById(R.id.editTextPassword)

        txtEmailError = view.findViewById(R.id.txtEmailError)
        txtLoginError = view.findViewById(R.id.txtLoginError)

    }

    private fun checkUserCredentials(firebaseListener: FirebaseListener) {
        clearErrorLabels()
        val email = editTextEmail.text.toString().toLowerCase(Locale.ROOT).trim()

        if(email.isEmpty()) {
            txtEmailError.text = getString(R.string.error_no_email)
        } else {
            FirebaseRepo.getUser(email, firebaseListener)
        }
    }

    private fun clearErrorLabels() {
        txtLoginError.text = ""
        txtEmailError.text = ""
    }

    private fun setButtonsEnabled(enabled: Boolean) {
        btnRegister.isEnabled = enabled
        btnLogin.isEnabled = enabled
    }
}

