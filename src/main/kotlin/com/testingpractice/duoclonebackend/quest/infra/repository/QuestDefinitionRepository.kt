package com.testingpractice.duoclonebackend.quest.infra.repository

import com.testingpractice.duoclonebackend.quest.domain.entity.QuestDefinition
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface QuestDefinitionRepository :
    JpaRepository<QuestDefinition, Int> {

    fun findAllByActive(active: Boolean): List<QuestDefinition>

    fun findByCode(code: String): Optional<QuestDefinition>

    fun findByCodeAndActiveTrue(code: String): Optional<QuestDefinition>
}