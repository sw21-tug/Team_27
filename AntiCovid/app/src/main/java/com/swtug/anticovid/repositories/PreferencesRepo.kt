package com.swtug.anticovid.repositories

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.swtug.anticovid.models.Vaccination

object PreferencesRepo {
    private const val PREFERENCES_NAME = "ANTI_COVID_APP"
    private const val VACCINATION = "VACCINATION"
    private val TERMS_OF_USE = "TERMS_OF_USE_ACCEPTED"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun saveAcceptTermsOfUse(context: Context, accept: Boolean) {
        getPreferences(context)
            .edit()
            .putBoolean(TERMS_OF_USE, accept)
            .apply()
    }

    fun getTermsOfUseAccepted(context: Context) : Boolean {
        return getPreferences(context).getBoolean(TERMS_OF_USE, false)
    }

    fun saveVaccination(context: Context, vaccination: Vaccination) {
        val gson = Gson()
        val json = gson.toJson(vaccination)

        getPreferences(context)
            .edit()
            .putString(VACCINATION, json)
            .apply()
    }

    fun getVaccination(context: Context): Vaccination? {
        val json =  getPreferences(context)
            .getString(VACCINATION, null)

        return if(json.isNullOrEmpty()) {
            null
        } else {
            val gson = Gson()
            gson.fromJson(json, Vaccination::class.java)
        }
    }
}