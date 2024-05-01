package com.dev.storyapp.data.di

import android.content.Context
import com.dev.storyapp.data.UploadRepository
import com.dev.storyapp.data.UserRepository
import com.dev.storyapp.data.api.ApiConfig
import com.dev.storyapp.data.pref.UserPreference
import com.dev.storyapp.data.pref.dataStore

object Injection {
    fun provideRepository(): UploadRepository {
        val apiService = ApiConfig.getApiService()
        return UploadRepository.getInstance(apiService)
    }
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}