package com.testingpractice.duoclonebackend.service;

import com.testingpractice.duoclonebackend.dto.GoogleUserInfo;
import com.testingpractice.duoclonebackend.dto.UserDto;
import com.testingpractice.duoclonebackend.entity.User;
import com.testingpractice.duoclonebackend.mapper.UserMapper;
import com.testingpractice.duoclonebackend.repository.UserRepository;
import com.testingpractice.duoclonebackend.user.api.dto.UserResponse;
import com.testingpractice.duoclonebackend.utils.UserCreationUtils;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreationService {

  private final UserRepository userRepository;
  private final QuestService questService;
  private final MonthlyChallengeService monthlyChallengeService;
  private final LookupService lookupService;
  private final UserMapper userMapper;

  @Transactional
  public User createUser(GoogleUserInfo googleUser) {

    User newUser = new User();
    newUser.setEmail(googleUser.getEmail());
    newUser.setFirstName(googleUser.getGivenName());
    newUser.setLastName(googleUser.getFamilyName());
    newUser.setUsername(UserCreationUtils.generateUsername(googleUser.getName()));
    newUser.setPfpSrc(UserCreationUtils.getRandomProfilePic());
    newUser.setPoints(0);
    newUser.setStreakLength(0);
    newUser.setCreatedAt(Timestamp.from(Instant.now()));
    userRepository.save(newUser);

    // This generates daily quests and monthly challenge for the new user
    questService.getQuestsForUser(newUser.getId());
    monthlyChallengeService.getMonthlyChallengeForUser(newUser.getId());

    return newUser;
  }

  @Transactional
  public UserResponse updateAvatar(Integer userId, String newAvatarSrc) {
    User user = lookupService.userOrThrow(userId);
    user.setPfpSrc(newAvatarSrc);
    userRepository.save(user);
    return userMapper.toDto(user);
  }

  public List<String> getDefaultProfilePics() {
    return UserCreationUtils.getAllProfilePics();
  }
}
