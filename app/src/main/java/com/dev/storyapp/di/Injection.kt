package com.dev.storyapp.di

import android.content.Context
import com.dev.storyapp.data.UserRepository
import com.dev.storyapp.data.pref.UserPreference
import com.dev.storyapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}