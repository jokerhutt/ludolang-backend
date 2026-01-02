package com.testingpractice.duoclonebackend.quest.domain.entity.embeddable

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.time.LocalDate

@Embeddable
data class UserDailyQuestId(

    @Column(name = "user_id")
    val userId: Int,

    @Column(name = "quest_def_id")
    val questDefId: Int,

    @Column(name = "date")
    val date: LocalDate
) : Serializable