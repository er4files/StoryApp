package com.dev.storyapp.view.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dev.storyapp.data.UploadRepository
import com.dev.storyapp.data.UserRepository
import com.dev.storyapp.data.pref.UserModel
import java.io.File

class UploadViewModel(
    private val repository: UploadRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun uploadImage(token:String, file: File, description: String) = repository.uploadImage(token, file, description)

}