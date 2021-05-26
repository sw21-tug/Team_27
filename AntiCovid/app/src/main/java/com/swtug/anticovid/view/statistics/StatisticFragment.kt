package com.swtug.anticovid.view.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swtug.anticovid.R
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class StatisticFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_statistic, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadCountries(view)
        super.onViewCreated(view, savedInstanceState)

    }

    private fun loadCountries(view : View) {
        //initiate the service
        val destinationService = ServiceBuilder.buildService(CountryService::class.java)
        val requestCall = destinationService.getAffectedCountryList()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<List<MyCountry>> {
            override fun onResponse(call: Call<List<MyCountry>>, response: Response<List<MyCountry>>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val countryList = response.body()!!
                    Log.d("Response", "countrylist size : ${countryList.size}")

                    var country = view.findViewById<TextView>(R.id.land)
                    var totalCases = view.findViewById<TextView>(R.id.totalcasesvalue)
                    var totalTest = view.findViewById<TextView>(R.id.totaltestedvalue)
                    var totalDeath = view.findViewById<TextView>(R.id.totaldeathvalue)
                    var todayCases = view.findViewById<TextView>(R.id.casestodayvalue)
                    var todayDeath = view.findViewById<TextView>(R.id.deathtodayvalue)
                    var todayRecovered = view.findViewById<TextView>(R.id.recoveredtodayvalue)
                    var totalRecovered = view.findViewById<TextView>(R.id.totalrecoveredvalue)
                    var population = view.findViewById<TextView>(R.id.populationvalue)
                    var update = view.findViewById<TextView>(R.id.lastupdatevalue)
                    totalCases.text = countryList[11].cases.toString()
                    totalTest.text = countryList[11].tests.toString()
                    totalDeath.text = countryList[11].deaths.toString()
                    todayCases.text = countryList[11].todayCases.toString()
                    todayDeath.text = countryList[11].todayDeaths.toString()
                    todayRecovered.text = countryList[11].todayRecovered.toString()
                    totalRecovered.text = countryList[11].todayRecovered.toString()
                    population.text = countryList[11].population.toString()
                    val date = Date(countryList[11].updated)
                    val format = SimpleDateFormat("dd.MM.yyyy - HH:mm")
                    update.text = format.format(date)


                }else{
                    //error message
                }
            }
            override fun onFailure(call: Call<List<MyCountry>>, t: Throwable) {
                //error message
            }
        })
    }

}