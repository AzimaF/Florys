package com.example.florys.ui.showqr

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.florys.data.responses.DataItem
import com.example.florys.databinding.ActivityShowQrBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ShowQrActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowQrBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
    }

    private fun setupData() {
        val productItem = intent.getParcelableExtra<DataItem>("ProductItem") as DataItem

        // Date format
        val formattedDateString: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val dateTimeString = productItem.harvestDate
            val parsedHarvestDate = LocalDateTime.parse(dateTimeString, inputFormat)
            formattedDateString = parsedHarvestDate.format(outputFormat)
        } else {
            formattedDateString = productItem.harvestDate.toString()
        }

        // Generate QR Code
        val qrCodeWriter = QRCodeWriter()
        val stringToEncode = productItem.id
        var bitMatrix: BitMatrix? = null
        val width = 350
        val height = 350

        try {
            bitMatrix = qrCodeWriter.encode(stringToEncode, BarcodeFormat.QR_CODE, width, height)
        } catch (e: WriterException) {
            e.printStackTrace()
            Toast.makeText(this, "QR Code generation failed", Toast.LENGTH_SHORT).show()
        }

        val bitmap = bitMatrix?.let {
            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                for (x in

                0 until width) {
                    for (y in

                    0 until height) {
                        setPixel(x, y, if (it.get(x, y)) Color.BLACK else Color.WHITE)
                    }
                }
            }
        }

        val imageView = binding.imgQr
        imageView.setImageBitmap(bitmap)

        binding.apply {
            textItemName.text = productItem.name
            textProducer.text = productItem.producerName
            textHarvestDate.text = formattedDateString
            textDistributor.text = productItem.distributorName
            textSeller.text = productItem.sellerName
        }
    }
}