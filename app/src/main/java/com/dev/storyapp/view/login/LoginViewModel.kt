package com.dev.storyapp.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.storyapp.data.UserRepository
import com.dev.storyapp.data.api.ApiConfig
import com.dev.storyapp.data.model.LoginResponse
import com.dev.storyapp.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    suspend fun login(email: String, password: String): LoginResponse? {
        try {
            return ApiConfig.getApiService().login(email, password)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}