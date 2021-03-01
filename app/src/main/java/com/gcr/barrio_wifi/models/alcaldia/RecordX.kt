package com.gcr.barrio_wifi.models.alcaldia

data class RecordX(
    val _id: Int,
    val cve_ent: Int,
    val cve_mun: Int,
    val cvegeo: Int,
    val geo_point_2d: String,
    val geo_shape: String,
    val id: Int,
    val municipio: Int,
    val nomgeo: String,
    val json_file:String
)