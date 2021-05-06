package com.swtug.anticovid.view.testResults

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swtug.anticovid.view.BaseFragment
import com.swtug.anticovid.R

class TestResultFragment: BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_test_results, null)
    }
}