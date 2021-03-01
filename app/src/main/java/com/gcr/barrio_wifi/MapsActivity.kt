package com.gcr.barrio_wifi

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gcr.barrio_wifi.models.Coordinates
import com.gcr.barrio_wifi.models.ItemMarker
import com.gcr.barrio_wifi.models.wifipoints.WifiPoint
import com.gcr.barrio_wifi.utils.MarkerClusterRenderer
import com.gcr.barrio_wifi.utils.OpenFile
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.maps.android.clustering.ClusterManager
import java.io.InputStream
import java.lang.Exception


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnPolylineClickListener,
    GoogleMap.OnPolygonClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var polygon: String
    private lateinit var jsonFile: String
    private lateinit var alcaldia: String
    private lateinit var clusterManager: ClusterManager<ItemMarker>

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
        //selectJsonFile(alcaldia)
        createCoordinates(this, mMap)
        createPolygons(googleMap)
    }

    override fun onPolylineClick(polynline: Polyline) {
        Log.d(TAG, "onPolylineClick: ")
    }

    override fun onPolygonClick(polygon: Polygon) {
        Log.d(TAG, "onPolygonClick: ")
    }
    // Convertidor
    private fun jsonToGson(fileName: String): WifiPoint {
        return Gson().fromJson(OpenFile.loadJson(this, fileName), WifiPoint::class.java)
    }

    private fun createMarker(
        latitud: Double,
        longitud: Double,
        title: String,
        snippet: String
    ): ItemMarker {
        val marker = ItemMarker(latitud, longitud, title, snippet)
        return marker
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
        area.strokeColor = Color.rgb(185, 41, 56)
        area.fillColor = Color.argb(50, 185, 41, 56)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points[points.size - 1], 12f))

        googleMap.setOnPolylineClickListener(this)
        googleMap.setOnPolygonClickListener(this)
    }

    private fun createCoordinates(context: Context, googleMap: GoogleMap) {
        clusterManager = ClusterManager(this,googleMap)
        clusterManager.renderer = MarkerClusterRenderer(context, googleMap, clusterManager)
        clusterManager.setAnimation(true)
        googleMap.setOnCameraIdleListener(clusterManager)
        googleMap.setOnMarkerClickListener(clusterManager)

        val listItemMarker = mutableListOf<ItemMarker>()
        // Crea los marcadores
        jsonToGson(jsonFile).forEachIndexed { index, item ->
            val latString =
                item.latitud.substring(0, 2) + "." + item.latitud.substring(2, item.latitud.length)
            val lngString = item.longitud.substring(0, 3) + "." + item.longitud.substring(
                3,
                item.longitud.length
            )
            val latDouble = latString.toDouble()
            val lngDouble = lngString.toDouble()
            listItemMarker.add(
                createMarker(
                    latDouble,
                    lngDouble,
                    item.estatus_conectividad,
                    item.direccion
                )
            )
        }
        clusterManager.addItems(listItemMarker)
        clusterManager.cluster()
    }
}

      