package com.gcr.barrio_wifi.repository

import com.gcr.barrio_wifi.api.ApiHelper
import com.gcr.barrio_wifi.models.alcaldia.Alcaldia
import com.gcr.barrio_wifi.models.wifizones.Response

class Repository(private val apiHelper: ApiHelper) {
    suspend fun getData(id: String, limit: String): Response {
        return apiHelper.getData(id, limit)
    }

    suspend fun getAlcaldia(id: String, limit: String): Alcaldia {
        return apiHelper.getAlcaldia(id, limit)
    }
}