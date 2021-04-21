package com.swtug.anticovid.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.swtug.anticovid.R

class LoginFragment : Fragment() {

    private lateinit var btnLogin: Button
    private lateinit var btnBack: Button
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


        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        if (sharedPref?.getBoolean("loggedIn", false) == true) {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }


        initFields(view)
        initListeners()

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // User is not allowed to go back to login
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback);

    }


    private fun initListeners() {
        btnLogin.setOnClickListener {

            val db = FirebaseFirestore.getInstance()
            db.collection("User")
                .whereEqualTo("email", editTextEmail.text.toString().toLowerCase().trim())
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.size() == 0) {
                        Toast.makeText(
                            requireContext(),
                            "You do not have an account. Please create one first.",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else if (documents.size() > 1) {
                        Toast.makeText(requireContext(), "Login not possible!", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        val document = documents.first()
                        if (editTextPassword.text.toString() == document.data["password"]) {

                            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                            if (sharedPref != null) {
                                with(sharedPref.edit()) {
                                    putBoolean("loggedIn", true)
                                    apply()
                                }
                                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                            } else {
                                Toast.makeText(requireContext(), "Login failed!", Toast.LENGTH_LONG)
                                    .show()
                            }


                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Wrong login credentials",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }

                }


        }
        btnBack.setOnClickListener {
            //findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            Toast.makeText(requireContext(), "Back not implementet!", Toast.LENGTH_LONG).show()
        }

    }

    private fun initFields(view: View) {
        btnLogin = view.findViewById(R.id.buttonLogin)
        btnBack = view.findViewById(R.id.buttonBack)
        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress)
        editTextPassword = view.findViewById(R.id.editTextPassword)

    }
}

