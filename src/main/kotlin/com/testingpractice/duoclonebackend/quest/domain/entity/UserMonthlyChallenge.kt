package com.testingpractice.duoclonebackend.quest.domain.entity

import com.testingpractice.duoclonebackend.quest.domain.entity.embeddable.UserMonthlyChallengeId
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "user_monthly_challenge")
data class UserMonthlyChallenge(

    @EmbeddedId
    val id: UserMonthlyChallengeId,

    @Column(nullable = false)
    var progress: Int = 0,

    @Column(name = "completed_at")
    var completedAt: Timestamp? = null,

    @Column(name = "reward_claimed", nullable = false)
    var rewardClaimed: Boolean = false
)
