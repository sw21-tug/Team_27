package com.swtug.anticovid.extensions

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.swtug.anticovid.MainActivity
import com.swtug.anticovid.R
import com.swtug.anticovid.models.TestReport
import com.swtug.anticovid.models.User
import com.swtug.anticovid.utils.DateTimeUtils
import com.swtug.anticovid.utils.QRCodeGenerator

fun MainActivity.createPreConfirmDialog(
    testReport: TestReport,
    deleteTestReport: (TestReport) -> Unit
): AlertDialog.Builder {
    val dialogClickListener = DialogInterface.OnClickListener { _, which ->
        if (which == DialogInterface.BUTTON_POSITIVE) {
            deleteTestReport(testReport)
        }
    }

    val alertDialogBuilder = AlertDialog.Builder(this)
    alertDialogBuilder.setMessage(
        getString(R.string.delete_report_question) + "\n\n"
                + getString(R.string.test_date_info) + DateTimeUtils.getStringFromDate(
            testReport.testdate
        ) + "\n"
                + getString(R.string.valid_until_info) + DateTimeUtils.getStringFromDate(
            testReport.validdate
        ) + "\n"
                + getString(R.string.result_info) + if (testReport.testresult) {
            getString(R.string.positive)
        } else {
            getString(R.string.negative)
        }
    )

    alertDialogBuilder.setPositiveButton(getString(R.string.yes), dialogClickListener)
    alertDialogBuilder.setNegativeButton(getString(R.string.no), dialogClickListener)

    return alertDialogBuilder
}

fun MainActivity.createQRCodeDialog(
    testReport: TestReport,
    user: User?,
    vaccinated: Boolean
): AlertDialog.Builder {
    val alertDialogBuilder = AlertDialog.Builder(this)
    alertDialogBuilder.setView(createQRView(testReport, user, vaccinated))
    alertDialogBuilder.setPositiveButton(getString(R.string.ok)) { _, _ -> }

    return alertDialogBuilder
}

private fun MainActivity.createQRView(
    testReport: TestReport,
    user: User?,
    vaccinated: Boolean
): View {
    val qrView = LayoutInflater.from(this).inflate(R.layout.dialog_qr_code, null)

    qrView.findViewById<TextView>(R.id.textQRTestDate).text =
        getString(R.string.test_date_info) +
                " " + DateTimeUtils.getStringFromDate(testReport.testdate)
    qrView.findViewById<TextView>(R.id.textQRValidUntil).text =
        getString(R.string.valid_until_info) +
                " " + DateTimeUtils.getStringFromDate(testReport.validdate)

    qrView.findViewById<TextView>(R.id.textQRTestResult).text =
        getString(R.string.result_info) + " " +
                if (testReport.testresult) {
                    getString(R.string.positive)
                } else {
                    getString(R.string.negative)
                }

    user?.let {
        qrView.findViewById<TextView>(R.id.textQRUserName).text = user.name + " " + user.surname
        qrView.findViewById<TextView>(R.id.textQREmail).text = user.email
        qrView.findViewById<TextView>(R.id.textQRAddress).text = user.address
        qrView.findViewById<TextView>(R.id.textQRPhoneNumber).text = user.phonenumber
    }

    qrView.findViewById<TextView>(R.id.textQRVaccinated).text =
        getString(R.string.vaccinated) + " " +
                if (vaccinated) {
                    getString(R.string.yes)
                } else {
                    getString(R.string.no)
                }

    val sizeAsPoint = Point()
    windowManager.defaultDisplay.getRealSize(sizeAsPoint)
    val halfDisplayWidth = (sizeAsPoint.x * 0.7).toInt()
    val qrCodeBitmap = QRCodeGenerator.generateQRCodeWithSize(createQRString(testReport, user, vaccinated), halfDisplayWidth, halfDisplayWidth)
    qrView.findViewById<ImageView>(R.id.imageViewQRCode).setImageBitmap(qrCodeBitmap)

    return qrView
}

private fun MainActivity.createQRString(
    testReport: TestReport,
    user: User?,
    vaccinated: Boolean
): String {
    var qrCodeString = ""
    user?.let {
        qrCodeString += getString(R.string.name) + ": " + user.name + " " + user.surname + "\n"
    }
    qrCodeString += getString(R.string.test_date_info) + DateTimeUtils.getStringFromDate(testReport.testdate) + "\n"
    qrCodeString += getString(R.string.valid_until_info) + DateTimeUtils.getStringFromDate(testReport.validdate) + "\n"
    qrCodeString += getString(R.string.result_info) +
            if (testReport.testresult) {
                getString(R.string.positive)
            } else {
                getString(R.string.negative)
            } + "\n"

    qrCodeString += getString(R.string.vaccinated) + " " +
            if (vaccinated) {
                getString(R.string.yes)
            } else {
                getString(R.string.no)
            } + "\n"

    return qrCodeString
}