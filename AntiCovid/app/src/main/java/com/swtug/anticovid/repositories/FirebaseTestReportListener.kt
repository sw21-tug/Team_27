package com.swtug.anticovid.repositories
import com.swtug.anticovid.models.TestReport

interface FirebaseTestReportListener {
    fun onSuccess(testReports: ArrayList<TestReport>)
    fun onStart()
    fun onFailure()
}
