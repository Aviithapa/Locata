package com.example.locata.ui.main.view.main.ui.home


import androidx.lifecycle.ViewModel
import com.example.locata.data.db.entities.Location
import com.example.locata.data.db.entities.User
import com.example.locata.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel (
private val repository: UserRepository
) : ViewModel() {


    val user = repository.getUser()
    suspend fun Location() = withContext(Dispatchers.IO) { repository.Location() }
    val data:ArrayList<Location>?=null


}