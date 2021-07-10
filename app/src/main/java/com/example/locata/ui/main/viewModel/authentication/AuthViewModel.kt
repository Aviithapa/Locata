package com.example.locata.ui.main.viewModel.authentication


import androidx.lifecycle.ViewModel
import com.example.locata.data.db.entities.User
import com.example.locata.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var user:User?=null

    suspend fun userLogin(
         user: User
    ) = withContext(Dispatchers.IO) { repository.LoginUser(user) }

    fun getLoggedInUser() = repository.getUser()

    suspend fun userSignup(
        user: User
    ) = withContext(Dispatchers.IO) { repository.RegisterUser(user) }

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)
}