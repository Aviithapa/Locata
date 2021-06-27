package com.example.locata.ui.main.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.locata.R
import com.example.locata.utils.checkInternetConnection
import com.example.locata.utils.checkRunTimePermission
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.Marker
import java.util.*


class HomeFragment : Fragment(), LocationListener, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mMap: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: EditText
    private var lastLocation: Location? = null
    private var latitudeLabel: String? = null
    private var longitudeLabel: String? = null
    private val locationPermissionCode = 2
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest? = null

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        return root
    }


     override fun onStart() {
        super.onStart()
        checkRunTimePermission(requireActivity())
         if (checkInternetConnection(requireContext())){
             getLastLocation()
         }

    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient?.lastLocation!!.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful && task.result != null) {
                lastLocation = task.result
                val geocoder: Geocoder
                val addresses: List<Address>
                geocoder = Geocoder(requireContext(), Locale.getDefault())
                addresses = geocoder.getFromLocation(
                    (lastLocation)!!.latitude,
                    (lastLocation)!!.longitude,
                    1
                ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                val address = addresses[0].subLocality
                val cityName = addresses[0].locality
                val stateName = addresses[0].adminArea
                tvGpsLocation.setText("${address + ' ' + cityName}")
            }
            else {
                Log.w(TAG, "getLastLocation:exception", task.exception)
                showMessage("No location detected. Make sure location is enabled on the device.")
            }
        }
    }
    private fun showMessage(string: String) {

            Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()

    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }
    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showMessage("Location permission is needed for core functionality")
        }
        else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    getLastLocation()
                }
                else -> {

                }
            }
        }
    }
    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

    override fun onLocationChanged(location: Location) {
                val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(requireContext(), Locale.getDefault())
        addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        val loc: String= addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    }

    override fun onConnected(p0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }
}
