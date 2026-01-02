package com.testingpractice.duoclonebackend.follow.infra.repository

import com.testingpractice.duoclonebackend.follow.domain.entity.Follow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface FollowRepository : JpaRepository<Follow, Int> {

    @Query("select f.followedId from Follow f where f.followerId = :userId")
    fun findFollowedIdsByFollowerId(
        @Param("userId") userId: Int
    ): List<Int>

    @Query("select f.followerId from Follow f where f.followedId = :userId")
    fun findFollowerIdsByFollowedId(
        @Param("userId") userId: Int
    ): List<Int>

    fun existsByFollowerIdAndFollowedId(
        followerId: Int,
        followedId: Int
    ): Boolean

    fun findByFollowerIdAndFollowedId(
        followerId: Int,
        followedId: Int
    ): Follow
}