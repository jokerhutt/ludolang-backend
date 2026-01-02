package com.testingpractice.duoclonebackend.catalog.api.controller

import com.testingpractice.duoclonebackend.auth.app.service.JwtService
import com.testingpractice.duoclonebackend.catalog.api.dto.ExerciseDto
import com.testingpractice.duoclonebackend.catalog.api.dto.LessonDto
import com.testingpractice.duoclonebackend.catalog.app.service.ExerciseService
import com.testingpractice.duoclonebackend.catalog.app.service.LessonService
import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.LESSONS)
class LessonController(
    private val lessonService: LessonService,
    private val exerciseService: ExerciseService,
    private val jwtService: JwtService
) {

    @GetMapping(pathConstants.GET_LESSONS_FROM_IDS)
    fun getLessonsByIds(
        @RequestParam lessonIds: List<Int>,
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): List<LessonDto> {
        return lessonService.getLessonsByIds(lessonIds, userId)
    }

    @GetMapping(pathConstants.GET_EXERCISES_BY_LESSON)
    fun getExercisesByLessonId(
        @PathVariable lessonId: Int,
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): List<ExerciseDto> {
        return exerciseService.getExercisesForLesson(lessonId, userId)
    }
}