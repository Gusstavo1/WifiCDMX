package com.gcr.barrio_wifi

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gcr.barrio_wifi.models.Coordinates
import com.gcr.barrio_wifi.models.wifipoints.WifiPoint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import java.io.InputStream
import java.lang.Exception


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnPolylineClickListener,
    GoogleMap.OnPolygonClickListener {

    private val COLOR_ORANGE_ARGB = -0xa80e9

    private lateinit var mMap: GoogleMap
    private lateinit var polygon: String
    private lateinit var jsonFile: String
    private lateinit var alcaldia: String
    private val TAG = "MAP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val bundle = intent.extras
        polygon = bundle?.getString("polygon").toString()
        jsonFile = bundle?.getString("json").toString()
        alcaldia = bundle?.getString("alcaldia").toString()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        selectJsonFile(alcaldia)
        createCoordinates()
        createPolygons(googleMap)
    }

    override fun onPolylineClick(polynline: Polyline) {
        Log.d(TAG, "onPolylineClick: ")
    }

    override fun onPolygonClick(polygon: Polygon) {
        Log.d(TAG, "onPolygonClick: ")
    }

    private fun selectJsonFile(alcaldia: String) {
        when (alcaldia) {
            "Azcapotzalco" -> {
                jsonFile = "azcapotzalco.json"
            }
            "lvaro Obregn" -> {
                jsonFile = "alvaro_obregon.json"
            }
            "Benito Jurez" -> {
                jsonFile = "benito_juarez.json"
            }
        }
    }

    private fun loadJson(context: Context, fileName: String): String? {
        var inputStream: InputStream? = null
        val jsonString: String
        try {
            inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            jsonString = String(buffer)
            return jsonString
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            inputStream?.close()
        }
        return null
    }

    // convierte el file json a Objeto
    private fun jsonToGson(fileName: String): WifiPoint {
        return Gson().fromJson(loadJson(this, fileName), WifiPoint::class.java)
    }

    private fun createMarker(
        lat: Double,
        lng: Double,
        title: String,
        googleMap: GoogleMap
    ) {
        googleMap.apply {
            addMarker(
                MarkerOptions()
                    .position(LatLng(lat, lng))
                    .title(title)
            )
        }
    }

    private fun createPolygons(googleMap: GoogleMap) {
        val points = mutableListOf<LatLng>()
        val polygons = Gson().fromJson(polygon, Coordinates::class.java)
        polygons.coordinates.forEachIndexed { index, list ->
            list.forEachIndexed { index, coordinates ->
                points.add(LatLng(coordinates[1], coordinates[0]))
            }
        }
        val area = googleMap.addPolygon(
            PolygonOptions()
                .clickable(true)
                .addAll(points)
        )
        area.tag = "BARRIO WIFI"
        area.isGeodesic = true
        area.fillColor = COLOR_ORANGE_ARGB
        area.strokeColor = COLOR_ORANGE_ARGB
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points[points.size - 1], 15f))

        googleMap.setOnPolylineClickListener(this)
        googleMap.setOnPolygonClickListener(this)
    }

    private fun createCoordinates() {
        Log.d(TAG, "onCreate: ${jsonToGson(jsonFile).forEachIndexed { index, item ->
            val latString =
                item.latitud.substring(0, 2) + "." + item.latitud.substring(2, item.latitud.length)
            val lngString = item.longitud.substring(0, 3) + "." + item.longitud.substring(
                3,
                item.longitud.length
            )
            val latDouble = latString.toDouble()
            val lngDouble = lngString.toDouble()
            createMarker(latDouble, lngDouble, item.estatus_conectividad, mMap)
        }}")
    }
}

      