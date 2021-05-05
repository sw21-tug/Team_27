package com.swtug.anticovid.repositories

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.swtug.anticovid.models.User
import com.swtug.anticovid.models.Vaccination
import java.util.*

interface IPreferencesRepo{
    fun saveAcceptTermsOfUse(context: Context, accept: Boolean)
    fun getTermsOfUseAccepted(context: Context): Boolean
    fun saveVaccination(context: Context, vaccination: Vaccination)
    fun deleteVaccination(context: Context)
    fun getVaccination(context: Context): Vaccination?
    fun saveUser(context: Context, user: User)
    fun getUser(context: Context): User?
    fun saveLocale(locale: Locale)
    fun getLocale(context: Context): Locale
    fun deleteUser(context: Context)
}

object PreferencesRepo :  IPreferencesRepo{
    private const val PREFERENCES_NAME = "ANTI_COVID_APP"
    private const val VACCINATION = "VACCINATION"
    private const val TERMS_OF_USE = "TERMS_OF_USE_ACCEPTED"
    private const val LOGGED_IN_USER = "LOGGED_IN_USER"
    private const val CURRENT_LOCALE = "CURRENT_LOCALE"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun saveAcceptTermsOfUse(context: Context, accept: Boolean) {
        getPreferences(context)
            .edit()
            .putBoolean(TERMS_OF_USE, accept)
            .apply()
    }

    override fun getTermsOfUseAccepted(context: Context): Boolean {
        return getPreferences(context).getBoolean(TERMS_OF_USE, false)
    }

    override fun saveVaccination(context: Context, vaccination: Vaccination) {
        val gson = Gson()
        val json = gson.toJson(vaccination)

        getPreferences(context)
            .edit()
            .putString(VACCINATION, json)
            .apply()
    }

    override fun deleteVaccination(context: Context){
        getPreferences(context).edit().remove(VACCINATION).apply()
    }

    override fun getVaccination(context: Context): Vaccination? {
        val json = getPreferences(context)
            .getString(VACCINATION, null)

        return if (json.isNullOrEmpty()) {
            null
        } else {
            val gson = Gson()
            gson.fromJson(json, Vaccination::class.java)
        }
    }

    override fun saveUser(context: Context, user: User) {
        val gson = Gson()
        val json = gson.toJson(user)

        getPreferences(context)
            .edit()
            .putString(LOGGED_IN_USER, json)
            .apply()
    }

    override fun getUser(context: Context): User? {
        val json = getPreferences(context)
            .getString(LOGGED_IN_USER, null)

        return if (json.isNullOrEmpty()) {
            null
        } else {
            val gson = Gson()
            gson.fromJson(json, User::class.java)
        }
    }

    override fun saveLocale(locale: Locale) {
        val gson = Gson()
        val json = gson.toJson(locale)

        //getPreferences(context)
        //    .edit()
        //    .putString(CURRENT_LOCALE, json)
        //    .apply()
    }

    override fun getLocale(context: Context): Locale {
        val json = getPreferences(context)
            .getString(CURRENT_LOCALE, null)

        return if (json.isNullOrEmpty()) {
            Locale.getDefault()
        } else {
            val gson = Gson()
            gson.fromJson(json, Locale::class.java)
        }
    }

    override fun deleteUser(context: Context){
        getPreferences(context).edit().remove(LOGGED_IN_USER).apply()
    }

}