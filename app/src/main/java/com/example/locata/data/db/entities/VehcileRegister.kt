package com.example.locata.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class VehcileRegister(
    val _id:String?=null,
val vehicle_no:String?=null,
val user_id:String?=null,
val Location:ArrayList<logitute>?=null,
val created_at:String?=null
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}