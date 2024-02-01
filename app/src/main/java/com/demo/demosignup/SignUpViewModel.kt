package com.demo.demosignup

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel:ViewModel() {
    private var _result= MutableLiveData<Boolean>()
    private var _selectedProfilePictureUri= MutableLiveData<Uri>()
    val result:LiveData<Boolean> get() =_result
    val selectedProfilePictureUri:LiveData<Uri> get() =_selectedProfilePictureUri
    fun signUpUser(email:String,username:String, password:String,webpath:String,imagepath:String){
        val isAddedAllField = email.isEmailValid() && password.isNotBlank() && username.isNotBlank() && imagepath.isNotBlank() && webpath.isNotBlank()
        _result.value =isAddedAllField
    }

    fun setSelectedProfilePictureUri(uri: Uri) {
        _selectedProfilePictureUri.value=uri
    }

    private fun String.isEmailValid(): Boolean {
        // Basic email validation
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return this.matches(emailRegex.toRegex())
    }
}