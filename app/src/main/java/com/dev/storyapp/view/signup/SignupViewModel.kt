package com.dev.storyapp.view.signup

import androidx.lifecycle.ViewModel
import com.dev.storyapp.data.api.ApiConfig
import com.dev.storyapp.data.model.ErrorResponse
import com.dev.storyapp.data.model.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class SignupViewModel: ViewModel() {
    fun register(name: String, email: String, password: String): Flow<RegisterResponse> = flow {
        try {
            val response = ApiConfig.getApiService().register(name, email, password)
            emit(response)
        } catch (e: Exception) {
            val errorMessage: String? = when (e) {
                is HttpException -> {
                    try {
                        val jsonInString = e.response()?.errorBody()?.string()
                        val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                        errorBody.message
                    } catch (ex: Exception) {
                        ex.message
                    }
                }
                else -> e.message
            }
            emit(RegisterResponse(error = true, message = errorMessage ?: "Terjadi kesalahan"))
        }
    }.catch { e ->
        emit(RegisterResponse(error = true, message = e.message ?: "Terjadi kesalahan"))
    }


}