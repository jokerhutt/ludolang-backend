package com.testingpractice.duoclonebackend.quest.infra.repository

import com.testingpractice.duoclonebackend.quest.domain.entity.UserMonthlyChallenge
import com.testingpractice.duoclonebackend.quest.domain.entity.embeddable.UserMonthlyChallengeId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserMonthlyChallengeRepository :
    JpaRepository<UserMonthlyChallenge, UserMonthlyChallengeId> {

    @Modifying
    @Query(
        value = """
        INSERT INTO user_monthly_challenge
          (user_id, challenge_def_id, year, month, progress, reward_claimed, completed_at)
        VALUES (:userId, :defId, :year, :month, 0, false, NULL)
        ON DUPLICATE KEY UPDATE user_id = user_id
        """,
        nativeQuery = true
    )
    fun upsertCreate(
        @Param("userId") userId: Int,
        @Param("defId") defId: Int,
        @Param("year") year: Int,
        @Param("month") month: Int
    ): Int

    @Modifying
    @Query(
        value = """
        INSERT INTO user_monthly_challenge
          (user_id, challenge_def_id, year, month, progress, reward_claimed, completed_at)
        VALUES (:userId, :defId, :year, :month, :delta, false, NULL)
        ON DUPLICATE KEY UPDATE progress = progress + :delta
        """,
        nativeQuery = true
    )
    fun upsertIncrement(
        @Param("userId") userId: Int,
        @Param("defId") defId: Int,
        @Param("year") year: Int,
        @Param("month") month: Int,
        @Param("delta") delta: Int
    ): Int
}