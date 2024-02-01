package com.demo.demosignup

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.demo.demosignup.databinding.ActivitySignUpBinding
import com.demo.demosignup.profiledetails.ProfileActivity
import java.io.ByteArrayOutputStream

class SignUpActivity : AppCompatActivity() {
    private lateinit var _binding:ActivitySignUpBinding
    private val viewModel:SignUpViewModel by viewModels()

    // Register the camera permission request
    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCameraForImage()
            } else {
               Toast.makeText(applicationContext,"Please Enable Camera Permission ",Toast.LENGTH_LONG).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        _binding.btnSignUp.setOnClickListener{
            val email= _binding.etEmail.text.toString()
            val username= _binding.etUsername.text.toString()
            val password= _binding.etPassword.text.toString()
            val webpath= _binding.etwebsite.text.toString()
            val imagepath= viewModel.selectedProfilePictureUri.value?.let { it.toString() }?:""
            viewModel.signUpUser(email,username,password, webpath,imagepath)
        }
        _binding.ivProfilePicture.setOnClickListener{
            checkCameraPermissionAndOpenCamera()
        }
        viewModel.result.observe(this) { isSuccess ->
            if (isSuccess){
               navigateToProfile()
            }else{
                Toast.makeText(applicationContext,"Please enter valid email , password and other input field",Toast.LENGTH_LONG).show()
            }
        }
        viewModel.selectedProfilePictureUri.observe(this){
           it?.let {imageUri->
               _binding.ivProfilePicture.setImageURI(imageUri)
           }
        }
    }

    private fun navigateToProfile() {
        val nextScreen= Intent(this,ProfileActivity::class.java)
        nextScreen.apply {
            putExtra(USERNAME,_binding.etUsername.text.toString())
            putExtra(EMAIL,_binding.etEmail.text.toString())
            putExtra(IMAGE_PATH,viewModel.selectedProfilePictureUri.value.toString())
            putExtra(WEBPATH,_binding.etwebsite.text.toString())
        }
        startActivity(nextScreen)
    }

    private fun checkCameraPermissionAndOpenCamera() {
        if (hasCameraPermission()) {
            openCameraForImage()
        } else {
            requestCameraPermission.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun hasCameraPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun openCameraForImage() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    data?.extras?.get("data")?.let { bitmap ->
                        val uri = getImageUriFromBitmap(bitmap as Bitmap)
                        viewModel.setSelectedProfilePictureUri(uri)
                    }
                }
            }
        }
    }

    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "profile",
            null
        )
        return Uri.parse(path)
    }

    companion object {
        const val CAMERA_REQUEST_CODE = 2
        const val WEBPATH="webpath"
        const val EMAIL="email"
        const val USERNAME="username"
        const val IMAGE_PATH="imagepath"

    }
}