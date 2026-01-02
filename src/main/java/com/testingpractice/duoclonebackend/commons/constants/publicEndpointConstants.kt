package com.testingpractice.duoclonebackend.commons.constants

import com.testingpractice.duoclonebackend.commons.constants.pathConstants.*

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