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
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader
import java.lang.Exception

class TermsOfUseFragment: Fragment() {
    private final val TERMS_OF_USE_FILE_DIR = "app/src/main/java/com/swtug/anticovid/view/termsOfUse/AntiCovidTermsOfUse.txt"
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

            val inputStream = requireContext().assets.open("AntiCovidTermsOfUse.txt")
            InputStreamReader(inputStream).forEachLine {
                textTermsOfUse.append(it);
                textTermsOfUse.append("\n");
            }

        } catch (e : Exception) {
            textTermsOfUse.text = getString(R.string.terms_of_use_full_text)
        }
    }
}