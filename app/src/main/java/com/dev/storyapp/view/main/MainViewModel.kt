package com.dev.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dev.storyapp.data.UserRepository
import com.dev.storyapp.data.api.ApiConfig
import com.dev.storyapp.data.model.StoryResponse
import com.dev.storyapp.data.pref.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _storyResponse = MutableLiveData<StoryResponse>()
    val storyResponse: LiveData<StoryResponse>
        get() = _storyResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getStories(token : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiService(token).getStories()
                if (response.error == false) {
                    _storyResponse.postValue(response)
                }
            } catch (e: Exception) {
                // Handle network errors or exceptions
            }
        }

    }
}