package com.swtug.anticovid.view.informationAndRules

import android.os.Bundle
import android.view.*
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swtug.anticovid.MainActivity
import com.swtug.anticovid.R

class InformationAndRules :  Fragment(R.layout.fragment_information_and_rules) {
    private lateinit var webView: WebView

    init {
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as? MainActivity?)?.run {
            setSupportActionBar(view.findViewById(R.id.toolbar))
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }

        initFields(view)
        initListeners()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
            }
        }
        return true
    }

    private fun initFields(view: View) {
        webView = view.findViewById(R.id.webView)
        webView.loadUrl("https://www.sozialministerium.at/Informationen-zum-Coronavirus/Coronavirus---Aktuelle-Maßnahmen.html")

    }

    private fun initListeners() {

    }





}