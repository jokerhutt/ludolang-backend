package com.testingpractice.duoclonebackend.progress.infra.repository

import com.testingpractice.duoclonebackend.progress.domain.entity.AttemptOption
import org.springframework.data.jpa.repository.JpaRepository

interface ExerciseAttemptOptionRepository :
    JpaRepository<AttemptOption, Int>