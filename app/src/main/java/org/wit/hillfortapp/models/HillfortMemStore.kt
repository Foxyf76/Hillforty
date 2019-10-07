package org.wit.hillfortapp.models

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.*


var lastHillfortId = 0L

internal fun getHillfortId(): Long {
    return lastHillfortId++
}

class HillfortMemStore : HillfortStore, AnkoLogger {
    val hillforts = ArrayList<HillfortModel>()
    var gson = Gson()

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun findOne(id: Long): HillfortModel {
        return hillforts.single { hillfort ->
            hillfort.id == id
        }
    }

    override fun create(hillfort: HillfortModel) {
        hillfort.id = getId()
        hillforts.add(hillfort)
        logAll()
    }

    override fun update(hillfort: HillfortModel) {
        val foundHillfort: HillfortModel? = hillforts.find { h -> h.id == hillfort.id }
        if (foundHillfort != null) {
            foundHillfort.id = hillfort.id
            foundHillfort.description = hillfort.description
            foundHillfort.location = hillfort.location
            foundHillfort.images = hillfort.images
            foundHillfort.notes = hillfort.notes
            foundHillfort.visited = hillfort.visited
            foundHillfort.dateVisited = hillfort.dateVisited
        }
    }

    private fun logAll() {
        hillforts.forEach { info("${it}") }
    }
}