package com.testingpractice.duoclonebackend.catalog.infra.repository

import com.testingpractice.duoclonebackend.catalog.domain.entity.Exercise
import org.springframework.data.jpa.repository.JpaRepository

interface ExerciseRepository :
    JpaRepository<Exercise, Int> {

    fun findAllByLessonId(lessonId: Int): List<Exercise>
}