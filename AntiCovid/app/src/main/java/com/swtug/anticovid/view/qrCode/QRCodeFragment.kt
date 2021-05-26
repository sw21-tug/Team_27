package com.swtug.anticovid.view.qrCode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.zxing.WriterException
import com.swtug.anticovid.R


class QRCodeFragment : Fragment() {

    private val qrCodeGenerator = QRCodeGenerator()

    companion object {
        const val TAG = "qrTag"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_qr_code, container, false)
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
        val bitmap = try {
            qrCodeGenerator.generateQRCode(qrCodeContent)
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }

        imvShowQRCode.setImageBitmap(bitmap)

        val textField = view.findViewById<TextView>(R.id.txvQRScreen)

        textField.text = qrCodeContent
    }

}