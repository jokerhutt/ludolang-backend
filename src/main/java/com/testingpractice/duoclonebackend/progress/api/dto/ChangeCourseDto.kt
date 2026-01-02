package com.testingpractice.duoclonebackend.progress.api.dto

import com.testingpractice.duoclonebackend.catalog.domain.entity.Course
import com.testingpractice.duoclonebackend.user.api.dto.UserResponse

data class ChangeCourseDto(
    val newUser: UserResponse,
    val newCourses: List<Course>
)