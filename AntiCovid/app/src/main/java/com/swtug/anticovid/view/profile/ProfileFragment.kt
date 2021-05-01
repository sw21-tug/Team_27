package com.swtug.anticovid.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.swtug.anticovid.R
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.view.BaseFragment
import java.util.*


class ProfileFragment : BaseFragment() {
    private lateinit var btnChinese: MaterialButton
    private lateinit var btnEnglish: MaterialButton
    private lateinit var toggleGroupLanguage: MaterialButtonToggleGroup
    private lateinit var btnlogout: Button


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
        initListener()
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
    }

    private fun initListener() {
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

        btnlogout.setOnClickListener {
            PreferencesRepo.deleteUser(requireContext())
            PreferencesRepo.deleteVaccination(requireContext())
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
}