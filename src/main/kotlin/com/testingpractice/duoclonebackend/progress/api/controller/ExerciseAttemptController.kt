package com.testingpractice.duoclonebackend.progress.api.controller
import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.progress.api.dto.ExerciseAttemptRequest
import com.testingpractice.duoclonebackend.progress.api.dto.ExerciseAttemptResponse
import com.testingpractice.duoclonebackend.progress.app.service.ExerciseAttemptService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.EXERCISES_ATTEMPTS)
class ExerciseAttemptController(
    private val exerciseAttemptService: ExerciseAttemptService
) {

    @PostMapping(pathConstants.SUBMIT_EXERCISE)
    fun submitExerciseAttempt(
        @RequestBody exerciseAttemptRequest: ExerciseAttemptRequest,
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): ExerciseAttemptResponse {
        return exerciseAttemptService.submitExerciseAttempt(
            exerciseAttemptRequest.exerciseId,
            exerciseAttemptRequest.optionIds,
            userId
        )
    }
}