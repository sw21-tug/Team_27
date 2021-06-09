package com.swtug.anticovid.repositories

import com.swtug.anticovid.models.Vaccination

interface FirebaseVaccinationListener {
    fun onSuccess(vaccination: Vaccination?)
    fun onStart()
    fun onFailure()
}