package com.testingpractice.duoclonebackend.quest.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "monthly_challenge_definition")
data class MonthlyChallengeDefinition(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,

    @Column(name = "code", nullable = false)
    val code: String,

    @Column(name = "target", nullable = false)
    val target: Int,

    @Column(name = "reward_points", nullable = false)
    val rewardPoints: Int,

    @Column(name = "active", nullable = false)
    var active: Boolean = true
)