package com.swtug.anticovid.view.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swtug.anticovid.api.CountryService
import com.swtug.anticovid.api.ServiceBuilder
import com.swtug.anticovid.models.Country
import kotlinx.coroutines.launch


class StatisticsViewModel : ViewModel() {
    private var countryList = listOf<Country>()
    val selectedCountry = MutableLiveData<Country>()

    fun loadCountries(onLoaded: () -> Unit){
        val destinationService = ServiceBuilder.buildService(CountryService::class.java)

        viewModelScope.launch {
            countryList = destinationService.getAffectedCountryList()
            onLoaded()
        }
    }

    fun selectCountry(id: Int) {
        if(countryList.isEmpty()) {
            loadCountries {
                selectedCountry.value = countryList[id]
            }
        } else {
            selectedCountry.value = countryList[id]
        }
    }
}