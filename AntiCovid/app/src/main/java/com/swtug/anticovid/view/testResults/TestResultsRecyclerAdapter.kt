package com.swtug.anticovid.view.testResults

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.swtug.anticovid.DateTimeUtils
import com.swtug.anticovid.R
import com.swtug.anticovid.TestReportProvider
import java.util.*

class TestResultsRecyclerAdapter : RecyclerView.Adapter<TestResultsRecyclerAdapter.ViewHolder>() {

    private val testResults = TestReportProvider.getAllTestReports()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var testResultDateText: TextView = itemView.findViewById(R.id.cardTextTestDate)
        var testResultText: TextView = itemView.findViewById(R.id.cardTextTestResult)
        var testValidDateText: TextView = itemView.findViewById(R.id.cardTextValidDate)
        var testResultImageView: ImageView = itemView.findViewById(R.id.cardResultImage)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.test_result_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val testReport = testResults[i]

        viewHolder.testResultDateText.text = DateTimeUtils.getStringFromDate(testReport.testdate)
        viewHolder.testValidDateText.text = DateTimeUtils.getStringFromDate(testReport.validdate)

        if(testReport.testresult) {
            setPositiveResultImage(viewHolder.itemView.context, viewHolder.testResultImageView)
            viewHolder.testResultText.text = viewHolder.itemView.context.getString(R.string.positive)
        } else {
            setNegativeResultImage(viewHolder.itemView.context, viewHolder.testResultImageView)
            viewHolder.testResultText.text = viewHolder.itemView.context.getString(R.string.negative)
        }
    }

    private fun setPositiveResultImage(context: Context, imageView: ImageView) {
        imageView.background = ContextCompat.getDrawable(context, R.drawable.ic_corona_foreground)
        imageView.background.setTint(context.getColor(R.color.errorRed))
    }

    private fun setNegativeResultImage(context: Context, imageView: ImageView) {
        imageView.background = ContextCompat.getDrawable(context, R.drawable.ic_happy_emote_foreground)
        imageView.background.setTint(context.getColor(R.color.happyGreen))
    }

    override fun getItemCount(): Int {
        return testResults.size
    }
}