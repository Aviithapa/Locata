package com.example.locata.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class logitute(
    val _id:String?=null,
    val longitude:String?=null,
    val latitude:String?=null,
    val direction:String?=null
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}