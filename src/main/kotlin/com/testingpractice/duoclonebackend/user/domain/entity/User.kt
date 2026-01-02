package com.testingpractice.duoclonebackend.user.domain.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "users")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "current_course_id")
    var currentCourseId: Int? = null,

    @Column(name = "username", nullable = false)
    val username: String,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "pfp_source")
    var pfpSrc: String? = null,

    @Column(name = "points", nullable = false)
    var points: Int = 0,

    @Column(name = "created_at", nullable = false)
    val createdAt: Timestamp,

    @Column(name = "last_submission")
    var lastSubmission: Timestamp? = null,

    @Column(name = "streak_length", nullable = false)
    var streakLength: Int = 0
)