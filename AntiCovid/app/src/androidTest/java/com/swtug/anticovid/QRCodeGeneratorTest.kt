package com.swtug.anticovid

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.swtug.anticovid.qrCode.QRCodeGenerator
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QRCodeGeneratorTest {

    private lateinit var qrCodeGenerator: QRCodeGenerator

    private lateinit var qrCodeTestContent: String

    @Before
    fun setUp() {
        qrCodeGenerator = QRCodeGenerator()
        qrCodeTestContent = "Informations \n" +
                "\n" +
                "Simon, Schrimpf \n" +
                "Test Date: 19.04.2021 \n" +
                "Valid till: 21.04.2021\n" +
                "Result: negative"
    }

    @Test
    fun testQRCodeCreation() {
        val bitmap = qrCodeGenerator.generateQRCode(qrCodeTestContent)

        assertNotNull(bitmap)
    }
}