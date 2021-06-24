package com.example.locata.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


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
