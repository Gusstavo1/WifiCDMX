package com.gcr.barrio_wifi.api

import com.gcr.barrio_wifi.models.alcaldia.Alcaldia
import com.gcr.barrio_wifi.models.wifizones.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("api/3/action/datastore_search?")
    suspend fun getData(
        @Query("resource_id") id: String,
        @Query("limit") limit: String
    ): Response

    @GET("api/3/action/datastore_search?")
    suspend fun getAlcaldia(
        @Query("resource_id") id: String,
        @Query("limit") limit: String
    ): Alcaldia
}