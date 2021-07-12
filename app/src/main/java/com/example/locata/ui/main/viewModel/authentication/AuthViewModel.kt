package com.example.locata.ui.main.viewModel.authentication


import androidx.lifecycle.ViewModel
import com.example.locata.data.db.entities.Location
import com.example.locata.data.db.entities.Route
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

   // suspend fun saveLocation() = repository.saveLocation(user)

    suspend fun Location() = withContext(Dispatchers.IO) { repository.Location() }

    fun getRoute() = repository.getRoute()

    suspend fun saveLocation(location: Location) = repository.saveRoute(location)
}