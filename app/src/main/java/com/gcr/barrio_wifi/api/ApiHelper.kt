package com.gcr.barrio_wifi.api

import com.gcr.barrio_wifi.models.alcaldia.Alcaldia
import com.gcr.barrio_wifi.models.wifizones.Response

class ApiHelper(
    private val service: Service

) {
    suspend fun getData(
        id: String,
        limit: String
    ): Response {
        return service.getData(id, limit)
    }

    suspend fun getAlcaldia(
        id: String,
        limit: String
    ): Alcaldia {
        return service.getAlcaldia(id, limit)
    }
}