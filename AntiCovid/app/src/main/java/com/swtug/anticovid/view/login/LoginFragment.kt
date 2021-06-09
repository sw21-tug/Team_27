package com.swtug.anticovid.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swtug.anticovid.R
import com.swtug.anticovid.TestReportProvider
import com.swtug.anticovid.models.TestReport
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.FirebaseUserListener
import com.swtug.anticovid.repositories.FirebaseRepo
import com.swtug.anticovid.repositories.FirebaseTestReportListener
import com.swtug.anticovid.repositories.PreferencesRepo
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
        return layoutInflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)
        initListeners(object : FirebaseUserListener {
            override fun onSuccess(user: User?) {

                if(user != null && editTextPassword.text.toString() == user.password) {
                    PreferencesRepo.saveUser(requireContext(), user)
                    fetchTestReports(user.email)

                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.error_wrong_password),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onStart() {
            }

            override fun onFailure() {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.error_firebase_communication),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun fetchTestReports(userEmail: String) {
        FirebaseRepo.getAllTestReportsFrom(userEmail, object : FirebaseTestReportListener {
            override fun onSuccess(testReports: ArrayList<TestReport>) {
                TestReportProvider.setTestReports(testReports)
            }

            override fun onStart() { }

            override fun onFailure() {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.error_firebase_communication),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }


    private fun initListeners(firebaseUserListener: FirebaseUserListener) {

        btnLogin.setOnClickListener {
            checkUserCredentials(firebaseUserListener)
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

    private fun checkUserCredentials(firebaseUserListener: FirebaseUserListener) {

        val email = editTextEmail.text.toString().toLowerCase(Locale.ROOT).trim()
        val password = editTextPassword.text.toString().trim()

        if(email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_no_credentials),
                    Toast.LENGTH_LONG
                ).show()


        } else {
            FirebaseRepo.getUser(email, firebaseUserListener)
        }
    }






}

