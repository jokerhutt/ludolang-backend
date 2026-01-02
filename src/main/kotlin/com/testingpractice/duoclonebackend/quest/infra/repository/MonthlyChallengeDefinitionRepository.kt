package com.testingpractice.duoclonebackend.quest.infra.repository

import com.testingpractice.duoclonebackend.quest.domain.entity.MonthlyChallengeDefinition
import org.springframework.data.jpa.repository.JpaRepository

interface MonthlyChallengeDefinitionRepository :
    JpaRepository<MonthlyChallengeDefinition, Int> {

    fun findByActive(active: Boolean): MonthlyChallengeDefinition?
}