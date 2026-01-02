package com.testingpractice.duoclonebackend.progress.app.service

import com.testingpractice.duoclonebackend.progress.api.dto.NewStreakCount
import com.testingpractice.duoclonebackend.user.domain.entity.User
import com.testingpractice.duoclonebackend.user.infra.repository.UserRepository
import com.testingpractice.duoclonebackend.utils.DateUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.Clock
import java.time.LocalDate

@Service
open class StreakService(
    private val clock: Clock,
    private val userRepository: UserRepository
) {

    @Transactional
    open fun updateUserStreak(user: User): NewStreakCount {
        val last: Timestamp? = user.lastSubmission
        val today: LocalDate = DateUtils.today(clock)

        val prev = user.streakLength ?: 0
        var next = prev

        if (last == null) {
            next = 1
        } else {
            val lastDay =
                last.toInstant().atZone(clock.zone).toLocalDate()

            val isToday = lastDay == today
            val isYesterday = lastDay == today.minusDays(1)

            if (isYesterday || prev == 0) {
                next = prev + 1
            } else if (!isToday) {
                next = 1
            }
        }

        user.streakLength = next
        user.lastSubmission = DateUtils.nowTs(clock)
        userRepository.save(user)

        return NewStreakCount(prev, next)
    }
}