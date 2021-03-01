package com.gcr.barrio_wifi.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gcr.barrio_wifi.models.alcaldia.Alcaldia
import com.gcr.barrio_wifi.models.alcaldia.RecordX
import com.gcr.barrio_wifi.repository.Repository
import com.gcr.barrio_wifi.utils.OpenFile
import com.gcr.barrio_wifi.utils.Response
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val TAG = "VModel"
    val lista = MutableLiveData<List<RecordX>>()

    fun getData() = liveData(Dispatchers.IO) {
        emit(Response.loading(data = null))
        try {
            emit(
                Response.success(
                    data = repository.getData(
                        "7bb21e65-d6d1-44c3-8db0-ee91012ac2d9",
                        "100"
                    )
                )
            )
            Log.d(TAG, "getData: success!")
        } catch (exception: java.lang.Exception) {
            emit(Response.error(exception.message.toString(), null))
            Log.d(TAG, "Error: ${exception.message}")
        }
    }

    fun getAlcaldia() = liveData(Dispatchers.IO) {
        emit(Response.loading(data = null))
        try {
            emit(
                Response.success(
                    data = repository.getAlcaldia(
                        "e4a9b05f-c480-45fb-a62c-6d4e39c5180e",
                        "16"
                    )
                )
            )
            Log.d(TAG, "getAlcaldia: success!")
        } catch (exception: java.lang.Exception) {
            emit(Response.error(exception.message.toString(), null))
            Log.d(TAG, "Error: ${exception.message}")
        }
    }

    // OpenFile.loagJson lee un archivo json  retornando una cadena String
    // gson es el archivo convertido a objeto kotlin
    fun getLocalData(context: Context) {
        val gson = Gson().fromJson(OpenFile.loadJson(context, "alcaldias"), Alcaldia::class.java)
        lista.value =  gson.result.records
    }
}