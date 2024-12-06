package com.example.florys.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.florys.databinding.ActivityProfileBinding
import com.example.florys.helper.ViewModelFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import com.example.florys.R
import com.example.florys.data.pref.UserPreference

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userPreference:UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        openGallery()

        loadImageFromStorage()
        deleteProfileImage()
    }

    private fun openGallery() {
        binding.btnEdit.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }
    }
    private fun saveImage(bitmap: Bitmap) {
        val filename = "profile_image.jpg"
        val directory = File(filesDir, "images")

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, filename)

        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show()

            saveImageFileName(filename)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveImageFileName(fileName: String) {
        val sharedPreferences = getSharedPreferences(NAME_KEY, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("imageFileName", fileName)
        editor.apply()
    }

    private fun loadImageFromStorage() {
        val sharedPreferences = getSharedPreferences(NAME_KEY, MODE_PRIVATE)
        val fileName = sharedPreferences.getString("imageFileName", "")

        if (!fileName.isNullOrEmpty()) {
            val directory = File(filesDir, "images")
            val file = File(directory, fileName)

            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                binding.ivAvatar.setImageBitmap(bitmap)
            } else {
                binding.ivAvatar.setImageResource(R.drawable.profile)
            }
        }
    }

    private fun deleteProfileImage() {
        val filename = "profile_image.jpg"
        val directory = File(filesDir, "images")

        val file = File(directory, filename)
        if (file.exists()) {
            file.delete()
            Toast.makeText(this, "Profile image deleted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Profile image not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[ProfileViewModel::class.java]

        profileViewModel.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
                if (user.type.equals("Producer")) {
                    binding.name.text = "Producer : " + user.name
                } else  {
                    binding.name.text = "Username :  " + user.name
                    binding.email.text = "Id User :  " + user.id
                }
            }
        }
    }
    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val NAME_KEY = "MyPrefs"
    }
}

