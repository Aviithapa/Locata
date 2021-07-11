package com.example.locata.ui.main.view.main.ui.home


import androidx.lifecycle.ViewModel
import com.example.locata.data.repository.UserRepository

class HomeViewModel (
repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()

    val location = repository.getlocation()
}