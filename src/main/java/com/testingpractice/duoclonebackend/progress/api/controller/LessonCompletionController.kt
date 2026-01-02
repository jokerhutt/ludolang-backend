package com.testingpractice.duoclonebackend.progress.api.controller

import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.dto.LessonCompleteRequest
import com.testingpractice.duoclonebackend.dto.LessonCompleteResponse
import com.testingpractice.duoclonebackend.service.LessonCompletionService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.LESSONS_COMPLETIONS)
class LessonCompletionController(
    private val lessonCompletionService: LessonCompletionService
) {

    @PostMapping(pathConstants.SUBMIT_COMPLETED_LESSON)
    fun completeLesson(
        @RequestBody lessonCompleteRequest: LessonCompleteRequest,
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): LessonCompleteResponse {
        return lessonCompletionService.getCompletedLesson(
            lessonCompleteRequest.lessonId(),
            userId,
            lessonCompleteRequest.courseId()
        )
    }
}