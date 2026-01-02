package com.testingpractice.duoclonebackend.quest.api.controller

import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.quest.api.dto.QuestResponse
import com.testingpractice.duoclonebackend.quest.app.service.QuestService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.QUESTS)
class QuestController(
    private val questService: QuestService
) {

    @GetMapping(pathConstants.GET_QUESTS_BY_USER)
    fun getQuestsByUserId(
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): List<QuestResponse> {
        return questService.getQuestsForUser(userId)
    }
}