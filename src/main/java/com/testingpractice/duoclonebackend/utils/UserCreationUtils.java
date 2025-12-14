package com.testingpractice.duoclonebackend.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UserCreationUtils {

  private static final String BASE_URL = "https://storage.googleapis.com/ludolang-media/av";

  public static String getRandomProfilePic() {
    int num = ThreadLocalRandom.current().nextInt(2, 14);
    return BASE_URL + num + ".png";
  }

  public static String generateUsername(String name) {
    if (name == null) name = "user";
    String base = name.replaceAll("\\s+", "").toLowerCase();
    int rand = ThreadLocalRandom.current().nextInt(100, 1000);
    return base + rand;
  }

  public static List<String> getAllProfilePics() {
    List<String> pics = new ArrayList<>();
    for (int i = 2; i <= 13; i++) {
      pics.add(BASE_URL + i + ".png");
    }
    return pics;
  }
}
