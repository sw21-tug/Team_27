package com.swtug.anticovid.repositories

import com.swtug.anticovid.models.User

interface FirebaseListener {
    fun onSuccess(user: User?)
    fun onStart()
    fun onFailure()
}