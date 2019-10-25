package org.wit.hillfortapp.models

interface UserStore {
    fun findAll(): ArrayList<UserModel>
    fun findOne(email: String, password: String): UserModel?
    fun create(user: UserModel)
    fun update(user: UserModel)
    fun deleteUser(user: UserModel)

    // Hillfort functionality
    fun findAllHillforts(): ArrayList<HillfortModel>?
    fun findAllUserHillforts(activeUser: UserModel): ArrayList<HillfortModel>
    fun findOneUserHillfortNotes(activeUser: UserModel, hillfort: HillfortModel): ArrayList<Note>?
    fun findOneUserHillfort(hillfortID: Int, activeUser: UserModel): HillfortModel?
    fun createHillfort(hillfort: HillfortModel, activeUser: UserModel)
    fun updateHillfort(hillfort: HillfortModel, activeUser: UserModel)
    fun deleteHillfort(hillfort: HillfortModel, activeUser: UserModel)
    fun deleteAllHillforts(activeUser: UserModel)

}