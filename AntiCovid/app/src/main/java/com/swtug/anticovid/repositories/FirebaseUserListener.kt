package com.swtug.anticovid.repositories

import com.swtug.anticovid.models.User

interface FirebaseUserListener {
    fun onSuccess(user: User?)
    fun onStart()
    fun onFailure()
}