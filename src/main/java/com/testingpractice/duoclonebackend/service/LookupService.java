package com.testingpractice.duoclonebackend.service;

import com.testingpractice.duoclonebackend.entity.*;
import com.testingpractice.duoclonebackend.exception.ApiException;
import com.testingpractice.duoclonebackend.exception.ErrorCode;
import com.testingpractice.duoclonebackend.repository.*;
import com.testingpractice.duoclonebackend.user.domain.entity.User;
import com.testingpractice.duoclonebackend.user.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LookupService {

  private final UserRepository userRepository;
  private final LessonRepository lessonRepository;
  private final UnitRepository unitRepository;
  private final CourseRepository courseRepository;
  private final UserCourseProgressRepository userCourseProgressRepository;

  public User userOrThrow(int userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
  }

  public Lesson lessonOrThrow(int lessonId) {
    return lessonRepository
        .findById(lessonId)
        .orElseThrow(() -> new ApiException(ErrorCode.LESSON_NOT_FOUND));
  }

  public Unit unitOrThrow(int unitId) {
    return unitRepository
        .findById(unitId)
        .orElseThrow(() -> new ApiException(ErrorCode.UNIT_NOT_FOUND));
  }

  public Course courseOrThrow(int unitId) {
    return courseRepository
        .findById(unitId)
        .orElseThrow(() -> new ApiException(ErrorCode.COURSE_NOT_FOUND));
  }
}
