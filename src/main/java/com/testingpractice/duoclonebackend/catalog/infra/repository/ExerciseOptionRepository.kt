package com.testingpractice.duoclonebackend.catalog.infra.repository

import com.testingpractice.duoclonebackend.catalog.domain.entity.ExerciseOption
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import kotlin.collections.Collection

interface ExerciseOptionRepository :
    JpaRepository<ExerciseOption, Int> {

    fun findAllByExerciseId(exerciseId: Int): List<ExerciseOption>

    fun findAllByIdIn(ids: Collection<Int>): List<ExerciseOption>

    @Query(
        """
        select eo.id
        from ExerciseOption eo
        where eo.exerciseId = :exerciseId
          and eo.answerOrder is not null
        order by eo.answerOrder asc, eo.id asc
        """
    )
    fun findCorrectOptionIds(
        @Param("exerciseId") exerciseId: Int
    ): List<Int>
}