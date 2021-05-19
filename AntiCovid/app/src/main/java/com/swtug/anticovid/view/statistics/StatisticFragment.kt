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
import android.widget.*
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.swtug.anticovid.view.statistics.CountryService
import com.swtug.anticovid.view.statistics.MyCountry
import com.swtug.anticovid.view.statistics.ServiceBuilder
import java.text.SimpleDateFormat
import java.util.*

class StatisticFragment : Fragment() , AdapterView.OnItemSelectedListener{

    private lateinit var spinnerCountries: Spinner
    private lateinit var totalCases: TextView
    private lateinit var totalTest: TextView
    private lateinit var totalDeath: TextView
    private lateinit var todayCases: TextView
    private lateinit var todayDeath: TextView
    private lateinit var todayRecovered: TextView
    private lateinit var totalRecovered: TextView
    private lateinit var population: TextView
    private lateinit var update: TextView
    private lateinit var imvFlag: ImageView
    private lateinit var active: TextView

    val AUSTRIA: Int = 11
    var countryID: Int = AUSTRIA
    lateinit var CountryList: List<MyCountry>




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

    private fun loadCountries(view: View){
        //initiate the service
        val destinationService = ServiceBuilder.buildService(CountryService::class.java)
        val requestCall = destinationService.getAffectedCountryList()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<List<MyCountry>> {
            override fun onResponse(call: Call<List<MyCountry>>, response: Response<List<MyCountry>>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                   CountryList = response.body()!!
                    Log.d("Response", "countrylist size : ${CountryList.size}")
                    initFields(view)

                }else {
                    Toast.makeText(context, getString(R.string.error_statistics), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<MyCountry>>, t: Throwable) {
                Toast.makeText(context, getString(R.string.error_statistics), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initFields(view: View) {
        spinnerCountries = view.findViewById(R.id.spinner)
        spinnerCountries.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerCountries.adapter = adapter
            spinnerCountries.setSelection(AUSTRIA)

            totalCases = view.findViewById<TextView>(R.id.totalcasesvalue)
            totalTest = view.findViewById<TextView>(R.id.totaltestedvalue)
            totalDeath = view.findViewById<TextView>(R.id.totaldeathvalue)
            todayCases = view.findViewById<TextView>(R.id.casestodayvalue)
            todayDeath = view.findViewById<TextView>(R.id.deathtodayvalue)
            todayRecovered = view.findViewById<TextView>(R.id.recoveredtodayvalue)
            totalRecovered = view.findViewById<TextView>(R.id.totalrecoveredvalue)
            population = view.findViewById<TextView>(R.id.populationvalue)
            update = view.findViewById<TextView>(R.id.lastupdatevalue)
            imvFlag = view.findViewById<ImageView>(R.id.imv_Showflag)
            active = view.findViewById<TextView>(R.id.activevalue)
        }
    }


    private fun updateData(_countryList: List<MyCountry>, _countryID: Int) {
        _countryList[_countryID].let { country ->
            active.text = country.active.toString()
            totalCases.text = country.cases.toString()
            totalTest.text = country.tests.toString()
            totalDeath.text = country.deaths.toString()
            todayCases.text = country.todayCases.toString()
            todayDeath.text = country.todayDeaths.toString()
            todayRecovered.text = country.todayRecovered.toString()
            totalRecovered.text = country.recovered.toString()
            population.text = country.population.toString()
            val date = Date(country.updated)
            val format = SimpleDateFormat("dd.MM.yyyy - HH:mm")
            update.text = format.format(date)
            Picasso.get().load(country.countryInfo.flag).into(imvFlag);
        }

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using

        countryID = pos
        updateData(CountryList, countryID)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        countryID = 11
        updateData(CountryList, countryID)
        // Another interface callback
    }


}