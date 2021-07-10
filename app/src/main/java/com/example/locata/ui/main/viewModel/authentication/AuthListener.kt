package com.example.locata.ui.main.viewModel.authentication

import com.example.locata.data.response.UserResponse

interface AuthListener {
    fun onStarted()
    fun onSuccess(loginResponse: UserResponse)
    fun onFailure(msg : String)
}