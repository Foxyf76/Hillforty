package org.wit.hillfortapp.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfortapp.helpers.exists
import org.wit.hillfortapp.helpers.read
import org.wit.hillfortapp.helpers.write
import java.util.*
import kotlin.collections.ArrayList

val JSON_FILE = "users.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class UserJSONStore : UserStore, AnkoLogger {

    val context: Context
    var users = arrayListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): ArrayList<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.id = generateRandomId().toInt()
        users.add(user)
        serialize()
    }

    override fun update(user: UserModel) {
        val foundUser: UserModel? = users.find { u -> u.id == user.id }
        if (foundUser != null) {
            foundUser.email = user.email
            foundUser.password = user.password
            foundUser.hillforts = user.hillforts
        }
        serialize()
    }

    override fun findOne(email: String, password: String): UserModel {
        return users.single { user ->
            user.email == email && user.password == password
        }
    }

    // Hillfort Functionality

    override fun findAllHillforts(activeUser: UserModel): ArrayList<HillfortModel> {
        return activeUser.hillforts
    }

    override fun findOneHillfort(hillfortID: Int, activeUser: UserModel): HillfortModel {
        return activeUser.hillforts.single { hillfort ->
            hillfort.id == hillfortID
        }
    }

    override fun createHillfort(hillfort: HillfortModel, activeUser: UserModel) {
        hillfort.id = getHillfortId()
        activeUser.hillforts.add(hillfort)
        serialize()
    }

    override fun updateHillfort(hillfort: HillfortModel, activeUser: UserModel) {
        val foundHillfort = findOneHillfort(hillfort.id, activeUser)
        activeUser.hillforts[foundHillfort.id] = hillfort
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(users, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        users = Gson().fromJson(jsonString, listType)
    }
}