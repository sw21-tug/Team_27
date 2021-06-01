package com.swtug.anticovid.view.testResults

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.swtug.anticovid.R
import com.swtug.anticovid.TestReportProvider
import com.swtug.anticovid.models.FirebaseTestReport
import com.swtug.anticovid.models.TestReport
import com.swtug.anticovid.repositories.FirebaseRepo
import com.swtug.anticovid.repositories.FirebaseTestReportListener
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.utils.DateTimeUtils
import kotlin.collections.ArrayList

class TestResultFragment: Fragment(R.layout.fragment_test_results) {

    private lateinit var testResultsView: RecyclerView
    private lateinit var testResultAdapter: TestResultsRecyclerAdapter
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var disableView: View
    private lateinit var fab: FloatingActionButton
    private lateinit var emptyListText: TextView
    private lateinit var emptyListImage: ImageView

    override fun onResume() {
        super.onResume()
        updateTestReports(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initFields(view)
        initListeners()

        testResultsView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = testResultAdapter
        }
    }

    private fun initFields(view: View) {
        testResultsView = view.findViewById(R.id.testResultsView)
        progressIndicator = view.findViewById(R.id.testReportProgressIndicator)
        swipeLayout = view.findViewById(R.id.swipeLayout)
        disableView = view.findViewById(R.id.viewDisableLayout)
        fab = view.findViewById(R.id.fab_add_test_report)
        emptyListText = view.findViewById(R.id.emptyListText)
        emptyListImage = view.findViewById(R.id.emptyListImage)

        testResultAdapter = TestResultsRecyclerAdapter()
    }

    private fun initListeners() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addTestReportFragment)
        }

        testResultAdapter.setOnItemClickListener {testReport ->
            createPreConfirmDialog(testReport).show()
        }

        swipeLayout.setOnRefreshListener {
            updateTestReports(true)
        }
    }

    private fun deleteTestReport(testReport: TestReport) {
        val firebaseReport = FirebaseTestReport(
            testReport.email,
            DateTimeUtils.getStringFromDate(testReport.testdate),
            testReport.testresult,
            DateTimeUtils.getStringFromDate(testReport.validdate)
        )

        FirebaseRepo.deleteTestReport(firebaseReport, object : FirebaseTestReportListener {
            override fun onSuccess(testReports: ArrayList<TestReport>) {
                Snackbar.make(requireView(), getString(R.string.report_delete_success), Snackbar.LENGTH_LONG).show()
                updateTestReports(false)
            }

            override fun onStart() {
                showLoadingScreen(false)
            }

            override fun onFailure() {
                Snackbar.make(requireView(), getString(R.string.error_firebase_communication), Snackbar.LENGTH_LONG).show()
                hideLoadingScreen(false)
            }
        })
    }

    private fun showLoadingScreen(isSwipeAction: Boolean) {
        if(!isSwipeAction) {
            progressIndicator.show()
        }
        disableView.visibility = View.VISIBLE
    }

    private fun hideLoadingScreen(isSwipeAction: Boolean) {
        if(!isSwipeAction) {
            progressIndicator.hide()
        }
        disableView.visibility = View.GONE
        swipeLayout.isRefreshing = false
    }

    private fun showEmptyView() {
        emptyListText.visibility = View.VISIBLE
        emptyListImage.visibility = View.VISIBLE
    }

    private fun hideEmptyView() {
        emptyListText.visibility = View.GONE
        emptyListImage.visibility = View.GONE
    }

    private fun updateTestReports(isSwipeAction: Boolean) {
        val loggedInUser = PreferencesRepo.getUser(requireContext())
        if(loggedInUser != null) {
            FirebaseRepo.getAllTestReportsFrom(
                loggedInUser.email,
                object : FirebaseTestReportListener {
                    override fun onSuccess(testReports: ArrayList<TestReport>) {
                        if(testReports.isEmpty()) {
                            showEmptyView()
                        } else {
                            hideEmptyView()
                        }

                        TestReportProvider.setTestReports(testReports)
                        testResultAdapter.setNewDataSet(TestReportProvider.getAllTestReports())
                        testResultAdapter.notifyDataSetChanged()
                        hideLoadingScreen(isSwipeAction)
                    }

                    override fun onStart() {
                        showLoadingScreen(isSwipeAction)
                    }

                    override fun onFailure() {
                        Snackbar.make(requireView(), getString(R.string.error_firebase_communication), Snackbar.LENGTH_LONG).show()
                        hideLoadingScreen(isSwipeAction)
                    }
                })
        } else {
            hideLoadingScreen(isSwipeAction)
        }
    }

    private fun createPreConfirmDialog(testReport: TestReport) : AlertDialog.Builder {
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            if(which == DialogInterface.BUTTON_POSITIVE) {
                deleteTestReport(testReport)
            }
        }

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage(getString(R.string.delete_report_question) + "\n\n"
                + getString(R.string.test_date_info) + DateTimeUtils.getStringFromDate(testReport.testdate) + "\n"
                + getString(R.string.valid_until_info) + DateTimeUtils.getStringFromDate(testReport.validdate) + "\n"
                + getString(R.string.result_info) + if(testReport.testresult) {getString(R.string.positive)} else {getString(R.string.negative)})

        alertDialogBuilder.setPositiveButton(getString(R.string.yes), dialogClickListener)
        alertDialogBuilder.setNegativeButton(getString(R.string.no), dialogClickListener)

        return alertDialogBuilder
    }
}