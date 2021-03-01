package com.gcr.barrio_wifi.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gcr.barrio_wifi.api.ApiHelper
import com.gcr.barrio_wifi.repository.Repository
import com.gcr.barrio_wifi.ui.MainViewModel

class VMfactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                Repository(
                    apiHelper
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}