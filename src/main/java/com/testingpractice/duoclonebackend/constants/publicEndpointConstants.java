package com.testingpractice.duoclonebackend.constants;

import static com.testingpractice.duoclonebackend.constants.pathConstants.*;
import static com.testingpractice.duoclonebackend.constants.pathConstants.AUTH;
import static com.testingpractice.duoclonebackend.constants.pathConstants.COURSES;
import static com.testingpractice.duoclonebackend.constants.pathConstants.FOLLOWS;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GET_ALL_COURSES;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GET_AVATARS;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GET_FOLLOWS_BY_USER;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GET_LESSON_IDS_BY_UNIT;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GET_PAGINATED_LEADERBOARD;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GET_SECTIONS_BY_COURSE;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GET_SECTIONS_FROM_IDS;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GET_UNITS_BY_SECTION;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GET_UNITS_FROM_IDS;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GET_UNIT_IDS_BY_SECTION;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GET_USER_BY_ID;
import static com.testingpractice.duoclonebackend.constants.pathConstants.GOOGLE_LOGOUT;
import static com.testingpractice.duoclonebackend.constants.pathConstants.LEADERBOARD;
import static com.testingpractice.duoclonebackend.constants.pathConstants.SECTIONS;
import static com.testingpractice.duoclonebackend.constants.pathConstants.UNITS;
import static com.testingpractice.duoclonebackend.constants.pathConstants.USERS;

public class publicEndpointConstants {

    public static final String[] PUBLIC_ENDPOINTS = {
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
    };

}
