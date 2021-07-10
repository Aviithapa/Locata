package com.example.locata.data.api

import com.example.locata.data.model.User
import com.example.locata.data.response.UserResponse
import com.example.locata.utils.APIConstant.UserLogin
import com.example.locata.utils.APIConstant.UserRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
interface ApiService {

    @POST(UserRegister)
    suspend fun registerUSer(@Body user: User):Response<UserResponse>

    @FormUrlEncoded
    @POST(UserLogin)
    suspend fun login(
        @Field("username") username:String,
        @Field("password") password:String
    ):Response<UserResponse>

}