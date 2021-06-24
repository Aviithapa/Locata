package com.example.locata.ui.main.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.locata.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.*
import java.util.*


class HomeFragment : Fragment(), LocationListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mMap: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: EditText

    private val locationPermissionCode = 2
    @SuppressLint("MissingPermission")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        tvGpsLocation = root.findViewById(R.id.editTextCurrentLocation)
         getLocation()
        return root
    }
    private fun getLocation() {
        locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    override fun onLocationChanged(location: Location) {

        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(requireContext(), Locale.getDefault())

        addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        val loc = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        tvGpsLocation.setText("$loc")
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
            else {
            }
        }
    }
}