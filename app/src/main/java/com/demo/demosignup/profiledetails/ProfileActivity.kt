package com.demo.demosignup.profiledetails

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.demosignup.R
import com.demo.demosignup.SignUpActivity
import com.demo.demosignup.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(_binding.root)

       with(intent){
            getStringExtra(SignUpActivity.USERNAME)?.let {
                _binding.txtUsername.text =it
                _binding.title.text="Hello, $it!"
            }
            getStringExtra(SignUpActivity.EMAIL)?.let {
                _binding.txtEmail.text =it
            }
            getStringExtra(SignUpActivity.IMAGE_PATH)?.let {
                _binding.ivProfilePicture.setImageURI(Uri.parse(it))
            }
            getStringExtra(SignUpActivity.WEBPATH)?.let {
                _binding.txtWebsite.text =it
            }
        }
    }
}