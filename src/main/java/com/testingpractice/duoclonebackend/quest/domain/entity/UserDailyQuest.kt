package com.testingpractice.duoclonebackend.quest.domain.entity

import com.testingpractice.duoclonebackend.quest.domain.entity.embeddable.UserDailyQuestId
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.sql.Timestamp

@Entity
@Table(name = "user_daily_quest")
data class UserDailyQuest(

    @EmbeddedId
    val id: UserDailyQuestId,

    @Column(nullable = false)
    var progress: Int = 0,

    @Column(name = "completed_at")
    var completedAt: Timestamp? = null,

    @Column(name = "reward_claimed", nullable = false)
    var rewardClaimed: Boolean = false
)