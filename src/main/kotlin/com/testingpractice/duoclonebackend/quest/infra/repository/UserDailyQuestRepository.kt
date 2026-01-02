package com.testingpractice.duoclonebackend.quest.infra.repository
import com.testingpractice.duoclonebackend.quest.domain.entity.UserDailyQuest
import com.testingpractice.duoclonebackend.quest.domain.entity.embeddable.UserDailyQuestId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.util.Optional

interface UserDailyQuestRepository :
    JpaRepository<UserDailyQuest, UserDailyQuestId> {

    fun findAllByIdUserIdAndIdDate(
        userId: Int,
        date: LocalDate
    ): List<UserDailyQuest>

    fun findByIdUserIdAndIdQuestDefIdAndIdDate(
        userId: Int,
        questDefId: Int,
        date: LocalDate
    ): Optional<UserDailyQuest>

    @Modifying
    @Query(
        value = """
        INSERT INTO user_daily_quest (user_id, quest_def_id, date, progress, reward_claimed)
        VALUES (?1, ?2, ?3, 0, false)
        ON DUPLICATE KEY UPDATE user_id = user_id
        """,
        nativeQuery = true
    )
    fun upsertCreate(
        userId: Int,
        questDefId: Int,
        date: LocalDate
    )
}