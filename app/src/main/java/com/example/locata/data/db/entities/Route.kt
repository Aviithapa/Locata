package com.example.locata.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Route (
         val _id:String?=null,
         val RouteName:String?=null,
         val RouteWay:ArrayList<Location>?=null,
         val created_at:String?=null
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}