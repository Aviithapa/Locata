package com.example.locata.data.response

import com.example.locata.data.db.entities.User


data class UserResponse (
    val success:Boolean?=null,
    val token:String?=null,
    val message:String?=null,
    val error:String?=null,
    val data: User?=null,
    val id:String?=null
){
}