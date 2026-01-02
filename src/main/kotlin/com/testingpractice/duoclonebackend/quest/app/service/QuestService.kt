package com.testingpractice.duoclonebackend.quest.app.service
import com.testingpractice.duoclonebackend.commons.exception.ApiException
import com.testingpractice.duoclonebackend.commons.exception.ErrorCode
import com.testingpractice.duoclonebackend.quest.api.dto.QuestResponse
import com.testingpractice.duoclonebackend.quest.domain.entity.QuestDefinition
import com.testingpractice.duoclonebackend.quest.domain.entity.UserDailyQuest
import com.testingpractice.duoclonebackend.quest.domain.enums.QuestCode
import com.testingpractice.duoclonebackend.quest.infra.repository.QuestDefinitionRepository
import com.testingpractice.duoclonebackend.quest.infra.repository.UserDailyQuestRepository
import com.testingpractice.duoclonebackend.utils.DateUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.LocalDate

@Service
open class QuestService(
    private val questDefinitionRepository: QuestDefinitionRepository,
    private val userDailyQuestRepository: UserDailyQuestRepository,
    private val monthlyChallengeService: MonthlyChallengeService,
    private val clock: Clock
) {

    @Transactional
    open fun getQuestsForUser(userId: Int): List<QuestResponse> {
        val today = DateUtils.today(clock)

        val questDefinitions = questDefinitionRepository.findAllByActive(true)

        refreshDailyActiveQuests(userId, questDefinitions, today)

        val userDailyQuests =
            userDailyQuestRepository.findAllByIdUserIdAndIdDate(userId, today)

        return parseToQuestResponseList(questDefinitions, userDailyQuests)
    }

    @Transactional
    open fun updateQuestProgress(userId: Int, questCode: QuestCode) {
        val questDefinition = questDefinitionRepository
            .findByCodeAndActiveTrue(questCode.name)
            .orElseThrow { ApiException(ErrorCode.QUEST_NOT_FOUND) }

        val today = DateUtils.today(clock)

        val userDailyQuest = userDailyQuestRepository
            .findByIdUserIdAndIdQuestDefIdAndIdDate(userId, questDefinition.id!!, today)
            .orElseGet {
                createNewUserDailyQuest(questDefinition, userId, today)
            }

        if (userDailyQuest.progress < questDefinition.target) {
            userDailyQuest.progress += 1
            monthlyChallengeService.addChallengeProgress(userId)
        }
    }

    private fun parseToQuestResponseList(
        questDefinitions: List<QuestDefinition>,
        userDailyQuests: List<UserDailyQuest>
    ): List<QuestResponse> {
        val definitionById = questDefinitions.associateBy { it.id }

        return userDailyQuests.map { userDailyQuest ->
            val definition = definitionById[userDailyQuest.id.questDefId]
                ?: throw IllegalStateException("Quest definition missing")

            QuestResponse(
                code = definition.code,
                progress = userDailyQuest.progress,
                total = definition.target,
                active = definition.active
            )
        }
    }

    @Transactional
    open fun refreshDailyActiveQuests(
        userId: Int,
        questDefinitions: List<QuestDefinition>,
        today: LocalDate
    ) {
        questDefinitions.forEach { def ->
            createNewUserDailyQuest(def, userId, today)
        }
    }

    @Transactional
    open fun createNewUserDailyQuest(
        questDefinition: QuestDefinition,
        userId: Int,
        today: LocalDate
    ): UserDailyQuest {

        userDailyQuestRepository.upsertCreate(
            userId,
            questDefinition.id!!,
            today
        )

        return userDailyQuestRepository
            .findByIdUserIdAndIdQuestDefIdAndIdDate(userId, questDefinition.id!!, today)
            .orElseThrow { ApiException(ErrorCode.USER_DAILY_QUEST_NOT_FOUND) }
    }
}