package com.swtug.anticovid.view.testResults

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swtug.anticovid.R

class TestResultFragment: Fragment() {

    private lateinit var testResultsView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var testResultAdapter: RecyclerView.Adapter<TestResultsRecyclerAdapter.ViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_test_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testResultsView = view.findViewById(R.id.testResultsView)
        testResultsView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = TestResultsRecyclerAdapter()
        }
    }
}