package com.testingpractice.duoclonebackend.catalog.infra.repository

import com.testingpractice.duoclonebackend.catalog.domain.entity.Exercise
import com.testingpractice.duoclonebackend.catalog.domain.entity.ExerciseOption
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Collection

interface ExerciseRepository :
    JpaRepository<Exercise, Int> {

    fun findAllByLessonId(lessonId: Int): List<Exercise>

    fun findAllByIdIn(ids: Collection<Int>): List<ExerciseOption>
}