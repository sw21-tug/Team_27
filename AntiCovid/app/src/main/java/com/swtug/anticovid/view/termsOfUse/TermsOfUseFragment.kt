package com.swtug.anticovid.view.termsOfUse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swtug.anticovid.R
import com.swtug.anticovid.repositories.PreferencesRepo
import java.io.InputStreamReader
import java.util.*

class TermsOfUseFragment: Fragment() {
    private lateinit var switchAccepted: SwitchCompat
    private lateinit var btnAccept: Button
    private lateinit var textTermsOfUse: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_terms_of_use, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields(view)
        readTermsOfUseFile()
        initListeners()
    }

    private fun initListeners() {
        switchAccepted.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->
            btnAccept.isEnabled = switchAccepted.isChecked
        }

        btnAccept.setOnClickListener {
            PreferencesRepo.saveAcceptTermsOfUse(requireContext(), true)
            if(PreferencesRepo.getUser(requireContext()) != null){
                findNavController().navigate(R.id.action_termsOfUseFragment_to_mainFragment)
            }else{
                findNavController().navigate(R.id.action_termsOfUseFragment_to_loginFragment)
            }
        }
    }

    private fun initFields(view: View) {
        switchAccepted = view.findViewById(R.id.checkTermsOfUse)
        btnAccept = view.findViewById(R.id.buttonAcceptTermsOfUse)
        textTermsOfUse = view.findViewById(R.id.termsFullText)

        switchAccepted.isChecked = false
        btnAccept.isEnabled = false
    }

    private fun readTermsOfUseFile() {
        try {
            val language =
                if (requireActivity().resources.configuration.locale == Locale.SIMPLIFIED_CHINESE) {
                    "_CN"
                } else {
                    ""
                }

            val inputStream = requireContext().assets.open("AntiCovidTermsOfUse$language.txt")
            InputStreamReader(inputStream).forEachLine {
                textTermsOfUse.append(it);
                textTermsOfUse.append("\n");
            }

        } catch (e : Exception) {
            textTermsOfUse.text = getString(R.string.terms_of_use_full_text)
        }
    }
}