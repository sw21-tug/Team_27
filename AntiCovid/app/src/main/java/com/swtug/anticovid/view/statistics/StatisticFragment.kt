package com.swtug.anticovid.view.statistics

import android.icu.number.NumberFormatter.with
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
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.swtug.anticovid.MainActivity
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
    private lateinit var fab: FloatingActionButton

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
                    initFields(view)
                    setErrorMessage()
                }
                initListeners()
            }
            override fun onFailure(call: Call<List<MyCountry>>, t: Throwable) {
                Toast.makeText(context, getString(R.string.statistic_error_message), Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun initListeners() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_informationAndRulesFragment)
        }
    }

    private fun initFields(view: View) {
        fab = view.findViewById(R.id.fab_show_information_and_rules)

        spinnerCountries = view.findViewById(R.id.spinner)
        spinnerCountries.onItemSelectedListener = this
        if(activity !is MainActivity)
            return
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCountries.adapter = adapter
            spinnerCountries.setSelection(AUSTRIA)

            totalCases = view.findViewById(R.id.totalcasesvalue)
            totalTest = view.findViewById(R.id.totaltestedvalue)
            totalDeath = view.findViewById(R.id.totaldeathvalue)
            todayCases = view.findViewById(R.id.casestodayvalue)
            todayDeath = view.findViewById(R.id.deathtodayvalue)
            todayRecovered = view.findViewById(R.id.recoveredtodayvalue)
            totalRecovered = view.findViewById(R.id.totalrecoveredvalue)
            population = view.findViewById(R.id.populationvalue)
            update = view.findViewById(R.id.lastupdatevalue)
            imvFlag = view.findViewById(R.id.imv_Showflag)
            active = view.findViewById(R.id.activevalue)
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
            Picasso.get()
                .load(country.countryInfo.flag)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(imvFlag)


        }

    }

    private fun setErrorMessage(){
        active.text = getString(R.string.statistic_error_message)
        totalCases.text = getString(R.string.statistic_error_message)
        totalTest.text = getString(R.string.statistic_error_message)
        totalDeath.text = getString(R.string.statistic_error_message)
        todayCases.text = getString(R.string.statistic_error_message)
        todayDeath.text = getString(R.string.statistic_error_message)
        todayRecovered.text = getString(R.string.statistic_error_message)
        totalRecovered.text = getString(R.string.statistic_error_message)
        population.text = getString(R.string.statistic_error_message)
        update.text = getString(R.string.statistic_error_message)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        countryID = pos
        updateData(CountryList, countryID)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        countryID = 11
        updateData(CountryList, countryID)
    }


}