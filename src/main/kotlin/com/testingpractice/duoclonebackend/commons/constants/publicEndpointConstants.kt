package com.testingpractice.duoclonebackend.commons.constants
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.AUTH
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.CATALOG
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.COURSES
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.FOLLOWS
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_ALL_COURSES
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_AVATARS
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_FOLLOWS_BY_USER
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_LESSON_IDS_BY_UNIT
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_PAGINATED_LEADERBOARD
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_SECTIONS_BY_COURSE
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_SECTIONS_FROM_IDS
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_UNITS_BY_SECTION
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_UNITS_FROM_IDS
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_UNIT_IDS_BY_SECTION
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_USER_BY_ID
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GET_USER_COURSES
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GOOGLE_LOGIN
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.GOOGLE_LOGOUT
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.LEADERBOARD
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.SECTIONS
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.SECTION_TREE
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.UNITS
import com.testingpractice.duoclonebackend.commons.constants.pathConstants.USERS


object publicEndpointConstants {

    val PUBLIC_ENDPOINTS = arrayOf(
        "/actuator/**",
        AUTH + GOOGLE_LOGIN,
        AUTH + GOOGLE_LOGOUT,
        COURSES + GET_SECTIONS_BY_COURSE,
        COURSES + GET_ALL_COURSES,
        FOLLOWS + GET_FOLLOWS_BY_USER,
        LEADERBOARD + GET_PAGINATED_LEADERBOARD,
        SECTIONS + GET_SECTIONS_FROM_IDS,
        SECTIONS + GET_UNITS_BY_SECTION,
        SECTIONS + GET_UNIT_IDS_BY_SECTION,
        UNITS + GET_LESSON_IDS_BY_UNIT,
        UNITS + GET_UNITS_FROM_IDS,
        USERS + GET_USER_BY_ID,
        USERS + GET_AVATARS,
        COURSES + GET_USER_COURSES,
        CATALOG + SECTION_TREE
    )
}