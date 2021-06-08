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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.swtug.anticovid.MainActivity
import com.swtug.anticovid.api.CountryService
import com.swtug.anticovid.api.ServiceBuilder
import com.swtug.anticovid.models.Country
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StatisticFragment : Fragment(R.layout.fragment_statistic) , AdapterView.OnItemSelectedListener{

    private val viewModel: StatisticsViewModel by activityViewModels()

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields(view)
        initListeners()
        setupLayout()

        viewModel.selectCountry(AUSTRIA)
        viewModel.selectedCountry.observe(viewLifecycleOwner) {
            it?.let {
                updateData(it)
            } ?: setErrorMessage()
        }
    }

    private fun setupLayout() {
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
        }

    }

    private fun initListeners() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_informationAndRulesFragment)
        }
    }

    private fun initFields(view: View) {
        fab = view.findViewById(R.id.fab_show_information_and_rules)
        spinnerCountries = view.findViewById(R.id.spinner)
        totalCases = view.findViewById(R.id.infected_total_count)
        totalTest = view.findViewById(R.id.tested_total_count)
        totalDeath = view.findViewById(R.id.death_total_count)
        todayCases = view.findViewById(R.id.infected_count)
        todayDeath = view.findViewById(R.id.death_count)
        todayRecovered = view.findViewById(R.id.recovered_count)
        totalRecovered = view.findViewById(R.id.recovered_total_count)
        population = view.findViewById(R.id.txt_population_count)
        update = view.findViewById(R.id.last_updated_timestamp)
        imvFlag = view.findViewById(R.id.imv_Showflag)
        active = view.findViewById(R.id.active_cases)
    }


    private fun updateData(country: Country) {
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
                .into(imvFlag)
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
        viewModel.selectCountry(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        viewModel.selectCountry(AUSTRIA)
    }
}