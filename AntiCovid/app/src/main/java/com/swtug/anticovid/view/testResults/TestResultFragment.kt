package com.swtug.anticovid.view.testResults

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.swtug.anticovid.MainActivity
import com.swtug.anticovid.R
import com.swtug.anticovid.TestReportProvider
import com.swtug.anticovid.extensions.createPreConfirmDialog
import com.swtug.anticovid.extensions.createQRCodeDialog
import com.swtug.anticovid.models.FirebaseTestReport
import com.swtug.anticovid.models.TestReport
import com.swtug.anticovid.models.User
import com.swtug.anticovid.repositories.FirebaseRepo
import com.swtug.anticovid.repositories.FirebaseTestReportListener
import com.swtug.anticovid.repositories.PreferencesRepo
import com.swtug.anticovid.utils.DateTimeUtils
import java.time.LocalDateTime

class TestResultFragment : Fragment(R.layout.fragment_test_results) {

    private lateinit var testResultsView: RecyclerView
    private lateinit var testResultAdapter: TestResultsRecyclerAdapter
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var disableView: View
    private lateinit var fab: FloatingActionButton
    private lateinit var emptyListText: TextView
    private lateinit var emptyListImage: ImageView
    private lateinit var checkOnlyValids: CheckBox

    override fun onResume() {
        super.onResume()
        updateTestReports(false, checkOnlyValids.isChecked)
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
        checkOnlyValids = view.findViewById(R.id.checkShowOnlyValidTests)

        val currentUser = PreferencesRepo.getUser(requireContext())
        val vaccinated = PreferencesRepo.getVaccination(requireContext()) != null

        testResultAdapter = TestResultsRecyclerAdapter(currentUser)
    }

    private fun initListeners() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addTestReportFragment)
        }

        testResultAdapter.setOnItemClickListener(
            onDeleteClicked = { testReport ->
                (activity as? MainActivity?)?.createPreConfirmDialog(testReport) { report -> deleteTestReport(report) }?.show()
            },
            onItemClicked = { testReport, user ->
                (activity as? MainActivity?)?.createQRCodeDialog(testReport, user)
                    ?.show()
            }
        )

        swipeLayout.setOnRefreshListener {
            updateTestReports(true, checkOnlyValids.isChecked)
        }

        checkOnlyValids.setOnClickListener {
            updateTestReports(false, checkOnlyValids.isChecked)
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
                Snackbar.make(
                    requireView(),
                    getString(R.string.report_delete_success),
                    Snackbar.LENGTH_LONG
                ).show()
                updateTestReports(false, checkOnlyValids.isChecked)
            }

            override fun onStart() {
                showLoadingScreen(false)
            }

            override fun onFailure() {
                Snackbar.make(
                    requireView(),
                    getString(R.string.error_firebase_communication),
                    Snackbar.LENGTH_LONG
                ).show()
                hideLoadingScreen(false)
            }
        })
    }

    private fun showLoadingScreen(isSwipeAction: Boolean) {
        if (!isSwipeAction) {
            progressIndicator.show()
        }
        disableView.visibility = View.VISIBLE
    }

    private fun hideLoadingScreen(isSwipeAction: Boolean) {
        if (!isSwipeAction) {
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

    private fun updateTestReports(isSwipeAction: Boolean, showOnlyValids: Boolean) {
        val loggedInUser = PreferencesRepo.getUser(requireContext())
        if (loggedInUser != null) {
            FirebaseRepo.getAllTestReportsFrom(
                loggedInUser.email,
                object : FirebaseTestReportListener {
                    override fun onSuccess(testReports: ArrayList<TestReport>) {
                        if (testReports.isEmpty()) {
                            showEmptyView()
                        } else {
                            hideEmptyView()
                        }

                        TestReportProvider.setTestReports(testReports)

                        testResultAdapter.setNewDataSet(
                            if (showOnlyValids) {
                                TestReportProvider.getOnlyValidTestReports(LocalDateTime.now())
                            } else {
                                TestReportProvider.getAllTestReports()
                            }
                        )
                        testResultAdapter.notifyDataSetChanged()
                        hideLoadingScreen(isSwipeAction)
                    }

                    override fun onStart() {
                        showLoadingScreen(isSwipeAction)
                    }

                    override fun onFailure() {
                        Snackbar.make(
                            requireView(),
                            getString(R.string.error_firebase_communication),
                            Snackbar.LENGTH_LONG
                        ).show()
                        hideLoadingScreen(isSwipeAction)
                    }
                })
        } else {
            hideLoadingScreen(isSwipeAction)
        }
    }

}