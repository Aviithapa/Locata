package com.example.locata.data.repository

import com.example.locata.data.api.ApiRequest
import com.example.locata.data.api.ApiService
import com.example.locata.data.db.AppDatabase
import com.example.locata.data.db.entities.Location
import com.example.locata.data.db.entities.Route
import com.example.locata.data.db.entities.User
import com.example.locata.data.db.entities.VehcileRegister
import com.example.locata.data.response.LocationResponse
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

    suspend fun Location():LocationResponse{
        return apiRequest {
            api.location()
        }
    }

    suspend fun vehicleRegister(vehcileRegister: VehcileRegister):UserResponse{
        return  apiRequest {
            api.vehicleRegister(vehcileRegister)
        }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()

    suspend fun saveRoute(location: Location) = db.getRouteDao().upsertLocation(location)

    fun getRoute() = db.getRouteDao().getlocation()
}