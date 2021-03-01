package com.gcr.barrio_wifi.localdata

import com.gcr.barrio_wifi.models.alcaldia.LocalDataAlcaldia

class Alcaldias {
    fun getaAlcaldias(): List<LocalDataAlcaldia> {
        return listOf(
            LocalDataAlcaldia("Álvaro Obregón", "alvaro_obregon.json",""),
            LocalDataAlcaldia("Azcapotzalco", "azcapotzalco.json",""),
            LocalDataAlcaldia("Benito Juárez", "benito_juarez.json",""))
    }
}