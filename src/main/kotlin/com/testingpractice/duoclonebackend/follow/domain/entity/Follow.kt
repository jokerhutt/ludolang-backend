package com.testingpractice.duoclonebackend.follow.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Timestamp

@Entity
@Table(name = "follows")
class Follow(
    @Id @GeneratedValue
    var id: Int? = null,

    @Column(nullable = false)
    var followerId: Int,

    @Column(nullable = false)
    var followedId: Int,

    var createdAt: Timestamp
)