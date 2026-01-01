package com.testingpractice.duoclonebackend.quest.app.service

import com.testingpractice.duoclonebackend.exception.ApiException
import com.testingpractice.duoclonebackend.exception.ErrorCode
import com.testingpractice.duoclonebackend.quest.api.dto.QuestResponse
import com.testingpractice.duoclonebackend.quest.domain.entity.embeddable.UserMonthlyChallengeId
import com.testingpractice.duoclonebackend.quest.infra.repository.MonthlyChallengeDefinitionRepository
import com.testingpractice.duoclonebackend.quest.infra.repository.UserMonthlyChallengeRepository
import com.testingpractice.duoclonebackend.utils.DateUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.LocalDate

@Service
open class MonthlyChallengeService(
    private val monthlyChallengeDefinitionRepository: MonthlyChallengeDefinitionRepository,
    private val userMonthlyChallengeRepository: UserMonthlyChallengeRepository,
    private val clock: Clock
) {

    @Transactional
    open fun getMonthlyChallengeForUser(userId: Int): QuestResponse {
        val today = DateUtils.today(clock)

        val def = monthlyChallengeDefinitionRepository.findByActive(true)
            ?: throw ApiException(ErrorCode.MONTHLY_CHALLENGE_NOT_FOUND)

        userMonthlyChallengeRepository.upsertCreate(
            userId,
            def.id ?: throw ApiException(ErrorCode.MONTHLY_CHALLENGE_INVALID),
            today.year,
            today.monthValue
        )

        val userMonthlyChallengeId =
            getMonthlyChallengeId(userId, def.id, today)

        val umc = userMonthlyChallengeRepository
            .findById(userMonthlyChallengeId)
            .orElseThrow {
                ApiException(ErrorCode.MONTHLY_CHALLENGE_NOT_FOUND)
            }

        return QuestResponse(
            code = def.code,
            progress = umc.progress,
            total = def.target,
            active = def.active
        )
    }

    @Transactional
    open fun addChallengeProgress(userId: Int) {
        val today = DateUtils.today(clock)

        val def = monthlyChallengeDefinitionRepository.findByActive(true)
            ?: throw ApiException(ErrorCode.MONTHLY_CHALLENGE_NOT_FOUND)

        userMonthlyChallengeRepository.upsertIncrement(
            userId,
            def.id ?: throw ApiException(ErrorCode.MONTHLY_CHALLENGE_INVALID),
            today.year,
            today.monthValue,
            1
        )
    }

    private fun getMonthlyChallengeId(
        userId: Int,
        challengeDefId: Int,
        date: LocalDate
    ): UserMonthlyChallengeId {
        return UserMonthlyChallengeId(
            userId = userId,
            challengeDefId = challengeDefId,
            year = date.year,
            month = date.monthValue
        )
    }
}