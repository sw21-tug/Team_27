package com.swtug.anticovid.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.swtug.anticovid.view.BaseFragment
import com.swtug.anticovid.R

class ProfileFragment: BaseFragment() {

    private lateinit var btnlogout: MaterialButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_profile, null)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)

        btnlogout.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
    private fun initFields(view: View) {
        btnlogout = view.findViewById(R.id.logoutbutton)
    }
}