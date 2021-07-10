package com.example.locata.data.response

import com.example.locata.data.model.User

data class UserResponse (
    val success:Boolean?=null,
    val token:String?=null,
    val message:String?=null,
    val data:MutableList<User>?=null,
    val id:String?=null
){
}