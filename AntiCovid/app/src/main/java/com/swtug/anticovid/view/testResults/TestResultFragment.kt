package com.swtug.anticovid.view.testResults

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.swtug.anticovid.R

class TestResultFragment: Fragment(R.layout.fragment_test_results) {

    private lateinit var testResultsView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var testResultAdapter: RecyclerView.Adapter<TestResultsRecyclerAdapter.ViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initFields(view)

        testResultsView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = TestResultsRecyclerAdapter()
        }

        initListeners()
    }

    private fun initFields(view: View) {
        testResultsView = view.findViewById(R.id.testResultsView)
        fab = view.findViewById(R.id.fab_add_test_report)
    }

    private fun initListeners() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addTestReportFragment)
        }
    }
}