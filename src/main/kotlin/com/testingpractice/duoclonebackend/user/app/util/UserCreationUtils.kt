package com.testingpractice.duoclonebackend.user.app.util

import java.util.concurrent.ThreadLocalRandom

object UserCreationUtils {

    private const val BASE_URL = "https://storage.googleapis.com/ludolang-media/av"

    fun getRandomProfilePic(): String {
        val num = ThreadLocalRandom.current().nextInt(2, 14)
        return "$BASE_URL$num.png"
    }

    fun generateUsername(name: String?): String {
        val safeName = name ?: "user"
        val base = safeName.replace("\\s+".toRegex(), "").lowercase()
        val rand = ThreadLocalRandom.current().nextInt(100, 1000)
        return "$base$rand"
    }

    fun getAllProfilePics(): List<String> {
        val pics = mutableListOf<String>()
        for (i in 2..13) {
            pics.add("$BASE_URL$i.png")
        }
        return pics
    }
}