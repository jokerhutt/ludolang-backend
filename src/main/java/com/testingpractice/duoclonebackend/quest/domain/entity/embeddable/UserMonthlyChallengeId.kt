package com.testingpractice.duoclonebackend.quest.domain.entity.embeddable

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class UserMonthlyChallengeId(

    @Column(name = "user_id")
    val userId: Int,

    @Column(name = "challenge_def_id")
    val challengeDefId: Int,

    @Column(name = "year")
    val year: Int,

    @Column(name = "month")
    val month: Int
)