package com.example.locata.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity
data class User(
    val _id:String?=null,
    val name:String?=null,
    val username:String?=null,
    val phone_number:String?=null,
    val password:String?=null,
    val role:String?=null,
    val user_image:String?=null,
    val licence_image:String?=null,
    val created_at:String?=null
){
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}