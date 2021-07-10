package com.example.locata.data.repository

import com.example.locata.data.api.ApiRequest
import com.example.locata.data.api.ApiService
import com.example.locata.data.db.AppDatabase
import com.example.locata.data.db.entities.User
import com.example.locata.data.response.UserResponse

class UserRepository(
    private val api: ApiService,
    private val db: AppDatabase
):ApiRequest() {

    suspend fun RegisterUser(user: User): UserResponse {
        return apiRequest {
            api.registerUSer(user)
        }
    }
    suspend fun LoginUser(user: User):UserResponse   {
        return apiRequest {
            api.login(user)
        }
    }


    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()
}