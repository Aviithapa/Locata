package com.example.locata.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Location (
        val _id:String?=null,
        val name:String?=null,
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}