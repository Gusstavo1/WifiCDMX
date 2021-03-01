package com.gcr.barrio_wifi.utils

import android.content.Context
import com.gcr.barrio_wifi.models.wifipoints.WifiPoint
import java.io.InputStream
import java.lang.Exception

class OpenFile {
    companion object {
        fun loadJson(context: Context, fileName: String): String? {
            var inputStream: InputStream? = null
            val jsonString: String
            try {
                inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                jsonString = String(buffer)
                return jsonString
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                inputStream?.close()
            }
            return null
        }
    }
}