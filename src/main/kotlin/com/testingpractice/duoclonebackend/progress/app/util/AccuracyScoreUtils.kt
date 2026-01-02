package com.testingpractice.duoclonebackend.progress.app.util

import com.testingpractice.duoclonebackend.progress.domain.entity.ExerciseAttempt

object AccuracyScoreUtils {

    fun getAccuracyMessage(accuracy: Int): String {
        return when {
            accuracy <= 30 -> "OK"
            accuracy <= 60 -> "GOOD"
            accuracy <= 80 -> "GREAT"
            else -> "AMAZING"
        }
    }

    fun getLessonAccuracy(exerciseAttempts: List<ExerciseAttempt>): Int {
        val earned = exerciseAttempts.sumOf { it.score ?: 0 }

        val max = exerciseAttempts.size * 5
        return if (max == 0) 0 else ((earned.toDouble() / max) * 100).toInt()
    }

    fun getLessonPoints(
        exerciseAttempts: List<ExerciseAttempt>,
        isFirstAttempt: Boolean,
        accuracy: Int
    ): Int {

        val isPerfect = accuracy >= 100
        var basePoints = 0

        if (isPerfect) {
            basePoints += 5
        }

        basePoints += if (isFirstAttempt) {
            exerciseAttempts.sumOf { it.score ?: 0 }
        } else {
            5
        }

        return basePoints
    }
}