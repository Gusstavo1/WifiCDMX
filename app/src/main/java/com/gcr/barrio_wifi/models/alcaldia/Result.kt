package com.gcr.barrio_wifi.models.alcaldia

data class Result(
    val include_total: Boolean,
    val limit: Int,
    val records: List<RecordX>,
    val records_format: String,
    val resource_id: String,
    val total_estimation_threshold: Any
)