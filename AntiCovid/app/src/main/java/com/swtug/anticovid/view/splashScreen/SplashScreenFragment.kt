package com.swtug.anticovid.view.splashScreen

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swtug.anticovid.R
import com.swtug.anticovid.repositories.PreferencesRepo

class SplashScreenFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_splash_screen, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            if(PreferencesRepo.getTermsOfUseAccepted(requireContext())) {
                if(PreferencesRepo.getUser(requireContext()) != null){
                    findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                }else{
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_termsOfUse)
            }
        }, 3000)
    }
}