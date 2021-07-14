package com.example.locata.data.response

import com.example.locata.data.db.entities.Location
import com.example.locata.data.db.entities.Route

data class LocationResponse(
        val success:Boolean?=null,
        val message:String?=null,
        val error:String?=null,
        val data: Location?=null,
        val id:String?=null
) {
}