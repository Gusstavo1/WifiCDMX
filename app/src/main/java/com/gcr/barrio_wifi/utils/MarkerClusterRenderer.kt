package com.gcr.barrio_wifi.utils

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.gcr.barrio_wifi.R
import com.gcr.barrio_wifi.models.ItemMarker
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator

class MarkerClusterRenderer(
    context: Context,
    googleMap: GoogleMap,
    clusterManager: ClusterManager<ItemMarker>
): DefaultClusterRenderer<ItemMarker>(context,googleMap,clusterManager) {
    private val MARKER_DIMENSION = 100
    private var iconGenerator: IconGenerator? = null
    private var markerImageView: ImageView? = null
    init {
        iconGenerator = IconGenerator(context)
        markerImageView = ImageView(context)
        markerImageView!!.setLayoutParams(ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION))
        markerImageView!!.setBackgroundColor(getColor(R.color.colorPrimary))
        iconGenerator!!.setContentView(markerImageView)
    }

    override fun onBeforeClusterItemRendered(item: ItemMarker, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerImageView?.setImageResource(R.drawable.wifi)
        var icon = iconGenerator?.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
        markerOptions.title(item?.getTitle())
    }
}