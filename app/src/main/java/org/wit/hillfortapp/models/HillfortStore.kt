package org.wit.hillfortapp.models

interface HillfortStore {

    fun findAllHillforts(): List<HillfortModel>?
    fun findOneHillfort(hillfortID: Int): HillfortModel?
    fun findHillfortsByName(name:String): ArrayList<HillfortModel>?
    fun createHillfort(hillfort: HillfortModel)
    fun updateHillfort(hillfort: HillfortModel)
    fun deleteHillfort(hillfort: HillfortModel)
    fun deleteAllHillforts(activeUserID: Int)
    fun toggleFavourite(hillfort: HillfortModel)
    fun findOneFavourite(hillfort: HillfortModel): Boolean
    fun findAllFavourites(): ArrayList<HillfortModel>?
    fun sortByRating(): List<HillfortModel>?

}