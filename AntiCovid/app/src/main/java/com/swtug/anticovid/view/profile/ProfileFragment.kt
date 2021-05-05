package com.swtug.anticovid.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.swtug.anticovid.view.BaseFragment
import com.swtug.anticovid.R
import com.swtug.anticovid.repositories.PreferencesRepo

class ProfileFragment: BaseFragment() {

    private lateinit var btnlogout: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_profile, null)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)

        btnlogout.setOnClickListener {
            PreferencesRepo.deleteUser(requireContext())
            PreferencesRepo.deleteVaccination(requireContext())
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
    private fun initFields(view: View) {
        btnlogout = view.findViewById(R.id.logoutbutton)
    }
}