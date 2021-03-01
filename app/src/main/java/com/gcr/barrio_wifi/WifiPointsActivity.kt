package com.gcr.barrio_wifi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gcr.barrio_wifi.models.alcaldia.LocalDataAlcaldia
import com.gcr.barrio_wifi.ui.adapters.RvLocalAlcaldiaAdapter
import kotlinx.android.synthetic.main.activity_wifi_points.*

class WifiPointsActivity : AppCompatActivity(), RvLocalAlcaldiaAdapter.ItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_points)
        setUpRv()
    }

    private fun setUpRv() {
        rvAlcaldiaData.layoutManager = LinearLayoutManager(this)
        rvAlcaldiaData.setHasFixedSize(true)
        val adapter = RvLocalAlcaldiaAdapter(this)
        rvAlcaldiaData.adapter = adapter
    }

    override fun clickListener(item: LocalDataAlcaldia) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("json",item.jsonName)
        intent.putExtra("isWifiPoints","true")
        startActivity(intent)
    }
}