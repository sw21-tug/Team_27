package com.swtug.anticovid.api

import com.swtug.anticovid.models.Country
import retrofit2.http.GET

interface CountryService {

    @GET("countries")
    suspend fun getAffectedCountryList(): List<Country>
}