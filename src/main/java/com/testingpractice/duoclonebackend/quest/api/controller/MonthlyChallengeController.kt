package com.testingpractice.duoclonebackend.quest.api.controller

import com.testingpractice.duoclonebackend.constants.pathConstants
import com.testingpractice.duoclonebackend.quest.api.dto.QuestResponse
import com.testingpractice.duoclonebackend.quest.app.service.MonthlyChallengeService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.MONTHLY_CHALLENGES)
class MonthlyChallengeController(
    private val monthlyChallengeService: MonthlyChallengeService
) {

    @GetMapping(pathConstants.GET_MONTHLY_CHALLENGE_BY_USER)
    fun getMonthlyChallenge(
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): QuestResponse {
        return monthlyChallengeService.getMonthlyChallengeForUser(userId)
    }
}