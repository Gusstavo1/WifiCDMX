package com.gcr.barrio_wifi.models.wifizones

data class Record(
    val _id: Int,
    val alcaldia: String,
    val ancho_de_banda: String,
    val clave_colonia: String,
    val colonia: String,
    val estatus_conectividad: String,
    val geo_point_2d: String,
    val geo_shape: String,
    val id: Int,
    val id_stv: String,
    val nombre_de_la_red: String,
    val tipo_de_infraestructura: String
)