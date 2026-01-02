package com.testingpractice.duoclonebackend.leaderboard.app.service
import com.testingpractice.duoclonebackend.leaderboard.api.dto.LeaderboardPageDto
import com.testingpractice.duoclonebackend.user.api.dto.UserResponse
import com.testingpractice.duoclonebackend.user.app.mapper.UserMapper
import com.testingpractice.duoclonebackend.user.domain.entity.User
import com.testingpractice.duoclonebackend.user.infra.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import kotlin.collections.isNotEmpty

@Service
class LeaderboardService(
    private val repo: UserRepository,
    private val mapper: UserMapper
) {

    fun getLeaderboardPage(cursor: String?, limit: Int): LeaderboardPageDto {
        val users: List<User> =
            if (cursor == null || cursor.isBlank()) {
                repo.findTopOrdered(PageRequest.of(0, limit))
            } else {
                val parts = cursor.split(":")
                val points = parts[0].toInt()
                val id = parts[1].toInt()
                repo.findAfterCursor(points, id, PageRequest.of(0, limit))
            }

        val dtos: List<UserResponse> =
            users.map { user -> mapper.toUserResponse(user) }

        var next: String? = null
        if (users.isNotEmpty() && users.size == limit) {
            val last = users[users.size - 1]
            next = "${last.points}:${last.id}"
        }

        return LeaderboardPageDto(dtos, next)
    }
}