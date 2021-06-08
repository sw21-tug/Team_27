package com.swtug.anticovid.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.swtug.anticovid.models.User
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.swtug.anticovid.R
import com.swtug.anticovid.models.TestReport
import kotlin.jvm.Throws

object QRCodeGenerator {

    private const val DEFAULT_WIDTH = 500
    private const val DEFAULT_HEIGHT = 500

    @Throws(WriterException::class)
    fun generateQRCode(qrCodeContent: String) : Bitmap  {
        return generateQRCodeWithSize(qrCodeContent, DEFAULT_WIDTH, DEFAULT_HEIGHT)
    }

    fun generateQRCodeWithSize(qrCodeContent: String, width: Int, height: Int) : Bitmap {

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()

        val bitMatrix = codeWriter.encode(
            qrCodeContent,
            BarcodeFormat.QR_CODE,
            width,
            height
        )
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        return bitmap
    }

}


