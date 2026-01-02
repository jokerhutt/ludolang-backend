package com.testingpractice.duoclonebackend.progress.infra.repository

import com.testingpractice.duoclonebackend.progress.domain.entity.UserCourseProgress
import org.springframework.data.jpa.repository.JpaRepository

interface UserCourseProgressRepository :
    JpaRepository<UserCourseProgress, Int> {

    fun findByUserId(userId: Int): UserCourseProgress?

    fun findByUserIdAndCourseId(userId: Int, courseId: Int): UserCourseProgress?

    fun findAllByUserId(userId: Int): List<UserCourseProgress>
}