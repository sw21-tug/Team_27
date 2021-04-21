package com.swtug.anticovid.qrCode

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.swtug.anticovid.BaseFragment
import com.swtug.anticovid.R


class QRCodeFragment : BaseFragment() {

    companion object {
        private const val TAG = "qrTag"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_qr_code, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imvShowQRCode = view.findViewById<ImageView>(R.id.QRScreenQRCodeImg)

        val qrCodeContent = "Informations \n" +
                "\n" +
                "Simon, Schrimpf \n" +
                "Test Date: 19.04.2021 \n" +
                "Valid till: 21.04.2021\n" +
                "Result: negative"   //TODO get Data from saved bitmap
        val bitmap = generateQRCode(qrCodeContent)
        imvShowQRCode.setImageBitmap(bitmap)

        val textField = view.findViewById<TextView>(R.id.txvQRScreen)

        textField.text = qrCodeContent
    }

    private fun generateQRCode(qrCodeContent: String): Bitmap {

        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            Log.d(TAG, "generateQRCode: ${e.message}")
        }
        return bitmap
    }


}