package com.testingpractice.duoclonebackend.leaderboard.api.controller
import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.leaderboard.api.dto.LeaderboardPageDto
import com.testingpractice.duoclonebackend.leaderboard.app.service.LeaderboardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.LEADERBOARD)
class LeaderboardController(
    private val leaderboardService: LeaderboardService
) {

    @GetMapping(pathConstants.GET_PAGINATED_LEADERBOARD)
    fun get(
        @RequestParam(required = false) cursor: String?,
        @RequestParam(defaultValue = "20") limit: Int
    ): LeaderboardPageDto {
        val clampedLimit = minOf(maxOf(limit, 1), 100) // 1..100
        return leaderboardService.getLeaderboardPage(cursor, clampedLimit)
    }
}