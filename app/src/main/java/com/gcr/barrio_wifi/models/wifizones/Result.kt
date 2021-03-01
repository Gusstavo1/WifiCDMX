package com.gcr.barrio_wifi.models.wifizones

data class Result(
    val include_total: Boolean,
    val limit: Int,
    val records: List<Record>,
    val records_format: String,
    val resource_id: String,
    val total_estimation_threshold: Any
)