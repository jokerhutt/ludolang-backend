package com.testingpractice.duoclonebackend.commons.constants;

object pathConstants {

        const val API_V1 = "/api"

// ----------------------------- API PREFIXES -----------------------------
    const val COURSES = "$API_V1/courses"
    const val EXERCISES = "$API_V1/exercises"
    const val EXERCISES_ATTEMPTS = "$EXERCISES/attempts"
    const val UNITS = "$API_V1/units"
    const val USERS = "$API_V1/users"
    const val LESSONS = "$API_V1/lessons"
    const val LESSONS_COMPLETIONS = "$LESSONS/completions"
    const val SECTIONS = "$API_V1/sections"
    const val QUESTS = "$API_V1/quests"
    const val FOLLOWS = "$API_V1/follows"
    const val MONTHLY_CHALLENGES = "$API_V1/monthly-challenges"
    const val LEADERBOARD = "$API_V1/leaderboard"
    const val AUTH = "$API_V1/auth"
    const val CATALOG = "$API_V1/catalog"

// ----------------------------- GET REQUESTS -----------------------------
    const val GET_USER_COURSE_PROGRESS = "/progress/{courseId}"
    const val GET_USER_BY_ID = "/{userId}"
    const val GET_QUESTS_BY_USER = "/get"
    const val GET_MONTHLY_CHALLENGE_BY_USER = "/get"
    const val GET_FOLLOWS_BY_USER = "/{userId}"
    const val GET_TOP_LEADERBOARD = "/top"
    const val GET_ALL_COURSES = "/all"
    const val GET_AVATARS = "/avatars"
    const val GET_USER_COURSES = "/get/{userId}"

// ===== GET DTO LIST BY PARENT =====
    const val GET_UNITS_BY_SECTION = "/{sectionId}/units"
    const val GET_LESSONS_BY_UNIT = "/{unitId}/lessons"
    const val GET_SECTIONS_BY_COURSE = "/{courseID}/sections"
    const val GET_BULK_SECTIONS = "/getBulk/{sectionId}"

// !! TRANSACTIONAL !!
    const val GET_EXERCISES_BY_LESSON = "{lessonId}/exercises"

// ===== GET DTO LIST FROM IDS =====
    const val GET_UNITS_FROM_IDS = "/ids"
    const val GET_LESSONS_FROM_IDS = "/ids"
    const val GET_SECTIONS_FROM_IDS = "/ids"
    const val GET_USERS_FROM_IDS = "/ids"

// ===== GET IDS LIST BY PARENT =====
    const val GET_LESSON_IDS_BY_UNIT = "$GET_LESSONS_BY_UNIT/ids"
    const val GET_UNIT_IDS_BY_SECTION = "$GET_UNITS_BY_SECTION/ids"
    const val GET_SECTION_IDS_BY_COURSE = "$GET_SECTIONS_BY_COURSE/ids"

// ===== GET PAGINATED =====
    const val GET_PAGINATED_LEADERBOARD = "/paginated"
    const val GET_PAGINATED_FOLLOWERS_AND_FOLLOWING = "/paginated"

// ----------------------------- POST REQUESTS AND MUTATIONS -----------------------------
    const val SUBMIT_EXERCISE = "/submit"
    const val SUBMIT_COMPLETED_LESSON = "/submit"
    const val FOLLOW_USER = "/follow"
    const val UNFOLLOW_USER = "/unfollow"
    const val CHANGE_COURSE = "/change"
    const val GOOGLE_LOGIN = "/google-login"
    const val UPDATE_AVATAR = "/update-avatar"
    const val GOOGLE_LOGOUT = "/logout"
    const val AUTH_ME = "/me"

// ----------------------------- MISC -----------------------------
    const val SECTION_TREE = "/{sectionId}/tree"
    const val UNITS_IDS = "$API_V1/units/ids"
    const val LESSONS_IDS = "$API_V1/lessons/ids"
    const val EXERCISES_LESSON_ID = "$API_V1/exercises/{lessonId}"
}