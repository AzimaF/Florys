package com.example.florys.ui.qr

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.florys.R
import com.example.florys.databinding.FragmentQrBinding
import com.example.florys.helper.ViewModelFactory
import com.example.florys.ui.productitemdetail.ProductItemDetailActivity
import com.example.florys.ui.qr.QRFragment.Companion.NAME_KEY
import com.example.florys.ui.qr.QRFragment.Companion.PICK_IMAGE_REQUEST
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class QRFragment : Fragment() {

    private var _binding: FragmentQrBinding? = null
    private lateinit var qrViewModel: QRViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        qrViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireActivity().applicationContext)
        )[QRViewModel::class.java]

        _binding = FragmentQrBinding.inflate(inflater, container, false)
        val root: View = binding.root

        codeScanner()
        setPermission()
        setData()
        setupAction()
        openGallery()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData(){
        qrViewModel.productitem.observe(viewLifecycleOwner) { productsItem ->
            binding.outputQR.text = productsItem.data.name
        }
    }
    private fun setupAction(){
        binding.btnShowinfo.setOnClickListener {
            val intent = Intent(context, ProductItemDetailActivity::class.java)
            intent.putExtra("ProductItem", qrViewModel.productitem.value?.data)
            startActivity(intent)
        }
    }
    private fun codeScanner(){
        codeScanner = CodeScanner(requireContext(), binding.scanner)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                requireActivity().runOnUiThread {
                    qrViewModel.getProductItem(it.text)
                    binding.btnShowinfo.visibility = View.VISIBLE
                    Log.d("TEST", it.text)
                }
            }

            errorCallback = ErrorCallback {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }

            binding.scanner.setOnClickListener(){
                codeScanner.startPreview()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.startPreview()

    }

    override fun onDestroy() {
        // Pastikan untuk melepaskan sumber daya CodeScanner saat fragment di-destroy
        codeScanner.releaseResources()
        super.onDestroy()
    }

    private fun setPermission(){
        val permission = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED){
            makeReq()
        }
    }

    private fun makeReq() {
        requestPermissions(
            arrayOf(android.Manifest.permission.CAMERA),
            101
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            101 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(requireContext(), "Permission is Needed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openGallery() {
        binding.btnQr.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }
    }


    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val NAME_KEY = "MyPrefs"
    }
}