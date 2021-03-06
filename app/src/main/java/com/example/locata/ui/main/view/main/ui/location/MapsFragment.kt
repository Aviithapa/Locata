package com.example.locata.ui.main.view.main.ui.location

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.locata.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request



class MapsFragment : Fragment() {
    private val MY_PERMISSIONS_REQUEST = 123
    private var mMap: GoogleMap? = null
    private lateinit var origin:LatLng
    private lateinit var dest:LatLng
    internal lateinit var MarkerPoints: ArrayList<LatLng>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_maps, container, false)
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//        mapFragment?.getMapAsync(this)
//        MarkerPoints = ArrayList<LatLng>()
    }

//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//
//        val URL = getDirectionURL("Makassar", "Bandung")
//        GetDirection(URL).execute()
//    }
//
//    // Membuat function untuk origun dan destination
//    fun getDirectionURL(origin: String, destination: String) : String{
//        return ""
//    }
//
//    private inner class GetDirection(val url: String): AsyncTask<Void, Void, List<List<LatLng>>>(){
//        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
//            val client = OkHttpClient()
//            val request = Request.Builder().url(url).build()
//            val response = client.newCall(request).execute()
//            val data = response.body()?.string()
//            Log.d("GoogleMaps", "data : $data")
//
//            val result = ArrayList<List<LatLng>>()
//            try {
//                val response = Gson().fromJson(data, PojoDirection::class.java)
//                val path = ArrayList<LatLng>()
//
//                for (i in 0..(response.routes[0].legs[0].steps.size-1)){
//                    path.addAll(decodePolyLine(response.routes[0].legs[0].steps[i].polyline.points))
//                }
//                result.add(path)
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//            return result
//
//        }
//
//        override fun onPostExecute(result: List<List<LatLng>>) {
//            val lineoption = PolylineOptions()
//            for (i in result.indices){
//                lineoption.addAll(result[i])
//                lineoption.width(10f)
//                lineoption.color(Color.BLUE)
//                lineoption.geodesic(true)
//
//            }
//
//            mMap?.addPolyline(lineoption)
//        }
//    }
//
//    fun decodePolyLine(encode: String): List<LatLng>{
//        val poly = ArrayList<LatLng>()
//        var index = 0
//        val len = encode.length
//        var lat = 0
//        var lng = 0
//
//        while (index < len){
//            var b: Int
//            var shift =  0
//            var result =  0
//            do {
//                b = encode[index++].toInt() - 63
//                result = result or (b and 0x1f shl shift)
//                shift +=5
//            }while (b >= 0x20)
//            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
//            lat += dlat
//
//            shift = 0
//            result = 0
//            do {
//                b = encode[index++].toInt() - 63
//                result = result or (b and 0x1f shl shift)
//                shift += 5
//            }while (b >= 0x20)
//            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
//            lng += dlng
//
//            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
//            poly.add(latLng)
//        }
//
//        return poly
//    }
}

