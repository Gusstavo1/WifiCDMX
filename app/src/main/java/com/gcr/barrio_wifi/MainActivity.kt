package com.gcr.barrio_wifi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gcr.barrio_wifi.api.ApiHelper
import com.gcr.barrio_wifi.api.RetrofitClient
import com.gcr.barrio_wifi.models.alcaldia.RecordX
import com.gcr.barrio_wifi.ui.MainViewModel
import com.gcr.barrio_wifi.ui.adapters.RvAdapter
import com.gcr.barrio_wifi.ui.adapters.RvAlcaldiaAdapter
import com.gcr.barrio_wifi.utils.Status
import com.gcr.barrio_wifi.utils.VMfactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    RvAlcaldiaAdapter.ItemClickListener {
    private lateinit var viewModel: MainViewModel
    private val TAG = "Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        viewModel.getLocalData(this)
        setUpObserverLocalData()
        //setUpAlcaldiaObserver()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this, VMfactory(
                ApiHelper(RetrofitClient.apiService)
            )
        ).get(MainViewModel::class.java)
    }

    private fun setUpObserverLocalData(){
        viewModel.lista.observe(this, Observer {result->
            rvAlcaldia.visibility = View.VISIBLE
            rvAlcaldia.setHasFixedSize(true)
            rvAlcaldia.layoutManager = LinearLayoutManager(this)
            val sortAlcaldias = result.sortedBy {
                it.nomgeo
            }
            val adapter = RvAlcaldiaAdapter(sortAlcaldias,this)
            rvAlcaldia.adapter = adapter
        })
    }

    /*
    private fun setUpAlcaldiaObserver() {
        viewModel.getAlcaldia().observe(this, Observer { result ->
            result.let { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        loading.visibility = View.GONE
                        rvAlcaldia.visibility = View.VISIBLE
                        rvAlcaldia.setHasFixedSize(true)
                        rvAlcaldia.layoutManager = LinearLayoutManager(this)

                        val alcaldia = result.data!!.result.records.sortedBy {
                            it.nomgeo
                        }
                        val adapter =
                            RvAlcaldiaAdapter(
                                alcaldia,
                                this
                            )
                        rvAlcaldia.adapter = adapter
                    }
                    Status.LOADING -> {
                        loading.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        loading.visibility = View.GONE
                        Toast.makeText(this,"Algo salio mal",Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }*/

    /*private fun setUpObserver() {
        hideViews()
        viewModel.getData().observe(this, Observer { result ->
            result.let { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        rvWifi.visibility = View.VISIBLE
                        rvWifi.setHasFixedSize(true)
                        rvWifi.layoutManager = LinearLayoutManager(this)
                        val adapter = RvAdapter(
                            result.data!!.result.records,
                            this
                        )
                        rvWifi.adapter = adapter
                    }
                    Status.LOADING -> {
                        Toast.makeText(this, "Cargando", Toast.LENGTH_SHORT).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }*/

    override fun clickListener(item:RecordX) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("polygon", item.geo_shape)
        intent.putExtra("alcaldia",item.nomgeo)
        intent.putExtra("json",item.json_file)
        startActivity(intent)
    }
}