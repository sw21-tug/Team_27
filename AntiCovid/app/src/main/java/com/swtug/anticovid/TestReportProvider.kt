package com.swtug.anticovid

import com.swtug.anticovid.models.TestReport
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

object TestReportProvider {
    private var testReports: ArrayList<TestReport> = ArrayList()

    fun setTestReports(newTestReports: ArrayList<TestReport>) {
        testReports = newTestReports
    }

    fun getAllTestReports(): ArrayList<TestReport> {
        testReports.sortByDescending { it.testdate }
        return testReports
    }
}