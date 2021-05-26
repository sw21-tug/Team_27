package com.swtug.anticovid

import com.swtug.anticovid.models.TestReport

object TestReportProvider {
    private var testReports: ArrayList<TestReport> = ArrayList()

    fun setTestReports(newTestReports: ArrayList<TestReport>) {
        testReports = newTestReports
    }

    fun getAllTestReports(): ArrayList<TestReport> {
        return testReports
    }
}