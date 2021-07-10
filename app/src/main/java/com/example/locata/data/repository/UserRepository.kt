package com.example.locata.data.repository

import com.example.locata.data.api.ApiRequest
import com.example.locata.data.api.ApiService
import com.example.locata.data.api.ServiceBuilder
import com.example.locata.data.model.User
import com.example.locata.data.response.UserResponse

class UserRepository:ApiRequest() {

    var api= ServiceBuilder.buildServices(ApiService::class.java)
    var db=
    suspend fun RegisterUser(user: User): UserResponse {
        return apiRequest {
            api.registerUSer(user)
        }
    }
    suspend fun LoginUser(username:String, password:String):UserResponse   {
        return apiRequest {
            api.login(username, password)
        }
    }


    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()
}