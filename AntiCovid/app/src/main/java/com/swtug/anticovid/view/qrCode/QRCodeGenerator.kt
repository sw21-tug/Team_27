package com.swtug.anticovid.view.qrCode

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlin.jvm.Throws

class QRCodeGenerator {

    private val DEFAULT_WIDTH = 500
    private val DEFAULT_HEIGHT = 500

    @Throws(WriterException::class)
    fun generateQRCode(qrCodeContent: String) : Bitmap  {

        val bitmap = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()

        val bitMatrix = codeWriter.encode(
            qrCodeContent,
            BarcodeFormat.QR_CODE,
            DEFAULT_WIDTH,
            DEFAULT_HEIGHT
        )
        for (x in 0 until DEFAULT_WIDTH) {
            for (y in 0 until DEFAULT_HEIGHT) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        return bitmap
    }

}


