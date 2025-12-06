package com.testingpractice.duoclonebackend.constants;

public class pathConstants {

  public static final String API_V1 = "/api";

  // ----------------------------- API PREFIXES -----------------------------
  public static final String COURSES = API_V1 + "/courses";
  public static final String EXERCISES = API_V1 + "/exercises";
  public static final String EXERCISES_ATTEMPTS = EXERCISES + "/attempts";
  public static final String UNITS = API_V1 + "/units";
  public static final String USERS = API_V1 + "/users";
  public static final String LESSONS = API_V1 + "/lessons";
  public static final String LESSONS_COMPLETIONS = LESSONS + "/completions";
  public static final String SECTIONS = API_V1 + "/sections";
  public static final String QUESTS = API_V1 + "/quests";
  public static final String FOLLOWS = API_V1 + "/follows";
  public static final String MONTHLY_CHALLENGES = API_V1 + "/monthly-challenges";
  public static final String LEADERBOARD = API_V1 + "/leaderboard";
  public static final String AUTH = API_V1 + "/auth";
  // ----------------------------- GET REQUESTS -----------------------------
  public static final String GET_USER_COURSE_PROGRESS = "/progress/{courseId}";
  public static final String GET_USER_BY_ID = "/{userId}";
  public static final String GET_QUESTS_BY_USER = "/get";
  public static final String GET_MONTHLY_CHALLENGE_BY_USER = "/get";
  public static final String GET_FOLLOWS_BY_USER = "/{userId}";
  public static final String GET_TOP_LEADERBOARD = "/top";
  public static final String GET_ALL_COURSES = "/all";
  public static final String GET_AVATARS = "/avatars";
  public static final String GET_USER_COURSES = "/get/{userId}";

  // ===== GET DTO LIST BY PARENT =====
  public static final String GET_UNITS_BY_SECTION = "/{sectionId}/units";
  public static final String GET_LESSONS_BY_UNIT = "/{unitId}/lessons";
  public static final String GET_SECTIONS_BY_COURSE = "/{courseID}/sections";

  public static final String GET_BULK_SECTIONS = "/getBulk/{sectionId}";

  // !! TRANSACTIONAL !!//
  public static final String GET_EXERCISES_BY_LESSON = "{lessonId}/exercises";

  // ===== GET DTO LIST FROM IDS =====
  public static final String GET_UNITS_FROM_IDS = "/ids";
  public static final String GET_LESSONS_FROM_IDS = "/ids";
  public static final String GET_SECTIONS_FROM_IDS = "/ids";
  public static final String GET_USERS_FROM_IDS = "/ids";

  // ===== GET IDS LIST BY PARENT =====
  public static final String GET_LESSON_IDS_BY_UNIT = GET_LESSONS_BY_UNIT + "/ids";
  public static final String GET_UNIT_IDS_BY_SECTION = GET_UNITS_BY_SECTION + "/ids";
  public static final String GET_SECTION_IDS_BY_COURSE = GET_SECTIONS_BY_COURSE + "/ids";

  // ===== GET PAGINATED ===== //
  public static final String GET_PAGINATED_LEADERBOARD = "/paginated";
  public static final String GET_PAGINATED_FOLLOWERS_AND_FOLLOWING = "/paginated";

  // ----------------------------- POST REQUESTS AND MUTATIONS -----------------------------
  public static final String SUBMIT_EXERCISE = "/submit";
  public static final String SUBMIT_COMPLETED_LESSON = "/submit";

  public static final String FOLLOW_USER = "/follow";
  public static final String UNFOLLOW_USER = "/unfollow";

  public static final String CHANGE_COURSE = "/change";

  public static final String GOOGLE_LOGIN = "/google-login";
  public static final String UPDATE_AVATAR = "/update-avatar";
  public static final String GOOGLE_LOGOUT = "/logout";
  public static final String AUTH_ME = "/me";

  public static final String CATALOG = API_V1 + "/catalog";
  public static final String SECTION_TREE = "/{sectionId}/tree";
  public static final String UNITS_IDS = API_V1 + "/units/ids";
  public static final String LESSONS_IDS = API_V1 + "/lessons/ids";
  public static final String EXERCISES_LESSON_ID = API_V1 + "/exercises/{lessonId}";



}
