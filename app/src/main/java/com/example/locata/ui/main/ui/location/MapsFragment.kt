package com.example.locata.ui.main.ui.location

import android.annotation.SuppressLint
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MapsFragment : Fragment(), OnMapReadyCallback {
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        MarkerPoints = ArrayList<LatLng>()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Dubai and move the camera
        val dubai = LatLng(	27.704122, 	85.324952)
        mMap!!.addMarker(MarkerOptions().position(dubai).title("Abhishek"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(dubai))


        mMap!!.setOnMapClickListener { point ->
            // Already two locations
            if (MarkerPoints.size > 1) {
                MarkerPoints.clear()
                mMap!!.clear()
            }
            // Adding new item to the ArrayList
            MarkerPoints.add(point)
            // Creating MarkerOptions
            val options = MarkerOptions()
            // Setting the position of the marker
            options.position(point)
            /**
             * For the start location, the color of marker is GREEN and
             * for the end location, the color of marker is RED.
             */

            /**
             * For the start location, the color of marker is GREEN and
             * for the end location, the color of marker is RED.
             */
            if (MarkerPoints.size == 1) {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            } else if (MarkerPoints.size == 2) {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            }
            // Add new marker to the Google Map Android API V2
            mMap!!.addMarker(options)
            // Checks, whether start and end locations are captured
            if (MarkerPoints.size >= 2) {
                origin = MarkerPoints[0]
                dest = MarkerPoints[1]
                // Getting URL to the Google Directions API
                val url = getUrl(origin, dest)
                Log.d("onMapClick", url.toString())
                val FetchUrl = FetchUrl()

                // Start downloading json data from Google Directions API
                FetchUrl.execute(url)
                //move map camera
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(origin))
                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
            }
        }

    }


    private fun getUrl(origin: LatLng, dest: LatLng): String {
        // Origin of route
        val str_origin = "origin=" + origin.latitude + "," + origin.longitude
        // Destination of route
        val str_dest = "destination=" + dest.latitude + "," + dest.longitude
        // Sensor enabled
        val sensor = "sensor=false"
        // Building the parameters to the web service
        val parameters = "$str_origin&$str_dest&$sensor"
        // Output format
        val output = "json"
        // Building the url to the web service
        val url = "https://maps.googleapis.com/maps/api/directions/$output?$parameters"
        return url
    }

    /** A class to download data from Google Directions URL */
    // Fetches data from url passed
    private inner class FetchUrl : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg url: String): String {
            // For storing data from web service
            var data = ""
            try {
                // Fetching the data from web service
                data = downloadUrl(url[0])
                Log.d("Background Task data", data.toString())
            } catch (e: Exception) {
                Log.d("Background Task", e.toString())
            }
            return data
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            val parserTask = ParserTask()
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result)
        }
    }
    @Throws(IOException::class)
    private fun downloadUrl(strUrl: String): String {
        var data = ""
        var iStream: InputStream? = null
        var urlConnection: HttpURLConnection? = null
        try {
            val url = URL(strUrl)
            // Creating an http connection to communicate with url
            urlConnection = url.openConnection() as HttpURLConnection
            // Connecting to url
            urlConnection.connect()
            // Reading data from url
            iStream = urlConnection.inputStream
            val br = BufferedReader(InputStreamReader(iStream!!))
            val sb = StringBuffer()
            var line = ""
//            while ((line = br.readLine()) != null) {
//                sb.append(line)
//            }
            while(line!=null){
                line = br.readLine()
                sb.append(line)
            }
            data = sb.toString()
            Log.d("downloadUrl", data.toString())
            br.close()

        } catch (e: Exception) {
            Log.d("Exception", e.toString())
        } finally {
            iStream!!.close()
            urlConnection!!.disconnect()
        }
        return data
    }

    /**
     * Method to decode polyline points
     * Courtesy : https://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     */
    private fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5,
                    lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }

    private inner class ParserTask : AsyncTask<String, Int, List<List<HashMap<String, String>>>>() {

        // Parsing the data in non-ui thread
        override fun doInBackground(vararg jsonData: String): List<List<HashMap<String, String>>> {
            val jObject: JSONObject
            try {
                jObject = JSONObject(jsonData[0])
                Log.d("ParserTask", jsonData[0])
                val parser = DirectionJSONParser()
                Log.d("ParserTask", parser.toString())
                // Starts parsing data
                var routes: List<List<HashMap<String, String>>>  = parser.parse(jObject)
                Log.d("ParserTask", "Executing routes")
                Log.d("ParserTask", routes.toString())
                return routes
            } catch (e: Exception) {
                Log.d("ParserTask", e.toString())
                e.printStackTrace()
            }
            val r:List<List<HashMap<String, String>>> = ArrayList<ArrayList<HashMap<String, String>>>()
            return r
        }

        // Executes in UI thread, after the parsing process
        override fun onPostExecute(result: List<List<HashMap<String, String>>>) {
            var points: ArrayList<LatLng>
            var lineOptions: PolylineOptions? = null
            // Traversing through all the routes
            for (i in result.indices) {
                points = ArrayList<LatLng>()
                lineOptions = PolylineOptions()
                // Fetching i-th route
                val path = result[i]
                // Fetching all the points in i-th route
                for (j in path.indices) {
                    val point = path[j]
                    val lat = java.lang.Double.parseDouble(point["lat"])
                    val lng = java.lang.Double.parseDouble(point["lng"])
                    val position = LatLng(lat, lng)
                    points.add(position)
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points)
                lineOptions.width(10f)
                lineOptions.color(Color.RED)
                Log.d("onPostExecute", "onPostExecute lineoptions decoded")
            }
            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                mMap!!.addPolyline(lineOptions)
            } else {
                Log.d("onPostExecute", "without Polylines drawn")
            }
        }
    }





}


