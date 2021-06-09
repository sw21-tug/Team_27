package com.swtug.anticovid

import com.swtug.anticovid.models.TestReport
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

object TestReportProvider {
    private var testReports: ArrayList<TestReport> = ArrayList()

    fun setTestReports(newTestReports: ArrayList<TestReport>) {
        testReports = newTestReports
        testReports.sortByDescending { it.testdate }
    }

    fun getAllTestReports(): ArrayList<TestReport> {
        return testReports
    }

    fun getOnlyValidTestReports(currentDate: LocalDateTime): ArrayList<TestReport> {
        val validReports = ArrayList<TestReport>()
        for(report in testReports) {
            if(report.validdate.isAfter(currentDate)) {
                validReports.add(report)
            }
        }

        return validReports
    }
}