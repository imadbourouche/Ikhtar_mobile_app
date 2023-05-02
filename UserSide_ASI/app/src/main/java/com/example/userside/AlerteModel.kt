package com.example.rentgo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.userside.Alerte
import com.example.userside.R

class AlerteModel (application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val alertes: List<Alerte>

    fun loadData():List<Alerte> {
        val alertesTab = context.resources.getStringArray(R.array.alertesList)
        val list = mutableListOf<Alerte>()
        for (i in alertesTab.indices) {
            list.add(Alerte(
                id = 0,
                date = alertesTab[i]
            )
            )
        }
        return  list
    }

    init {
        this.alertes = loadData()
    }
}
