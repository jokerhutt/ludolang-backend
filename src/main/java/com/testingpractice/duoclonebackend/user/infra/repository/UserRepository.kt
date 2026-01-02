package com.testingpractice.duoclonebackend.user.infra.repository

import com.testingpractice.duoclonebackend.user.domain.entity.User
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface UserRepository : JpaRepository<User, Int> {

    @Query(
        """
        SELECT u FROM User u
        ORDER BY u.points DESC, u.id ASC
        """
    )
    fun findTopOrdered(pageable: Pageable): List<User>

    @Query(
        """
        SELECT u FROM User u
        WHERE (u.points < :points)
           OR (u.points = :points AND u.id > :id)
        ORDER BY u.points DESC, u.id ASC
        """
    )
    fun findAfterCursor(
        @Param("points") points: Int,
        @Param("id") id: Int,
        pageable: Pageable
    ): List<User>

    fun findByEmail(email: String): Optional<User>
}