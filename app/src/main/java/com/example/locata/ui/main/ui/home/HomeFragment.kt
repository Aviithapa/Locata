package com.example.locata.ui.main.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.locata.R
import com.example.locata.utils.checkInternetConnection
import com.example.locata.utils.checkRunTimePermission
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class HomeFragment : Fragment(), OnMapReadyCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var tvGpsLocation: EditText
    private var mMap: GoogleMap? = null
    private var lastLocation: Location? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mLastLocation: Location
    private lateinit var mLocationCallback: LocationCallback
    private var mCurrLocationMarker: Marker? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private lateinit var mLocationRequest: LocationRequest
    private var mFusedLocationClient: FusedLocationProviderClient? = null
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
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
                val latLng = LatLng(lastLocation!!.latitude, lastLocation!!.longitude)
//                mMap!!.addMarker(MarkerOptions().position(latLng).title("Your current location"))
                mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))

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


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }

    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(requireContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }

    override fun onConnected(bundle: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            mFusedLocationClient?.requestLocationUpdates(mLocationRequest, LocationCallback(), Looper.myLooper())
        }
    }


    override fun onLocationChanged(location: Location) {
        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }
        val latLng = LatLng(location.latitude, location.longitude)
        mMap!!.addMarker(MarkerOptions().position(latLng).title("Marker in Dillibazar"))
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))

        Toast.makeText(requireContext(),"connection failed", Toast.LENGTH_SHORT).show()
        //stop location updates
        if (mGoogleApiClient != null) {
            mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Toast.makeText(requireContext(),"connection failed", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionSuspended(p0: Int) {
        Toast.makeText(requireContext(),"connection suspended", Toast.LENGTH_SHORT).show()
    }
}
