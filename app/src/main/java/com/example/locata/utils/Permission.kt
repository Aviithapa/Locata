package com.example.locata.utils

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.core.app.ActivityCompat
var connectivity : ConnectivityManager? = null
var info : NetworkInfo? = null

val permissions = arrayOf(
    android.Manifest.permission.ACCESS_FINE_LOCATION,
    android.Manifest.permission.ACCESS_COARSE_LOCATION,
    android.Manifest.permission.ACCESS_BACKGROUND_LOCATION,
    android.Manifest.permission.INTERNET,
)

fun checkRunTimePermission(activity: Activity) {
    if (!hasPermission(activity)) {
        requestPermission(activity)
    }
}

fun hasPermission(activity: Activity): Boolean {
    var hasPermission = true
    for (permission in permissions) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            hasPermission = false
            break
        }
    }
    return hasPermission
}

fun requestPermission(activity: Activity) {
    ActivityCompat.requestPermissions(activity, permissions, 1)
}


fun checkInternetConnection(context:Context){

    connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    if ( connectivity != null)
    {
        info = connectivity!!.activeNetworkInfo

        if (info != null)
        {
            if (info!!.state == NetworkInfo.State.CONNECTED)
            {
                Toast.makeText(context, "CONNECTED", Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            Toast.makeText(context, "NOT CONNECTED", Toast.LENGTH_LONG).show()
        }
    }
}