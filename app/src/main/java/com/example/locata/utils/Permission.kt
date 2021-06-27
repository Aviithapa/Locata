package com.example.locata.utils

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.DialogInterface
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


fun checkInternetConnection(context: Context):Boolean{

    connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    if ( connectivity != null)
    {
        info = connectivity!!.activeNetworkInfo

        if (info != null)
        {
            if (info!!.state == NetworkInfo.State.CONNECTED)
            {
               return true
            }
        }
        else
        {
            getAlertBox("No Internet Connection","You are offline please check your internet connection",context)
            return false
        }
    }
    return false
}

fun getAlertBox(title:String, message: String,context: Context){
    val builder = AlertDialog.Builder(context)
    //Uncomment the below code to Set the message and title from the strings.xml file
    //Uncomment the below code to Set the message and title from the strings.xml file
    builder.setMessage(message).setTitle(title)

    //Setting message manually and performing action on button click

    //Setting message manually and performing action on button click
    builder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id -> checkInternetConnection(context)  })

    val alert: AlertDialog = builder.create()
    alert.setTitle(title)
    alert.show()
}