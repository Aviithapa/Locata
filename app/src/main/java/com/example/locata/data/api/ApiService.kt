package com.example.locata.data.api

import com.example.locata.data.db.entities.User
import com.example.locata.data.response.LocationResponse
import com.example.locata.data.response.UserResponse
import com.example.locata.utils.APIConstant.UserLogin
import com.example.locata.utils.APIConstant.UserRegister
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @POST(UserRegister)
    suspend fun registerUSer(@Body user: User):Response<UserResponse>

    @POST(UserLogin)
    suspend fun login(
        @Body user: User
    ):Response<UserResponse>


    @GET("location/display")
    suspend fun location():Response<LocationResponse>

    
    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ApiService{

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

}