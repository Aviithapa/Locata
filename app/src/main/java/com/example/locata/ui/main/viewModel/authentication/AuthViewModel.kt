package com.example.locata.ui.main.viewModel.authentication


import android.view.View
import androidx.lifecycle.ViewModel
import com.example.locata.data.model.User
import com.example.locata.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var username:String?=null
    var password:String?=null

    suspend fun userLogin(
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) { repository.LoginUser(email, password) }



    suspend fun userSignup(
        user: User
    ) = withContext(Dispatchers.IO) { repository.RegisterUser(user) }

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)
}