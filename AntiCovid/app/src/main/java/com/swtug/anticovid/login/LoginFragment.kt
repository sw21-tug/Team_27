package com.swtug.anticovid.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_login, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)
        initListeners(object : FirebaseListener {
            override fun onSuccess(user: User?) {
                btnLogin.isEnabled = true

                if (user == null) {
                    Toast.makeText(
                        requireContext(),
                        "Wrong login credentials",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (editTextPassword.text.toString() == user.password) {
                        PreferencesRepo.saveUser(requireContext(), user)
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)

                    } else {
                        Toast.makeText(requireContext(), "Login failed!", Toast.LENGTH_LONG)
                            .show()
                    }

                }
            }

            override fun onStart() {
                btnLogin.isEnabled = false

            }

            override fun onFailure() {
                btnLogin.isEnabled = true

                Toast.makeText(
                    requireContext(),
                    "Wrong login credentials",
                    Toast.LENGTH_LONG
                ).show()

            }
        })

    }


    private fun initListeners(firebaseListener: FirebaseListener) {

        btnLogin.setOnClickListener {
            checkUserCredentials(firebaseListener)
        }

        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun initFields(view: View) {
        btnLogin = view.findViewById(R.id.buttonLogin)
        btnRegister = view.findViewById(R.id.buttonRegister)
        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress)
        editTextPassword = view.findViewById(R.id.editTextPassword)

    }

    private fun checkUserCredentials(firebaseListener: FirebaseListener) {
        val email = editTextEmail.text.toString().toLowerCase(Locale.ROOT).trim()
        FirebaseRepo.getUser(email, firebaseListener)
    }


}

